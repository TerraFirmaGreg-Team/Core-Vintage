package net.dries007.tfc.client.render;

import su.terrafirmagreg.api.data.enums.Mods;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

import net.dries007.tfc.client.model.ModelPigvil;
import net.dries007.tfc.objects.entity.living.EntityPigvil;
import net.dries007.tfcthings.init.TFCThingsBlocks;

import javax.annotation.Nullable;

public class RenderPigvil extends RenderLiving<EntityPigvil> {

  private static final ResourceLocation PIGVIL = new ResourceLocation(Mods.ModIDs.TFCTHINGS, "textures/entity/pigvil.png");
  private static final ResourceLocation PIGVIL_BLACK = new ResourceLocation(Mods.ModIDs.TFCTHINGS, "textures/entity/pigvil_black.png");
  private static final ResourceLocation PIGVIL_RED = new ResourceLocation(Mods.ModIDs.TFCTHINGS, "textures/entity/pigvil_red.png");
  private static final ResourceLocation PIGVIL_BLUE = new ResourceLocation(Mods.ModIDs.TFCTHINGS, "textures/entity/pigvil_blue.png");
  private static final ResourceLocation PIGVIL_PURPLE = new ResourceLocation(Mods.ModIDs.TFCTHINGS, "textures/entity/pigvil_purple.png");

  public RenderPigvil(RenderManager rendermanagerIn) {
    super(rendermanagerIn, new ModelPigvil(), 0.7F);
  }

  @Nullable
  @Override
  protected ResourceLocation getEntityTexture(EntityPigvil entity) {
    Block anvil = entity.getAnvil();
    if (anvil.getRegistryName() == TFCThingsBlocks.PIGVIL_BLOCK.getRegistryName()) {
      return PIGVIL;
    } else if (anvil.getRegistryName() == TFCThingsBlocks.PIGVIL_BLOCK_BLACK.getRegistryName()) {
      return PIGVIL_BLACK;
    } else if (anvil.getRegistryName() == TFCThingsBlocks.PIGVIL_BLOCK_BLUE.getRegistryName()) {
      return PIGVIL_BLUE;
    } else if (anvil.getRegistryName() == TFCThingsBlocks.PIGVIL_BLOCK_RED.getRegistryName()) {
      return PIGVIL_RED;
    } else {
      return PIGVIL_PURPLE;
    }
  }

}
