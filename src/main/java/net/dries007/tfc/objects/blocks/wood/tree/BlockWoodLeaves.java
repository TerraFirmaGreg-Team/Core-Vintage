package net.dries007.tfc.objects.blocks.wood.tree;

import com.google.common.collect.ImmutableList;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.wood.IWoodBlock;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.WoodVariant_old;
import net.dries007.tfc.client.particle.TFCParticles;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;

import static net.dries007.tfc.Constants.RNG;
import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.wood.variant.WoodVariant_old.LOG;
import static net.dries007.tfc.api.types.wood.variant.WoodVariant_old.SAPLING;

@ParametersAreNonnullByDefault
public class BlockWoodLeaves extends BlockLeaves implements IWoodBlock {
    private final WoodVariant_old woodVariant;
    private final WoodType woodType;
    private final ResourceLocation modelLocation;

    public BlockWoodLeaves(WoodVariant_old woodVariant, WoodType woodType) {
        this.woodVariant = woodVariant;
        this.woodType = woodType;
        this.modelLocation = new ResourceLocation(MOD_ID, "wood/" + woodVariant);

        var blockRegistryName = String.format("wood/%s/%s", woodVariant, woodType);
        setRegistryName(MOD_ID, blockRegistryName);
        setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
        setCreativeTab(CreativeTabsTFC.WOOD);

        setDefaultState(blockState.getBaseState().withProperty(DECAYABLE, false)); // TFC leaves don't use CHECK_DECAY, so just don't use it

        leavesFancy = true; // Fast / Fancy graphics works correctly
        OreDictionaryHelper.register(this, "tree", "leaves");
        //noinspection ConstantConditions
        OreDictionaryHelper.register(this, "tree", "leaves", woodType.toString());
        Blocks.FIRE.setFireInfo(this, 30, 60);
        setTickRandomly(true);
    }

    @Override
    public WoodVariant_old getWoodVariant() {
        return woodVariant;
    }

    @Override
    public WoodType getWood() {
        return woodType;
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTFC(this);
    }

    @SuppressWarnings("deprecation")
    @Override
    @Nonnull
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(DECAYABLE, (meta & 0b01) == 0b01);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return (state.getValue(DECAYABLE) ? 1 : 0);
    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return NULL_AABB;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(IBlockState state, World world, BlockPos pos, @Nullable Block blockIn, @Nullable BlockPos fromPos) {
        world.scheduleUpdate(pos, this, 0);
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (!(entityIn instanceof EntityPlayer && ((EntityPlayer) entityIn).isCreative())) {
            // Player will take damage when falling through leaves if fall is over 9 blocks, fall damage is then set to 0.
            entityIn.fall((entityIn.fallDistance - 6), 1.0F);
            entityIn.fallDistance = 0;
            // Entity motion is reduced by leaves.
            entityIn.motionX *= ConfigTFC.General.MISC.leafMovementModifier;
            if (entityIn.motionY < 0) {
                entityIn.motionY *= ConfigTFC.General.MISC.leafMovementModifier;
            }
            entityIn.motionZ *= ConfigTFC.General.MISC.leafMovementModifier;
        }
    }

