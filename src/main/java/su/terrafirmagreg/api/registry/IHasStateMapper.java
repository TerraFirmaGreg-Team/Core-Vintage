package su.terrafirmagreg.api.registry;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

// Configure automatic model registration
public interface IHasStateMapper {

	@SideOnly(Side.CLIENT)
	void onStateMapperRegister();
}
