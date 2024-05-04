package su.terrafirmagreg.modules.device;

import su.terrafirmagreg.api.lib.LoggingHelper;
import su.terrafirmagreg.api.module.Module;
import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.network.IPacketService;
import su.terrafirmagreg.api.spi.creativetab.BaseCreativeTab;
import su.terrafirmagreg.modules.device.init.BlocksDevice;
import su.terrafirmagreg.modules.device.init.EntitiesDevice;
import su.terrafirmagreg.modules.device.init.ItemsDevice;
import su.terrafirmagreg.modules.device.init.PacketDevice;
import su.terrafirmagreg.modules.device.init.SoundDevice;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.modules.Modules.Device;

@Module(moduleID = Device)
public final class ModuleDevice extends ModuleBase {

    public static final LoggingHelper LOGGER = new LoggingHelper(ModuleDevice.class.getSimpleName());
    public static final CreativeTabs DEVICES_TAB = new BaseCreativeTab("device", "device/firestarter");

    public static IPacketService PACKET_SERVICE;

    public ModuleDevice() {
        this.enableAutoRegistry(DEVICES_TAB);

        PACKET_SERVICE = this.enableNetwork();
    }

    @Override
    public void onRegister() {
        BlocksDevice.onRegister(registryManager);
        ItemsDevice.onRegister(registryManager);
        EntitiesDevice.onRegister(registryManager);
        SoundDevice.onRegister(registryManager);
    }

    @SideOnly(Side.CLIENT)
    public void onClientRegister() {
        EntitiesDevice.onClientRegister(registryManager);

    }

    @Override
    protected void onNetworkRegister() {
        PacketDevice.onRegister(packetRegistry);
    }

    @Override
    public @NotNull LoggingHelper getLogger() {
        return LOGGER;
    }

}
