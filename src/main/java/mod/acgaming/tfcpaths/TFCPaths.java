package mod.acgaming.tfcpaths;

import net.minecraftforge.fml.common.Mod;
import su.terrafirmagreg.Tags;

import static su.terrafirmagreg.Constants.MODID_TFCPATHS;

@Mod(modid = MODID_TFCPATHS,
		version = Tags.VERSION,
		name = TFCPaths.NAME,
		dependencies = "required-after:tfc")
public class TFCPaths {
	public static final String NAME = "TFC Paths";
}
