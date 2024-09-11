package net.dries007.tfc.objects.items.metal;

import su.terrafirmagreg.modules.core.capabilities.metal.ICapabilityMetal;
import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;

import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;


import net.dries007.tfc.api.capability.forge.ForgeableHeatableHandler;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.ItemArmorTFC;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class ItemMetalArmor extends ItemArmorTFC implements ICapabilityMetal, ICapabilitySize {

  private static final Map<Metal, EnumMap<Metal.ItemType, ItemMetalArmor>> TABLE = new HashMap<>();
  private final Metal metal;
  private final Metal.ItemType type;

  public ItemMetalArmor(Metal metal, Metal.ItemType type) {
    //noinspection ConstantConditions
    super(metal.getArmorMetal(), type.getArmorSlot(), type.getEquipmentSlot());
    this.metal = metal;
    this.type = type;
    if (!TABLE.containsKey(metal)) {
      TABLE.put(metal, new EnumMap<>(Metal.ItemType.class));
    }
    TABLE.get(metal).put(type, this);
  }

  public static ItemMetalArmor get(Metal metal, Metal.ItemType type) {
    return TABLE.get(metal).get(type);
  }

  @Nullable
  @Override
  public Metal getMetal(ItemStack stack) {
    return metal;
  }

  @Override
  public int getSmeltAmount(ItemStack stack) {
    if (!isDamageable() || !stack.isItemDamaged()) {
      return type.getSmeltAmount();
    }
    double d = (stack.getMaxDamage() - stack.getItemDamage()) / (double) stack.getMaxDamage() - .10;
    return d < 0 ? 0 : MathHelper.floor(type.getSmeltAmount() * d);
  }

  @Nullable
  @Override
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    return new ForgeableHeatableHandler(nbt, metal.getSpecificHeat(), metal.getMeltTemp());
  }

  @Override
  @NotNull
  public IRarity getForgeRarity(@NotNull ItemStack stack) {
    switch (metal.getTier()) {
      case TIER_I:
      case TIER_II:
        return EnumRarity.COMMON;
      case TIER_III:
        return EnumRarity.UNCOMMON;
      case TIER_IV:
        return EnumRarity.RARE;
      case TIER_V:
        return EnumRarity.EPIC;
    }
    return super.getForgeRarity(stack);
  }
}
