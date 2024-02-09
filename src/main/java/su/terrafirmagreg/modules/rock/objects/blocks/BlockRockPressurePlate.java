package su.terrafirmagreg.modules.rock.objects.blocks;

import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.dries007.tfc.util.OreDictionaryHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import java.util.List;

public class BlockRockPressurePlate extends BlockPressurePlate implements IRockBlock {

    private final RockBlockVariant variant;
    private final RockType type;

    public BlockRockPressurePlate(RockBlockVariant variant, RockType type) {
        super(Material.ROCK, Sensitivity.MOBS);

        this.variant = variant;
        this.type = type;

        setSoundType(SoundType.STONE);
        setHardness(0.5f);

        OreDictionaryHelper.register(this, variant.toString(), type.toString());
        OreDictionaryHelper.register(this, "pressure_plate_stone");
    }

    @NotNull
    @Override
    public RockBlockVariant getBlockVariant() {
        return variant;
    }

    @NotNull
    @Override
    public RockType getType() {
        return type;
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockBase(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TextComponentTranslation("rockcategory.name")
                        .getFormattedText() + ": " + type.getRockCategory().getLocalizedName());
    }
}
