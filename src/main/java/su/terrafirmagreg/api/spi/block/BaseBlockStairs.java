package su.terrafirmagreg.api.spi.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;


import lombok.Getter;

@Getter
public abstract class BaseBlockStairs extends BlockStairs implements ISettingsBlock {

    protected final Settings settings;

    // the super constructor is protected...
    protected BaseBlockStairs(Block baseBlock) {
        super(baseBlock.getDefaultState());

        this.settings = Settings.copy(baseBlock);
        this.useNeighborBrightness = true;

    }
}
