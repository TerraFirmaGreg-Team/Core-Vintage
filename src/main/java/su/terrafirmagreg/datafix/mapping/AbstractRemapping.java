package su.terrafirmagreg.datafix.mapping;

import su.terrafirmagreg.api.data.enums.Mods.Names;
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

public abstract class AbstractRemapping {

  protected static final Map<String, Block> BLOCK_MAP = new Object2ObjectOpenHashMap<>() {{
    put("debug", BlocksCore.DEBUG.get());
    put("puddle", BlocksCore.PUDDLE.get());
    put("fire_bricks", BlocksCore.FIRE_BRICKS.get());

    put("alloy_calculator", BlocksDevice.ALLOY_CALCULATOR.get());
    put("bear_trap", BlocksDevice.BEAR_TRAP.get());
    put("snare", BlocksDevice.SNARE.get());
    put("bellows", BlocksDevice.BELLOWS.get());
    put("blast_furnace", BlocksDevice.BLAST_FURNACE.get());
    put("molten", BlocksDevice.MOLTEN.get());
    put("bloomery", BlocksDevice.BLOOMERY.get());

    put("nest_box", BlocksAnimal.NEST_BOX.get());

  }};

  protected static final Map<String, Item> ITEM_MAP = new Object2ObjectOpenHashMap<>() {{
    put("wand", ItemsCore.WAND.get());

    put("bladder", ItemsAnimal.BLADDER.get());
    put("halter", ItemsAnimal.HALTER.get());
    put("product/silk_cloth", ItemsAnimal.SILK_CLOTH.get());
    put("product/wool_cloth", ItemsAnimal.WOOL_CLOTH.get());
    put("product/wool_yarn", ItemsAnimal.WOOL_YARN.get());
    put("product/wool", ItemsAnimal.WOOL.get());

    put("firestarter", ItemsDevice.FIRESTARTER.get());
    put("leather_flask", ItemsDevice.LEATHER_FLASK.get());
    put("leather_side", ItemsDevice.LEATHER_FLASK_UNFINISHED.get());
    put("broken_leather_flask", ItemsDevice.LEATHER_FLASK_BROKEN.get());
    put("iron_flask", ItemsDevice.METAL_FLASK.get());
    put("unfinished_iron_flask", ItemsDevice.METAL_FLASK_UNFINISHED.get());
    put("broken_iron_flask", ItemsDevice.METAL_FLASK_BROKEN.get());

    put("gem/amber", ItemGem.get(Gem.AMBER));
    put("powder/pearl", ItemPowder.get(Powder.PEARL));
    put("powder/black_pearl", ItemPowder.get(Powder.BLACK_PEARL));

    put("blue_steel_ice_saw_head", ItemMetal.get(Metal.BLUE_STEEL, ItemType.ICE_SAW_HEAD));
    put("black_steel_ice_saw_head", ItemMetal.get(Metal.BLACK_STEEL, ItemType.ICE_SAW_HEAD));
    put("red_steel_ice_saw_head", ItemMetal.get(Metal.RED_STEEL, ItemType.ICE_SAW_HEAD));
    put("steel_ice_saw_head", ItemMetal.get(Metal.STEEL, ItemType.ICE_SAW_HEAD));
    put("bismuth_bronze_ice_saw_head", ItemMetal.get(Metal.BISMUTH_BRONZE, ItemType.ICE_SAW_HEAD));
    put("wrought_iron_ice_saw_head", ItemMetal.get(Metal.WROUGHT_IRON, ItemType.ICE_SAW_HEAD));
    put("black_bronze_ice_saw_head", ItemMetal.get(Metal.BLACK_BRONZE, ItemType.ICE_SAW_HEAD));
    put("bronze_ice_saw_head", ItemMetal.get(Metal.BRONZE, ItemType.ICE_SAW_HEAD));

    put("blue_steel_ice_saw", ItemMetal.get(Metal.BLUE_STEEL, ItemType.ICE_SAW));
    put("black_steel_ice_saw", ItemMetal.get(Metal.BLACK_STEEL, ItemType.ICE_SAW));
    put("red_steel_ice_saw", ItemMetal.get(Metal.RED_STEEL, ItemType.ICE_SAW));
    put("steel_ice_saw", ItemMetal.get(Metal.STEEL, ItemType.ICE_SAW));
    put("bismuth_bronze_ice_saw", ItemMetal.get(Metal.BISMUTH_BRONZE, ItemType.ICE_SAW));
    put("wrought_iron_ice_saw", ItemMetal.get(Metal.WROUGHT_IRON, ItemType.ICE_SAW));
    put("black_bronze_ice_saw", ItemMetal.get(Metal.BLACK_BRONZE, ItemType.ICE_SAW));
    put("bronze_ice_saw", ItemMetal.get(Metal.BRONZE, ItemType.ICE_SAW));

    put("blue_steel_mallet_head", ItemMetal.get(Metal.BLUE_STEEL, ItemType.MALLET_HEAD));
    put("black_steel_mallet_head", ItemMetal.get(Metal.BLACK_STEEL, ItemType.MALLET_HEAD));
    put("red_steel_mallet_head", ItemMetal.get(Metal.RED_STEEL, ItemType.MALLET_HEAD));
    put("steel_mallet_head", ItemMetal.get(Metal.STEEL, ItemType.MALLET_HEAD));
    put("bismuth_bronze_mallet_head", ItemMetal.get(Metal.BISMUTH_BRONZE, ItemType.MALLET_HEAD));
    put("wrought_iron_mallet_head", ItemMetal.get(Metal.WROUGHT_IRON, ItemType.MALLET_HEAD));
    put("black_bronze_mallet_head", ItemMetal.get(Metal.BLACK_BRONZE, ItemType.MALLET_HEAD));
    put("bronze_mallet_head", ItemMetal.get(Metal.BRONZE, ItemType.MALLET_HEAD));

    put("blue_steel_mallet", ItemMetal.get(Metal.BLUE_STEEL, ItemType.MALLET));
    put("black_steel_mallet", ItemMetal.get(Metal.BLACK_STEEL, ItemType.MALLET));
    put("red_steel_mallet", ItemMetal.get(Metal.RED_STEEL, ItemType.MALLET));
    put("steel_mallet", ItemMetal.get(Metal.STEEL, ItemType.MALLET));
    put("bismuth_bronze_mallet", ItemMetal.get(Metal.BISMUTH_BRONZE, ItemType.MALLET));
    put("wrought_iron_mallet", ItemMetal.get(Metal.WROUGHT_IRON, ItemType.MALLET));
    put("black_bronze_mallet", ItemMetal.get(Metal.BLACK_BRONZE, ItemType.MALLET));
    put("bronze_mallet", ItemMetal.get(Metal.BRONZE, ItemType.MALLET));
  }};

