package su.terrafirmagreg.framework.manager.registry.api;

import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.framework.manager.registry.base.biome.api.IBiomeEntry;
import su.terrafirmagreg.framework.manager.registry.base.block.api.IBlockEntry;
import su.terrafirmagreg.framework.manager.registry.base.effect.api.IEffectEntry;
import su.terrafirmagreg.framework.manager.registry.base.enchantment.api.IEnchantmentEntry;
import su.terrafirmagreg.framework.manager.registry.base.entity.api.IEntityEntry;
import su.terrafirmagreg.framework.manager.registry.base.group.spi.BaseItemGroup;
import su.terrafirmagreg.framework.manager.registry.base.item.api.IItemEntry;
import su.terrafirmagreg.framework.manager.registry.base.potion.api.IPotionEntry;
import su.terrafirmagreg.framework.manager.registry.base.sound.api.ISoundEntry;

import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.LootFunction.Serializer;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;


public interface IRegistryRegistrar {

  BaseItemGroup group(String icon);

  BaseItemGroup group(BaseItemGroup group);

  <T extends IForgeRegistryEntry<T>> void addEntry(String identifier, T entry);

  // --------------------------------------------------------------------------
  // - Block
  // --------------------------------------------------------------------------

  <V extends Block> V addBlock(String identifier, V block);

  <V extends Block & IBlockEntry> V addBlock(V block);

  <V extends Block & IBlockEntry> Collection<V> addBlock(Collection<V> collection);

  <V extends Block & IBlockEntry, T extends Type<T>> Map<T, V> addBlock(Map<T, V> map);

  <V extends Block & IBlockEntry, T extends Type<T>> Map<T, V> addBlock(Set<T> types, Function<T, V> factory);

  // --------------------------------------------------------------------------
  // - Item
  // --------------------------------------------------------------------------

  <V extends Item> V addItem(String identifier, V item);

  <V extends Item & IItemEntry> V addItem(V item);

  <V extends Item & IItemEntry> Collection<V> addItem(Collection<V> collection);

  <V extends Item & IItemEntry, T extends Type<T>> Map<T, V> addItem(Map<T, V> map);

  <V extends Item & IItemEntry, T extends Type<T>> Map<T, V> addItem(Set<T> types, Function<T, V> factory);

  // --------------------------------------------------------------------------
  // - Biome
  // --------------------------------------------------------------------------

  <V extends Biome> V addBiome(String identifier, V item);

  <V extends Biome & IBiomeEntry> V addBiome(V item);

  <V extends Biome & IBiomeEntry> Collection<V> addBiome(Collection<V> collection);

  <V extends Biome & IBiomeEntry, T extends Type<T>> Map<T, V> addBiome(Map<T, V> map);

  <V extends Biome & IBiomeEntry, T extends Type<T>> Map<T, V> addBiome(Set<T> types, Function<T, V> factory);

  // --------------------------------------------------------------------------
  // - Enchantment
  // --------------------------------------------------------------------------

  <V extends Enchantment> V addEnchantment(String identifier, V entry);

  <V extends Enchantment & IEnchantmentEntry> V addEnchantment(V entry);

  <V extends Enchantment & IEnchantmentEntry> Collection<V> addEnchantment(Collection<V> collection);

  <V extends Enchantment & IEnchantmentEntry, T extends Type<T>> Map<T, V> addEnchantment(Map<T, V> map);

  <V extends Enchantment & IEnchantmentEntry, T extends Type<T>> Map<T, V> addEnchantment(Set<T> types, Function<T, V> factory);

  // --------------------------------------------------------------------------
  // - Effect
  // --------------------------------------------------------------------------

  <V extends Potion> V addEffect(String identifier, V entry);

  <V extends Potion & IEffectEntry> V addEffect(V entry);

  <V extends Potion & IEffectEntry> Collection<V> addEffect(Collection<V> collection);

  <V extends Potion & IEffectEntry, T extends Type<T>> Map<T, V> addEffect(Map<T, V> map);

  <V extends Potion & IEffectEntry, T extends Type<T>> Map<T, V> addEffect(Set<T> types, Function<T, V> factory);

  // --------------------------------------------------------------------------
  // - Potion
  // --------------------------------------------------------------------------

  <V extends PotionType> V addPotion(String identifier, V entry);

  <V extends PotionType & IPotionEntry> V addPotion(V entry);

  <V extends PotionType & IPotionEntry> Collection<V> addPotion(Collection<V> collection);

  <V extends PotionType & IPotionEntry, T extends Type<T>> Map<T, V> addPotion(Map<T, V> map);

  <V extends PotionType & IPotionEntry, T extends Type<T>> Map<T, V> addPotion(Set<T> types, Function<T, V> factory);

  // --------------------------------------------------------------------------
  // - Sound
  // --------------------------------------------------------------------------

  <V extends SoundEvent> V addSound(String identifier, V entry);

  <V extends SoundEvent & ISoundEntry> V addSound(V entry);

  <V extends SoundEvent> SoundEvent addSound(String identifier);

  <V extends SoundEvent & ISoundEntry> Collection<V> addSound(Collection<V> collection);

  <V extends SoundEvent & ISoundEntry, T extends Type<T>> Map<T, V> addSound(Map<T, V> map);

  <V extends SoundEvent & ISoundEntry, T extends Type<T>> Map<T, V> addSound(Set<T> types, Function<T, V> factory);

  // --------------------------------------------------------------------------
  // - Entity
  // --------------------------------------------------------------------------

  <V extends EntityEntry> V addEntity(String identifier, V entry);

  <V extends EntityEntry & IEntityEntry> V addEntity(V entry);

  <V extends EntityEntry & IEntityEntry> Collection<V> addEntity(Collection<V> collection);

  <V extends EntityEntry & IEntityEntry, T extends Type<T>> Map<T, V> addEntity(Map<T, V> map);

  <V extends EntityEntry & IEntityEntry, T extends Type<T>> Map<T, V> addEntity(Set<T> types, Function<T, V> factory);

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
