package net.dries007.tfc.module.metal;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.module.core.api.util.CreativeTabBase;
import net.dries007.tfc.module.metal.api.type.MetalTypeHandler;
import net.dries007.tfc.module.metal.api.variant.Item.MetalItemVariantHandler;
import net.dries007.tfc.module.metal.api.variant.block.MetalBlockVariantHandler;
import net.dries007.tfc.module.metal.init.BlocksMetal;
import net.dries007.tfc.module.metal.init.ItemsMetal;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.dries007.tfc.Tags.MOD_ID;
import static net.dries007.tfc.Tags.MOD_NAME;

public class ModuleMetal extends ModuleBase {

    public static final String MODULE_ID = "module.metal";
    public static final CreativeTabs METAL_TAB = new CreativeTabBase("metal", "tfc:metal.anvil.red_steel");
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME + "." + ModuleMetal.class.getSimpleName());

    public ModuleMetal() {
        super(0, MOD_ID);

        this.setRegistry(new Registry(MOD_ID, METAL_TAB));
        this.enableAutoRegistry();

        //PACKET_SERVICE = this.enableNetwork();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onRegister(Registry registry) {
        MetalTypeHandler.init();
        MetalBlockVariantHandler.init();
        MetalItemVariantHandler.init();

        BlocksMetal.onRegister(registry);
        ItemsMetal.onRegister(registry);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onClientRegister(Registry registry) {
        BlocksMetal.onClientRegister(registry);
        ItemsMetal.onClientRegister(registry);
    }

    @Override
    public void onClientInitializationEvent(FMLInitializationEvent event) {
        super.onClientInitializationEvent(event);

        BlocksMetal.onClientInitialization();
        ItemsMetal.onClientInitialization();
    }
}
