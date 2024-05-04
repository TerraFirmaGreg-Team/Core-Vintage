package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlock;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.List;

@Getter
public abstract class BlockRock extends BaseBlock implements IRockBlock {

    private final RockBlockVariant blockVariant;
    private final RockType type;

    public BlockRock(Settings settings, RockBlockVariant blockVariant, RockType type) {
        super(settings.soundType(SoundType.STONE));

        this.blockVariant = blockVariant;
        this.type = type;

        setHarvestLevel("pickaxe", 0);
    }

    public BlockRock(RockBlockVariant blockVariant, RockType type) {
        this(Settings.of().material(Material.ROCK), blockVariant, type);
    }

    @Override
    public void onRegisterOreDict() {
        OreDictUtils.register(this, blockVariant);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TextComponentTranslation("rockcategory.name").getFormattedText() + ": " + getCategory().getLocalizedName());
    }
}
