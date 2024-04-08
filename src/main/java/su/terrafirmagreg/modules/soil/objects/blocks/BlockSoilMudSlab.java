package su.terrafirmagreg.modules.soil.objects.blocks;

import su.terrafirmagreg.api.model.ICustomStateMapper;
import su.terrafirmagreg.api.spi.block.BlockBaseSlab;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;


import lombok.Getter;

import org.jetbrains.annotations.NotNull;

import java.util.Random;

@Getter
public abstract class BlockSoilMudSlab extends BlockBaseSlab implements ISoilBlock, ICustomStateMapper {

    private final SoilBlockVariant blockVariant;
    private final SoilType type;

    protected Block block;
    protected Half halfSlab;
    protected Double doubleSlab;

    private BlockSoilMudSlab(SoilBlockVariant model, SoilBlockVariant blockVariant, SoilType type) {
        super(Material.GROUND);

        this.blockVariant = blockVariant;
        this.type = type;
        this.block = model.get(type);

        setSoundType(SoundType.GROUND);
        setHarvestLevel("pickaxe", 0);
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, "slab");
        OreDictUtils.register(this, "slab", "mud", "bricks");
    }

    @Override
    public ItemBlock getItemBlock() {
        return this.isDouble() ? null : new ItemSlab(this.halfSlab, this.halfSlab, this.halfSlab.doubleSlab);
    }

    @Override
    @NotNull
    public Item getItemDropped(@NotNull IBlockState state, @NotNull Random rand, int fortune) {
        return Item.getItemFromBlock(halfSlab);
    }

    public static class Double extends BlockSoilMudSlab {

        public Double(SoilBlockVariant model, SoilBlockVariant variant, SoilType type) {
            super(model, variant, type);
        }

        @Override
        public boolean isDouble() {
            return true;
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

    }
}
