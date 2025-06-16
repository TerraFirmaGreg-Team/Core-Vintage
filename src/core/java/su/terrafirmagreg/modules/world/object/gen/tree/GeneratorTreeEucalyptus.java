package su.terrafirmagreg.modules.world.object.gen.tree;

import su.terrafirmagreg.framework.manager.registry.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.api.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeEucalyptus extends BaseGeneratorTree {


  public GeneratorTreeEucalyptus() {
    super(Builder.builder()
      .name("eucalyptus")
      .rainInfo(320f, 500f)
      .tempInfo(22f, 50f)
      .radius(1)
      .decayDist(6)
      .minGrowthTime(18)
      .density(0.5f, 2f)
      .bushes()
      //.generator(GEN_ANGELIM)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.ACACIA).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.ACACIA).getDefaultState())

    );
  }


}
