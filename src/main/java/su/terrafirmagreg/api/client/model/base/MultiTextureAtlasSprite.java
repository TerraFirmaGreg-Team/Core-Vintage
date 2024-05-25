package su.terrafirmagreg.api.client.model.base;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;


import com.google.common.collect.ImmutableList;

import java.util.Collection;
import java.util.function.Function;

public class MultiTextureAtlasSprite extends TextureAtlasSprite {

    private final ResourceLocation texture1;
    private final ResourceLocation texture2;
    private final ImmutableList<ResourceLocation> dependencies;

    public MultiTextureAtlasSprite(ResourceLocation texture1, ResourceLocation texture2) {
        super(texture1.toString().concat("_".concat(texture2.toString())));
        this.texture1 = texture1;
        this.texture2 = texture2;
        dependencies = ImmutableList.of(this.texture1, this.texture2);
    }

    @Override
    public boolean hasCustomLoader(IResourceManager manager, ResourceLocation location) {
        return true;
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return dependencies;
    }

    @Override
    public boolean load(IResourceManager manager, ResourceLocation location, Function<ResourceLocation, TextureAtlasSprite> textureGetter) {
        //получаем текстуры по отдельности
        TextureAtlasSprite sprite = textureGetter.apply(texture1);
        TextureAtlasSprite mappingSprite = textureGetter.apply(texture2);
        width = sprite.getIconWidth();
        height = sprite.getIconHeight();
        int[][] pixels = new int[Minecraft.getMinecraft().gameSettings.mipmapLevels + 1][];
        //кол-во пикселей в новой текстуре
        pixels[0] = new int[width * height];

        int[][] oldPixels = sprite.getFrameTextureData(0);
        //получаем пиксели 2 текстуры
        int[][] alphaPixels = mappingSprite.getFrameTextureData(0);

        for (int p = 0; p < width * height; p++) {
            //достаем альфу из значения пикселя(каждый цвет обозначается 8 битами)
            int oldPixelAlpha = alphaPixels[0][p] >>> 24;
            //Проверяем, пустой ли пиксель?
            if (oldPixelAlpha > 0)
                //если не пустой, то сетаем пиксель 2 текстурки в новую
                pixels[0][p] = alphaPixels[0][p];
            else
                //если пустой, то сетаем пиксель 1 текстурки в новую
                pixels[0][p] = oldPixels[0][p];
        }
        //очищаем предыдущие пиксели, которые могли остаться при перезагрузке текстур
        this.clearFramesTextureData();
        //добавляем свои
        this.framesTextureData.add(pixels);
        //возвращаем false, чтобы наш TextureAtlasSprite не загружался обычным способом
        return false;
    }
}
