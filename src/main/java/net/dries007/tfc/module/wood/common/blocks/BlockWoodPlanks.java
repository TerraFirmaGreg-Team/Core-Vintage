package net.dries007.tfc.module.wood.common.blocks;

import net.dries007.tfc.module.wood.api.type.WoodType;
import net.dries007.tfc.module.wood.api.variant.block.WoodBlockVariant;
import net.minecraft.init.Blocks;

public class BlockWoodPlanks extends BlockWood {

    public BlockWoodPlanks(WoodBlockVariant variant, WoodType type) {
        super(variant, type);

        setHardness(2.0F);
        setResistance(5.0F);
        setHarvestLevel("axe", 0);

        Blocks.FIRE.setFireInfo(this, 5, 20);
        //OreDictionaryHelper.register(this, variant.toString(), type.toString());
    }
}
