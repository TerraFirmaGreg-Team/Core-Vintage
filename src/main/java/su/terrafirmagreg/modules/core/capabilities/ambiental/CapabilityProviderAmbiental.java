package su.terrafirmagreg.modules.core.capabilities.ambiental;

import su.terrafirmagreg.api.data.DamageSources;
import su.terrafirmagreg.api.util.NBTUtils;
import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.ModuleCore;
import su.terrafirmagreg.modules.core.feature.ambiental.AmbientalModifierStorage;
import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierBase;
import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierBlock;
import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierEnvironmental;
import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierEquipment;
import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierItem;
import su.terrafirmagreg.modules.core.feature.ambiental.modifier.ModifierTile;
import su.terrafirmagreg.modules.core.network.SCPacketAmbiental;
import su.terrafirmagreg.modules.food.api.IFoodStatsTFC;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import gregtech.common.items.MetaItems;

import org.jetbrains.annotations.NotNull;

import lombok.Getter;

public class CapabilityProviderAmbiental implements ICapabilityAmbiental {

  public static final String TAG_TEMPERATURE = "temperature";
  public static final String TAG_TARGET = "target";
  public static final String TAG_POTENCY = "potency";
  

  public static final float BAD_MULTIPLIER = 0.002f;
  public static final float GOOD_MULTIPLIER = 0.002f;
  public static final float CHANGE_CAP = 7.5f;
  public static final float HIGH_CHANGE = 0.20f;

  public static float AVERAGE = ConfigCore.MISC.AMBIENTAL.averageTemperature;
  public static float HOT_THRESHOLD = ConfigCore.MISC.AMBIENTAL.hotTemperature;
  public static float COOL_THRESHOLD = ConfigCore.MISC.AMBIENTAL.coldTemperature;
  public static float BURN_THRESHOLD = ConfigCore.MISC.AMBIENTAL.burningTemperature;
  public static float FREEZE_THRESHOLD = ConfigCore.MISC.AMBIENTAL.freezingTemperature;
  public static float NANO_QUARK_ARMOR_TEMP = ConfigCore.MISC.AMBIENTAL.nanoOrQuarkTemp;
  @Getter
  private final EntityPlayer player;
  /**
   * The capability this is for
   */
  public AmbientalModifierStorage modifiers;
  public boolean isRising;
  @Getter
  public float temperature;
  @Getter
  public float target;
  @Getter
  public float potency;
  public int tick;
  public int damageTick;

  public CapabilityProviderAmbiental() {
    this(null);
  }

  public CapabilityProviderAmbiental(EntityPlayer player) {
    this.player = player;
    this.modifiers = new AmbientalModifierStorage();
    this.temperature = AVERAGE;
    this.target = AVERAGE;
    this.potency = 1f;
    this.tick = 0;
    this.damageTick = 0;
    this.isRising = true;
  }

  // TODO переделать на простое накладывания резиста
  public static boolean hasNanoOrQuarkArmorProtection(EntityPlayer player) {
    Item head = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem();
    Item chest = player.getItemStackFromSlot(EntityEquipmentSlot.CHEST).getItem();
    Item legs = player.getItemStackFromSlot(EntityEquipmentSlot.LEGS).getItem();
    Item feet = player.getItemStackFromSlot(EntityEquipmentSlot.FEET).getItem();

    Item nanoHelmet = MetaItems.NANO_HELMET.getStackForm().getItem();
    Item nanoChestplate = MetaItems.NANO_CHESTPLATE.getStackForm().getItem();
    Item nanoAdvancedChestplate = MetaItems.NANO_CHESTPLATE_ADVANCED.getStackForm().getItem();
    Item nanoLeggings = MetaItems.NANO_LEGGINGS.getStackForm().getItem();
    Item nanoBoots = MetaItems.NANO_BOOTS.getStackForm().getItem();

    Item quantumHelmet = MetaItems.QUANTUM_HELMET.getStackForm().getItem();
    Item quantumChestplate = MetaItems.QUANTUM_CHESTPLATE.getStackForm().getItem();
    Item quantumAdvancedChestplate = MetaItems.QUANTUM_CHESTPLATE_ADVANCED.getStackForm().getItem();
    Item quantumLeggings = MetaItems.QUANTUM_LEGGINGS.getStackForm().getItem();
    Item quantumBoots = MetaItems.QUANTUM_BOOTS.getStackForm().getItem();

    // Nano Armor
    if (head.equals(nanoHelmet) && (chest.equals(nanoChestplate) || chest.equals(nanoAdvancedChestplate)) && legs.equals(nanoLeggings)
        && feet.equals(nanoBoots)) {
      return true;
    }

    // Quark Armor
    return head.equals(quantumHelmet) &&
           (chest.equals(quantumChestplate) || chest.equals(quantumAdvancedChestplate)) &&
           legs.equals(quantumLeggings) &&
           feet.equals(quantumBoots);
  }

