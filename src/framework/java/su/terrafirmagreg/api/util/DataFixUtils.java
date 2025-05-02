package su.terrafirmagreg.api.util;

import su.terrafirmagreg.api.data.enums.Mods;
import su.terrafirmagreg.api.data.enums.Mods.ModIDs;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

@SuppressWarnings("unused")
public class DataFixUtils {


  public static void of() {
    MinecraftForge.EVENT_BUS.register(DataFixUtils.class);
  }

  public static final Set<String> MOD_ID_SET = new ObjectOpenHashSet<>() {{
    add(ModIDs.TFCF);
    add(ModIDs.CAFFEINEADDON);
    add(ModIDs.CELLARS);
  }};

  public static final Map<String, Block> BLOCK_MAP = new Object2ObjectOpenHashMap<>();
  public static final Map<String, Item> ITEM_MAP = new Object2ObjectOpenHashMap<>();
  public static final Map<String, EntityEntry> ENTITY_MAP = new Object2ObjectOpenHashMap<>();
  public static final Map<String, Potion> EFFECT_MAP = new Object2ObjectOpenHashMap<>();
  public static final Map<String, PotionType> POTION_MAP = new Object2ObjectOpenHashMap<>();
  public static final Map<String, SoundEvent> SOUND_MAP = new Object2ObjectOpenHashMap<>();
  public static final Map<String, Biome> BIOME_MAP = new Object2ObjectOpenHashMap<>();
  public static final Map<String, Enchantment> ENCHANTMENT_MAP = new Object2ObjectOpenHashMap<>();

  private static <T extends IForgeRegistryEntry<T>> void remap(RegistryEvent.MissingMappings<T> event, Map<String, T> map) {

    event.getAllMappings().forEach(mapping -> {
      String mappingKey = mapping.key.toString();
      String mappingNamespace = mapping.key.getNamespace();
      String mappingPath = mapping.key.getPath();

//      if (!MOD_ID_SET.contains(mappingNamespace)) {
//        mapping.warn();
//      }

      if (!Mods.contains(mappingNamespace)) {
        mapping.warn();
      }

      map.forEach((key, value) -> {
        if (mappingPath.endsWith(key)) {
          mapping.remap(value);
        }
      });
      return;

    });
  }

  public static <K, V> void put(Map<K, V> map, Consumer<Map<K, V>> consumer) {
    Map<K, V> temp = new HashMap<>();
    consumer.accept(temp);
    map.putAll(temp);
    temp.clear();
  }

  @SubscribeEvent
  public static void onBlockRemapping(final RegistryEvent.MissingMappings<Block> event) {
    DataFixUtils.remap(event, BLOCK_MAP);
  }

  @SubscribeEvent
  public static void onEffectRemapping(final RegistryEvent.MissingMappings<Potion> event) {
    DataFixUtils.remap(event, EFFECT_MAP);
  }

  @SubscribeEvent
  public static void onEntityRemapping(final RegistryEvent.MissingMappings<EntityEntry> event) {
    DataFixUtils.remap(event, ENTITY_MAP);
  }

  @SubscribeEvent
  public static void onItemRemapping(final RegistryEvent.MissingMappings<Item> event) {
    DataFixUtils.remap(event, ITEM_MAP);
  }

  @SubscribeEvent
  public static void onPotionRemapping(final RegistryEvent.MissingMappings<PotionType> event) {
    DataFixUtils.remap(event, POTION_MAP);
  }

  @SubscribeEvent
  public static void onSoundRemapping(final RegistryEvent.MissingMappings<SoundEvent> event) {
    DataFixUtils.remap(event, SOUND_MAP);
  }

  @SubscribeEvent
  public static void onBiomeRemapping(final RegistryEvent.MissingMappings<Biome> event) {
    DataFixUtils.remap(event, BIOME_MAP);
  }

  @SubscribeEvent
  public static void onEnchantmentRemapping(final RegistryEvent.MissingMappings<Enchantment> event) {
    DataFixUtils.remap(event, ENCHANTMENT_MAP);
  }

}
