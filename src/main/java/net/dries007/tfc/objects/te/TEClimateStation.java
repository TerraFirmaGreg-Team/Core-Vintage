package net.dries007.tfc.objects.te;

import su.terrafirmagreg.api.base.tile.BaseTile;

import net.minecraft.nbt.NBTTagCompound;


import mcp.MethodsReturnNonnullByDefault;

@MethodsReturnNonnullByDefault
public class TEClimateStation extends BaseTile {

  public int forward;
  public int arcs;
  public int height;
  public boolean isSeeded;

  public TEClimateStation() {
    forward = 0;
    arcs = 0;
    height = 0;
    isSeeded = false;
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    forward = nbt.getInteger("forward");
    arcs = nbt.getInteger("arcs");
    height = nbt.getInteger("height");
    isSeeded = nbt.getBoolean("seeded");
    super.readFromNBT(nbt);
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    nbt.setInteger("forward", forward);
    nbt.setInteger("arcs", arcs);
    nbt.setInteger("height", height);
    nbt.setBoolean("seeded", isSeeded);
    return super.writeToNBT(nbt);
  }

  public void setPositions(int forward, int arcs, int height) {
    this.forward = forward;
    this.arcs = arcs;
    this.height = height;
    isSeeded = true;
  }
}
