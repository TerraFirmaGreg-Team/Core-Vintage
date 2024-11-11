package su.terrafirmagreg.modules.wood.api.types.variant.block;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;
import su.terrafirmagreg.api.registry.provider.IProviderBlockColor;
import su.terrafirmagreg.api.library.types.variant.block.IVariantBlock;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;

import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;

/**
 * Интерфейс IWoodBlock представляет деревянный блок.
 */
public interface IWoodBlock extends IVariantBlock<WoodBlockVariant, WoodType>, IBlockSettings, IProviderBlockColor {

  @Override
  default IBlockColor getBlockColor() {
    return (s, w, p, i) -> this.getType().getColor();
  }

  @Override
  default IItemColor getItemColor() {
    return (s, i) -> this.getType().getColor();
  }
}
