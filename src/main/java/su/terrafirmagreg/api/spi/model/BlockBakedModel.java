package su.terrafirmagreg.api.spi.model;


import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.model.IModelState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public class BlockBakedModel implements IBakedModel {
	List<BakedQuad> bakedQuads;
	TextureAtlasSprite textureParticle;
	VertexFormat format;
	IModelState state;

	public BlockBakedModel(List<BakedQuad> bakedQuads, TextureAtlasSprite textureParticle, VertexFormat format, IModelState state) {
		this.bakedQuads = bakedQuads;
		this.textureParticle = textureParticle;
		this.format = format;
		this.state = state;
	}

	@Override
	public @NotNull List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
		return bakedQuads;
	}

	@Override
	public boolean isAmbientOcclusion() {
		return true;
	}

	@Override
	public boolean isGui3d() {
		return true;
	}

	@Override
	public boolean isBuiltInRenderer() {
		return false;
	}

	@Override
	public @NotNull TextureAtlasSprite getParticleTexture() {
		return textureParticle;
	}

	@Override
	public @NotNull ItemOverrideList getOverrides() {
		return ItemOverrideList.NONE;
	}


	//Копируем все трансформации из block/generated.json

	//Трансформация от первого лица в правой руке
	ItemTransformVec3f firstperson_righthand = new ItemTransformVec3f(new Vector3f(0, 45, 0), new Vector3f(0, 0, 0), new Vector3f(0.40f, 0.40f, 0.40f));
	//От первого лица в левой руке
	ItemTransformVec3f firstperson_lefthand = new ItemTransformVec3f(new Vector3f(0, 225, 0), new Vector3f(0, 0, 0), new Vector3f(0.40f, 0.40f, 0.40f));
	//От третьего лица
	ItemTransformVec3f thirdperson_righthand = new ItemTransformVec3f(new Vector3f(75, 45, 0), new Vector3f(0, 0.15f, 0), new Vector3f(0.375f, 0.375f, 0.375f));
	//На голове
	ItemTransformVec3f fixed = new ItemTransformVec3f(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(0.5f, 0.5f, 0.5f));
	//В виде предмета в мире(EntityItem)
	ItemTransformVec3f ground = new ItemTransformVec3f(new Vector3f(0, 0, 0), new Vector3f(0, 0.15f, 0), new Vector3f(0.25f, 0.25f, 0.25f));
	//В графическом интерфейсе
	ItemTransformVec3f gui = new ItemTransformVec3f(new Vector3f(30, 225, 0), new Vector3f(0, 0, 0), new Vector3f(0.625f, 0.625f, 0.625f));
	//Общая транфсормация
	ItemCameraTransforms itemCameraTransforms = new ItemCameraTransforms(thirdperson_righthand, thirdperson_righthand, firstperson_lefthand, firstperson_righthand, ItemTransformVec3f.DEFAULT, gui, ground, fixed);

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {
		return itemCameraTransforms;
	}


}

