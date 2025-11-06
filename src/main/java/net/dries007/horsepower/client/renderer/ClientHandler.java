package net.dries007.horsepower.client.renderer;

import su.terrafirmagreg.api.util.GameUtils;
import su.terrafirmagreg.api.util.RenderUtils;
import su.terrafirmagreg.modules.device.content.block.BlockQuernHorse;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.dries007.horsepower.Configs;
import net.dries007.horsepower.blocks.BlockHPBase;
import net.dries007.horsepower.tileentity.TileEntityHPHorseBase;

import java.util.stream.StreamSupport;

public class ClientHandler {

  private static final float RENDER_ALPHA = 0.15F;
  private static final float RENDER_LINE_WIDTH = 0.05F;
  private static final int Y_OFFSET_QUERN_HORSE = -1;

  @SubscribeEvent
  public static void renderWorld(RenderWorldLastEvent event) {
    if (!Configs.client.showObstructedPlace) {
      return;
    }

    ItemStack heldItem = getHeldBlockItem();
    if (heldItem.isEmpty()) {
      return;
    }

    Minecraft mc = GameUtils.getMinecraft();
    EntityPlayer player = GameUtils.getPlayer();

    if (mc.objectMouseOver == null || mc.objectMouseOver.typeOfHit != RayTraceResult.Type.BLOCK) {
      return;
    }

    BlockPos targetPos = calculateTargetPosition(mc, player, heldItem);
    if (targetPos == null) {
      return;
    }

    int yOffset = isQuernHorse(heldItem) ? Y_OFFSET_QUERN_HORSE : 0;
    RenderUtils.renderUsedArea(GameUtils.getWorld(), targetPos, yOffset, RENDER_ALPHA, RENDER_LINE_WIDTH);

//    final ItemStack[] itemStack = {ItemStack.EMPTY};
//    if (StreamSupport.stream(GameUtils.getPlayer().getHeldEquipment().spliterator(), false)
//      .anyMatch(stack -> !stack.isEmpty() && isHPBlock((itemStack[0] = stack).getItem()))) {
//      Minecraft mc = GameUtils.getMinecraft();
//      if (mc.objectMouseOver == null || mc.objectMouseOver.typeOfHit != RayTraceResult.Type.BLOCK) {return;}
//
//      int offset = 0;
//      if (!itemStack[0].isEmpty() && itemStack[0].getItem() instanceof ItemBlock itemBlock && itemBlock.getBlock() instanceof BlockQuernHorse) {offset = -1;}
//
//      EnumFacing enumFacing = mc.objectMouseOver.sideHit;
//      BlockPos pos = mc.objectMouseOver.getBlockPos();
//      if (!GameUtils.getWorld().getBlockState(pos).getBlock().isReplaceable(GameUtils.getWorld(), pos)) {pos = pos.offset(enumFacing);}
//      if (offset == 0 && !GameUtils.getWorld().getBlockState(pos.up()).getBlock().isReplaceable(GameUtils.getWorld(), pos.up())) {pos = pos.down();}
//
//      RenderUtils.renderUsedArea(GameUtils.getWorld(), pos, offset, 0.15F, 0.05F);
//    }
  }

  private static ItemStack getHeldBlockItem() {
    return StreamSupport.stream(GameUtils.getPlayer().getHeldEquipment().spliterator(), false)
      .filter(stack -> !stack.isEmpty() && isHPBlock(stack.getItem()))
      .findFirst()
      .orElse(ItemStack.EMPTY);
  }

  private static BlockPos calculateTargetPosition(Minecraft mc, EntityPlayer player, ItemStack heldItem) {
    World world = GameUtils.getWorld();
    BlockPos pos = mc.objectMouseOver.getBlockPos();

    if (!world.getBlockState(pos).getBlock().isReplaceable(world, pos)) {
      pos = pos.offset(mc.objectMouseOver.sideHit);
    }

    if (!isQuernHorse(heldItem) && !world.getBlockState(pos.up()).getBlock().isReplaceable(world, pos.up())) {
      pos = pos.down();
    }

    return pos;
  }

  private static boolean isQuernHorse(ItemStack stack) {
    return stack.getItem() instanceof ItemBlock itemBlock && itemBlock.getBlock() instanceof BlockQuernHorse;
  }

  private static boolean isHPBlock(Item item) {
    if (item instanceof ItemBlock itemBlock && itemBlock.getBlock() instanceof BlockHPBase) {
      return TileEntityHPHorseBase.class.isAssignableFrom(((BlockHPBase) itemBlock.getBlock()).getTileClass());
    }
    return false;
  }
}
