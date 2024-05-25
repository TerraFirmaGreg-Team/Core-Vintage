package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlockWall;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.SoundType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.List;

@Getter
public class BlockRockWall extends BaseBlockWall implements IRockBlock {

    private final RockBlockVariant variant;
    private final RockType type;

    public BlockRockWall(RockBlockVariant modelBlock, RockBlockVariant variant, RockType type) {
        super(modelBlock.get(type));

        this.variant = variant;
        this.type = type;

        getSettings()
                .soundType(SoundType.STONE)
                .renderLayer(BlockRenderLayer.CUTOUT)
                .addOreDict("wall")
                .addOreDict("wall", "stone");

        setHarvestLevel("pickaxe", 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TextComponentTranslation("rockcategory.name").getFormattedText() + ": " + type.getRockCategory().getLocalizedName());
    }
}
