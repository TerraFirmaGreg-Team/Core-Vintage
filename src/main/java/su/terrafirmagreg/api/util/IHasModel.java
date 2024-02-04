package su.terrafirmagreg.api.util;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// Configure automatic model registration
@FunctionalInterface
public interface IHasModel {

	@SideOnly(Side.CLIENT)
	void onModelRegister();
}
