package su.terrafirmagreg.modules.world.object.gen.tree;

import su.terrafirmagreg.framework.manager.registry.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeBlackWalnut extends BaseGeneratorTree {


  public GeneratorTreeBlackWalnut() {
    super(Builder.builder()
      .name("acacia")
      .rainInfo(30f, 210f)
      .tempInfo(19f, 31f)
      .height(12)
      .minGrowthTime(11)
      .density(0.1f, 0.6f)
      //.generator(GEN_ACACIA)
      .paramMap(0.10f, 14f, 6, 6, 0.90f)
      .cellKit("acacia")
      .log(() -> BlocksWood.LOG.get(WoodTypes.ACACIA).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.ACACIA).getDefaultState())

    );
  }


}
