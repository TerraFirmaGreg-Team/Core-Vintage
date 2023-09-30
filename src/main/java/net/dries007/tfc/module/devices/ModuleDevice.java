package net.dries007.tfc.module.devices;

import com.codetaylor.mc.athenaeum.module.ModuleBase;
import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.module.core.api.util.CreativeTabBase;
import net.dries007.tfc.module.devices.init.BlocksDevice;
import net.dries007.tfc.module.devices.init.ItemsDevice;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static net.dries007.tfc.Tags.MOD_ID;
import static net.dries007.tfc.Tags.MOD_NAME;

public class ModuleDevice extends ModuleBase {

    public static final String MODULE_ID = "module.devices";
    public static final CreativeTabs DEVICES_TAB = new CreativeTabBase("device", "tfc:device.bellows");
    public static final Logger LOGGER = LogManager.getLogger(MOD_NAME + "." + ModuleDevice.class.getSimpleName());


    public ModuleDevice() {
        super(0, MOD_ID);

        this.setRegistry(new Registry(MOD_ID, DEVICES_TAB));
        this.enableAutoRegistry();

        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public void onRegister(Registry registry) {
        BlocksDevice.onRegister(registry);
        ItemsDevice.onRegister(registry);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onClientRegister(Registry registry) {
        BlocksDevice.onClientRegister(registry);
        ItemsDevice.onClientRegister(registry);
    }
}
