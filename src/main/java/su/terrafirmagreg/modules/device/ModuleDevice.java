package su.terrafirmagreg.modules.device;

import su.terrafirmagreg.api.base.creativetab.BaseCreativeTab;
import su.terrafirmagreg.data.lib.LoggingHelper;
import su.terrafirmagreg.api.module.Module;
import su.terrafirmagreg.api.module.ModuleBase;
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

import static su.terrafirmagreg.modules.ModuleContainer.DEVICE;

@Module(moduleID = DEVICE)
public final class ModuleDevice extends ModuleBase {

    public static final LoggingHelper LOGGER = LoggingHelper.of(ModuleDevice.class.getSimpleName());
    
    public final CreativeTabs DEVICES_TAB;

    public ModuleDevice() {
        this.DEVICES_TAB = BaseCreativeTab.of("device", "device/firestarter");

        this.enableAutoRegistry(DEVICES_TAB);
        this.enableNetwork();
    }

    @Override
    public void onRegister() {
        BlocksDevice.onRegister(registryManager);
        ItemsDevice.onRegister(registryManager);
        EntitiesDevice.onRegister(registryManager);
        SoundsDevice.onRegister(registryManager);
    }

    @SideOnly(Side.CLIENT)
    public void onClientRegister() {
        EntitiesDevice.onClientRegister(registryManager);

    }

    @Override
    public void onNetworkRegister() {

        PacketsDevice.onRegister(packetRegistry);
    }

    @Override
    public void onNewRegister() {

        RegistriesDevice.onRegister();
    }

    @Override
    public void onRecipesRegister() {

        RecipesDevice.onRegister();
    }

    @Override
    public void onInit(FMLInitializationEvent event) {

        PluginTheOneProbe.init();
    }

    @Override
    public @NotNull LoggingHelper getLogger() {
        return LOGGER;
    }

}
