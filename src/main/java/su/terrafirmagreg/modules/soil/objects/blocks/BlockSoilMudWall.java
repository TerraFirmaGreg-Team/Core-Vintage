package su.terrafirmagreg.modules.soil.objects.blocks;

import lombok.Getter;
import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.model.CustomStateMap;
import su.terrafirmagreg.api.model.ICustomStateMapper;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.api.util.ModelUtils;
import su.terrafirmagreg.api.util.OreDictUtils;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlock;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

@Getter
public class BlockSoilMudWall extends BlockWall implements ISoilBlock, ICustomStateMapper {

	private final SoilBlockVariant blockVariant;
	private final SoilType type;

	public BlockSoilMudWall(SoilBlockVariant modelBlock, SoilBlockVariant blockVariant, SoilType type) {
		super(modelBlock.get(type));

		this.blockVariant = blockVariant;
		this.type = type;

		setSoundType(SoundType.STONE);
		setHarvestLevel("pickaxe", 0);
	}

	@Override
	public void onRegisterOreDict() {
		OreDictUtils.register(this, "wall");
		OreDictUtils.register(this, "wall", "mud", "bricks");
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
	public void onStateMapperRegister() {
		ModelUtils.registerStateMapper(this, new CustomStateMap.Builder().ignore(BlockWall.VARIANT).build());
	}
}
