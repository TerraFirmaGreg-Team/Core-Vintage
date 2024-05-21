package su.terrafirmagreg.modules.device.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlock;
import su.terrafirmagreg.api.spi.tile.provider.ITileProvider;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.modules.animal.objects.entities.EntityAnimalBase;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalHare;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalPheasant;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalRabbit;
import su.terrafirmagreg.modules.animal.objects.entities.huntable.EntityAnimalTurkey;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalChicken;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalDuck;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalGrouse;
import su.terrafirmagreg.modules.animal.objects.entities.livestock.EntityAnimalQuail;
import su.terrafirmagreg.modules.device.objects.tiles.TileSnare;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;


import lyeoj.tfcthings.main.ConfigTFCThings;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.objects.entity.animal.AnimalFood;
import net.dries007.tfc.objects.items.ItemSeedsTFC;

import org.jetbrains.annotations.Nullable;

import java.util.Random;

import static su.terrafirmagreg.api.data.Blockstates.*;

@SuppressWarnings("deprecation")
public class BlockSnare extends BaseBlock implements ITileProvider {

    protected static final AxisAlignedBB TRAP_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D);

    public BlockSnare() {
        super(Settings.of(Material.WOOD));

        getSettings()
                .registryKey("device/snare")
                .soundType(SoundType.WOOD)
                .hardness(1.5f)
                .nonFullCube()
                .nonOpaque()
                .size(Size.LARGE)
                .weight(Weight.MEDIUM);

        setTickRandomly(true);
        setHarvestLevel("axe", 0);
        setDefaultState(getBlockState().getBaseState()
                .withProperty(HORIZONTAL, EnumFacing.NORTH)
                .withProperty(BAITED, Boolean.FALSE)
                .withProperty(CLOSED, Boolean.FALSE));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HORIZONTAL, BAITED, CLOSED);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta % 4))
                .withProperty(BAITED, meta / 4 % 2 != 0)
                .withProperty(CLOSED, meta / 8 != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HORIZONTAL).getHorizontalIndex() + (state.getValue(BAITED) ? 4 : 0) + (state.getValue(CLOSED) ? 8 : 0);
    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(HORIZONTAL, placer.getHorizontalFacing());
    }

    @Override
    public @Nullable AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        AxisAlignedBB axisalignedbb = blockState.getBoundingBox(worldIn, pos);
        return new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.maxX, (float) 0 * 0.125F, axisalignedbb.maxZ);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        IBlockState iblockstate = worldIn.getBlockState(pos.down());
        Block block = iblockstate.getBlock();

        if (block != Blocks.BARRIER) {
            BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos.down(), EnumFacing.UP);
            return blockfaceshape == BlockFaceShape.SOLID || iblockstate.getBlock()
                    .isLeaves(iblockstate, worldIn, pos.down());
        } else {
            return false;
        }
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        var tile = TileUtils.getTile(worldIn, pos, TileSnare.class);
        if (!tile.isOpen()) {
            if (Math.random() < ConfigTFCThings.Items.SNARE.breakChance) {
                worldIn.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0f, 0.8f);
            } else {
                super.harvestBlock(worldIn, player, pos, state, te, stack);
            }
        } else {
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!state.getValue(BAITED)) {
            ItemStack stack = playerIn.getHeldItem(hand);
            if ((stack.getItem() instanceof ItemSeedsTFC || isFood(stack)) && !worldIn.isRemote) {
                if (!playerIn.isCreative()) {
                    stack.shrink(1);
                    if (stack.isEmpty()) {
                        playerIn.inventory.deleteStack(stack);
                    }
                }
                state = state.withProperty(BAITED, Boolean.TRUE);
                worldIn.setBlockState(pos, state, 2);
            }
        }
        return true;
    }

    private boolean isFood(ItemStack stack) {
        AnimalFood food = AnimalFood.get(EntityAnimalChicken.class);
        return food != null && food.isFood(stack);
    }

    @Override
    public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        if (isCapturable(entityIn)) {
            var tile = TileUtils.getTile(worldIn, pos, TileSnare.class);
            EntityLivingBase entityLiving = (EntityLivingBase) entityIn;
            assert tile != null;
            if (tile.isOpen()) {
                tile.setCapturedEntity(entityLiving);
                entityIn.setPositionAndUpdate(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                tile.setOpen(false);
                state = state.withProperty(CLOSED, Boolean.TRUE);
                state = state.withProperty(BAITED, Boolean.FALSE);
                worldIn.setBlockState(pos, state, 2);
            } else if (tile.getCapturedEntity() != null && tile.getCapturedEntity().equals(entityLiving)) {
                entityLiving.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            }
        }
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        AxisAlignedBB captureBox = new AxisAlignedBB(pos.getX() - 10.0D, pos.getY() - 5.0D, pos.getZ() - 10.0D, pos.getX() + 10.0D, pos.getY() + 5.0D, pos.getZ() + 10.0D);
        var tile = TileUtils.getTile(worldIn, pos, TileSnare.class);
        if (tile.isOpen() && worldIn.getEntitiesWithinAABB(EntityPlayer.class, captureBox).isEmpty() && !worldIn.isRemote) {
            for (EntityAnimalBase animal : worldIn.getEntitiesWithinAABB(EntityAnimalBase.class, captureBox)) {
                if ((isCapturable(animal)) && !(worldIn.getBlockState(animal.getPosition())
                        .getBlock() instanceof BlockSnare)) {
                    tile.setCapturedEntity(animal);
                    tile.setOpen(false);
                    state = state.withProperty(CLOSED, Boolean.TRUE);
                    state = state.withProperty(BAITED, Boolean.FALSE);
                    worldIn.setBlockState(pos, state, 2);
                    animal.setPositionAndUpdate(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
                    return;
                }
            }
            if (state.getValue(BAITED)) {
                if (rand.nextDouble() < ConfigTFCThings.Items.SNARE.baitCaptureChance) {
                    double entitySelection = rand.nextDouble();
                    EntityAnimalBase animal;
                    if (entitySelection < 0.1) {
                        if (entitySelection < 0.03) {
                            if (entitySelection < 0.01) {
                                animal = new EntityAnimalGrouse(worldIn);
                            } else {
                                animal = new EntityAnimalQuail(worldIn);
                            }
                        } else {
                            animal = new EntityAnimalDuck(worldIn);
                        }
                    } else if (entitySelection < 0.5) {
                        if (entitySelection < 0.3) {
                            animal = new EntityAnimalHare(worldIn);
                        } else {
                            animal = new EntityAnimalRabbit(worldIn);
                        }
                    } else {
                        animal = new EntityAnimalPheasant(worldIn);
                    }
                    animal.setLocationAndAngles(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 0, 0);
                    worldIn.spawnEntity(animal);
                    tile.setCapturedEntity(animal);
                    tile.setOpen(false);
                    state = state.withProperty(CLOSED, Boolean.TRUE);
                    state = state.withProperty(BAITED, Boolean.FALSE);
                    worldIn.setBlockState(pos, state, 2);
                } else if (rand.nextDouble() < ConfigTFCThings.Items.SNARE.baitExpireChance) {
                    state = state.withProperty(BAITED, Boolean.FALSE);
                    worldIn.setBlockState(pos, state, 2);
                }
            }
        }
    }

    private boolean isCapturable(Entity entityIn) {
        return entityIn instanceof EntityAnimalRabbit || entityIn instanceof EntityAnimalPheasant || entityIn instanceof EntityAnimalDuck ||
                entityIn instanceof EntityAnimalChicken || entityIn instanceof EntityAnimalTurkey;
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP)) {
            var tile = TileUtils.getTile(worldIn, pos, TileSnare.class);
            if (!tile.isOpen()) {
                if (Math.random() < ConfigTFCThings.Items.SNARE.breakChance) {
                    worldIn.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0f, 0.8f);
                } else {
                    this.dropBlockAsItem(worldIn, pos, state, 0);
                }
            } else {
                this.dropBlockAsItem(worldIn, pos, state, 0);
            }
            worldIn.setBlockToAir(pos);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return TRAP_AABB;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
        return true;
    }

    @Override
    public Class<? extends TileEntity> getTileEntityClass() {
        return TileSnare.class;
    }

    @Override
    public TileSnare createNewTileEntity(World worldIn, int meta) {
        return new TileSnare();
    }
}
