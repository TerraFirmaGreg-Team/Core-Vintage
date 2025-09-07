package su.terrafirmagreg.modules.world.content.gen.tree;

import su.terrafirmagreg.framework.manager.content.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeIroko extends BaseGeneratorTree {


  public GeneratorTreeIroko() {
    super(Builder.builder()
      .name("iroko")
      .rainInfo(300f, 500f)
      .tempInfo(21f, 50f)
      .radius(1)
      .decayDist(6)
      .minGrowthTime(18)
      .bushes()
      .density(0.5f, 2f)
      //.generator(GEN_IROKO)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.ACACIA).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.ACACIA).getDefaultState())

    );
  }


}
