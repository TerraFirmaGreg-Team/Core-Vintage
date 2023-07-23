package net.dries007.tfc.objects.blocks.rock;

import net.dries007.tfc.api.types2.rock.RockBlockType;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.api.util.IRockTypeBlock;
import net.dries007.tfc.api.util.Triple;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types2.rock.RockVariant.GRAVEL;
import static net.dries007.tfc.objects.blocks.rock.BlockRock.BLOCK_ROCK_MAP;

public class BlockRockFallable extends Block implements IRockTypeBlock {

	private final RockVariant blockVariant;
	private final RockType stoneType;
	private final ResourceLocation modelLocation;

	public BlockRockFallable(RockBlockType blockType, RockVariant rockVariant, RockType stoneType) {
		super(Material.SAND);

		if (BLOCK_ROCK_MAP.put(new Triple<>(blockType, rockVariant, stoneType), this) != null)
			throw new RuntimeException("Duplicate registry entry detected for block: " + rockVariant + " " + stoneType);

		if (rockVariant.canFall())
		{
			FallingBlockManager.registerFallable(this, rockVariant.getFallingSpecification());
		}

		this.blockVariant = rockVariant;
		this.stoneType = stoneType;
		this.modelLocation = new ResourceLocation(MOD_ID, blockType + "/" + rockVariant);

		String blockRegistryName = String.format("%s/%s/%s", blockType, rockVariant, stoneType);
		this.setCreativeTab(CreativeTabsTFC.CT_ROCK_BLOCKS);
		this.setSoundType(SoundType.GROUND);
		this.setHardness(0.6F);
		this.setResistance(rockVariant.getResistance());
		this.setHarvestLevel("shovel", rockVariant.getHarvestLevel());
		this.setRegistryName(MOD_ID, blockRegistryName);
		this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));

		//OreDictionaryModule.register(this, rockVariant.getName(), rockVariant.getName() + WordUtils.capitalize(stoneType.getName()));
	}

	@Override
	public RockVariant getRockVariant() {
		return blockVariant;
	}

	@Override
	public RockType getRockType() {
		return stoneType;
	}

	@Override
	public ItemBlock getItemBlock() {
		ItemBlock itemBlock = new ItemBlock(this);
		//noinspection ConstantConditions
		itemBlock.setRegistryName(this.getRegistryName());
		return itemBlock;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onModelRegister() {
		ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
			@Nonnull
			protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
				return new ModelResourceLocation(modelLocation, "stonetype=" + stoneType.getName());
			}
		});

		ModelLoader.setCustomModelResourceLocation(
				Item.getItemFromBlock(this),
				this.getMetaFromState(this.getBlockState().getBaseState()),
				new ModelResourceLocation(modelLocation, "stonetype=" + stoneType.getName()));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(@Nonnull ItemStack stack, World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);

		tooltip.add(new TextComponentTranslation("stonecategory.name").getFormattedText() + ": " + stoneType.getRockCategory().getLocalizedName());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
	{
		if (this.blockVariant.canFall() && rand.nextInt(16) == 0 && FallingBlockManager.shouldFall(world, pos, pos, state, false))
		{
			double d0 = (float) pos.getX() + rand.nextFloat();
			double d1 = (double) pos.getY() - 0.05D;
			double d2 = (float) pos.getZ() + rand.nextFloat();
			world.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.getStateId(state));
		}
	}

	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune)
	{
		if (blockVariant == GRAVEL)
		{
			if (fortune > 3)
			{
				fortune = 3;
			}

			if (rand.nextInt(10 - fortune * 3) == 0)
			{
				return Items.FLINT;
			}
		}
		return super.getItemDropped(state, rand, fortune);
	}
}
