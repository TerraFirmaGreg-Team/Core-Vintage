package su.terrafirmagreg.modules.device.objects.tiles;

import su.terrafirmagreg.api.base.tile.BaseTileTickableInventory;
import su.terrafirmagreg.api.registry.provider.IProviderContainer;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.data.MathConstants;
import su.terrafirmagreg.modules.device.client.gui.GuiPowderkeg;
import su.terrafirmagreg.modules.device.objects.blocks.BlockPowderKeg;
import su.terrafirmagreg.modules.device.objects.containers.ContainerPowderKeg;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;


import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.dries007.tfc.api.capability.inventory.IItemHandlerSidedCallback;
import net.dries007.tfc.api.capability.inventory.ItemHandlerSidedWrapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

import static su.terrafirmagreg.data.Properties.SEALED;

/**
 * @see BlockPowderKeg
 */

public class TilePowderKeg extends BaseTileTickableInventory
        implements IItemHandlerSidedCallback, IProviderContainer<ContainerPowderKeg, GuiPowderkeg> {

  @Getter
  private boolean sealed;
  @Getter
  private int fuse = -1;

  private boolean isLit = false;
  private EntityLivingBase igniter;

  public TilePowderKeg() {
    super(new ItemStackHandler(12));
  }

  /**
   * Called when this TileEntity was created by placing a sealed keg Item. Loads its data from the Item's NBTTagCompound without loading xyz coordinates.
   *
   * @param nbt The NBTTagCompound to load from.
   */
  public void readFromItemTag(NBTTagCompound nbt) {
    inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
    sealed = nbt.getBoolean("sealed");
    markForSync();
  }

  /**
   * Called once per side when the TileEntity has finished loading. On servers, this is the earliest point in time to safely access the TE's World object.
   */
  @Override
  public void onLoad() {
    if (!world.isRemote) {
      sealed = world.getBlockState(pos).getValue(SEALED);
    }
  }

  @Override
  public boolean canInsert(int slot, ItemStack stack, EnumFacing side) {
    return !world.getBlockState(pos).getValue(SEALED) && isItemValid(slot, stack);
  }

  @Override
  public boolean canExtract(int slot, EnumFacing side) {
    return !sealed;
  }

  @Override
  public boolean isItemValid(int slot, @NotNull ItemStack stack) {
    return OreDictUtils.contains(stack, "gunpowder");
  }

  public void setSealed(boolean sealed) {
    this.sealed = sealed;
    markForSync();
  }

  @Override
  public void readFromNBT(@NotNull NBTTagCompound nbt) {
    super.readFromNBT(nbt);
    sealed = nbt.getBoolean("sealed");
  }

  @Override
  @NotNull
  public NBTTagCompound writeToNBT(@NotNull NBTTagCompound nbt) {
    nbt.setBoolean("sealed", sealed);
    return super.writeToNBT(nbt);
  }

  @Override
  public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing facing) {
    return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing facing) {
    if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
      return (T) new ItemHandlerSidedWrapper(this, inventory, facing);
    }
    return super.getCapability(capability, facing);
  }

  @Override
  public void onBreakBlock(World world, BlockPos pos, IBlockState state) {
    if (!state.getValue(SEALED)) {
      // Not sealed, so empty contents normally
      super.onBreakBlock(world, pos, state);
    } else {
      // Need to create the full keg and drop it now
      ItemStack stack = getItemStack(state);
      InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
    }
  }

  public ItemStack getItemStack(IBlockState state) {
    ItemStack stack = new ItemStack(state.getBlock());
    stack.setTagCompound(getItemTag());
    return stack;
  }

  /**
   * Called to get the NBTTagCompound that is put on keg Items. This happens when a sealed keg was broken.
   *
   * @return An NBTTagCompound containing inventory and tank data.
   */
  private NBTTagCompound getItemTag() {
    NBTTagCompound nbt = new NBTTagCompound();
    nbt.setTag("inventory", inventory.serializeNBT());
    nbt.setBoolean("sealed", sealed);
    return nbt;
  }

  public void setIgniter(@Nullable EntityLivingBase igniterIn) {
    igniter = igniterIn;
  }

  public boolean isLit() {
    return isLit;
  }

  public void setLit(boolean lit) {
    isLit = lit;
    if (lit) {
      world.playSound(null, pos.getX(), pos.getY() + 0.5D, pos.getZ(),
              SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.33F);
      fuse = 80;
    } else {
      world.playSound(null, pos.getX(), pos.getY() + 0.5D, pos.getZ(),
              SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.8f,
              0.6f + MathConstants.RNG.nextFloat() * 0.4f);
      fuse = -1;
    }
    markForSync();
  }

  @Override
  public void update() {
    if (isLit) {
      --fuse;

      if (fuse <= 0) {
        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
        if (!this.world.isRemote) {
          explode();
        }
      }
    }
    super.update();
  }

  private void explode() {
    // world.createExplosion(igniter, pos.getX(), pos.getY(), pos.getZ(), getStrength(), true);
    PowderKegExplosion explosion = new PowderKegExplosion(world, igniter, pos.getX(), pos.getY(),
            pos.getZ(), getStrength());
    if (ForgeEventFactory.onExplosionStart(world, explosion)) {
      return;
    }
    explosion.doExplosionA();
    explosion.doExplosionB(true);
  }

  public int getStrength() {
    int count = 0;
    for (int i = 0; i < inventory.getSlots(); i++) {
      count += inventory.getStackInSlot(i).getCount();
    }
    return count / 12;
  }

  public static class PowderKegExplosion extends Explosion {

    public PowderKegExplosion(World world, Entity entity, double x, double y, double z,
            float size) {
      super(world, entity, x, y, z, size, false, true);
    }

    /**
     * Does the second part of the explosion (sound, particles, drop spawn)
     * <p>
     * (Forgive the Mojang copypasta)
     */
    @Override
    public void doExplosionB(boolean spawnParticles) {
      world.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 4.0f,
              (1.0f + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.2f) * 0.7f);

      if (size >= 2.0F) {
        world.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, x, y, z, 1.0d, 0.d, 0.0d);
      }

      boolean isSmall = affectedBlockPositions.size() < 128;
      List<ItemStack> allDrops = new ArrayList<>();
      for (BlockPos blockpos : affectedBlockPositions) {
        IBlockState iblockstate = world.getBlockState(blockpos);
        Block block = iblockstate.getBlock();

        if (spawnParticles) {
          double d0 = ((float) blockpos.getX() + world.rand.nextFloat());
          double d1 = ((float) blockpos.getY() + world.rand.nextFloat());
          double d2 = ((float) blockpos.getZ() + world.rand.nextFloat());
          double d3 = d0 - x;
          double d4 = d1 - y;
          double d5 = d2 - z;
          double d6 = MathHelper.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
          d3 = d3 / d6;
          d4 = d4 / d6;
          d5 = d5 / d6;
          double d7 = 0.5d / (d6 / (double) size + 0.1d);
          d7 = d7 * (double) (world.rand.nextFloat() * world.rand.nextFloat() + 0.3f);
          d3 = d3 * d7;
          d4 = d4 * d7;
          d5 = d5 * d7;
          world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, (d0 + x) / 2.0d, (d1 + y) / 2.0d,
                  (d2 + z) / 2.0d, d3, d4, d5);
          world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, d3, d4, d5);
        }

        if (iblockstate.getMaterial() != Material.AIR) {
          if (isSmall) {
            block.dropBlockAsItemWithChance(world, blockpos, iblockstate, 1f, 0);
          } else {
            // noinspection deprecation
            List<ItemStack> drops = block.getDrops(world, blockpos, iblockstate, 0);
            float chance = ForgeEventFactory.fireBlockHarvesting(drops, world, blockpos,
                    iblockstate, 0, 1f, false, null);
            if (world.rand.nextFloat() <= chance) {
              for (ItemStack stack : drops) {
                //noinspection all
                allDrops.add(stack); //addAll is unsupported
              }
            }
          }
          block.onBlockExploded(world, blockpos, this);
        }
      }
      if (!isSmall) {
        List<ItemStack> squish = squish(allDrops);
        for (ItemStack drop : squish) {
          int index = world.rand.nextInt(affectedBlockPositions.size());
          BlockPos dropPos = affectedBlockPositions.get(index);
          Block.spawnAsEntity(world, dropPos, drop);
        }
      }

    }

    private List<ItemStack> squish(List<ItemStack> list) {
      List<ItemStack> drops = NonNullList.create();
      Object2IntMap<Item> map = new Object2IntOpenHashMap<>();
      for (ItemStack stack : list) {
        Item item = stack.getItem();
        int count = stack.getCount();
        int current = map.getOrDefault(item, 0);
        map.put(item, count + current);
      }
      for (Object2IntMap.Entry<Item> entry : map.object2IntEntrySet()) {
        int count = entry.getIntValue();
        Item item = entry.getKey();
        int stackLimit = item.getItemStackLimit(new ItemStack(item));
        while (count > 0) {
          int amountToDrop = Math.min(count, stackLimit);
          drops.add(new ItemStack(item, amountToDrop));
          count -= amountToDrop;
        }
      }
      return drops;
    }
  }

  @Override
  public ContainerPowderKeg getContainer(InventoryPlayer inventoryPlayer, World world,
          IBlockState state, BlockPos pos) {
    return new ContainerPowderKeg(inventoryPlayer, this);
  }

  @Override
  public GuiPowderkeg getGuiContainer(InventoryPlayer inventoryPlayer, World world,
          IBlockState state, BlockPos pos) {
    return new GuiPowderkeg(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer, this,
            state);
  }


}
