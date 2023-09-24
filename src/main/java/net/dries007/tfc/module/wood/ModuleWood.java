package net.dries007.tfc.module.wood;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.module.core.common.objects.TFCCreativeTab;
import net.dries007.tfc.module.wood.api.type.WoodTypeHandler;
import net.dries007.tfc.module.wood.api.variant.block.WoodBlockVariantHandler;
import net.dries007.tfc.module.wood.api.variant.item.WoodItemVariantHandler;
import net.dries007.tfc.module.wood.common.WoodStorage;
import net.dries007.tfc.module.wood.init.BlockInitializer;
import net.dries007.tfc.module.wood.init.EntityInitializer;
import net.dries007.tfc.module.wood.init.ItemInitializer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.dries007.tfc.Tags.MOD_ID;
import static net.dries007.tfc.Tags.MOD_NAME;
import static net.dries007.tfc.module.wood.api.type.WoodTypes.PINE;
import static net.dries007.tfc.module.wood.api.variant.block.WoodBlockVariants.PLANKS;

public class ModuleWood extends ModuleBase {

    public static final CreativeTabs WOOD_TAB = new TFCCreativeTab("wood", WoodStorage.getWoodBlock(PLANKS, PINE));


    public static final CreativeTabs POTTERY_TAB = new TFCCreativeTab("pottery", "tfc:ceramics/fired/mold/ingot");
    public static final CreativeTabs FOOD_TAB = new TFCCreativeTab("food", "tfc:food/green_apple");
    public static final CreativeTabs MISC_TAB = new TFCCreativeTab("misc", "tfc:wand");


    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME + "." + ModuleWood.class.getSimpleName());

//    public static IPacketService PACKET_SERVICE;

    public ModuleWood() {
        super(0, MOD_ID);

        this.setRegistry(new Registry(MOD_ID, WOOD_TAB));
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

        BlockInitializer.onRegister(registry);
        ItemInitializer.onRegister(registry);
        EntityInitializer.onRegister(registry);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onClientRegister(Registry registry) {
        BlockInitializer.onClientRegister(registry);
        ItemInitializer.onClientRegister(registry);
        EntityInitializer.onClientRegister();
    }

    @Override
    public void onClientInitializationEvent(FMLInitializationEvent event) {
        super.onClientInitializationEvent(event);

        BlockInitializer.onClientInitialization();
        ItemInitializer.onClientInitialization();
    }
}
