package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;
import su.terrafirmagreg.modules.rock.init.BlocksRock;
import su.terrafirmagreg.modules.rock.init.ItemsRock;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import su.terrafirmagreg.modules.core.features.falling.FallingBlockManager;


import java.util.Random;

@SuppressWarnings("deprecation")
public class BlockRockAnvil extends BlockRock {

    private static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 0.875, 1);

    public BlockRockAnvil(RockBlockVariant variant, RockType type) {
        super(Settings.of(Material.ROCK), variant, type);

        getSettings().nonCube();

        FallingBlockManager.registerFallable(this, variant.getSpecification());
    }

    @Override
    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return side == EnumFacing.DOWN;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return AABB;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return AABB;
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
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ItemsRock.LOOSE.get(getType());
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(BlocksRock.RAW.get(getType()));
    }

    //	@Nullable
    //	@Override
    //	public TileEntity createTileEntity(World world, IBlockState state) {
    //		return new TEMetalAnvil();
    //	}
}
