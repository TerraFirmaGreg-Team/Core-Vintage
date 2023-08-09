package net.dries007.tfc.objects.blocks.wood.tree;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.Constants;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.wood.IWoodBlock;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.WoodBlockVariant;
import net.dries007.tfc.client.CustomStateMap;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.BlockLog;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public class BlockWoodLog extends BlockLog implements IItemSize, IWoodBlock {
    public static final PropertyBool PLACED = PropertyBool.create("placed");
    public static final PropertyBool SMALL = PropertyBool.create("small");
    public static final AxisAlignedBB SMALL_AABB_Y = new AxisAlignedBB(0.25, 0, 0.25, 0.75, 1, 0.75);
    public static final AxisAlignedBB SMALL_AABB_X = new AxisAlignedBB(0, 0.25, 0.25, 1, 0.75, 0.75);
    public static final AxisAlignedBB SMALL_AABB_Z = new AxisAlignedBB(0.25, 0.25, 0, 0.75, 0.75, 1);
    private final WoodBlockVariant woodBlockVariant;
    private final WoodType woodType;

    public BlockWoodLog(WoodBlockVariant woodBlockVariant, WoodType woodType) {
        this.woodBlockVariant = woodBlockVariant;
        this.woodType = woodType;

        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());
        setCreativeTab(CreativeTabsTFC.WOOD);

        setDefaultState(blockState.getBaseState().withProperty(LOG_AXIS, BlockLog.EnumAxis.Y).withProperty(PLACED, true).withProperty(SMALL, false));
        setHarvestLevel("axe", 0);
        setHardness(2.0F);
        setResistance(5.0F);

        Blocks.FIRE.setFireInfo(this, 5, 5);
        setTickRandomly(true);
    }

    @Override
    public WoodBlockVariant getWoodBlockVariant() {
        return woodBlockVariant;
    }

    @Override
    public WoodType getWoodType() {
        return woodType;
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTFC(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullBlock(IBlockState state) {
        return !state.getValue(SMALL);
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getLightOpacity(IBlockState state) {
        return state.getValue(SMALL) ? 0 : 255;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return !state.getValue(SMALL);
    }

    @Override
    @SuppressWarnings("deprecation")
    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
        return (blockState.getValue(PLACED) ? 1.0f : 2.5f) * super.getBlockHardness(blockState, worldIn, pos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        if (!state.getValue(SMALL)) return FULL_BLOCK_AABB;
        return switch (state.getValue(LOG_AXIS)) {
            case X -> SMALL_AABB_X;
            case Y -> SMALL_AABB_Y;
            case Z -> SMALL_AABB_Z;
            default -> FULL_BLOCK_AABB;
        };
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return !state.getValue(SMALL);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        // For floating tree things, just make them gently disappear over time
        if (state.getValue(PLACED)) return;
        for (int x = -1; x <= 1; x++)
            for (int y = -1; y <= 1; y++)
                for (int z = -1; z <= 1; z++)
                    if (world.getBlockState(pos.add(x, y, z)).getBlock() == this && (z != 0 || y != 0 || x != 0))
                        return;
        world.setBlockToAir(pos);
    }

    @Override
    public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
        if (!worldIn.isRemote) {
            removeTree(worldIn, pos, null, ItemStack.EMPTY, false);
        }
    }

    @Override
    public boolean isToolEffective(String type, IBlockState state) {
        return ("hammer".equals(type) && ConfigTFC.General.TREE.enableHammerSticks) || super.isToolEffective(type, state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(LOG_AXIS, EnumAxis.values()[meta & 0b11]).withProperty(PLACED, (meta & 0b100) == 0b100).withProperty(SMALL, (meta & 0b1000) == 0b1000);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(LOG_AXIS).ordinal() | (state.getValue(PLACED) ? 0b100 : 0) | (state.getValue(SMALL) ? 0b1000 : 0);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LOG_AXIS, PLACED, SMALL);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        // Don't do vanilla leaf decay
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        // Small logs are a weird feature, for now they shall be disabled via shift placement since it interferes with log pile placement
        return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(PLACED, true);
    }

    @Override
    public Size getSize(ItemStack stack) {
        return Size.VERY_LARGE; // Can't store anywhere, but not overburden
    }

    @Override
    public Weight getWeight(ItemStack stack) {
        return Weight.MEDIUM; // Stacksize = 16
    }

    private boolean removeTree(World world, BlockPos pos, @Nullable EntityPlayer player, ItemStack stack, boolean stoneTool) {
        final boolean explosion = stack.isEmpty() || player == null;
        final int maxLogs = explosion ? Integer.MAX_VALUE : 1 + stack.getMaxDamage() - stack.getItemDamage();

        // Find all logs and add them to a list
        List<BlockPos> logs = new ArrayList<>(50);
        Set<BlockPos> checked = new HashSet<>(50 * 3 * 3);
        logs.add(pos);
        for (int i = 0; i < logs.size(); i++) {
            final BlockPos pos1 = logs.get(i);
            // check for nearby logs
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    for (int z = -1; z <= 1; z++) {
                        final BlockPos pos2 = pos1.add(x, y, z);
                        if (!checked.contains(pos2)) {
                            checked.add(pos2);
                            IBlockState state = world.getBlockState(pos2);
                            if (state.getBlock() == this && !state.getValue(PLACED)) {
                                logs.add(pos2);
                            }
                        }
                    }
                }
            }
        }
        // Sort the list in terms of max distance to the original tree
        logs.sort(Comparator.comparing(x -> -x.distanceSq(pos)));

        // Start removing logs
        for (final BlockPos pos1 : logs.subList(0, Math.min(logs.size(), maxLogs))) {
            if (explosion) {
                // Explosions are 30% Efficient: no TNT powered tree farms.
                if (Constants.RNG.nextFloat() < 0.3) {
                    if (!world.isRemote) {
                        Helpers.spawnItemStack(world, pos.add(0.5d, 0.5d, 0.5d), new ItemStack(Item.getItemFromBlock(this)));
                    }
                }
            } else {
                // Stone tools are 60% efficient (default config)
                if (!stoneTool || Constants.RNG.nextFloat() < ConfigTFC.General.TREE.stoneAxeReturnRate && !world.isRemote) {
                    harvestBlock(world, player, pos1, world.getBlockState(pos1), null, stack);
                }
                stack.damageItem(1, player);
            }
            if (!world.isRemote) {
                world.setBlockToAir(pos1);
            }
        }
        return maxLogs >= logs.size();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new CustomStateMap.Builder().customPath(getRegistryLocation()).ignore(BlockWoodLog.PLACED).build());

        for (var state : getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),
                    getMetaFromState(state),
                    new ModelResourceLocation(getRegistryLocation(), "normal"));
        }
    }
}
