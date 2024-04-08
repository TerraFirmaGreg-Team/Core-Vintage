package su.terrafirmagreg.modules.device;

import su.terrafirmagreg.api.module.ModuleBase;
import su.terrafirmagreg.api.module.ModuleTFG;
import su.terrafirmagreg.api.network.IPacketService;
import su.terrafirmagreg.api.spi.creativetab.CreativeTabBase;
import su.terrafirmagreg.modules.device.init.BlocksDevice;
import su.terrafirmagreg.modules.device.init.EntitiesDevice;
import su.terrafirmagreg.modules.device.init.ItemsDevice;
import su.terrafirmagreg.modules.device.init.PacketDevice;
import su.terrafirmagreg.modules.device.init.SoundDevice;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.jetbrains.annotations.NotNull;

@ModuleTFG(moduleID = "Device", name = "TFG Module Device")
public final class ModuleDevice extends ModuleBase {

    public static final Logger LOGGER = LogManager.getLogger(ModuleDevice.class.getSimpleName());
    public static final CreativeTabs DEVICES_TAB = new CreativeTabBase("device", "device/firestarter");

    public static IPacketService PACKET_SERVICE;

    public ModuleDevice() {
        super(5);
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
    public @NotNull Logger getLogger() {
        return LOGGER;
    }

}
