package su.terrafirmagreg.modules.rock.objects.blocks;

import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.spi.block.BlockSlabBase;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.rock.api.types.type.RockType;
import su.terrafirmagreg.modules.rock.api.types.variant.block.IRockBlock;
import su.terrafirmagreg.modules.rock.api.types.variant.block.RockBlockVariant;

import java.util.List;
import java.util.Random;

@Getter
public abstract class BlockRockSlab extends BlockSlabBase implements IRockBlock {

	private final RockBlockVariant blockVariant;
	private final RockType type;

	protected Block block;
	protected Half halfSlab;
	protected Double doubleSlab;

	private BlockRockSlab(RockBlockVariant model, RockBlockVariant blockVariant, RockType type) {
		super(Material.ROCK);

		this.blockVariant = blockVariant;
		this.type = type;
		this.block = model.get(type);

		setSoundType(SoundType.STONE);
		setHarvestLevel("pickaxe", 0);
	}

	@Override
	public void onRegisterOreDict() {
		OreDictUtils.register(this, "slab");
		OreDictUtils.register(this, "slab", "stone");
	}

	@Override
	public ItemBlock getItemBlock() {
		return this.isDouble() ? null : new ItemSlab(this.halfSlab, this.halfSlab, this.halfSlab.doubleSlab);
	}

	@Override
	@NotNull
	public Item getItemDropped(@NotNull IBlockState state, @NotNull Random rand, int fortune) {
		return Item.getItemFromBlock(halfSlab);
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(@NotNull ItemStack stack, @Nullable World worldIn, @NotNull List<String> tooltip, @NotNull ITooltipFlag flagIn) {
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
	}

	public static class Half extends BlockRockSlab {

		public Half(RockBlockVariant model, RockBlockVariant doubleSlab, RockBlockVariant variant, RockType type) {
			super(model, variant, type);

			this.doubleSlab = (Double) doubleSlab.get(type);
			this.doubleSlab.halfSlab = this;
			this.halfSlab = this;
		}

		@Override
		public boolean isDouble() {
			return false;
		}

	}
}
