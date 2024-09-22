package su.terrafirmagreg.modules.wood.api.types.variant.block;

import su.terrafirmagreg.api.base.block.spi.IBlockSettings;
import su.terrafirmagreg.api.registry.provider.IProviderBlockColor;
import su.terrafirmagreg.data.lib.types.type.IType;
import su.terrafirmagreg.data.lib.types.variant.IVariant;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;

import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;

/**
 * Интерфейс IWoodBlock представляет деревянный блок.
 */
public interface IWoodBlock extends IType<WoodType>, IVariant<WoodBlockVariant>, IBlockSettings, IProviderBlockColor {

  @Override
  default IBlockColor getBlockColor() {
    return (s, w, p, i) -> this.getType().getColor();
  }

  @Override
  default IItemColor getItemColor() {
    return (s, i) -> this.getType().getColor();
  }
}
