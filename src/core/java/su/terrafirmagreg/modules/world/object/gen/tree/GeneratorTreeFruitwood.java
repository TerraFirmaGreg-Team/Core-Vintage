package su.terrafirmagreg.modules.world.object.gen.tree;

import su.terrafirmagreg.framework.manager.registry.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeFruitwood extends BaseGeneratorTree {


  public GeneratorTreeFruitwood() {
    super(Builder.builder()
      .name("fruitwood")
      .rainInfo(180f, 550f)
      .tempInfo(11f, 30f)
      .dominance(0)
      .minGrowthTime(9)
      .bushes()
      .density(0f, 0f)
      //.generator(GEN_FRUITWOOD)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.FRUITWOOD).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.FRUITWOOD).getDefaultState())

    );
  }


}
