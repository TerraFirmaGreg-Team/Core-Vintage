package net.dries007.tfctech.client;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static net.dries007.tfc.util.Helpers.getNull;
import static su.terrafirmagreg.api.data.enums.Mods.ModIDs.TFCTECH;

@Mod.EventBusSubscriber(modid = TFCTECH)
public final class TechSounds {

  @GameRegistry.ObjectHolder(TFCTECH + ":rubbertapping.groove.fit")
  public static final SoundEvent RUBBER_GROOVE_FIT = getNull();

  @GameRegistry.ObjectHolder(TFCTECH + ":rubbertapping.mount.fit")
  public static final SoundEvent RUBBER_MOUNT_FIT = getNull();

  @GameRegistry.ObjectHolder(TFCTECH + ":rubbertapping.bowl.fit")
  public static final SoundEvent RUBBER_BOWL_FIT = getNull();

  @GameRegistry.ObjectHolder(TFCTECH + ":rubbertapping.bowl.grab")
  public static final SoundEvent RUBBER_BOWL_GRAB = getNull();

  @GameRegistry.ObjectHolder(TFCTECH + ":rubbertapping.trunk.scratch")
  public static final SoundEvent RUBBER_TRUNK_SCRATH = getNull();

  @GameRegistry.ObjectHolder(TFCTECH + ":wiredrawbench.tongs_fall")
  public static final SoundEvent WIREDRAW_TONGS_FALL = getNull();

  @GameRegistry.ObjectHolder(TFCTECH + ":wiredrawbench.drawing")
  public static final SoundEvent WIREDRAW_DRAWING = getNull();

  @GameRegistry.ObjectHolder(TFCTECH + ":fridge.open")
  public static final SoundEvent FRIDGE_OPEN = getNull();

  @GameRegistry.ObjectHolder(TFCTECH + ":fridge.close")
  public static final SoundEvent FRIDGE_CLOSE = getNull();

  @GameRegistry.ObjectHolder(TFCTECH + ":induction_smelter.work")
  public static final SoundEvent INDUCTION_WORK = getNull();

  @SubscribeEvent
  public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
    event.getRegistry().registerAll(
      createSoundEvent("rubbertapping.groove.fit"),
      createSoundEvent("rubbertapping.mount.fit"),
      createSoundEvent("rubbertapping.bowl.fit"),
      createSoundEvent("rubbertapping.bowl.grab"),
      createSoundEvent("rubbertapping.trunk.scratch"),
      createSoundEvent("wiredrawbench.tongs_fall"),
      createSoundEvent("wiredrawbench.drawing"),
      createSoundEvent("fridge.open"),
      createSoundEvent("fridge.close"),
      createSoundEvent("induction_smelter.work")
    );
  }

  private static SoundEvent createSoundEvent(String name) {
    final ResourceLocation soundID = new ResourceLocation(TFCTECH, name);
    return new SoundEvent(soundID).setRegistryName(soundID);
  }
}
