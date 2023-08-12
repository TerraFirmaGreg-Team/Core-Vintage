package net.dries007.tfc.common;

import gregtech.api.unification.material.event.MaterialEvent;
import gregtech.api.unification.material.properties.PropertyKey;
import gregtech.loaders.recipe.handlers.OreRecipeHandler;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.TerraFirmaCraft;

import net.dries007.tfc.api.capability.damage.CapabilityDamageResistance;
import net.dries007.tfc.api.capability.egg.CapabilityEgg;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.metal.CapabilityMetalItem;
import net.dries007.tfc.api.capability.player.CapabilityPlayerData;
import net.dries007.tfc.api.capability.size.CapabilityItemSize;
import net.dries007.tfc.api.capability.worldtracker.CapabilityWorldTracker;
import net.dries007.tfc.api.types.plant.type.PlantTypeHandler;
import net.dries007.tfc.api.types.rock.category.RockCategoryHandler;
import net.dries007.tfc.api.types.rock.type.RockTypeHandler;
import net.dries007.tfc.api.types.rock.variant.RockBlockVariantHandler;
import net.dries007.tfc.api.types.soil.type.SoilTypeHandler;
import net.dries007.tfc.api.types.soil.variant.SoilBlockVariantHandler;
import net.dries007.tfc.api.types.wood.type.WoodTypeHandler;
import net.dries007.tfc.api.types.wood.variant.WoodBlockVariantHandler;
import net.dries007.tfc.api.util.IItemProvider;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.compat.gregtech.items.tools.TFGToolItems;
import net.dries007.tfc.compat.gregtech.material.TFGMaterialHandler;
import net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefix;
import net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefixHandler;
import net.dries007.tfc.compat.gregtech.stonetypes.StoneTypeHandler;
import net.dries007.tfc.compat.top.TOPIntegration;
import net.dries007.tfc.network.*;
import net.dries007.tfc.objects.blocks.BlockIceTFC;
import net.dries007.tfc.objects.blocks.BlockSnowTFC;
import net.dries007.tfc.objects.blocks.BlockTorchTFC;
import net.dries007.tfc.objects.blocks.TFCBlocks;
import net.dries007.tfc.objects.blocks.fluid.BlockFluidHotWater;
import net.dries007.tfc.objects.blocks.fluid.BlockFluidWater;
import net.dries007.tfc.objects.entity.EntitiesTFC;
import net.dries007.tfc.objects.items.TFCItems;
import net.dries007.tfc.objects.te.*;
import net.dries007.tfc.util.WrongSideException;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.fuel.FuelManager;
import net.dries007.tfc.util.json.JsonConfigRegistry;
import net.dries007.tfc.world.classic.chunkdata.CapabilityChunkData;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Function;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.registries.TFCStorage.*;
import static net.dries007.tfc.api.registries.TFCStorage.ITEM;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = TerraFirmaCraft.MOD_ID)
public class CommonProxy {

    public void onPreInit(FMLPreInitializationEvent event) {
        TFCBlocks.preInit();
        TFCItems.preInit();
        TFGToolItems.preInit();

        JsonConfigRegistry.INSTANCE.preInit(event.getModConfigurationDirectory());

        // No need to sync config here, forge magic
        NetworkRegistry.INSTANCE.registerGuiHandler(TerraFirmaCraft.getInstance(), new TFCGuiHandler());

        // Received on server
        TerraFirmaCraft.registerNetwork(new PacketGuiButton.Handler(), PacketGuiButton.class, Side.SERVER);
        TerraFirmaCraft.registerNetwork(new PacketPlaceBlockSpecial.Handler(), PacketPlaceBlockSpecial.class, Side.SERVER);
        TerraFirmaCraft.registerNetwork(new PacketSwitchPlayerInventoryTab.Handler(), PacketSwitchPlayerInventoryTab.class, Side.SERVER);
        TerraFirmaCraft.registerNetwork(new PacketOpenCraftingGui.Handler(), PacketOpenCraftingGui.class, Side.SERVER);
        TerraFirmaCraft.registerNetwork(new PacketCycleItemMode.Handler(), PacketCycleItemMode.class, Side.SERVER);
        TerraFirmaCraft.registerNetwork(new PacketStackFood.Handler(), PacketStackFood.class, Side.SERVER);

        // Received on client
        TerraFirmaCraft.registerNetwork(new PacketChunkData.Handler(), PacketChunkData.class, Side.CLIENT);
        TerraFirmaCraft.registerNetwork(new PacketCapabilityContainerUpdate.Handler(), PacketCapabilityContainerUpdate.class, Side.CLIENT);
        TerraFirmaCraft.registerNetwork(new PacketCalendarUpdate.Handler(), PacketCalendarUpdate.class, Side.CLIENT);
        TerraFirmaCraft.registerNetwork(new PacketFoodStatsUpdate.Handler(), PacketFoodStatsUpdate.class, Side.CLIENT);
        TerraFirmaCraft.registerNetwork(new PacketFoodStatsReplace.Handler(), PacketFoodStatsReplace.class, Side.CLIENT);
        TerraFirmaCraft.registerNetwork(new PacketPlayerDataUpdate.Handler(), PacketPlayerDataUpdate.class, Side.CLIENT);
        TerraFirmaCraft.registerNetwork(new PacketSpawnTFCParticle.Handler(), PacketSpawnTFCParticle.class, Side.CLIENT);
        TerraFirmaCraft.registerNetwork(new PacketSimpleMessage.Handler(), PacketSimpleMessage.class, Side.CLIENT);
        TerraFirmaCraft.registerNetwork(new PacketProspectResult.Handler(), PacketProspectResult.class, Side.CLIENT);

        EntitiesTFC.preInit();

        CapabilityChunkData.preInit();
        CapabilityItemSize.preInit();
        CapabilityItemHeat.preInit();
        CapabilityForgeable.preInit();
        CapabilityFood.preInit();
        CapabilityEgg.preInit();
        CapabilityPlayerData.preInit();
        CapabilityDamageResistance.preInit();
        CapabilityMetalItem.preInit();
        CapabilityWorldTracker.preInit();

        TOPIntegration.onPreInit();
    }

