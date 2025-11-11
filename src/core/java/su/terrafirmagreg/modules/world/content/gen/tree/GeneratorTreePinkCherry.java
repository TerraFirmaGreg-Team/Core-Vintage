package su.terrafirmagreg.modules.world.content.gen.tree;

import su.terrafirmagreg.framework.manager.content.base.generation.spi.BaseGeneratorTree;
import su.terrafirmagreg.modules.wood.api.type.WoodTypes;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

import net.dries007.tfc.objects.items.food.ItemFoodTFC;
import net.dries007.tfc.util.agriculture.Food;

import static net.dries007.tfc.objects.blocks.wood.BlockLogTFC.PLACED;

public class GeneratorTreePinkCherry extends BaseGeneratorTree {


  public GeneratorTreePinkCherry() {
    super(Builder.builder()
      .name("pink_cherry")
      .rainInfo(180f, 300f)
      .tempInfo(0f, 20f)
      .minGrowthTime(8)
      .density(0.25f, 2f)
      //.generator(GEN_PINK_CHERRY)
      .fruit(() -> ItemFoodTFC.get(Food.CHERRY), 0.33f)
      .stages(new int[]{0, 0, 1, 2, 2, 1, 1, 3, 4, 4, 0, 0})
      .paramMap(0.30f, 16f, 3, 3, 0.85f)
      .growthLogicKit("darkoak")
      .cellKit("deciduous")
      .log(() -> BlocksWood.LOG.get(WoodTypes.ACACIA).getDefaultState().withProperty(PLACED, false))
      .leaves(() -> BlocksWood.LEAVES.get(WoodTypes.ACACIA).getDefaultState())

    );
  }


}
