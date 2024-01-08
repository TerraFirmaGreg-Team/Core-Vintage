package com.eerussianguy.firmalife.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;

import javax.annotation.Nonnull;

import static su.terrafirmagreg.Constants.MODID_FL;

public class QuadPlanterStateMapper extends StateMapperBase {
	@Override
	@Nonnull
	protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
		return new ModelResourceLocation(MODID_FL + ":quad_planter");
	}
}
