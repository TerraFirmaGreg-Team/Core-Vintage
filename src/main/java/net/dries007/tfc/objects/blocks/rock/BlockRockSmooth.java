/*
 * Work under Copyright. Licensed under the EUPL.
 * See the project README.md and LICENSE.txt for more information.
 */

package net.dries007.tfc.objects.blocks.rock;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.api.types2.rock.util.IRockTypeBlock;
import net.dries007.tfc.api.util.Triple;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types2.rock.RockBlockType.ORDINARY;
import static net.dries007.tfc.objects.blocks.rock.BlockRockVariant.BLOCK_ROCK_MAP;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockRockSmooth extends Block implements IRockTypeBlock, IItemSize {
	public static final PropertyBool CAN_FALL = PropertyBool.create("can_fall");

	private final RockType rockType;
	private final RockVariant rockVariant;
	private final ResourceLocation modelLocation;

	public BlockRockSmooth(RockVariant rockVariant, RockType rockType) {
		super(Material.ROCK);

		if (BLOCK_ROCK_MAP.put(new Triple<>(ORDINARY, rockVariant, rockType), this) != null)
			throw new RuntimeException("Duplicate registry entry detected for block: " + rockVariant + " " + rockType);

		this.rockVariant = rockVariant;
		this.rockType = rockType;
		this.modelLocation = new ResourceLocation(MOD_ID, "rock/" + rockVariant);

		String blockRegistryName = String.format("rock/%s/%s", rockVariant, rockType);
		this.setHardness(getFinalHardness());
		this.setHarvestLevel("pickaxe", 0);
		this.setDefaultState(getBlockState().getBaseState().withProperty(CAN_FALL, false));
		this.setRegistryName(MOD_ID, blockRegistryName);
		this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));


		FallingBlockManager.Specification spec = new FallingBlockManager.Specification(rockVariant.getFallingSpecification()); // Copy as each raw stone has an unique resultingState
		FallingBlockManager.registerFallable(this.getDefaultState().withProperty(CAN_FALL, true), spec);
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

	@Nonnull
	@Override
	public Size getSize(@Nonnull ItemStack stack) {
		return Size.SMALL; // Store anywhere
	}

	@Nonnull
	@Override
	public Weight getWeight(@Nonnull ItemStack stack) {
		return Weight.LIGHT; // Stacksize = 32
	}

	@Override
	@SuppressWarnings("deprecation")
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(CAN_FALL, meta == 1);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(CAN_FALL) ? 1 : 0;
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, CAN_FALL);
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
