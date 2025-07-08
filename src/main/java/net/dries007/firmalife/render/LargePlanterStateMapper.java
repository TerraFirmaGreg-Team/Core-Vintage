package net.dries007.firmalife.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;

import javax.annotation.Nonnull;

import static su.terrafirmagreg.api.data.enums.Mods.ModIDs.FL;

public class LargePlanterStateMapper extends StateMapperBase {

  @Override
  @Nonnull
  protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
    return new ModelResourceLocation(FL + ":large_planter");
  }
}
