package su.terrafirmagreg.modules.world.object.gen.tree;

import su.terrafirmagreg.framework.manager.registry.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeNorwaySpruce extends BaseGeneratorTree {


  public GeneratorTreeNorwaySpruce() {
    super(Builder.builder()
      .name("norway_spruce")
      .rainInfo(100f, 380f)
      .tempInfo(-20f, 5f)
      .minGrowthTime(8)
      .isConifer()
      .density(0.1f, 0.9f)
      //.generator(GEN_NORWAY_SPRUCE)
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.ACACIA).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.ACACIA).getDefaultState())

    );
  }


}
