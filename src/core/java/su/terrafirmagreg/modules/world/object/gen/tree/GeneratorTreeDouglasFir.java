package su.terrafirmagreg.modules.world.object.gen.tree;

import su.terrafirmagreg.framework.manager.registry.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeDouglasFir extends BaseGeneratorTree {

  public GeneratorTreeDouglasFir() {
    super(Builder.builder()
      .name("douglas_fir")
      .rainInfo(280f, 480f)
      .tempInfo(-2f, 14f)
      .dominance(5.2f)
      .height(16)
      .density(0.25f, 2f)
      //.generator(GEN_TALL)
      .paramMap(0.15f, 20f, 5, 3, 1.15f)
      .growthLogicKit("conifer")
      .cellKit("conifer")
      .bushes()
      .isConifer()
      .log(() -> BlocksWood.LOG.get(WoodTypes.BLACKWOOD).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.BLACKWOOD).getDefaultState())
    );
  }


}
