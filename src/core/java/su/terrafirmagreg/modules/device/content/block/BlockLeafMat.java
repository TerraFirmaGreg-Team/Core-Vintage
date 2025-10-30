package su.terrafirmagreg.modules.device.content.block;

import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.util.StackUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.manager.content.base.block.spi.BaseBlockContainer;
import su.terrafirmagreg.modules.core.feature.size.capability.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.feature.size.spi.Size;
import su.terrafirmagreg.modules.core.feature.size.spi.Weight;
import su.terrafirmagreg.modules.device.content.render.TESRLeafMat;
import su.terrafirmagreg.modules.device.content.tile.TileLeafMat;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;

import net.dries007.tfc.objects.recipes.DryingRecipe;

import org.jetbrains.annotations.Nullable;

import java.util.Random;

@SuppressWarnings("deprecation")
public class BlockLeafMat extends BaseBlockContainer {

  public static final AxisAlignedBB MAT_SHAPE = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);

  public BlockLeafMat() {
    super(BlockSettings.of()
      .material(Material.PLANTS, MapColor.GREEN)
      .hardness(1.0F)
      .resistance(1.0F)
      .lightValue(0)
      .randomTicks()
      .nonOpaque()
      .tile(TileLeafMat.class, new TESRLeafMat())
      .harvestLevel(ToolClasses.KNIFE, 0)
      .sound(SoundType.PLANT)
      .renderType(EnumBlockRenderType.MODEL)
      .capability(CapabilityProviderSize.of(Size.SMALL, Weight.LIGHT))
    );
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return MAT_SHAPE;
  }

  @Override
  public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
    if (!worldIn.isRainingAt(pos.up())) {return;}
    TileUtils.getTile(worldIn, pos, TileLeafMat.class).ifPresent(TileLeafMat::rain);

  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
    if (world.isRemote) {return true;}
    ItemStack held = player.getHeldItem(hand);
    if (held.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) {
      return false;
    }
    TileUtils.getTile(world, pos, TileLeafMat.class).map(tile -> {
      IItemHandler inventory = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
      if (inventory != null) {
        ItemStack tryStack = new ItemStack(held.getItem(), 1);
        if (DryingRecipe.get(tryStack) != null && inventory.getStackInSlot(0).isEmpty()) {
          ItemStack leftover = inventory.insertItem(0, held.splitStack(1), false);
          ItemHandlerHelper.giveItemToPlayer(player, leftover);
          tile.start();
          tile.markForSync();
          return true;
        }
        if (held.isEmpty() && player.isSneaking()) {
          ItemStack takeStack = inventory.extractItem(0, 1, false);
          StackUtils.spawnItemStack(world, pos, takeStack);
          tile.deleteSlot();
          tile.clear();
          tile.markForSync();
        }
      }
      return true;
    });
    return true;
  }


  @Override
  public @Nullable TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileLeafMat();
  }

}
