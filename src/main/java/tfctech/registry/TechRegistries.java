package tfctech.registry;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryBuilder;

import tfctech.api.recipes.GlassworkingRecipe;
import tfctech.api.recipes.SmelteryRecipe;
import tfctech.api.recipes.WireDrawingRecipe;

import static su.terrafirmagreg.api.data.enums.Mods.Names.TFCTECH;

@Mod.EventBusSubscriber(modid = TFCTECH)
public final class TechRegistries {

  public static IForgeRegistry<WireDrawingRecipe> WIRE_DRAWING;
  public static IForgeRegistry<SmelteryRecipe> SMELTERY;
  public static IForgeRegistry<GlassworkingRecipe> GLASSWORKING;

  @SubscribeEvent
  public static void onNewRegistryEvent(RegistryEvent.NewRegistry event) {
    WIRE_DRAWING = createRegistry(new ResourceLocation(TFCTECH, "wire_drawing_recipe"), WireDrawingRecipe.class);
    SMELTERY = createRegistry(new ResourceLocation(TFCTECH, "smeltery_recipe"), SmelteryRecipe.class);
    GLASSWORKING = createRegistry(new ResourceLocation(TFCTECH, "glassworking_recipe"), GlassworkingRecipe.class);
  }

  private static <T extends IForgeRegistryEntry<T>> IForgeRegistry<T> createRegistry(ResourceLocation name, Class<T> tClass) {
    return new RegistryBuilder<T>().setName(name).allowModification().setType(tClass).create();
  }
}
