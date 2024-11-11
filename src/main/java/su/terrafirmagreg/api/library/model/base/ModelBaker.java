package su.terrafirmagreg.api.library.model.base;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.model.IModelState;

import com.google.common.collect.ImmutableList;

import lombok.Getter;

import java.util.List;

public class ModelBaker {

  @Getter
  private static final ModelBaker instance = new ModelBaker();
  private IModelState currentState;
  private VertexFormat currentFormat;
  private ImmutableList.Builder<BakedQuad> builder;
  private TextureAtlasSprite texture;
  private boolean readyForUse;
  private boolean alreadyBuilding;

  private ModelBaker() {
  }

  public ModelBaker begin(IModelState state, VertexFormat format) {
    if (readyForUse || alreadyBuilding) {
      throw new ModelBakerException("Already bake!");
    }
    this.currentState = state;
    this.currentFormat = format;
    builder = ImmutableList.builder();
    readyForUse = true;
    return this;
  }

  public ModelBaker setTexture(TextureAtlasSprite texture) {
    if (!readyForUse) {
      throw new ModelBakerException("Not ready to put quad!");
    }
    this.texture = texture;
    return this;
  }

  public ModelBaker putTexturedCube(float offsetX, float offsetY, float offsetZ, float size) {
    if (texture == null) {
      throw new ModelBakerException("No texture!");
    }
    putCube(offsetX, offsetY, offsetZ, size, texture.getMinU(), texture.getMaxU(),
            texture.getMinV(), texture.getMaxV());
    return this;
  }

  public ModelBaker putCube(float offsetX, float offsetY, float offsetZ, float size, float uMin,
                            float uMax, float vMin, float vMax) {
    putCube(offsetX, offsetY, offsetZ, size, size, size, uMin, uMax, vMin, vMax);
    return this;
  }

  public ModelBaker putCube(float offsetX, float offsetY, float offsetZ, float scaleX, float scaleY,
                            float scaleZ, float uMin, float uMax,
                            float vMin, float vMax) {
    for (EnumFacing facing : EnumFacing.values()) {
      putQuad(offsetX, offsetY, offsetZ, scaleX, scaleY, scaleZ, uMin, uMax, vMin, vMax, facing);
    }
    return this;
  }

