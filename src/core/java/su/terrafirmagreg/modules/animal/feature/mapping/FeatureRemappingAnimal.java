package su.terrafirmagreg.modules.animal.feature.mapping;

import su.terrafirmagreg.api.util.DataFixUtils;
import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;
import su.terrafirmagreg.modules.animal.ModuleAnimal;
import su.terrafirmagreg.modules.animal.init.BlocksAnimal;
import su.terrafirmagreg.modules.animal.init.EntitiesAnimal;
import su.terrafirmagreg.modules.animal.init.ItemsAnimal;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public class FeatureRemappingAnimal extends BaseFeature {


  @SubscribeEvent
  public static void onBlockRemapping(final RegistryEvent.MissingMappings<Block> event) {
    DataFixUtils.remap(event, ModuleAnimal.LOGGER.getLogger(), new Object2ObjectOpenHashMap<>() {{
      put("nest_box", () -> BlocksAnimal.NEST_BOX);
    }});
  }

  @SubscribeEvent
  public static void onItemRemapping(final RegistryEvent.MissingMappings<Item> event) {
    DataFixUtils.remap(event, ModuleAnimal.LOGGER.getLogger(), new Object2ObjectOpenHashMap<>() {{
      put("bladder", () -> ItemsAnimal.BLADDER);
      put("halter", () -> ItemsAnimal.HALTER);
      put("product/silk_cloth", () -> ItemsAnimal.SILK_CLOTH);
      put("product/wool_cloth", () -> ItemsAnimal.WOOL_CLOTH);
      put("product/wool_yarn", () -> ItemsAnimal.WOOL_YARN);
      put("product/wool", () -> ItemsAnimal.WOOL);

      put("nest_box", () -> BlocksAnimal.NEST_BOX.asItem());
    }});
  }


  @SubscribeEvent
  public static void onEntityRemapping(final RegistryEvent.MissingMappings<EntityEntry> event) {
    DataFixUtils.remap(event, ModuleAnimal.LOGGER.getLogger(), new Object2ObjectOpenHashMap<>() {{
      put("sheeptfc", () -> EntitiesAnimal.SHEEP);
      put("cowtfc", () -> EntitiesAnimal.COW);
      put("grizzlybeartfc", () -> EntitiesAnimal.GRIZZLY_BEAR);
      put("chickentfc", () -> EntitiesAnimal.CHICKEN);
      put("pheasanttfc", () -> EntitiesAnimal.PHEASANT);
      put("deertfc", () -> EntitiesAnimal.DEER);
      put("pigtfc", () -> EntitiesAnimal.PIG);
      put("wolftfc", () -> EntitiesAnimal.WOLF);
      put("rabbittfc", () -> EntitiesAnimal.RABBIT);
      put("horsetfc", () -> EntitiesAnimal.HORSE);
      put("donkeytfc", () -> EntitiesAnimal.DONKEY);
      put("muletfc", () -> EntitiesAnimal.MULE);
      put("polarbeartfc", () -> EntitiesAnimal.POLAR_BEAR);
      put("parrottfc", () -> EntitiesAnimal.PARROT);
      put("llamatfc", () -> EntitiesAnimal.LLAMA);
      put("ocelottfc", () -> EntitiesAnimal.OCELOT);
      put("panthertfc", () -> EntitiesAnimal.PANTHER);
      put("ducktfc", () -> EntitiesAnimal.DUCK);
      put("alpacatfc", () -> EntitiesAnimal.ALPACA);
      put("goattfc", () -> EntitiesAnimal.GOAT);
      put("sabertoothtfc", () -> EntitiesAnimal.SABER_TOOTH);
      put("cameltfc", () -> EntitiesAnimal.CAMEL);
      put("liontfc", () -> EntitiesAnimal.LION);
      put("hyenatfc", () -> EntitiesAnimal.HYENA);
      put("direwolftfc", () -> EntitiesAnimal.DIRE_WOLF);
      put("haretfc", () -> EntitiesAnimal.HARE);
      put("boartfc", () -> EntitiesAnimal.BOAR);
      put("zebutfc", () -> EntitiesAnimal.ZEBU);
      put("gazelletfc", () -> EntitiesAnimal.GAZELLE);
      put("wildebeesttfc", () -> EntitiesAnimal.WILDEBEEST);
      put("quailtfc", () -> EntitiesAnimal.QUAIL);
      put("grousetfc", () -> EntitiesAnimal.GROUSE);
      put("mongoosetfc", () -> EntitiesAnimal.MONGOOSE);
      put("turkeytfc", () -> EntitiesAnimal.TURKEY);
      put("jackaltfc", () -> EntitiesAnimal.JACKAL);
      put("muskoxtfc", () -> EntitiesAnimal.MUSKOX);
      put("yaktfc", () -> EntitiesAnimal.YAK);
      put("blackbeartfc", () -> EntitiesAnimal.BLACK_BEAR);
      put("cougartfc", () -> EntitiesAnimal.COUGAR);
      put("coyotetfc", () -> EntitiesAnimal.COYOTE);
    }});
  }
}
