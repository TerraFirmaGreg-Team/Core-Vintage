package su.terrafirmagreg.modules.rock.object.block;

import su.terrafirmagreg.api.client.GuiHandler;
import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.util.CapabilityUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.manager.registry.base.block.spi.BaseBlock;
import su.terrafirmagreg.framework.manager.registry.provider.IProviderTile;
import su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager;
import su.terrafirmagreg.modules.core.feature.falling.spi.FallingBlockManager.Specification;
import su.terrafirmagreg.modules.rock.ModuleRock;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.feature.rocktype.types.IRockEntry;
import su.terrafirmagreg.modules.rock.init.BlocksRock;
import su.terrafirmagreg.modules.rock.init.ItemsRock;
import su.terrafirmagreg.modules.rock.object.render.TESRRockAnvil;
import su.terrafirmagreg.modules.rock.object.tile.TileRockAnvil;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import net.dries007.tfc.client.TFCSounds;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Random;

@SuppressWarnings("deprecation")
@Getter
public class BlockRockAnvil extends BaseBlock implements IRockEntry, IProviderTile {


  protected static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 0.875, 1);
  protected final RockType type;

  public BlockRockAnvil(RockType type) {
    super(BlockSettings.of()
      .material(Material.ROCK)
      .hardness(type.getHardness(6f))
      .sound(SoundType.STONE)
      .tile(TileRockAnvil.class, new TESRRockAnvil())
      .harvestLevel(ToolClasses.PICKAXE, 0)
      .nonCube()
      .addOreDict("anvil")
      .addOreDict("anvil", type)
    );

    this.type = type;

    FallingBlockManager.registerFallable(this, Specification.COLLAPSABLE_ROCK);
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return AABB;
  }

  @SideOnly(Side.CLIENT)
  @Override
  public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
    return AABB;
  }

  @Override
  public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    TileUtils.getTile(worldIn, pos, TileRockAnvil.class)
      .ifPresent(tile -> tile.onBreakBlock(worldIn, pos, state));
    super.breakBlock(worldIn, pos, state);
  }

  @Override
  public int quantityDropped(Random random) {
    return 1 + random.nextInt(3);
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return ItemsRock.LOOSE.get(type);
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

    //Avoid issues with insertion/extraction
    if (hand == EnumHand.OFF_HAND) {
      return false;
    }

    return TileUtils.getTile(worldIn, pos, TileRockAnvil.class).map(tile -> {
      IItemHandler cap = CapabilityUtils.get(tile, CapabilityItemHandler.ITEM_HANDLER_CAPABILITY);
      if (cap == null) {
        return false;
      }

      if (playerIn.isSneaking()) {
        ItemStack heldItem = playerIn.getHeldItem(hand);

        // Extract requires main hand empty
        if (heldItem.isEmpty()) {
          // Only check the input slots
          for (int i = 0; i < 2; i++) {
            ItemStack stack = cap.getStackInSlot(i);
            if (!stack.isEmpty()) {
              // Give the item to player in the main hand
              ItemStack result = cap.extractItem(i, 1, false);
              playerIn.setHeldItem(hand, result);
              return true;
            }
          }
        }

        // Welding requires a hammer in main hand
        else if (tile.isItemValid(TileRockAnvil.SLOT_HAMMER, heldItem)) {
          if (!worldIn.isRemote && tile.attemptWelding(playerIn)) {
            // Valid welding occurred.
            worldIn.playSound(null, pos, TFCSounds.ANVIL_IMPACT, SoundCategory.PLAYERS, 1.0f, 1.0f);
            return true;
          }
        }
        //If main hand isn't empty and is not a hammer
        else {
          //Try inserting items
          for (int i = 0; i < 4; i++) {
            // Check the input slots and flux. Do NOT check the hammer slot
            if (i == TileRockAnvil.SLOT_HAMMER) {
              continue;
            }
            // Try to insert an item
            // Hammers will not be inserted since we already checked if heldItem is a hammer for attemptWelding
            if (tile.isItemValid(i, heldItem) && tile.getSlotLimit(i) > cap.getStackInSlot(i).getCount()) {

              ItemStack result = cap.insertItem(i, heldItem, false);
              playerIn.setHeldItem(hand, result);
              ModuleRock.LOGGER.info("Inserted {} into slot {}", heldItem.getDisplayName(), i);
              return true;
            }
          }
        }
      } else {
        // not sneaking, so try and open GUI
        if (!worldIn.isRemote) {
          GuiHandler.openGui(worldIn, pos, playerIn);
        }
        return true;
      }
      return false;
    }).orElse(false);
  }

  @Override
  public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
    return side == EnumFacing.DOWN;
  }

  @Override
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    return new ItemStack(BlocksRock.RAW.get(type));
  }

  @Override
  public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    return AABB;
  }

  @Override
  public @Nullable TileEntity createNewTileEntity(World worldIn, int meta) {
    return new TileRockAnvil();
  }
}
