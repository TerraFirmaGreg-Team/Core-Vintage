package net.dries007.tfc.compat.dynamictrees.trees;

import com.ferreusveritas.dynamictrees.blocks.BlockBranch;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.common.objects.blocks.wood.BlockWoodBranchBasic;
import net.dries007.tfc.common.objects.blocks.wood.BlockWoodBranchThick;
import net.dries007.tfc.compat.dynamictrees.ModBlocks;
import net.minecraft.util.ResourceLocation;

public class TreeFamilyTFC extends TreeFamily {
	public boolean hasConiferVariants = false;
	private boolean thick = false;

	public TreeFamilyTFC(ResourceLocation name, WoodType type) {
		super(name);

		switch (getName().getPath()) {
			case "sequoia":
			case "kapok":
				setThick(true);
				//redo this after setting Thick, so get the right branch
				setDynamicBranch(createBranch());
		}
	}

	@Override
	public boolean isThick() {
		return thick;
	}

	public void setThick(boolean thick) {
		this.thick = thick;
	}


	@Override
	public void createSpecies() {
		setCommonSpecies(new TreeTFCSpecies(this, ModBlocks.leafMap.get(getName().toString())));
		getCommonSpecies().generateSeed();
	}

	@Override
	public BlockBranch createBranch() {
		String branchName = "branch/" + getName().getPath();
		return isThick() ? new BlockWoodBranchThick(branchName) : new BlockWoodBranchBasic(branchName);
	}

/* //comment out for now
    @Override
    public List<Item> getRegisterableItems(List<Item> itemList) {
        return itemList;
    }*/


}
