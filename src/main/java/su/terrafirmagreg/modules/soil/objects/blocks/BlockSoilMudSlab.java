package su.terrafirmagreg.modules.soil.objects.blocks;

import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.models.ICustomStateMapper;
import su.terrafirmagreg.api.models.ModelManager;
import su.terrafirmagreg.api.spi.block.BlockSlabBase;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

import java.util.Random;

@Getter
public abstract class BlockSoilMudSlab extends BlockSlabBase implements ISoilBlock, ICustomStateMapper {

	private final SoilBlockVariant blockVariant;
	private final SoilType type;

	protected Block block;
	protected Half halfSlab;
	protected Double doubleSlab;

	private BlockSoilMudSlab(SoilBlockVariant model, SoilBlockVariant blockVariant, SoilType type) {
		super(Material.GROUND);

		this.blockVariant = blockVariant;
		this.type = type;
		this.block = model.getBlock(type);

		setSoundType(SoundType.GROUND);
		setHarvestLevel("pickaxe", 0);
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

	@SuppressWarnings("deprecation")
	@Override
	@NotNull
	public ItemStack getItem(@NotNull World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state) {
		return new ItemStack(halfSlab);
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void onStateMapperRegister() {
		ModelManager.registerStateMapper(this, new StateMap.Builder().ignore(VARIANT).build());
	}


	public static class Double extends BlockSoilMudSlab {

		public Double(SoilBlockVariant model, SoilBlockVariant variant, SoilType type) {
			super(model, variant, type);
		}

		@Override
		public boolean isDouble() {
			return true;
		}
	}

	public static class Half extends BlockSoilMudSlab {

		public Half(SoilBlockVariant model, SoilBlockVariant doubleSlab, SoilBlockVariant variant, SoilType type) {
			super(model, variant, type);

			this.doubleSlab = (Double) doubleSlab.getBlock(type);
			this.doubleSlab.halfSlab = this;
			this.halfSlab = this;

			//OreDictionaryHelper.register(this, variant.toString());
			//OreDictionaryHelper.register(this, variant.toString(), type.toString());
		}

		@Override
		public boolean isDouble() {
			return false;
		}

	}
}
