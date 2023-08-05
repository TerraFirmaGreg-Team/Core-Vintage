package net.dries007.tfc.test.proxy;

import net.dries007.tfc.api.util.IItemProvider;
import net.dries007.tfc.objects.te.*;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.function.Function;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.registries.TFCStorage.*;

@Mod.EventBusSubscriber(modid = MOD_ID)
public class CommonProxy {
    @SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> r = event.getRegistry();

        //=== Rock ===================================================================================================//

        for (var stoneTypeBlock : ROCK_BLOCKS.values()) {
            r.register((Block) stoneTypeBlock);
        }

        //=== Soil ===================================================================================================//

        for (var soilTypeBlock : SOIL_BLOCKS.values()) {
            r.register((Block) soilTypeBlock);
        }

        //=== Plant ==================================================================================================//

        for (var plantTypeBlock : PLANT_BLOCKS.values()) {
            r.register((Block) plantTypeBlock);
        }

        //=== Wood ===================================================================================================//

        for (var woodTypeBlock : WOOD_BLOCKS.values()) {
            r.register((Block) woodTypeBlock);
        }

        //=== Alabaster ==============================================================================================//

        for (var alabasterBlock : ALABASTER_BLOCK.values()) {
            r.register(alabasterBlock);
        }

        //=== Groundcover ============================================================================================//

        for (var groundcoverBlock : GROUNDCOVER_BLOCK.values()) {
            r.register(groundcoverBlock);
        }

        //=== Other ==================================================================================================//

        ITEM_BLOCKS.forEach(x -> r.register(x.getBlock()));


        //=== TileEntity =============================================================================================//
        // Помещение регистрации объекта тайла в соответствующий блок может вызывать его несколько раз. Просто поместите сюда, чтобы избежать дубликатов

        register(TETickCounter.class, "tick_counter");
        register(TEPlacedItem.class, "placed_item");
        register(TEPlacedItemFlat.class, "placed_item_flat");
        register(TEPlacedHide.class, "placed_hide");
        register(TEPitKiln.class, "pit_kiln");
        register(TEChestTFC.class, "chest");
        register(TENestBox.class, "nest_box");
        register(TELogPile.class, "log_pile");
        register(TEFirePit.class, "fire_pit");
        register(TEToolRack.class, "tool_rack");
        register(TELoom.class, "loom");
        register(TEBellows.class, "bellows");
        register(TEBarrel.class, "barrel");
        register(TECharcoalForge.class, "charcoal_forge");
        register(TEAnvilTFC.class, "anvil");
        register(TECrucible.class, "crucible");
        register(TECropBase.class, "crop_base");
        register(TECropSpreading.class, "crop_spreading");
        register(TEBlastFurnace.class, "blast_furnace");
        register(TEBloomery.class, "bloomery");
        register(TEBloom.class, "bloom");
        register(TEMetalSheet.class, "metal_sheet");
        register(TEQuern.class, "quern");
        register(TELargeVessel.class, "large_vessel");
        register(TEPowderKeg.class, "powderkeg");

    }


    @SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> r = event.getRegistry();

        //=== Rock ===================================================================================================//

        for (var stoneTypeBlock : ROCK_BLOCKS.values()) {
            var itemBlock = stoneTypeBlock.getItemBlock();
            if (itemBlock != null) registerItemBlock(r, itemBlock);
        }

        //=== Soil ===================================================================================================//

        for (var soilTypeBlock : SOIL_BLOCKS.values()) {
            var itemBlock = soilTypeBlock.getItemBlock();
            if (itemBlock != null) registerItemBlock(r, itemBlock);
        }

        //=== Plant ==================================================================================================//

        for (var plantTypeBlock : PLANT_BLOCKS.values()) {
            var itemBlock = plantTypeBlock.getItemBlock();
            if (itemBlock != null) registerItemBlock(r, itemBlock);
        }

        //=== Wood ===================================================================================================//

        for (var woodTypeBlock : WOOD_BLOCKS.values()) {
            var itemBlock = woodTypeBlock.getItemBlock();
            if (itemBlock != null) registerItemBlock(r, itemBlock);
        }

        //=== Alabaster ==============================================================================================//

        for (var alabasterBlock : ALABASTER_BLOCK.values()) {
            r.register(createItemBlock(alabasterBlock));
        }

        //=== Groundcover ==============================================================================================//

//		for (var groundcoverBlock : GROUNDCOVER_BLOCK.values()) {
//			r.register(createItemBlock(groundcoverBlock, ItemBlock::new));
//		}

        //=== Other ==================================================================================================//

        ITEM_BLOCKS.forEach(x -> registerItemBlock(r, x));


    }

    @SuppressWarnings("ConstantConditions")
    private static <T extends Block> ItemBlock createItemBlock(T block) {
        var itemBlock = ((IItemProvider) block).getItemBlock();
        var registryName = block.getRegistryName();
        if (registryName == null)
            throw new IllegalArgumentException("Block " + block.getTranslationKey() + " has no registry name.");

        itemBlock.setRegistryName(registryName);
        return itemBlock;
    }


    private static <T extends Block> ItemBlock createItemBlock(T block, Function<T, ItemBlock> producer) {
        var itemBlock = producer.apply(block);
        var registryName = block.getRegistryName();
        if (registryName == null)
            throw new IllegalArgumentException("Block " + block.getTranslationKey() + " has no registry name.");

        itemBlock.setRegistryName(registryName);
        return itemBlock;
    }

    @SuppressWarnings("ConstantConditions")
    private static void registerItemBlock(IForgeRegistry<Item> r, ItemBlock item) {
        item.setRegistryName(item.getBlock().getRegistryName());
        item.setCreativeTab(item.getBlock().getCreativeTab());
        r.register(item);
    }

    // Для регистрации одиночных блоков
    private static <T extends Block> T registerBlock(String name, T block, CreativeTabs ct) {
        block.setRegistryName(MOD_ID, name);
        block.setTranslationKey(MOD_ID + "." + name.replace('/', '.'));
        block.setCreativeTab(ct);
        return block;
    }

    // Для регистрации одиночных предметов
    private static <T extends Item> T registerItem(String name, T item, CreativeTabs ct) {
        item.setRegistryName(MOD_ID, name);
        item.setTranslationKey(MOD_ID + "." + name.replace('/', '.'));
        item.setCreativeTab(ct);
        return item;
    }

    // Для регистрации тайловых объектов
    private static <T extends TileEntity> void register(Class<T> te, String name) {
        TileEntity.register(MOD_ID + ":" + name, te);
    }

}
