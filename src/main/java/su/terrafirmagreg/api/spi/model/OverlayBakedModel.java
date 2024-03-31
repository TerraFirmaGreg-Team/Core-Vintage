package su.terrafirmagreg.api.spi.model;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public class OverlayBakedModel implements IBakedModel {
	List<BakedQuad> quads;
	TextureAtlasSprite textureAtlasSprite;

	public OverlayBakedModel(List<BakedQuad> quads, TextureAtlasSprite textureAtlasSprite) {
		this.quads = quads;
		this.textureAtlasSprite = textureAtlasSprite;
	}

	@Override
	public @NotNull List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
		return quads;
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
		return textureAtlasSprite;
	}

	@Override
	public @NotNull ItemOverrideList getOverrides() {
		return ItemOverrideList.NONE;
	}

	ItemTransformVec3f firstperson_righthand = new ItemTransformVec3f(new Vector3f(0, 45, 0), new Vector3f(0, 0, 0), new Vector3f(0.40f, 0.40f, 0.40f));
	ItemTransformVec3f firstperson_lefthand = new ItemTransformVec3f(new Vector3f(0, 225, 0), new Vector3f(0, 0, 0), new Vector3f(0.40f, 0.40f, 0.40f));

	ItemTransformVec3f thirdperson_righthand = new ItemTransformVec3f(new Vector3f(75, 45, 0), new Vector3f(0, 0.15f, 0), new Vector3f(0.375f, 0.375f, 0.375f));

	private ItemTransformVec3f fixed = new ItemTransformVec3f(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(0.5f, 0.5f, 0.5f));
	private ItemTransformVec3f ground = new ItemTransformVec3f(new Vector3f(0, 0, 0), new Vector3f(0, 0.15f, 0), new Vector3f(0.25f, 0.25f, 0.25f));
	private ItemTransformVec3f gui = new ItemTransformVec3f(new Vector3f(30, 225, 0), new Vector3f(0, 0, 0), new Vector3f(0.625f, 0.625f, 0.625f));


	private ItemCameraTransforms itemCameraTransforms = new ItemCameraTransforms(thirdperson_righthand, thirdperson_righthand,
			firstperson_lefthand, firstperson_righthand,
			ItemTransformVec3f.DEFAULT, gui, ground, fixed);

	@Override
	public ItemCameraTransforms getItemCameraTransforms() {

		return itemCameraTransforms;
	}
}
