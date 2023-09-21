package net.dries007.tfc.module.rock;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.module.core.common.objects.CreativeTabsTFC;
import net.dries007.tfc.module.rock.api.category.RockCategoryHandler;
import net.dries007.tfc.module.rock.api.type.RockTypeHandler;
import net.dries007.tfc.module.rock.api.variant.block.RockBlockVariantHandler;
import net.dries007.tfc.module.rock.api.variant.item.RockItemVariantHandler;
import net.dries007.tfc.module.rock.init.BlockInitializer;
import net.dries007.tfc.module.rock.init.ItemInitializer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.dries007.tfc.Tags.MOD_ID;
import static net.dries007.tfc.Tags.MOD_NAME;

public class ModuleRock extends ModuleBase {

    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME + "." + ModuleRock.class.getSimpleName());

    public ModuleRock() {
        super(0, MOD_ID);

        this.setRegistry(new Registry(MOD_ID, CreativeTabsTFC.ROCK));
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
        RockCategoryHandler.init();
        RockTypeHandler.init();
        RockBlockVariantHandler.init();
        RockItemVariantHandler.init();

        BlockInitializer.onRegister(registry);
        ItemInitializer.onRegister(registry);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onClientRegister(Registry registry) {
        BlockInitializer.onClientRegister(registry);
        ItemInitializer.onClientRegister(registry);
    }
}
