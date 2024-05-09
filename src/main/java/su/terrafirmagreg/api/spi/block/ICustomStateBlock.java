package su.terrafirmagreg.api.spi.block;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ICustomStateBlock {

    @SideOnly(Side.CLIENT)
    void onStateRegister();
}
