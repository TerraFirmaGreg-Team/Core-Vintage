package su.terrafirmagreg.framework.registry.api;

import su.terrafirmagreg.api.base.object.biome.api.IBiomeSettings;
import su.terrafirmagreg.api.base.object.block.api.IBlockSettings;
import su.terrafirmagreg.api.base.object.enchantment.api.IEnchantmentSettings;
import su.terrafirmagreg.api.base.object.entity.api.IEntitySettings;
import su.terrafirmagreg.api.base.object.group.spi.BaseItemGroup;
import su.terrafirmagreg.api.base.object.item.api.IItemSettings;
import su.terrafirmagreg.api.base.object.potion.api.IPotionSettings;
import su.terrafirmagreg.api.base.object.potiontype.api.IPotionTypeSettings;
import su.terrafirmagreg.api.base.object.sound.api.ISoundSettings;
import su.terrafirmagreg.api.library.IdSupplier;
import su.terrafirmagreg.api.library.collection.RegistrySupplierMap;
import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.api.library.types.variant.Variant;
import su.terrafirmagreg.api.library.types.variant.block.VariantBlock;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.module.api.IModule;
import su.terrafirmagreg.framework.registry.spi.RegistryWrapper;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.registries.IForgeRegistryEntry;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

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

  default <T extends Block & IBlockSettings> Supplier<Block> block(T block) {
    var settings = block.getSettings();

    if (settings.getItemBlock() != null) {
      settings.getGroups().add(getGroup());
      settings.getGroups().forEach(block::setCreativeTab);
      this.item(settings.getRegistryKey(), settings.getItemBlock().apply(block));
    }

    return this.block(settings.getRegistryKey(), block);
  }

  default <T extends Type<T>, B extends Block & IBlockSettings> Map<T, Supplier<Block>> block(Set<T> types, Function<T, B> factory) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.block(factory.apply(type))));
  }

  default <T extends Type<T>, V extends Variant<V, T>, B extends Block & IBlockSettings> Map<T, Supplier<Block>> block(Set<T> types, VariantBlock<V, T> variant, Function<T, B> factory) {

//    return variant.getMap().putAll(types.stream().collect(Collectors.toMap(Function.identity(), type -> this.block(factory.apply(type)))));
    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.block(factory.apply(type))));
  }

  default Supplier<Block> block(String identifier, Block block) {
    return this.block(identifier, () -> block);
  }

  default Supplier<Block> block(String identifier, Supplier<Block> block) {
    return this.addEntry(getWrapper().getBlocks(), identifier, block);
  }

  // endregion

  // region Item

  default <T extends Item & IItemSettings> Supplier<Item> item(T item) {
    var settings = item.getSettings();
    settings.getGroups().add(getGroup());
    settings.getGroups().forEach(item::setCreativeTab);
    return this.item(settings.getRegistryKey(), item);
  }

  default Supplier<Item> item(String identifier, Item item) {
    return this.item(identifier, () -> item);
  }

  default Supplier<Item> item(String identifier, Supplier<Item> item) {
    return this.addEntry(getWrapper().getItems(), identifier, item);
  }

  // endregion

  // region Biome

  default <T extends Biome & IBiomeSettings> Supplier<Biome> biome(T biome) {

    return this.biome(biome.getSettings().getRegistryKey(), biome);
  }

  default Supplier<Biome> biome(String identifier, Biome biome) {
    return this.biome(identifier, () -> biome);
  }

  default Supplier<Biome> biome(String identifier, Supplier<Biome> biome) {
    return this.addEntry(getWrapper().getBiomes(), identifier, biome);
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

  default <T extends Entity & IEntitySettings> Supplier<EntityEntry> entity(String identifier, EntityEntryBuilder<T> builder) {

    builder.id(getIdentifier(identifier), this.getIdSupplier().getAndIncrement());
    builder.name(ModUtils.localize(getIdentifier(identifier)));

    return this.entity(identifier, builder.build());
  }

  default Supplier<EntityEntry> entity(String identifier, EntityEntry entity) {
    return this.entity(identifier, () -> entity);
  }

  default Supplier<EntityEntry> entity(String identifier, Supplier<EntityEntry> entity) {
    return this.addEntry(getWrapper().getEntities(), identifier, entity);
  }

  // endregion

  // region Potion

  default <T extends Potion & IPotionSettings> Supplier<Potion> potion(T potion) {
    return this.potion(potion.getSettings().getRegistryKey(), potion);
  }

  default Supplier<Potion> potion(String identifier, Potion potion) {
    return this.potion(identifier, () -> potion);
  }

  default Supplier<Potion> potion(String identifier, Supplier<Potion> potion) {
    return this.addEntry(getWrapper().getPotions(), identifier, potion);
  }

  // endregion

  // region PotionType

  default <T extends PotionType & IPotionTypeSettings> Supplier<PotionType> potionType(T potionType) {
    return this.potionType(potionType.getSettings().getRegistryKey(), potionType);
  }

  default Supplier<PotionType> potionType(String identifier, PotionType potionType) {
    return this.potionType(identifier, () -> potionType);
  }

  default Supplier<PotionType> potionType(String identifier, Supplier<PotionType> potionType) {
    return this.addEntry(getWrapper().getPotionTypes(), identifier, potionType);
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


}
