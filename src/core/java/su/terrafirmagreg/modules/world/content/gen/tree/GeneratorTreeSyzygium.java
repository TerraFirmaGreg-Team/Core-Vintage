package su.terrafirmagreg.modules.world.content.gen.tree;

import su.terrafirmagreg.framework.manager.content.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.api.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import net.dries007.tfc.objects.items.ItemsTFCF;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreeSyzygium extends BaseGeneratorTree {


  public GeneratorTreeSyzygium() {
    super(Builder.builder()
      .name("syzygium")
      .rainInfo(140f, 360f)
      .tempInfo(13f, 35f)
      .decayDist(6)
      .minGrowthTime(16)
      .bushes()
      .density(0.2f, 1f)
      //.generator(GEN_SYZYGIUM)
      .fruit(() -> ItemsTFCF.RIBERRY, 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.SYZYGIUM).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.SYZYGIUM).getDefaultState())

    );
  }


}
