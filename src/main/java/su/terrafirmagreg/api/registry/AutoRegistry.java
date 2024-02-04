package su.terrafirmagreg.api.registry;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import lombok.Getter;
import su.terrafirmagreg.api.module.ModuleManager;
import su.terrafirmagreg.api.objects.block.IColorfulBlock;
import su.terrafirmagreg.api.objects.item.IColorfulItem;
import su.terrafirmagreg.api.util.Helpers;
import su.terrafirmagreg.api.util.IHasModel;

/**
 * This is used to automatically register things from the registry helper. The hope is that by
 * registering the event while the owner is active, Forge will shut up about harmless registry
 * entries being dangerous.
 */
@Getter
public class AutoRegistry {

    /**
     * The registry helper to register things from.
     */
    private final Registry registry;

    public AutoRegistry(Registry registry) {

        this.registry = registry;
    }


    public void onRegisterBlock(RegistryEvent.Register<Block> event) {
        for (var block : this.registry.getBlocks()) {
            event.getRegistry().register(block);
        }
    }


    public void onRegisterItem(RegistryEvent.Register<Item> event) {
        for (var item : this.registry.getItems()) {
            event.getRegistry().register(item);
        }
    }


    public void onRegisterPotion(RegistryEvent.Register<Potion> event) {
        for (var potion : this.registry.getPotions()) {
            event.getRegistry().register(potion);
        }
    }


    public void onRegisterPotionType(RegistryEvent.Register<PotionType> event) {
        for (var potionType : this.registry.getPotionType()) {
            event.getRegistry().register(potionType);
        }
    }


    public void onRegisterBiome(RegistryEvent.Register<Biome> event) {
        for (var biome : this.registry.getBiomes()) {
            event.getRegistry().register(biome);
        }
    }


    public void onRegisterSound(RegistryEvent.Register<SoundEvent> event) {
        for (var sound : this.registry.getSounds()) {
            event.getRegistry().register(sound);
        }
    }


    public void onRegisterEntity(RegistryEvent.Register<EntityEntry> event) {
        for (var entry : this.registry.getEntities()) {
            event.getRegistry().register(entry.build());
        }
    }


    public void onRegisterEnchantment(RegistryEvent.Register<Enchantment> event) {
        for (var enchant : this.registry.getEnchantments()) {
            event.getRegistry().register(enchant);
        }
    }


    public void onRegisterVillagerProfession(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
        // TODO: register villager profession event
    }


    public void onRegisterRecipes(RegistryEvent.Register<IRecipe> event) {
        for (var recipe : this.registry.getRecipes()) {
            event.getRegistry().register(recipe);
        }
    }


    public void onRegisterTileEntities() {
        for (var provider : this.registry.getTileProviders()) {
            GameRegistry.registerTileEntity(provider.getTileEntityClass(), Helpers.getID("tile." + provider.getTileEntityClass().getSimpleName()));
        }
    }


    public void onTableLoaded(LootTableLoadEvent event) {
        for (var builder : this.registry.getLootTableEntries().get(event.getName())) {
            var pool = event.getTable().getPool(builder.getPool());
            if (pool != null) pool.addEntry(builder.build());
            else ModuleManager.LOGGER.info("The mod {} tried to add loot to {} but the pool was not found. {}", this.registry.getModid(), event.getName(), builder.toString());
        }
    }


    // --------------------------------------------------------------------------
    // - Client
    // --------------------------------------------------------------------------


    @SideOnly(Side.CLIENT)
    public void onRegisterModels(ModelRegistryEvent event) {
        for (Block block : this.registry.getBlocks()) {
            this.registry.registerInventoryModel(block);
        }

        for (var item : this.registry.getItems()) {
            this.registry.registerInventoryModel(item);
        }

        for (var model : this.registry.getModels()) {
            model.onModelRegister();
        }
    }

    @SideOnly(Side.CLIENT)
    public void onRegisterTileEntitySpecialRenderer() {
        for (var provider : this.registry.getTileProviders()) {
            TileEntitySpecialRenderer tesr = provider.getTileRenderer();

            if (tesr != null) ClientRegistry.bindTileEntitySpecialRenderer(provider.getTileEntityClass(), tesr);
        }
    }

    @SideOnly(Side.CLIENT)
    public void onRegisterBlockColor(ColorHandlerEvent.Block event) {
        for (var block : this.registry.getColoredBlocks()) {
            event.getBlockColors().registerBlockColorHandler(((IColorfulBlock) block).getColorHandler(), block);
        }
    }

    @SideOnly(Side.CLIENT)
    public void onRegisterItemColor(ColorHandlerEvent.Item event) {
        for (var block : this.registry.getColoredBlocks()) {
            var colorfulBlock = (IColorfulBlock) block;
            if (colorfulBlock.getItemColorHandler() != null) {
                event.getItemColors().registerItemColorHandler(colorfulBlock.getItemColorHandler(), Item.getItemFromBlock(block));
            }
        }

        for (var item : this.registry.getColoredItems()) {
            event.getItemColors().registerItemColorHandler(((IColorfulItem) item).getColorHandler(), item);
        }
    }
}
