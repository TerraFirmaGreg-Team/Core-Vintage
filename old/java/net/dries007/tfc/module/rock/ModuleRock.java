package net.dries007.tfc.module.rock;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.module.rock.api.types.category.RockCategoryHandler;
import net.dries007.tfc.module.rock.api.types.type.RockTypeHandler;
import net.dries007.tfc.module.rock.api.types.variant.block.RockBlockVariantHandler;
import net.dries007.tfc.module.rock.api.types.variant.item.RockItemVariantHandler;
import net.dries007.tfc.module.rock.init.BlocksRock;
import net.dries007.tfc.module.rock.init.ItemsRock;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.dries007.tfc.Tags.MOD_ID;
import static net.dries007.tfc.Tags.MOD_NAME;

public class ModuleRock extends ModuleBase {

    public static final String MODULE_ID = "module.rock";
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME + "." + ModuleRock.class.getSimpleName());
    public static final CreativeTabs ROCK_TAB = new CreativeTabsTFC.TFCCreativeTab("rock", "tfc:rock.raw.shale");

    public ModuleRock() {
        super(0, MOD_ID);

        this.setRegistry(new Registry(MOD_ID, ROCK_TAB));
        this.enableAutoRegistry();

        //PACKET_SERVICE = this.enableNetwork();

        MinecraftForge.EVENT_BUS.register(this);
    }


    @Override
    public void onRegister(Registry registry) {
        RockCategoryHandler.init();
        RockTypeHandler.init();
        RockBlockVariantHandler.init();
        RockItemVariantHandler.init();

        BlocksRock.onRegister(registry);
        ItemsRock.onRegister(registry);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onClientRegister(Registry registry) {
        BlocksRock.onClientRegister(registry);
        ItemsRock.onClientRegister(registry);
    }

    @Override
    public void onRegisterRecipesEvent(RegistryEvent.Register<IRecipe> event) {
        super.onRegisterRecipesEvent(event);

        //KnappingRecipesAdd.apply(TFCRegistries.KNAPPING);
    }
}
