package su.terrafirmagreg.modules.device.init;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.framework.registry.api.IRegistryManager;
import su.terrafirmagreg.modules.device.object.tile.multi.MetaTileEntityGreenhouse;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;

public final class BlocksDevice {

  public static MetaTileEntityGreenhouse GREENHOUSE;

  public static void onRegister(IRegistryManager registry) {

    GREENHOUSE = registerMetaTileEntity(32000, new MetaTileEntityGreenhouse(ModUtils.resource("greenhouse")));

  }
}
