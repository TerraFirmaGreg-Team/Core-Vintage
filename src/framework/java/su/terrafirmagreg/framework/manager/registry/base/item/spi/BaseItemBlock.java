package su.terrafirmagreg.framework.manager.registry.base.item.spi;


import su.terrafirmagreg.api.library.types.type.IType;
import su.terrafirmagreg.api.util.TranslatorUtils;
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

  protected final ItemSettings settings;

  public BaseItemBlock(Block block) {
    super(block);

    this.settings = ItemSettings.of(block);
  }


  @Override
  public String getItemStackDisplayName(ItemStack stack) {

    String displayName;

    if (block instanceof IType<?> type) {
      displayName = TranslatorUtils.getDisplayTypeName(block.getLocalizedName(), type.getType());
    } else {
      displayName = super.getItemStackDisplayName(stack);
    }

    return displayName;
  }

  @Override
  public @Nullable ICapabilityProvider initCapabilities(@NotNull ItemStack stack, @Nullable NBTTagCompound nbt) {
    if (getSettings().getCapability().isEmpty()) {
      return null;
    }
    return settings$initCapabilities(stack, nbt);
  }

}
