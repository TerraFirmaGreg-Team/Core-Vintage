package su.terrafirmagreg.modules.world.object.gen.tree;

import su.terrafirmagreg.framework.manager.registry.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeBlackwood extends BaseGeneratorTree {

  public GeneratorTreeBlackwood() {
    super(Builder.builder()
      .name("blackwood")
      .rainInfo(0f, 120f)
      .tempInfo(4f, 33f)
      .height(12)
      .minGrowthTime(8)
      //.generator(GEN_MEDIUM)
      .paramMap(0.20f, 13f, 3, 4, 0.90f)
      .growthLogicKit("darkoak")
      .cellKit("darkoak")
      .log(() -> BlocksWood.LOG.get(WoodTypes.BLACKWOOD).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.BLACKWOOD).getDefaultState())
    );
  }


}
