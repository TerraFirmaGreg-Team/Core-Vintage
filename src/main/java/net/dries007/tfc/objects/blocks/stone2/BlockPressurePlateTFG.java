package net.dries007.tfc.objects.blocks.stone2;

import net.dries007.tfc.api.types2.rock.RockBlockType;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.util.IStoneTypeBlock;
import net.dries007.tfc.api.util.Triple;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.objects.blocks.rock.BlockRock.BLOCK_MAP;

public class BlockPressurePlateTFG extends BlockPressurePlate implements IStoneTypeBlock {

	private final RockBlockType blockType;
	private final RockVariant rockVariant;
	private final RockType rockType;
	private final ResourceLocation modelLocation;

	public BlockPressurePlateTFG(RockBlockType blockType, RockVariant rockVariant, RockType rockType) {
		super(Material.ROCK, Sensitivity.MOBS);

		if (BLOCK_MAP.put(new Triple<>(blockType, rockVariant, rockType), this) != null)
			throw new RuntimeException("Duplicate registry entry detected for block: " + rockVariant + " " + rockType);

		this.blockType = blockType;
		this.rockVariant = rockVariant;
		this.rockType = rockType;
		this.modelLocation = new ResourceLocation(MOD_ID, blockType + "/" + rockVariant);

		String blockRegistryName = String.format("%s/%s/%s", blockType, rockVariant, rockType);
		this.setCreativeTab(CreativeTabsTFC.CT_ROCK_BLOCKS);
		this.setSoundType(SoundType.STONE);
		this.setHardness(0.5F);
		this.setResistance(rockVariant.getResistance());
		this.setHarvestLevel("pickaxe", rockVariant.getHarvestLevel());
		this.setRegistryName(MOD_ID, blockRegistryName);
		this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));

		//OreDictionaryModule.register(this, blockType.getName(), rockVariant.getName(), rockVariant.getName() + WordUtils.capitalize(rockType.getName()));
	}

	@Override
	public RockVariant getRockVariant() {
		return rockVariant;
	}

	@Override
	public RockType getRockType() {
		return rockType;
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
						"powered=" + state.getValue(POWERED) + "," +
								"stonetype=" + rockType.getName());
			}
		});

		for (IBlockState state : this.getBlockState().getValidStates()) {
			ModelLoader.setCustomModelResourceLocation(
					Item.getItemFromBlock(this),
					this.getMetaFromState(state),
					new ModelResourceLocation(modelLocation, "inventory=" + rockType.getName()));
		}

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);

		tooltip.add(new TextComponentTranslation("stonecategory.name").getFormattedText() + ": " + rockType.getRockCategory().getLocalizedName());
	}
}
