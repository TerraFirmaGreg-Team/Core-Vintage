package su.terrafirmagreg.api.registry.provider;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// Configure automatic model registration
public interface IModelProvider {

    @SideOnly(Side.CLIENT)
    void onModelRegister();
}
