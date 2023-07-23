package net.dries007.tfc.objects.blocks.stone2;

import net.dries007.tfc.api.types2.BlockType;
import net.dries007.tfc.api.types2.BlockVariant;
import net.dries007.tfc.api.util.IStoneTypeBlock;
import net.dries007.tfc.api.types2.StoneType;
import net.dries007.tfc.api.util.Triple;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.minecraft.block.BlockButtonStone;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.objects.blocks.stone2.BlockOrdinaryTFG.BLOCK_MAP;

public class BlockButtonTFG extends BlockButtonStone implements IStoneTypeBlock {

	private final BlockVariant blockVariant;
	private final StoneType stoneType;
	private final ResourceLocation modelLocation;

	public BlockButtonTFG(BlockType blockType, BlockVariant blockVariant, StoneType stoneType) {

		if (BLOCK_MAP.put(new Triple<>(blockType, blockVariant, stoneType), this) != null)
			throw new RuntimeException("Duplicate registry entry detected for block: " + blockVariant + " " + stoneType);

		this.blockVariant = blockVariant;
		this.stoneType = stoneType;
		this.modelLocation = new ResourceLocation(MOD_ID, blockType + "/" + blockVariant);

		String blockRegistryName = String.format("%s/%s/%s", blockType, blockVariant, stoneType);
		this.setSoundType(SoundType.STONE);
		this.setHardness(0.5F);
		this.setResistance(stoneType.getResistance());
		this.setHarvestLevel("pickaxe", 0);
		this.setCreativeTab(CreativeTabsTFC.CT_ROCK_BLOCKS);
		this.setRegistryName(MOD_ID, blockRegistryName);
		this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));

		//OreDictionaryModule.register(this, blockType.getName(), blockVariant.getName(), blockVariant.getName() + WordUtils.capitalize(stoneType.getName()));
	}

	@Override
	public BlockVariant getBlockVariant() {
		return blockVariant;
	}

	@Override
	public StoneType getStoneType() {
		return stoneType;
	}

	@Override
	public ItemBlock getItemBlock() {
		ItemBlock itemBlock = new ItemBlock(this);
		//noinspection ConstantConditions
		itemBlock.setRegistryName(this.getRegistryName());
		return itemBlock;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onModelRegister() {
		ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
			@Nonnull
			protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
				return new ModelResourceLocation(modelLocation,
						"facing=" + state.getValue(FACING) + "," +
								"powered=" + state.getValue(POWERED) + "," +
								"stonetype=" + stoneType.getName());
			}
		});

		for (IBlockState state : this.getBlockState().getValidStates()) {
			ModelLoader.setCustomModelResourceLocation(
					Item.getItemFromBlock(this),
					this.getMetaFromState(state),
					new ModelResourceLocation(modelLocation, "inventory=" + stoneType.getName()));
		}
	}

	@Nonnull
	@Override
	public IBlockState getStateForPlacement(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull EnumFacing facing, float hitX, float hitY,
											float hitZ, int meta, @Nonnull EntityLivingBase placer) {
		IBlockState state = getStateFromMeta(meta);
		return canPlaceBlock(worldIn, pos, facing) ? state.withProperty(FACING, facing)
				: state.withProperty(FACING, EnumFacing.DOWN);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(@Nonnull ItemStack stack, World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);

		tooltip.add(new TextComponentTranslation("stonecategory.name").getFormattedText() + ": " + stoneType.getStoneCategory().getLocalizedName());
	}
}
