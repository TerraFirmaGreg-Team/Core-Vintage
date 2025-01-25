package su.terrafirmagreg.modules.integration.gregtech.init;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.integration.gregtech.object.metatileentity.multi.MetaTileEntityGreenhouse;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;

public final class BlocksGregTech {

  public static MetaTileEntityGreenhouse GREENHOUSE;

  public static void init() {

    GREENHOUSE = registerMetaTileEntity(32000, new MetaTileEntityGreenhouse(ModUtils.resource("greenhouse")));

  }
}
