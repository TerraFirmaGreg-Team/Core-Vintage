package su.terrafirmagreg.modules.wood.objects.blocks;

import su.terrafirmagreg.api.model.CustomStateMap;
import su.terrafirmagreg.api.spi.block.BaseBlock;
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

    private final WoodBlockVariant blockVariant;
    private final WoodType type;

    protected Block block;
    protected Half halfSlab;
    protected Double doubleSlab;

    private BlockWoodSlab(WoodBlockVariant model, WoodBlockVariant blockVariant, WoodType type) {
        super(Settings.of()
                .material(Material.WOOD)
                .soundType(SoundType.WOOD));

        this.blockVariant = blockVariant;
        this.type = type;
        this.block = model.get(type);

        setHarvestLevel("pickaxe", block.getHarvestLevel(block.getDefaultState()));
        BlockUtils.setFireInfo(this, blockVariant.getEncouragement(), blockVariant.getFlammability());
    }

    @Override
    public IBlockColor getColorHandler() {
        return (s, w, p, i) -> this.getType().getColor();
    }

    @Override
    public IItemColor getItemColorHandler() {
        return (s, i) -> this.getType().getColor();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onStateMapperRegister() {
        ModelLoader.setCustomStateMapper(this, new CustomStateMap.Builder().customResource(getResourceLocation()).ignore(VARIANT).build());
    }

    public static class Double extends BlockWoodSlab {

        public Double(WoodBlockVariant model, WoodBlockVariant blockVariant, WoodType type) {
            super(model, blockVariant, type);

        }

        @Override
        public boolean isDouble() {
            return true;
        }
    }

    public static class Half extends BlockWoodSlab {

        public Half(WoodBlockVariant model, WoodBlockVariant doubleSlab, WoodBlockVariant blockVariant, WoodType type) {
            super(model, blockVariant, type);

            this.doubleSlab = (Double) doubleSlab.get(type);
            this.doubleSlab.halfSlab = this;
            this.halfSlab = this;

        }

        @Override
        public void onRegisterOreDict() {
            OreDictUtils.register(this, getBlockVariant());
            OreDictUtils.register(this, getBlockVariant(), "wood");
            OreDictUtils.register(this, getBlockVariant(), "wood", getType());
        }

        @Override
        public boolean isDouble() {
            return false;
        }
    }
}
