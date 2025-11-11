package su.terrafirmagreg.modules.world.content.gen.tree;

import su.terrafirmagreg.framework.manager.content.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.api.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeRedCedar extends BaseGeneratorTree {


  public GeneratorTreeRedCedar() {
    super(Builder.builder()
      .name("red_elm")
      .rainInfo(60f, 290f)
      .tempInfo(2f, 20f)
      .decayDist(6)
      .minGrowthTime(18)
      .bushes()
      .density(0.4f, 2f)
      //.generator(GEN_RED_ELM)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.RED_ELM).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.RED_ELM).getDefaultState())

    );
  }


}
