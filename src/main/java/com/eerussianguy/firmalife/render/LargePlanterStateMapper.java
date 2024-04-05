package com.eerussianguy.firmalife.render;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import org.jetbrains.annotations.NotNull;

import static su.terrafirmagreg.api.lib.Constants.MODID_FL;

public class LargePlanterStateMapper extends StateMapperBase {
	@Override
	@NotNull
	protected ModelResourceLocation getModelResourceLocation(@NotNull IBlockState state) {
		return new ModelResourceLocation(MODID_FL + ":large_planter");
	}
}
