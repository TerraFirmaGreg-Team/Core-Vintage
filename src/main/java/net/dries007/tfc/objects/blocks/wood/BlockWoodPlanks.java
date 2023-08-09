package net.dries007.tfc.objects.blocks.wood;

import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.WoodBlockVariant;
import net.minecraft.init.Blocks;

public class BlockWoodPlanks extends BlockWood {

    public BlockWoodPlanks(WoodBlockVariant woodBlockVariant, WoodType woodType) {
        super(woodBlockVariant, woodType);

        setHardness(2.0F);
        setResistance(5.0F);
        setHarvestLevel("axe", 0);
        Blocks.FIRE.setFireInfo(this, 5, 20);
    }
}
