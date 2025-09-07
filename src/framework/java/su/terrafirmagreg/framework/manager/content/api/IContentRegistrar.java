package su.terrafirmagreg.framework.manager.content.api;

import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.framework.manager.api.IBaseRegistrar;
import su.terrafirmagreg.framework.manager.content.base.biome.api.IBiomeEntry;
import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry;
import su.terrafirmagreg.framework.manager.content.base.effect.api.IEffectEntry;
import su.terrafirmagreg.framework.manager.content.base.enchantment.api.IEnchantmentEntry;
import su.terrafirmagreg.framework.manager.content.base.entity.api.IEntityEntry;
import su.terrafirmagreg.framework.manager.content.base.group.spi.BaseItemGroup;
import su.terrafirmagreg.framework.manager.content.base.item.api.IItemEntry;
import su.terrafirmagreg.framework.manager.content.base.potion.api.IPotionEntry;
import su.terrafirmagreg.framework.manager.content.base.sound.api.ISoundEntry;

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

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;


public interface IContentRegistrar extends IBaseRegistrar<IContentEntry<?, ?>> {

  BaseItemGroup group(String icon);

  <V extends IContentEntry<?, ?>> V addContent(V entry);

  // --------------------------------------------------------------------------
  // - Block
  // --------------------------------------------------------------------------

  <V extends Block & IBlockEntry> V addBlock(String identifier, V entry);

  <V extends Block & IBlockEntry, T> Map<T, V> addBlock(String identifier, Function<T, V> factory, Collection<T> types);

  <V extends Block & IBlockEntry> V addBlock(V block);

  <V extends Block & IBlockEntry, T> Map<T, V> addBlock(Function<T, V> factory, Collection<T> types);

  // --------------------------------------------------------------------------
  // - Item
  // --------------------------------------------------------------------------

  <V extends Item & IItemEntry> V addItem(V item);

  <V extends Item & IItemEntry, T extends Type<T>> Map<T, V> addItem(Function<T, V> factory, Set<T> types);

  // --------------------------------------------------------------------------
  // - Biome
  // --------------------------------------------------------------------------

  <V extends Biome & IBiomeEntry> V addBiome(V item);

  <V extends Biome & IBiomeEntry, T extends Type<T>> Map<T, V> addBiome(Function<T, V> factory, Set<T> types);

  // --------------------------------------------------------------------------
  // - Enchantment
  // --------------------------------------------------------------------------

  <V extends Enchantment & IEnchantmentEntry> V addEnchantment(V entry);

  <V extends Enchantment & IEnchantmentEntry, T extends Type<T>> Map<T, V> addEnchantment(Function<T, V> factory, Set<T> types);

  // --------------------------------------------------------------------------
  // - Effect
  // --------------------------------------------------------------------------

  <V extends Potion & IEffectEntry> V addEffect(V entry);

  <V extends Potion & IEffectEntry, T extends Type<T>> Map<T, V> addEffect(Function<T, V> factory, Set<T> types);

  // --------------------------------------------------------------------------
  // - Potion
  // --------------------------------------------------------------------------

  <V extends PotionType & IPotionEntry> V addPotion(V entry);

  <V extends PotionType & IPotionEntry, T extends Type<T>> Map<T, V> addPotion(Function<T, V> factory, Set<T> types);

  // --------------------------------------------------------------------------
  // - Sound
  // --------------------------------------------------------------------------

  <V extends SoundEvent & ISoundEntry> V addSound(V entry);

  <V extends SoundEvent> SoundEvent addSound(String identifier);

  <V extends SoundEvent & ISoundEntry, T extends Type<T>> Map<T, V> addSound(Function<T, V> factory, Set<T> types);

  // --------------------------------------------------------------------------
  // - Entity
  // --------------------------------------------------------------------------

  <V extends EntityEntry & IEntityEntry> V addEntity(V entry);

  <V extends EntityEntry & IEntityEntry, T extends Type<T>> Map<T, V> addEntity(Function<T, V> factory, Set<T> types);

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
