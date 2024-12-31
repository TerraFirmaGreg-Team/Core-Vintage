package su.terrafirmagreg.old.api.library.model.base;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;

public class TextureEventHandler {

  private static final List<TextureAtlasSprite> textureAtlasSprites = new ArrayList<>();

  public static void registerSprite(TextureAtlasSprite textureAtlasSprite) {
    textureAtlasSprites.add(textureAtlasSprite);
  }

  @SubscribeEvent
  public void event(TextureStitchEvent.Pre event) {
    for (TextureAtlasSprite sprite : textureAtlasSprites) {
      event.getMap().setTextureEntry(sprite);
    }
  }

}
