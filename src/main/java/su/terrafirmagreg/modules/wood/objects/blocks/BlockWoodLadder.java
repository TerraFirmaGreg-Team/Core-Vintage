package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.BlockLadder;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;


import lombok.Getter;

@Getter
public class BlockWoodLadder extends BlockLadder implements IWoodBlock {

    protected final Settings settings;
    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockWoodLadder(WoodBlockVariant variant, WoodType type) {
        this.variant = variant;
        this.type = type;

        this.settings = Settings.of(Material.CIRCUITS)
                .soundType(SoundType.LADDER)
                .addOreDict(variant)
                .addOreDict(variant, "wood");

        BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
    }
}
