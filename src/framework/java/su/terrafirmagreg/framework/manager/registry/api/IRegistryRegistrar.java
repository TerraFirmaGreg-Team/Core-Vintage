package su.terrafirmagreg.framework.manager.registry.api;

import su.terrafirmagreg.api.base.object.biome.api.IBiomeSettings;
import su.terrafirmagreg.api.base.object.block.api.IBlockSettings;
import su.terrafirmagreg.api.base.object.effect.api.IEffectSettings;
import su.terrafirmagreg.api.base.object.enchantment.api.IEnchantmentSettings;
import su.terrafirmagreg.api.base.object.entity.api.IEntitySettings;
import su.terrafirmagreg.api.base.object.group.spi.BaseItemGroup;
import su.terrafirmagreg.api.base.object.item.api.IItemSettings;
import su.terrafirmagreg.api.base.object.potion.api.IPotionSettings;
import su.terrafirmagreg.api.base.object.sound.api.ISoundSettings;
import su.terrafirmagreg.api.library.types.type.Type;

import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.LootFunction.Serializer;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;


public interface IRegistryRegistrar {

  BaseItemGroup group(String icon);

  BaseItemGroup group(BaseItemGroup group);

  <T extends IForgeRegistryEntry<T>> T addEntry(Class<T> registry, String identifier, T entry);

  // --------------------------------------------------------------------------
  // - Block
  // --------------------------------------------------------------------------

  <V extends Block> V addBlock(String identifier, V block);

  <V extends Block & IBlockSettings> V addBlock(V block);

  <V extends Block & IBlockSettings> Collection<V> addBlock(Collection<V> collection);

  <V extends Block & IBlockSettings, T extends Type<T>> Map<T, V> addBlock(Map<T, V> map);

  <V extends Block & IBlockSettings, T extends Type<T>> Map<T, V> addBlock(Set<T> types, Function<T, V> factory);

  // --------------------------------------------------------------------------
  // - Item
  // --------------------------------------------------------------------------

  <V extends Item> V addItem(String identifier, V item);

  <V extends Item & IItemSettings> V addItem(V item);

  <V extends Item & IItemSettings> Collection<V> addItem(Collection<V> collection);

  <V extends Item & IItemSettings, T extends Type<T>> Map<T, V> addItem(Map<T, V> map);

  <V extends Item & IItemSettings, T extends Type<T>> Map<T, V> addItem(Set<T> types, Function<T, V> factory);

  // --------------------------------------------------------------------------
  // - Biome
  // --------------------------------------------------------------------------

  <V extends Biome> V addBiome(String identifier, V item);

  <V extends Biome & IBiomeSettings> V addBiome(V item);

  <V extends Biome & IBiomeSettings> Collection<V> addBiome(Collection<V> collection);

  <V extends Biome & IBiomeSettings, T extends Type<T>> Map<T, V> addBiome(Map<T, V> map);

  <V extends Biome & IBiomeSettings, T extends Type<T>> Map<T, V> addBiome(Set<T> types, Function<T, V> factory);

  // --------------------------------------------------------------------------
  // - Enchantment
  // --------------------------------------------------------------------------

  <V extends Enchantment> V addEnchantment(String identifier, V entry);

  <V extends Enchantment & IEnchantmentSettings> V addEnchantment(V entry);

  <V extends Enchantment & IEnchantmentSettings> Collection<V> addEnchantment(Collection<V> collection);

  <V extends Enchantment & IEnchantmentSettings, T extends Type<T>> Map<T, V> addEnchantment(Map<T, V> map);

  <V extends Enchantment & IEnchantmentSettings, T extends Type<T>> Map<T, V> addEnchantment(Set<T> types, Function<T, V> factory);

  // --------------------------------------------------------------------------
  // - Effect
  // --------------------------------------------------------------------------

  <V extends Potion> V addEffect(String identifier, V entry);

  <V extends Potion & IEffectSettings> V addEffect(V entry);

  <V extends Potion & IEffectSettings> Collection<V> addEffect(Collection<V> collection);

  <V extends Potion & IEffectSettings, T extends Type<T>> Map<T, V> addEffect(Map<T, V> map);

  <V extends Potion & IEffectSettings, T extends Type<T>> Map<T, V> addEffect(Set<T> types, Function<T, V> factory);

  // --------------------------------------------------------------------------
  // - Potion
  // --------------------------------------------------------------------------

  <V extends PotionType> V addPotion(String identifier, V entry);

  <V extends PotionType & IPotionSettings> V addPotion(V entry);

  <V extends PotionType & IPotionSettings> Collection<V> addPotion(Collection<V> collection);

  <V extends PotionType & IPotionSettings, T extends Type<T>> Map<T, V> addPotion(Map<T, V> map);

  <V extends PotionType & IPotionSettings, T extends Type<T>> Map<T, V> addPotion(Set<T> types, Function<T, V> factory);

  // --------------------------------------------------------------------------
  // - Sound
  // --------------------------------------------------------------------------

  <V extends SoundEvent> V addSound(String identifier, V entry);

  <V extends SoundEvent & ISoundSettings> V addSound(V entry);

  <V extends SoundEvent> SoundEvent addSound(String identifier);

  <V extends SoundEvent & ISoundSettings> Collection<V> addSound(Collection<V> collection);

  <V extends SoundEvent & ISoundSettings, T extends Type<T>> Map<T, V> addSound(Map<T, V> map);

  <V extends SoundEvent & ISoundSettings, T extends Type<T>> Map<T, V> addSound(Set<T> types, Function<T, V> factory);

  // --------------------------------------------------------------------------
  // - Entity
  // --------------------------------------------------------------------------

  <V extends EntityEntry> V addEntity(String identifier, V entry);

  <V extends EntityEntry & IEntitySettings> V addEntity(V entry);

  <V extends EntityEntry & IEntitySettings> Collection<V> addEntity(Collection<V> collection);

  <V extends EntityEntry & IEntitySettings, T extends Type<T>> Map<T, V> addEntity(Map<T, V> map);

  <V extends EntityEntry & IEntitySettings, T extends Type<T>> Map<T, V> addEntity(Set<T> types, Function<T, V> factory);

  <V extends Entity> EntityEntry addEntity(String identifier, EntityEntryBuilder<V> builder);

  <V extends Entity> EntityEntry addEntity(String identifier, Class<V> entClass, int primary, int seconday);

  // --------------------------------------------------------------------------
  // - KeyBinding
  // --------------------------------------------------------------------------

  KeyBinding addKeyBinding(String name, int keyCode);

  // --------------------------------------------------------------------------
  // - Loot
  // --------------------------------------------------------------------------

  ResourceLocation addLoot(String name);

  <T extends LootFunction> void addLootFunction(Serializer<? extends T> serializer);
}
