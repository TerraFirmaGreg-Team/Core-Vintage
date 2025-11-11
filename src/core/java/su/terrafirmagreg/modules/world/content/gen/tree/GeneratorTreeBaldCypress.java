package su.terrafirmagreg.modules.world.content.gen.tree;

import su.terrafirmagreg.framework.manager.content.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.api.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeBaldCypress extends BaseGeneratorTree {


  public GeneratorTreeBaldCypress() {
    super(Builder.builder()
      .name("bald_cypress")
      .rainInfo(180f, 500f)
      .tempInfo(10f, 38f)
      .dominance(0f)
      .density(0f, 0f)
      .minGrowthTime(8)
      .bushes()
      .isConifer()
      //.generator(GEN_BALD_CYPRESS)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.ACACIA).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.ACACIA).getDefaultState())

    );
  }


}
