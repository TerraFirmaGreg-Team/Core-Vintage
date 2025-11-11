package su.terrafirmagreg.modules.world.content.gen.tree;

import su.terrafirmagreg.framework.manager.content.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.api.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeJoshua extends BaseGeneratorTree {


  public GeneratorTreeJoshua() {
    super(Builder.builder()
      .name("joshua")
      .rainInfo(30f, 210f)
      .tempInfo(11f, 36f)
      .decayDist(6)
      .minGrowthTime(11)
      .bushes()
      .density(0.1f, 0.6f)
      //.generator(GEN_IRONWOOD)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.ACACIA).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.ACACIA).getDefaultState())

    );
  }


}
