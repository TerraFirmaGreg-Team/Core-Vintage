package su.terrafirmagreg.modules.world.content.gen.tree;

import su.terrafirmagreg.framework.manager.content.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeWillow extends BaseGeneratorTree {


  public GeneratorTreeWillow() {
    super(Builder.builder()
      .name("willow")
      .rainInfo(230f, 400f)
      .tempInfo(15f, 32f)
      .minGrowthTime(11)
      .density(0.7f, 2f)
      .radius(1)
      //.generator(GEN_WILLOW)
      .paramMap(0.55f, 15f, 2, 2, 1.40f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .bushes()
      .log(() -> BlocksWood.LOG.get(WoodTypes.ACACIA).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.ACACIA).getDefaultState())

    );
  }


}
