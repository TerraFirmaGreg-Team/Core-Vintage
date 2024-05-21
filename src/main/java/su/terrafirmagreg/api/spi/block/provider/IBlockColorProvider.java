package su.terrafirmagreg.api.spi.block.provider;

import su.terrafirmagreg.api.spi.item.provider.IItemColorProvider;

import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IBlockColorProvider extends IItemColorProvider {

    @SideOnly(Side.CLIENT)
    IBlockColor getBlockColor();

    @SideOnly(Side.CLIENT)
    default IItemColor getItemColor() {
        return null;
    }
}
