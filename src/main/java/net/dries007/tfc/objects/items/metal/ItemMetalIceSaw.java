package net.dries007.tfc.objects.items.metal;

import su.terrafirmagreg.modules.core.capabilities.metal.ICapabilityMetal;
import su.terrafirmagreg.modules.core.init.ItemsCore;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.blocks.BlocksTFC;

import javax.annotation.Nullable;

import static su.terrafirmagreg.api.data.enums.Mods.ModIDs.TFC;

@Mod.EventBusSubscriber(modid = TFC)
public class ItemMetalIceSaw extends ItemMetalTool implements ICapabilityMetal {


  public ItemMetalIceSaw(Metal metal, Metal.ItemType type) {
    super(metal, type);
  }

  @Override
  public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
    if (getDroppedItem(state) != null) {
      return true;
    }
    return super.canHarvestBlock(state, stack);
  }

  @Override
  public boolean onBlockDestroyed(ItemStack itemStack, World world, IBlockState state, BlockPos pos, EntityLivingBase player) {
    super.onBlockDestroyed(itemStack, world, state, pos, player);

    if (world.isRemote) {
      return false;
    }

    if (state.getBlockHardness(world, pos) > 0) {
      itemStack.damageItem(1, player);
    }

    Item droppedItem = getDroppedItem(state);
    if (droppedItem != null) {
      EntityItem entityItem = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(droppedItem, 1));
      world.spawnEntity(entityItem);
      world.setBlockToAir(pos);
    }

    return true;
  }

  @Nullable
  private Item getDroppedItem(IBlockState state) {
    Block block = state.getBlock();

    if (block == BlocksTFC.SEA_ICE || block == Blocks.PACKED_ICE || block == Blocks.ICE) {
      return ItemsCore.ICE_SHARD.get();
    }

    return null;
  }
}