    public void onInit(FMLInitializationEvent event) {

    }

    public void onPostInit(FMLPostInitializationEvent event) {
        FuelManager.postInit();
        JsonConfigRegistry.INSTANCE.postInit();
    }

    @SubscribeEvent
    public static void onMaterialsInit(MaterialEvent event) {
        TFGMaterialHandler.init();
        TFGOrePrefixHandler.init();

        RockCategoryHandler.init();
        RockTypeHandler.init();
        RockBlockVariantHandler.init();

        SoilTypeHandler.init();
        SoilBlockVariantHandler.init();

        PlantTypeHandler.init();

        WoodTypeHandler.init();
        WoodBlockVariantHandler.init();

        StoneTypeHandler.init();
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        TFGOrePrefix.oreChunk.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);

        TFGOrePrefix.oreQuartzite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreChalk.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreChert.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreClaystone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreConglomerate.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreDacite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreDolomite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreGabbro.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreGneiss.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreLimestone.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.orePhyllite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreRhyolite.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreSchist.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreShale.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
        TFGOrePrefix.oreSlate.addProcessingHandler(PropertyKey.ORE, OreRecipeHandler::processOre);
    }

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

        //=== Metal ==================================================================================================//

        for (var metalTypeBlock : METAL_BLOCKS.values()) {
            r.register((Block) metalTypeBlock);
        }

        //=== Alabaster ==============================================================================================//

        for (var alabasterBlock : ALABASTER_BLOCKS.values()) {
            r.register(alabasterBlock);
        }

        //=== Groundcover ============================================================================================//

        for (var groundcoverBlock : GROUNDCOVER_BLOCKS.values()) {
            r.register(groundcoverBlock);
        }

        //=== Other ==================================================================================================//

        ITEM_BLOCKS.forEach(x -> r.register(x.getBlock()));
        BLOCKS.forEach(r::register);
        FLUID.forEach(r::register);


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

        for (var rockBlock : ROCK_BLOCKS.values()) {
            var itemBlock = rockBlock.getItemBlock();
            if (itemBlock != null) registerItemBlock(r, itemBlock);
        }

        for (var rockItem : ROCK_ITEMS.values()) r.register(rockItem);
        for (var brickItem : BRICK_ITEMS.values()) r.register(brickItem);

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

        for (var lumberItem : LUMBER_ITEMS.values()) r.register(lumberItem);
        for (var boatItem : BOAT_ITEMS.values()) r.register(boatItem);

        //=== Metal ==================================================================================================//

        for (var metalTypeBlock : METAL_BLOCKS.values()) {
            var itemBlock = metalTypeBlock.getItemBlock();
            if (itemBlock != null) registerItemBlock(r, itemBlock);
        }

        //=== Alabaster ==============================================================================================//

        for (var alabasterBlock : ALABASTER_BLOCKS.values()) {
            r.register(createItemBlock(alabasterBlock));
        }

        //=== Groundcover ==============================================================================================//

//		for (var groundcoverBlock : GROUNDCOVER_BLOCK.values()) {
//			r.register(createItemBlock(groundcoverBlock, ItemBlock::new));
//		}

        //=== Other ==================================================================================================//

        ITEM_BLOCKS.forEach(x -> registerItemBlock(r, x));
        ITEM.forEach(r::register);


    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerVanillaOverrides(RegistryEvent.Register<Block> event) {
        // Ванильные переопределения. Используется для небольших настроек ванильных предметов, а не для их полной замены.
        if (ConfigTFC.General.OVERRIDES.enableFrozenOverrides) {
            TerraFirmaCraft.LOGGER.info("The below warnings about unintended overrides are normal. The override is intended. ;)");
            event.getRegistry().registerAll(
                    new BlockIceTFC(FluidRegistry.WATER),
                    new BlockSnowTFC()
            );
        }

        if (ConfigTFC.General.OVERRIDES.enableTorchOverride) {
            event.getRegistry().register(new BlockTorchTFC());
        }
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

    @Nonnull
    public IThreadListener getThreadListener(MessageContext context) {
        if (context.side.isServer()) {
            return context.getServerHandler().player.server;
        } else {
            throw new WrongSideException("Tried to get the IThreadListener from a client-side MessageContext on the dedicated server");
        }
    }

    @Nullable
    public EntityPlayer getPlayer(MessageContext context) {
        if (context.side.isServer()) {
            return context.getServerHandler().player;
        } else {
            throw new WrongSideException("Tried to get the player from a client-side MessageContext on the dedicated server");
        }
    }

    @Nullable
    public World getWorld(MessageContext context) {
        if (context.side.isServer()) {
            return context.getServerHandler().player.getServerWorld();
        } else {
            throw new WrongSideException("Tried to get the player from a client-side MessageContext on the dedicated server");
        }
    }

    @Nonnull
    public String getMonthName(Month month, boolean useSeasons) {
        return month.name().toLowerCase();
    }

    @Nonnull
    public String getDayName(int dayOfMonth, long totalDays) {
        return CalendarTFC.DAY_NAMES[(int) (totalDays % 7)];
    }

    @Nonnull
    public String getDate(int hour, int minute, String monthName, int day, long years) {
        return String.format("%02d:%02d %s %02d, %04d", hour, minute, monthName, day, years);
    }

}
