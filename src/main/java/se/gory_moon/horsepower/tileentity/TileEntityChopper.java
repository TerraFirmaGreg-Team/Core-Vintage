package se.gory_moon.horsepower.tileentity;

import com.google.common.collect.Lists;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.property.IExtendedBlockState;
import se.gory_moon.horsepower.Configs;
import se.gory_moon.horsepower.blocks.BlockChopper;
import se.gory_moon.horsepower.recipes.HPRecipeBase;
import se.gory_moon.horsepower.recipes.HPRecipes;
import se.gory_moon.horsepower.util.Localization;

import javax.annotation.Nullable;

public class TileEntityChopper extends TileEntityHPHorseBase {
	private final float oldVisualWindup = -1;
	private int currentWindup;
	private int currentItemChopTime;
	private int totalItemChopTime;
	private float visualWindup = 0;

	public TileEntityChopper() {
		super(2);
	}

	public NBTTagCompound getTextureBlock() {
		return getTileData().getCompoundTag("textureBlock");
	}

	public void setTextureBlock(NBTTagCompound textureBlock) {
		getTileData().setTag("textureBlock", textureBlock);
	}

	@Override
	public boolean validateArea() {
		if (searchPos == null) {
			searchPos = Lists.newArrayList();

			for (int x = -3; x <= 3; x++) {
				for (int z = -3; z <= 3; z++) {
					if ((x <= 1 && x >= -1) && (z <= 1 && z >= -1))
						continue;
					searchPos.add(getPos().add(x, 0, z));
					searchPos.add(getPos().add(x, 1, z));
				}
			}
		}

		for (BlockPos pos : searchPos) {
			if (!getWorld().getBlockState(pos).getBlock().isReplaceable(world, pos))
				return false;
		}
		return true;
	}

	@Override
	public boolean targetReached() {
		currentWindup++;

		if (currentWindup >= Configs.general.pointsForWindup) {
			currentWindup = 0;
			currentItemChopTime++;

			if (currentItemChopTime >= totalItemChopTime) {
				currentItemChopTime = 0;

				totalItemChopTime = HPRecipes.instance().getChoppingTime(getStackInSlot(0), false);
				chopItem();
				return true;
			}
		}
		markDirty();
		return false;
	}

	@Override
	public int getPositionOffset() {
		return 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		currentWindup = compound.getInteger("currentWindup");

		if (getStackInSlot(0).getCount() > 0) {
			currentItemChopTime = compound.getInteger("chopTime");
			totalItemChopTime = compound.getInteger("totalChopTime");
		} else {
			currentItemChopTime = 0;
			totalItemChopTime = 1;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("currentWindup", currentWindup);
		compound.setInteger("chopTime", currentItemChopTime);
		compound.setInteger("totalChopTime", totalItemChopTime);

		return super.writeToNBT(compound);
	}

	@Override
	public void update() {
		super.update();

		float windup = Configs.general.pointsForWindup > 0 ? Configs.general.pointsForWindup : 1;
		visualWindup = -0.74F + (0.74F * (((float) currentWindup) / (windup - 1)));
	}

	@Override
	public HPRecipeBase getRecipe() {
		return HPRecipes.instance().getChoppingBlockRecipe(getStackInSlot(0), false);
	}

	@Override
	public ItemStack getRecipeItemStack() {
		return HPRecipes.instance().getChopperResult(getStackInSlot(0), false);
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return index != 1 && index == 0 && HPRecipes.instance()
		                                            .hasChopperRecipe(stack, false) && getStackInSlot(1).isEmpty() && getStackInSlot(0).isEmpty();
	}

	@Override
	public int getField(int id) {
		switch (id) {
			case 0:
				return totalItemChopTime;
			case 1:
				return currentItemChopTime;
			case 2:
				return currentWindup;
			default:
				return 0;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
			case 0:
				totalItemChopTime = value;
				break;
			case 1:
				currentItemChopTime = value;
			case 2:
				currentWindup = value;
		}
	}

	@Override
	public int getFieldCount() {
		return 3;
	}

	@Override
	public String getName() {
		return "container.chopper";
	}

	@Override
	public int getOutputSlot() {
		return 1;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		ItemStack itemstack = getStackInSlot(index);
		super.setInventorySlotContents(index, stack);

		if (index == 1 && getStackInSlot(1).isEmpty()) {
			markDirty();
		}

		boolean flag = !stack.isEmpty() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack);
		if (index == 0 && !flag) {
			totalItemChopTime = HPRecipes.instance().getChoppingTime(stack, false);
			currentItemChopTime = 0;
			currentWindup = 0;
			markDirty();
		}
	}

	public IExtendedBlockState getExtendedState(IExtendedBlockState state) {
		state = (IExtendedBlockState) state.withProperty(BlockChopper.FACING, getForward());
		state = (IExtendedBlockState) state.withProperty(BlockChopper.PART, state.getValue(BlockChopper.PART));

		return state;
	}

	@Override
	public boolean canBeRotated() {
		return true;
	}

	public float getVisualWindup() {
		return visualWindup;
	}

	public float getOldVisualWindup() {
		return oldVisualWindup;
	}

	@Nullable
	@Override
	public ITextComponent getDisplayName() {
		if (valid)
			return super.getDisplayName();
		else
			return new TextComponentTranslation(Localization.INFO.CHOPPING_INVALID.key()).setStyle(new Style().setColor(TextFormatting.RED));
	}

	private void chopItem() {
		if (canWork()) {
			ItemStack input = getStackInSlot(0);
			ItemStack result = getRecipeItemStack();
			ItemStack output = getStackInSlot(1);

			if (output.isEmpty()) {
				setInventorySlotContents(1, result.copy());
			} else if (output.getItem() == result.getItem()) {
				output.grow(result.getCount());
			}

			input.shrink(1);
			markDirty();
		}
	}
}
