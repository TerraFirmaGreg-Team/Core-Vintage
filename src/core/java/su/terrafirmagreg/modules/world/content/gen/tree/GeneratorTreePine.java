package su.terrafirmagreg.modules.world.content.gen.tree;

import su.terrafirmagreg.framework.manager.content.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreePine extends BaseGeneratorTree {

  public GeneratorTreePine() {
    super(Builder.builder()
      .name("pine")
      .rainInfo(60f, 250f)
      .tempInfo(-15f, 7f)
      .density(0.1f, 0.8f)
      .radius(1)
      //.generator(GEN_CONIFER)
      .paramMap(0.20f, 18f, 6, 2, 1.20f)
      .growthLogicKit("conifer")
      .cellKit("conifer")
      .isConifer()
      .log(() -> BlocksWood.LOG.get(WoodTypes.BLACKWOOD).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.BLACKWOOD).getDefaultState())
    );
  }


}
