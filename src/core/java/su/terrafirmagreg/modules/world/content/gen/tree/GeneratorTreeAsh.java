package su.terrafirmagreg.modules.world.content.gen.tree;

import su.terrafirmagreg.framework.manager.content.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeAsh extends BaseGeneratorTree {


  public GeneratorTreeAsh() {
    super(Builder.builder()
      .name("ash")
      .rainInfo(60f, 140f)
      .tempInfo(-6f, 12f)
      .bushes()
      //.generator(GEN_NORMAL_2)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.25f, 12f, 4, 3, 1.00f)
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.ASH).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.ASH).getDefaultState())

    );
  }


}
