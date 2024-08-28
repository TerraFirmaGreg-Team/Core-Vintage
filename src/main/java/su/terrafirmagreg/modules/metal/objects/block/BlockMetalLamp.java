package su.terrafirmagreg.modules.metal.objects.block;

import su.terrafirmagreg.api.base.block.BaseBlock;
import su.terrafirmagreg.api.base.item.BaseItemBlock;
import su.terrafirmagreg.api.registry.provider.IProviderTile;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.core.capabilities.heat.HandlerHeat;
import su.terrafirmagreg.modules.core.capabilities.heat.ProviderHeat;
import su.terrafirmagreg.modules.core.capabilities.metal.ICapabilityMetal;
import su.terrafirmagreg.modules.metal.ConfigMetal;
import su.terrafirmagreg.modules.metal.api.types.type.MetalType;
import su.terrafirmagreg.modules.metal.api.types.variant.block.IMetalBlock;
import su.terrafirmagreg.modules.metal.api.types.variant.block.MetalBlockVariant;
import su.terrafirmagreg.modules.metal.objects.itemblock.ItemBlockMetalLamp;
import su.terrafirmagreg.modules.metal.objects.tile.TileMetalLamp;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.blocks.BlockTorchTFC;
import net.dries007.tfc.objects.inventory.ingredient.IIngredient;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.ICalendar;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.List;
import java.util.Random;

import static su.terrafirmagreg.data.Properties.LIT;
import static su.terrafirmagreg.data.Properties.VERTICAL;
import static su.terrafirmagreg.modules.metal.objects.itemblock.ItemBlockMetalLamp.CAPACITY;