  protected static final Map<String, EntityEntry> ENTITY_ENTRY_MAP = new Object2ObjectOpenHashMap<>() {{
    put("sitblock", EntitiesCore.SIT_BLOCK.get());
    put("sheeptfc", EntitiesAnimal.SHEEP.get());
    put("cowtfc", EntitiesAnimal.COW.get());
    put("grizzlybeartfc", EntitiesAnimal.GRIZZLYBEAR.get());
    put("chickentfc", EntitiesAnimal.CHICKEN.get());
    put("pheasanttfc", EntitiesAnimal.PHEASANT.get());
    put("deertfc", EntitiesAnimal.DEER.get());
    put("pigtfc", EntitiesAnimal.PIG.get());
    put("wolftfc", EntitiesAnimal.WOLF.get());
    put("rabbittfc", EntitiesAnimal.RABBIT.get());
    put("horsetfc", EntitiesAnimal.HORSE.get());
    put("donkeytfc", EntitiesAnimal.DONKEY.get());
    put("muletfc", EntitiesAnimal.MULE.get());
    put("polarbeartfc", EntitiesAnimal.POLARBEAR.get());
    put("parrottfc", EntitiesAnimal.PARROT.get());
    put("llamatfc", EntitiesAnimal.LLAMA.get());
    put("ocelottfc", EntitiesAnimal.OCELOT.get());
    put("panthertfc", EntitiesAnimal.PANTHER.get());
    put("ducktfc", EntitiesAnimal.DUCK.get());
    put("alpacatfc", EntitiesAnimal.ALPACA.get());
    put("goattfc", EntitiesAnimal.GOAT.get());
    put("sabertoothtfc", EntitiesAnimal.SABERTOOTH.get());
    put("cameltfc", EntitiesAnimal.CAMEL.get());
    put("liontfc", EntitiesAnimal.LION.get());
    put("hyenatfc", EntitiesAnimal.HYENA.get());
    put("direwolftfc", EntitiesAnimal.DIREWOLF.get());
    put("haretfc", EntitiesAnimal.HARE.get());
    put("boartfc", EntitiesAnimal.BOAR.get());
    put("zebutfc", EntitiesAnimal.ZEBU.get());
    put("gazelletfc", EntitiesAnimal.GAZELLE.get());
    put("wildebeesttfc", EntitiesAnimal.WILDEBEEST.get());
    put("quailtfc", EntitiesAnimal.QUAIL.get());
    put("grousetfc", EntitiesAnimal.GROUSE.get());
    put("mongoosetfc", EntitiesAnimal.MONGOOSE.get());
    put("turkeytfc", EntitiesAnimal.TURKEY.get());
    put("jackaltfc", EntitiesAnimal.JACKAL.get());
    put("muskoxtfc", EntitiesAnimal.MUSKOX.get());
    put("yaktfc", EntitiesAnimal.YAK.get());
    put("blackbeartfc", EntitiesAnimal.BLACKBEAR.get());
    put("cougartfc", EntitiesAnimal.COUGAR.get());
    put("coyotetfc", EntitiesAnimal.COYOTE.get());

  }};

