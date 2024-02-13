package su.terrafirmagreg.modules.rock.objects.blocks;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import java.util.List;

@Getter
public class BlockRockStairs extends BlockStairs implements IRockBlock {

    private final RockBlockVariant blockVariant;
    private final RockType type;

    public BlockRockStairs(RockBlockVariant blockVariant, RockType type) {
        super(Blocks.COBBLESTONE.getDefaultState());

        this.blockVariant = blockVariant;
        this.type = type;
        this.useNeighborBrightness = true;

        setSoundType(SoundType.STONE);
        setHardness(getFinalHardness());
        setHarvestLevel("pickaxe", 0);

        //OreDictionaryHelper.register(this, blockVariant.toString(), type.toString());
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockBase(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(
                new TextComponentTranslation("rockcategory.name")
                        .getFormattedText() + ": " + type.getRockCategory().getLocalizedName());
    }


}
