package su.terrafirmagreg.framework.registry.api.provider;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.InvocationTargetException;

public interface IProviderEntityRenderer {

  default IRenderFactory getRenderFactory() {
    return manager -> {
      Render render = null;
      try {
        var constructor = renderClass().getConstructor(RenderManager.class);
        constructor.setAccessible(true);
        render = constructor.newInstance(manager);
      } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        e.printStackTrace();
      }
      return render;
    };
  }

  @SideOnly(Side.CLIENT)
  default Class<? extends Render<? extends Entity>> renderClass() {
    return null;
  }
}
