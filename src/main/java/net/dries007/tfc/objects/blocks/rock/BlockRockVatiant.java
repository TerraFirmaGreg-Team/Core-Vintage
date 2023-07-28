package net.dries007.tfc.objects.blocks.rock;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types2.rock.RockBlockType;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.util.IRockTypeBlock;
import net.dries007.tfc.api.util.Triple;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BlockRockVatiant extends Block implements IRockTypeBlock, IItemSize {

	public static final Map<Triple<RockBlockType, RockVariant, RockType>, IRockTypeBlock> BLOCK_ROCK_MAP = new LinkedHashMap<>();

	public BlockRockVatiant(Material material) {
		super(material);

		setCreativeTab(CreativeTabsTFC.ROCK_STUFFS);
		setSoundType(SoundType.STONE);
	}

	public static Block getBlockRockMap(RockBlockType rockBlockType, RockVariant blockVariant, RockType stoneType) {
		return (Block) BLOCK_ROCK_MAP.get(new Triple<>(rockBlockType, blockVariant, stoneType));
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

	@Nonnull
	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.SOLID;
	}
}
