package su.terrafirmagreg.api.spi.block.provider;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface IBlockStateProvider {

    @SideOnly(Side.CLIENT)
    void onRegisterState();
}
