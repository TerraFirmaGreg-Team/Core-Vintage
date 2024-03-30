package su.terrafirmagreg.modules.wood.objects.blocks;

import lombok.Getter;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.models.CustomStateMap;
import su.terrafirmagreg.api.spi.block.BlockSlabBase;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

import java.util.Random;

@Getter
public abstract class BlockWoodSlab extends BlockSlabBase implements IWoodBlock {

	private final WoodBlockVariant blockVariant;
	private final WoodType type;

	protected Block block;
	protected Half halfSlab;
	protected Double doubleSlab;

	private BlockWoodSlab(WoodBlockVariant model, WoodBlockVariant blockVariant, WoodType type) {
		super(Material.WOOD);

		this.blockVariant = blockVariant;
		this.type = type;
		this.block = model.get(type);

		setSoundType(SoundType.STONE);
		setHarvestLevel("axe", 0);
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
	public IBlockColor getColorHandler() {
		return (s, w, p, i) -> this.getType().getColor();
	}

	@Override
	public IItemColor getItemColorHandler() {
		return (s, i) -> this.getType().getColor();
	}


	@Override
	@SideOnly(Side.CLIENT)
	public void onStateMapperRegister() {
		ModelLoader.setCustomStateMapper(this, new CustomStateMap.Builder().customResource(getResourceLocation()).ignore(VARIANT).build());
	}

	public static class Double extends BlockWoodSlab {

		public Double(WoodBlockVariant model, WoodBlockVariant blockVariant, WoodType type) {
			super(model, blockVariant, type);

		}

		@Override
		public boolean isDouble() {
			return true;
		}
	}

	public static class Half extends BlockWoodSlab {

		public Half(WoodBlockVariant model, WoodBlockVariant doubleSlab, WoodBlockVariant blockVariant, WoodType type) {
			super(model, blockVariant, type);

			this.doubleSlab = (Double) doubleSlab.get(type);
			this.doubleSlab.halfSlab = this;
			this.halfSlab = this;

//            OreDictUtils.register(this, variant.toString());
//            OreDictUtils.register(this, variant.toString(), "wood");
//            OreDictUtils.register(this, variant.toString(), "wood", type.toString());
		}

		@Override
		public boolean isDouble() {
			return false;
		}
	}
}