  public ModelBaker putQuad(float offsetX, float offsetY, float offsetZ, float scaleX, float scaleY,
                            float scaleZ, float uMin, float uMax,
                            float vMin, float vMax, EnumFacing rotate) {
    switch (rotate) {
      case NORTH:
        putQuad(
          offsetX + 0.5f - scaleX, offsetY + 0.5f - scaleY, offsetZ + 0.5f - scaleZ, uMin, vMax,
          offsetX + 0.5f - scaleX, offsetY + 0.5f + scaleY, offsetZ + 0.5f - scaleZ, uMin, vMin,
          offsetX + 0.5f + scaleX, offsetY + 0.5f + scaleY, offsetZ + 0.5f - scaleZ, uMax, vMin,
          offsetX + 0.5f + scaleX, offsetY + 0.5f - scaleY, offsetZ + 0.5f - scaleZ, uMax, vMax,
          EnumFacing.NORTH
        );
        break;
      case SOUTH:
        putQuad(
          offsetX + 0.5f + scaleX, offsetY + 0.5f - scaleY, offsetZ + 0.5f + scaleZ, uMin, vMax,
          offsetX + 0.5f + scaleX, offsetY + 0.5f + scaleY, offsetZ + 0.5f + scaleZ, uMin, vMin,
          offsetX + 0.5f - scaleX, offsetY + 0.5f + scaleY, offsetZ + 0.5f + scaleZ, uMax, vMin,
          offsetX + 0.5f - scaleX, offsetY + 0.5f - scaleY, offsetZ + 0.5f + scaleZ, uMax, vMax,
          EnumFacing.SOUTH
        );
        break;
      case EAST:
        putQuad(
          offsetX + 0.5f + scaleX, offsetY + 0.5f - scaleY, offsetZ + 0.5f - scaleZ, uMin, vMax,
          offsetX + 0.5f + scaleX, offsetY + 0.5f + scaleY, offsetZ + 0.5f - scaleZ, uMin, vMin,
          offsetX + 0.5f + scaleX, offsetY + 0.5f + scaleY, offsetZ + 0.5f + scaleZ, uMax, vMin,
          offsetX + 0.5f + scaleX, offsetY + 0.5f - scaleY, offsetZ + 0.5f + scaleZ, uMax, vMax,
          EnumFacing.EAST
        );
        break;
      case WEST:
        putQuad(
          offsetX + 0.5f - scaleX, offsetY + 0.5f - scaleY, offsetZ + 0.5f + scaleZ, uMin, vMax,
          offsetX + 0.5f - scaleX, offsetY + 0.5f + scaleY, offsetZ + 0.5f + scaleZ, uMin, vMin,
          offsetX + 0.5f - scaleX, offsetY + 0.5f + scaleY, offsetZ + 0.5f - scaleZ, uMax, vMin,
          offsetX + 0.5f - scaleX, offsetY + 0.5f - scaleY, offsetZ + 0.5f - scaleZ, uMax, vMax,
          EnumFacing.WEST
        );
        break;
      case DOWN:
        putQuad(
          offsetX + 0.5f - scaleX, offsetY + 0.5f - scaleY, offsetZ + 0.5f - scaleZ, uMin, vMax,
          offsetX + 0.5f + scaleX, offsetY + 0.5f - scaleY, offsetZ + 0.5f - scaleZ, uMin, vMin,
          offsetX + 0.5f + scaleX, offsetY + 0.5f - scaleY, offsetZ + 0.5f + scaleZ, uMax, vMin,
          offsetX + 0.5f - scaleX, offsetY + 0.5f - scaleY, offsetZ + 0.5f + scaleZ, uMax, vMax,
          EnumFacing.DOWN
        );
        break;
      case UP:
        putQuad(
          offsetX + 0.5f + scaleX, offsetY + 0.5f + scaleY, offsetZ + 0.5f + scaleZ, uMin, vMax,
          offsetX + 0.5f + scaleX, offsetY + 0.5f + scaleY, offsetZ + 0.5f - scaleZ, uMin, vMin,
          offsetX + 0.5f - scaleX, offsetY + 0.5f + scaleY, offsetZ + 0.5f - scaleZ, uMax, vMin,
          offsetX + 0.5f - scaleX, offsetY + 0.5f + scaleY, offsetZ + 0.5f + scaleZ, uMax, vMax,
          EnumFacing.UP
        );
        break;
    }
    return this;
  }

  public ModelBaker putQuad(float x0, float y0, float z0, float u0, float v0,
                            float x1, float y1, float z1, float u1, float v1,
                            float x2, float y2, float z2, float u2, float v2,
                            float x3, float y3, float z3, float u3, float v3, EnumFacing side) {
    if (!readyForUse) {
      throw new ModelBakerException("Not ready to put quad!");
    }
    alreadyBuilding = true;
    QuadBuilder builder = QuadBuilder.INSTANCE;
    builder.begin(currentState, currentFormat, 0, side);
    if (texture != null) {
      builder.setTexture(texture);
      builder.tex(u0, v0).putVertex(x0, y0, z0);
      builder.tex(u1, v1).putVertex(x1, y1, z1);
      builder.tex(u2, v2).putVertex(x2, y2, z2);
      builder.tex(u3, v3).putVertex(x3, y3, z3);
    } else {
      builder.putVertex(x0, y0, z0);
      builder.putVertex(x1, y1, z1);
      builder.putVertex(x2, y2, z2);
      builder.putVertex(x3, y3, z3);
    }
    putQuad(builder.build());
    return this;
  }

  public ModelBaker putQuad(BakedQuad quad) {
    builder.add(quad);
    return this;
  }

  public List<BakedQuad> bake() {
    if (!alreadyBuilding) {
      throw new ModelBakerException("Not baked!");
    }
    readyForUse = false;
    alreadyBuilding = false;
    texture = null;
    return builder.build();
  }
}
