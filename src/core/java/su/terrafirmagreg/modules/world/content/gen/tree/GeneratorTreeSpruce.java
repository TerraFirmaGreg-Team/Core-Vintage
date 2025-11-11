package su.terrafirmagreg.modules.world.content.gen.tree;

import su.terrafirmagreg.framework.manager.content.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.api.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeSpruce extends BaseGeneratorTree {

  public GeneratorTreeSpruce() {
    super(Builder.builder()
      .name("spruce")
      .rainInfo(120f, 380f)
      .tempInfo(-11f, 6f)
      .density(0.1f, 0.8f)
      .radius(1)
      //.generator(GEN_CONIFER)
      .paramMap(0.15f, 12f, 6, 3, 1.10f)
      .growthLogicKit("conifer")
      .cellKit("conifer")
      .isConifer()
      .log(() -> BlocksWood.LOG.get(WoodTypes.BLACKWOOD).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.BLACKWOOD).getDefaultState())
    );
  }


}
