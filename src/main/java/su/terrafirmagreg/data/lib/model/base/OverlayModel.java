package su.terrafirmagreg.data.lib.model.base;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

public class OverlayModel implements IModel {

  public ResourceLocation texture;
  public ResourceLocation overlayTexture;
  MultiTextureAtlasSprite sprite;

  public OverlayModel(ResourceLocation texture, ResourceLocation overlayTexture) {
    this.texture = texture;
    this.overlayTexture = overlayTexture;
    sprite = new MultiTextureAtlasSprite(overlayTexture, texture);
    TextureEventHandler.registerSprite(sprite);
  }

  @Override
  public @NotNull Collection<ResourceLocation> getTextures() {
    return Collections.singletonList(texture);
  }

  @Override
  public @NotNull IBakedModel bake(@NotNull IModelState state, @NotNull VertexFormat format,
                                   Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {

    ModelBaker baker = ModelBaker.getInstance();
    baker.begin(state, format);
    baker.setTexture(sprite);
    baker.putCube(0, 0, 0, 0.5f, sprite.getMaxU(), sprite.getMinU(), sprite.getMinV(),
                  sprite.getMaxV());
    return new OverlayBakedModel(baker.bake(), bakedTextureGetter.apply(overlayTexture));
  }

  @Override
  public @NotNull IModel smoothLighting(boolean value) {
    return new OverlayModel(texture, overlayTexture);
  }

}
