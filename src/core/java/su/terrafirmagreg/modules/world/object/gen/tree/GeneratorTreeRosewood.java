package su.terrafirmagreg.modules.world.object.gen.tree;

import su.terrafirmagreg.framework.manager.registry.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeRosewood extends BaseGeneratorTree {

  public GeneratorTreeRosewood() {
    super(Builder.builder()
      .name("rosewood")
      .rainInfo(10f, 190f)
      .tempInfo(8f, 18f)
      .height(12)
      .minGrowthTime(8)
      //.generator(GEN_MEDIUM)
      .paramMap(0.35f, 15f, 7, 3, 1.00f)
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.BLACKWOOD).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.BLACKWOOD).getDefaultState())
    );
  }


}
