package su.terrafirmagreg.framework.manager.registry;

import su.terrafirmagreg.api.base.object.biome.api.IBiomeSettings;
import su.terrafirmagreg.api.base.object.block.api.IBlockSettings;
import su.terrafirmagreg.api.base.object.effect.api.IEffectSettings;
import su.terrafirmagreg.api.base.object.enchantment.api.IEnchantmentSettings;
import su.terrafirmagreg.api.base.object.entity.api.IEntitySettings;
import su.terrafirmagreg.api.base.object.group.spi.BaseItemGroup;
import su.terrafirmagreg.api.base.object.item.api.IItemSettings;
import su.terrafirmagreg.api.base.object.potion.api.IPotionSettings;
import su.terrafirmagreg.api.base.object.sound.api.ISoundSettings;
import su.terrafirmagreg.api.library.IdSupplier;
import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.api.util.BiomeUtils;
import su.terrafirmagreg.api.util.KeyBindUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.manager.registry.RegistryMap.RegistryWrapper;
import su.terrafirmagreg.framework.manager.registry.api.IRegistryRegistrar;
import su.terrafirmagreg.framework.manager.registry.api.provider.IProviderTile;
import su.terrafirmagreg.framework.module.api.IModule;

import net.minecraft.block.Block;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
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

  private final IdSupplier idSupplier;
  private BaseItemGroup group;

  public RegistryRegistrar(RegistryManager manager) {

    this.module = manager.getModule();
    this.map = manager.getMap();

    this.idSupplier = new IdSupplier();
  }

  @Override
  public BaseItemGroup group(String icon) {
    this.group = BaseItemGroup.of(module, icon);
    return this.group;
  }

  @Override
  public BaseItemGroup group(BaseItemGroup group) {
    this.group = group;
    return this.group;
  }

  // region Block

  public ResourceLocation getIdentifier(String identifier) {
    return ModUtils.resource(module.getIdentifier(), identifier);
  }

  @Override
  public <T extends IForgeRegistryEntry<T>> void addEntry(Class<T> registry, String identifier, T entry) {
    entry.setRegistryName(getIdentifier(identifier));
    this.map.computeIfAbsent(registry, RegistryWrapper.of(getIdentifier(identifier), () -> entry));
  }

  @Override
  public <V extends Block> V addBlock(String identifier, V entry) {

    entry.setCreativeTab(group);
    addEntry(Block.class, identifier, entry);
    if (entry instanceof IProviderTile provider) {
      this.addTile(identifier, provider.getTileClass());
    }
    return entry;
  }

  @Override
  public <V extends Block & IBlockSettings> V addBlock(V entry) {

    entry.overrideSetter();
    var settings = entry.getSettings();
    if (settings.getItemBlock() != null) {
      this.addItem(settings.getRegistryKey(), settings.getItemBlock().apply(entry));
    }

    return this.addBlock(settings.getRegistryKey(), entry);
  }

  @Override
  public <V extends Block & IBlockSettings> Collection<V> addBlock(Collection<V> collection) {

    collection.forEach(this::addBlock);
    return collection;
  }

  // endregion

  // region Item

  @Override
  public <V extends Block & IBlockSettings, T extends Type<T>> Map<T, V> addBlock(Map<T, V> entry) {

    this.addBlock(entry.values());
    return entry;
  }

  @Override
  public <V extends Block & IBlockSettings, T extends Type<T>> Map<T, V> addBlock(Set<T> types, Function<T, V> factory) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addBlock(factory.apply(type))));
  }

  @Override
  public <V extends Item> V addItem(String identifier, V entry) {
    entry.setCreativeTab(group);
    addEntry(Item.class, identifier, entry);
    return entry;
  }

  @Override
  public <V extends Item & IItemSettings> V addItem(V entry) {

    entry.overrideSetter();
    var settings = entry.getSettings();
    return this.addItem(settings.getRegistryKey(), entry);
  }

  @Override
  public <V extends Item & IItemSettings> Collection<V> addItem(Collection<V> collection) {

    collection.forEach(this::addItem);
    return collection;
  }

  // endregion

  // region Biome

  @Override
  public <V extends Item & IItemSettings, T extends Type<T>> Map<T, V> addItem(Map<T, V> map) {

    this.addItem(map.values());
    return map;
  }

  @Override
  public <V extends Item & IItemSettings, T extends Type<T>> Map<T, V> addItem(Set<T> types, Function<T, V> factory) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addItem(factory.apply(type))));
  }

  @Override
  public <V extends Biome> V addBiome(String identifier, V entry) {

    addEntry(Biome.class, identifier, entry);
    if (entry instanceof IBiomeSettings provider) {
      var settings = provider.getSettings();
      BiomeUtils.addTypes(entry, settings.getTypes());
    }
    return entry;
  }

  @Override
  public <V extends Biome & IBiomeSettings> V addBiome(V entry) {

    var settings = entry.getSettings();
    return this.addBiome(settings.getRegistryKey(), entry);
  }

  @Override
  public <V extends Biome & IBiomeSettings> Collection<V> addBiome(Collection<V> collection) {

    collection.forEach(this::addBiome);
    return collection;
  }

  // endregion

  // region Enchantment

  @Override
  public <V extends Biome & IBiomeSettings, T extends Type<T>> Map<T, V> addBiome(Map<T, V> map) {

    this.addBiome(map.values());
    return map;
  }

  @Override
  public <V extends Biome & IBiomeSettings, T extends Type<T>> Map<T, V> addBiome(Set<T> types, Function<T, V> factory) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addBiome(factory.apply(type))));
  }

  @Override
  public <V extends Enchantment> V addEnchantment(String identifier, V entry) {

    addEntry(Enchantment.class, identifier, entry);
    return entry;
  }

  @Override
  public <V extends Enchantment & IEnchantmentSettings> V addEnchantment(V entry) {

    var settings = entry.getSettings();
    return this.addEnchantment(settings.getRegistryKey(), entry);
  }

  @Override
  public <V extends Enchantment & IEnchantmentSettings> Collection<V> addEnchantment(Collection<V> collection) {

    collection.forEach(this::addEnchantment);
    return collection;
  }

  // endregion

  // region Effect

  @Override
  public <V extends Enchantment & IEnchantmentSettings, T extends Type<T>> Map<T, V> addEnchantment(Map<T, V> map) {

    this.addEnchantment(map.values());
    return map;
  }

  @Override
  public <V extends Enchantment & IEnchantmentSettings, T extends Type<T>> Map<T, V> addEnchantment(Set<T> types, Function<T, V> factory) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addEnchantment(factory.apply(type))));
  }

  @Override
  public <V extends Potion> V addEffect(String identifier, V entry) {

    addEntry(Potion.class, identifier, entry);
    return entry;
  }

  @Override
  public <V extends Potion & IEffectSettings> V addEffect(V entry) {

    var settings = entry.getSettings();
    return this.addEffect(settings.getRegistryKey(), entry);
  }

  @Override
  public <V extends Potion & IEffectSettings> Collection<V> addEffect(Collection<V> collection) {

    collection.forEach(this::addEffect);
    return collection;
  }

  // endregion

  // region Potion

  @Override
  public <V extends Potion & IEffectSettings, T extends Type<T>> Map<T, V> addEffect(Map<T, V> map) {

    this.addEffect(map.values());
    return map;
  }

  @Override
  public <V extends Potion & IEffectSettings, T extends Type<T>> Map<T, V> addEffect(Set<T> types, Function<T, V> factory) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addEffect(factory.apply(type))));
  }

  @Override
  public <V extends PotionType> V addPotion(String identifier, V entry) {

    addEntry(PotionType.class, identifier, entry);
    return entry;
  }

  @Override
  public <V extends PotionType & IPotionSettings> V addPotion(V entry) {

    var settings = entry.getSettings();
    return this.addPotion(settings.getRegistryKey(), entry);
  }

  @Override
  public <V extends PotionType & IPotionSettings> Collection<V> addPotion(Collection<V> collection) {

    collection.forEach(this::addPotion);
    return collection;
  }

  // endregion

  // region Sound

  @Override
  public <V extends PotionType & IPotionSettings, T extends Type<T>> Map<T, V> addPotion(Map<T, V> map) {

    this.addPotion(map.values());
    return map;
  }

  @Override
  public <V extends PotionType & IPotionSettings, T extends Type<T>> Map<T, V> addPotion(Set<T> types, Function<T, V> factory) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addPotion(factory.apply(type))));
  }

  @Override
  public <V extends SoundEvent> V addSound(String identifier, V entry) {

    addEntry(SoundEvent.class, identifier, entry);
    return entry;
  }

  @Override
  public <V extends SoundEvent & ISoundSettings> V addSound(V entry) {

    var settings = entry.getSettings();
    return this.addSound(settings.getRegistryKey(), entry);
  }

  @Override
  public <V extends SoundEvent> SoundEvent addSound(String identifier) {
    return this.addSound(identifier, new SoundEvent(getIdentifier(identifier)));
  }

  @Override
  public <V extends SoundEvent & ISoundSettings> Collection<V> addSound(Collection<V> collection) {

    collection.forEach(this::addSound);
    return collection;
  }

  // endregion

  // region Entity

  @Override
  public <V extends SoundEvent & ISoundSettings, T extends Type<T>> Map<T, V> addSound(Map<T, V> map) {

    this.addSound(map.values());
    return map;
  }

  @Override
  public <V extends SoundEvent & ISoundSettings, T extends Type<T>> Map<T, V> addSound(Set<T> types, Function<T, V> factory) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addSound(factory.apply(type))));
  }

  @Override
  public <V extends EntityEntry> V addEntity(String identifier, V entry) {

    addEntry(EntityEntry.class, identifier, entry);
    return entry;
  }

  @Override
  public <V extends EntityEntry & IEntitySettings> V addEntity(V entry) {

    var settings = entry.getSettings();
    return this.addEntity(settings.getRegistryKey(), entry);
  }

  @Override
  public <V extends EntityEntry & IEntitySettings> Collection<V> addEntity(Collection<V> collection) {

    collection.forEach(this::addEntity);
    return collection;
  }

  @Override
  public <V extends EntityEntry & IEntitySettings, T extends Type<T>> Map<T, V> addEntity(Map<T, V> map) {

    this.addEntity(map.values());
    return map;
  }

  @Override
  public <V extends EntityEntry & IEntitySettings, T extends Type<T>> Map<T, V> addEntity(Set<T> types, Function<T, V> factory) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addEntity(factory.apply(type))));
  }

  @Override
  public <V extends Entity> EntityEntry addEntity(String identifier, EntityEntryBuilder<V> builder) {

    builder.id(getIdentifier(identifier), this.idSupplier.getAndIncrement());
    builder.name(ModUtils.localize(getIdentifier(identifier)));

    return this.addEntity(identifier, builder.build());
  }

  @Override
  public <V extends Entity> EntityEntry addEntity(String identifier, Class<V> entClass, int primary, int seconday) {

    final EntityEntryBuilder<V> builder = EntityEntryBuilder.create();
    builder.entity(entClass);
    builder.tracker(64, 1, true);
    builder.egg(primary, seconday);

    return this.addEntity(identifier, builder);
  }

  // endregion

  // region Tile

  @Override
  public <V extends TileEntity> Class<V> addTile(String identifier, Class<V> tileClass) {
    TileUtils.register(tileClass, getIdentifier(identifier));
    return tileClass;
  }

  // endregion

  // region Key Binding

  @Override
  public KeyBinding addKeyBinding(String name, int keyCode) {
    final KeyBinding key = new KeyBinding(ModUtils.localize("key", getIdentifier(name)), keyCode, ModUtils.localize("categories", module.getIdentifier()));
    KeyBindUtils.register(key);
    return key;
  }

  // endregion

  // region Key Binding

  public ResourceLocation addLoot(String name) {

    return LootTableList.register(getIdentifier(name));
  }

  // endregion
}
