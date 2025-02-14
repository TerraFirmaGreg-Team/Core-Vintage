package su.terrafirmagreg.api.base.object.item.spi;


import su.terrafirmagreg.api.base.object.item.api.IItemSettings;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
public class BaseItemBlock extends ItemBlock implements IItemSettings {

  protected final Settings settings;

  public BaseItemBlock(Block block) {
    super(block);

    this.settings = Settings.of(block);
  }

  @Override
  public @Nullable ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
    if (getSettings().getCapability().isEmpty()) {
      return null;
    }
    return def$initCapabilities(stack, nbt);
  }

}