  @Override
  public NBTTagCompound serializeNBT() {
    NBTTagCompound tag = new NBTTagCompound();
    NBTUtils.setGenericNBTValue(tag, TAG_TEMPERATURE, this.getTemperature());
    NBTUtils.setGenericNBTValue(tag, TAG_TARGET, this.getTarget());
    NBTUtils.setGenericNBTValue(tag, TAG_POTENCY, this.getPotency());
    return tag;
  }

  public String toString() {
    StringBuilder str = new StringBuilder();
    for (ModifierBase modifier : modifiers) {
      str.append(modifier.getName()).append(" -> ")
        .append(modifier.getChange()).append(" @ ")
        .append(modifier.getPotency()).append("\n");
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

  public float getTemperatureChange() {
    float target = this.getTarget();
    float speed = this.getPotency() * ConfigCore.MISC.AMBIENTAL.temperatureMultiplier;
    float change = Math.min(CHANGE_CAP, Math.max(-CHANGE_CAP, target - temperature));
    float newTemp = temperature + change;

    if ((temperature < AVERAGE && newTemp > temperature) || (temperature > AVERAGE
                                                             && newTemp < temperature)) {
      speed *= GOOD_MULTIPLIER * ConfigCore.MISC.AMBIENTAL.positiveModifier;
    } else {
      speed *= BAD_MULTIPLIER * ConfigCore.MISC.AMBIENTAL.negativeModifier;
    }
    return (change * speed);
  }

  public void clearModifiers() {
    this.modifiers = new AmbientalModifierStorage();
  }

  public void evaluateModifiers() {
    this.clearModifiers();
    ModifierItem.computeModifiers(player, modifiers);
    ModifierEnvironmental.computeModifiers(player, modifiers);
    ModifierBlock.computeModifiers(player, modifiers);
    ModifierTile.computeModifiers(player, modifiers);
    ModifierEquipment.computeModifiers(player, modifiers);
    this.modifiers.keepOnlyNEach(ConfigCore.MISC.AMBIENTAL.modifierCap);

    this.target = modifiers.getTargetTemperature();
    this.potency = modifiers.getTotalPotency();
  }

  @Override
  public void update() {
    if (player.world.isRemote) {return;}
    if (hasNanoOrQuarkArmorProtection(player)) {
      this.setTemperature(NANO_QUARK_ARMOR_TEMP);
    } else {
      this.setTemperature(this.getTemperature() + this.getTemperatureChange() / ConfigCore.MISC.AMBIENTAL.tickInterval);
    }
    if (tick <= ConfigCore.MISC.AMBIENTAL.tickInterval) {
      tick++;
      return;
    } else {
      tick = 0;
      if (damageTick > 40) {
        damageTick = 0;
        if (ConfigCore.MISC.AMBIENTAL.takeDamage) {
          if (this.getTemperature() > BURN_THRESHOLD) {
            player.attackEntityFrom(DamageSources.HYPERTHERMIA, 4f);
          } else if (this.getTemperature() < FREEZE_THRESHOLD) {
            player.attackEntityFrom(DamageSources.HYPOTHERMIA, 4f);
          }
        }
        if (ConfigCore.MISC.AMBIENTAL.loseHungerThirst) {
          if (player.getFoodStats() instanceof IFoodStatsTFC stats) {
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
    this.updateAndSync();


  }

  @Override
  public void setTemperature(float newTemp) {
    this.isRising = !(newTemp < this.getTemperature());
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
  public void deserializeNBT(NBTTagCompound tag) {
    if (tag.hasKey(TAG_TEMPERATURE)) {
      this.setTemperature(tag.getFloat(TAG_TEMPERATURE));
      this.target = tag.getFloat(TAG_TARGET);
      this.potency = tag.getFloat(TAG_POTENCY);

    } else {
      this.setTemperature(23.4f);
    }
  }

  public void updateAndSync() {
    EntityPlayer player = getPlayer();
    if (player instanceof EntityPlayerMP entityPlayerMP) {
      ModuleCore.NETWORK.sendTo(new SCPacketAmbiental(serializeNBT()), entityPlayerMP);
    }
  }

  @Override
  public boolean hasCapability(@NotNull Capability<?> capability, EnumFacing facing) {
    return capability == CapabilityAmbiental.CAPABILITY;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T getCapability(@NotNull Capability<T> capability, EnumFacing facing) {
    return hasCapability(capability, facing) ? (T) (this) : null;
  }
}
