package su.terrafirmagreg.modules.world.content.gen.tree;

import su.terrafirmagreg.framework.manager.content.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeAspen extends BaseGeneratorTree {


  public GeneratorTreeAspen() {
    super(Builder.builder()
      .name("aspen")
      .rainInfo(10f, 80f)
      .tempInfo(-10f, 16f)
      .minGrowthTime(8)
      .density(0.25f, 1f)
      //.generator(GEN_ASPEN)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 8, 2, 1.50f)
      .growthLogicKit("conifer")
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.ASPEN).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.ASPEN).getDefaultState())
    );
  }


}
