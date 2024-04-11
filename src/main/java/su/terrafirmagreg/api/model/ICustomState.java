package su.terrafirmagreg.api.model;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public interface ICustomState {

    @SideOnly(Side.CLIENT)
    void onStateMapperRegister();
}
