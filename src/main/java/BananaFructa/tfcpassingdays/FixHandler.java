package BananaFructa.tfcpassingdays;

import BananaFructa.tfcpassingdays.fixes.BlockDaylightDetectorFixed;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static su.terrafirmagreg.Constants.MODID_TFCPASSINGDAYS;

@Mod.EventBusSubscriber(modid = MODID_TFCPASSINGDAYS)
public class FixHandler {

	@SubscribeEvent(priority = EventPriority.LOW)
	public static void registry(RegistryEvent.Register<Block> event) {
		event.getRegistry()
		     .register(new BlockDaylightDetectorFixed(false).setCreativeTab(CreativeTabs.REDSTONE)
		                                                    .setTranslationKey("tile.daylightDetector.name")
		                                                    .setRegistryName("minecraft", "daylight_detector"));
		event.getRegistry()
		     .register(new BlockDaylightDetectorFixed(true).setTranslationKey("tile.daylightDetector.name")
		                                                   .setRegistryName("minecraft", "daylight_detector_inverted"));
	}

}
