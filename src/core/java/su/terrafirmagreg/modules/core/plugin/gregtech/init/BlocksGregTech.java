package su.terrafirmagreg.modules.core.plugin.gregtech.init;

import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.core.plugin.gregtech.object.metatileentity.multi.MetaTileEntityGreenhouse;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;

public final class BlocksGregTech {

  public static MetaTileEntityGreenhouse GREENHOUSE;

  public static void preInit() {

    GREENHOUSE = registerMetaTileEntity(32000, new MetaTileEntityGreenhouse(ModUtils.resource("greenhouse")));
    MetaTileEntityGreenhouse.addGrasses();

  }
}
