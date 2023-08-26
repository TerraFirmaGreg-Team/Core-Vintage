package net.dries007.tfc.common.objects.blocks.wood;

import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.block.WoodBlockVariant;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.init.Blocks;

public class BlockWoodPlanks extends BlockWood {

    public BlockWoodPlanks(WoodBlockVariant variant, WoodType type) {
        super(variant, type);

        setHardness(2.0F);
        setResistance(5.0F);
        setHarvestLevel("axe", 0);

        Blocks.FIRE.setFireInfo(this, 5, 20);
        OreDictionaryHelper.register(this, variant.toString(), type.toString());
    }
}
