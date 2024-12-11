package su.terrafirmagreg.modules.agriculture.init;

import su.terrafirmagreg.api.registry.RegistryManager;
import su.terrafirmagreg.modules.agriculture.api.types.berrybush.BerryBushType;
import su.terrafirmagreg.modules.agriculture.object.block.BlockBerryBush;
import su.terrafirmagreg.modules.agriculture.object.block.BlockBushTrellis;
import su.terrafirmagreg.modules.agriculture.object.block.BlockTrellis;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

import static su.terrafirmagreg.modules.agriculture.ModuleAgriculture.REGISTRY;

public final class BlocksAgriculture {

  public static final Map<BerryBushType, BlockBerryBush> BERRY_BUSH = REGISTRY.block(BerryBushType.getTypes(), BlockBerryBush::new);
  public static final Map<BerryBushType, BlockBushTrellis> BUSH_TRELLIS = REGISTRY.block(BerryBushType.getTypes(), BlockBushTrellis::new);

  public static final BlockTrellis TRELLIS = REGISTRY.block(new BlockTrellis());

  public static void onRegister(RegistryManager registryManager) {
  }

  @SideOnly(Side.CLIENT)
  public static void onClientRegister(RegistryManager registryManager) {

  }
}
