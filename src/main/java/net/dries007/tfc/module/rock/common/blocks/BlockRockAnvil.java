package net.dries007.tfc.module.rock.common.blocks;

import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.module.core.sound.TFCSounds;
import net.dries007.tfc.client.util.TFCGuiHandler;
import net.dries007.tfc.module.core.api.block.itemblocks.ItemBlockBase;
import net.dries007.tfc.module.metal.common.tiles.TEMetalAnvil;
import net.dries007.tfc.module.rock.StorageRock;
import net.dries007.tfc.module.rock.api.types.type.RockType;
import net.dries007.tfc.module.rock.api.types.variant.block.RockBlockVariant;
import net.dries007.tfc.module.rock.api.types.variant.item.RockItemVariants;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

import static net.dries007.tfc.module.rock.api.types.variant.block.RockBlockVariants.RAW;

@ParametersAreNonnullByDefault
public class BlockRockAnvil extends BlockRock {
    private static final AxisAlignedBB AABB = new AxisAlignedBB(0, 0, 0, 1, 0.875, 1);

    public BlockRockAnvil(RockBlockVariant variant, RockType type) {
        super(variant, type);

        FallingBlockManager.registerFallable(this, FallingBlockManager.Specification.COLLAPSABLE_ROCK);
    }

    @Override
    public ItemBlock getItemBlock() {
        return this.getCategory().hasAnvil() ? new ItemBlockBase(this) : null;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return side == EnumFacing.DOWN;
    }

    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    @Nullable
    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return AABB;
    }

    @SideOnly(Side.CLIENT)
    @Override
    @Nonnull
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return AABB;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        //Avoid issues with insertion/extraction
        if (hand == EnumHand.OFF_HAND) {
            return false;
        }
        TEMetalAnvil te = Helpers.getTE(worldIn, pos, TEMetalAnvil.class);
        if (te == null) {
            return false;
        }
        IItemHandler cap = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
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
            else if (te.isItemValid(TEMetalAnvil.SLOT_HAMMER, heldItem)) {
                if (!worldIn.isRemote && te.attemptWelding(playerIn)) {
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
                    if (i == TEMetalAnvil.SLOT_HAMMER) continue;
                    // Try to insert an item
                    // Hammers will not be inserted since we already checked if heldItem is a hammer for attemptWelding
                    if (te.isItemValid(i, heldItem) && te.getSlotLimit(i) > cap.getStackInSlot(i).getCount()) {
                        ItemStack result = cap.insertItem(i, heldItem, false);
                        playerIn.setHeldItem(hand, result);
                        TerraFirmaCraft.LOGGER.info("Inserted {} into slot {}", heldItem.getDisplayName(), i);
                        return true;
                    }
                }
            }
        } else {
            // not sneaking, so try and open GUI
            if (!worldIn.isRemote) {
                TFCGuiHandler.openGui(worldIn, pos, playerIn, TFCGuiHandler.Type.ANVIL);
            }
            return true;
        }
        return false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TEMetalAnvil te = Helpers.getTE(worldIn, pos, TEMetalAnvil.class);
        if (te != null) {
            te.onBreakBlock(worldIn, pos, state);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public int quantityDropped(Random random) {
        return 1 + random.nextInt(3);
    }

    @Override
    @Nonnull
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return StorageRock.getRockItem(RockItemVariants.LOOSE, getType());
    }

    @Override
    @Nonnull
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(StorageRock.getRockBlock(RAW, getType()));
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TEMetalAnvil();
    }
}
