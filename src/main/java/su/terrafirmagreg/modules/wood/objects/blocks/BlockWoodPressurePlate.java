package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.spi.itemblock.BaseItemBlock;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;


import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
public class BlockWoodPressurePlate extends BlockPressurePlate implements IWoodBlock {

    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockWoodPressurePlate(WoodBlockVariant variant, WoodType type) {
        super(Material.WOOD, Sensitivity.EVERYTHING);

        this.variant = variant;
        this.type = type;

        setHardness(0.5F);
        setSoundType(SoundType.WOOD);

        BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
    }

    public void onRegisterOreDict() {
        OreDictUtils.register(this, variant);
        OreDictUtils.register(this, variant, "wood");
        OreDictUtils.register(this, variant, type);
    }

    @Override
    public @Nullable BaseItemBlock getItemBlock() {
        return new BaseItemBlock(this);
    }
}
