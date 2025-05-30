package su.terrafirmagreg.api.base.object.item.spi;


import su.terrafirmagreg.api.base.object.item.api.IItemSettings;
import su.terrafirmagreg.api.data.ToolTipKeys;
import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
public abstract class BaseItem extends Item implements IItemSettings {

  protected final Settings settings;

  public BaseItem() {
    this.settings = Settings.of();

  }

  @Override
  public IRarity getForgeRarity(ItemStack stack) {
    return settings.getRarity();
  }


  @Override
  public String getTranslationKey(ItemStack stack) {
    return this.getTranslationKey();
  }

  @Override
  public String getTranslationKey() {
    return ModUtils.localize(ToolTipKeys.ITEM, this.getRegistryName());
  }


  @Override
  public @Nullable ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
    addCapabilities(stack, nbt);

    if (getSettings().getCapability().isEmpty()) {
      return super.initCapabilities(stack, nbt);
    }
    return settings$initCapabilities(stack, nbt);
  }
}
