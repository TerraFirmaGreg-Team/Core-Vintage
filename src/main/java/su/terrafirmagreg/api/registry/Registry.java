package su.terrafirmagreg.api.registry;

import su.terrafirmagreg.api.base.biome.BaseBiome;
import su.terrafirmagreg.api.registry.builder.LootBuilder;
import su.terrafirmagreg.api.registry.provider.IProviderBlockColor;
import su.terrafirmagreg.api.registry.provider.IProviderBlockState;
import su.terrafirmagreg.api.registry.provider.IProviderItemColor;
import su.terrafirmagreg.api.registry.provider.IProviderItemMesh;
import su.terrafirmagreg.api.registry.provider.IProviderModel;
import su.terrafirmagreg.api.registry.provider.IProviderOreDict;
import su.terrafirmagreg.api.registry.provider.IProviderTile;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.data.lib.collection.RegistryList;
import su.terrafirmagreg.data.lib.model.CustomModelLoader;
import su.terrafirmagreg.modules.core.object.command.CommandManager;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.ICommand;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.VillagerRegistry.VillagerProfession;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.DataSerializerEntry;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import lombok.Getter;

@Getter
public class Registry {

  private final RegistryManager manager;

  /**
   * A list of all blocks registered by the helper.
   */
  private final RegistryList<Block> blocks;

  /**
   * A list of all items registered by the helper.
   */
  private final RegistryList<Item> items;

  /**
   * A list of all potions registered by the helper.
   */
  private final RegistryList<Potion> potions;

  /**
   * A list of all potion type registered by the helper.
   */
  private final RegistryList<PotionType> potionTypes;

  /**
   * A list of all biomes registered by the helper.
   */
  private final RegistryList<Biome> biomes;

  /**
   * A list of all the sounds registered by the helper.
   */
  private final RegistryList<SoundEvent> sounds;

  /**
   * A list of all enchantments registered.
   */
  private final RegistryList<Enchantment> enchantments;

  /**
   * A list of all professions registered by the helper.
   */
  private final RegistryList<VillagerProfession> professions;

  /**
   * A list of all entities registered by the helper.
   */
  private final RegistryList<EntityEntry> entities;

  /**
   * A list of all recipes registered by the helper.
   */
  private final RegistryList<IRecipe> recipes;

  /**
   * A list of all recipes registered by the helper.
   */
  private final RegistryList<DataSerializerEntry> dataSerializerEntries;

  /**
   * A list of all the keyBinding registered by the helper.
   */
  private final NonNullList<KeyBinding> keyBinding;

  /**
   * A list of all the commands registered here.
   */
  private final NonNullList<ICommand> commands;

  /**
   * A local map of all the entries that have been added. This is on a per instance basis, used to get mod-specific entries.
   */
  private final Multimap<ResourceLocation, LootBuilder> lootTable;

  public Registry(RegistryManager manager) {
    this.manager = manager;

    this.blocks = RegistryList.create();
    this.items = RegistryList.create();
    this.potions = RegistryList.create();
    this.potionTypes = RegistryList.create();
    this.biomes = RegistryList.create();
    this.sounds = RegistryList.create();
    this.enchantments = RegistryList.create();
    this.professions = RegistryList.create();
    this.entities = RegistryList.create();
    this.recipes = RegistryList.create();
    this.dataSerializerEntries = RegistryList.create();

    this.keyBinding = NonNullList.create();
    this.commands = NonNullList.create();
    this.lootTable = HashMultimap.create();

  }

  public void onRegisterBlock(RegistryEvent.Register<Block> event) {

    this.blocks.register(event);
  }

  public void onRegisterTileEntities() {

    blocks.forEach(block -> {
      if (block instanceof IProviderTile provider) {
        TileUtils.registerTileEntity(provider.getTileClass(), provider.getTileClass().getSimpleName());
      }
    });
  }

  public void onRegisterItem(RegistryEvent.Register<Item> event) {

    this.items.register(event);
  }

  public void onRegisterPotion(RegistryEvent.Register<Potion> event) {

    this.potions.register(event);
  }

  public void onRegisterPotionType(RegistryEvent.Register<PotionType> event) {

    this.potionTypes.register(event);
  }

  public void onRegisterBiome(RegistryEvent.Register<Biome> event) {

    this.biomes.register(event);

    this.biomes.forEach(biome -> {
      if (biome instanceof BaseBiome provider) {
        if (provider.getTypes().length > 0) {
          BiomeDictionary.addTypes(biome, provider.getTypes());
        }
      }
    });

  }

