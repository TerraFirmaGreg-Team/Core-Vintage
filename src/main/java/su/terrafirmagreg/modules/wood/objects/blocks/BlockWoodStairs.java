package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.spi.itemblock.BaseItemBlock;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.BlockStairs;


import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
public class BlockWoodStairs extends BlockStairs implements IWoodBlock {

    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockWoodStairs(WoodBlockVariant modelBlock, WoodBlockVariant variant, WoodType type) {
        super(modelBlock.get(type).getDefaultState());

        this.variant = variant;
        this.type = type;
        this.useNeighborBrightness = true;

        setHarvestLevel("axe", 0);

        BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, variant);
        OreDictUtils.register(this, variant, "wood");
        OreDictUtils.register(this, variant, "wood", type);
    }

    @Override
    public @Nullable BaseItemBlock getItemBlock() {
        return new BaseItemBlock(this);
    }
}
