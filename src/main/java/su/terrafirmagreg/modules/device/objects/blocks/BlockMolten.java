package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlock;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.api.data.Blockstates.LIT;

@SuppressWarnings("deprecation")
public class BlockMolten extends BaseBlock {

    public static final PropertyInteger LAYERS = PropertyInteger.create("layers", 1, 4);

    private static final AxisAlignedBB[] MOLTEN_AABB = new AxisAlignedBB[] {
            new AxisAlignedBB(0, 0, 0, 1, 0.25, 1),
            new AxisAlignedBB(0, 0, 0, 1, 0.5, 1),
            new AxisAlignedBB(0, 0, 0, 1, 0.75, 1),
            FULL_BLOCK_AABB
    };

    public BlockMolten() {
        super(Settings.of()
                .material(Material.ROCK)
                .nonFullCube()
                .nonOpaque()
                .hardness(-1));

        setDefaultState(getBlockState().getBaseState()
                .withProperty(LIT, false)
                .withProperty(LAYERS, 1));
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(LAYERS, (meta & 0b11) + 1).withProperty(LIT, meta > 3);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(LAYERS) + (state.getValue(LIT) ? 4 : 0) - 1;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return MOLTEN_AABB[state.getValue(LAYERS) - 1];
    }

    @Override
    public @Nullable AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return MOLTEN_AABB[blockState.getValue(LAYERS) - 1];
    }

    @SideOnly(Side.CLIENT)
    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
        return MOLTEN_AABB[state.getValue(LAYERS) - 1];
    }

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
        IBlockState state = worldIn.getBlockState(pos);
        if (state.getValue(LIT) && !entityIn.isImmuneToFire() && entityIn instanceof EntityLivingBase && state.getValue(LIT)) {
            entityIn.attackEntityFrom(DamageSource.IN_FIRE, 4.0f);
        }
        super.onEntityWalk(worldIn, pos, entityIn);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LIT, LAYERS);
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.getValue(LIT) ? 15 : 0;
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        // Drops are handled by the relevant TE (blast furnace or bloomery)
    }

    @Override
    public @Nullable PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EntityLiving entity) {
        return state.getValue(LIT) && (entity == null || !entity.isImmuneToFire()) ? net.minecraft.pathfinding.PathNodeType.DAMAGE_FIRE : null;
    }

    @Override
    public String getName() {
        return "device/molten";
    }
}
