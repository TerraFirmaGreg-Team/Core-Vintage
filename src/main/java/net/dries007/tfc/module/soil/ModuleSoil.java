package net.dries007.tfc.module.soil;

import su.terrafirmagreg.util.module.ModuleBase;
import su.terrafirmagreg.util.registry.Registry;
import net.dries007.tfc.Tags;
import net.dries007.tfc.module.core.api.util.CreativeTabBase;
import net.dries007.tfc.module.soil.api.types.type.SoilTypeHandler;
import net.dries007.tfc.module.soil.api.types.variant.block.SoilBlockVariantHandler;
import net.dries007.tfc.module.soil.api.types.variant.item.SoilItemVariantHandler;
import net.dries007.tfc.module.soil.init.BlocksSoil;
import net.dries007.tfc.module.soil.init.ItemsSoil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModuleSoil extends ModuleBase {

    public static final String MODULE_ID = "module.soil";
    public static final CreativeTabs SOIL_TAB = new CreativeTabBase("soil", "tfc:soil.grass.silt");
    public static final Logger LOGGER = LogManager.getLogger(Tags.MOD_NAME + "." + ModuleSoil.class.getSimpleName());

    public ModuleSoil() {
        super(0, Tags.MOD_ID);

        this.setRegistry(new Registry(Tags.MOD_ID, SOIL_TAB));
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
        SoilTypeHandler.init();
        SoilBlockVariantHandler.init();
        SoilItemVariantHandler.init();

        BlocksSoil.onRegister(registry);
        ItemsSoil.onRegister(registry);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onClientRegister(Registry registry) {
        BlocksSoil.onClientRegister(registry);
        ItemsSoil.onClientRegister(registry);
    }

    @Override
    public void onClientInitializationEvent(FMLInitializationEvent event) {
        super.onClientInitializationEvent(event);

        BlocksSoil.onClientInitialization();
        ItemsSoil.onClientInitialization();
    }
}
