package su.terrafirmagreg.modules.world.object.gen.tree;

import su.terrafirmagreg.framework.manager.registry.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.api.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeBirch extends BaseGeneratorTree {

  public GeneratorTreeBirch() {
    super(Builder.builder()
      .name("birch")
      .rainInfo(20f, 180f)
      .tempInfo(-15f, 7f)
      .radius(1)
      //.generator(GEN_TALL_2)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.25f, 12f, 5, 5, 1.15f)
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.BIRCH).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.BIRCH).getDefaultState())
    );
  }


}
