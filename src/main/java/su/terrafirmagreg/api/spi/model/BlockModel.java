package su.terrafirmagreg.api.spi.model;


import com.google.common.collect.Lists;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.model.block.ModelBaker;

import java.util.Collection;
import java.util.function.Function;

public class BlockModel implements IModel {
	final ResourceLocation loc;

	public BlockModel(ResourceLocation loc) {
		this.loc = loc;
	}

	@Override
	public @NotNull IBakedModel bake(@NotNull IModelState state, @NotNull VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
		//текстурка
		TextureAtlasSprite sprite = bakedTextureGetter.apply(loc);

		//Получаем экземпляр для лучшей читабельности
		ModelBaker baker = ModelBaker.getInstance();
		//начинаем создание модели
		baker.begin(state, format);
		//привязываем текстурку
		baker.setTexture(sprite);
		//создаем куб размером 0.5 - это полная величина, т.е. куб будет размером с полноценный блок(1 метр)
		baker.putTexturedCube(0, 0.5f, 0, 0.5f);

		/*Испекаем нашу модель*/
		return new BlockBakedModel(baker.bake(), bakedTextureGetter.apply(loc), format, state);
	}

	@Override
	public @NotNull Collection<ResourceLocation> getTextures() {
		return Lists.newArrayList(loc);
	}


}

