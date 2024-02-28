package dev.quarris.tfcalloycalc;

import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Logger;

import static su.terrafirmagreg.api.lib.Constants.MODID_TFCALLOYCALC;

public class ModRef {
	public static final String NAME = "TFC Alloy Calc";
	public static final String DEPENDENCIES = "required-after:tfc";
	public static Logger logger;

	public static ResourceLocation res(String res) {
		return new ResourceLocation(MODID_TFCALLOYCALC, res);
	}
}
