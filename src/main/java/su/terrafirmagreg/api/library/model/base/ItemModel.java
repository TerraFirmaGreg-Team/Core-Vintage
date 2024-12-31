package su.terrafirmagreg.api.library.model.base;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ItemLayerModel;
import net.minecraftforge.common.model.IModelState;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

public class ItemModel implements IModel {

  final ResourceLocation texture;

  public ItemModel(ResourceLocation texture) {
    this.texture = texture;
  }

  //Добавляем текстурки которые будем использовать
  @Override
  public @NotNull Collection<ResourceLocation> getTextures() {
    return Lists.newArrayList(texture);
  }

  //Строим саму модель
  @Override
  public @NotNull IBakedModel bake(IModelState state, @NotNull VertexFormat format,
                                   Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {

    //получаем TextureAtlasSprite из ResourceLocation
    TextureAtlasSprite textureAtlasSprite = bakedTextureGetter.apply(texture);

    //Получаем список квадратов из текструки, для этого уже есть удобный метод.
    ImmutableList<BakedQuad> quads = ItemLayerModel.getQuadsForSprite(0, textureAtlasSprite, format,
                                                                      state.apply(Optional.empty()));
    return new ItemBakedModel(quads, textureAtlasSprite);
  }
}

