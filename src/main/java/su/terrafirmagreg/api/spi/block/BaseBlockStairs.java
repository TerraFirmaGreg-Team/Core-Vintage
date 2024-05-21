package su.terrafirmagreg.api.spi.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import lombok.Getter;

@Getter
public abstract class BaseBlockStairs extends BlockStairs implements IBlockSettings {

    protected final Settings settings;

    // the super constructor is protected...
    protected BaseBlockStairs(Block model) {
        super(model.getDefaultState());

        this.settings = Settings.copy(model);
        this.useNeighborBrightness = true;

        setHarvestLevel(model.getHarvestTool(model.getDefaultState()), model.getHarvestLevel(model.getDefaultState()));

    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getRenderLayer() {
        return getSettings().getRenderLayer();
    }
}
