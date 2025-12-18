package su.terrafirmagreg.framework.manager.content;

import su.terrafirmagreg.api.library.IStringLocalized;
import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.api.util.KeyBindUtils;
import su.terrafirmagreg.api.util.LootUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.manager.content.api.IContentEntry;
import su.terrafirmagreg.framework.manager.content.api.IContentRegistrar;
import su.terrafirmagreg.framework.manager.content.base.biome.api.IBiomeEntry;
import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry;
import su.terrafirmagreg.framework.manager.content.base.block.api.IBlockEntry.BlockSettings;
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
  public <V extends IContentEntry<?, ?>> void addContent(String identifier, V entry) {
    this.group = group != null ? group : BaseItemGroup.of(module.getIdentifier(), getIdentifier(identifier));
    entry.getSettings().registryKey(identifier);
    this.addContent(entry);
  }

  @Override
  public <V extends IContentEntry<?, ?>> void addContent(V entry) {

    addEntry(entry);
  }

  public <T extends Block & IBlockEntry> T addBlock(String name, Function<BlockSettings, T> factory) {
    return addBlock(name, factory.apply(BlockSettings.of()));
  }


  @Override
  public <V extends Block & IBlockEntry> V addBlock(String identifier, V entry) {
    var settings = entry.getSettings();

    this.addContent(identifier, entry);

    if (settings.getItemBlock() != null) {
      this.addItem(identifier, settings.getItemBlock().apply(entry));
    }
    if (settings.getStairsBlock() != null) {
      this.addBlock(identifier + "_stairs", settings.getStairsBlock().apply(entry));
    }
    if (settings.getWallBlock() != null) {
      this.addBlock(identifier + "_wall", settings.getWallBlock().apply(entry));
    }
    if (settings.getSlabDoubleBlock() != null) {
      this.addBlock(identifier + "_slab_double", settings.getSlabDoubleBlock().apply(entry));
      this.addBlock(identifier + "_slab", settings.getSlabSingleBlock().apply(entry));
    }

    return entry;
  }

  @Override
  public <V extends Block & IBlockEntry, T extends IStringLocalized> Map<T, V> addBlock(String identifier, Function<T, V> factory, Collection<T> collection) {
    return collection.stream().collect(Collectors.toMap(Function.identity(), type -> {

      var entry = factory.apply(type);

      entry.getSettings().translateKey(
        ModUtils.localize(getIdentifier(identifier)),
        type.getTranslationKey()
      );

      return this.addBlock(
        String.format("%s/%s", identifier, type),
        entry
      );
    }));

  }

  // endregion

  // region Item

  @Override
  public <V extends Item & IItemEntry> V addItem(String identifier, V entry) {

    group.addToTab(entry);

    this.addContent(identifier, entry);

    return entry;
  }

  @Override
  public <V extends Item & IItemEntry, T extends IStringLocalized> Map<T, V> addItem(String identifier, Function<T, V> factory, Collection<T> collection) {
    return collection.stream().collect(Collectors.toMap(Function.identity(), type -> {

      var entry = factory.apply(type);
      entry.getSettings().translateKey(
        ModUtils.localize(getIdentifier(identifier)),
        type.getTranslationKey()
      );

      return this.addItem(
        String.format("%s/%s", identifier, type),
        entry
      );
    }));

  }

  // endregion

  // region Biome


  @Override
  public <V extends Biome & IBiomeEntry> V addBiome(V entry) {

    this.addContent(entry);
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

    this.addContent(entry);
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
    this.addContent(entry);
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

    this.addContent(entry);
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

    this.addContent(entry);
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

    this.addContent(entry);
    return entry;
  }

  @Override
  public <V extends SoundEvent> SoundEvent addSound(String identifier) {

    var entry = new BaseSound(SoundSettings.of().name(getIdentifier(identifier)).registryKey(identifier));
    this.addContent(entry);
    return entry;
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
