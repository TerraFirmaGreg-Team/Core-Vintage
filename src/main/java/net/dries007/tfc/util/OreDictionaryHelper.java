package net.dries007.tfc.util;

import su.terrafirmagreg.modules.core.feature.damageresistance.spi.DamageType;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import com.google.common.base.Joiner;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Multimap;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.objects.Powder;
import net.dries007.tfc.objects.blocks.BlockDecorativeStone;
import net.dries007.tfc.objects.blocks.BlocksTFC;
import net.dries007.tfc.objects.blocks.BlocksTFCF;
import net.dries007.tfc.objects.blocks.groundcover.BlockCoral;
import net.dries007.tfc.objects.blocks.groundcover.BlockCoralBlock;
import net.dries007.tfc.objects.blocks.plants.BlockPlantTFC;
import net.dries007.tfc.objects.items.ItemPowder;
import net.dries007.tfc.types.BlockTypesTFCF.RockTFCF;
import net.dries007.tfc.types.PlantsTFCF;

/**
 * This is not the best example of good coding practice, but I do think it works rather well. The reason for the delayed registration it because now the helper's functions can be called in the constructor of the blocks/items (BEFORE they
 * are actually in registries). At this point you cannot yet make an itemstack. Storing based on RegistryName is also not possible, as they don't have one yet.
 */
public class OreDictionaryHelper {

  public static final Multimap<Thing, String> MAP = HashMultimap.create();
  private static final Converter<String, String> UPPER_UNDERSCORE_TO_LOWER_CAMEL = CaseFormat.UPPER_UNDERSCORE.converterTo(CaseFormat.LOWER_CAMEL);
  private static final Joiner JOINER_UNDERSCORE = Joiner.on('_').skipNulls();
  private static final boolean done = false;

  public static String toString(Object... parts) {
    return UPPER_UNDERSCORE_TO_LOWER_CAMEL.convert(JOINER_UNDERSCORE.join(parts));
  }

  public static String toString(Iterable<Object> parts) {
    return UPPER_UNDERSCORE_TO_LOWER_CAMEL.convert(JOINER_UNDERSCORE.join(parts));
  }

  public static String toString(Object[] prefix, Object... parts) {
    return toString(ImmutableList.builder().add(prefix).add(parts).build());
  }

  public static void register(Block thing, Object... parts) {
    register(new Thing(thing), parts);
  }

  public static void register(Item thing, Object... parts) {
    register(new Thing(thing), parts);
  }

  public static void registerMeta(Item thing, int meta, Object... parts) {
    register(new Thing(thing, meta), parts);
  }

  public static void registerRockType(Block thing, Rock.Type type, Object... prefixParts) {
    registerRockType(new Thing(thing), type, prefixParts);
  }

  public static void registerDamageType(Item thing, DamageType type) {
    register(thing, "damage", "type", type.name().toLowerCase());
  }

  public static void registerRockType(Block thing, RockTFCF rockTFCF, Object... prefixParts) {
    registerRockType(new Thing(thing), rockTFCF, prefixParts);
  }


  private static void register(Thing thing, Object... parts) {
    MAP.put(thing, toString(parts));
  }

