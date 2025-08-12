package su.terrafirmagreg.framework.manager.registry;

import su.terrafirmagreg.api.library.types.type.Type;
import su.terrafirmagreg.api.util.KeyBindUtils;
import su.terrafirmagreg.api.util.LootUtils;
import su.terrafirmagreg.api.util.ModUtils;
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

  public <T extends IForgeRegistryEntry<T>> void addEntry(String registerKey, T entry) {

    var identifier = getIdentifier(registerKey);
    if (!identifier.equals(entry.getRegistryName())) {
      entry.setRegistryName(identifier);
    }
    this.map.addEntry(entry.getRegistryType(), entry);
  }

  // region Block

  @Override
  public <V extends Block> V addBlock(String identifier, V entry) {

    entry.setCreativeTab(group);
    addEntry(identifier, entry);
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

  public <V extends Block & IBlockEntry, T extends Type<T>> Map<T, V> addBlock(Function<T, V> factory, Set<T> types) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addBlock(factory.apply(type))));
  }

  // endregion

  // region Item

  @Override
  public <V extends Item> V addItem(String identifier, V entry) {

    entry.setCreativeTab(group);
    addEntry(identifier, entry);
    return entry;
  }

  @Override
  public <V extends Item & IItemEntry> V addItem(V entry) {

    var settings = entry.getSettings();

    return this.addItem(settings.getRegistryKey(), entry);
  }

  @Override
  public <V extends Item & IItemEntry, T extends Type<T>> Map<T, V> addItem(Function<T, V> factory, Set<T> types) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addItem(factory.apply(type))));
  }

  // endregion

  // region Biome


  @Override
  public <V extends Biome> V addBiome(String identifier, V entry) {

    addEntry(identifier, entry);
    return entry;
  }

  @Override
  public <V extends Biome & IBiomeEntry> V addBiome(V entry) {

    var settings = entry.getSettings();
    return this.addBiome(settings.getRegistryKey(), entry);
  }

  @Override
  public <V extends Biome & IBiomeEntry, T extends Type<T>> Map<T, V> addBiome(Function<T, V> factory, Set<T> types) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addBiome(factory.apply(type))));
  }

  // endregion

  // region Enchantment


  @Override
  public <V extends Enchantment> V addEnchantment(String identifier, V entry) {

    addEntry(identifier, entry);
    return entry;
  }

  @Override
  public <V extends Enchantment & IEnchantmentEntry> V addEnchantment(V entry) {

    var settings = entry.getSettings();
    return this.addEnchantment(settings.getRegistryKey(), entry);
  }

  @Override
  public <V extends Enchantment & IEnchantmentEntry, T extends Type<T>> Map<T, V> addEnchantment(Function<T, V> factory, Set<T> types) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addEnchantment(factory.apply(type))));
  }

  // endregion

  // region Effect


  @Override
  public <V extends Potion> V addEffect(String identifier, V entry) {

    addEntry(identifier, entry);
    return entry;
  }

  @Override
  public <V extends Potion & IEffectEntry> V addEffect(V entry) {

    var settings = entry.getSettings();
    return this.addEffect(settings.getRegistryKey(), entry);
  }

  @Override
  public <V extends Potion & IEffectEntry, T extends Type<T>> Map<T, V> addEffect(Function<T, V> factory, Set<T> types) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addEffect(factory.apply(type))));
  }

  // endregion

  // region Potion


  @Override
  public <V extends PotionType> V addPotion(String identifier, V entry) {

    addEntry(identifier, entry);
    return entry;
  }

  @Override
  public <V extends PotionType & IPotionEntry> V addPotion(V entry) {

    var settings = entry.getSettings();
    return this.addPotion(settings.getRegistryKey(), entry);
  }

  @Override
  public <V extends PotionType & IPotionEntry, T extends Type<T>> Map<T, V> addPotion(Function<T, V> factory, Set<T> types) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addPotion(factory.apply(type))));
  }

  // endregion

  // region Sound


  @Override
  public <V extends SoundEvent> V addSound(String identifier, V entry) {

    addEntry(identifier, entry);
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
  public <V extends SoundEvent & ISoundEntry, T extends Type<T>> Map<T, V> addSound(Function<T, V> factory, Set<T> types) {

    return types.stream().collect(Collectors.toMap(Function.identity(), type -> this.addSound(factory.apply(type))));
  }

  // endregion

  // region Entity


  @Override
  public <V extends EntityEntry> V addEntity(String identifier, V entry) {

    addEntry(identifier, entry);
    return entry;
  }

  @Override
  public <V extends EntityEntry & IEntityEntry> V addEntity(V entry) {

    var settings = entry.getSettings();

    this.addEntity(settings.getRegistryKey(), entry);
    return entry;
  }

  @Override
  public <V extends EntityEntry & IEntityEntry, T extends Type<T>> Map<T, V> addEntity(Function<T, V> factory, Set<T> types) {

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
