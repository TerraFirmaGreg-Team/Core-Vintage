package su.terrafirmagreg.modules.device;

import su.terrafirmagreg.api.lib.LoggingHelper;
import su.terrafirmagreg.api.module.Module;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.spi.creativetab.BaseCreativeTab;
import su.terrafirmagreg.modules.device.init.BlocksDevice;
import su.terrafirmagreg.modules.device.init.EntitiesDevice;
import su.terrafirmagreg.modules.device.init.ItemsDevice;
import su.terrafirmagreg.modules.device.init.PacketsDevice;
import su.terrafirmagreg.modules.device.init.RecipesDevice;
import su.terrafirmagreg.modules.device.init.RegistriesDevice;
import su.terrafirmagreg.modules.device.init.SoundsDevice;
import su.terrafirmagreg.modules.device.plugin.top.PluginTheOneProbe;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.modules.Modules.DEVICE;

@Module(moduleID = DEVICE)
public final class ModuleDevice extends ModuleBase {

    public static final LoggingHelper LOGGER = new LoggingHelper(ModuleDevice.class.getSimpleName());
    public static final CreativeTabs DEVICES_TAB = new BaseCreativeTab("device", "device/firestarter");

    public ModuleDevice() {
        this.enableAutoRegistry(DEVICES_TAB);
        this.enableNetwork();
    }

    @Override
    protected void onRegister() {
        BlocksDevice.onRegister(registryManager);
        ItemsDevice.onRegister(registryManager);
        EntitiesDevice.onRegister(registryManager);
        SoundsDevice.onRegister(registryManager);
    }

    @SideOnly(Side.CLIENT)
    protected void onClientRegister() {
        EntitiesDevice.onClientRegister(registryManager);

    }

    @Override
    protected void onNetworkRegister() {

        PacketsDevice.onRegister(packetRegistry);
    }

    @Override
    protected void onNewRegister() {

        RegistriesDevice.onRegister();
    }

    @Override
    protected void onRecipesRegister() {

        RecipesDevice.onRegister();
    }

    @Override
    protected void onInit(FMLInitializationEvent event) {

        PluginTheOneProbe.init();
    }

    @Override
    public @NotNull LoggingHelper getLogger() {
        return LOGGER;
    }

}