  private static void registerRockType(Thing thing, RockTFCF rockTFCF, Object... prefixParts) {
    switch (rockTFCF) {
      case MOSSY_RAW:
        MAP.put(thing, toString(prefixParts, "stone"));
        //MAP.put(thing, toString(prefixParts, "stone", rock));
        MAP.put(thing, toString(prefixParts, "stone_mossy"));
        //MAP.put(thing, toString(prefixParts, "stone_mossy", rock));
        break;
      case MUD:
        MAP.put(thing, toString(prefixParts, "block", rockTFCF));
        //MAP.put(thing, toString(prefixParts, "block", rockTFCF, rock));
        break;
      case MUD_BRICKS:
        MAP.put(thing, toString(prefixParts, rockTFCF));
        //MAP.put(thing, toString(prefixParts, "mud_bricks", rock));
        break;
      case COARSE_DIRT:
      case COARSE_LOAMY_SAND:
      case COARSE_SANDY_LOAM:
      case COARSE_LOAM:
      case COARSE_SILT_LOAM:
      case COARSE_SILT:
      case COARSE_HUMUS:
        MAP.put(thing, toString(prefixParts, rockTFCF));
        MAP.put(thing, toString(prefixParts, "coarse_dirt"));
        //MAP.put(thing, toString(prefixParts, "coarse_dirt", rock));
        break;
      case COARSE_CLAY:
      case COARSE_SANDY_CLAY_LOAM:
      case COARSE_SANDY_CLAY:
      case COARSE_CLAY_LOAM:
      case COARSE_SILTY_CLAY:
      case COARSE_SILTY_CLAY_LOAM:
      case COARSE_CLAY_HUMUS:
        MAP.put(thing, toString(prefixParts, rockTFCF));
        MAP.put(thing, toString(prefixParts, "coarse_dirt"));
        //MAP.put(thing, toString(prefixParts, "coarse_dirt", rock));
        MAP.put(thing, toString(prefixParts, "coarse_clay_dirt"));
        //MAP.put(thing, toString(prefixParts, "coarse_clay_dirt", rock));
        break;
      case LOAMY_SAND_GRASS:
      case SANDY_LOAM_GRASS:
      case LOAM_GRASS:
      case SILT_LOAM_GRASS:
      case SILT_GRASS:
      case HUMUS_GRASS:
        MAP.put(thing, toString(prefixParts, rockTFCF));
        MAP.put(thing, toString(prefixParts, "grass"));
        //MAP.put(thing, toString(prefixParts, "grass", rock));
        break;
      case PODZOL:
      case LOAMY_SAND_PODZOL:
      case SANDY_LOAM_PODZOL:
      case LOAM_PODZOL:
      case SILT_LOAM_PODZOL:
      case SILT_PODZOL:
        MAP.put(thing, toString(prefixParts, rockTFCF));
        MAP.put(thing, toString(prefixParts, "podzol"));
        //MAP.put(thing, toString(prefixParts, "podzol", rock));
        break;
      case LOAMY_SAND_FARMLAND:
      case SANDY_LOAM_FARMLAND:
      case LOAM_FARMLAND:
      case SILT_LOAM_FARMLAND:
      case SILT_FARMLAND:
      case HUMUS_FARMLAND:
        MAP.put(thing, toString(prefixParts, rockTFCF));
        MAP.put(thing, toString(prefixParts, "farmland"));
        //MAP.put(thing, toString(prefixParts, "farmland", rock));
        break;
      case LOAMY_SAND:
      case SANDY_LOAM:
      case LOAM:
      case SILT_LOAM:
      case SILT:
      case HUMUS:
        MAP.put(thing, toString(prefixParts, rockTFCF));
        MAP.put(thing, toString(prefixParts, "soil"));
        //MAP.put(thing, toString(prefixParts, "soil", rock));
        MAP.put(thing, toString(prefixParts, "dirt"));
        //MAP.put(thing, toString(prefixParts, "dirt", rock));
        break;
      case SANDY_CLAY_LOAM:
      case SANDY_CLAY:
      case CLAY_LOAM:
      case SILTY_CLAY:
      case SILTY_CLAY_LOAM:
      case CLAY_HUMUS:
        MAP.put(thing, toString(prefixParts, rockTFCF));
        MAP.put(thing, toString(prefixParts, "block_clay"));
        //MAP.put(thing, toString(prefixParts, "block_clay", rock));
        break;
      case DRY_CLAY_GRASS:
      case DRY_SANDY_CLAY_LOAM_GRASS:
      case DRY_SANDY_CLAY_GRASS:
      case DRY_CLAY_LOAM_GRASS:
      case DRY_SILTY_CLAY_GRASS:
      case DRY_SILTY_CLAY_LOAM_GRASS:
      case DRY_CLAY_HUMUS_GRASS:
        MAP.put(thing, toString(prefixParts, rockTFCF));
        MAP.put(thing, toString(prefixParts, "block_clay_dirt"));
        //MAP.put(thing, toString(prefixParts, "block_clay_dirt", rock));
        MAP.put(thing, toString(prefixParts, "dry_grass"));
        //MAP.put(thing, toString(prefixParts, "dry_grass", rock));
        break;
      case SANDY_CLAY_LOAM_GRASS:
      case SANDY_CLAY_GRASS:
      case CLAY_LOAM_GRASS:
      case SILTY_CLAY_GRASS:
      case SILTY_CLAY_LOAM_GRASS:
      case CLAY_HUMUS_GRASS:
        MAP.put(thing, toString(prefixParts, rockTFCF));
        MAP.put(thing, toString(prefixParts, "block_clay_grass"));
        //MAP.put(thing, toString(prefixParts, "block_clay_grass", rock));
        MAP.put(thing, toString(prefixParts, "grass"));
        //MAP.put(thing, toString(prefixParts, "grass", rock));
      case CLAY_PODZOL:
      case SANDY_CLAY_LOAM_PODZOL:
      case SANDY_CLAY_PODZOL:
      case CLAY_LOAM_PODZOL:
      case SILTY_CLAY_PODZOL:
      case SILTY_CLAY_LOAM_PODZOL:
        MAP.put(thing, toString(prefixParts, rockTFCF));
        MAP.put(thing, toString(prefixParts, "block_clay_podzol"));
        //MAP.put(thing, toString(prefixParts, "block_clay_podzol", rock));
        MAP.put(thing, toString(prefixParts, "podzol"));
        //MAP.put(thing, toString(prefixParts, "podzol", rock));
        break;
      default:
        MAP.put(thing, toString(prefixParts, rockTFCF));
    }
  }

