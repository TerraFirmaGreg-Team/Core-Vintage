package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlockStairs;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;


import lombok.Getter;

@Getter
public class BlockWoodStairs extends BaseBlockStairs implements IWoodBlock {

    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockWoodStairs(WoodBlockVariant modelBlock, WoodBlockVariant variant, WoodType type) {
        super(modelBlock.get(type));

        this.variant = variant;
        this.type = type;

        setHarvestLevel("axe", 0);

        BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, getVariant());
        OreDictUtils.register(this, getVariant(), "wood");
        OreDictUtils.register(this, getVariant(), "wood", getType());
    }
}
