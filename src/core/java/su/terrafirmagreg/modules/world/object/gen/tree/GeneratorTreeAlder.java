package su.terrafirmagreg.modules.world.object.gen.tree;

import su.terrafirmagreg.framework.manager.registry.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeAlder extends BaseGeneratorTree {


  public GeneratorTreeAlder() {
    super(Builder.builder()
      .name("alder")
      .rainInfo(60f, 400f)
      .tempInfo(-4f, 13f)
      .minGrowthTime(8)
      .bushes()
      .density(0.25f, 2f)
      //.generator(GEN_ALDER)
      .fruit(null, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 1, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.ACACIA).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.ACACIA).getDefaultState())

    );
  }


}