  private static void registerRockType(Thing thing, Rock.Type type, Object... prefixParts) {
    switch (type) {
      case RAW:
        MAP.put(thing, toString(prefixParts, "stone"));
        break;
      case SMOOTH:
        MAP.put(thing, toString(prefixParts, "stone", "polished"));
        break;
      case COBBLE:
        MAP.put(thing, toString(prefixParts, "cobblestone"));
        break;
      case BRICKS:
        MAP.put(thing, toString(prefixParts, "stone", "brick"));
        MAP.put(thing, toString(prefixParts, "brick", "stone"));
        break;
      case DRY_GRASS:
        MAP.put(thing, toString(prefixParts, type, "dry"));
        break;
      case CLAY:
        MAP.put(thing, toString(prefixParts, "block", type, "dirt"));
        break;
      case CLAY_GRASS:
        MAP.put(thing, toString(prefixParts, "block", type));
        break;
      case SAND:
      case GRAVEL:
      case DIRT:
      case GRASS:
      default:
        MAP.put(thing, toString(prefixParts, type));
    }
  }

  public static void init() {
    OreDictionaryHelper.MAP.forEach((t, s) -> OreDictionary.registerOre(s, t.toItemStack()));
    OreDictionaryHelper.MAP.clear(); // No need to keep this stuff around

    //oredict support for TFC dyes
    OreDictionary.registerOre("dyePink", new ItemStack(ItemPowder.get(Powder.KAOLINITE)));
    OreDictionary.registerOre("dyeGray", new ItemStack(ItemPowder.get(Powder.GRAPHITE)));
    OreDictionary.registerOre("dyeRed", new ItemStack(ItemPowder.get(Powder.HEMATITE)));
    OreDictionary.registerOre("dyeBlue", new ItemStack(ItemPowder.get(Powder.LAPIS_LAZULI)));
    OreDictionary.registerOre("dyeYellow", new ItemStack(ItemPowder.get(Powder.LIMONITE)));
    OreDictionary.registerOre("dyeGreen", new ItemStack(ItemPowder.get(Powder.MALACHITE)));
    OreDictionary.registerOre("dyeBrown", new ItemStack(ItemPowder.get(Powder.FERTILIZER)));
    OreDictionary.registerOre("dyeBlack", new ItemStack(ItemPowder.get(Powder.CHARCOAL)));
    OreDictionary.registerOre("dyeBlack", new ItemStack(ItemPowder.get(Powder.COKE)));

    BlockDecorativeStone.ALABASTER_BRICKS.forEach((dyeColor, blockDecorativeStone) -> OreDictionary.registerOre("alabasterBricks", new ItemStack(blockDecorativeStone)));
    BlockDecorativeStone.ALABASTER_BRICKS.forEach((dyeColor, blockDecorativeStone) -> OreDictionary.registerOre("bricksAlabaster", new ItemStack(blockDecorativeStone)));
    BlockDecorativeStone.ALABASTER_POLISHED.forEach((dyeColor, blockDecorativeStone) -> OreDictionary.registerOre("alabasterPolished", new ItemStack(blockDecorativeStone)));
    BlockDecorativeStone.ALABASTER_RAW.forEach((dyeColor, blockDecorativeStone) -> OreDictionary.registerOre("alabasterRaw", new ItemStack(blockDecorativeStone)));

    OreDictionary.registerOre("alabasterBricks", new ItemStack(BlocksTFC.ALABASTER_BRICKS_PLAIN));
    OreDictionary.registerOre("bricksAlabaster", new ItemStack(BlocksTFC.ALABASTER_BRICKS_PLAIN));
    OreDictionary.registerOre("alabasterRaw", new ItemStack(BlocksTFC.ALABASTER_RAW_PLAIN));
    OreDictionary.registerOre("alabasterPolished", new ItemStack(BlocksTFC.ALABASTER_POLISHED_PLAIN));

    // TFC Florae
    OreDictionary.registerOre("baleCotton", new ItemStack(BlocksTFCF.COTTON_BALE));
    OreDictionary.registerOre("baleCottonYarn", new ItemStack(BlocksTFCF.COTTON_YARN_BALE));
    OreDictionary.registerOre("baleFlax", new ItemStack(BlocksTFCF.FLAX_BALE));
    OreDictionary.registerOre("baleFlaxFiber", new ItemStack(BlocksTFCF.FLAX_FIBER_BALE));
    OreDictionary.registerOre("baleHemp", new ItemStack(BlocksTFCF.HEMP_BALE));
    OreDictionary.registerOre("baleHempFiber", new ItemStack(BlocksTFCF.HEMP_FIBER_BALE));
    OreDictionary.registerOre("baleJute", new ItemStack(BlocksTFCF.JUTE_BALE));
    OreDictionary.registerOre("baleJuteFiber", new ItemStack(BlocksTFCF.JUTE_FIBER_BALE));
    OreDictionary.registerOre("baleLinen", new ItemStack(BlocksTFCF.LINEN_BALE));
    OreDictionary.registerOre("baleLinenString", new ItemStack(BlocksTFCF.LINEN_STRING_BALE));
    OreDictionary.registerOre("balePapyrusFiber", new ItemStack(BlocksTFCF.PAPYRUS_FIBER_BALE));
    OreDictionary.registerOre("baleSilkString", new ItemStack(BlocksTFCF.SILK_STRING_BALE));
    OreDictionary.registerOre("baleSisalFiber", new ItemStack(BlocksTFCF.SISAL_FIBER_BALE));
    OreDictionary.registerOre("baleYucca", new ItemStack(BlocksTFCF.YUCCA_BALE));
    OreDictionary.registerOre("baleYuccaFiber", new ItemStack(BlocksTFCF.YUCCA_FIBER_BALE));
    OreDictionary.registerOre("glue", new ItemStack(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.RESIN))));
    OreDictionary.registerOre("slimeball", new ItemStack(BlockPlantTFC.get(TFCRegistries.PLANTS.getValue(PlantsTFCF.RESIN))));

    // Corals
    BlockCoral.TUBE_CORAL.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coralTube", new ItemStack(blockCoral)));
    BlockCoral.BRAIN_CORAL.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coralBrain", new ItemStack(blockCoral)));
    BlockCoral.BUBBLE_CORAL.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coralBubble", new ItemStack(blockCoral)));
    BlockCoral.FIRE_CORAL.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coralFire", new ItemStack(blockCoral)));
    BlockCoral.HORN_CORAL.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coralHorn", new ItemStack(blockCoral)));

    BlockCoral.TUBE_CORAL.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coral", new ItemStack(blockCoral)));
    BlockCoral.BRAIN_CORAL.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coral", new ItemStack(blockCoral)));
    BlockCoral.BUBBLE_CORAL.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coral", new ItemStack(blockCoral)));
    BlockCoral.FIRE_CORAL.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coral", new ItemStack(blockCoral)));
    BlockCoral.HORN_CORAL.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coral", new ItemStack(blockCoral)));

    OreDictionary.registerOre("coralTubeDead", new ItemStack(BlocksTFCF.TUBE_CORAL_DEAD));
    OreDictionary.registerOre("coralBrainDead", new ItemStack(BlocksTFCF.BRAIN_CORAL_DEAD));
    OreDictionary.registerOre("coralBubbleDead", new ItemStack(BlocksTFCF.BUBBLE_CORAL_DEAD));
    OreDictionary.registerOre("coralFireDead", new ItemStack(BlocksTFCF.FIRE_CORAL_DEAD));
    OreDictionary.registerOre("coralHornDead", new ItemStack(BlocksTFCF.HORN_CORAL_DEAD));

    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.TUBE_CORAL_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.BRAIN_CORAL_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.BUBBLE_CORAL_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.FIRE_CORAL_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.HORN_CORAL_DEAD));

    BlockCoral.TUBE_CORAL_FAN.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coralFanTube", new ItemStack(blockCoral)));
    BlockCoral.BRAIN_CORAL_FAN.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coralFanBrain", new ItemStack(blockCoral)));
    BlockCoral.BUBBLE_CORAL_FAN.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coralFanBubble", new ItemStack(blockCoral)));
    BlockCoral.FIRE_CORAL_FAN.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coralFanFire", new ItemStack(blockCoral)));
    BlockCoral.HORN_CORAL_FAN.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coralFanHorn", new ItemStack(blockCoral)));

    BlockCoral.TUBE_CORAL_FAN.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coral", new ItemStack(blockCoral)));
    BlockCoral.BRAIN_CORAL_FAN.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coral", new ItemStack(blockCoral)));
    BlockCoral.BUBBLE_CORAL_FAN.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coral", new ItemStack(blockCoral)));
    BlockCoral.FIRE_CORAL_FAN.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coral", new ItemStack(blockCoral)));
    BlockCoral.HORN_CORAL_FAN.forEach((dyeColor, blockCoral) -> OreDictionary.registerOre("coral", new ItemStack(blockCoral)));

    OreDictionary.registerOre("coralFanTubeDead", new ItemStack(BlocksTFCF.TUBE_CORAL_FAN_DEAD));
    OreDictionary.registerOre("coralFanBrainDead", new ItemStack(BlocksTFCF.BRAIN_CORAL_FAN_DEAD));
    OreDictionary.registerOre("coralFanBubbleDead", new ItemStack(BlocksTFCF.BUBBLE_CORAL_FAN_DEAD));
    OreDictionary.registerOre("coralFanFireDead", new ItemStack(BlocksTFCF.FIRE_CORAL_FAN_DEAD));
    OreDictionary.registerOre("coralFanHornDead", new ItemStack(BlocksTFCF.HORN_CORAL_FAN_DEAD));

    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.TUBE_CORAL_FAN_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.BRAIN_CORAL_FAN_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.BUBBLE_CORAL_FAN_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.FIRE_CORAL_FAN_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.HORN_CORAL_FAN_DEAD));

    BlockCoralBlock.TUBE_CORAL_BLOCK.forEach((dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coralFanTube", new ItemStack(blockCoralBlock)));
    BlockCoralBlock.BRAIN_CORAL_BLOCK.forEach((dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coralFanBrain", new ItemStack(blockCoralBlock)));
    BlockCoralBlock.BUBBLE_CORAL_BLOCK.forEach((dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coralFanBubble", new ItemStack(blockCoralBlock)));
    BlockCoralBlock.FIRE_CORAL_BLOCK.forEach((dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coralFanFire", new ItemStack(blockCoralBlock)));
    BlockCoralBlock.HORN_CORAL_BLOCK.forEach((dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coralFanHorn", new ItemStack(blockCoralBlock)));

    BlockCoralBlock.TUBE_CORAL_BLOCK.forEach((dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coralBlock", new ItemStack(blockCoralBlock)));
    BlockCoralBlock.BRAIN_CORAL_BLOCK.forEach((dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coralBlock", new ItemStack(blockCoralBlock)));
    BlockCoralBlock.BUBBLE_CORAL_BLOCK.forEach((dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coralBlock", new ItemStack(blockCoralBlock)));
    BlockCoralBlock.FIRE_CORAL_BLOCK.forEach((dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coralBlock", new ItemStack(blockCoralBlock)));
    BlockCoralBlock.HORN_CORAL_BLOCK.forEach((dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coralBlock", new ItemStack(blockCoralBlock)));

    BlockCoralBlock.TUBE_CORAL_BLOCK.forEach((dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coral", new ItemStack(blockCoralBlock)));
    BlockCoralBlock.BRAIN_CORAL_BLOCK.forEach((dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coral", new ItemStack(blockCoralBlock)));
    BlockCoralBlock.BUBBLE_CORAL_BLOCK.forEach((dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coral", new ItemStack(blockCoralBlock)));
    BlockCoralBlock.FIRE_CORAL_BLOCK.forEach((dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coral", new ItemStack(blockCoralBlock)));
    BlockCoralBlock.HORN_CORAL_BLOCK.forEach((dyeColor, blockCoralBlock) -> OreDictionary.registerOre("coral", new ItemStack(blockCoralBlock)));

    OreDictionary.registerOre("coralBlockTubeDead", new ItemStack(BlocksTFCF.TUBE_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralBlockBrainDead", new ItemStack(BlocksTFCF.BRAIN_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralBlockBubbleDead", new ItemStack(BlocksTFCF.BUBBLE_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralBlockFireDead", new ItemStack(BlocksTFCF.FIRE_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralBlockHornDead", new ItemStack(BlocksTFCF.HORN_CORAL_BLOCK_DEAD));

    OreDictionary.registerOre("coralBlockDead", new ItemStack(BlocksTFCF.TUBE_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralBlockDead", new ItemStack(BlocksTFCF.BRAIN_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralBlockDead", new ItemStack(BlocksTFCF.BUBBLE_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralBlockDead", new ItemStack(BlocksTFCF.FIRE_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralBlockDead", new ItemStack(BlocksTFCF.HORN_CORAL_BLOCK_DEAD));

    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.TUBE_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.BRAIN_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.BUBBLE_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.FIRE_CORAL_BLOCK_DEAD));
    OreDictionary.registerOre("coralDead", new ItemStack(BlocksTFCF.HORN_CORAL_BLOCK_DEAD));

  }

  public static class Thing {

    private final Block block;
    private final Item item;
    private final int meta;

    public Thing(Block thing) {
      block = thing;
      item = null;
      meta = 0;
    }

    public Thing(Item thing) {
      this(thing, -1);
    }

    public Thing(Item thing, int meta) {
      block = null;
      item = thing;
      this.meta = meta;
    }

    public ItemStack toItemStack() {
      if (block != null) {
        return new ItemStack(block, 1, meta);
      } else if (item != null) {
        int meta = this.meta;
        if (meta == -1 && item.isDamageable()) {
          meta = OreDictionary.WILDCARD_VALUE;
        }
        return new ItemStack(item, 1, meta);
      }
      return ItemStack.EMPTY;
    }
  }

}
