package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.modules.core.capabilities.size.spi.Size;
import su.terrafirmagreg.modules.core.capabilities.size.spi.Weight;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.BlockMagma;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import gregtech.api.items.toolitem.ToolClasses;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.List;

@Getter
public class BlockRockMagma extends BlockMagma implements IRockBlock {

    protected final Settings settings;
    private final RockBlockVariant variant;
    private final RockType type;

    public BlockRockMagma(RockBlockVariant variant, RockType type) {

        this.variant = variant;
        this.type = type;
        this.settings = Settings.of(Material.ROCK)
                .soundType(SoundType.STONE)
                .size(Size.SMALL)
                .weight(Weight.LIGHT)
                .renderLayer(BlockRenderLayer.CUTOUT)
                .harvestLevel(ToolClasses.PICKAXE, 0)
                .fallable(this, variant.getSpecification())
                .addOreDict(variant)
                .addOreDict(variant, type);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TextComponentTranslation(
                "rockcategory.name").getFormattedText() + ": " + getType().getRockCategory().getLocalizedName());
    }
}
