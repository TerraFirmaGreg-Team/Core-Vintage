package su.terrafirmagreg.modules.world.content.gen.tree;

import su.terrafirmagreg.framework.manager.content.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import net.dries007.tfc.objects.items.ItemsTFCF;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeHawthorn extends BaseGeneratorTree {


  public GeneratorTreeHawthorn() {
    super(Builder.builder()
      .name("hawthorn")
      .rainInfo(180f, 400f)
      .tempInfo(-8f, 14f)
      .minGrowthTime(8)
      .density(0.25f, 1f)
      //.generator(GEN_HAWTHORN)
      .fruit(() -> ItemsTFCF.HAWTHORN, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.ACACIA).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.ACACIA).getDefaultState())

    );
  }


}
