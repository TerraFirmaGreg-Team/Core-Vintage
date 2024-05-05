package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlockSlab;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.List;

@Getter
public abstract class BlockRockSlab extends BaseBlockSlab implements IRockBlock {

    private final RockBlockVariant variant;
    private final RockType type;

    protected final Block block;
    protected Half halfSlab;
    protected Double doubleSlab;

    private BlockRockSlab(RockBlockVariant model, RockBlockVariant variant, RockType type) {
        super(Settings.of()
                .material(Material.ROCK)
                .soundType(SoundType.STONE));

        this.variant = variant;
        this.type = type;
        this.block = model.get(type);

        setHarvestLevel("pickaxe", block.getHarvestLevel(block.getDefaultState()));
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, "slab");
        OreDictUtils.register(this, "slab", "stone");
    }

    @NotNull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(
                new TextComponentTranslation("rockcategory.name")
                        .getFormattedText() + ": " + getType().getRockCategory().getLocalizedName());
    }

    public static class Double extends BlockRockSlab {

        public Double(RockBlockVariant model, RockBlockVariant variant, RockType type) {
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

    public static class Half extends BlockRockSlab {

        public Half(RockBlockVariant model, RockBlockVariant doubleSlab, RockBlockVariant variant, RockType type) {
            super(model, variant, type);

            this.doubleSlab = (Double) doubleSlab.get(type);
            this.doubleSlab.halfSlab = this;
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
