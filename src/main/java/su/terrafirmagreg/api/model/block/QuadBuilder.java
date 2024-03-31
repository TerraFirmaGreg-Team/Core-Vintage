package su.terrafirmagreg.api.model.block;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.UnpackedBakedQuad;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import su.terrafirmagreg.api.util.ModelUtils;

import java.util.Optional;

public class QuadBuilder {
	public static final QuadBuilder INSTANCE = new QuadBuilder();
	private float r = 1, g = 1, b = 1, a = 1, u, v;
	private boolean textured;

	private VertexFormat currentFormat;
	private Optional<TRSRTransformation> transform;
	private UnpackedBakedQuad.Builder builderQ;

	private boolean readyForUse;
	private boolean alreadyBuilding;
	private EnumFacing currentSide;

	private QuadBuilder() {

	}

	public QuadBuilder begin(IModelState state, VertexFormat format, int tint, EnumFacing side) {
		if (readyForUse || alreadyBuilding) throw new QuadBuilderException("Already building!");
		currentFormat = format;
		transform = state.apply(Optional.empty());
		builderQ = new UnpackedBakedQuad.Builder(format);
		builderQ.setQuadTint(tint);
		builderQ.setQuadOrientation(side);
		currentSide = side;
		readyForUse = true;
		return this;
	}

	public QuadBuilder color(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
		return this;
	}

	public QuadBuilder tex(float u, float v) {
		this.u = u;
		this.v = v;
		return this;
	}

	public QuadBuilder setTexture(TextureAtlasSprite texture) {
		if (alreadyBuilding) throw new QuadBuilderException("Already building!");
		this.textured = true;
		builderQ.setTexture(texture);
		return this;
	}

	public QuadBuilder putVertex(float x, float y, float z) {
		if (!readyForUse) throw new QuadBuilderException("Not ready to put vertex!");
		alreadyBuilding = true;

		if (!textured)
			ModelUtils.putVertex(builderQ, currentFormat, transform, currentSide, x, y, z, -1, -1, r, g, b, a);
		else
			ModelUtils.putVertex(builderQ, currentFormat, transform, currentSide, x, y, z, u, v, r, g, b, a);
		return this;
	}

	public BakedQuad build() {
		if (!alreadyBuilding) throw new QuadBuilderException("Not building!");
		readyForUse = false;
		alreadyBuilding = false;
		textured = false;
		this.r = 1;
		this.g = 1;
		this.b = 1;
		this.a = 1;
		this.u = 0;
		this.v = 0;
		return builderQ.build();
	}
}
