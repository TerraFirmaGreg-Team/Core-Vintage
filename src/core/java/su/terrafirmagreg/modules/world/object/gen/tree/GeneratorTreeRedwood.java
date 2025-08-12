package su.terrafirmagreg.modules.world.object.gen.tree;

import su.terrafirmagreg.framework.manager.registry.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import net.dries007.tfc.objects.items.ItemsTFCF;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeRedwood extends BaseGeneratorTree {


  public GeneratorTreeRedwood() {
    super(Builder.builder()
      .name("redwood")
      .rainInfo(10f, 240f)
      .tempInfo(-8f, 17f)
      .decayDist(6)
      .minGrowthTime(18)
      .bushes()
      .isConifer()
      .bushes()
      .density(0.4f, 2f)
      //.generator(GEN_RED_CEDAR)
      .fruit(() -> ItemsTFCF.JUNIPER, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.ACACIA).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.ACACIA).getDefaultState())

    );
  }


}
