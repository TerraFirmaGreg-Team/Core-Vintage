package su.terrafirmagreg.framework.manager.registry;

import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.api.util.KeyBindUtils;
import su.terrafirmagreg.api.util.LootUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryEntry;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.framework.manager.registry.base.biome.api.IBiomeEntry;
import su.terrafirmagreg.framework.manager.registry.base.block.api.IBlockEntry;
import su.terrafirmagreg.framework.manager.registry.base.effect.api.IEffectEntry;
import su.terrafirmagreg.framework.manager.registry.base.enchantment.api.IEnchantmentEntry;
import su.terrafirmagreg.framework.manager.registry.base.entity.api.IEntityEntry;
import su.terrafirmagreg.framework.manager.registry.base.group.spi.BaseItemGroup;
import su.terrafirmagreg.framework.manager.registry.base.item.api.IItemEntry;
import su.terrafirmagreg.framework.manager.registry.base.potion.api.IPotionEntry;
import su.terrafirmagreg.framework.manager.registry.base.sound.api.ISoundEntry;
import su.terrafirmagreg.framework.module.api.IModule;

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

import lombok.Getter;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class RegistryRegistrar implements IRegistryRegistrar {

  private final IModule module;
  private final RegistryMap map;

  private BaseItemGroup group;

  public RegistryRegistrar(RegistryManager manager) {

    this.module = manager.getModule();
    this.map = manager.getMap();
  }

  // Предмет должен быть в рамках модуля
  @Override
  public BaseItemGroup group(String icon) {

    return this.group(BaseItemGroup.of(module.getIdentifier(), getIdentifier(icon)));
  }

  @Override
  public BaseItemGroup group(BaseItemGroup group) {
    this.group = group;
    return this.group;
  }

  public ResourceLocation getIdentifier(String identifier) {
    return ModUtils.resource(module.getIdentifier(), identifier);
  }

  @Override
  public <T extends IForgeRegistryEntry<T>> T addEntry(Class<T> registry, String identifier, T entry) {
    //this.map.computeIfAbsent(registry, RegistryWrapper.of(getIdentifier(identifier), entry));
    if (entry.getRegistryName() == null) {
      entry.setRegistryName(getIdentifier(identifier));
    }
    addEntry(entry);
    return entry;
  }

  public <T extends IForgeRegistryEntry<T>> void addEntry(T entry) {

    if (entry instanceof IRegistryEntry<?, ?> registryEntry) {
      this.map.computeIfAbsent(registryEntry.getRegistryType(), registryEntry);
    }
  }

  // region Block

  @Override
  public <V extends Block> V addBlock(String identifier, V entry) {

    entry.setCreativeTab(group);
    addEntry(Block.class, identifier, entry);
    return entry;
  }

  @Override
  public <V extends Block & IBlockEntry> V addBlock(V entry) {

    var settings = entry.getSettings();
    if (settings.getItemBlock() != null) {
      this.addItem(settings.getRegistryKey(), settings.getItemBlock().apply(entry));
    }

    return this.addBlock(settings.getRegistryKey(), entry);
  }

  public <V extends Block & IBlockEntry> Collection<V> addBlock(Collection<V> collection) {

    collection.forEach(this::addBlock);
    return collection;
  }

  public <V extends Block & IBlockEntry, T extends Type<T>> Map<T, V> addBlock(Map<T, V> entry) {

    this.addBlock(entry.values());
    return entry;
  }

  public <V extends Block & IBlockEntry, T extends Type<T>> Map<T, V> addBlock(Set<T> types, Function<T, V> factory) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addBlock(factory.apply(type))));
  }

  // endregion

  // region Item

  @Override
  public <V extends Item> V addItem(String identifier, V entry) {

    entry.setCreativeTab(group);
    addEntry(Item.class, identifier, entry);
    return entry;
  }

  @Override
  public <V extends Item & IItemEntry> V addItem(V entry) {

    return this.addItem(entry.getSettings().getRegistryKey(), entry);
  }

  @Override
  public <V extends Item & IItemEntry> Collection<V> addItem(Collection<V> collection) {

    collection.forEach(this::addItem);
    return collection;
  }

  @Override
  public <V extends Item & IItemEntry, T extends Type<T>> Map<T, V> addItem(Map<T, V> map) {

    this.addItem(map.values());
    return map;
  }

  @Override
  public <V extends Item & IItemEntry, T extends Type<T>> Map<T, V> addItem(Set<T> types, Function<T, V> factory) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addItem(factory.apply(type))));
  }

  // endregion

  // region Biome


  @Override
  public <V extends Biome> V addBiome(String identifier, V entry) {

    addEntry(Biome.class, identifier, entry);
    return entry;
  }

  @Override
  public <V extends Biome & IBiomeEntry> V addBiome(V entry) {

    var settings = entry.getSettings();
    return this.addBiome(settings.getRegistryKey(), entry);
  }

  @Override
  public <V extends Biome & IBiomeEntry> Collection<V> addBiome(Collection<V> collection) {

    collection.forEach(this::addBiome);
    return collection;
  }

  @Override
  public <V extends Biome & IBiomeEntry, T extends Type<T>> Map<T, V> addBiome(Map<T, V> map) {

    this.addBiome(map.values());
    return map;
  }

  @Override
  public <V extends Biome & IBiomeEntry, T extends Type<T>> Map<T, V> addBiome(Set<T> types, Function<T, V> factory) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addBiome(factory.apply(type))));
  }

  // endregion

  // region Enchantment


  @Override
  public <V extends Enchantment> V addEnchantment(String identifier, V entry) {

    addEntry(Enchantment.class, identifier, entry);
    return entry;
  }

  @Override
  public <V extends Enchantment & IEnchantmentEntry> V addEnchantment(V entry) {

    var settings = entry.getSettings();
    return this.addEnchantment(settings.getRegistryKey(), entry);
  }

  @Override
  public <V extends Enchantment & IEnchantmentEntry> Collection<V> addEnchantment(Collection<V> collection) {

    collection.forEach(this::addEnchantment);
    return collection;
  }

  @Override
  public <V extends Enchantment & IEnchantmentEntry, T extends Type<T>> Map<T, V> addEnchantment(Map<T, V> map) {

    this.addEnchantment(map.values());
    return map;
  }

  @Override
  public <V extends Enchantment & IEnchantmentEntry, T extends Type<T>> Map<T, V> addEnchantment(Set<T> types, Function<T, V> factory) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addEnchantment(factory.apply(type))));
  }

  // endregion

  // region Effect


  @Override
  public <V extends Potion> V addEffect(String identifier, V entry) {

    addEntry(Potion.class, identifier, entry);
    return entry;
  }

  @Override
  public <V extends Potion & IEffectEntry> V addEffect(V entry) {

    var settings = entry.getSettings();
    return this.addEffect(settings.getRegistryKey(), entry);
  }

  @Override
  public <V extends Potion & IEffectEntry> Collection<V> addEffect(Collection<V> collection) {

    collection.forEach(this::addEffect);
    return collection;
  }

  @Override
  public <V extends Potion & IEffectEntry, T extends Type<T>> Map<T, V> addEffect(Map<T, V> map) {

    this.addEffect(map.values());
    return map;
  }

  @Override
  public <V extends Potion & IEffectEntry, T extends Type<T>> Map<T, V> addEffect(Set<T> types, Function<T, V> factory) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addEffect(factory.apply(type))));
  }

  // endregion

  // region Potion


  @Override
  public <V extends PotionType> V addPotion(String identifier, V entry) {

    addEntry(PotionType.class, identifier, entry);
    return entry;
  }

  @Override
  public <V extends PotionType & IPotionEntry> V addPotion(V entry) {

    var settings = entry.getSettings();
    return this.addPotion(settings.getRegistryKey(), entry);
  }

  @Override
  public <V extends PotionType & IPotionEntry> Collection<V> addPotion(Collection<V> collection) {

    collection.forEach(this::addPotion);
    return collection;
  }

  @Override
  public <V extends PotionType & IPotionEntry, T extends Type<T>> Map<T, V> addPotion(Map<T, V> map) {

    this.addPotion(map.values());
    return map;
  }

  @Override
  public <V extends PotionType & IPotionEntry, T extends Type<T>> Map<T, V> addPotion(Set<T> types, Function<T, V> factory) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addPotion(factory.apply(type))));
  }

  // endregion

  // region Sound


  @Override
  public <V extends SoundEvent> V addSound(String identifier, V entry) {

    addEntry(SoundEvent.class, identifier, entry);
    return entry;
  }

  @Override
  public <V extends SoundEvent & ISoundEntry> V addSound(V entry) {

    var settings = entry.getSettings();
    return this.addSound(settings.getRegistryKey(), entry);
  }

  @Override
  public <V extends SoundEvent> SoundEvent addSound(String identifier) {
    return this.addSound(identifier, new SoundEvent(getIdentifier(identifier)));
  }

  @Override
  public <V extends SoundEvent & ISoundEntry> Collection<V> addSound(Collection<V> collection) {

    collection.forEach(this::addSound);
    return collection;
  }

  @Override
  public <V extends SoundEvent & ISoundEntry, T extends Type<T>> Map<T, V> addSound(Map<T, V> map) {

    this.addSound(map.values());
    return map;
  }

  @Override
  public <V extends SoundEvent & ISoundEntry, T extends Type<T>> Map<T, V> addSound(Set<T> types, Function<T, V> factory) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addSound(factory.apply(type))));
  }

  // endregion

  // region Entity


  @Override
  public <V extends EntityEntry> V addEntity(String identifier, V entry) {

    addEntry(EntityEntry.class, identifier, entry);
    return entry;
  }

  @Override
  public <V extends EntityEntry & IEntityEntry> V addEntity(V entry) {

    var settings = entry.getSettings();

    this.addEntity(settings.getRegistryKey(), entry);
    return entry;
  }

  @Override
  public <V extends EntityEntry & IEntityEntry> Collection<V> addEntity(Collection<V> collection) {

    collection.forEach(this::addEntity);
    return collection;
  }

  @Override
  public <V extends EntityEntry & IEntityEntry, T extends Type<T>> Map<T, V> addEntity(Map<T, V> map) {

    this.addEntity(map.values());
    return map;
  }

  @Override
  public <V extends EntityEntry & IEntityEntry, T extends Type<T>> Map<T, V> addEntity(Set<T> types, Function<T, V> factory) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addEntity(factory.apply(type))));
  }

  // endregion

  // region Key Binding

  @Override
  public KeyBinding addKeyBinding(String name, int keyCode) {

    return KeyBindUtils.addKeyBinding(ModUtils.localize("key", getIdentifier(name)), keyCode, ModUtils.localize("categories", module.getIdentifier()));
  }

  // endregion

  // region Key Binding

  @Override
  public ResourceLocation addLoot(String name) {

    return LootUtils.addLoot(getIdentifier(name));
  }

  @Override
  public <T extends LootFunction> void addLootFunction(Serializer<? extends T> serializer) {

    LootUtils.addLootFunction(serializer);
  }

  // endregion
}
