package su.terrafirmagreg.modules.wood.objects.blocks;

import lombok.Getter;
import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.models.CustomStateMap;
import su.terrafirmagreg.api.models.ModelManager;
import su.terrafirmagreg.api.spi.block.IColorfulBlock;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;

@Getter
public class BlockWoodWall extends BlockWall implements IWoodBlock, IColorfulBlock {

	private final WoodBlockVariant blockVariant;
	private final WoodType type;

	public BlockWoodWall(WoodBlockVariant modelBlock, WoodBlockVariant blockVariant, WoodType type) {
		super(modelBlock.getBlock(type));

		this.blockVariant = blockVariant;
		this.type = type;

		setSoundType(SoundType.WOOD);
		setHarvestLevel("axe", 0);

//		OreDictionaryHelper.register(this, blockVariant.toString(), type.toString());
	}

	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockBase(this);
	}

	@NotNull
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public void getSubBlocks(@NotNull CreativeTabs itemIn, @NotNull NonNullList<ItemStack> items) {
		items.add(new ItemStack(this));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onModelRegister() {
		ModelManager.registerBlockInventoryModel(this, getResourceLocation());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onStateMapperRegister() {
		ModelManager.registerStateMapper(this, new CustomStateMap.Builder()
				.customResource(getResourceLocation())
				.ignore(BlockWall.VARIANT)
				.build());
	}

	@Override
	public IBlockColor getColorHandler() {
		return (s, w, p, i) -> this.getType().getColor();
	}

	@Override
	public IItemColor getItemColorHandler() {
		return (s, i) -> this.getType().getColor();
	}
}