@Getter
@SuppressWarnings("deprecation")
public class BlockMetalLamp
        extends BaseBlock
        implements IMetalBlock, IProviderTile, ICapabilityMetal {

    private static final AxisAlignedBB AABB_UP = new AxisAlignedBB(0.3125, 0, 0.3125, 0.6875, 0.5, 0.6875);
    private static final AxisAlignedBB AABB_DOWN = new AxisAlignedBB(0.3125, 0, 0.3125, 0.6875, 1, 0.6875);

    private final MetalBlockVariant variant;
    private final MetalType type;

    public BlockMetalLamp(MetalBlockVariant variant, MetalType type) {
        super(Settings.of(Material.REDSTONE_LIGHT));

        this.variant = variant;
        this.type = type;

        getSettings()
                .soundType(SoundType.METAL)
                .nonCube()
                .hardness(1f)
                .addOreDict("lamp");

        setTickRandomly(true);
        setDefaultState(getBlockState().getBaseState()
                .withProperty(VERTICAL, EnumFacing.UP)
                .withProperty(LIT, false));

        // In the interest of not writing a joint heat / fluid capability that extends ICapabilityProvider, I think this is justified
        HandlerHeat.CUSTOM_ITEMS.put(IIngredient.of(this), () -> new ProviderHeat(null, type.getSpecificHeat(), type.getMeltTemp()));
    }

    @Override
    public @Nullable BaseItemBlock getItemBlock() {
        return new ItemBlockMetalLamp(this);
    }

    /**
     * @param stack the item stack. This can assume that it is of the right item type and do casts without checking
     * @return the metal of the stack
     */
    @Nullable
    @Override
    public Metal getMetal(ItemStack stack) {
        return null; //type.getMaterial(); return ((BlockMetalLamp) (super.block)).getMetal();
    }

    /**
     * @param stack The item stack
     * @return the amount of liquid metal that this item will create (in TFC units or mB: 1 unit = 1 mB)
     */
    @Override
    public int getSmeltAmount(ItemStack stack) {
        return 144;
    }

    //Don't need to do any clientside display ticking, as no smoke particles... yet
    //public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand)

    //may support wall attachments sometime
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(VERTICAL, EnumFacing.byIndex(meta % 2))
                .withProperty(LIT, meta >= 2);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(VERTICAL).getIndex() + (state.getValue(LIT) ? 2 : 0);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return switch (state.getValue(VERTICAL)) {
            case UP -> AABB_UP;
            default -> AABB_DOWN;
        };
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return getBoundingBox(state, worldIn, pos).offset(pos);
    }

    @Override
    public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {
        if (state.getValue(LIT) && ConfigMetal.BLOCKS.LAMP.burnRate > 0) {
            var tile = TileUtils.getTile(worldIn, pos, TileMetalLamp.class);
            if (tile != null) {
                checkFuel(worldIn, pos, state, tile);
            }
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        var tile = TileUtils.getTile(worldIn, pos, TileMetalLamp.class);
        if (tile != null) {
            if (worldIn.isBlockPowered(pos) && !tile.isPowered()) //power on
            {
                lightWithFuel(worldIn, pos, state, tile);
                tile.setPowered(true);
            } else if (!worldIn.isBlockPowered(pos) && tile.isPowered()) //power off
            {
                if (!checkFuel(worldIn, pos, state, tile)) //if it didn't run out turn it off anyway
                {
                    worldIn.setBlockState(pos, state.withProperty(LIT, false));
                    tile.setPowered(false);
                    tile.resetCounter();
                }
            }
        }
        if (!canPlaceAt(worldIn, pos, state.getValue(VERTICAL))) {
            worldIn.destroyBlock(pos, true);
        }
    }

    /**
     * Checks if this block can be placed exactly at the given position.
     */
    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        for (EnumFacing enumfacing : VERTICAL.getAllowedValues()) {
            if (this.canPlaceAt(worldIn, pos, enumfacing)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        var tile = TileUtils.getTile(worldIn, pos, TileMetalLamp.class);
        ItemStack stack = playerIn.getHeldItem(hand);
        if (!worldIn.isRemote && tile != null) {
            if (state.getValue(LIT)) {
                if (!checkFuel(worldIn, pos, state, tile)) //if it didn't run out turn it off
                {
                    worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(LIT, false));
                    tile.resetCounter();
                }
            } else if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) { //refill only if not lit
                IFluidHandler fluidHandler = tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
                if (fluidHandler != null) {
                    FluidUtil.interactWithFluidHandler(playerIn, hand, fluidHandler);
                    tile.markDirty();
                }
            } else if (BlockTorchTFC.canLight(stack)) {
                if (lightWithFuel(worldIn, pos, state, tile)) {
                }
            }
        }
        return true;
    }

    /**
     * Called by ItemBlocks just before a block is actually set in the world, to allow for adjustments to the IBlockstate
     */
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        if (this.canPlaceAt(worldIn, pos, facing)) {
            return this.getDefaultState().withProperty(VERTICAL, facing);
        } else if (this.canPlaceAt(worldIn, pos, EnumFacing.UP)) {
            return this.getDefaultState().withProperty(VERTICAL, EnumFacing.UP);
        } else if (this.canPlaceAt(worldIn, pos, EnumFacing.DOWN)) // last resort, must have matched in canPlaceAt test
        {
            return this.getDefaultState().withProperty(VERTICAL, EnumFacing.DOWN);
        }
        return this.getDefaultState(); //should never happen
    }

    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack tool) {
        super.harvestBlock(world, player, pos, state, te, tool);
        world.setBlockToAir(pos);
    }

    // after BlockBarrel#onBlockPlacedBy
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (!worldIn.isRemote && stack.getTagCompound() != null) {
            // Set the initial counter value and fill from item
            TileMetalLamp tile = TileUtils.getTile(worldIn, pos, TileMetalLamp.class);
            if (tile != null) {
                tile.resetCounter();
                tile.loadFromItemStack(stack);
            }
            worldIn.setBlockState(pos, state.withProperty(LIT, false));
        }
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VERTICAL, LIT);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.getValue(LIT) ? 15 : 0;
    }

    //Lifted from BlockFlowerPot

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        return willHarvest || super.removedByPlayer(state, world, pos, player, willHarvest); //delay deletion of the block until after getDrops
    }

    @Override
    public TileMetalLamp createNewTileEntity(World worldIn, int meta) {
        return new TileMetalLamp();
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileMetalLamp.class;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        var tile = TileUtils.getTile(world, pos, TileMetalLamp.class);
        if (tile != null) {
            if (tile.getFuel() == 0) {
                super.getDrops(drops, world, pos, state, fortune);
            } else {
                drops.add(tile.getItemStack(tile, state));
            }
        }
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        var tile = TileUtils.getTile(world, pos, TileMetalLamp.class);
        if (tile != null) {
            return tile.getItemStack(tile, state);
        }
        return new ItemStack(state.getBlock());
    }

    private boolean lightWithFuel(World worldIn, BlockPos pos, IBlockState state, TileMetalLamp tel) {
        if (tel.getFuel() > 0) {
            worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(LIT, true));
            tel.resetCounter();
            return true;
        }
        return false;
    }

    private boolean checkFuel(World worldIn, BlockPos pos, IBlockState state, TileMetalLamp tel) {
        IFluidHandler fluidHandler = tel.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
        boolean ranOut = false;
        if (!worldIn.isRemote && fluidHandler != null) {
            long ticks = tel.getTicksSinceUpdate();
            double usage = ConfigMetal.BLOCKS.LAMP.burnRate * ticks / ICalendar.TICKS_IN_HOUR;
            if (usage >= 1) // minimize rounding issues
            {
                FluidStack used = fluidHandler.drain((int) usage, true); // use fuel
                if (used == null || used.amount < (int) usage) {
                    worldIn.setBlockState(pos, state.withProperty(LIT, false));
                    ranOut = true;
                }
                tel.resetCounter();
            }
        }
        return ranOut;
    }

    private boolean canPlaceOn(World worldIn, BlockPos pos) {
        IBlockState state = worldIn.getBlockState(pos);
        return state.getBlock().canPlaceTorchOnTop(state, worldIn, pos);
    }

    private boolean canPlaceAt(World worldIn, BlockPos pos, EnumFacing facing) {
        if (!VERTICAL.getAllowedValues().contains(facing)) {
            return false;
        }

        BlockPos blockpos = pos.offset(facing.getOpposite());
        IBlockState iblockstate = worldIn.getBlockState(blockpos);
        Block block = iblockstate.getBlock();
        BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, blockpos, facing);

        if (facing.equals(EnumFacing.UP) && this.canPlaceOn(worldIn, blockpos)) {
            return true;
        } else if (facing != EnumFacing.UP && facing != EnumFacing.DOWN) {
            return !isExceptBlockForAttachWithPiston(block) && blockfaceshape == BlockFaceShape.SOLID;
        } else return facing == EnumFacing.DOWN && blockfaceshape == BlockFaceShape.SOLID;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addMetalInfo(ItemStack stack, List<String> text) // shamelessly co-opted to show liquid too
    {
        IFluidHandler fluidCap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        boolean spacer = false;
        if (fluidCap != null) {
            FluidStack fluidStack = fluidCap.drain(CAPACITY, false);
            if (fluidStack != null) {
                spacer = true;
                text.add("");
                String fluidName = fluidStack.getLocalizedName();
                text.add(I18n.format("tfc.tooltip.barrel_fluid", fluidStack.amount, fluidName));
            }
        }
        Metal metal = getMetal(stack);
        if (metal != null) {
            if (!spacer) {
                text.add("");
            }
            text.add(I18n.format("tfc.tooltip.metal", I18n.format(Helpers.getTypeName(metal))));
            text.add(I18n.format("tfc.tooltip.units", getSmeltAmount(stack)));
            text.add(I18n.format(Helpers.getEnumName(metal.getTier())));
        }
    }

}
