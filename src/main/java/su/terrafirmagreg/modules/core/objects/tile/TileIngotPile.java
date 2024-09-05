package su.terrafirmagreg.modules.core.objects.tile;

import su.terrafirmagreg.api.base.tile.BaseTile;

import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.objects.items.metal.ItemIngot;

import lombok.Getter;

@Getter
public class TileIngotPile
    extends BaseTile {

  private Metal metal;
  private int count;

  public TileIngotPile() {
    metal = Metal.UNKNOWN;
    count = 1;
  }

  @Override
  public void readFromNBT(NBTTagCompound tag) {
    metal = TFCRegistries.METALS.getValue(new ResourceLocation(tag.getString("metal")));
    count = tag.getInteger("count");
    super.readFromNBT(tag);
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tag) {
    //noinspection ConstantConditions
    tag.setString("metal",
        (metal == null) ? Metal.UNKNOWN.getRegistryName().toString() : metal.getRegistryName()
            .toString());
    tag.setInteger("count", count);
    return super.writeToNBT(tag);
  }

  @Override
  @SideOnly(Side.CLIENT)
  public double getMaxRenderDistanceSquared() {
    return 1024.0D;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public AxisAlignedBB getRenderBoundingBox() {
    return INFINITE_EXTENT_AABB;
  }

  public void onBreakBlock() {
    InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(),
        new ItemStack(ItemIngot.get(metal, Metal.ItemType.INGOT), count));
  }

  public void setMetal(Metal metal) {
    this.metal = metal;
    markForBlockUpdate();
  }

  public void setCount(int count) {
    this.count = count;
    markForBlockUpdate();
  }
}
