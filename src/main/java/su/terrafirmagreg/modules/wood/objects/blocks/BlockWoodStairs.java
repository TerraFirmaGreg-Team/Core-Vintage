package su.terrafirmagreg.modules.wood.objects.blocks;

import lombok.Getter;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.models.ModelManager;
import su.terrafirmagreg.api.spi.block.IColorfulBlock;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.api.util.CustomStateMap;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariants;
import su.terrafirmagreg.modules.wood.init.BlocksWood;

@Getter
public class BlockWoodStairs extends BlockStairs implements IWoodBlock, IColorfulBlock {


	private final WoodBlockVariant blockVariant;
	private final WoodType type;

	public BlockWoodStairs(WoodBlockVariant blockVariant, WoodType type) {
		super(BlocksWood.getBlock(WoodBlockVariants.PLANKS, type).getDefaultState());

		this.blockVariant = blockVariant;
		this.type = type;
		this.useNeighborBrightness = true;
		setHarvestLevel("axe", 0);

//            OreDictionaryHelper.register(this, variant.toString());
//            OreDictionaryHelper.register(this, variant.toString(), "wood");
//            OreDictionaryHelper.register(this, variant.toString(), "wood", type.toString());
	}

	@Nullable
	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockBase(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onModelRegister() {
		ModelManager.registerBlockItemModel(this, getResourceLocation());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onStateMapperRegister() {
		ModelManager.registerStateMapper(this, new CustomStateMap.Builder().customPath(getResourceLocation()).build());
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
