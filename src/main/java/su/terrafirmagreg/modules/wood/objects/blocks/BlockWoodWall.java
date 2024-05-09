package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.model.CustomStateMap;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;

import lombok.Getter;

@Getter
public class BlockWoodWall extends BlockWall implements IWoodBlock {

    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockWoodWall(WoodBlockVariant modelBlock, WoodBlockVariant variant, WoodType type) {
        super(modelBlock.get(type));

        this.variant = variant;
        this.type = type;

        setSoundType(SoundType.WOOD);
        setHarvestLevel("axe", 0);

        BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, getVariant(), "wood");
        OreDictUtils.register(this, getVariant(), "wood", getType());
    }

    @NotNull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onStateRegister() {
        ModelUtils.registerStateMapper(this, new CustomStateMap.Builder()
                .customResource(getResourceLocation())
                .ignore(BlockWall.VARIANT)
                .build());
    }
}
