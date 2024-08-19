package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.base.block.BaseBlockStairs;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;


import gregtech.api.items.toolitem.ToolClasses;

import lombok.Getter;

@Getter
public class BlockWoodStairs extends BaseBlockStairs implements IWoodBlock {

    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockWoodStairs(WoodBlockVariant modelBlock, WoodBlockVariant variant, WoodType type) {
        super(modelBlock.get(type));

        this.variant = variant;
        this.type = type;

        getSettings()
                .addOreDict("stairs")
                .addOreDict("stairs", "wood");

        setHarvestLevel(ToolClasses.AXE, 0);

        BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
    }
}
