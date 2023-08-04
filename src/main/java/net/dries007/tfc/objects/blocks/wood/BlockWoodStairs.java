package net.dries007.tfc.objects.blocks.wood;

import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types2.wood.Wood;
import net.dries007.tfc.api.types2.wood.WoodVariant;
import net.dries007.tfc.api.types2.wood.util.IWoodBlock;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types2.wood.WoodVariant.PLANKS;

@ParametersAreNonnullByDefault
public class BlockWoodStairs extends BlockStairs implements IWoodBlock {
	private final WoodVariant woodVariant;
	private final Wood wood;
	private final ResourceLocation modelLocation;

	public BlockWoodStairs(WoodVariant woodVariant, Wood wood) {
		super(TFCStorage.getWoodBlock(PLANKS, wood).getDefaultState());
		this.woodVariant = woodVariant;
		this.wood = wood;
		this.modelLocation = new ResourceLocation(MOD_ID, "wood/" + woodVariant);

		var blockRegistryName = String.format("wood/%s/%s", woodVariant, wood);
		setRegistryName(MOD_ID, blockRegistryName);
		setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
		setCreativeTab(CreativeTabsTFC.WOOD);

		useNeighborBrightness = true;

		OreDictionaryHelper.register(this, "stair");
		OreDictionaryHelper.register(this, "stair", "wood");
		OreDictionaryHelper.register(this, "stair", "wood", wood);

		Blocks.FIRE.setFireInfo(this, 5, 20);
	}

	@Override
	public WoodVariant getWoodVariant() {
		return woodVariant;
	}

	@Override
	public Wood getWood() {
		return wood;
	}

	@Nullable
	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockTFC(this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onModelRegister() {
		ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
			@Nonnull
			protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
				return new ModelResourceLocation(modelLocation, this.getPropertyString(state.getProperties()));
			}
		});

		for (IBlockState state : this.getBlockState().getValidStates()) {
			ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),
				this.getMetaFromState(state),
				new ModelResourceLocation(modelLocation, "normal"));
		}
	}
}