  protected static final Map<String, Potion> EFFECT_MAP = new Object2ObjectOpenHashMap<>() {{
    put("cool", EffectsCore.HYPOTHERMIA.get());
    put("warm", EffectsCore.HYPERTHERMIA.get());
    put("overburdened", EffectsCore.OVERBURDENED.get());
    put("thirst", EffectsCore.THIRST.get());
    put("parasites", EffectsCore.PARASITES.get());
    put("swarm", EffectsCore.SWARM.get());

  }};

  protected static final Map<String, PotionType> POTION_MAP = new Object2ObjectOpenHashMap<>() {{

  }};

  protected static final Map<String, SoundEvent> SOUND_MAP = new Object2ObjectOpenHashMap<>() {{

    put("animal.alpaca.cry", SoundsAnimal.ANIMAL_ALPACA_CRY.get());
    put("animal.alpaca.death", SoundsAnimal.ANIMAL_ALPACA_DEATH.get());
    put("animal.alpaca.hurt", SoundsAnimal.ANIMAL_ALPACA_HURT.get());
    put("animal.alpaca.say", SoundsAnimal.ANIMAL_ALPACA_SAY.get());
    put("animal.bear.cry", SoundsAnimal.ANIMAL_BEAR_CRY.get());
    put("animal.bear.death", SoundsAnimal.ANIMAL_BEAR_DEATH.get());
    put("animal.bear.hurt", SoundsAnimal.ANIMAL_BEAR_HURT.get());
    put("animal.bear.say", SoundsAnimal.ANIMAL_BEAR_SAY.get());
    put("animal.boar.death", SoundsAnimal.ANIMAL_BOAR_DEATH.get());
    put("animal.boar.hurt", SoundsAnimal.ANIMAL_BOAR_HURT.get());
    put("animal.boar.say", SoundsAnimal.ANIMAL_BOAR_SAY.get());
    put("animal.camel.cry", SoundsAnimal.ANIMAL_CAMEL_CRY.get());
    put("animal.camel.death", SoundsAnimal.ANIMAL_CAMEL_DEATH.get());
    put("animal.camel.hurt", SoundsAnimal.ANIMAL_CAMEL_HURT.get());
    put("animal.camel.say", SoundsAnimal.ANIMAL_CAMEL_SAY.get());
    put("animal.cougar.cry", SoundsAnimal.ANIMAL_COUGAR_CRY.get());
    put("animal.cougar.death", SoundsAnimal.ANIMAL_COUGAR_DEATH.get());
    put("animal.cougar.hurt", SoundsAnimal.ANIMAL_COUGAR_HURT.get());
    put("animal.cougar.say", SoundsAnimal.ANIMAL_COUGAR_SAY.get());
    put("animal.coyote.cry", SoundsAnimal.ANIMAL_COYOTE_CRY.get());
    put("animal.coyote.death", SoundsAnimal.ANIMAL_COYOTE_DEATH.get());
    put("animal.coyote.hurt", SoundsAnimal.ANIMAL_COYOTE_HURT.get());
    put("animal.coyote.say", SoundsAnimal.ANIMAL_COYOTE_SAY.get());
    put("animal.deer.cry", SoundsAnimal.ANIMAL_DEER_CRY.get());
    put("animal.deer.death", SoundsAnimal.ANIMAL_DEER_DEATH.get());
    put("animal.deer.hurt", SoundsAnimal.ANIMAL_DEER_HURT.get());
    put("animal.deer.say", SoundsAnimal.ANIMAL_DEER_SAY.get());
    put("animal.direwolf.cry", SoundsAnimal.ANIMAL_DIREWOLF_CRY.get());
    put("animal.direwolf.death", SoundsAnimal.ANIMAL_DIREWOLF_DEATH.get());
    put("animal.direwolf.hurt", SoundsAnimal.ANIMAL_DIREWOLF_HURT.get());
    put("animal.direwolf.say", SoundsAnimal.ANIMAL_DIREWOLF_SAY.get());
    put("animal.duck.cry", SoundsAnimal.ANIMAL_DUCK_CRY.get());
    put("animal.duck.death", SoundsAnimal.ANIMAL_DUCK_DEATH.get());
    put("animal.duck.hurt", SoundsAnimal.ANIMAL_DUCK_HURT.get());
    put("animal.duck.say", SoundsAnimal.ANIMAL_DUCK_SAY.get());
    put("animal.feline.step", SoundsAnimal.ANIMAL_FELINE_STEP.get());
    put("animal.gazelle.death", SoundsAnimal.ANIMAL_GAZELLE_DEATH.get());
    put("animal.gazelle.hurt", SoundsAnimal.ANIMAL_GAZELLE_HURT.get());
    put("animal.gazelle.say", SoundsAnimal.ANIMAL_GAZELLE_SAY.get());
    put("animal.goat.cry", SoundsAnimal.ANIMAL_GOAT_CRY.get());
    put("animal.goat.death", SoundsAnimal.ANIMAL_GOAT_DEATH.get());
    put("animal.goat.hurt", SoundsAnimal.ANIMAL_GOAT_HURT.get());
    put("animal.goat.say", SoundsAnimal.ANIMAL_GOAT_SAY.get());
    put("animal.grouse.death", SoundsAnimal.ANIMAL_GROUSE_DEATH.get());
    put("animal.grouse.hurt", SoundsAnimal.ANIMAL_GROUSE_HURT.get());
    put("animal.grouse.say", SoundsAnimal.ANIMAL_GROUSE_SAY.get());
    put("animal.hyena.cry", SoundsAnimal.ANIMAL_HYENA_CRY.get());
    put("animal.hyena.death", SoundsAnimal.ANIMAL_HYENA_DEATH.get());
    put("animal.hyena.hurt", SoundsAnimal.ANIMAL_HYENA_HURT.get());
    put("animal.hyena.say", SoundsAnimal.ANIMAL_HYENA_SAY.get());
    put("animal.zebu.death", SoundsAnimal.ANIMAL_ZEBU_DEATH.get());
    put("animal.zebu.hurt", SoundsAnimal.ANIMAL_ZEBU_HURT.get());
    put("animal.zebu.say", SoundsAnimal.ANIMAL_ZEBU_SAY.get());
    put("animal.jackal.cry", SoundsAnimal.ANIMAL_JACKAL_CRY.get());
    put("animal.jackal.death", SoundsAnimal.ANIMAL_JACKAL_DEATH.get());
    put("animal.jackal.hurt", SoundsAnimal.ANIMAL_JACKAL_HURT.get());
    put("animal.jackal.say", SoundsAnimal.ANIMAL_JACKAL_SAY.get());
    put("animal.lion.cry", SoundsAnimal.ANIMAL_LION_CRY.get());
    put("animal.lion.death", SoundsAnimal.ANIMAL_LION_DEATH.get());
    put("animal.lion.hurt", SoundsAnimal.ANIMAL_LION_HURT.get());
    put("animal.lion.say", SoundsAnimal.ANIMAL_LION_SAY.get());
    put("animal.mongoose.death", SoundsAnimal.ANIMAL_MONGOOSE_DEATH.get());
    put("animal.mongoose.hurt", SoundsAnimal.ANIMAL_MONGOOSE_HURT.get());
    put("animal.mongoose.say", SoundsAnimal.ANIMAL_MONGOOSE_SAY.get());
    put("animal.muskox.death", SoundsAnimal.ANIMAL_MUSKOX_DEATH.get());
    put("animal.muskox.hurt", SoundsAnimal.ANIMAL_MUSKOX_HURT.get());
    put("animal.muskox.say", SoundsAnimal.ANIMAL_MUSKOX_SAY.get());
    put("animal.panther.cry", SoundsAnimal.ANIMAL_PANTHER_CRY.get());
    put("animal.panther.death", SoundsAnimal.ANIMAL_PANTHER_DEATH.get());
    put("animal.panther.hurt", SoundsAnimal.ANIMAL_PANTHER_HURT.get());
    put("animal.panther.say", SoundsAnimal.ANIMAL_PANTHER_SAY.get());
    put("animal.pheasant.death", SoundsAnimal.ANIMAL_PHEASANT_DEATH.get());
    put("animal.pheasant.hurt", SoundsAnimal.ANIMAL_PHEASANT_HURT.get());
    put("animal.pheasant.say", SoundsAnimal.ANIMAL_PHEASANT_SAY.get());
    put("animal.quail.death", SoundsAnimal.ANIMAL_QUAIL_DEATH.get());
    put("animal.quail.hurt", SoundsAnimal.ANIMAL_QUAIL_HURT.get());
    put("animal.quail.say", SoundsAnimal.ANIMAL_QUAIL_SAY.get());
    put("animal.rooster.cry", SoundsAnimal.ANIMAL_ROOSTER_CRY.get());
    put("animal.sabertooth.cry", SoundsAnimal.ANIMAL_SABERTOOTH_CRY.get());
    put("animal.sabertooth.death", SoundsAnimal.ANIMAL_SABERTOOTH_DEATH.get());
    put("animal.sabertooth.hurt", SoundsAnimal.ANIMAL_SABERTOOTH_HURT.get());
    put("animal.sabertooth.say", SoundsAnimal.ANIMAL_SABERTOOTH_SAY.get());
    put("animal.turkey.death", SoundsAnimal.ANIMAL_TURKEY_DEATH.get());
    put("animal.turkey.hurt", SoundsAnimal.ANIMAL_TURKEY_HURT.get());
    put("animal.turkey.say", SoundsAnimal.ANIMAL_TURKEY_SAY.get());
    put("animal.wildebeest.death", SoundsAnimal.ANIMAL_WILDEBEEST_DEATH.get());
    put("animal.wildebeest.hurt", SoundsAnimal.ANIMAL_WILDEBEEST_HURT.get());
    put("animal.wildebeest.say", SoundsAnimal.ANIMAL_WILDEBEEST_SAY.get());
    put("animal.yak.say", SoundsAnimal.ANIMAL_YAK_SAY.get());
    put("animal.yak.death", SoundsAnimal.ANIMAL_YAK_DEATH.get());
    put("animal.yak.hurt", SoundsAnimal.ANIMAL_YAK_HURT.get());

    put("item.flaskbreak", SoundsDevice.FLASK_BREAK.get());
    put("item.firestarter", SoundsDevice.FIRE_STARTER.get());
    put("bellows.blow.air", SoundsDevice.BELLOWS_BLOW_AIR.get());
  }};

  protected static final Set<String> MOD_ID_SET = new ObjectOpenHashSet<>() {{
    add(Names.TFCF);
  }};


}
