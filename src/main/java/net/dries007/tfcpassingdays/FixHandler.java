package net.dries007.tfcpassingdays;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.tfcpassingdays.fixes.BlockDaylightDetectorFixed;

import static su.terrafirmagreg.api.data.Reference.MODID_TFCPASSINGDAYS;

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
