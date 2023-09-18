package net.dries007.tfc.module.wood;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.module.wood.api.type.WoodTypeHandler;
import net.dries007.tfc.module.wood.api.variant.block.WoodBlockVariantHandler;
import net.dries007.tfc.module.wood.api.variant.item.WoodItemVariantHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.dries007.tfc.Tags.MOD_ID;
import static net.dries007.tfc.Tags.MOD_NAME;

public class ModuleWood extends ModuleBase {

    public static final String MODULE_ID = "module.wood";

    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME + "." + ModuleWood.class.getSimpleName());

//    public static IPacketService PACKET_SERVICE;

    public ModuleWood() {
        super(0, MOD_ID);

        this.setRegistry(new Registry(MOD_ID, CreativeTabsTFC.WOOD));
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
        WoodTypeHandler.init();
        WoodBlockVariantHandler.init();
        WoodItemVariantHandler.init();
    }
}
