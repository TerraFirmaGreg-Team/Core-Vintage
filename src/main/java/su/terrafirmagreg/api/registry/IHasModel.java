package su.terrafirmagreg.api.registry;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import su.terrafirmagreg.api.spi.item.ICustomModel;

// Configure automatic model registration
public interface IHasModel {

    @SideOnly(Side.CLIENT)
    default void onModelRegister() {};
}
