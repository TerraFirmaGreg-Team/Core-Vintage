package su.terrafirmagreg.modules.rock.objects.blocks;

import su.terrafirmagreg.api.spi.block.BaseBlock;
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


import gregtech.api.items.toolitem.ToolClasses;

import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.List;

@Getter
public abstract class BlockRock extends BaseBlock implements IRockBlock {

    private final RockBlockVariant variant;
    private final RockType type;

    public BlockRock(Settings settings, RockBlockVariant variant, RockType type) {
        super(settings);

        this.variant = variant;
        this.type = type;

        getSettings()
                .soundType(SoundType.STONE)
                .addOreDict(variant)
                .addOreDict(variant, type);

        setHarvestLevel(ToolClasses.PICKAXE, 0);
    }

    public BlockRock(RockBlockVariant variant, RockType type) {
        this(Settings.of(Material.ROCK), variant, type);

    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TextComponentTranslation("rockcategory.name").getFormattedText() + ": " + getType().getRockCategory().getLocalizedName());
    }
}
