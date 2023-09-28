package net.dries007.tfc.common;

import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.ferreusveritas.dynamictrees.event.BiomeSuitabilityEvent;
import com.ferreusveritas.dynamictrees.seasons.SeasonHelper;
import gregtech.api.unification.material.event.MaterialEvent;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.damage.CapabilityDamageResistance;
import net.dries007.tfc.api.capability.egg.CapabilityEgg;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.food.FoodHandler;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.metal.CapabilityMetalItem;
import net.dries007.tfc.api.capability.player.CapabilityPlayerData;
import net.dries007.tfc.api.capability.size.CapabilityItemSize;
import net.dries007.tfc.api.capability.worldtracker.CapabilityWorldTracker;
import net.dries007.tfc.api.types.drinkable.DrinkableHandler;
import net.dries007.tfc.api.util.IItemProvider;
import net.dries007.tfc.client.util.TFCGuiHandler;
import net.dries007.tfc.commands.*;
import net.dries007.tfc.common.objects.LootTablesTFC;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.common.objects.entity.EntitiesTFC;
import net.dries007.tfc.common.objects.items.TFCItems;
import net.dries007.tfc.compat.dynamictrees.SeasonManager;
import net.dries007.tfc.compat.dynamictrees.TFCRootDecay;
import net.dries007.tfc.compat.gregtech.items.tools.TFGToolItems;
import net.dries007.tfc.compat.gregtech.material.TFGMaterialHandler;
import net.dries007.tfc.compat.gregtech.oreprefix.TFGOrePrefixHandler;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.module.core.common.blocks.BlockIceTFC;
import net.dries007.tfc.module.core.common.blocks.BlockSnowTFC;
import net.dries007.tfc.module.core.common.blocks.BlockTorchTFC;
import net.dries007.tfc.module.core.common.blocks.itemblocks.ItemBlockTorch;
import net.dries007.tfc.module.core.common.items.ItemGlassBottleTFC;
import net.dries007.tfc.module.core.init.RegistryCore;
import net.dries007.tfc.module.food.ModuleFood;
import net.dries007.tfc.network.*;
import net.dries007.tfc.util.WrongSideException;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.fuel.FuelManager;
import net.dries007.tfc.util.json.JsonConfigRegistry;
import net.dries007.tfc.world.classic.chunkdata.CapabilityChunkData;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSnow;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.dedicated.PropertyManager;
import net.minecraft.util.IThreadListener;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.server.FMLServerHandler;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.dries007.tfc.Tags.MOD_ID;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = MOD_ID)
public class CommonProxy {

    @SubscribeEvent
    public static void onMaterialsInit(MaterialEvent event) {
        TFGMaterialHandler.init();
        TFGOrePrefixHandler.init();
    }

    @SubscribeEvent
    public static void onNewRegistryEvent(RegistryEvent.NewRegistry event) {
        RegistryCore.createRegistries(event);

    }


