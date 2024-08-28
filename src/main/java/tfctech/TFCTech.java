package tfctech;

import su.terrafirmagreg.Tags;
import su.terrafirmagreg.data.lib.LoggingHelper;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;


import tfctech.client.TechGuiHandler;

import lombok.Getter;

import static su.terrafirmagreg.data.Constants.MODID_TFCTECH;

@SuppressWarnings("WeakerAccess")
@Mod(modid = MODID_TFCTECH, name = TFCTech.NAME, version = Tags.MOD_VERSION, dependencies = TFCTech.DEPENDENCIES)
public class TFCTech {

    public static final String NAME = "TFCTech Unofficial";
    public static final String DEPENDENCIES = "after:tfc;after:ic2;after:gregtech";
    private static final boolean signedBuild = true;
    private static final LoggingHelper LOGGER = LoggingHelper.of(MODID_TFCTECH);
    @Getter
    @SuppressWarnings("FieldMayBeFinal")
    @Mod.Instance(MODID_TFCTECH)
    private static TFCTech instance = null;

    public static LoggingHelper getLog() {
        return LOGGER;
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        FMLInterModComms.sendFunctionMessage("theoneprobe", "getTheOneProbe", "tfctech.compat.waila.TOPPlugin");
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new TechGuiHandler());
    }
}
