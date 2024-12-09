package su.terrafirmagreg.modules.device.object.tile;

import su.terrafirmagreg.api.base.tile.BaseTileTickableInventory;
import su.terrafirmagreg.api.registry.provider.IProviderContainer;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.core.feature.calendar.Calendar;
import su.terrafirmagreg.modules.core.feature.calendar.ICalendarFormatted;
import su.terrafirmagreg.modules.device.client.gui.GuiDryingMat;
import su.terrafirmagreg.modules.device.object.container.ContainerDryingMat;
import su.terrafirmagreg.modules.device.object.recipe.dryingmat.DryingMatRecipeManager;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.jetbrains.annotations.NotNull;

public class TileDryingMat extends BaseTileTickableInventory implements IProviderContainer<ContainerDryingMat, GuiDryingMat> {

  public static final int SLOT = 0;
  //tickcounter
  private long lastUpdateTick;
  private long sealedCalendarTick;

  public TileDryingMat() {
    super(1);
  }

  public ItemStack insertOrSwapItem(int slot, ItemStack playerStack) {
    ItemStack quernStack = inventory.getStackInSlot(slot);

    if (quernStack.isEmpty() || (playerStack.isStackable() && quernStack.isStackable()
                                 && quernStack.getItem() == playerStack.getItem()
                                 && (!playerStack.getHasSubtypes() || playerStack.getMetadata() == quernStack.getMetadata())
                                 && ItemStack.areItemStackTagsEqual(playerStack, quernStack))) {
      return inventory.insertItem(slot, playerStack, false);
    }
    inventory.setStackInSlot(slot, playerStack);
    return quernStack;
  }

  @Override
  public boolean isItemValid(int slot, ItemStack stack) {
    if (slot == SLOT) {
      return DryingMatRecipeManager.findMatchingRecipe(stack) != null;
    }
    return false;
  }

  @Override
  public void setAndUpdateSlots(int slot) {
    this.resetCounter();
    super.setAndUpdateSlots(slot);
  }

  public void resetCounter() {
    lastUpdateTick = Calendar.PLAYER_TIME.getTicks();
    sealedCalendarTick = Calendar.CALENDAR_TIME.getTicks();
    markDirty();
  }

  @Override
  public void readFromNBT(NBTTagCompound nbt) {
    lastUpdateTick = nbt.getLong("tick");
    sealedCalendarTick = nbt.getLong("date");
    super.readFromNBT(nbt);
  }

  @Override
  @NotNull
  public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
    nbt.setLong("tick", lastUpdateTick);
    nbt.setLong("date", sealedCalendarTick);
    return super.writeToNBT(nbt);
  }

  public void reduceCounter(long amount) {
    lastUpdateTick += amount;
    markDirty();
  }

  @NotNull
  public String getSealedDate() {
    return getStack().isEmpty() ? "" : ICalendarFormatted.getTimeAndDate(sealedCalendarTick, Calendar.CALENDAR_TIME.getDaysInMonth());
  }

  public ItemStack getStack() {
    return inventory.getStackInSlot(SLOT);
  }

  public void setStack(ItemStack stack) {
    inventory.setStackInSlot(SLOT, stack);
    markDirty();
  }


  public String getOutputName() {
    var recipe = DryingMatRecipeManager.findMatchingRecipe(getStack());
    return recipe == null ? "" : recipe.getOutputItem().getDisplayName();
  }


  public float getProgress() {
    var recipe = DryingMatRecipeManager.findMatchingRecipe(getStack());
    return recipe == null ? 0 : ((float) getTicksSinceUpdate()) / ((float) recipe.getDuration());
  }

  public long getTicksSinceUpdate() {
    return Calendar.PLAYER_TIME.getTicks() - lastUpdateTick;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public double getMaxRenderDistanceSquared() {
    return 1024.0D;
  }

  @Override
  @NotNull
  @SideOnly(Side.CLIENT)
  public AxisAlignedBB getRenderBoundingBox() {
    return new AxisAlignedBB(getPos(), getPos().add(1D, 1D, 1D));
  }

  @Override
  public void update() {
    checkDry();
  }

  public void checkDry() {
    if (!inventory.getStackInSlot(SLOT).isEmpty()) {
      ItemStack stack = inventory.getStackInSlot(SLOT);
      var recipe = DryingMatRecipeManager.findMatchingRecipe(stack);
      if (recipe != null && !world.isRemote && getTicksSinceUpdate() >= recipe.getDuration()) {
        setStack(new ItemStack(recipe.getOutputItem(stack).getItem(), stack.getCount()));
        resetCounter();
        BlockUtils.notifyBlockUpdate(world, pos);
      }
    }
  }

  @Override
  public ContainerDryingMat getContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
    return new ContainerDryingMat(inventoryPlayer, this);
  }

  @Override
  public GuiDryingMat getGuiContainer(InventoryPlayer inventoryPlayer, World world, IBlockState state, BlockPos pos) {
    return new GuiDryingMat(getContainer(inventoryPlayer, world, state, pos), inventoryPlayer, this);
  }
}
