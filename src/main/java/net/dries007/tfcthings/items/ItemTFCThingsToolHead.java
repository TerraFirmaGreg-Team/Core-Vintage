package net.dries007.tfcthings.items;

import su.terrafirmagreg.modules.core.capabilities.forge.ForgeableHeatableHandler;
import su.terrafirmagreg.modules.core.capabilities.metal.ICapabilityMetal;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.ItemTFC;
import net.dries007.tfcthings.init.TFCThingsItems;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class ItemTFCThingsToolHead extends ItemTFC implements ICapabilityMetal, TFCThingsConfigurableItem {

  private final Metal metal;
  private final int smeltAmount;
  private final boolean isEnabled;

  public ItemTFCThingsToolHead(Metal metal, int smeltAmount, boolean enabled, String... name) {
    this.metal = metal;
    this.smeltAmount = smeltAmount;
    this.isEnabled = enabled;
    String registryName = "";
    String translationKey = "";
    if (TFCThingsItems.TOOLS_HEADS_BY_METAL.containsKey(name[0])) {
      TFCThingsItems.TOOLS_HEADS_BY_METAL.get(name[0]).put(metal, this);
    } else {
      Map<Metal, Item> toolMap = new HashMap<>();
      toolMap.put(metal, this);
      TFCThingsItems.TOOLS_HEADS_BY_METAL.put(name[0], toolMap);
    }
    for (int i = 0; i < name.length; i++) {
      registryName += name[i];
      translationKey += name[i];
      if (i + 1 < name.length) {
        registryName += "/";
        translationKey += "_";
      }
    }
    this.setRegistryName(registryName);
    this.setTranslationKey(translationKey);
    setCreativeTab(CreativeTabsTFC.CT_METAL);
  }


  @Nullable
  @Override
  public Metal getMetal(ItemStack itemStack) {
    return metal;
  }

  @Override
  public int getSmeltAmount(ItemStack itemStack) {
    return smeltAmount;
  }

  @Override
  public boolean canMelt(ItemStack stack) {
    return true;
  }

  @Nonnull
  @Override
  public Size getSize(@Nonnull ItemStack itemStack) {
    return Size.SMALL;
  }

  @Nonnull
  @Override
  public Weight getWeight(@Nonnull ItemStack itemStack) {
    return Weight.LIGHT;
  }

  @Nullable
  public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
    return new ForgeableHeatableHandler(nbt, metal.getSpecificHeat(), metal.getMeltTemp());
  }

  @Override
  public boolean isEnabled() {
    return isEnabled;
  }
}
