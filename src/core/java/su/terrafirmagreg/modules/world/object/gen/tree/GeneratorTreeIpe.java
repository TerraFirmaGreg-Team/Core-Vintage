package su.terrafirmagreg.modules.world.object.gen.tree;

import su.terrafirmagreg.framework.manager.registry.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.api.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import net.dries007.tfc.objects.items.ItemsTFCF;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeIpe extends BaseGeneratorTree {


  public GeneratorTreeIpe() {
    super(Builder.builder()
      .name("pink_ivory")
      .rainInfo(210f, 500f)
      .tempInfo(18f, 31f)
      .decayDist(6)
      .minGrowthTime(18)
      .bushes()
      .density(0.2f, 2f)
      //.generator(GEN_PINK_IVORY)
      .fruit(() -> ItemsTFCF.PINK_IVORY_DRUPE, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.ACACIA).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.ACACIA).getDefaultState())

    );
  }


}
