package su.terrafirmagreg.modules.device.object.block;

import net.dries007.tfc.api.util.IBellowsConsumerBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.base.object.block.spi.BaseBlockHorizontal;
import su.terrafirmagreg.api.data.ToolClasses;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.network.spi.GuiHandler;
import su.terrafirmagreg.framework.registry.api.provider.IProviderTile;
import su.terrafirmagreg.modules.core.capabilities.size.CapabilityProviderSize;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.device.object.item.ItemFireStarter;
import su.terrafirmagreg.modules.device.object.tile.TileBellows;
import su.terrafirmagreg.modules.device.object.tile.TileSmelteryFirebox;

import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.BoolProp.LIT;
import static su.terrafirmagreg.api.data.Properties.DirectionProp.HORIZONTAL;

@SuppressWarnings("deprecation")
public class BlockSmelteryFirebox extends BaseBlockHorizontal implements IBellowsConsumerBlock, IProviderTile {

    public BlockSmelteryFirebox() {
        super(Settings.of(Material.IRON));

        getSettings()
                .registryKey("smeltery/firebox")
                .sound(SoundType.STONE)
                .hardness(3.0F)
                .lightValue(1)
                .nonFullCube()
                .nonOpaque()
                .randomTicks()
                .harvestLevel(ToolClasses.PICKAXE, 0)
                .capability(CapabilityProviderSize.of(Size.LARGE, Weight.MEDIUM));

        setDefaultState(getBlockState().getBaseState()
                .withProperty(LIT, false)
                .withProperty(HORIZONTAL, EnumFacing.NORTH));

    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState()
                .withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta % 4))
                .withProperty(LIT, meta / 4 % 2 != 0);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HORIZONTAL).getHorizontalIndex() + (state.getValue(LIT) ? 4 : 0);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LIT, HORIZONTAL);
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return face == EnumFacing.DOWN ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        return state.getValue(LIT) ? 15 : 0;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FULL_BLOCK_AABB;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rng) {
        if (!state.getValue(LIT)) {
            return;
        }

        if (rng.nextInt(24) == 0) {
            world.playSound((float) pos.getX() + 0.5F, (float) pos.getY() + 0.5F,
                    (float) pos.getZ() + 0.5F, SoundEvents.BLOCK_FIRE_AMBIENT,
                    SoundCategory.BLOCKS, 1.0F + rng.nextFloat(), rng.nextFloat() * 0.7F + 0.3F, false);
        }
        if (rng.nextFloat() < 0.4f) {
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 0.35;
            double z = pos.getZ() + 0.5;
            switch (state.getValue(HORIZONTAL)) {
                case NORTH:
                    z -= 0.6f;
                    break;
                case SOUTH:
                    z += 0.6f;
                    break;
                case WEST:
                    x += 0.6f;
                    break;
                case EAST:
                    x += 0.6f;
                    break;
            }

            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x, y, z, 0.0D, 0.2D, 0.0D);
            if (rng.nextFloat() > 0.75) {
                world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, x, y, z, 0.0D, 0.1D, 0.0D);
            }
        }
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileUtils.getTile(worldIn, pos, TileSmelteryFirebox.class).ifPresent(tile -> tile.onBreakBlock(worldIn, pos, state));
        super.breakBlock(worldIn, pos, state);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (player.isSneaking()) {
            return false;
        }
        if (world.isRemote) {
            return true;
        }

        ItemStack held = player.getHeldItem(hand);
        if (world.getBlockState(pos.up()).getBlock() instanceof BlockSmelteryCauldron) {
            TileUtils.getTile(world, pos, TileSmelteryFirebox.class).ifPresent(tile -> {
                if (ItemFireStarter.canIgnite(held) && tile.onIgnite()) {
                    ItemFireStarter.onIgnition(held);
                } else {
                    GuiHandler.openGui(world, pos, player);
                }
            });

        } else {
            if (held.getItem() instanceof ItemBlock itemBlock && itemBlock.getBlock() instanceof BlockSmelteryCauldron &&
                    world.getBlockState(pos.up())
                            .getMaterial()
                            .isReplaceable()) {
                held.getItem().onItemUse(player, world, pos.up(), hand, side, hitX, hitY, hitZ);
            } else {
                player.sendStatusMessage(
                        new TextComponentTranslation("tooltip.tfctech.smeltery.invalid"), true);
            }
        }
        return true;
    }

    @Override
    public boolean canIntakeFrom(@NotNull Vec3i offset, @NotNull EnumFacing direction) {
        return offset.equals(TileBellows.OFFSET_LEVEL);
    }

    @Override
    public void onAirIntake(@NotNull World world, @NotNull BlockPos pos, int airAmount) {
        TileUtils.getTile(world, pos, TileSmelteryFirebox.class).ifPresent(tile -> tile.onAirIntake(airAmount));
    }

    @Nullable
    @Override
    public TileSmelteryFirebox createNewTileEntity(World worldIn, int meta) {
        return new TileSmelteryFirebox();
    }

    @Override
    public Class<TileSmelteryFirebox> getTileClass() {
        return TileSmelteryFirebox.class;
    }
}
