package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;


import gregtech.api.items.toolitem.ToolClasses;

import lombok.Getter;

@Getter
public class BlockWoodFence extends BlockFence implements IWoodBlock {

    protected final Settings settings;
    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockWoodFence(WoodBlockVariant variant, WoodType type) {
        super(Material.WOOD, Material.WOOD.getMaterialMapColor());

        this.variant = variant;
        this.type = type;

        this.settings = Settings.of(Material.WOOD)
                .soundType(SoundType.WOOD)
                .hardness(2.0F)
                .resistance(15.0F)
                .addOreDict("fence", "wood")
                .addOreDict("fence", "wood", type);

        setHarvestLevel(ToolClasses.AXE, 0);

        BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
    }
}
