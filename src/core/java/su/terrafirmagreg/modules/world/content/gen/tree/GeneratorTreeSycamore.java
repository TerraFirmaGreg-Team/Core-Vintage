package su.terrafirmagreg.modules.world.content.gen.tree;

import su.terrafirmagreg.framework.manager.content.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.feature.woodtype.types.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import net.dries007.tfc.objects.items.ItemsTFCF;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeSycamore extends BaseGeneratorTree {

  public GeneratorTreeSycamore() {
    super(Builder.builder()
      .name("sycamore")
      .rainInfo(10f, 240f)
      .tempInfo(-8f, 17f)
      .height(16)
      .bushes()
      //.generator(GEN_TALL_SINGLE)
      .fruit(() -> ItemsTFCF.JUNIPER, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
      .paramMap(0.15f, 20f, 6, 2, 1.10f)
      .growthLogicKit("conifer")
      .cellKit("deciduous")
      .isConifer()
      .log(() -> BlocksWood.LOG.get(WoodTypes.BLACKWOOD).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.BLACKWOOD).getDefaultState())
    );
  }


}
