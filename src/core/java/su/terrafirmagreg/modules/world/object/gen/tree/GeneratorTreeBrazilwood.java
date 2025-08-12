package su.terrafirmagreg.modules.world.object.gen.tree;

import su.terrafirmagreg.framework.manager.registry.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeBrazilwood extends BaseGeneratorTree {


  public GeneratorTreeBrazilwood() {
    super(Builder.builder()
      .name("brazilwood")
      .rainInfo(290f, 550f)
      .tempInfo(14f, 37f)
      .minGrowthTime(8)
      .bushes()
      .density(0.25f, 1f)
      //.generator(GEN_BRAZILWOOD)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.ACACIA).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.ACACIA).getDefaultState())

    );
  }


}
