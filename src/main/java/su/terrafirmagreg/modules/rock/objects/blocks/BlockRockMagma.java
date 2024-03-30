package su.terrafirmagreg.modules.rock.objects.blocks;

import lombok.Getter;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.minecraft.block.BlockMagma;
import net.minecraft.block.SoundType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import java.util.List;

@Getter
public class BlockRockMagma extends BlockMagma implements IRockBlock {

	private final RockBlockVariant blockVariant;
	private final RockType type;

	public BlockRockMagma(RockBlockVariant blockVariant, RockType type) {

		this.blockVariant = blockVariant;
		this.type = type;

		setSoundType(SoundType.STONE);
		setHarvestLevel("pickaxe", 0);

		FallingBlockManager.registerFallable(this, blockVariant.getSpecification());
	}

	@Override
	public void onRegisterOreDict() {
		OreDictUtils.register(this, blockVariant);
	}

	@Override
	public ItemBlock getItemBlock() {
//        return this.getType().getRockCategory().isHasAnvil() ? new ItemBlockBase(this) : null;
		return new ItemBlockBase(this);
	}

	@NotNull
	@Override
	public Size getSize(@NotNull ItemStack stack) {
		return Size.SMALL;
	}

	@NotNull
	@Override
	public Weight getWeight(@NotNull ItemStack stack) {
		return Weight.LIGHT;
	}

	@NotNull
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);

		tooltip.add(new TextComponentTranslation(
				"rockcategory.name").getFormattedText() + ": " + getType().getRockCategory().getLocalizedName());
	}
}
