package su.terrafirmagreg.core;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.DumbStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import su.terrafirmagreg.core.modules.ambiental.TFCAmbientalEventHandler;
import su.terrafirmagreg.core.modules.ambiental.TFCAmbientalGuiRenderer;
import su.terrafirmagreg.core.modules.ambiental.capability.TemperatureCapability;
import su.terrafirmagreg.core.modules.ambiental.capability.TemperaturePacket;
import su.terrafirmagreg.core.modules.gregtech.items.TFGModMetaItem;
import su.terrafirmagreg.core.modules.gregtech.items.tools.TFGToolItems;

import static su.terrafirmagreg.Tags.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = MOD_VERSION, dependencies = TerraFirmaGreg.DEPENDENCIES)
public class TerraFirmaGreg {

    public static final String DEPENDENCIES =
            "required:forge@[14.23.5.2847,);" +
                    "required:mixinbooter;" +
                    "required:tfc;" +
                    "required:gregtech;" +
                    "required:firmalife;" +
                    "required:cellars;" +
                    "required:tfctech;";

    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @EventHandler
    public void onConstruct(FMLConstructionEvent event) {
        LOGGER.info("TerraFirmaGreg Core by Exception and Xikaro is working :)");
    }

    @EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        TFGToolItems.init();
        TFGModMetaItem.init();

        // Common Events
        MinecraftForge.EVENT_BUS.register(new TFCAmbientalEventHandler());

        // Client Events
        if (event.getSide() == Side.CLIENT) {
            MinecraftForge.EVENT_BUS.register(new TFCAmbientalGuiRenderer());
        }

        // Capability Registry
        CapabilityManager.INSTANCE.register(TemperatureCapability.class, new DumbStorage<>(), () -> null);

        TerraFirmaCraft.getNetwork().registerMessage(new TemperaturePacket.Handler(), TemperaturePacket.class, 0, Side.CLIENT);
    }

    @EventHandler
    public void onInit(FMLInitializationEvent event) {

    }

    @EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        Recipes.register();
    }
}
