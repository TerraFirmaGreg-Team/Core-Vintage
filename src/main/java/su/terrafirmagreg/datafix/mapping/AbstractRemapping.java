package su.terrafirmagreg.datafix.mapping;

import su.terrafirmagreg.api.data.enums.Mods.Names;
import su.terrafirmagreg.modules.animal.init.BlocksAnimal;
import su.terrafirmagreg.modules.animal.init.EntitiesAnimal;
import su.terrafirmagreg.modules.animal.init.ItemsAnimal;
import su.terrafirmagreg.modules.core.init.BlocksCore;
import su.terrafirmagreg.modules.core.init.EffectsCore;
import su.terrafirmagreg.modules.core.init.EntitiesCore;
import su.terrafirmagreg.modules.core.init.ItemsCore;
import su.terrafirmagreg.modules.device.init.BlocksDevice;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
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

    put("alloy_calculator", BlocksDevice.ALLOY_CALCULATOR.get());
//    put("bear_trap", BlocksDevice.BEAR_TRAP.get());

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

  protected static final Set<String> MOD_ID_SET = new ObjectOpenHashSet<>() {{
    add(Names.TFCF);
  }};

}
