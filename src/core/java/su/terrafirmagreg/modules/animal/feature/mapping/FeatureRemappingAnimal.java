package su.terrafirmagreg.modules.animal.feature.mapping;

import su.terrafirmagreg.api.util.DataFixUtils;
import su.terrafirmagreg.framework.manager.feature.base.BaseFeature;
import su.terrafirmagreg.modules.animal.ModuleAnimal;
import su.terrafirmagreg.modules.animal.init.BlocksAnimal;
import su.terrafirmagreg.modules.animal.init.EntitiesAnimal;
import su.terrafirmagreg.modules.animal.init.ItemsAnimal;
import su.terrafirmagreg.modules.animal.init.SoundsAnimal;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
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
  public static void onSoundRemapping(final RegistryEvent.MissingMappings<SoundEvent> event) {
    DataFixUtils.remap(event, ModuleAnimal.LOGGER.getLogger(), new Object2ObjectOpenHashMap<>() {{
      put("animal.alpaca.cry", () -> SoundsAnimal.ANIMAL_ALPACA_CRY);
      put("animal.alpaca.death", () -> SoundsAnimal.ANIMAL_ALPACA_DEATH);
      put("animal.alpaca.hurt", () -> SoundsAnimal.ANIMAL_ALPACA_HURT);
      put("animal.alpaca.say", () -> SoundsAnimal.ANIMAL_ALPACA_SAY);
      put("animal.bear.cry", () -> SoundsAnimal.ANIMAL_BEAR_CRY);
      put("animal.bear.death", () -> SoundsAnimal.ANIMAL_BEAR_DEATH);
      put("animal.bear.hurt", () -> SoundsAnimal.ANIMAL_BEAR_HURT);
      put("animal.bear.say", () -> SoundsAnimal.ANIMAL_BEAR_SAY);
      put("animal.boar.death", () -> SoundsAnimal.ANIMAL_BOAR_DEATH);
      put("animal.boar.hurt", () -> SoundsAnimal.ANIMAL_BOAR_HURT);
      put("animal.boar.say", () -> SoundsAnimal.ANIMAL_BOAR_SAY);
      put("animal.camel.cry", () -> SoundsAnimal.ANIMAL_CAMEL_CRY);
      put("animal.camel.death", () -> SoundsAnimal.ANIMAL_CAMEL_DEATH);
      put("animal.camel.hurt", () -> SoundsAnimal.ANIMAL_CAMEL_HURT);
      put("animal.camel.say", () -> SoundsAnimal.ANIMAL_CAMEL_SAY);
      put("animal.cougar.cry", () -> SoundsAnimal.ANIMAL_COUGAR_CRY);
      put("animal.cougar.death", () -> SoundsAnimal.ANIMAL_COUGAR_DEATH);
      put("animal.cougar.hurt", () -> SoundsAnimal.ANIMAL_COUGAR_HURT);
      put("animal.cougar.say", () -> SoundsAnimal.ANIMAL_COUGAR_SAY);
      put("animal.coyote.cry", () -> SoundsAnimal.ANIMAL_COYOTE_CRY);
      put("animal.coyote.death", () -> SoundsAnimal.ANIMAL_COYOTE_DEATH);
      put("animal.coyote.hurt", () -> SoundsAnimal.ANIMAL_COYOTE_HURT);
      put("animal.coyote.say", () -> SoundsAnimal.ANIMAL_COYOTE_SAY);
      put("animal.deer.cry", () -> SoundsAnimal.ANIMAL_DEER_CRY);
      put("animal.deer.death", () -> SoundsAnimal.ANIMAL_DEER_DEATH);
      put("animal.deer.hurt", () -> SoundsAnimal.ANIMAL_DEER_HURT);
      put("animal.deer.say", () -> SoundsAnimal.ANIMAL_DEER_SAY);
      put("animal.direwolf.cry", () -> SoundsAnimal.ANIMAL_DIREWOLF_CRY);
      put("animal.direwolf.death", () -> SoundsAnimal.ANIMAL_DIREWOLF_DEATH);
      put("animal.direwolf.hurt", () -> SoundsAnimal.ANIMAL_DIREWOLF_HURT);
      put("animal.direwolf.say", () -> SoundsAnimal.ANIMAL_DIREWOLF_SAY);
      put("animal.duck.cry", () -> SoundsAnimal.ANIMAL_DUCK_CRY);
      put("animal.duck.death", () -> SoundsAnimal.ANIMAL_DUCK_DEATH);
      put("animal.duck.hurt", () -> SoundsAnimal.ANIMAL_DUCK_HURT);
      put("animal.duck.say", () -> SoundsAnimal.ANIMAL_DUCK_SAY);
      put("animal.feline.step", () -> SoundsAnimal.ANIMAL_FELINE_STEP);
      put("animal.gazelle.death", () -> SoundsAnimal.ANIMAL_GAZELLE_DEATH);
      put("animal.gazelle.hurt", () -> SoundsAnimal.ANIMAL_GAZELLE_HURT);
      put("animal.gazelle.say", () -> SoundsAnimal.ANIMAL_GAZELLE_SAY);
      put("animal.goat.cry", () -> SoundsAnimal.ANIMAL_GOAT_CRY);
      put("animal.goat.death", () -> SoundsAnimal.ANIMAL_GOAT_DEATH);
      put("animal.goat.hurt", () -> SoundsAnimal.ANIMAL_GOAT_HURT);
      put("animal.goat.say", () -> SoundsAnimal.ANIMAL_GOAT_SAY);
      put("animal.grouse.death", () -> SoundsAnimal.ANIMAL_GROUSE_DEATH);
      put("animal.grouse.hurt", () -> SoundsAnimal.ANIMAL_GROUSE_HURT);
      put("animal.grouse.say", () -> SoundsAnimal.ANIMAL_GROUSE_SAY);
      put("animal.hyena.cry", () -> SoundsAnimal.ANIMAL_HYENA_CRY);
      put("animal.hyena.death", () -> SoundsAnimal.ANIMAL_HYENA_DEATH);
      put("animal.hyena.hurt", () -> SoundsAnimal.ANIMAL_HYENA_HURT);
      put("animal.hyena.say", () -> SoundsAnimal.ANIMAL_HYENA_SAY);
      put("animal.zebu.death", () -> SoundsAnimal.ANIMAL_ZEBU_DEATH);
      put("animal.zebu.hurt", () -> SoundsAnimal.ANIMAL_ZEBU_HURT);
      put("animal.zebu.say", () -> SoundsAnimal.ANIMAL_ZEBU_SAY);
      put("animal.jackal.cry", () -> SoundsAnimal.ANIMAL_JACKAL_CRY);
      put("animal.jackal.death", () -> SoundsAnimal.ANIMAL_JACKAL_DEATH);
      put("animal.jackal.hurt", () -> SoundsAnimal.ANIMAL_JACKAL_HURT);
      put("animal.jackal.say", () -> SoundsAnimal.ANIMAL_JACKAL_SAY);
      put("animal.lion.cry", () -> SoundsAnimal.ANIMAL_LION_CRY);
      put("animal.lion.death", () -> SoundsAnimal.ANIMAL_LION_DEATH);
      put("animal.lion.hurt", () -> SoundsAnimal.ANIMAL_LION_HURT);
      put("animal.lion.say", () -> SoundsAnimal.ANIMAL_LION_SAY);
      put("animal.mongoose.death", () -> SoundsAnimal.ANIMAL_MONGOOSE_DEATH);
      put("animal.mongoose.hurt", () -> SoundsAnimal.ANIMAL_MONGOOSE_HURT);
      put("animal.mongoose.say", () -> SoundsAnimal.ANIMAL_MONGOOSE_SAY);
      put("animal.muskox.death", () -> SoundsAnimal.ANIMAL_MUSKOX_DEATH);
      put("animal.muskox.hurt", () -> SoundsAnimal.ANIMAL_MUSKOX_HURT);
      put("animal.muskox.say", () -> SoundsAnimal.ANIMAL_MUSKOX_SAY);
      put("animal.panther.cry", () -> SoundsAnimal.ANIMAL_PANTHER_CRY);
      put("animal.panther.death", () -> SoundsAnimal.ANIMAL_PANTHER_DEATH);
      put("animal.panther.hurt", () -> SoundsAnimal.ANIMAL_PANTHER_HURT);
      put("animal.panther.say", () -> SoundsAnimal.ANIMAL_PANTHER_SAY);
      put("animal.pheasant.death", () -> SoundsAnimal.ANIMAL_PHEASANT_DEATH);
      put("animal.pheasant.hurt", () -> SoundsAnimal.ANIMAL_PHEASANT_HURT);
      put("animal.pheasant.say", () -> SoundsAnimal.ANIMAL_PHEASANT_SAY);
      put("animal.quail.death", () -> SoundsAnimal.ANIMAL_QUAIL_DEATH);
      put("animal.quail.hurt", () -> SoundsAnimal.ANIMAL_QUAIL_HURT);
      put("animal.quail.say", () -> SoundsAnimal.ANIMAL_QUAIL_SAY);
      put("animal.rooster.cry", () -> SoundsAnimal.ANIMAL_ROOSTER_CRY);
      put("animal.sabertooth.cry", () -> SoundsAnimal.ANIMAL_SABERTOOTH_CRY);
      put("animal.sabertooth.death", () -> SoundsAnimal.ANIMAL_SABERTOOTH_DEATH);
      put("animal.sabertooth.hurt", () -> SoundsAnimal.ANIMAL_SABERTOOTH_HURT);
      put("animal.sabertooth.say", () -> SoundsAnimal.ANIMAL_SABERTOOTH_SAY);
      put("animal.turkey.death", () -> SoundsAnimal.ANIMAL_TURKEY_DEATH);
      put("animal.turkey.hurt", () -> SoundsAnimal.ANIMAL_TURKEY_HURT);
      put("animal.turkey.say", () -> SoundsAnimal.ANIMAL_TURKEY_SAY);
      put("animal.wildebeest.death", () -> SoundsAnimal.ANIMAL_WILDEBEEST_DEATH);
      put("animal.wildebeest.hurt", () -> SoundsAnimal.ANIMAL_WILDEBEEST_HURT);
      put("animal.wildebeest.say", () -> SoundsAnimal.ANIMAL_WILDEBEEST_SAY);
      put("animal.yak.say", () -> SoundsAnimal.ANIMAL_YAK_SAY);
      put("animal.yak.death", () -> SoundsAnimal.ANIMAL_YAK_DEATH);
      put("animal.yak.hurt", () -> SoundsAnimal.ANIMAL_YAK_HURT);
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
