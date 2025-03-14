package net.dries007.tfc.objects.items;

import su.terrafirmagreg.modules.core.capabilities.damage.ICapabilityDamageResistance;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfcthings.main.ConfigTFCThings;

import su.terrafirmagreg.modules.core.capabilities.size.ICapabilitySize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.dries007.tfc.objects.Gem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemCrown extends ItemArmor implements ICapabilitySize, ICapabilityDamageResistance, TFCThingsConfigurableItem {

  private final Gem gem;

  public ItemCrown(int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String metal, Gem gem) {
    super(EnumHelper.addArmorMaterial(
      "crown_" + metal + "_" + gem.name().toLowerCase(), "tfcthings:crown/" + metal + "_" + gem.name().toLowerCase(), 10, new int[]{0, 0, 0,
                                                                                                                                    0}, 50, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F), renderIndexIn, equipmentSlotIn);
    this.gem = gem;
    this.setTranslationKey(metal + "_crown");
    this.setRegistryName("crown/" + metal + "_" + gem.name().toLowerCase());
    this.setNoRepair();
  }

  public ItemCrown(int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String metal) {
    super(EnumHelper.addArmorMaterial(
      "crown_" + metal + "_empty",
      "tfcthings:crown/" + metal + "_empty", 10, new int[]{0, 0, 0, 0}, 50, SoundEvents.ITEM_ARMOR_EQUIP_GOLD, 0.0F), renderIndexIn, equipmentSlotIn);
    this.gem = null;
    this.setTranslationKey(metal + "_crown");
    this.setRegistryName("crown/" + metal + "_empty");
    this.setNoRepair();
  }

  @Nonnull
  @Override
  public Size getSize(@Nonnull ItemStack itemStack) {
    return Size.LARGE;
  }

  @Nonnull
  @Override
  public Weight getWeight(@Nonnull ItemStack itemStack) {
    return Weight.HEAVY;
  }

  @Override
  public boolean canStack(@Nonnull ItemStack stack) {
    return false;
  }

  @Override
  public float getCrushingModifier() {
    return 0;
  }

  @Override
  public float getPiercingModifier() {
    return 0;
  }

  @Override
  public float getSlashingModifier() {
    return 0;
  }

  public boolean isDamageable() {
    return false;
  }

  @SideOnly(Side.CLIENT)
  public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    if (gem == null) {
      tooltip.add(I18n.format("tfcthings.tooltip.crown.gem.empty"));
      tooltip.add(I18n.format("tfcthings.tooltip.crown.missing"));
    } else {
      String gemName = I18n.format(ItemGem.get(gem).getTranslationKey() + ".normal.name");
      tooltip.add(I18n.format("tfcthings.tooltip.crown.gem", gemName));
    }
    super.addInformation(stack, worldIn, tooltip, flagIn);
  }

  @Override
  public boolean isEnabled() {
    return ConfigTFCThings.Items.MASTER_ITEM_LIST.enableCrowns;
  }
}
