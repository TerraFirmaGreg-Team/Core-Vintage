package su.terrafirmagreg.modules.world.object.gen.tree;

import su.terrafirmagreg.framework.manager.registry.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeMaple extends BaseGeneratorTree {

  public GeneratorTreeMaple() {
    super(Builder.builder()
      .name("maple")
      .rainInfo(140f, 360f)
      .tempInfo(3f, 20f)
      .dominance(6.3f)
      .radius(1)
      //.generator(GEN_MEDIUM_2)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.15f, 15f, 6, 3, 0.95f)
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.BLACKWOOD).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.BLACKWOOD).getDefaultState())
    );
  }


}
