package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlock;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;


import lombok.Getter;

@Getter
public abstract class BlockWood extends BaseBlock implements IWoodBlock {

    private final WoodBlockVariant variant;
    private final WoodType type;

    protected BlockWood(WoodBlockVariant variant, WoodType type) {
        super(Settings.of()
                .material(Material.WOOD)
                .soundType(SoundType.WOOD));

        this.variant = variant;
        this.type = type;

        BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
    }

}