  public void onRegisterSound(RegistryEvent.Register<SoundEvent> event) {

    this.sounds.register(event);
  }

  public void onRegisterEntity(RegistryEvent.Register<EntityEntry> event) {

    this.entities.register(event);
  }

  public void onRegisterEnchantment(RegistryEvent.Register<Enchantment> event) {

    this.enchantments.register(event);
  }

  public void onRegisterVillagerProfession(RegistryEvent.Register<VillagerProfession> event) {

    this.professions.register(event);
  }

  public void onRegisterRecipe(RegistryEvent.Register<IRecipe> event) {

    this.recipes.register(event);
  }

  public void onRegisterDataSerializerEntry(RegistryEvent.Register<DataSerializerEntry> event) {

    this.dataSerializerEntries.register(event);
  }

  public void onRegisterLootTableLoad(LootTableLoadEvent event) {

    this.lootTable.get(event.getName())
                  .forEach(builder -> event.getTable()
                                           .getPool(builder.getPool())
                                           .addEntry(builder.build()));
  }

  public void onRegisterCommand(FMLServerStartingEvent event) {
    var manager = CommandManager.create(event);
    this.commands.forEach(manager::addCommand);
  }

  public void onRegisterOreDict() {

    this.blocks.forEach(block -> {
      if (block instanceof IProviderOreDict provider) {
        provider.onRegisterOreDict();
      }
    });

    this.items.forEach(item -> {
      if (item instanceof IProviderOreDict provider) {
        provider.onRegisterOreDict();
      }
    });

    OreDictUtils.init();
  }

  // --------------------------------------------------------------------------
  // - Client
  // --------------------------------------------------------------------------

  @SideOnly(Side.CLIENT)
  public void onRegisterModels(ModelRegistryEvent event) {
    ModelLoaderRegistry.registerLoader(new CustomModelLoader());

    onRegisterModelsBlock();
    onRegisterModelsItem();
    onRegisterModelsTile();
  }

  public void onRegisterModelsBlock() {

    this.blocks.forEach(block -> {
      if (block instanceof IProviderModel provider) {
        if (provider.getResourceLocation() != null) {
          ModelUtils.registerBlockInventoryModel(block, provider.getResourceLocation());
        }
      }
      //            else {
      //                ModelUtils.registerBlockInventoryModel(block);
      //            }

      if (block instanceof IProviderBlockState provider) {
        ModelUtils.registerStateMapper(block, provider.getStateMapper());
      }
    });
  }

  public void onRegisterModelsItem() {

    this.items.forEach(item -> {
      if (item instanceof IProviderModel provider) {
        if (provider.getResourceLocation() != null) {
          ModelUtils.registerInventoryModel(item, provider.getResourceLocation());
        } else if (!(item instanceof ItemBlock)) {
          ModelUtils.registerInventoryModel(item);
        }
      }

      if (item instanceof IProviderItemMesh provider) {
        ModelUtils.registerCustomMeshDefinition(item, provider.getItemMesh());
      }
    });
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  public void onRegisterModelsTile() {

    this.blocks.forEach(block -> {
      if (block instanceof IProviderTile provider) {
        final TileEntitySpecialRenderer tesr = provider.getTileRenderer();

        ModelUtils.registerTileEntitySpecialRenderer(provider.getTileClass(), tesr);
      }
    });
  }


  @SideOnly(Side.CLIENT)
  public void onRegisterBlockColor(ColorHandlerEvent.Block event) {

    this.blocks.forEach(block -> {
      if (block instanceof IProviderBlockColor provider) {
        if (provider.getBlockColor() != null) {
          event.getBlockColors().registerBlockColorHandler(provider.getBlockColor(), block);
        }
      }
    });
  }

  @SideOnly(Side.CLIENT)
  public void onRegisterItemColor(ColorHandlerEvent.Item event) {

    this.blocks.forEach(block -> {
      if (block instanceof IProviderBlockColor provider) {
        if (provider.getItemColor() != null) {
          event.getItemColors().registerItemColorHandler(provider.getItemColor(), Item.getItemFromBlock(block));
        }
      }
    });

    this.items.forEach(item -> {
      if (item instanceof IProviderItemColor provider) {
        if (provider.getItemColor() != null) {
          event.getItemColors().registerItemColorHandler(provider.getItemColor(), item);
        }
      }
    });
  }
}
