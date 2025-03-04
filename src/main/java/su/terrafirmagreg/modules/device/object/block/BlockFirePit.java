package su.terrafirmagreg.modules.device.object.block;

import su.terrafirmagreg.api.base.object.block.spi.BaseBlockContainer;
import su.terrafirmagreg.api.data.DamageSources;
import su.terrafirmagreg.api.data.enums.EnumFirePitAttachment;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.api.util.TileUtils;
import su.terrafirmagreg.framework.network.spi.GuiHandler;
import su.terrafirmagreg.modules.core.init.FluidsCore;
import su.terrafirmagreg.modules.core.init.ItemsCore;
import su.terrafirmagreg.modules.device.client.render.TESRFirePit;
import su.terrafirmagreg.modules.device.object.item.ItemFireStarter;
import su.terrafirmagreg.modules.device.object.tile.TileBellows;
import su.terrafirmagreg.modules.device.object.tile.TileFirePit;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import net.dries007.tfc.api.util.IBellowsConsumerBlock;
import net.dries007.tfc.client.particle.TFCParticles;

import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

import static su.terrafirmagreg.api.data.Properties.BoolProp.LIT;
import static su.terrafirmagreg.api.data.Properties.EnumProp.FIRE_PIT_ATTACHMENT;
import static su.terrafirmagreg.api.util.MathUtils.RNG;

@SuppressWarnings("deprecation")
public class BlockFirePit extends BaseBlockContainer implements IBellowsConsumerBlock {


  private static final AxisAlignedBB FIREPIT_AABB = new AxisAlignedBB(0, 0, 0, 1, 0.125, 1);
  private static final AxisAlignedBB FIREPIT_ATTACHMENT_SELECTION_AABB = new AxisAlignedBB(0, 0, 0,
    1, 0.9375, 1);
  private static final AxisAlignedBB ATTACHMENT_COLLISION_ADDITION_AABB = new AxisAlignedBB(0.1875,
    0.125, 0.1875, 0.8125, 0.9375, 0.8125);

  public BlockFirePit() {
    super(Settings.of(Material.WOOD));

    getSettings()
      .registryKey("fire_pit")
      .hardness(0.3F)
      .randomTicks()
      .nonCube()
      .lightValue(15);
    disableStats();

    setDefaultState(getBlockState().getBaseState()
      .withProperty(LIT, false)
      .withProperty(FIRE_PIT_ATTACHMENT, EnumFirePitAttachment.NONE));
  }

  @Override
  public EnumBlockRenderType getRenderType(IBlockState state) {
    return EnumBlockRenderType.MODEL;
  }


  @Override
  public IBlockState getStateFromMeta(int meta) {
    return this.getDefaultState()
      .withProperty(LIT, (meta & 1) > 0)
      .withProperty(FIRE_PIT_ATTACHMENT, EnumFirePitAttachment.valueOf(meta >> 1));
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return (state.getValue(LIT) ? 1 : 0) + (state.getValue(FIRE_PIT_ATTACHMENT).ordinal() << 1);
  }