    @SubscribeEvent
    public static void biomeHandler(BiomeSuitabilityEvent event) {
        event.setSuitability(1.0f); //doesn't change value, sets isHandled
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        IForgeRegistry<Block> r = event.getRegistry();


        //==== Other =================================================================================================//

        r.registerAll(LeavesPaging.getLeavesMapForModId(MOD_ID).values().toArray(new Block[0]));
        TFCBlocks.BLOCKS.forEach(b -> registerItemBlock(r, b));
        TFCBlocks.FLUID.forEach(r::register);
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        IForgeRegistry<Item> r = event.getRegistry();

        //==== Molds =================================================================================================//

        for (var mold : TFCItems.UNFIRED_MOLDS.values()) {
            r.register(mold);
        }

        for (var mold : TFCItems.FIRED_MOLDS.values()) {
            r.register(mold);
        }

        //==== Other =================================================================================================//

        TFCItems.ITEMS.forEach(r::register);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerVanillaOverridesBlock(RegistryEvent.Register<Block> event) {
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

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void registerVanillaOverridesItem(RegistryEvent.Register<Item> event) {
        // Vanilla Overrides. Used for small tweaks on vanilla items, rather than replacing them outright
        TerraFirmaCraft.LOGGER.info("The below warnings about unintended overrides are normal. The override is intended. ;)");
        event.getRegistry().registerAll(
                new ItemSnow(Blocks.SNOW_LAYER).setRegistryName("minecraft", "snow_layer"),
                new ItemGlassBottleTFC().setRegistryName(Items.GLASS_BOTTLE.getRegistryName()).setTranslationKey("glassBottle")
        );

        if (ConfigTFC.General.OVERRIDES.enableTorchOverride) {
            event.getRegistry().register(new ItemBlockTorch(Blocks.TORCH).setRegistryName("minecraft", "torch"));
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static void registerItemBlock(IForgeRegistry<Block> r, Block block) {
        r.register(block);
        if (block instanceof IItemProvider itemBlock) {
            var item = itemBlock.getItemBlock();
            if (item != null) {
                item.setRegistryName(block.getRegistryName());
                item.setCreativeTab(block.getCreativeTab());
                TFCItems.ITEMS.add(item);
            }
        }

    }


    public void onPreInit(FMLPreInitializationEvent event) {
        //TreeModule.preInit();
        ModuleFood.preInit();

        DrinkableHandler.preInit();

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

        SeasonHelper.setSeasonManager(SeasonManager.INSTANCE);
    }

    public void onInit(FMLInitializationEvent event) {
        LootTablesTFC.init();
        CapabilityFood.init();

        setTFCWorldTypeAsDefault(event);

        CapabilityItemSize.init();
        CapabilityItemHeat.init();
        CapabilityMetalItem.init();
        CapabilityForgeable.init();

        //RecipeHandler.init();
    }

    public void onPostInit(FMLPostInitializationEvent event) {

        TreeHelper.setCustomRootBlockDecay(TFCRootDecay.INSTANCE);
        FuelManager.postInit();
        JsonConfigRegistry.INSTANCE.postInit();
    }

    public void onLoadComplete(FMLLoadCompleteEvent event) {
        // Я сука несколько дней разбирался как эта хуйня работает, теперь рассказываю.
        // У нас есть статическая переменная в капабилити еды, отвечающая за то, будет ли еда портиться.
        // При создании еды и присваивании ей капабилити, создается еда, которая не умеет портиться,
        // но чтобы в игре она пропадала, в самом конце загрузки мы включаем порчу еды и при получении еды, мы О ЧУДО,
        // получаем еду которая портится, ведь переменная стоит на false.
        // Кстати говоря, почему это происходит именно на LoadComplete?
        // Потому что JEI загрузка плагинов срабатывает на PostInit,
        // поэтому, чтобы в JEI еда не портилась, мы делаем это на самом последнем моменте загрузки.
        FoodHandler.setNonDecaying(false);
    }

    public void onServerStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandGetHeat());
        event.registerServerCommand(new CommandStripWorld());
        event.registerServerCommand(new CommandHeat());
        event.registerServerCommand(new CommandPlayerTFC());
        event.registerServerCommand(new CommandTimeTFC());
        event.registerServerCommand(new CommandDebugInfo());
        event.registerServerCommand(new CommandWorkChunk());
        event.registerServerCommand(new CommandGenTree());

        // Initialize calendar for the current server
        CalendarTFC.INSTANCE.init(event.getServer());
    }

    private void setTFCWorldTypeAsDefault(FMLInitializationEvent event) {
        if (event.getSide().isServer()) {
            MinecraftServer server = FMLServerHandler.instance().getServer();
            if (server instanceof DedicatedServer) {
                PropertyManager settings = ((DedicatedServer) server).settings;
                if (ConfigTFC.General.OVERRIDES.forceTFCWorldType) {
                    // This is called before vanilla defaults it, meaning we intercept it's default with ours
                    // However, we can't actually set this due to fears of overriding the existing world
                    TerraFirmaCraft.LOGGER.info("Setting default level-type to `tfc_classic`");
                    settings.getStringProperty("level-type", "tfc_classic");
                }
            }
        }
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
