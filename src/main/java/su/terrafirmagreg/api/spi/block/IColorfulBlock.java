package su.terrafirmagreg.api.spi.block;

import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IColorfulBlock {

    @SideOnly(Side.CLIENT)
    IBlockColor getColorHandler();

    @SideOnly(Side.CLIENT)
    default IItemColor getItemColorHandler() {

        return null;
    }
}
