package su.terrafirmagreg.modules.world.object.gen.tree;

import su.terrafirmagreg.framework.manager.registry.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.api.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreePalm extends BaseGeneratorTree {

  public GeneratorTreePalm() {
    super(Builder.builder()
      .name("palm")
      .rainInfo(280f, 500f)
      .tempInfo(16f, 35f)
      .decayDist(6)
      //.generator(GEN_TROPICAL)
      .paramMap(0.05f, 16f, 5, 4, 1.10f)
      .growthLogicKit("jungle")
      .cellKit("palm")
      .log(() -> BlocksWood.LOG.get(WoodTypes.BLACKWOOD).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.BLACKWOOD).getDefaultState())
    );
  }


}
