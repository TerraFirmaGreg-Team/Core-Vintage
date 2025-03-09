package su.terrafirmagreg.framework.registry.api;

import su.terrafirmagreg.api.base.object.biome.api.IBiomeSettings;
import su.terrafirmagreg.api.base.object.block.api.IBlockSettings;
import su.terrafirmagreg.api.base.object.effect.api.IEffectSettings;
import su.terrafirmagreg.api.base.object.enchantment.api.IEnchantmentSettings;
import su.terrafirmagreg.api.base.object.group.spi.BaseItemGroup;
import su.terrafirmagreg.api.base.object.item.api.IItemSettings;
import su.terrafirmagreg.api.base.object.potion.api.IPotionSettings;
import su.terrafirmagreg.api.base.object.sound.api.ISoundSettings;
import su.terrafirmagreg.api.library.IdSupplier;
import su.terrafirmagreg.api.library.collection.RegistrySupplierMap;
import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.module.api.IModule;
import su.terrafirmagreg.framework.registry.spi.RegistryWrapper;
import su.terrafirmagreg.framework.registry.spi.builder.LootBuilder;

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
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraft.world.storage.loot.functions.LootFunction;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraft.world.storage.loot.functions.SetCount;
import net.minecraft.world.storage.loot.functions.SetMetadata;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public interface IRegistryManager extends IRegistryEventHandler {

  IModule getModule();

  RegistryWrapper getWrapper();

  IdSupplier getIdSupplier();

  IRegistryManager group(Supplier<BaseItemGroup> group);

  BaseItemGroup getGroup();

  default ResourceLocation getIdentifier(String identifier) {
    var moduleIdentifier = this.getModule().getIdentifier();
    return ModUtils.resource(moduleIdentifier.getNamespace(), ModUtils.regKey(moduleIdentifier.getPath(), identifier));
  }


  default <T extends IForgeRegistryEntry<T>> Supplier<T> addEntry(RegistrySupplierMap<T> registry, String identifier, Supplier<T> entry) {
    ModUtils.isValidPath(identifier);
    registry.put(getIdentifier(identifier), entry);
    return entry;
  }

  // region Block

  default <B extends Block & IBlockSettings> Supplier<B> block(B block) {
    block.defaultSetter();
    var settings = block.getSettings();
    if (settings.getItemBlock() != null) {
      settings.group(getGroup());
      settings.getGroups().forEach(block::setCreativeTab);
      this.item(settings.getRegistryKey(), settings.getItemBlock().apply(block));
    }

    return this.block(settings.getRegistryKey(), block);
  }

  default <T extends Type<T>, B extends Block & IBlockSettings> Map<T, B> block(Map<T, B> map) {
    this.block(map.values());

    return map;
  }

  default <T extends Block & IBlockSettings> Collection<T> block(Collection<T> collection) {
    collection.forEach(this::block);
    return collection;
  }

  default <T extends Type<T>, B extends Block & IBlockSettings> Map<T, Supplier<B>> block(Set<T> types, Function<T, B> factory) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.block(factory.apply(type))));
  }

  default <B extends Block> Supplier<B> block(String identifier, B block) {
    return this.block(identifier, (Supplier<B>) () -> block);
  }

  default <B extends Block> Supplier<B> block(String identifier, Item itemBlock, B block) {
    return this.block(identifier, () -> itemBlock, () -> block);
  }

  default <B extends Block> Supplier<B> block(String identifier, Supplier<Item> itemBlock, Supplier<B> block) {
    if (itemBlock != null) {
      this.item(identifier, itemBlock);
    }
    return this.block(identifier, block);
  }


  default <B extends Block> Supplier<B> block(String identifier, Supplier<B> block) {
    this.addEntry(getWrapper().getBlocks(), identifier, (Supplier<Block>) block);
    return block;
  }

  // endregion

  // region Item

  default <T extends Item & IItemSettings> Supplier<T> item(T item) {
    var settings = item.getSettings();
    item.defaultSetter();
    settings.getGroups().add(getGroup());
    settings.getGroups().forEach(item::setCreativeTab);
    return this.item(settings.getRegistryKey(), item);
  }

  default <T extends Type<T>, I extends Item> Map<T, I> item(Map<T, I> map) {
    this.item(map.values());

    return map;
  }

  default <T extends Item> void item(Collection<T> collection) {
    collection.forEach(item -> {
      if (item instanceof IItemSettings provider) {
        provider.defaultSetter();
        var settings = provider.getSettings();
        settings.getGroups().add(getGroup());
        settings.getGroups().forEach(item::setCreativeTab);
        this.item(settings.getRegistryKey(), item);
      }
    });
  }

  default <T extends Type<T>, I extends Item & IItemSettings> Map<T, Supplier<I>> item(Set<T> types, Function<T, I> factory) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.item(factory.apply(type))));
  }

  default <T extends Item> Supplier<T> item(String identifier, T item) {
    return this.item(identifier, (Supplier<T>) () -> item);
  }

  default <T extends Item> Supplier<T> item(String identifier, Supplier<T> item) {
    this.addEntry(getWrapper().getItems(), identifier, (Supplier<Item>) item);
    return item;
  }

  // endregion

  // region Biome

  default <T extends Biome & IBiomeSettings> Supplier<T> biome(T biome) {

    return this.biome(biome.getSettings().getRegistryKey(), biome);
  }

  default <T extends Biome> Supplier<T> biome(String identifier, T biome) {
    return this.biome(identifier, (Supplier<T>) () -> biome);
  }

  default <T extends Biome> Supplier<T> biome(String identifier, Supplier<T> biome) {
    this.addEntry(getWrapper().getBiomes(), identifier, (Supplier<Biome>) biome);
    return biome;
  }

  // endregion

  // region Enchantment

  default <T extends Enchantment & IEnchantmentSettings> Supplier<Enchantment> enchantment(T enchantment) {

    return this.enchantment(enchantment.getSettings().getRegistryKey(), enchantment);
  }

  default Supplier<Enchantment> enchantment(String identifier, Enchantment enchantment) {
    return this.enchantment(identifier, () -> enchantment);
  }

  default Supplier<Enchantment> enchantment(String identifier, Supplier<Enchantment> enchantment) {
    return this.addEntry(getWrapper().getEnchantments(), identifier, enchantment);
  }

  // endregion

  // region EntityEntry

