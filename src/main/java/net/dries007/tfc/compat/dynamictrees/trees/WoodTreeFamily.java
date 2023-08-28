package net.dries007.tfc.compat.dynamictrees.trees;

import com.ferreusveritas.dynamictrees.blocks.BlockBranch;
import com.ferreusveritas.dynamictrees.trees.TreeFamily;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.common.objects.blocks.wood.BlockWoodBranchBasic;
import net.dries007.tfc.common.objects.blocks.wood.BlockWoodBranchThick;
import net.dries007.tfc.compat.dynamictrees.ModBlocks;

public class WoodTreeFamily extends TreeFamily {
    private final WoodType type;
    public boolean hasConiferVariants = false;
    private boolean thick = false;

    public WoodTreeFamily(WoodType type) {
        super(TerraFirmaCraft.identifier(type.toString()));

        this.type = type;

        switch (getName().getPath()) {
            case "sequoia", "kapok" -> {
                setThick(true);
                //redo this after setting Thick, so get the right branch
                setDynamicBranch(createBranch());
            }
        }
    }

    public WoodType getType() {
        return type;
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
        setCommonSpecies(new WoodTreeSpecies(this, ModBlocks.leafMap.get(type)));
        getCommonSpecies().generateSeed();
    }

    @Override
    public BlockBranch createBranch() {
        var branchName = "branch/" + getName().getPath();
        return isThick() ? new BlockWoodBranchThick(branchName) : new BlockWoodBranchBasic(branchName);
    }

/* //comment out for now
    @Override
    public List<Item> getRegisterableItems(List<Item> itemList) {
        return itemList;
    }*/


}
