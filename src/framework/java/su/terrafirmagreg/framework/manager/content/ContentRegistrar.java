package su.terrafirmagreg.framework.manager.content;

import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.api.util.KeyBindUtils;
import su.terrafirmagreg.api.util.LootUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.content.api.IContentEntry;
import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;
import su.terrafirmagreg.framework.manager.content.base.biome.api.IBiomeEntry;
import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry;
import su.terrafirmagreg.framework.manager.content.base.effect.api.IEffectEntry;
import su.terrafirmagreg.framework.manager.content.base.enchantment.api.IEnchantmentEntry;
import su.terrafirmagreg.framework.manager.content.base.entity.api.IEntityEntry;
import su.terrafirmagreg.framework.manager.content.base.group.spi.BaseItemGroup;
import su.terrafirmagreg.framework.manager.content.base.item.api.IItemEntry;
import su.terrafirmagreg.framework.manager.content.base.potion.api.IPotionEntry;
import su.terrafirmagreg.framework.manager.content.base.sound.api.ISoundEntry;
import su.terrafirmagreg.framework.manager.content.base.sound.api.ISoundEntry.SoundSettings;
import su.terrafirmagreg.framework.manager.content.base.sound.spi.BaseSound;
import su.terrafirmagreg.framework.module.api.IModuleEntry;

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

import com.google.common.collect.Multimap;

import lombok.Getter;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public class ContentRegistrar implements IContentRegistrar {

  private final IModuleEntry module;
  private final Multimap<Class<?>, IContentEntry<?, ?>> mapEntry;

  private BaseItemGroup group;

  public ContentRegistrar(ContentManager manager) {

    this.module = manager.getModule();
    this.mapEntry = manager.getMapEntry();
  }

  // Предмет должен быть в рамках модуля
  @Override
  public BaseItemGroup group(String icon) {
    this.group = BaseItemGroup.of(module.getIdentifier(), getIdentifier(icon));
    return this.group;
  }

  // region Block

  @Override
  public <V extends IContentEntry<?, ?>> V addContent(V entry) {
    addEntry(entry);

    return entry;
  }

  @Override
  public <V extends Block & IBlockEntry> V addBlock(String identifier, V entry) {
    var settings = entry.getSettings().registryKey(identifier).group(group);

    this.addEntry(entry);

    if (settings.getItemBlock() != null) {
      this.addItem(settings.getRegistryKey(), settings.getItemBlock().apply(entry));
    }
    if (settings.getStairsBlock() != null) {
      this.addBlock(settings.getRegistryKey() + "_stairs", settings.getStairsBlock().apply(entry));
    }
    if (settings.getWallBlock() != null) {
      this.addBlock(settings.getRegistryKey() + "_wall", settings.getWallBlock().apply(entry));
    }
    if (settings.getSlabDoubleBlock() != null) {
      this.addBlock(settings.getRegistryKey() + "_slab_double", settings.getSlabDoubleBlock().apply(entry));
      this.addBlock(settings.getRegistryKey() + "_slab", settings.getSlabSingleBlock().apply(entry));
    }

    return entry;
  }

  @Override
  public <V extends Block & IBlockEntry, T> Map<T, V> addBlock(String identifier, Function<T, V> factory, Collection<T> types) {
    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addBlock(String.format("%s/%s", identifier, type), factory.apply(type))));
  }

  @Override
  public <V extends Block & IBlockEntry> V addBlock(V entry) {

    var settings = entry.getSettings();

    this.addBlock(settings.getRegistryKey(), entry);

    return entry;
  }

  @Override
  public <V extends Block & IBlockEntry, T> Map<T, V> addBlock(Function<T, V> factory, Collection<T> types) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addBlock(factory.apply(type))));
  }

  // endregion

  // region Item


  public <V extends Item & IItemEntry> V addItem(String identifier, V entry) {
    var settings = entry.getSettings().registryKey(identifier).group(group);

    this.addEntry(entry);

    return entry;
  }

  @Override
  public <V extends Item & IItemEntry> V addItem(V entry) {

    var settings = entry.getSettings();

    this.addItem(settings.getRegistryKey(), entry);
    return entry;
  }

  @Override
  public <V extends Item & IItemEntry, T extends Type<T>> Map<T, V> addItem(Function<T, V> factory, Set<T> types) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addItem(factory.apply(type))));
  }

  // endregion

  // region Biome


  @Override
  public <V extends Biome & IBiomeEntry> V addBiome(V entry) {

    this.addEntry(entry);
    return entry;
  }

  @Override
  public <V extends Biome & IBiomeEntry, T extends Type<T>> Map<T, V> addBiome(Function<T, V> factory, Set<T> types) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addBiome(factory.apply(type))));
  }

  // endregion

  // region Enchantment

  @Override
  public <V extends Enchantment & IEnchantmentEntry> V addEnchantment(V entry) {

    this.addEntry(entry);
    return entry;
  }

  @Override
  public <V extends Enchantment & IEnchantmentEntry, T extends Type<T>> Map<T, V> addEnchantment(Function<T, V> factory, Set<T> types) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addEnchantment(factory.apply(type))));
  }

  // endregion

  // region Effect


  @Override
  public <V extends Potion & IEffectEntry> V addEffect(V entry) {
    this.addEntry(entry);
    return entry;
  }

  @Override
  public <V extends Potion & IEffectEntry, T extends Type<T>> Map<T, V> addEffect(Function<T, V> factory, Set<T> types) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addEffect(factory.apply(type))));
  }

  // endregion

  // region Potion

  @Override
  public <V extends PotionType & IPotionEntry> V addPotion(V entry) {

    this.addEntry(entry);
    return entry;
  }

  @Override
  public <V extends PotionType & IPotionEntry, T extends Type<T>> Map<T, V> addPotion(Function<T, V> factory, Set<T> types) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addPotion(factory.apply(type))));
  }

  // endregion

  // region Entity

  @Override
  public <V extends EntityEntry & IEntityEntry> V addEntity(V entry) {

    this.addEntry(entry);
    return entry;
  }

  @Override
  public <V extends EntityEntry & IEntityEntry, T extends Type<T>> Map<T, V> addEntity(Function<T, V> factory, Set<T> types) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addEntity(factory.apply(type))));
  }

  // endregion

  // region Sound

  @Override
  public <V extends SoundEvent & ISoundEntry> V addSound(V entry) {

    this.addEntry(entry);
    return entry;
  }

  @Override
  public <V extends SoundEvent> SoundEvent addSound(String identifier) {

    var soundEvent = new BaseSound(SoundSettings.of().name(getIdentifier(identifier)).registryKey(identifier));
    this.addEntry(soundEvent);
    return soundEvent;
  }

  @Override
  public <V extends SoundEvent & ISoundEntry, T extends Type<T>> Map<T, V> addSound(Function<T, V> factory, Set<T> types) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addSound(factory.apply(type))));
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
