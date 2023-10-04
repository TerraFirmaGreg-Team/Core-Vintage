package net.dries007.tfc.module.wood;

import su.terrafirmagreg.util.module.ModuleBase;
import su.terrafirmagreg.util.registry.Registry;
import com.ferreusveritas.dynamictrees.api.TreeHelper;
import com.ferreusveritas.dynamictrees.seasons.SeasonHelper;
import net.dries007.tfc.Tags;
import net.dries007.tfc.module.core.api.util.CreativeTabBase;
import net.dries007.tfc.module.wood.api.types.type.WoodTypeHandler;
import net.dries007.tfc.module.wood.api.types.variant.block.WoodBlockVariantHandler;
import net.dries007.tfc.module.wood.api.types.variant.item.WoodItemVariantHandler;
import net.dries007.tfc.module.wood.init.BlocksWood;
import net.dries007.tfc.module.wood.init.EntitiesWood;
import net.dries007.tfc.module.wood.init.ItemsWood;
import net.dries007.tfc.module.wood.objects.commands.CommandGenTree;
import net.dries007.tfc.module.wood.plugin.dynamictrees.SeasonManager;
import net.dries007.tfc.module.wood.plugin.dynamictrees.TFCRootDecay;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModuleWood extends ModuleBase {

    public static final String MODULE_ID = "module.wood";
    public static final CreativeTabs WOOD_TAB = new CreativeTabBase("wood", "tfc:wood.planks.pine");

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME + "." + ModuleWood.class.getSimpleName());

//    public static IPacketService PACKET_SERVICE;

    public ModuleWood() {
        super(0, Tags.MOD_ID);

        this.setRegistry(new Registry(Tags.MOD_ID, WOOD_TAB));
        this.enableAutoRegistry();

        //PACKET_SERVICE = this.enableNetwork();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onRegister(Registry registry) {
        WoodTypeHandler.init();
        WoodBlockVariantHandler.init();
        WoodItemVariantHandler.init();

        BlocksWood.onRegister(registry);
        ItemsWood.onRegister(registry);
        EntitiesWood.onRegister(registry);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onClientRegister(Registry registry) {
        BlocksWood.onClientRegister(registry);
        ItemsWood.onClientRegister(registry);
        EntitiesWood.onClientRegister();
    }


    // --------------------------------------------------------------------------
    // - FML Lifecycle
    // --------------------------------------------------------------------------

    @Override
    public void onConstructionEvent(FMLConstructionEvent event) {
        super.onConstructionEvent(event);

    }

    @Override
    public void onPreInitializationEvent(FMLPreInitializationEvent event) {
        super.onPreInitializationEvent(event);

        SeasonHelper.setSeasonManager(SeasonManager.INSTANCE);
    }

    @Override
    public void onInitializationEvent(FMLInitializationEvent event) {
        super.onInitializationEvent(event);

    }

    @Override
    public void onPostInitializationEvent(FMLPostInitializationEvent event) {
        super.onPostInitializationEvent(event);

        BlocksWood.onPostInitialization();
        TreeHelper.setCustomRootBlockDecay(TFCRootDecay.INSTANCE);
    }

    @Override
    public void onLoadCompleteEvent(FMLLoadCompleteEvent event) {
        super.onLoadCompleteEvent(event);

    }

    // --------------------------------------------------------------------------
    // - FML Lifecycle: Client
    // --------------------------------------------------------------------------

    @SideOnly(Side.CLIENT)
    @Override
    public void onClientPreInitializationEvent(FMLPreInitializationEvent event) {
        super.onClientPreInitializationEvent(event);
    }

    @Override
    public void onClientInitializationEvent(FMLInitializationEvent event) {
        super.onClientInitializationEvent(event);

        BlocksWood.onClientInitialization();
        ItemsWood.onClientInitialization();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onClientPostInitializationEvent(FMLPostInitializationEvent event) {
        super.onClientPostInitializationEvent(event);
    }

    // --------------------------------------------------------------------------
    // - Server
    // --------------------------------------------------------------------------

    @Override
    public void onServerAboutToStartEvent(FMLServerAboutToStartEvent event) {
        super.onServerAboutToStartEvent(event);

    }

    @Override
    public void onServerStartingEvent(FMLServerStartingEvent event) {
        super.onServerStartingEvent(event);

        event.registerServerCommand(new CommandGenTree());
    }

    @Override
    public void onServerStartedEvent(FMLServerStartedEvent event) {
        super.onServerStartedEvent(event);

    }

    @Override
    public void onServerStoppingEvent(FMLServerStoppingEvent event) {
        super.onServerStoppingEvent(event);

    }

    @Override
    public void onServerStoppedEvent(FMLServerStoppedEvent event) {
        super.onServerStoppedEvent(event);

    }
}
