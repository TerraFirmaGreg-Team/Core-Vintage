package su.terrafirmagreg.modules.soil.objects.blocks;

import lombok.Getter;
import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import su.terrafirmagreg.api.models.ICustomStateMapper;
import su.terrafirmagreg.api.models.ModelManager;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.modules.soil.api.types.type.SoilType;
import su.terrafirmagreg.modules.soil.api.types.variant.block.ISoilBlockVariant;
import su.terrafirmagreg.modules.soil.api.types.variant.block.SoilBlockVariant;

@Getter
public class BlockSoilMudWall extends BlockWall implements ISoilBlockVariant, ICustomStateMapper {

	private final SoilBlockVariant blockVariant;
	private final SoilType type;

	public BlockSoilMudWall(SoilBlockVariant blockVariant, SoilType type) {
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
		ModelManager.registerStateMapper(this, new StateMap.Builder().ignore(BlockWall.VARIANT).build());
	}
}
