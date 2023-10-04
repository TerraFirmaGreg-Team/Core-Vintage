package net.dries007.tfc.module.plant;

import net.dries007.tfc.module.core.api.util.CreativeTabBase;
import net.dries007.tfc.module.plant.api.types.category.PlantCategoryHandler;
import net.dries007.tfc.module.plant.api.types.type.PlantTypeHandler;
import net.dries007.tfc.module.plant.api.types.variant.block.PlantBlockVariantHandler;
import net.dries007.tfc.module.plant.api.types.variant.item.PlantItemVariantHandler;
import net.dries007.tfc.module.plant.init.BlocksPlant;
import net.dries007.tfc.module.plant.init.ItemsPlant;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import su.terrafirmagreg.util.module.ModuleBase;
import su.terrafirmagreg.util.registry.Registry;

import static net.dries007.tfc.Tags.MOD_ID;
import static net.dries007.tfc.Tags.MOD_NAME;

public class ModulePlant extends ModuleBase {

    public static final String MODULE_ID = "module.plant";
    public static final CreativeTabs FLORA_TAB = new CreativeTabBase("flora", "tfc:plant.cactus.barrel_cactus");
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME + "." + ModulePlant.class.getSimpleName());

//    public static IPacketService PACKET_SERVICE;

    public ModulePlant() {
        super(0, MOD_ID);

        this.setRegistry(new Registry(MOD_ID, FLORA_TAB));
        this.enableAutoRegistry();

        //PACKET_SERVICE = this.enableNetwork();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onPreInitializationEvent(FMLPreInitializationEvent event) {
        super.onPreInitializationEvent(event);

    }

    @Override
    public void onRegister(Registry registry) {
        PlantCategoryHandler.init();
        PlantTypeHandler.init();
        PlantBlockVariantHandler.init();
        PlantItemVariantHandler.init();

        BlocksPlant.onRegister(registry);
        ItemsPlant.onRegister(registry);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onClientRegister(Registry registry) {
        BlocksPlant.onClientRegister(registry);
        ItemsPlant.onClientRegister(registry);
    }

    @Override
    public void onClientInitializationEvent(FMLInitializationEvent event) {
        super.onClientInitializationEvent(event);

        BlocksPlant.onClientInitialization();
        ItemsPlant.onClientInitialization();
    }

}
