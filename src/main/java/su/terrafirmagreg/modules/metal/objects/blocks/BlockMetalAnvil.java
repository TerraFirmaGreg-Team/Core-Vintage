package su.terrafirmagreg.modules.metal.objects.blocks;

import su.terrafirmagreg.api.lib.Constants;
import su.terrafirmagreg.api.spi.block.BlockBase;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.metal.ModuleMetal;
import su.terrafirmagreg.modules.metal.api.types.type.MetalType;
import su.terrafirmagreg.modules.metal.api.types.variant.block.IMetalBlock;
import su.terrafirmagreg.modules.metal.api.types.variant.block.MetalBlockVariant;
import su.terrafirmagreg.modules.metal.api.types.variant.block.MetalBlockVariants;
import su.terrafirmagreg.modules.metal.objects.tiles.TEMetalAnvil;

import net.minecraft.block.SoundType;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
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


import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.client.TFCGuiHandler;
import net.dries007.tfc.client.TFCSounds;
import net.dries007.tfc.client.particle.TFCParticles;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.Random;

import static su.terrafirmagreg.api.util.PropertyUtils.HORIZONTAL;

@Getter
public class BlockMetalAnvil extends BlockBase implements IMetalBlock {

    private static final AxisAlignedBB AABB_Z = new AxisAlignedBB(0.1875, 0, 0, 0.8125, 0.6875, 1);
    private static final AxisAlignedBB AABB_X = new AxisAlignedBB(0, 0, 0.1875, 1, 0.6875, 0.8125);

    private final MetalBlockVariant blockVariant;
    private final MetalType type;

    public BlockMetalAnvil(MetalBlockVariant blockVariant, MetalType type) {
        super(net.minecraft.block.material.Material.IRON);

        this.blockVariant = blockVariant;
        this.type = type;

        setSoundType(SoundType.ANVIL);
        setHardness(4.0F);
        setResistance(10F);
        setHarvestLevel("pickaxe", 0);

        setDefaultState(this.blockState.getBaseState()
                .withProperty(HORIZONTAL, EnumFacing.NORTH));

        FallingBlockManager.registerFallable(this, blockVariant.getSpecification());
    }

    @Override
    public @Nullable ItemBlockBase getItemBlock() {
        return new ItemBlockBase(this);
    }

    //	@Nullable
    //	@Override
    //	public Material getMetal(ItemStack stack) {
    //		return type.getMetal();
    //	}

    //	@Override
    //	public int getSmeltAmount(ItemStack stack) {
    //		return 2016;
    //	}

    protected EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing,
                                         float hitX, float hitY, float hitZ) {
        if (facing != null) {
            BlockPos placedPos = pos.offset(facing);
            BlockPos supportPos = placedPos.down();
            IBlockState state = worldIn.getBlockState(placedPos);
            IBlockState stateSupport = worldIn.getBlockState(supportPos);
            if (state.getBlock().isReplaceable(worldIn, placedPos) &&
                    stateSupport.isSideSolid(worldIn, supportPos, EnumFacing.UP)) {
                if (!worldIn.isRemote) {
                    worldIn.setBlockState(placedPos, MetalBlockVariants.ANVIL.get(type).getDefaultState()
                            .withProperty(HORIZONTAL, player.getHorizontalFacing()));
                    worldIn.playSound(null, placedPos, SoundEvents.BLOCK_ANVIL_PLACE, SoundCategory.BLOCKS, 1.0F, 1.0F);
                    stack.shrink(1);
                    player.setHeldItem(hand, stack);
                }
                return EnumActionResult.SUCCESS;
            }
        }
        return EnumActionResult.FAIL;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullBlock(@NotNull IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HORIZONTAL).getHorizontalIndex();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isBlockNormalCube(@NotNull IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isNormalCube(@NotNull IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(@NotNull IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("deprecation")
    public @NotNull AxisAlignedBB getBoundingBox(IBlockState state, @NotNull IBlockAccess source, @NotNull BlockPos pos) {
        return state.getValue(HORIZONTAL).getAxis() == EnumFacing.Axis.X ? AABB_Z : AABB_X;
    }

    @SuppressWarnings("deprecation")
    @Override
    @NotNull
    public BlockFaceShape getBlockFaceShape(@NotNull IBlockAccess worldIn, @NotNull IBlockState state, @NotNull BlockPos pos,
                                            @NotNull EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @SideOnly(Side.CLIENT)
    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return getBoundingBox(state, worldIn, pos).offset(pos);
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TEMetalAnvil te = TileUtils.getTile(worldIn, pos, TEMetalAnvil.class);
        if (te != null) {
            te.onBreakBlock(worldIn, pos, state);
        }
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return super.getItemDropped(state, rand, fortune);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                    float hitX, float hitY, float hitZ) {
        if (hand == EnumHand.OFF_HAND) {
            return false;
        }
        TEMetalAnvil te = TileUtils.getTile(worldIn, pos, TEMetalAnvil.class);
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
                    double x = pos.getX() + 0.5;
                    double y = pos.getY() + 0.69;
                    double z = pos.getZ() + 0.5;
                    for (int i = 0; i < Constants.RANDOM.nextInt(5) + 3; i++)
                        TFCParticles.SPARK.sendToAllNear(worldIn, x + (Constants.RANDOM.nextFloat() - 0.5) / 7, y,
                                z + (Constants.RANDOM.nextFloat() - 0.5) / 7, 6 * (Constants.RANDOM.nextFloat() - 0.5), 2D,
                                6 * (Constants.RANDOM.nextFloat() - 0.5), 22);
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
                        ModuleMetal.LOGGER.info("Inserted {} into slot {}", heldItem.getDisplayName(), i);
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
    public BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HORIZONTAL);
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isSideSolid(IBlockState base_state, IBlockAccess world, BlockPos pos, EnumFacing side) {
        return false;
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

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(MetalBlockVariants.ANVIL.get(type));
    }

    @Override
    @NotNull
    public Size getSize(ItemStack stack) {
        return Size.HUGE; // Can't be stored and causes overburden
    }

    @Override
    @NotNull
    public Weight getWeight(ItemStack stack) {
        return Weight.VERY_HEAVY; // Stacksize = 1
    }

    @Override
    public boolean canStack(ItemStack stack) {
        return false;
    }
}
