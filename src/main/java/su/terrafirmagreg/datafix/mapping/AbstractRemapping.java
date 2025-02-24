package su.terrafirmagreg.datafix.mapping;

import su.terrafirmagreg.api.data.enums.Mods.ModIDs;
import su.terrafirmagreg.modules.animal.init.BlocksAnimal;
import su.terrafirmagreg.modules.animal.init.EntitiesAnimal;
import su.terrafirmagreg.modules.animal.init.ItemsAnimal;
import su.terrafirmagreg.modules.animal.init.SoundsAnimal;
import su.terrafirmagreg.modules.core.init.BlocksCore;
import su.terrafirmagreg.modules.core.init.EffectsCore;
import su.terrafirmagreg.modules.core.init.EntitiesCore;
import su.terrafirmagreg.modules.core.init.ItemsCore;
import su.terrafirmagreg.modules.device.init.BlocksDevice;
import su.terrafirmagreg.modules.device.init.ItemsDevice;
import su.terrafirmagreg.modules.device.init.SoundsDevice;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Metal.ItemType;
import net.dries007.tfc.objects.Gem;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.items.ItemGem;
import net.dries007.tfc.objects.items.ItemPowder;
import net.dries007.tfc.objects.items.metal.ItemMetal;

import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public abstract class AbstractRemapping {

  protected static final Map<String, Supplier<? extends Block>> BLOCK_MAP = new Object2ObjectOpenHashMap<>() {{
    put("debug", BlocksCore.DEBUG);
    put("puddle", BlocksCore.PUDDLE);
    put("fire_bricks", BlocksCore.FIRE_BRICKS);
    put("thatch", BlocksCore.THATCH);

    put("alloy_calculator", BlocksDevice.ALLOY_CALCULATOR);
    put("bear_trap", BlocksDevice.BEAR_TRAP);
    put("snare", BlocksDevice.SNARE);
    put("bellows", BlocksDevice.BELLOWS);
    put("blast_furnace", BlocksDevice.BLAST_FURNACE);
    put("molten", BlocksDevice.MOLTEN);
    put("bloomery", BlocksDevice.BLOOMERY);
    put("bloom", BlocksDevice.BLOOM);
    put("charcoal_forge", BlocksDevice.CHARCOAL_FORGE);
    put("crucible", BlocksDevice.CRUCIBLE);
    put("charcoal_pile", BlocksDevice.CHARCOAL_PILE);
    put("firepit", BlocksDevice.FIRE_PIT);
    put("pit_kiln", BlocksDevice.PIT_KILN);
    put("cellar_wall", BlocksDevice.CELLAR_WALL);
    put("cellar_door", BlocksDevice.CELLAR_DOOR);
    put("cellar_shelf", BlocksDevice.CELLAR_SHELF);
    put("smeltery_firebox", BlocksDevice.SMELTERY_FIREBOX);
    put("smeltery_cauldron", BlocksDevice.SMELTERY_CAULDRON);
    put("latex_extractor", BlocksDevice.LATEX_EXTRACTOR);
    put("infected_air", BlocksDevice.INFECTED_AIR);
    put("freeze_dryer", BlocksDevice.FREEZE_DRYER);
    put("ice_bunker", BlocksDevice.ICE_BUNKER);

    put("nest_box", BlocksAnimal.NEST_BOX);

  }};

  protected static final Map<String, Supplier<? extends Item>> ITEM_MAP = new Object2ObjectOpenHashMap<>() {{
    put("wand", ItemsCore.DEBUG_WAND);
    put("wood_ash", ItemsCore.WOOD_ASH);
    put("straw", ItemsCore.STRAW);
    put("glass_shard", ItemsCore.GLASS_SHARD);
    put("ice_shard", ItemsCore.ICE_SHARD);
    put("packed_ice_shard", ItemsCore.ICE_SHARD);
    put("sea_ice_shard", ItemsCore.ICE_SHARD);

    put("firestarter", ItemsDevice.FIRESTARTER);
    put("leather_flask", ItemsDevice.LEATHER_FLASK);
    put("leather_side", ItemsDevice.LEATHER_FLASK_UNFINISHED);
    put("broken_leather_flask", ItemsDevice.LEATHER_FLASK_BROKEN);
    put("iron_flask", ItemsDevice.METAL_FLASK);
    put("unfinished_iron_flask", ItemsDevice.METAL_FLASK_UNFINISHED);
    put("broken_iron_flask", ItemsDevice.METAL_FLASK_BROKEN);

    put("bladder", ItemsAnimal.BLADDER);
    put("halter", ItemsAnimal.HALTER);
    put("product/silk_cloth", ItemsAnimal.SILK_CLOTH);
    put("product/wool_cloth", ItemsAnimal.WOOL_CLOTH);
    put("product/wool_yarn", ItemsAnimal.WOOL_YARN);
    put("product/wool", ItemsAnimal.WOOL);

    put("gem/amber", () -> ItemGem.get(Gem.AMBER));
    put("powder/pearl", () -> ItemPowder.get(Powder.PEARL));
    put("powder/black_pearl", () -> ItemPowder.get(Powder.BLACK_PEARL));

    put("blue_steel_ice_saw_head", () -> ItemMetal.get(Metal.BLUE_STEEL, ItemType.ICE_SAW_HEAD));
    put("black_steel_ice_saw_head", () -> ItemMetal.get(Metal.BLACK_STEEL, ItemType.ICE_SAW_HEAD));
    put("red_steel_ice_saw_head", () -> ItemMetal.get(Metal.RED_STEEL, ItemType.ICE_SAW_HEAD));
    put("steel_ice_saw_head", () -> ItemMetal.get(Metal.STEEL, ItemType.ICE_SAW_HEAD));
    put("bismuth_bronze_ice_saw_head", () -> ItemMetal.get(Metal.BISMUTH_BRONZE, ItemType.ICE_SAW_HEAD));
    put("wrought_iron_ice_saw_head", () -> ItemMetal.get(Metal.WROUGHT_IRON, ItemType.ICE_SAW_HEAD));
    put("black_bronze_ice_saw_head", () -> ItemMetal.get(Metal.BLACK_BRONZE, ItemType.ICE_SAW_HEAD));
    put("bronze_ice_saw_head", () -> ItemMetal.get(Metal.BRONZE, ItemType.ICE_SAW_HEAD));

    put("blue_steel_ice_saw", () -> ItemMetal.get(Metal.BLUE_STEEL, ItemType.ICE_SAW));
    put("black_steel_ice_saw", () -> ItemMetal.get(Metal.BLACK_STEEL, ItemType.ICE_SAW));
    put("red_steel_ice_saw", () -> ItemMetal.get(Metal.RED_STEEL, ItemType.ICE_SAW));
    put("steel_ice_saw", () -> ItemMetal.get(Metal.STEEL, ItemType.ICE_SAW));
    put("bismuth_bronze_ice_saw", () -> ItemMetal.get(Metal.BISMUTH_BRONZE, ItemType.ICE_SAW));
    put("wrought_iron_ice_saw", () -> ItemMetal.get(Metal.WROUGHT_IRON, ItemType.ICE_SAW));
    put("black_bronze_ice_saw", () -> ItemMetal.get(Metal.BLACK_BRONZE, ItemType.ICE_SAW));
    put("bronze_ice_saw", () -> ItemMetal.get(Metal.BRONZE, ItemType.ICE_SAW));

    put("blue_steel_mallet_head", () -> ItemMetal.get(Metal.BLUE_STEEL, ItemType.MALLET_HEAD));
    put("black_steel_mallet_head", () -> ItemMetal.get(Metal.BLACK_STEEL, ItemType.MALLET_HEAD));
    put("red_steel_mallet_head", () -> ItemMetal.get(Metal.RED_STEEL, ItemType.MALLET_HEAD));
    put("steel_mallet_head", () -> ItemMetal.get(Metal.STEEL, ItemType.MALLET_HEAD));
    put("bismuth_bronze_mallet_head", () -> ItemMetal.get(Metal.BISMUTH_BRONZE, ItemType.MALLET_HEAD));
    put("wrought_iron_mallet_head", () -> ItemMetal.get(Metal.WROUGHT_IRON, ItemType.MALLET_HEAD));
    put("black_bronze_mallet_head", () -> ItemMetal.get(Metal.BLACK_BRONZE, ItemType.MALLET_HEAD));
    put("bronze_mallet_head", () -> ItemMetal.get(Metal.BRONZE, ItemType.MALLET_HEAD));

    put("blue_steel_mallet", () -> ItemMetal.get(Metal.BLUE_STEEL, ItemType.MALLET));
    put("black_steel_mallet", () -> ItemMetal.get(Metal.BLACK_STEEL, ItemType.MALLET));
    put("red_steel_mallet", () -> ItemMetal.get(Metal.RED_STEEL, ItemType.MALLET));
    put("steel_mallet", () -> ItemMetal.get(Metal.STEEL, ItemType.MALLET));
    put("bismuth_bronze_mallet", () -> ItemMetal.get(Metal.BISMUTH_BRONZE, ItemType.MALLET));
    put("wrought_iron_mallet", () -> ItemMetal.get(Metal.WROUGHT_IRON, ItemType.MALLET));
    put("black_bronze_mallet", () -> ItemMetal.get(Metal.BLACK_BRONZE, ItemType.MALLET));
    put("bronze_mallet", () -> ItemMetal.get(Metal.BRONZE, ItemType.MALLET));
  }};
  protected static final Map<String, Supplier<? extends EntityEntry>> ENTITY_MAP = new Object2ObjectOpenHashMap<>() {{
    put("sitblock", EntitiesCore.SIT_BLOCK);

    put("sheeptfc", EntitiesAnimal.SHEEP);
    put("cowtfc", EntitiesAnimal.COW);
    put("grizzlybeartfc", EntitiesAnimal.GRIZZLYBEAR);
    put("chickentfc", EntitiesAnimal.CHICKEN);
    put("pheasanttfc", EntitiesAnimal.PHEASANT);
    put("deertfc", EntitiesAnimal.DEER);
    put("pigtfc", EntitiesAnimal.PIG);
    put("wolftfc", EntitiesAnimal.WOLF);
    put("rabbittfc", EntitiesAnimal.RABBIT);
    put("horsetfc", EntitiesAnimal.HORSE);
    put("donkeytfc", EntitiesAnimal.DONKEY);
    put("muletfc", EntitiesAnimal.MULE);
    put("polarbeartfc", EntitiesAnimal.POLARBEAR);
    put("parrottfc", EntitiesAnimal.PARROT);
    put("llamatfc", EntitiesAnimal.LLAMA);
    put("ocelottfc", EntitiesAnimal.OCELOT);
    put("panthertfc", EntitiesAnimal.PANTHER);
    put("ducktfc", EntitiesAnimal.DUCK);
    put("alpacatfc", EntitiesAnimal.ALPACA);
    put("goattfc", EntitiesAnimal.GOAT);
    put("sabertoothtfc", EntitiesAnimal.SABERTOOTH);
    put("cameltfc", EntitiesAnimal.CAMEL);
    put("liontfc", EntitiesAnimal.LION);
    put("hyenatfc", EntitiesAnimal.HYENA);
    put("direwolftfc", EntitiesAnimal.DIREWOLF);
    put("haretfc", EntitiesAnimal.HARE);
    put("boartfc", EntitiesAnimal.BOAR);
    put("zebutfc", EntitiesAnimal.ZEBU);
    put("gazelletfc", EntitiesAnimal.GAZELLE);
    put("wildebeesttfc", EntitiesAnimal.WILDEBEEST);
    put("quailtfc", EntitiesAnimal.QUAIL);
    put("grousetfc", EntitiesAnimal.GROUSE);
    put("mongoosetfc", EntitiesAnimal.MONGOOSE);
    put("turkeytfc", EntitiesAnimal.TURKEY);
    put("jackaltfc", EntitiesAnimal.JACKAL);
    put("muskoxtfc", EntitiesAnimal.MUSKOX);
    put("yaktfc", EntitiesAnimal.YAK);
    put("blackbeartfc", EntitiesAnimal.BLACKBEAR);
    put("cougartfc", EntitiesAnimal.COUGAR);
    put("coyotetfc", EntitiesAnimal.COYOTE);

  }};
  protected static final Map<String, Supplier<? extends Potion>> EFFECT_MAP = new Object2ObjectOpenHashMap<>() {{
    put("cool", EffectsCore.HYPOTHERMIA);
    put("warm", EffectsCore.HYPERTHERMIA);
    put("overburdened", EffectsCore.OVERBURDENED);
    put("thirst", EffectsCore.THIRST);
    put("parasites", EffectsCore.PARASITES);
    put("swarm", EffectsCore.SWARM);

  }};
  protected static final Map<String, Supplier<? extends PotionType>> POTION_MAP = new Object2ObjectOpenHashMap<>() {{

  }};
  protected static final Map<String, Supplier<? extends SoundEvent>> SOUND_MAP = new Object2ObjectOpenHashMap<>() {{

    put("animal.alpaca.cry", SoundsAnimal.ANIMAL_ALPACA_CRY);
    put("animal.alpaca.death", SoundsAnimal.ANIMAL_ALPACA_DEATH);
    put("animal.alpaca.hurt", SoundsAnimal.ANIMAL_ALPACA_HURT);
    put("animal.alpaca.say", SoundsAnimal.ANIMAL_ALPACA_SAY);
    put("animal.bear.cry", SoundsAnimal.ANIMAL_BEAR_CRY);
    put("animal.bear.death", SoundsAnimal.ANIMAL_BEAR_DEATH);
    put("animal.bear.hurt", SoundsAnimal.ANIMAL_BEAR_HURT);
    put("animal.bear.say", SoundsAnimal.ANIMAL_BEAR_SAY);
    put("animal.boar.death", SoundsAnimal.ANIMAL_BOAR_DEATH);
    put("animal.boar.hurt", SoundsAnimal.ANIMAL_BOAR_HURT);
    put("animal.boar.say", SoundsAnimal.ANIMAL_BOAR_SAY);
    put("animal.camel.cry", SoundsAnimal.ANIMAL_CAMEL_CRY);
    put("animal.camel.death", SoundsAnimal.ANIMAL_CAMEL_DEATH);
    put("animal.camel.hurt", SoundsAnimal.ANIMAL_CAMEL_HURT);
    put("animal.camel.say", SoundsAnimal.ANIMAL_CAMEL_SAY);
    put("animal.cougar.cry", SoundsAnimal.ANIMAL_COUGAR_CRY);
    put("animal.cougar.death", SoundsAnimal.ANIMAL_COUGAR_DEATH);
    put("animal.cougar.hurt", SoundsAnimal.ANIMAL_COUGAR_HURT);
    put("animal.cougar.say", SoundsAnimal.ANIMAL_COUGAR_SAY);
    put("animal.coyote.cry", SoundsAnimal.ANIMAL_COYOTE_CRY);
    put("animal.coyote.death", SoundsAnimal.ANIMAL_COYOTE_DEATH);
    put("animal.coyote.hurt", SoundsAnimal.ANIMAL_COYOTE_HURT);
    put("animal.coyote.say", SoundsAnimal.ANIMAL_COYOTE_SAY);
    put("animal.deer.cry", SoundsAnimal.ANIMAL_DEER_CRY);
    put("animal.deer.death", SoundsAnimal.ANIMAL_DEER_DEATH);
    put("animal.deer.hurt", SoundsAnimal.ANIMAL_DEER_HURT);
    put("animal.deer.say", SoundsAnimal.ANIMAL_DEER_SAY);
    put("animal.direwolf.cry", SoundsAnimal.ANIMAL_DIREWOLF_CRY);
    put("animal.direwolf.death", SoundsAnimal.ANIMAL_DIREWOLF_DEATH);
    put("animal.direwolf.hurt", SoundsAnimal.ANIMAL_DIREWOLF_HURT);
    put("animal.direwolf.say", SoundsAnimal.ANIMAL_DIREWOLF_SAY);
    put("animal.duck.cry", SoundsAnimal.ANIMAL_DUCK_CRY);
    put("animal.duck.death", SoundsAnimal.ANIMAL_DUCK_DEATH);
    put("animal.duck.hurt", SoundsAnimal.ANIMAL_DUCK_HURT);
    put("animal.duck.say", SoundsAnimal.ANIMAL_DUCK_SAY);
    put("animal.feline.step", SoundsAnimal.ANIMAL_FELINE_STEP);
    put("animal.gazelle.death", SoundsAnimal.ANIMAL_GAZELLE_DEATH);
    put("animal.gazelle.hurt", SoundsAnimal.ANIMAL_GAZELLE_HURT);
    put("animal.gazelle.say", SoundsAnimal.ANIMAL_GAZELLE_SAY);
    put("animal.goat.cry", SoundsAnimal.ANIMAL_GOAT_CRY);
    put("animal.goat.death", SoundsAnimal.ANIMAL_GOAT_DEATH);
    put("animal.goat.hurt", SoundsAnimal.ANIMAL_GOAT_HURT);
    put("animal.goat.say", SoundsAnimal.ANIMAL_GOAT_SAY);
    put("animal.grouse.death", SoundsAnimal.ANIMAL_GROUSE_DEATH);
    put("animal.grouse.hurt", SoundsAnimal.ANIMAL_GROUSE_HURT);
    put("animal.grouse.say", SoundsAnimal.ANIMAL_GROUSE_SAY);
    put("animal.hyena.cry", SoundsAnimal.ANIMAL_HYENA_CRY);
    put("animal.hyena.death", SoundsAnimal.ANIMAL_HYENA_DEATH);
    put("animal.hyena.hurt", SoundsAnimal.ANIMAL_HYENA_HURT);
    put("animal.hyena.say", SoundsAnimal.ANIMAL_HYENA_SAY);
    put("animal.zebu.death", SoundsAnimal.ANIMAL_ZEBU_DEATH);
    put("animal.zebu.hurt", SoundsAnimal.ANIMAL_ZEBU_HURT);
    put("animal.zebu.say", SoundsAnimal.ANIMAL_ZEBU_SAY);
    put("animal.jackal.cry", SoundsAnimal.ANIMAL_JACKAL_CRY);
    put("animal.jackal.death", SoundsAnimal.ANIMAL_JACKAL_DEATH);
    put("animal.jackal.hurt", SoundsAnimal.ANIMAL_JACKAL_HURT);
    put("animal.jackal.say", SoundsAnimal.ANIMAL_JACKAL_SAY);
    put("animal.lion.cry", SoundsAnimal.ANIMAL_LION_CRY);
    put("animal.lion.death", SoundsAnimal.ANIMAL_LION_DEATH);
    put("animal.lion.hurt", SoundsAnimal.ANIMAL_LION_HURT);
    put("animal.lion.say", SoundsAnimal.ANIMAL_LION_SAY);
    put("animal.mongoose.death", SoundsAnimal.ANIMAL_MONGOOSE_DEATH);
    put("animal.mongoose.hurt", SoundsAnimal.ANIMAL_MONGOOSE_HURT);
    put("animal.mongoose.say", SoundsAnimal.ANIMAL_MONGOOSE_SAY);
    put("animal.muskox.death", SoundsAnimal.ANIMAL_MUSKOX_DEATH);
    put("animal.muskox.hurt", SoundsAnimal.ANIMAL_MUSKOX_HURT);
    put("animal.muskox.say", SoundsAnimal.ANIMAL_MUSKOX_SAY);
    put("animal.panther.cry", SoundsAnimal.ANIMAL_PANTHER_CRY);
    put("animal.panther.death", SoundsAnimal.ANIMAL_PANTHER_DEATH);
    put("animal.panther.hurt", SoundsAnimal.ANIMAL_PANTHER_HURT);
    put("animal.panther.say", SoundsAnimal.ANIMAL_PANTHER_SAY);
    put("animal.pheasant.death", SoundsAnimal.ANIMAL_PHEASANT_DEATH);
    put("animal.pheasant.hurt", SoundsAnimal.ANIMAL_PHEASANT_HURT);
    put("animal.pheasant.say", SoundsAnimal.ANIMAL_PHEASANT_SAY);
    put("animal.quail.death", SoundsAnimal.ANIMAL_QUAIL_DEATH);
    put("animal.quail.hurt", SoundsAnimal.ANIMAL_QUAIL_HURT);
    put("animal.quail.say", SoundsAnimal.ANIMAL_QUAIL_SAY);
    put("animal.rooster.cry", SoundsAnimal.ANIMAL_ROOSTER_CRY);
    put("animal.sabertooth.cry", SoundsAnimal.ANIMAL_SABERTOOTH_CRY);
    put("animal.sabertooth.death", SoundsAnimal.ANIMAL_SABERTOOTH_DEATH);
    put("animal.sabertooth.hurt", SoundsAnimal.ANIMAL_SABERTOOTH_HURT);
    put("animal.sabertooth.say", SoundsAnimal.ANIMAL_SABERTOOTH_SAY);
    put("animal.turkey.death", SoundsAnimal.ANIMAL_TURKEY_DEATH);
    put("animal.turkey.hurt", SoundsAnimal.ANIMAL_TURKEY_HURT);
    put("animal.turkey.say", SoundsAnimal.ANIMAL_TURKEY_SAY);
    put("animal.wildebeest.death", SoundsAnimal.ANIMAL_WILDEBEEST_DEATH);
    put("animal.wildebeest.hurt", SoundsAnimal.ANIMAL_WILDEBEEST_HURT);
    put("animal.wildebeest.say", SoundsAnimal.ANIMAL_WILDEBEEST_SAY);
    put("animal.yak.say", SoundsAnimal.ANIMAL_YAK_SAY);
    put("animal.yak.death", SoundsAnimal.ANIMAL_YAK_DEATH);
    put("animal.yak.hurt", SoundsAnimal.ANIMAL_YAK_HURT);

    put("item.flaskbreak", SoundsDevice.FLASK_BREAK);
    put("item.firestarter", SoundsDevice.FIRE_STARTER);
    put("bellows.blow.air", SoundsDevice.BELLOWS_BLOW_AIR);
  }};
  protected static final Set<String> MOD_ID_SET = new ObjectOpenHashSet<>() {{
    add(ModIDs.TFCF);
    add(ModIDs.CAFFEINEADDON);
    add(ModIDs.CELLARS);
  }};


}