  @Override
  public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
    if (state.getValue(FIRE_PIT_ATTACHMENT) != EnumFirePitAttachment.NONE) {
      return FIREPIT_ATTACHMENT_SELECTION_AABB;
    }
    return FIREPIT_AABB;
  }

  @Override
  public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
    addCollisionBoxToList(pos, entityBox, collidingBoxes, FIREPIT_AABB);
    if (state.getValue(FIRE_PIT_ATTACHMENT) != EnumFirePitAttachment.NONE) {
      addCollisionBoxToList(pos, entityBox, collidingBoxes, ATTACHMENT_COLLISION_ADDITION_AABB);
    }
  }

  @Override
  public void randomTick(World world, BlockPos pos, IBlockState state, Random random) {
    TileUtils.getTile(world, pos, TileFirePit.class).ifPresent(tile -> {
      // Have to check the above block, since minecraft think this block is "roof"
      if (state.getValue(LIT) && world.isRainingAt(pos.up())) {
        tile.onRainDrop();
      }
    });

  }

  @SideOnly(Side.CLIENT)
  @Override
  public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rng) {
    if (!state.getValue(LIT)) {
      return;
    }

    if (rng.nextInt(24) == 0) {
      world.playSound(pos.getX() + 0.5F, pos.getY() + 0.5F, pos.getZ() + 0.5F, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS,
        1.0F + rng.nextFloat(), rng.nextFloat() * 0.7F + 0.3F, false);
    }

    double x = pos.getX() + 0.5;
    double y = pos.getY() + 0.1;
    double z = pos.getZ() + 0.5;
    switch (rng.nextInt(3)) {
      case 0:
        TFCParticles.FIRE_PIT_SMOKE1.spawn(world, x, y, z, 0, 0.1D, 0, 120);
        break;
      case 1:
        TFCParticles.FIRE_PIT_SMOKE2.spawn(world, x, y, z, 0, 0.1D, 0, 110);
        break;
      case 2:
        TFCParticles.FIRE_PIT_SMOKE3.spawn(world, x, y, z, 0, 0.1D, 0, 100);
        break;
    }

    if (state.getValue(FIRE_PIT_ATTACHMENT) == EnumFirePitAttachment.COOKING_POT) {
      TileUtils.getTile(world, pos, TileFirePit.class).ifPresent(tile -> {
        if (tile.getCookingPotStage() == TileFirePit.CookingPotStage.BOILING) {
          for (int i = 0; i < rng.nextInt(5) + 4; i++) {
            TFCParticles.BUBBLE.spawn(world, x + rng.nextFloat() * 0.375 - 0.1875, y + 0.525, z + rng.nextFloat() * 0.375 - 0.1875, 0, 0.05D, 0, 3);
          }
          TFCParticles.STEAM.spawn(world, x, y + 0.425F, z, 0, 0, 0, (int) (12.0F / (rng.nextFloat() * 0.9F + 0.1F)));
          world.playSound(x, y + 0.425F, z, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, 1.0F, rng.nextFloat() * 0.7F + 0.4F, false);
        }
      });
    }
    if ((state.getValue(FIRE_PIT_ATTACHMENT) == EnumFirePitAttachment.GRILL)) {
      TileUtils.getTile(world, pos, TileFirePit.class).ifPresent(tile -> {
        IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (cap == null) {return;}
        boolean anythingInInv = false;
        for (int i = TileFirePit.SLOT_EXTRA_INPUT_START; i <= TileFirePit.SLOT_EXTRA_INPUT_END; i++) {
          if (!cap.getStackInSlot(i).isEmpty()) {
            anythingInInv = true;
            break;
          }
        }
        if (state.getValue(LIT) && anythingInInv) {
          world.playSound(x, y + 0.425F, z, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.25F, rng.nextFloat() * 0.7F + 0.4F, false);
          world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, x + rng.nextFloat() / 2 - 0.25, y + 0.6, z + rng.nextFloat() / 2 - 0.25, 0.0D, 0.1D, 0.0D);
        }
      });
    }
  }

  @Override
  public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
    if (!canBePlacedOn(world, pos.add(0, -1, 0))) {
      world.setBlockToAir(pos);
    }
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return Items.AIR;
  }

  @Override
  public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
    return super.canPlaceBlockAt(worldIn, pos) && canBePlacedOn(worldIn, pos.add(0, -1, 0));
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    if (!world.isRemote) {
      ItemStack held = player.getHeldItem(hand);

      // Try to light the fire pit
      if (!state.getValue(LIT)) {
        if (ItemFireStarter.onIgnition(held)) {
          world.setBlockState(pos, state.withProperty(LIT, true));
          return true;
        }
      }

      // Try to attach an item
      EnumFirePitAttachment attachment = state.getValue(FIRE_PIT_ATTACHMENT);
      return TileUtils.getTile(world, pos, TileFirePit.class).map(tile -> {
        if (attachment == EnumFirePitAttachment.NONE) {
          if (OreDictUtils.contains(held, "cookingPot")) {
            world.setBlockState(pos, state.withProperty(FIRE_PIT_ATTACHMENT, EnumFirePitAttachment.COOKING_POT));
            tile.onConvertToCookingPot(player, held);
            return true;
          } else if (OreDictUtils.contains(held, "grill")) {
            world.setBlockState(pos, state.withProperty(FIRE_PIT_ATTACHMENT, EnumFirePitAttachment.GRILL));
            tile.onConvertToGrill(player, held);
            return true;
          }
        } else if (attachment == EnumFirePitAttachment.COOKING_POT) {
          // Interact with the cooking pot
          if (tile.getCookingPotStage() == TileFirePit.CookingPotStage.EMPTY) {
            FluidStack fluidStack = FluidUtil.getFluidContained(held);
            if (fluidStack != null && fluidStack.amount >= 1000
                && fluidStack.getFluid() == FluidsCore.FRESH_WATER.get()) {
              // Add water
              tile.addWaterToCookingPot();
              IFluidHandler fluidHandler = FluidUtil.getFluidHandler(held);
              if (fluidHandler != null) {
                fluidHandler.drain(1000, true);
              }
              world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 0.5f,
                1.0f);
              return true;
            }
          } else if (tile.getCookingPotStage() == TileFirePit.CookingPotStage.FINISHED) {
            if (OreDictUtils.contains(held, "bowl")) {
              tile.onUseBowlOnCookingPot(player, held);
              return true;
            }
          }
        }

        if (!player.isSneaking()) {
          GuiHandler.openGui(world, pos, player);
        } else if ((held == ItemStack.EMPTY) && (attachment != EnumFirePitAttachment.NONE)) {
          boolean anythingInTheInv = false;
          IItemHandler cap = tile.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,
            null);
          if (cap != null) {
            for (int i = TileFirePit.SLOT_EXTRA_INPUT_START; i <= TileFirePit.SLOT_EXTRA_INPUT_END;
                 i++) {
              if (!cap.getStackInSlot(i).isEmpty()) {
                anythingInTheInv = true;
                break;
              }
            }
            if (!anythingInTheInv) {
              if (state.getValue(LIT)) {
                if (attachment == EnumFirePitAttachment.COOKING_POT) {
                  player.attackEntityFrom(DamageSources.SOUP, 1);
                } else {
                  player.attackEntityFrom(DamageSources.GRILL, 1);
                }

              } else {
                tile.onRemoveAttachment(player, held);
                world.setBlockState(pos, state.withProperty(FIRE_PIT_ATTACHMENT, EnumFirePitAttachment.NONE));
                return true;
              }

            }
          }
        }
        return false;
      }).orElse(true);
    }
    return false;
  }

  @Override
  public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
    IBlockState state = worldIn.getBlockState(pos);
    if (state.getValue(LIT) && !entityIn.isImmuneToFire() && entityIn instanceof EntityLivingBase) {
      entityIn.attackEntityFrom(DamageSource.IN_FIRE, 1.0F);
    }
    super.onEntityWalk(worldIn, pos, entityIn);
  }

  @Override
  public void onEntityCollision(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
    if (state.getValue(LIT) && !entityIn.isImmuneToFire() && entityIn instanceof EntityLivingBase) {
      entityIn.attackEntityFrom(DamageSource.IN_FIRE, 1.0F);
    }
    //todo: handle fuel and item inputs from thrown entities
    super.onEntityCollision(worldIn, pos, state, entityIn);
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, LIT, FIRE_PIT_ATTACHMENT);
  }

  @Override
  public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
    drops.add(new ItemStack(ItemsCore.WOOD_ASH.get(), 3 + RNG.nextInt(5)));
  }

  @Nullable
  @Override
  public PathNodeType getAiPathNodeType(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EntityLiving entity) {
    return state.getValue(LIT) && (entity == null || !entity.isImmuneToFire())
           ? PathNodeType.DAMAGE_FIRE : null;
  }

  private boolean canBePlacedOn(World worldIn, BlockPos pos) {
    return worldIn.getBlockState(pos).isSideSolid(worldIn, pos, EnumFacing.UP);
  }

  @Override
  public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
    return state.getValue(LIT) ? super.getLightValue(state, world, pos) : 0;
  }

  @Override
  public boolean canIntakeFrom(Vec3i offset, EnumFacing facing) {
    return offset.equals(TileBellows.OFFSET_LEVEL);
  }

  @Override
  public void onAirIntake(World world, BlockPos pos, int airAmount) {
    TileUtils.getTile(world, pos, TileFirePit.class).ifPresent(tile -> tile.onAirIntake(airAmount));
  }

  @Override
  public Class<TileFirePit> getTileClass() {
    return TileFirePit.class;
  }

  @Override
  public @Nullable TileEntitySpecialRenderer<?> getTileRenderer() {
    return new TESRFirePit();
  }

  @Override
  public @Nullable TileFirePit createNewTileEntity(World worldIn, int meta) {
    return new TileFirePit();
  }

}
