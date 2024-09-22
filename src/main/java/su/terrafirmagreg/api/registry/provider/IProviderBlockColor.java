package su.terrafirmagreg.api.registry.provider;

import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IProviderBlockColor extends IProviderItemColor {

  @SideOnly(Side.CLIENT)
  IBlockColor getBlockColor();

  @SideOnly(Side.CLIENT)
  default IItemColor getItemColor() {
    return null;
  }
}
