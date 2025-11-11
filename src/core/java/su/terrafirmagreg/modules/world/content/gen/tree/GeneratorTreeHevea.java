package su.terrafirmagreg.modules.world.content.gen.tree;

import su.terrafirmagreg.framework.manager.content.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.api.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeHevea extends BaseGeneratorTree {


  public GeneratorTreeHevea() {
    super(Builder.builder()
      .name("african_padauk")
      .rainInfo(275f, 500f)
      .tempInfo(22f, 50f)
      .density(0.5f, 2f)
      .minGrowthTime(18)
      .decayDist(6)
      .radius(1)
      .bushes()
      //.generator(GEN_AFRICAN_PADAUK)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.ACACIA).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.ACACIA).getDefaultState())

    );
  }


}
