package net.dries007.tfc.module.ceramic;

import su.terrafirmagreg.util.module.ModuleBase;
import su.terrafirmagreg.util.registry.Registry;
import net.dries007.tfc.module.ceramic.init.BlocksCeramic;
import net.dries007.tfc.module.ceramic.init.ItemsCeramic;
import net.dries007.tfc.module.core.api.util.CreativeTabBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.dries007.tfc.Tags.MOD_ID;
import static net.dries007.tfc.Tags.MOD_NAME;

public class ModuleCeramic extends ModuleBase {

    public static final CreativeTabs POTTERY_TAB = new CreativeTabBase("pottery", "tfc:ceramics.fired.mold.ingot");
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME + "." + ModuleCeramic.class.getSimpleName());

    public ModuleCeramic() {
        super(0, MOD_ID);

        this.setRegistry(new Registry(MOD_ID, POTTERY_TAB));
        this.enableAutoRegistry();

        //PACKET_SERVICE = this.enableNetwork();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onRegister(Registry registry) {

        BlocksCeramic.onRegister(registry);
        ItemsCeramic.onRegister(registry);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onClientRegister(Registry registry) {
        BlocksCeramic.onClientRegister(registry);
        ItemsCeramic.onClientRegister(registry);
    }
}