    @Override
    @Nonnull
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, DECAYABLE);
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        doLeafDecay(worldIn, pos, state);
    }

    @Override
    @Nonnull
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ConfigTFC.General.TREE.enableSaplings ? Item.getItemFromBlock(TFCStorage.getWoodBlock(SAPLING, woodType)) : Items.AIR;
    }

    @Override
    protected int getSaplingDropChance(IBlockState state) {
        return 1; //wood == Tree.SEQUOIA ? 0 : 25;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    @Nonnull
    public BlockRenderLayer getRenderLayer() {
        /*
         * This is a way to make sure the leave settings are updated.
         * The result of this call is cached somewhere, so it's not that important, but:
         * The alternative would be to use `Minecraft.getMinecraft().gameSettings.fancyGraphics` directly in the 2 relevant methods.
         * It's better to do that than to refer to Blocks.LEAVES, for performance reasons.
         */
        leavesFancy = Minecraft.getMinecraft().gameSettings.fancyGraphics;
        return super.getRenderLayer();
    }

    @Override
    @Nonnull
    public BlockPlanks.EnumType getWoodType(int meta) {
        // Unused so return whatever
        return BlockPlanks.EnumType.OAK;
    }

    @Override
    public void beginLeavesDecay(IBlockState state, World world, BlockPos pos) {
        // Don't do vanilla decay
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        int chance = this.getSaplingDropChance(state);
        if (chance > 0) {
            if (fortune > 0) {
                chance -= 2 << fortune;
                if (chance < 10) chance = 10;
            }

            if (RANDOM.nextInt(chance) == 0) {
                ItemStack drop = new ItemStack(getItemDropped(state, RANDOM, fortune), 1, damageDropped(state));
                if (!drop.isEmpty()) {
                    drops.add(drop);
                }
            }
        }
    }

    @SuppressWarnings("deprecation")
    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        /*
         * See comment on getRenderLayer()
         */
        leavesFancy = Minecraft.getMinecraft().gameSettings.fancyGraphics;
        return true;// super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @Override
    @Nonnull
    public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        return ImmutableList.of(new ItemStack(this));
    }

    private void doLeafDecay(World world, BlockPos pos, IBlockState state) {
        // TFC Leaf Decay
        if (world.isRemote || !state.getValue(DECAYABLE))
            return;

        Set<BlockPos> paths = new HashSet<>();
        Set<BlockPos> evaluated = new HashSet<>(); // Leaves that everything was evaluated so no need to do it again
        List<BlockPos> pathsToAdd; // New Leaves that needs evaluation
        BlockPos.MutableBlockPos pos1 = new BlockPos.MutableBlockPos(pos);
        IBlockState state1;
        paths.add(pos); // Center block

        for (int i = 0; i < 4; i++) { //todo woodType.getMaxDecayDistance()
            pathsToAdd = new ArrayList<>();
            for (BlockPos p1 : paths) {
                for (EnumFacing face : EnumFacing.values()) {
                    pos1.setPos(p1).move(face);
                    if (evaluated.contains(pos1) || !world.isBlockLoaded(pos1))
                        continue;
                    state1 = world.getBlockState(pos1);
                    if (state1.getBlock() == TFCStorage.getWoodBlock(LOG, woodType))
                        return;
                    if (state1.getBlock() == this)
                        pathsToAdd.add(pos1.toImmutable());
                }
                evaluated.add(p1); // Evaluated
            }
            paths.addAll(pathsToAdd);
            paths.removeAll(evaluated);
        }

        world.setBlockToAir(pos);
        int particleScale = 10;
        double x = pos.getX();
        double y = pos.getY();
        double z = pos.getZ();
        for (int i = 1; i < RNG.nextInt(4); i++) {
            switch (RNG.nextInt(4)) {
                case 1 ->
                        TFCParticles.LEAF1.sendToAllNear(world, x + RNG.nextFloat() / particleScale, y - RNG.nextFloat() / particleScale, z + RNG.nextFloat() / particleScale, (RNG.nextFloat() - 0.5) / particleScale, -0.15D + RNG.nextFloat() / particleScale, (RNG.nextFloat() - 0.5) / particleScale, 90);
                case 2 ->
                        TFCParticles.LEAF2.sendToAllNear(world, x + RNG.nextFloat() / particleScale, y - RNG.nextFloat() / particleScale, z + RNG.nextFloat() / particleScale, (RNG.nextFloat() - 0.5) / particleScale, -0.15D + RNG.nextFloat() / particleScale, (RNG.nextFloat() - 0.5) / particleScale, 70);
                case 3 ->
                        TFCParticles.LEAF3.sendToAllNear(world, x + RNG.nextFloat() / particleScale, y - RNG.nextFloat() / particleScale, z + RNG.nextFloat() / particleScale, (RNG.nextFloat() - 0.5) / particleScale, -0.15D + RNG.nextFloat() / particleScale, (RNG.nextFloat() - 0.5) / particleScale, 80);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(modelLocation,
                        "wood=" + woodType.toString());
            }
        });


        for (IBlockState state : this.getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(
                    Item.getItemFromBlock(this),
                    this.getMetaFromState(state),
                    new ModelResourceLocation(modelLocation,
                            "wood=" + woodType.toString()));
        }
    }
}
