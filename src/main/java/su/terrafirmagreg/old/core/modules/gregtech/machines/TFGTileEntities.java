package su.terrafirmagreg.old.core.modules.gregtech.machines;

import su.terrafirmagreg.old.core.modules.gregtech.machines.multiblock.MetaTileEntityGreenhouse;

import net.minecraft.util.ResourceLocation;

import static gregtech.common.metatileentities.MetaTileEntities.registerMetaTileEntity;
import static su.terrafirmagreg.Tags.MOD_ID;

public class TFGTileEntities {

  public static MetaTileEntityGreenhouse GREENHOUSE;

  public static void init() {
    
    GREENHOUSE = registerMetaTileEntity(32000, new MetaTileEntityGreenhouse(new ResourceLocation(MOD_ID, "greenhouse")));
  }
}
