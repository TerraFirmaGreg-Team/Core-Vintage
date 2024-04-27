package su.terrafirmagreg.modules.core.api.capabilities.temperature;

import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.modules.core.api.util.DamageSources;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;


import com.lumintorious.ambiental.TFCAmbientalConfig;
import com.lumintorious.ambiental.modifiers.BaseModifier;
import com.lumintorious.ambiental.modifiers.BlockModifier;
import com.lumintorious.ambiental.modifiers.EnvironmentalModifier;
import com.lumintorious.ambiental.modifiers.EquipmentModifier;
import com.lumintorious.ambiental.modifiers.ItemModifier;
import com.lumintorious.ambiental.modifiers.ModifierStorage;
import com.lumintorious.ambiental.network.TemperaturePacket;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.food.FoodStatsTFC;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

public class ProviderTemperature implements ICapabilityTemperature {

    public static final float BAD_MULTIPLIER = 0.002f;
    public static final float GOOD_MULTIPLIER = 0.002f;
    public static final float CHANGE_CAP = 7.5f;
    public static final float HIGH_CHANGE = 0.20f;
    public static float AVERAGE = TFCAmbientalConfig.GENERAL.averageTemperature;
    public static float HOT_THRESHOLD = TFCAmbientalConfig.GENERAL.hotTemperature;
    public static float COOL_THRESHOLD = TFCAmbientalConfig.GENERAL.coldTemperature;
    public static float BURN_THRESHOLD = TFCAmbientalConfig.GENERAL.burningTemperature;
    public static float FREEZE_THRESHOLD = TFCAmbientalConfig.GENERAL.freezingTemperature;
    /**
     * The capability this is for
     */
    public ModifierStorage modifiers = new ModifierStorage();

    @Getter
    private final EntityPlayer player;

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
        ItemModifier.computeModifiers(player, modifiers);
        EnvironmentalModifier.computeModifiers(player, modifiers);
        BlockModifier.computeModifiers(player, modifiers);
        EquipmentModifier.getModifiers(player, modifiers);

        target = modifiers.getTargetTemperature();
        potency = modifiers.getTotalPotency();
    }

    public float getTemperatureChange() {
        float target = this.getTarget();
        float speed = getPotency() * TFCAmbientalConfig.GENERAL.temperatureMultiplier;
        float change = Math.min(CHANGE_CAP, Math.max(-CHANGE_CAP, target - temperature));
        float newTemp = temperature + change;
        boolean isRising = true;
        if ((temperature < AVERAGE && newTemp > temperature) || (temperature > AVERAGE && newTemp < temperature)) {
            speed *= GOOD_MULTIPLIER * TFCAmbientalConfig.GENERAL.positiveModifier;
        } else {
            speed *= BAD_MULTIPLIER * TFCAmbientalConfig.GENERAL.negativeModifier;
        }
        return (change * speed);
    }

    @Override
    public void update() {
        boolean server = !player.world.isRemote;
        if (server) {
            this.setTemperature(this.getTemperature() + this.getTemperatureChange() / TFCAmbientalConfig.GENERAL.tickInterval);

            if (tick <= TFCAmbientalConfig.GENERAL.tickInterval) {
                tick++;
                return;
            } else {
                tick = 0;
                if (damageTick > 40) {
                    damageTick = 0;
                    if (TFCAmbientalConfig.GENERAL.takeDamage) {
                        if (this.getTemperature() > BURN_THRESHOLD) {
                            player.attackEntityFrom(DamageSources.HYPERTHERMIA, 4f);
                        } else if (this.getTemperature() < FREEZE_THRESHOLD) {
                            player.attackEntityFrom(DamageSources.HYPOTHERMIA, 4f);
                        }
                    }
                    if (TFCAmbientalConfig.GENERAL.loseHungerThirst) {
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
        for (BaseModifier modifier : modifiers) {
            str.append(modifier.getUnlocalizedName()).append(" -> ").append(modifier.getChange()).append(" @ ").append(modifier.getPotency())
                    .append("\n");
        }
        return String.format(
                """
                        Body: %.1f ( %.4f )
                        Target: %.1f
                        Potency: %.4f""",
                temperature,
                this.getTemperatureChange(),
                this.getTarget(),
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
            this.target = (tag.getFloat("target"));
            this.potency = (tag.getFloat("potency"));

        } else {
            this.setTemperature(23.4f);
        }
    }

    public void updateAndSync() {
        EntityPlayer player = getPlayer();
        if (player instanceof EntityPlayerMP entityPlayerMP) {
            TerraFirmaCraft.getNetwork().sendTo(new TemperaturePacket(serializeNBT()), entityPlayerMP);
        }
    }

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, EnumFacing facing) {
        return capability == CapabilityTemperature.CAPABILITY;
    }

    @Override
    public <T> T getCapability(@NotNull Capability<T> capability, EnumFacing facing) {
        return hasCapability(capability, facing) ? (T) (this) : null;
    }
}
