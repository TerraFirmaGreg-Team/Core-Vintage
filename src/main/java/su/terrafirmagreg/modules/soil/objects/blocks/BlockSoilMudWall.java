package su.terrafirmagreg.modules.soil.objects.blocks;

import su.terrafirmagreg.api.model.ICustomState;
import su.terrafirmagreg.api.spi.block.BaseBlockWall;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

import net.minecraft.block.SoundType;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import lombok.Getter;

@Getter
public class BlockSoilMudWall extends BaseBlockWall implements ISoilBlock, ICustomState {

    private final SoilBlockVariant blockVariant;
    private final SoilType type;

    public BlockSoilMudWall(SoilBlockVariant modelBlock, SoilBlockVariant blockVariant, SoilType type) {
        super(modelBlock.get(type));

        this.blockVariant = blockVariant;
        this.type = type;

        setSoundType(SoundType.STONE);
        setHarvestLevel("pickaxe", 0);
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, "wall");
        OreDictUtils.register(this, "wall", "mud", "bricks");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

}
