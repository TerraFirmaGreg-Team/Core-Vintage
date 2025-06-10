package su.terrafirmagreg.framework.manager.registry.base.item.spi;


import su.terrafirmagreg.api.data.LocalizeKeys;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.registry.base.item.api.IItemEntry;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
public class BaseItemBlock extends ItemBlock implements IItemEntry {

  protected final Settings settings;

  public BaseItemBlock(Block block) {
    super(block);

    this.settings = Settings.of(block);
  }

  @Override
  public String getTranslationKey() {
    return ModUtils.localize(LocalizeKeys.BLOCK, block.getRegistryName());
  }

  @Override
  public @Nullable ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
    if (getSettings().getCapability().isEmpty()) {
      return null;
    }
    return settings$initCapabilities(stack, nbt);
  }

}
