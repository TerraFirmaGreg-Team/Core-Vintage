package su.terrafirmagreg.modules.soil.objects.blocks;

import su.terrafirmagreg.api.base.block.BaseBlockWall;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

import net.minecraft.block.SoundType;
import net.minecraft.util.BlockRenderLayer;


import gregtech.api.items.toolitem.ToolClasses;

import lombok.Getter;

@Getter
public class BlockSoilMudWall extends BaseBlockWall implements ISoilBlock {

    private final SoilBlockVariant variant;
    private final SoilType type;

    public BlockSoilMudWall(SoilBlockVariant modelBlock, SoilBlockVariant variant, SoilType type) {
        super(modelBlock.get(type));

        this.variant = variant;
        this.type = type;

        getSettings()
                .soundType(SoundType.STONE)
                .renderLayer(BlockRenderLayer.CUTOUT)
                .addOreDict("wall")
                .addOreDict("wall", "mud", "bricks");

        setHarvestLevel(ToolClasses.PICKAXE, 0);
    }

}
