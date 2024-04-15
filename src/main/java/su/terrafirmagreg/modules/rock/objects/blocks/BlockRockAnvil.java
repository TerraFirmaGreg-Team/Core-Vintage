package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariants;
import su.terrafirmagreg.modules.rock.api.types.variant.item.RockItemVariants;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.api.util.FallingBlockManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class BlockRockAnvil extends BlockRock {

    private static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 0.875, 1);

    public BlockRockAnvil(RockBlockVariant blockVariant, RockType type) {
        super(blockVariant, type);

        FallingBlockManager.registerFallable(this, blockVariant.getSpecification());
    }

    @Override
    public @Nullable ItemBlockBase getItemBlock() {
        //        return this.getType().getRockCategory().isHasAnvil() ? new ItemBlockBase(this) : null;
        return new ItemBlockBase(this);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullBlock(@NotNull IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(@NotNull IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isSideSolid(@NotNull IBlockState state, @NotNull IBlockAccess world, @NotNull BlockPos pos, @NotNull EnumFacing side) {
        return side == EnumFacing.DOWN;
    }

    @NotNull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @NotNull
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(@NotNull IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
        return AABB;
    }

    @Nullable
    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getCollisionBoundingBox(@NotNull IBlockState blockState, @NotNull IBlockAccess worldIn, @NotNull BlockPos pos) {
        return AABB;
    }

    @SideOnly(Side.CLIENT)
    @Override
    @NotNull
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getSelectedBoundingBox(@NotNull IBlockState state, @NotNull World worldIn, @NotNull BlockPos pos) {
        return AABB;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(@NotNull IBlockState state) {
        return false;
    }

    //	@Override
    //	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    //		//Avoid issues with insertion/extraction
    //		if (hand == EnumHand.OFF_HAND) {
    //			return false;
    //		}
    //		TEMetalAnvil te = TileUtils.getTile(worldIn, pos, TEMetalAnvil.class);
    //		if (te == null) {
    //			return false;
    //		}
    //		IItemHandler cap = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
    //		if (cap == null) {
    //			return false;
    //		}
    //		if (playerIn.isSneaking()) {
    //			ItemStack heldItem = playerIn.getHeldItem(hand);
    //			// Extract requires main hand empty
    //			if (heldItem.isEmpty()) {
    //				// Only check the input slots
    //				for (int i = 0; i < 2; i++) {
    //					ItemStack stack = cap.getStackInSlot(i);
    //					if (!stack.isEmpty()) {
    //						// Give the item to player in the main hand
    //						ItemStack result = cap.extractItem(i, 1, false);
    //						playerIn.setHeldItem(hand, result);
    //						return true;
    //					}
    //				}
    //			}
    //			// Welding requires a hammer in main hand
    //			else if (te.isItemValid(TEMetalAnvil.SLOT_HAMMER, heldItem)) {
    //				if (!worldIn.isRemote && te.attemptWelding(playerIn)) {
    //					// Valid welding occurred.
    //					worldIn.playSound(null, pos, SoundAnimal.ANVIL_IMPACT, SoundCategory.PLAYERS, 1.0f, 1.0f);
    //					return true;
    //				}
    //			}
    //			//If main hand isn't empty and is not a hammer
    //			else {
    //				//Try inserting items
    //				for (int i = 0; i < 4; i++) {
    //					// Check the input slots and flux. Do NOT check the hammer slot
    //					if (i == TEMetalAnvil.SLOT_HAMMER) continue;
    //					// Try to insert an item
    //					// Hammers will not be inserted since we already checked if heldItem is a hammer for attemptWelding
    //					if (te.isItemValid(i, heldItem) && te.getSlotLimit(i) > cap.getStackInSlot(i).getCount()) {
    //						ItemStack result = cap.insertItem(i, heldItem, false);
    //						playerIn.setHeldItem(hand, result);
    //						ModuleCoreOld.LOGGER.info("Inserted {} into slot {}", heldItem.getDisplayName(), i);
    //						return true;
    //					}
    //				}
    //			}
    //		} else {
    //			// not sneaking, so try and open GUI
    //			if (!worldIn.isRemote) {
    //				TFCGuiHandler.openGui(worldIn, pos, playerIn, TFCGuiHandler.Type.ANVIL);
    //			}
    //			return true;
    //		}
    //		return false;
    //	}

    //	@Override
    //	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
    //		TEMetalAnvil te = TileUtils.getTile(worldIn, pos, TEMetalAnvil.class);
    //		if (te != null) {
    //			te.onBreakBlock(worldIn, pos, state);
    //		}
    //		super.breakBlock(worldIn, pos, state);
    //	}

    @Override
    public int quantityDropped(Random random) {
        return 1 + random.nextInt(3);
    }

    @Override
    @NotNull
    public Item getItemDropped(@NotNull IBlockState state, @NotNull Random rand, int fortune) {
        return RockItemVariants.LOOSE.get(getType());
    }

    @Override
    @NotNull
    public ItemStack getPickBlock(@NotNull IBlockState state, @NotNull RayTraceResult target, @NotNull World world, @NotNull BlockPos pos,
                                  @NotNull EntityPlayer player) {
        return new ItemStack(RockBlockVariants.RAW.get(getType()));
    }

    //	@Override
    //	public boolean hasTileEntity(IBlockState state) {
    //		return true;
    //	}
    //
    //	@Nullable
    //	@Override
    //	public TileEntity createTileEntity(World world, IBlockState state) {
    //		return new TEMetalAnvil();
    //	}
}
