package su.terrafirmagreg.framework.manager.registry.base.item.spi;


import su.terrafirmagreg.api.library.types.type.IType;
import su.terrafirmagreg.api.util.TranslatorUtils;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlockSlab;
import su.terrafirmagreg.framework.manager.registry.base.item.api.IItemEntry;

import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;

import lombok.Getter;

@Getter
public class BaseItemSlab extends ItemSlab implements IItemEntry {

  protected final Settings settings;

  public BaseItemSlab(BaseBlockSlab blockSlab) {
    super(blockSlab.getHalfSlab(), blockSlab.getHalfSlab(), blockSlab.getDoubleSlab());

    this.settings = Settings.of(blockSlab);
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
}
