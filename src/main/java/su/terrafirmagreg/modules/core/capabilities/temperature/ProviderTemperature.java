package su.terrafirmagreg.modules.core.capabilities.temperature;

import su.terrafirmagreg.data.DamageSources;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.ModuleCore;
import su.terrafirmagreg.modules.core.features.ambiental.modifiers.ModifierBase;
import su.terrafirmagreg.modules.core.features.ambiental.modifiers.ModifierBlock;
import su.terrafirmagreg.modules.core.features.ambiental.modifiers.ModifierEnvironmental;
import su.terrafirmagreg.modules.core.features.ambiental.modifiers.ModifierEquipment;
import su.terrafirmagreg.modules.core.features.ambiental.modifiers.ModifierItem;
import su.terrafirmagreg.modules.core.features.ambiental.modifiers.ModifierStorage;
import su.terrafirmagreg.modules.core.features.ambiental.modifiers.ModifierTile;
import su.terrafirmagreg.modules.core.network.SCPacketTemperature;
import su.terrafirmagreg.modules.food.api.FoodStatsTFC;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;


import org.jetbrains.annotations.NotNull;

import lombok.Getter;

public class ProviderTemperature implements ICapabilityTemperature {

    public static final float BAD_MULTIPLIER = 0.002f;
    public static final float GOOD_MULTIPLIER = 0.002f;
    public static final float CHANGE_CAP = 7.5f;
    public static final float HIGH_CHANGE = 0.20f;
    public static float AVERAGE = ConfigCore.MISC.TEMPERATURE.averageTemperature;
    public static float HOT_THRESHOLD = ConfigCore.MISC.TEMPERATURE.hotTemperature;
    public static float COOL_THRESHOLD = ConfigCore.MISC.TEMPERATURE.coldTemperature;
    public static float BURN_THRESHOLD = ConfigCore.MISC.TEMPERATURE.burningTemperature;
    public static float FREEZE_THRESHOLD = ConfigCore.MISC.TEMPERATURE.freezingTemperature;
    @Getter
    private final EntityPlayer player;
    /**
     * The capability this is for
     */
    public ModifierStorage modifiers = new ModifierStorage();
    public boolean isRising;
    @Getter
    public float temperature = AVERAGE;
    @Getter
    public float target = AVERAGE;
    @Getter
    public float potency = 1f;
    public int tick = 0;
    public int damageTick = 0;

    public ProviderTemperature() {
        this(null);
    }

    public ProviderTemperature(EntityPlayer player) {
        this.player = player;
    }

    public void clearModifiers() {
        this.modifiers = new ModifierStorage();
    }

    public void evaluateModifiers() {
        this.clearModifiers();
        ModifierItem.computeModifiers(player, modifiers);
        ModifierEnvironmental.computeModifiers(player, modifiers);
        ModifierBlock.computeModifiers(player, modifiers);
        ModifierTile.computeModifiers(player, modifiers);
        ModifierEquipment.getModifiers(player, modifiers);

        target = modifiers.getTargetTemperature();
        potency = modifiers.getTotalPotency();
    }

    public float getTemperatureChange() {
        float target = this.getTarget();
        float speed = getPotency() * ConfigCore.MISC.TEMPERATURE.temperatureMultiplier;
        float change = Math.min(CHANGE_CAP, Math.max(-CHANGE_CAP, target - temperature));
        float newTemp = temperature + change;
        boolean isRising = true;
        if ((temperature < AVERAGE && newTemp > temperature) || (temperature > AVERAGE && newTemp < temperature)) {
            speed *= GOOD_MULTIPLIER * ConfigCore.MISC.TEMPERATURE.positiveModifier;
        } else {
            speed *= BAD_MULTIPLIER * ConfigCore.MISC.TEMPERATURE.negativeModifier;
        }
        return (change * speed);
    }

    @Override
    public void update() {
        boolean server = !player.world.isRemote;
        if (server) {
            this.setTemperature(this.getTemperature() + this.getTemperatureChange() / ConfigCore.MISC.TEMPERATURE.tickInterval);

            if (tick <= ConfigCore.MISC.TEMPERATURE.tickInterval) {
                tick++;
                return;
            } else {
                tick = 0;
                if (damageTick > 40) {
                    damageTick = 0;
                    if (ConfigCore.MISC.TEMPERATURE.takeDamage) {
                        if (this.getTemperature() > BURN_THRESHOLD) {
                            player.attackEntityFrom(DamageSources.HYPERTHERMIA, 4f);
                        } else if (this.getTemperature() < FREEZE_THRESHOLD) {
                            player.attackEntityFrom(DamageSources.HYPOTHERMIA, 4f);
                        }
                    }
                    if (ConfigCore.MISC.TEMPERATURE.loseHungerThirst) {
                        if (player.getFoodStats() instanceof FoodStatsTFC stats) {
                            if (this.getTemperature() > (HOT_THRESHOLD * 2f + BURN_THRESHOLD) / 3f) {
                                stats.addThirst(-8);
                            } else if (this.getTemperature() < (COOL_THRESHOLD * 2f + FREEZE_THRESHOLD) / 3f) {
                                stats.setFoodLevel(stats.getFoodLevel() - 1);
                            }
                        }

                    }

                } else {
                    damageTick++;
                }
            }
            this.evaluateModifiers();
            updateAndSync();
        }

    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (ModifierBase modifier : modifiers) {
            str.append(modifier.getName()).append(" -> ").append(modifier.getChange()).append(" @ ").append(modifier.getPotency()).append("\n");
        }
        return String.format(
                """
                        Body: %.1f ( %.4f )
                        Target: %.1f
                        Potency: %.4f""",
                getTemperature(),
                getTemperatureChange(),
                getTarget(),
                modifiers.getTotalPotency()
        ) + "\n" + str;
    }

    @Override
    public void setTemperature(float newTemp) {
        isRising = !(newTemp < this.getTemperature());
        this.temperature = newTemp;
    }

    @Override
    public float getChange() {
        return getTemperatureChange();
    }

    public float getChangeSpeed() {
        return getPotency();
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();
        NBTUtils.setGenericNBTValue(tag, "temperature", this.getTemperature());
        NBTUtils.setGenericNBTValue(tag, "target", this.getTarget());
        NBTUtils.setGenericNBTValue(tag, "potency", this.getPotency());
        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound tag) {
        if (tag.hasKey("temperature")) {
            this.setTemperature(tag.getFloat("temperature"));
            this.target = tag.getFloat("target");
            this.potency = tag.getFloat("potency");

        } else {
            this.setTemperature(23.4f);
        }
    }

    public void updateAndSync() {
        EntityPlayer player = getPlayer();
        if (player instanceof EntityPlayerMP entityPlayerMP) {
            ModuleCore.getPacketService().sendTo(new SCPacketTemperature(serializeNBT()), entityPlayerMP);
        }
    }

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityTemperature.CAPABILITY;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getCapability(@NotNull Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? (T) (this) : null;
    }
}
