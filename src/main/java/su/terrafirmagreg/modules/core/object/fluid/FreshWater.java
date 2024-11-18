package su.terrafirmagreg.modules.core.object.fluid;

import su.terrafirmagreg.api.base.block.BaseBlockFluid;
import su.terrafirmagreg.api.base.fluid.BaseFluid;
import su.terrafirmagreg.modules.food.api.FoodStatsTFC;

import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;

import static net.dries007.tfc.objects.fluids.properties.DrinkableProperty.DRINKABLE;

public class FreshWater extends BaseFluid {

  public FreshWater() {
    super(Settings.of("fresh_water"));

    getSettings()
      .color(0xFF296ACD)
      .block(BlockFluidFreshWater::new)
      .property(DRINKABLE, player -> {
        if (player.getFoodStats() instanceof FoodStatsTFC foodStats) {
          foodStats.addThirst(40);
        }
      });
  }

  public static class BlockFluidFreshWater extends BaseBlockFluid {
    
    public BlockFluidFreshWater(Fluid fluid) {
      super(fluid, Material.WATER, false);

    }

  }
}
