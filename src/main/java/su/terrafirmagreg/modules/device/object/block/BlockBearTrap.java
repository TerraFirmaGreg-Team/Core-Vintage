package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.base.block.BaseBlock;
import su.terrafirmagreg.api.registry.provider.IProviderTile;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.data.DamageSources;
import su.terrafirmagreg.data.ToolClasses;
import su.terrafirmagreg.modules.animal.api.type.IPredator;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.device.ConfigDevice;
import su.terrafirmagreg.modules.device.object.tile.TileBearTrap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.metal.ItemMetalTool;

import org.jetbrains.annotations.Nullable;

import static su.terrafirmagreg.data.Properties.BoolProp.BURIED;
import static su.terrafirmagreg.data.Properties.BoolProp.CLOSED;
import static su.terrafirmagreg.data.Properties.DirectionProp.HORIZONTAL;

@SuppressWarnings("deprecation")
public class BlockBearTrap extends BaseBlock implements IProviderTile {

  protected static final AxisAlignedBB TRAP_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D);

  public BlockBearTrap() {
    super(Settings.of(Material.IRON));

    getSettings()
      .registryKey("device/bear_trap")
      .hardness(10.0F)
      .resistance(10.0F)
      .nonCube()
      .size(Size.LARGE)
      .weight(Weight.HEAVY);
    setHarvestLevel(ToolClasses.PICKAXE, 0);
    setDefaultState(blockState.getBaseState()
                              .withProperty(HORIZONTAL, EnumFacing.NORTH)
                              .withProperty(BURIED, Boolean.FALSE)
                              .withProperty(CLOSED, Boolean.FALSE));
  }

  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState()
               .withProperty(HORIZONTAL, EnumFacing.byHorizontalIndex(meta % 4))
               .withProperty(BURIED, meta / 4 % 2 != 0)
               .withProperty(CLOSED, meta / 8 != 0);
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(HORIZONTAL).getHorizontalIndex() + (state.getValue(BURIED) ? 4 : 0) + (
      state.getValue(CLOSED) ? 8 : 0);
  }

  @Override
  public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
    return true;
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    return TRAP_AABB;
  }

  @Override
  public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (!worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP)) {
      TileUtils.getTile(worldIn, pos, TileBearTrap.class).ifPresent(tile -> {
        if (!tile.isOpen()) {
          if (Math.random() < ConfigDevice.BLOCK.BEAR_TRAP.breakChance) {
            worldIn.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0f, 0.8f);
          } else {
            this.dropBlockAsItem(worldIn, pos, state, 0);
          }
        } else {
          this.dropBlockAsItem(worldIn, pos, state, 0);
        }
        worldIn.setBlockToAir(pos);
      });
    }
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    IBlockState iblockstate = worldIn.getBlockState(pos.down());
    Block block = iblockstate.getBlock();

    if (block != Blocks.BARRIER) {
      BlockFaceShape blockfaceshape = iblockstate.getBlockFaceShape(worldIn, pos.down(),
                                                                    EnumFacing.UP);
      return blockfaceshape == BlockFaceShape.SOLID || iblockstate.getBlock()
                                                                  .isLeaves(iblockstate, worldIn, pos.down());
    } else {
      return false;
    }
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    var item = playerIn.getHeldItem(hand).getItem();
    if (item instanceof ItemSpade || (item instanceof ItemMetalTool itemMetalTool && itemMetalTool.getType().equals(Metal.ItemType.SHOVEL))) {

      playerIn.getHeldItem(hand).damageItem(1, playerIn);
      state = state.cycleProperty(BURIED);
      worldIn.setBlockState(pos, state, 2);
      worldIn.playSound(playerIn, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0f, 1.0f);
    }
    return true;
  }

  @Override
  public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    return this.getDefaultState().withProperty(HORIZONTAL, placer.getHorizontalFacing());
  }

  @Override
  public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entityIn) {

    if (entityIn instanceof EntityLivingBase entityLiving) {

      TileUtils.getTile(world, pos, TileBearTrap.class).ifPresent(tile -> {
        IBlockState iBlockState = state;
        if (tile.isOpen()) {
          int debuffDuration = ConfigDevice.BLOCK.BEAR_TRAP.debuffDuration;
          double healthCut = ConfigDevice.BLOCK.BEAR_TRAP.healthCut;
          entityLiving.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, debuffDuration));
          entityLiving.addPotionEffect(new PotionEffect(MobEffects.MINING_FATIGUE, debuffDuration));
          entityLiving.addPotionEffect(new PotionEffect(MobEffects.WEAKNESS, debuffDuration));
          if (ConfigDevice.BLOCK.BEAR_TRAP.fixedDamage > 0) {
            entityLiving.attackEntityFrom(DamageSources.BEAR_TRAP,
                                          (float) ConfigDevice.BLOCK.BEAR_TRAP.fixedDamage);
          } else if (healthCut > 0) {
            entityLiving.attackEntityFrom(DamageSources.BEAR_TRAP,
                                          entityLiving.getHealth() / (float) healthCut);
          }
          tile.setCapturedEntity(entityLiving);
          entityIn.setPositionAndUpdate(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
          tile.setOpen(false);
          iBlockState = iBlockState.withProperty(CLOSED, Boolean.TRUE);
          world.setBlockState(pos, iBlockState, 2);
          entityLiving.playSound(SoundEvents.ENTITY_ITEM_BREAK, 2.0F, 0.4F);
        } else if (tile.getCapturedEntity() != null && tile.getCapturedEntity().equals(entityLiving)) {
          entityLiving.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
          if (entityLiving instanceof IPredator
              && Math.random() < ConfigDevice.BLOCK.BEAR_TRAP.breakoutChance) {
            world.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0f,
                            0.8f);
            if (Math.random() > 2 * ConfigDevice.BLOCK.BEAR_TRAP.breakChance) {
              this.dropBlockAsItem(world, pos, iBlockState, 0);
            }
            world.setBlockState(pos, Blocks.AIR.getDefaultState(), world.isRemote ? 11 : 3);
          }
          if (entityLiving.isDead) {
            tile.setCapturedEntity(null);
          }
        }
      });
    }
  }

  @Override
  public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity tile, ItemStack stack) {
    if (tile instanceof TileBearTrap tileBearTrap && !tileBearTrap.isOpen()) {
      if (Math.random() < ConfigDevice.BLOCK.BEAR_TRAP.breakChance) {
        world.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1.0f, 0.8f);
      }
    }
    super.harvestBlock(world, player, pos, state, tile, stack);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, HORIZONTAL, BURIED, CLOSED);
  }

  @Override
  public @Nullable AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    AxisAlignedBB axisalignedbb = blockState.getBoundingBox(worldIn, pos);
    return new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.maxX, (float) 0 * 0.125F, axisalignedbb.maxZ);
  }

  @Override
  public Class<TileBearTrap> getTileClass() {
    return TileBearTrap.class;
  }

  @Override
  public @Nullable TileBearTrap createNewTileEntity(World worldIn, int meta) {
    return new TileBearTrap();
  }
}
