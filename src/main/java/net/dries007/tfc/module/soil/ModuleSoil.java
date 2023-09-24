package net.dries007.tfc.module.soil;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.module.core.common.objects.CreativeTabsTFC;
import net.dries007.tfc.module.soil.api.type.SoilTypeHandler;
import net.dries007.tfc.module.soil.api.variant.block.SoilBlockVariantHandler;
import net.dries007.tfc.module.soil.api.variant.item.SoilItemVariantHandler;
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

import static net.dries007.tfc.Tags.MOD_ID;
import static net.dries007.tfc.Tags.MOD_NAME;

public class ModuleSoil extends ModuleBase {

    public static final CreativeTabs SOIL_TAB = new CreativeTabsTFC.TFCCreativeTab("soil", "tfc:soil.grass.silt");
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME + "." + ModuleSoil.class.getSimpleName());

    public ModuleSoil() {
        super(0, MOD_ID);

        this.setRegistry(new Registry(MOD_ID, SOIL_TAB));
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
