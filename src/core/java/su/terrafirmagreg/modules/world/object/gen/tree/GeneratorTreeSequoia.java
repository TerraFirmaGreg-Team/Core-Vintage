package su.terrafirmagreg.modules.world.object.gen.tree;

import su.terrafirmagreg.framework.manager.registry.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeSequoia extends BaseGeneratorTree {

  public GeneratorTreeSequoia() {
    super(Builder.builder()
      .name("sequoia")
      .rainInfo(250f, 420f)
      .tempInfo(-5f, 12f)
      .radius(3)
      .height(24)
      .decayDist(6)
      .minGrowthTime(18)
      .density(0.4f, 0.9f)
      //.generator(GEN_SEQUOIA)
      .paramMap(0.20f, 36f, 9, 4, 0.70f)
      .growthLogicKit("conifer")
      .cellKit("conifer")
      .isConifer()
      .bushes()
      .isThick()
      .log(() -> BlocksWood.LOG.get(WoodTypes.BLACKWOOD).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.BLACKWOOD).getDefaultState())
    );
  }


}
