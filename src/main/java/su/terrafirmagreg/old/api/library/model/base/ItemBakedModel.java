package su.terrafirmagreg.old.api.library.model.base;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.block.model.ItemTransformVec3f;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;

import org.lwjgl.util.vector.Vector3f;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemBakedModel implements IBakedModel {

  List<BakedQuad> quads;
  TextureAtlasSprite textureAtlasSprite;
  //Трансформация предмета когда он выброшен в мире(EntityItem)
  ItemTransformVec3f entity = new ItemTransformVec3f(
    new Vector3f(0f, 0, 0), /*Поворот*/
    new Vector3f(0.005f, 0.15f, 0.04f), /*Смещение*/
    new Vector3f(0.55f, 0.55f, 0.55f)); /*Размер*/
  //От третьего лица в правой руке
  ItemTransformVec3f thirdPersonRight = new ItemTransformVec3f(new Vector3f(0f, -90, -160),
                                                               new Vector3f(0.005f, -0.35f, 0.04f),
                                                               new Vector3f(0.55f, 0.55f, 0.55f));
  //От третьего лица в левой руке
  ItemTransformVec3f thirdPersonLeft = new ItemTransformVec3f(new Vector3f(0f, -90, 150),
                                                              new Vector3f(0.005f, -0.40f, 0.1f),
                                                              new Vector3f(0.55f, 0.55f, 0.55f));
  //От первого лица в правой руке
  ItemTransformVec3f firstPersonRight = new ItemTransformVec3f(new Vector3f(-140, -90, 25),
                                                               new Vector3f(0.03f, 0.23f, 0.104f),
                                                               new Vector3f(0.68f, 0.68f, 0.68f));
  //От первого лица в левой руке
  ItemTransformVec3f firstPersonLeft = new ItemTransformVec3f(new Vector3f(-120, -90, 25),
                                                              new Vector3f(0.03f, 0.08f, 0.104f),
                                                              new Vector3f(0.68f, 0.68f, 0.68f));
  //Общая трансформация. Вместо некоторый отдельных ItemTransformVec3f, мы добавляем дефолтные, тк они не будут использованы обычным предметом
  ItemCameraTransforms itemCameraTransforms = new ItemCameraTransforms(thirdPersonLeft,
                                                                       thirdPersonRight, firstPersonLeft, firstPersonRight,
                                                                       ItemTransformVec3f.DEFAULT, ItemTransformVec3f.DEFAULT, entity, ItemTransformVec3f.DEFAULT);

  public ItemBakedModel(List<BakedQuad> quads, TextureAtlasSprite textureAtlasSprite) {
    this.quads = quads;
    this.textureAtlasSprite = textureAtlasSprite;
  }

  //возвращаем квадраты для рендера
  @Override
  public @NotNull List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side,
                                           long rand) {
    return quads;
  }

  //Пусть на нашу модельку действуем мягкое освещение
  @Override
  public boolean isAmbientOcclusion() {
    return false;
  }

  //Делаем модельку плоской в GUI
  @Override
  public boolean isGui3d() {
    return false;
  }

  //У нашего предмета нет TileEntityItemStackRenderer
  @Override
  public boolean isBuiltInRenderer() {
    return false;
  }

  //Текстура частиц пусть будет такой же как и текстура предмета
  @Override
  public @NotNull TextureAtlasSprite getParticleTexture() {
    return textureAtlasSprite;
  }

  @Override
  public @NotNull ItemCameraTransforms getItemCameraTransforms() {
    return itemCameraTransforms;
  }

  //Стандартный лист дефолтных трансформаций
  @Override
  public @NotNull ItemOverrideList getOverrides() {
    return ItemOverrideList.NONE;
  }

}

