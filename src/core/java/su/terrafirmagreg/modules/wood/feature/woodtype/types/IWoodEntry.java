package su.terrafirmagreg.modules.wood.feature.woodtype.types;

import su.terrafirmagreg.api.library.types.type.IType;
import su.terrafirmagreg.framework.manager.content.provider.IProviderBlockColor;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodType;

import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;

public interface IWoodEntry extends IType<WoodType>, IProviderBlockColor {

  @Override
  default IBlockColor getBlockColor() {
    return (s, w, p, i) -> this.getType().getColor();
  }

  @Override
  default IItemColor getItemColor() {
    return (s, i) -> this.getType().getColor();
  }

}
