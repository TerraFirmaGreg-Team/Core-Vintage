package su.terrafirmagreg.modules.soil.objects.blocks;

import su.terrafirmagreg.api.spi.itemblock.BaseItemBlock;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

@Getter
public class BlockSoilMudStairs extends BlockStairs implements ISoilBlock {

    private final SoilBlockVariant variant;
    private final SoilType type;

    public BlockSoilMudStairs(SoilBlockVariant modelBlock, SoilBlockVariant variant, SoilType type) {
        super(modelBlock.get(type).getDefaultState());

        this.variant = variant;
        this.type = type;
        this.useNeighborBrightness = true;

        setSoundType(SoundType.GROUND);
        setHarvestLevel("pickaxe", 0);
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, "stairs");
        OreDictUtils.register(this, "stairs", "mud", "bricks");
    }

    @Override
    public @Nullable BaseItemBlock getItemBlock() {
        return new BaseItemBlock(this);
    }

    @NotNull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

}
