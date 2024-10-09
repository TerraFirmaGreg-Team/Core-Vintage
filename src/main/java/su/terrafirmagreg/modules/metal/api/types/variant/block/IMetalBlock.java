package su.terrafirmagreg.modules.metal.api.types.variant.block;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;
import su.terrafirmagreg.api.registry.provider.IProviderBlockColor;
import su.terrafirmagreg.data.lib.types.variant.block.IVariantBlock;
import su.terrafirmagreg.modules.metal.api.types.type.MetalType;

import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;

/**
 * Интерфейс, представляющий блок металла.
 */
public interface IMetalBlock extends IVariantBlock<MetalBlockVariant, MetalType>, IBlockSettings, IProviderBlockColor {

  @Override
  default IBlockColor getBlockColor() {
    return (s, w, p, i) -> this.getType().getColor();
  }

  @Override
  default IItemColor getItemColor() {
    return (s, i) -> this.getType().getColor();
  }
}
