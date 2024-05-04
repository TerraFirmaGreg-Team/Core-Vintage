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

    private final WoodBlockVariant blockVariant;
    private final WoodType type;

    public BlockWoodStairs(WoodBlockVariant modelBlock, WoodBlockVariant blockVariant, WoodType type) {
        super(modelBlock.get(type).getDefaultState());

        this.blockVariant = blockVariant;
        this.type = type;
        this.useNeighborBrightness = true;

        setHarvestLevel("axe", 0);

        BlockUtils.setFireInfo(this, blockVariant.getEncouragement(), blockVariant.getFlammability());
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, blockVariant);
        OreDictUtils.register(this, blockVariant, "wood");
        OreDictUtils.register(this, blockVariant, "wood", type);
    }

    @Override
    public @Nullable BaseItemBlock getItemBlock() {
        return new BaseItemBlock(this);
    }
}
