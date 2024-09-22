package se.gory_moon.horsepower.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;


import se.gory_moon.horsepower.Configs;
import se.gory_moon.horsepower.recipes.HPRecipeBase;
import se.gory_moon.horsepower.recipes.HPRecipes;

public class TileHandGrindstone extends TileHPBase implements ITickable {

  private final int ticksPerRotation = 18;
  private int currentItemMillTime;
  private int totalItemMillTime;
  private float visibleRotation = 0;
  private int currentTicks = 0;
  private int rotation = 0;

  public TileHandGrindstone() {
    super(3);
  }

  public boolean turn() {
    if (getWorld().isRemote) {
      return false;
    }

    if (rotation < 3 && canWork()) {
      rotation += ticksPerRotation;
      markDirty();
      return true;
    }
    return false;
  }

  @Override
  public void update() {
    if (rotation > 0) {

      visibleRotation = (visibleRotation - 360 / (ticksPerRotation)) % -360;
      currentTicks++;
      if (currentTicks >= ticksPerRotation) {
        currentTicks -= ticksPerRotation;

        currentItemMillTime += Configs.general.pointsPerRotation;

        if (currentItemMillTime >= totalItemMillTime) {
          currentItemMillTime = 0;

          millItem();
          totalItemMillTime = HPRecipes.instance().getGrindstoneTime(getStackInSlot(0), true);
        }
        markDirty();
      }

      rotation--;
    } else {
      visibleRotation = 0;
    }
  }

  private void millItem() {
    if (!world.isRemote && canWork()) {
      HPRecipeBase recipe = getRecipe();
      ItemStack result = recipe.getOutput();
      ItemStack secondary = recipe.getSecondary();

      ItemStack input = getStackInSlot(0);
      ItemStack output = getStackInSlot(1);
      ItemStack secondaryOutput = getStackInSlot(2);

      if (output.isEmpty()) {
        setInventorySlotContents(1, result.copy());
      } else if (output.isItemEqual(result)) {
        output.grow(result.getCount());
      }
      processSecondaries(getWorld(), secondary, secondaryOutput, recipe, this);

      input.shrink(1);
    }
  }

  @Override
  public void setInventorySlotContents(int index, ItemStack stack) {
    ItemStack itemstack = getStackInSlot(index);
    super.setInventorySlotContents(index, stack);

    boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
    if (index == 0 && !flag) {
      totalItemMillTime = HPRecipes.instance().getGrindstoneTime(stack, true);
      currentItemMillTime = 0;
    }
    markDirty();
  }

  public static void processSecondaries(World world, ItemStack secondary, ItemStack secondaryOutput, HPRecipeBase recipe, TileHPBase teBase) {
    if (!secondary.isEmpty()) {
      int recipeChance = recipe.getSecondaryChance();
      if (recipeChance >= 100 || world.rand.nextInt(100) < recipeChance) {
        if (secondaryOutput.isEmpty()) {
          teBase.setInventorySlotContents(2, secondary.copy());
        } else if (secondaryOutput.isItemEqual(secondary)) {
          secondaryOutput.grow(secondary.getCount());
        }
      }
    }
  }

  @Override
  public int getInventoryStackLimit() {
    return 64;
  }

  @Override
  public boolean isItemValidForSlot(int index, ItemStack stack) {
    return index == 0 && HPRecipes.instance().hasGrindstoneRecipe(stack, true);
  }

  @Override
  public int getField(int id) {
    switch (id) {
      case 0:
        return totalItemMillTime;
      case 1:
        return currentItemMillTime;
      default:
        return 0;
    }
  }

  @Override
  public void setField(int id, int value) {
    switch (id) {
      case 0:
        totalItemMillTime = value;
        break;
      case 1:
        currentItemMillTime = value;
    }
  }

  @Override
  public int getFieldCount() {
    return 2;
  }

  @Override
  public String getName() {
    return "container.hand_mill";
  }

  @Override
  public int getOutputSlot() {
    return 2;
  }

  @Override
  public ItemStack getRecipeItemStack() {
    return HPRecipes.instance().getGrindstoneResult(getStackInSlot(0), true);
  }

  @Override
  public HPRecipeBase getRecipe() {
    return HPRecipes.instance().getGrindstoneRecipe(getStackInSlot(0), true);
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {
    super.readFromNBT(compound);

    if (getStackInSlot(0).getCount() > 0) {
      currentItemMillTime = compound.getInteger("millTime");
      totalItemMillTime = compound.getInteger("totalMillTime");
      rotation = compound.getInteger("currentRotation");
    } else {
      currentItemMillTime = 0;
      totalItemMillTime = 1;
      rotation = 0;
    }
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound compound) {
    compound.setInteger("millTime", currentItemMillTime);
    compound.setInteger("totalMillTime", totalItemMillTime);
    compound.setInteger("currentRotation", rotation);

    return super.writeToNBT(compound);
  }

  @Override
  public void markDirty() {
    super.markDirty();
    if (getStackInSlot(0).isEmpty()) {
      currentItemMillTime = 0;
    }
  }

  @Override
  public boolean canBeRotated() {
    return true;
  }

  public float getVisibleRotation() {
    return visibleRotation;
  }
}
