package net.dries007.firmalife.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.firmalife.util.ClientHelpers;

import java.util.LinkedList;
import java.util.Queue;

import static net.dries007.firmalife.FirmaLife.MOD_ID;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = MOD_ID, value = Side.CLIENT)
public class RenderHandler {

  public static Queue<Runnable> TO_RUN = new LinkedList<>();

  @SubscribeEvent
  public static void onRenderTick(RenderWorldLastEvent event) {
    Runnable runnable = TO_RUN.poll();
    if (runnable != null) {
      EntityPlayerSP entity = Minecraft.getMinecraft().player;
      Vec3d trans = ClientHelpers.getEntityMovementPartial(entity, event.getPartialTicks());

      GlStateManager.disableAlpha();
      BufferBuilder buffer = Tessellator.getInstance().getBuffer();
      buffer.setTranslation(-trans.x, -trans.y, -trans.z);
      runnable.run();
      buffer.setTranslation(0.0D, 0.0D, 0.0D);
      GlStateManager.enableAlpha();
    }
  }
}
