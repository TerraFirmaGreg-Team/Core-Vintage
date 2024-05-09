package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.model.CustomStateMap;
import su.terrafirmagreg.api.spi.block.BaseBlockSlab;
import su.terrafirmagreg.api.util.BlockUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import lombok.Getter;

@Getter
public abstract class BlockWoodSlab extends BaseBlockSlab implements IWoodBlock {

    private final WoodBlockVariant variant;
    private final WoodType type;

    protected Block block;
    protected Half halfSlab;
    protected Double doubleSlab;

    private BlockWoodSlab(WoodBlockVariant model, WoodBlockVariant variant, WoodType type) {
        super(Settings.of()
                .material(Material.WOOD)
                .soundType(SoundType.WOOD));

        this.variant = variant;
        this.type = type;
        this.block = model.get(type);

        setHarvestLevel("pickaxe", block.getHarvestLevel(block.getDefaultState()));
        BlockUtils.setFireInfo(this, variant.getEncouragement(), variant.getFlammability());
    }

    @Override
    public IBlockColor getBlockColor() {
        return (s, w, p, i) -> this.getType().getColor();
    }

    @Override
    public IItemColor getItemColor() {
        return (s, i) -> this.getType().getColor();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onRegisterState() {
        ModelLoader.setCustomStateMapper(this, new CustomStateMap.Builder().customResource(getResourceLocation()).ignore(VARIANT).build());
    }

    public static class Double extends BlockWoodSlab {

        public Double(WoodBlockVariant model, WoodBlockVariant variant, WoodType type) {
            super(model, variant, type);

        }

        @Override
        public boolean isDouble() {
            return true;
        }
    }

    public static class Half extends BlockWoodSlab {

        public Half(WoodBlockVariant model, WoodBlockVariant doubleSlab, WoodBlockVariant variant, WoodType type) {
            super(model, variant, type);

            this.doubleSlab = (Double) doubleSlab.get(type);
            this.doubleSlab.halfSlab = this;
            this.halfSlab = this;

        }

        @Override
        public void onRegisterOreDict() {
            OreDictUtils.register(this, getVariant());
            OreDictUtils.register(this, getVariant(), "wood");
            OreDictUtils.register(this, getVariant(), "wood", getType());
        }

        @Override
        public boolean isDouble() {
            return false;
        }
    }
}
