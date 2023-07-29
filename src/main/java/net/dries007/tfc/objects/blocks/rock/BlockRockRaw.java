/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.blocks.rock;

import gregtech.common.items.ToolItems;
import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.api.util.Triple;
import net.dries007.tfc.objects.Gem;
import net.dries007.tfc.objects.items.ItemGem;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.registries.TFCRegistryNames.ROCK;
import static net.dries007.tfc.api.types2.rock.RockBlockType.ORDINARY;
import static net.dries007.tfc.api.types2.rock.RockVariant.ANVIL;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockRockRaw extends BlockRockVariant {
	/* This is for the not-surrounded-on-all-sides-pop-off mechanic. It's a dirty fix to the stack overflow caused by placement during water / lava collisions in world gen */
	public static final PropertyBool CAN_FALL = PropertyBool.create("can_fall");
	private final RockType rockType;
	private final RockVariant rockVariant;
	private final ResourceLocation modelLocation;

	public BlockRockRaw(RockVariant rockVariant, RockType rockType) {
		super(Material.ROCK);

		if (BLOCK_ROCK_MAP.put(new Triple<>(ORDINARY, rockVariant, rockType), this) != null)
			throw new RuntimeException("Duplicate registry entry detected for block: " + rockVariant + " " + rockType);

		FallingBlockManager.Specification spec = new FallingBlockManager.Specification(rockVariant.getFallingSpecification()); // Copy as each raw stone has an unique resultingState
		FallingBlockManager.registerFallable(this, spec);

		this.rockVariant = rockVariant;
		this.rockType = rockType;
		this.modelLocation = new ResourceLocation(MOD_ID, ROCK + "/" + rockVariant);

		String blockRegistryName = String.format("%s/%s/%s", ROCK, rockVariant, rockType);
		this.setHardness(getFinalHardness());
		this.setHarvestLevel("pickaxe", 0);
		this.setRegistryName(MOD_ID, blockRegistryName);
		this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
		setDefaultState(getBlockState().getBaseState().withProperty(CAN_FALL, true));
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
		return new ItemBlock(this);
	}

	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(CAN_FALL, meta == 0);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		if (state.getBlock() != this) {
			return 0;
		} else {
			return state.getValue(CAN_FALL) ? 0 : 1;
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
		// Raw blocks that can't fall also can't pop off
		if (state.getValue(CAN_FALL)) {
			for (EnumFacing face : EnumFacing.VALUES) {
				BlockPos offsetPos = pos.offset(face);
				IBlockState faceState = worldIn.getBlockState(offsetPos);
				if (faceState.getBlock().isSideSolid(faceState, worldIn, offsetPos, face.getOpposite())) {
					return;
				}
			}

			// No supporting solid blocks, so pop off as an item
			worldIn.setBlockToAir(pos);
			Helpers.spawnItemStack(worldIn, pos, new ItemStack(state.getBlock(), 1));
		}
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = playerIn.getHeldItemMainhand();
		if (ConfigTFC.General.OVERRIDES.enableStoneAnvil && stack.getItem() == ToolItems.HARD_HAMMER.get() && !worldIn.isBlockNormalCube(pos.up(), true)) {
			if (!worldIn.isRemote) {
				// Create a stone anvil
				Block anvil = getBlockRockMap(ORDINARY, ANVIL, rockType);
				if (anvil instanceof BlockRockAnvil) {
					worldIn.setBlockState(pos, anvil.getDefaultState());
				}
			}
			return true;
		}
		return false;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, CAN_FALL);
	}

	@Override
	public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
		super.getDrops(drops, world, pos, state, fortune);
		// Raw rocks drop random gems
		if (RANDOM.nextDouble() < ConfigTFC.General.MISC.stoneGemDropChance) {
			drops.add(ItemGem.get(Gem.getRandomDropGem(RANDOM), Gem.Grade.randomGrade(RANDOM), 1));
		}
	}

	@Override
	public int damageDropped(IBlockState state) {
		return 0;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onModelRegister() {
		ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
			@Nonnull
			protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
				return new ModelResourceLocation(modelLocation, "rocktype=" + rockType.getName());
			}
		});


		ModelLoader.setCustomModelResourceLocation(
				Item.getItemFromBlock(this),
				this.getMetaFromState(this.getBlockState().getBaseState()),
				new ModelResourceLocation(modelLocation, "rocktype=" + rockType.getName()));
	}
}