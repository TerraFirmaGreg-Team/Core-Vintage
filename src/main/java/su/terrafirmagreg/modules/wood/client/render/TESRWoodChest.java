package su.terrafirmagreg.modules.wood.client.render;

import su.terrafirmagreg.api.util.ColourUtils;
import su.terrafirmagreg.api.util.ModUtils;
import su.terrafirmagreg.modules.wood.objects.blocks.BlockWoodChest;
import su.terrafirmagreg.modules.wood.objects.tiles.TileWoodChest;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import java.util.Objects;

@SideOnly(Side.CLIENT)
public class TESRWoodChest extends TileEntitySpecialRenderer<TileWoodChest> {

  private static final ResourceLocation SINGLE_TEXTURE = ModUtils.resource(
      "textures/entity/wood/chests/single.png");
  private static final ResourceLocation DOUBLE_TEXTURE = ModUtils.resource(
      "textures/entity/wood/chests/double.png");

  private final ModelChest simpleChest = new ModelChest();
  private final ModelChest largeChest = new ModelLargeChest();

  @Override
  public void render(TileWoodChest tile, double x, double y, double z, float partialTicks,
      int destroyStage, float alpha) {
    GlStateManager.enableDepth();
    GlStateManager.depthFunc(515);
    GlStateManager.depthMask(true);
    int meta = 0;
    var woodColor = Objects.requireNonNull(tile.getWood()).getColor();

    if (tile.hasWorld()) {
      Block block = tile.getBlockType();
      meta = tile.getBlockMetadata();

      if (block instanceof BlockWoodChest blockWoodChest && meta == 0) {
        blockWoodChest.checkForSurroundingChests(tile.getWorld(), tile.getPos(),
            tile.getWorld().getBlockState(tile.getPos()));
        meta = tile.getBlockMetadata();
      }

      tile.checkForAdjacentChests();
    }

    if (tile.adjacentChestZNeg != null || tile.adjacentChestXNeg != null) {
      return;
    }

    ModelChest modelchest;
    if (tile.adjacentChestXPos == null && tile.adjacentChestZPos == null) {
      modelchest = simpleChest;

      if (destroyStage >= 0) {
        bindTexture(TileEntitySpecialRenderer.DESTROY_STAGES[destroyStage]);
        GlStateManager.matrixMode(5890);
        GlStateManager.pushMatrix();
        GlStateManager.scale(4.0F, 4.0F, 1.0F);
        GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
        GlStateManager.matrixMode(5888);
      } else {
        bindTexture(SINGLE_TEXTURE);
        ColourUtils.setColor(woodColor);
      }
    } else {
      modelchest = largeChest;

      if (destroyStage >= 0) {
        bindTexture(TileEntitySpecialRenderer.DESTROY_STAGES[destroyStage]);
        GlStateManager.matrixMode(5890);
        GlStateManager.pushMatrix();
        GlStateManager.scale(8.0F, 4.0F, 1.0F);
        GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
        GlStateManager.matrixMode(5888);
      } else {
        bindTexture(DOUBLE_TEXTURE);
      }
    }

    GlStateManager.pushMatrix();
    GlStateManager.enableRescaleNormal();

    if (destroyStage < 0) {
      ColourUtils.setColor(woodColor);
    }

    GlStateManager.translate((float) x, (float) y + 1.0F, (float) z + 1.0F);
    GlStateManager.scale(1.0F, -1.0F, -1.0F);
    GlStateManager.translate(0.5F, 0.5F, 0.5F);
    int rotation = 0;

    switch (meta) {
      case 2 -> {
        rotation = 180;
        if (tile.adjacentChestXPos != null) {
          GlStateManager.translate(1.0F, 0.0F, 0.0F);
        }
      }
      case 3 -> rotation = 0;
      case 4 -> rotation = 90;
      case 5 -> {
        rotation = -90;
        if (tile.adjacentChestZPos != null) {
          GlStateManager.translate(0.0F, 0.0F, -1.0F);
        }
      }
    }

    GlStateManager.rotate((float) rotation, 0.0F, 1.0F, 0.0F);
    GlStateManager.translate(-0.5F, -0.5F, -0.5F);
    float lidAngle = tile.prevLidAngle + (tile.lidAngle - tile.prevLidAngle) * partialTicks;

    if (tile.adjacentChestZNeg != null) {
      float f1 = tile.adjacentChestZNeg.prevLidAngle
          + (tile.adjacentChestZNeg.lidAngle - tile.adjacentChestZNeg.prevLidAngle) * partialTicks;
      if (f1 > lidAngle) {
        lidAngle = f1;
      }
    }

    if (tile.adjacentChestXNeg != null) {
      float f2 = tile.adjacentChestXNeg.prevLidAngle
          + (tile.adjacentChestXNeg.lidAngle - tile.adjacentChestXNeg.prevLidAngle) * partialTicks;
      if (f2 > lidAngle) {
        lidAngle = f2;
      }
    }

    lidAngle = 1.0F - lidAngle;
    lidAngle = 1.0F - lidAngle * lidAngle * lidAngle;
    modelchest.chestLid.rotateAngleX = -(lidAngle * ((float) Math.PI / 2F));

    // Отрисовка частей сундука, кроме ручки
    ColourUtils.setColor(woodColor);
    modelchest.chestLid.render(0.0625F);
    modelchest.chestBelow.render(0.0625F);
    ColourUtils.clearColor();

    // Отрисовка ручки

    if (tile.getChestType() == BlockChest.Type.TRAP) {
      GlStateManager.color(1.0F, 0.0F, 0.0F, 0.4F);
    }
    modelchest.chestKnob.render(0.0625F);

    GlStateManager.disableRescaleNormal();
    GlStateManager.popMatrix();

    if (destroyStage >= 0) {
      GlStateManager.matrixMode(5890);
      GlStateManager.popMatrix();
      GlStateManager.matrixMode(5888);
    }
  }
}
