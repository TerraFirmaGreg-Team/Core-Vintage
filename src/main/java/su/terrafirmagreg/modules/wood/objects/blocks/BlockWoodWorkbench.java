package su.terrafirmagreg.modules.wood.objects.blocks;

import lombok.Getter;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemBlock;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.models.CustomStateMap;
import su.terrafirmagreg.api.models.ModelManager;
import su.terrafirmagreg.api.spi.block.IColorfulBlock;
import su.terrafirmagreg.api.spi.itemblock.ItemBlockBase;
import su.terrafirmagreg.modules.wood.api.types.type.WoodType;
import su.terrafirmagreg.modules.wood.api.types.variant.block.IWoodBlock;
import su.terrafirmagreg.modules.wood.api.types.variant.block.WoodBlockVariant;
import su.terrafirmagreg.modules.wood.objects.container.ContainerWoodWorkbench;

@Getter
public class BlockWoodWorkbench extends BlockWorkbench implements IWoodBlock, IColorfulBlock {

	private final WoodBlockVariant blockVariant;
	private final WoodType type;

	public BlockWoodWorkbench(WoodBlockVariant blockVariant, WoodType type) {

		this.blockVariant = blockVariant;
		this.type = type;

		setSoundType(SoundType.WOOD);
		setHardness(2.0F);
		setResistance(5.0F);
		setHarvestLevel("axe", 0);

		//OreDictionaryHelper.register(this, variant.toString());
		//OreDictionaryHelper.register(this, variant.toString(), type.toString());
	}

	@Nullable
	@Override
	public ItemBlock getItemBlock() {
		return new ItemBlockBase(this);
	}

	@SideOnly(Side.CLIENT)
	@NotNull
	@Override
	public BlockRenderLayer getRenderLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public boolean onBlockActivated(World worldIn, @NotNull BlockPos pos, @NotNull IBlockState state, @Nullable EntityPlayer playerIn, @NotNull EnumHand hand, @NotNull EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote && playerIn != null) {
			playerIn.displayGui(new InterfaceCraftingTable(this, worldIn, pos));
			playerIn.addStat(StatList.CRAFTING_TABLE_INTERACTION);
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onModelRegister() {
		ModelManager.registerBlockInventoryModel(this, getResourceLocation());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onStateMapperRegister() {
		ModelManager.registerStateMapper(this, new CustomStateMap.Builder().customResource(getResourceLocation()).build());
	}

	@Override
	public IBlockColor getColorHandler() {
		return (s, w, p, i) -> this.getType().getColor();
	}

	@Override
	public IItemColor getItemColorHandler() {
		return (s, i) -> this.getType().getColor();
	}

	@SuppressWarnings("WeakerAccess")

	@MethodsReturnNonnullByDefault
	public static class InterfaceCraftingTable implements IInteractionObject {

		//todo: replace with proper workbench mechanics + normal forge gui code
		private final BlockWoodWorkbench workbench;
		private final World world;
		private final BlockPos position;

		public InterfaceCraftingTable(BlockWoodWorkbench workbench, World worldIn, BlockPos pos) {
			this.workbench = workbench;
			this.world = worldIn;
			this.position = pos;
		}

		/**
		 * Get the name of this object. For players this returns their username
		 */
		@Override
		public String getName() {
			return "crafting_table";
		}

		/**
		 * Returns true if this thing is named
		 */
		@Override
		public boolean hasCustomName() {
			return false;
		}

		/**
		 * Get the formatted ChatComponent that will be used for the sender's username in chat
		 */
		@Override
		public ITextComponent getDisplayName() {
			return new TextComponentTranslation(workbench.getTranslationKey() + ".name");
		}

		@Override
		public Container createContainer(@NotNull InventoryPlayer inv, @NotNull EntityPlayer player) {
			return new ContainerWoodWorkbench(inv, world, position, workbench);
		}

		@Override
		public String getGuiID() {
			return "minecraft:crafting_table";
		}
	}

}
