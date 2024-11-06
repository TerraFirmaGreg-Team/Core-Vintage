package dev.quarris.tfcalloycalc;

import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.Logger;

public class ModRef {

    public static final String ID = "tfcalloycalc";
    public static final String NAME = "TFC Alloy Calc";
    public static final String VERSION = "1.0";
    public static final String DEPENDENCIES = "required-after:tfc@[1.7.19,)";
    public static Logger logger;

    public static ResourceLocation res(String res) {
        return new ResourceLocation(ID, res);
    }
}
