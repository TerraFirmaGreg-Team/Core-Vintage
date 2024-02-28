package su.terrafirmagreg.modules.rock.objects.blocks;

import lombok.Getter;
import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import java.util.List;

@Getter
public class BlockRockWall extends BlockWall implements IRockBlock {

	private final RockBlockVariant blockVariant;
	private final RockType type;

	public BlockRockWall(RockBlockVariant blockVariant, RockType type) {
		super(Blocks.COBBLESTONE);

		this.blockVariant = blockVariant;
		this.type = type;

		setSoundType(SoundType.STONE);

		setHarvestLevel("pickaxe", 0);

//		OreDictionaryHelper.register(this, blockVariant.toString(), type.toString());
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockBase(this);
	}

	@Override
	public void getSubBlocks(@NotNull CreativeTabs itemIn, @NotNull NonNullList<ItemStack> items) {
		items.add(new ItemStack(this));
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
