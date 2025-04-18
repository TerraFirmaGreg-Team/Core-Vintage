package net.dries007.firmalife.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;

import javax.annotation.Nonnull;

import static net.dries007.firmalife.FirmaLife.MOD_ID;

public class QuadPlanterStateMapper extends StateMapperBase {

  @Override
  @Nonnull
  protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
    return new ModelResourceLocation(MOD_ID + ":quad_planter");
  }
}
