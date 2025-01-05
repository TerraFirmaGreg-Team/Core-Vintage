package su.terrafirmagreg.framework.registry.spi;

import su.terrafirmagreg.api.library.collection.RegistrySupplierMap;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.registries.DataSerializerEntry;

import lombok.Getter;

@Getter
public class RegistryWrapper {

  private final RegistrySupplierMap<Block> blocks;
  private final RegistrySupplierMap<Item> items;
  private final RegistrySupplierMap<Potion> potions;
  private final RegistrySupplierMap<PotionType> potionTypes;
  private final RegistrySupplierMap<Biome> biomes;
  private final RegistrySupplierMap<SoundEvent> sounds;
  private final RegistrySupplierMap<Enchantment> enchantments;
  private final RegistrySupplierMap<VillagerProfession> professions;
  private final RegistrySupplierMap<EntityEntry> entities;
  private final RegistrySupplierMap<IRecipe> recipes;
  private final RegistrySupplierMap<DataSerializerEntry> dataSerializer;


  public RegistryWrapper() {
    this.blocks = RegistrySupplierMap.create();
    this.items = RegistrySupplierMap.create();
    this.potions = RegistrySupplierMap.create();
    this.potionTypes = RegistrySupplierMap.create();
    this.biomes = RegistrySupplierMap.create();
    this.sounds = RegistrySupplierMap.create();
    this.enchantments = RegistrySupplierMap.create();
    this.professions = RegistrySupplierMap.create();
    this.entities = RegistrySupplierMap.create();
    this.recipes = RegistrySupplierMap.create();
    this.dataSerializer = RegistrySupplierMap.create();
  }


}
