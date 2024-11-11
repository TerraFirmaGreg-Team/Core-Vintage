package su.terrafirmagreg.modules.metal.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.api.library.Pair;
import su.terrafirmagreg.modules.metal.api.types.type.MetalType;
import su.terrafirmagreg.modules.metal.api.types.variant.Item.MetalItemVariant;

import net.minecraft.item.Item;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;

import java.util.Map;

public class ItemsMetal {

  public static final Map<Pair<MetalItemVariant, MetalType>, Item> METAL_ITEMS = new Object2ObjectLinkedOpenHashMap<>();

  public static MetalItemVariant INGOT;
  public static MetalItemVariant DOUBLE_INGOT;
  public static MetalItemVariant SCRAP;
  public static MetalItemVariant DUST;
  public static MetalItemVariant NUGGET;
  public static MetalItemVariant SHEET;
  public static MetalItemVariant DOUBLE_SHEET;
  public static MetalItemVariant ROD;

  public static MetalItemVariant TUYERE;
  public static MetalItemVariant PICK;
  public static MetalItemVariant PICK_HEAD;
  public static MetalItemVariant SHOVEL;
  public static MetalItemVariant SHOVEL_HEAD;
  public static MetalItemVariant AXE;
  public static MetalItemVariant AXE_HEAD;
  public static MetalItemVariant HOE;
  public static MetalItemVariant HOE_HEAD;
  public static MetalItemVariant CHISEL;
  public static MetalItemVariant CHISEL_HEAD;
  public static MetalItemVariant SWORD;
  public static MetalItemVariant SWORD_BLADE;
  public static MetalItemVariant MACE;
  public static MetalItemVariant MACE_HEAD;
  public static MetalItemVariant SAW;
  public static MetalItemVariant SAW_BLADE;
  public static MetalItemVariant JAVELIN;
  public static MetalItemVariant JAVELIN_HEAD;
  public static MetalItemVariant ROPE_JAVELIN;
  public static MetalItemVariant HOOK_JAVELIN;
  public static MetalItemVariant HOOK_JAVELIN_HEAD;
  public static MetalItemVariant HAMMER;
  public static MetalItemVariant HAMMER_HEAD;
  public static MetalItemVariant PROPICK;
  public static MetalItemVariant PROPICK_HEAD;
  public static MetalItemVariant KNIFE;
  public static MetalItemVariant KNIFE_BLADE;
  public static MetalItemVariant SCYTHE;
  public static MetalItemVariant SCYTHE_BLADE;
  public static MetalItemVariant PROSPECTORS_HAMMER;
  public static MetalItemVariant PROSPECTORS_HAMMER_HEAD;
  public static MetalItemVariant MALLET;
  public static MetalItemVariant MALLET_HEAD;
  public static MetalItemVariant TONGS;
  public static MetalItemVariant TONGS_HEAD;
  public static MetalItemVariant SHEARS;
  public static MetalItemVariant BLOWPIPE;

  public static MetalItemVariant UNFINISHED_HELMET;
  public static MetalItemVariant HELMET;
  public static MetalItemVariant UNFINISHED_CHESTPLATE;
  public static MetalItemVariant CHESTPLATE;
  public static MetalItemVariant UNFINISHED_GREAVES;
  public static MetalItemVariant GREAVES;
  public static MetalItemVariant UNFINISHED_BOOTS;
  public static MetalItemVariant BOOTS;

  public static MetalItemVariant SHIELD;

  public static MetalItemVariant BUCKET;

  public static void onRegister(RegistryManager registry) {

    registry.items(METAL_ITEMS.values());

  }

}
