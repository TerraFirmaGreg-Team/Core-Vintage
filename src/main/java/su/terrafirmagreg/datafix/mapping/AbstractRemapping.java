package su.terrafirmagreg.datafix.mapping;

import su.terrafirmagreg.api.data.enums.Mods.Names;
import su.terrafirmagreg.modules.core.init.BlocksCore;
import su.terrafirmagreg.modules.core.init.EffectsCore;
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


  }};

  protected static final Map<String, Item> ITEM_MAP = new Object2ObjectOpenHashMap<>() {{
    put("wand", ItemsCore.WAND.get());

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