//  default <T extends Entity & IEntitySettings> Supplier<EntityEntry> entity(T entity) {
//    var settings = entity.getSettings();
//    settings.entity(entity.getClass());
//    return this.entity(settings.getRegistryKey(), settings.getBuilder());
//  }

  default <T extends Entity> Supplier<EntityEntry> entity(String identifier, EntityEntryBuilder<T> builder) {

    builder.id(getIdentifier(identifier), this.getIdSupplier().getAndIncrement());
    builder.name(ModUtils.localize(getIdentifier(identifier)));

    return this.entity(identifier, builder.build());
  }

  default <T extends Entity> Supplier<EntityEntry> entity(String identifier, Class<T> entClass, int primary, int seconday) {

    final EntityEntryBuilder<T> builder = EntityEntryBuilder.create();
    builder.entity(entClass);
    builder.tracker(64, 1, true);
    builder.egg(primary, seconday);

    return this.entity(identifier, builder);
  }

  default Supplier<EntityEntry> entity(String identifier, EntityEntry entity) {
    return this.entity(identifier, () -> entity);
  }

  default Supplier<EntityEntry> entity(String identifier, Supplier<EntityEntry> entity) {
    return this.addEntry(getWrapper().getEntities(), identifier, entity);
  }

  // endregion

  // region Effect

  default <T extends Potion & IEffectSettings> Supplier<T> effect(T effect) {
    return this.effect(effect.getSettings().getRegistryKey(), effect);
  }

  default <T extends Potion> Supplier<T> effect(String identifier, T effect) {
    return this.effect(identifier, (Supplier<T>) () -> effect);
  }


  default <T extends Potion> Supplier<T> effect(String identifier, Supplier<T> effect) {
    this.addEntry(getWrapper().getPotions(), identifier, (Supplier<Potion>) effect);
    return effect;
  }

  // endregion

  // region Potion

  default <T extends PotionType & IPotionSettings> Supplier<T> potion(T potion) {
    return this.potion(potion.getSettings().getRegistryKey(), potion);
  }

  default <T extends PotionType> Supplier<T> potion(String identifier, T potion) {
    return this.potion(identifier, (Supplier<T>) () -> potion);
  }


  default <T extends PotionType> Supplier<T> potion(String identifier, Supplier<T> potion) {
    this.addEntry(getWrapper().getPotionTypes(), identifier, (Supplier<PotionType>) potion);
    return potion;
  }

  // endregion

  // region Sound

  default <T extends SoundEvent & ISoundSettings> Supplier<SoundEvent> sound(T sound) {
    return this.sound(sound.getSettings().getRegistryKey(), sound);
  }

  default Supplier<SoundEvent> sound(String identifier) {
    return this.sound(identifier, new SoundEvent(getIdentifier(identifier)));
  }

  default Supplier<SoundEvent> sound(String identifier, SoundEvent sound) {
    return this.sound(identifier, () -> sound);
  }

  default Supplier<SoundEvent> sound(String identifier, Supplier<SoundEvent> sound) {
    return this.addEntry(getWrapper().getSounds(), identifier, sound);
  }

  // endregion

  // region Loot Table

  default ResourceLocation loot(String name) {

    return LootTableList.register(getIdentifier(name));
  }

  default <T extends LootFunction> Class<? extends T> loot(LootFunction.Serializer<? extends T> serializer) {

    LootFunctionManager.registerFunction(serializer);
    return serializer.getFunctionClass();
  }

  default LootBuilder loot(ResourceLocation location, String name, String pool, int weight, Item item, int meta, int amount) {

    return this.loot(location, name, pool, weight, item, meta, amount, amount);
  }

  default LootBuilder loot(ResourceLocation location, String name, String pool, int weight, Item item, int meta, int min, int max) {

    final LootBuilder loot = this.loot(location, name, pool, weight, item, meta);
    loot.addFunction(new SetCount(new LootCondition[0], new RandomValueRange(min, max)));
    return loot;
  }

  default LootBuilder loot(ResourceLocation location, String name, String pool, int weight, Item item, int meta) {

    final LootBuilder loot = this.loot(location, name, pool, weight, item);
    loot.addFunction(new SetMetadata(new LootCondition[0], new RandomValueRange(meta, meta)));
    return loot;
  }

  default LootBuilder loot(ResourceLocation location, String name, String pool, int weight, Item item) {

    return this.loot(location, new LootBuilder(getIdentifier(name).toString(), pool, weight, item));
  }

  default LootBuilder loot(ResourceLocation location, LootBuilder builder) {

//    this.getRegistry().getLootTable().put(location, builder);
    return builder;
  }

  default LootBuilder loot(ResourceLocation location, String name, String pool, int weight, int quality, Item item, List<LootCondition> conditions, List<LootFunction> functions) {

    return this.loot(location, new LootBuilder(getIdentifier(name).toString(), pool, weight, quality, item, conditions, functions));
  }

  // endregion

  // region Key Binding

  default KeyBinding keyBinding(String description, int keyCode) {

    final KeyBinding key = new KeyBinding(ModUtils.localize("key", description), keyCode, ModUtils.localize(description, "categories"));
    ClientRegistry.registerKeyBinding(key);
    return key;
  }

  // endregion


}
