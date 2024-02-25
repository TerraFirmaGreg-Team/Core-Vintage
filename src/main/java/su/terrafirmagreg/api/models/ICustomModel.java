package su.terrafirmagreg.api.models;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// Configure automatic model registration
public interface ICustomModel {

	@SideOnly(Side.CLIENT)
	void onModelRegister();
}
