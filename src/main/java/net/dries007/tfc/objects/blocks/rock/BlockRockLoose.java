package net.dries007.tfc.objects.blocks.rock;

import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.types2.rock.util.IRockTypeBlock;
import net.dries007.tfc.api.util.Triple;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.util.ITooltipFlag;
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
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types2.rock.RockBlockType.ORDINARY;
import static net.dries007.tfc.objects.blocks.rock.BlockRock.BLOCK_ROCK_MAP;


@ParametersAreNonnullByDefault
public class BlockRockLoose extends BlockLoose implements IRockTypeBlock {
	private final RockVariant rockVariant;
	private final RockType rockType;
	private final ResourceLocation modelLocation;

	public BlockRockLoose(RockVariant rockVariant, RockType rockType) {
		if (BLOCK_ROCK_MAP.put(new Triple<>(ORDINARY, rockVariant, rockType), this) != null)
			throw new RuntimeException("Duplicate registry entry detected for block: " + rockVariant + " " + rockType);


		this.rockVariant = rockVariant;
		this.rockType = rockType;
		this.modelLocation = new ResourceLocation(MOD_ID, "rock/" + rockVariant);

		String blockRegistryName = String.format("rock/%s/%s", rockVariant, rockType);
		this.setSoundType(SoundType.STONE);
		this.setHardness(getFinalHardness());
		this.setHarvestLevel("pickaxe", 0);
		this.setRegistryName(MOD_ID, blockRegistryName);
		this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
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
		return null;
	}

//    @Override
//    @SuppressWarnings("ConstantConditions")
//    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
//                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
//        ItemStack itemStack = new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get(rockBlockType.getName() + "/" + rockType.getName()));
//
//        if (playerIn.addItemStackToInventory(itemStack)) {
//            worldIn.setBlockToAir(pos);
//
//            playerIn.swingArm(EnumHand.MAIN_HAND);
//            playerIn.playSound(SoundEvents.ENTITY_ITEMFRAME_REMOVE_ITEM, 1.0f, 1.0f);
//        }
//
//        return true;
//    }
//
//    @Override
//    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
//        drops.add(new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get(rockBlockType.getName() + "/" + rockType.getName())));
//    }
//
//    @Nonnull
//    @Override
//    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
//        return new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get(rockBlockType.getName() + "/" + rockType.getName()));
//    }


	@Override
	@SideOnly(Side.CLIENT)
	public void onModelRegister() {
		ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
			@Nonnull
			protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
				return new ModelResourceLocation(modelLocation, "rocktype=" + rockType.getName());
			}
		});
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);

		tooltip.add(new TextComponentTranslation("stonecategory.name").getFormattedText() + ": " + getRockType().getRockCategory().getLocalizedName());
	}
}
