package su.terrafirmagreg.modules.world.content.gen.tree;

import su.terrafirmagreg.framework.manager.content.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeNordmannFir extends BaseGeneratorTree {


  public GeneratorTreeNordmannFir() {
    super(Builder.builder()
      .name("nordmann_fir")
      .rainInfo(100f, 380f)
      .tempInfo(-16f, 7f)
      .minGrowthTime(8)
      .isConifer()
      .density(0.1f, 0.9f)
      //.generator(GEN_NORDMANN_FIR)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.ACACIA).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.ACACIA).getDefaultState())

    );
  }


}
