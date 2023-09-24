package net.dries007.tfc.module.plant;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.module.core.common.objects.CreativeTabsTFC;
import net.dries007.tfc.module.plant.api.category.PlantCategoryHandler;
import net.dries007.tfc.module.plant.api.type.PlantTypeHandler;
import net.dries007.tfc.module.plant.api.variant.block.PlantBlockVariantHandler;
import net.dries007.tfc.module.plant.api.variant.item.PlantItemVariantHandler;
import net.dries007.tfc.module.plant.init.BlockInitializer;
import net.dries007.tfc.module.plant.init.ItemInitializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.dries007.tfc.Tags.MOD_ID;
import static net.dries007.tfc.Tags.MOD_NAME;

public class PlantModule extends ModuleBase {


    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME + "." + PlantModule.class.getSimpleName());

//    public static IPacketService PACKET_SERVICE;

    public PlantModule() {
        super(0, MOD_ID);

        this.setRegistry(new Registry(MOD_ID, CreativeTabsTFC.FLORA_TAB));
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

        BlockInitializer.onRegister(registry);
        ItemInitializer.onRegister(registry);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onClientRegister(Registry registry) {
        BlockInitializer.onClientRegister(registry);
        ItemInitializer.onClientRegister(registry);
    }

    @Override
    public void onClientInitializationEvent(FMLInitializationEvent event) {
        super.onClientInitializationEvent(event);

        BlockInitializer.onClientInitialization();
        ItemInitializer.onClientInitialization();
    }

}
