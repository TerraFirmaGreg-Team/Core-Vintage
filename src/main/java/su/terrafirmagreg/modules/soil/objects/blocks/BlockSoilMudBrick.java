package su.terrafirmagreg.modules.soil.objects.blocks;

import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;
import su.terrafirmagreg.modules.soil.api.types.variant.item.SoilItemVariants;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;


import java.util.Random;

public class BlockSoilMudBrick extends BlockSoil {

    public BlockSoilMudBrick(SoilBlockVariant variant, SoilType type) {
        super(variant, type);

    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return SoilItemVariants.MUD_BRICK.get(getType());
    }
}
