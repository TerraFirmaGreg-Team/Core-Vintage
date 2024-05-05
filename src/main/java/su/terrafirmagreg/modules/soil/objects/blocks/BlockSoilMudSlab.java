package su.terrafirmagreg.modules.soil.objects.blocks;

import su.terrafirmagreg.api.model.ICustomState;
import su.terrafirmagreg.api.spi.block.BaseBlockSlab;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;


import lombok.Getter;

@Getter
public abstract class BlockSoilMudSlab extends BaseBlockSlab implements ISoilBlock, ICustomState {

    private final SoilBlockVariant variant;
    private final SoilType type;

    protected Block block;
    protected Half halfSlab;
    protected Double doubleSlab;

    private BlockSoilMudSlab(SoilBlockVariant model, SoilBlockVariant variant, SoilType type) {
        super(Settings.of()
                .material(Material.GROUND)
                .soundType(SoundType.GROUND));

        this.variant = variant;
        this.type = type;
        this.block = model.get(type);

        setHarvestLevel("pickaxe", block.getHarvestLevel(block.getDefaultState()));
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, "slab");
        OreDictUtils.register(this, "slab", "mud", "bricks");
    }

    public static class Double extends BlockSoilMudSlab {

        public Double(SoilBlockVariant model, SoilBlockVariant variant, SoilType type) {
            super(model, variant, type);
        }

        @Override
        public boolean isDouble() {
            return true;
        }

        @Override
        public Double getDoubleSlab() {
            return this;
        }

    }

    public static class Half extends BlockSoilMudSlab {

        public Half(SoilBlockVariant model, SoilBlockVariant doubleSlab, SoilBlockVariant variant, SoilType type) {
            super(model, variant, type);

            this.doubleSlab = (Double) doubleSlab.get(type);
            this.doubleSlab.halfSlab = this;
            this.halfSlab = this;
        }

        @Override
        public boolean isDouble() {
            return false;
        }

        @Override
        public Half getHalfSlab() {
            return this;
        }

    }
}
