package su.terrafirmagreg.api.registry;

import su.terrafirmagreg.api.client.model.CustomModelLoader;
import su.terrafirmagreg.api.registry.provider.IBlockColorProvider;
import su.terrafirmagreg.api.registry.provider.IBlockStateProvider;
import su.terrafirmagreg.api.registry.provider.IItemColorProvider;
import su.terrafirmagreg.api.registry.provider.IItemMeshProvider;
import su.terrafirmagreg.api.registry.provider.IModelProvider;
import su.terrafirmagreg.api.registry.provider.IOreDictProvider;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.objects.command.CommandManager;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
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
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import com.github.bsideup.jabel.Desugar;

/**
 * This is used to automatically register things from the registry helper. The hope is that by registering the event while the owner is active, Forge will shut up about harmless
 * registry entries being dangerous.
 *
 * @param registryManager The registry helper to register things from.
 */
@Desugar
public record Registry(RegistryManager registryManager) {

    public void onRegisterBlock(RegistryEvent.Register<Block> event) {
        for (var block : this.registryManager.getBlocks()) {
            event.getRegistry().register(block);
        }

        this.onRegisterTileEntities();
    }

    public void onRegisterItem(RegistryEvent.Register<Item> event) {
        for (var item : this.registryManager.getItems()) {
            event.getRegistry().register(item);
        }
    }

    public void onRegisterPotion(RegistryEvent.Register<Potion> event) {
        for (var potion : this.registryManager.getPotions()) {
            event.getRegistry().register(potion);
        }
    }

    public void onRegisterPotionType(RegistryEvent.Register<PotionType> event) {
        for (var potionType : this.registryManager.getPotionType()) {
            event.getRegistry().register(potionType);
        }
    }

    public void onRegisterBiome(RegistryEvent.Register<Biome> event) {
        for (var biome : this.registryManager.getBiomes()) {
            event.getRegistry().register(biome);

            if (biome.getTypes().length > 0) {
                BiomeDictionary.addTypes(biome, biome.getTypes());
            }
        }
    }

    public void onRegisterSound(RegistryEvent.Register<SoundEvent> event) {
        for (var sound : this.registryManager.getSounds()) {
            event.getRegistry().register(sound);
        }
    }

    public void onRegisterEntity(RegistryEvent.Register<EntityEntry> event) {
        for (var entity : this.registryManager.getEntities()) {
            event.getRegistry().register(entity);
        }
    }

    public void onRegisterEnchantment(RegistryEvent.Register<Enchantment> event) {
        for (var enchant : this.registryManager.getEnchantments()) {
            event.getRegistry().register(enchant);
        }
    }

    public void onRegisterVillagerProfession(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
        // TODO: register villager profession event
    }

    public void onRegisterTileEntities() {
        for (var provider : this.registryManager.getTiles()) {
            TileUtils.registerTileEntity(provider.getTileEntityClass(), provider.getTileEntityClass().getSimpleName());
        }
    }

    public void onRegisterOreDict() {
        for (var block : this.registryManager.getBlocks()) {
            if (block instanceof IOreDictProvider provider) {
                provider.onRegisterOreDict();
            }
        }
        for (var item : this.registryManager.getItems()) {
            if (item instanceof IOreDictProvider provider) {
                provider.onRegisterOreDict();
            }
        }
        OreDictUtils.init();
    }

    public void onRegisterLootTableLoad(LootTableLoadEvent event) {
        for (var builder : this.registryManager.getLootTableEntries().get(event.getName())) {
            var pool = event.getTable().getPool(builder.getPool());
            pool.addEntry(builder.build());
        }
    }

    public void onRegisterCommand(FMLServerStartingEvent event) {
        var manager = CommandManager.create(event);
        for (var command : this.registryManager.getCommands()) {
            manager.addCommand(command);
        }
    }

    // --------------------------------------------------------------------------
    // - Client
    // --------------------------------------------------------------------------

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void onRegisterModels(ModelRegistryEvent event) {
        ModelLoaderRegistry.registerLoader(new CustomModelLoader());

        for (var block : this.registryManager.getBlocks()) {

            if (block instanceof IModelProvider provider) {
                ModelUtils.registerBlockInventoryModel(block, provider.getResourceLocation());
            } else {
                ModelUtils.registerBlockInventoryModel(block);
            }

            if (block instanceof IBlockStateProvider provider) {
                ModelUtils.registerStateMapper(block, provider.getStateMapper());
            }
        }

        for (var item : this.registryManager.getItems()) {

            if (item instanceof IModelProvider provider) {
                ModelUtils.registerInventoryModel(item, provider.getResourceLocation());
            } else if (!(item instanceof ItemBlock)) {
                ModelUtils.registerInventoryModel(item);
            }

            if (item instanceof IItemMeshProvider provider) {
                ModelUtils.registerCustomMeshDefinition(item, provider.getItemMesh());
            }
        }

        for (var provider : this.registryManager.getTiles()) {
            final TileEntitySpecialRenderer tesr = provider.getTileRenderer();

            ModelUtils.registerTileEntitySpecialRenderer(provider.getTileEntityClass(), tesr);
        }
    }

    @SideOnly(Side.CLIENT)
    public void onRegisterBlockColor(ColorHandlerEvent.Block event) {
        for (var block : this.registryManager.getBlocks()) {
            if (block instanceof IBlockColorProvider provider) {
                event.getBlockColors().registerBlockColorHandler(provider.getBlockColor(), block);
            }

        }
    }

    @SideOnly(Side.CLIENT)
    public void onRegisterItemColor(ColorHandlerEvent.Item event) {
        for (var block : this.registryManager.getBlocks()) {
            if (block instanceof IBlockColorProvider provider) {
                if (provider.getItemColor() != null) {
                    event.getItemColors().registerItemColorHandler(provider.getItemColor(), Item.getItemFromBlock(block));
                }
            }
        }

        for (var item : this.registryManager.getItems()) {
            if (item instanceof IItemColorProvider provider) {
                event.getItemColors().registerItemColorHandler(provider.getItemColor(), item);
            }
        }
    }
}
