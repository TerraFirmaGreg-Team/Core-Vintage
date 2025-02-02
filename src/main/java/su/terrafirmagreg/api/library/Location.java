package su.terrafirmagreg.api.library;

import su.terrafirmagreg.api.util.SoundUtils;
import su.terrafirmagreg.api.util.StackUtils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class Location {

  public final World world;
  public int x, y, z;

  private BlockPos blockPos;

  public Location(World world, int x, int y, int z) {

    this.world = world;
    this.x = x;
    this.y = y;
    this.z = z;

    blockPos = new BlockPos(x, y, z);
  }

  public Location(World world, BlockPos pos) {

    this(world, pos.getX(), pos.getY(), pos.getZ());
  }

  public Location(TileEntity tileEntity) {

    this(tileEntity.getWorld(), tileEntity.getPos().getX(), tileEntity.getPos().getY(), tileEntity.getPos().getZ());
  }

  public Location(Entity entity) {

    this(entity.world, (int) entity.posX, (int) entity.posY, (int) entity.posZ);
  }

  public Location(Location location, EnumFacing dir, int distance) {

    this.world = location.world;
    this.x = location.x + (dir.getXOffset() * distance);
    this.y = location.y + (dir.getYOffset() * distance);
    this.z = location.z + (dir.getZOffset() * distance);

    blockPos = new BlockPos(x, y, z);
  }

  public Location(Location location, EnumFacing dir) {

    this(location, dir, 1);
  }

  public static Location readFromNBT(World world, NBTTagCompound nbt) {

    int x = nbt.getInteger("locX");
    int y = nbt.getInteger("locY");
    int z = nbt.getInteger("locZ");

    Location loc = new Location(world, x, y, z);

    if (!loc.isZero()) {
      return loc;
    }

    return null;
  }

  public Location translate(EnumFacing dir, int distance) {

    this.x += (dir.getXOffset() * distance);
    this.y += (dir.getYOffset() * distance);
    this.z += (dir.getZOffset() * distance);
    blockPos = new BlockPos(x, y, z);
    return this;
  }

  public Location translate(Location location) {

    this.x += location.x;
    this.y += location.y;
    this.z += location.z;
    blockPos = new BlockPos(x, y, z);
    return this;
  }

  @Override
  public boolean equals(Object obj) {

    if (obj instanceof Location newLoc) {

      return world == newLoc.world && x == newLoc.x && y == newLoc.y && z == newLoc.z;
    }

    return super.equals(obj);
  }

  public double getDistance(Location location) {

    int dx = x - location.x;
    int dy = y - location.y;
    int dz = z - location.z;

    return Math.sqrt((dx * dx) + (dy * dy) + (dz * dz));
  }

  public BlockPos getBlockPos() {

    return blockPos;
  }

  public IBlockState getBlockState() {

    if (getBlockPos() == null) {
      return null;
    }

    return world.getBlockState(getBlockPos());
  }

  public Block getBlock() {

    if (getBlockState() == null) {
      return null;
    }

    return getBlockState().getBlock();
  }

  public void setBlock(Block block) {

    world.setBlockState(getBlockPos(), block.getDefaultState());
  }

  public void setBlock(IBlockState state) {

    world.setBlockState(getBlockPos(), state.getBlock().getDefaultState());
    world.setBlockState(getBlockPos(), state);
  }

  public void breakBlock(EntityPlayer player) {
    SoundUtils.playBlockPlaceSound(world, player, getBlockState(), this);
    if (!world.isRemote && (!player.capabilities.isCreativeMode)) {
      StackUtils.spawnItemStack(world, this, getDrops());
    }
    if (!world.isRemote) {setBlockToAir();}
  }

  public int getBlockMeta() {

    return getBlock().getMetaFromState(getBlockState());
  }

  public Material getBlockMaterial() {
    return getBlock().getMaterial(getBlockState());
  }

  public List<ItemStack> getDrops() {

    return getBlock().getDrops(world, getBlockPos(), getBlockState(), 0);
  }

  public TileEntity getTileEntity() {

    return world.getTileEntity(getBlockPos());
  }

  public IInventory getIInventory() {

    if (getTileEntity() != null && getTileEntity() instanceof IInventory) {

      return (IInventory) getTileEntity();
    }

    return null;
  }

  public int getLightValue() {

    return world.getLight(getBlockPos(), false);
  }

  public void setBlock(Block block, EntityPlayer placer) {

    setBlock(block);
    block.onBlockPlacedBy(world, getBlockPos(), block.getDefaultState(), placer, new ItemStack(block));
  }

  public void setBlock(IBlockState state, EntityPlayer placer) {

    world.setBlockState(getBlockPos(), state, 2);
    state.getBlock().onBlockPlacedBy(world, getBlockPos(), state, placer, new ItemStack(state.getBlock()));
  }

  public void setBlockToAir() {

    world.setBlockToAir(getBlockPos());
  }

  public boolean isAirBlock() {

    return getBlock() == Blocks.AIR;
  }

  public boolean isBlockValidForPlacing(Block block) {

    return block.canPlaceBlockAt(world, getBlockPos()) || isAirBlock();
  }

  private boolean isZero() {

    return x == 0 && y == 0 && z == 0;
  }

  public void writeToNBT(NBTTagCompound nbt) {

    nbt.setInteger("locX", z);
    nbt.setInteger("locY", y);
    nbt.setInteger("locZ", z);
  }

  public boolean isEmpty() {

    return x == 0 && y == 0 && z == 0;
  }

  public String toString() {

    return "(" + x + ", " + y + ", " + z + ")";
  }
}
