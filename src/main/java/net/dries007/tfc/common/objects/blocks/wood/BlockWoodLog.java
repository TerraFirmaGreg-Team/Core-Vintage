package net.dries007.tfc.common.objects.blocks.wood;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.wood.IWoodBlock;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.block.WoodBlockVariant;
import net.dries007.tfc.client.util.CustomStateMap;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.items.itemblocks.ItemBlockTFC;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.util.Constants;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.OreDictionaryHelper;
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
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class BlockWoodLog extends BlockLog implements IItemSize, IWoodBlock {
    public static final PropertyBool PLACED = PropertyBool.create("placed");
    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockWoodLog(WoodBlockVariant variant, WoodType type) {
        this.variant = variant;
        this.type = type;

        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());
        setCreativeTab(CreativeTabsTFC.WOOD);

        setDefaultState(blockState.getBaseState()
                .withProperty(LOG_AXIS, BlockLog.EnumAxis.Y)
                .withProperty(PLACED, true));
        setHarvestLevel("axe", 0);
        setHardness(2.0F);
        setResistance(5.0F);

        Blocks.FIRE.setFireInfo(this, 5, 5);

        OreDictionaryHelper.register(this, "logWood");
        OreDictionaryHelper.register(this, variant.toString(), "wood", type.toString());
        if (type.canMakeTannin()) {
            OreDictionaryHelper.register(this, variant.toString(), "wood", "tannin");
        }
    }

    @Override
    public WoodBlockVariant getBlockVariant() {
        return variant;
    }

    @Override
    public WoodType getType() {
        return type;
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTFC(this);
    }

    //TODO в этом не вижу смысла после добавления dt, так как все бревна, что имели бы свойство PLACED,
    // генерируются dt, а у них свой подсчет Hardness
//    @Override
//    @SuppressWarnings("deprecation")
//    public float getBlockHardness(IBlockState blockState, World worldIn, BlockPos pos) {
//        return (blockState.getValue(PLACED) ? 1.0f : 2.5f) * super.getBlockHardness(blockState, worldIn, pos);
//    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FULL_BLOCK_AABB;
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
        return ("hammer".equals(type) && ConfigTFC.General.TREE.enableHammerSticks) ||
                super.isToolEffective(type, state);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState()
                .withProperty(LOG_AXIS, EnumAxis.values()[meta & 0b11])
                .withProperty(PLACED, (meta & 0b100) == 0b100);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(LOG_AXIS).ordinal() |
                (state.getValue(PLACED) ? 0b100 : 0);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LOG_AXIS, PLACED);
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

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack) {
        return Size.VERY_LARGE; // Can't store anywhere, but not overburden
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack) {
        return Weight.MEDIUM; // Stacksize = 16
    }

    // TODO это нужно?
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

    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this,
                new CustomStateMap.Builder()
                        .customPath(getResourceLocation())
                        .ignore(BlockWoodLog.PLACED)
                        .customVariant("woodtype=" + getType())
                        .build());

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getResourceLocation(),
                        "axis=none,woodtype=" + getType()));
    }
}
