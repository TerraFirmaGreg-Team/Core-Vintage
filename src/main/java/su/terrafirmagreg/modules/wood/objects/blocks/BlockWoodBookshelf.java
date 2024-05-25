package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.BlockBookshelf;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import gregtech.api.items.toolitem.ToolClasses;

import lombok.Getter;

@Getter
public class BlockWoodBookshelf extends BlockBookshelf implements IWoodBlock {

    protected final Settings settings;
    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockWoodBookshelf(WoodBlockVariant variant, WoodType type) {
        this.variant = variant;
        this.type = type;
        this.settings = Settings.of(Material.WOOD)
                .hardness(2.0F)
                .resistance(5.0F)
                .soundType(SoundType.WOOD)
                .renderLayer(BlockRenderLayer.CUTOUT_MIPPED)
                .addOreDict(variant)
                .addOreDict(variant, type);

        setHarvestLevel(ToolClasses.AXE, 0);

        BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
    }

    @Override
    public float getEnchantPowerBonus(World world, BlockPos pos) {
        return 1.0F;
    }

}
