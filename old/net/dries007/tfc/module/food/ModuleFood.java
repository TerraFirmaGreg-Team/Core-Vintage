package net.dries007.tfc.module.food;

import net.dries007.tfc.module.core.api.util.CreativeTabBase;
import net.dries007.tfc.module.food.api.types.category.FoodCategoryHandler;
import net.dries007.tfc.module.food.api.types.type.FoodTypeHandler;
import net.dries007.tfc.module.food.api.types.variant.Item.FoodItemVariantHandler;
import net.dries007.tfc.module.food.api.types.variant.block.FoodBlockVariantHandler;
import net.dries007.tfc.module.food.init.BlocksFood;
import net.dries007.tfc.module.food.init.ItemsFood;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import su.terrafirmagreg.util.module.ModuleBase;
import su.terrafirmagreg.util.registry.Registry;

import javax.annotation.Nonnull;

import static net.dries007.tfc.Tags.MOD_ID;
import static net.dries007.tfc.Tags.MOD_NAME;

public class ModuleFood extends ModuleBase {

    public static final String MODULE_ID = "module.food";
    public static final CreativeTabs FOOD_TAB = new CreativeTabBase("food", "tfc:food.ingredient.wheat_bread_sandwich");

    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME + "." + ModuleFood.class.getSimpleName());

    public ModuleFood() {
        super(0, Tags.MOD_ID);

        this.setRegistry(new Registry(Tags.MOD_ID, FOOD_TAB));
        this.enableAutoRegistry();

        //PACKET_SERVICE = this.enableNetwork();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onRegister(Registry registry) {
        FoodCategoryHandler.init();
        FoodTypeHandler.init();
        FoodBlockVariantHandler.init();
        FoodItemVariantHandler.init();

        BlocksFood.onRegister(registry);
        ItemsFood.onRegister(registry);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onClientRegister(Registry registry) {

        BlocksFood.onClientRegister(registry);
        ItemsFood.onClientRegister(registry);
    }

    @Override
    public void onClientInitializationEvent(FMLInitializationEvent event) {
        super.onClientInitializationEvent(event);

        BlocksFood.onClientInitialization();
        ItemsFood.onClientInitialization();
    }

    @Nonnull
    @Override
    public Logger getLogger() {
        return LOGGER;
    }
}
