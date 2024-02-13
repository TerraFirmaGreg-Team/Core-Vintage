package su.terrafirmagreg.modules.rock.objects.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.spi.block.BlockBase;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import java.util.List;

@Getter
public abstract class BlockRock extends BlockBase implements IRockBlock {

    private final RockBlockVariant blockVariant;
    private final RockType type;

    public BlockRock(Material material, RockBlockVariant blockVariant, RockType type) {
        super(material);

        this.blockVariant = blockVariant;
        this.type = type;

        setSoundType(SoundType.STONE);
        setHardness(getFinalHardness());
        setHarvestLevel("pickaxe", 0);

//        if (getItemBlock() != null) {
//            OreDictionaryHelper.register(this, blockVariant.toString());
//            OreDictionaryHelper.register(this, blockVariant.toString(), type.toString());
//        }

    }

    public BlockRock(RockBlockVariant blockVariant, RockType type) {
        this(Material.ROCK, blockVariant, type);
    }


    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TextComponentTranslation(
                "rockcategory.name").getFormattedText() + ": " + getType().getRockCategory().getLocalizedName());
    }
}
