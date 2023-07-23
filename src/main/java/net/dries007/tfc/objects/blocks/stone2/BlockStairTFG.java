package net.dries007.tfc.objects.blocks.stone2;

import net.dries007.tfc.api.types2.BlockType;
import net.dries007.tfc.api.types2.BlockVariant;
import net.dries007.tfc.api.util.IStoneTypeBlock;
import net.dries007.tfc.api.types2.StoneType;
import net.dries007.tfc.api.util.Triple;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

import static gregtech.common.items.ToolItems.HARD_HAMMER;
import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types2.BlockVariant.COBBLE;
import static net.dries007.tfc.objects.blocks.stone2.BlockOrdinaryTFG.BLOCK_MAP;
import static net.dries007.tfc.objects.blocks.stone2.BlockOrdinaryTFG.getBlockFromMap;

public class BlockStairTFG extends BlockStairs implements IStoneTypeBlock {

	private final BlockType blockType;
	private final BlockVariant blockVariant;
	private final StoneType stoneType;
	private final ResourceLocation modelLocation;

	public BlockStairTFG(BlockType blockType, BlockVariant blockVariant, StoneType stoneType) {
		super(Blocks.BRICK_STAIRS.getDefaultState());

		if (BLOCK_MAP.put(new Triple<>(blockType, blockVariant, stoneType), this) != null)
			throw new RuntimeException("Duplicate registry entry detected for block: " + blockVariant + " " + stoneType);

		this.blockType = blockType;
		this.blockVariant = blockVariant;
		this.stoneType = stoneType;
		this.modelLocation = new ResourceLocation(MOD_ID, blockType + "/" + blockVariant);
		useNeighborBrightness = true;

		String blockRegistryName = String.format("%s/%s/%s", blockType, blockVariant, stoneType);
		this.setCreativeTab(CreativeTabsTFC.CT_ROCK_BLOCKS);
		this.setSoundType(SoundType.STONE);
		this.setHardness(getFinalHardness());
		this.setResistance(stoneType.getResistance());
		this.setHarvestLevel("pickaxe", blockVariant.getHarvestLevel());
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
	public boolean removedByPlayer(@Nonnull IBlockState state, World world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player, boolean willHarvest) {
		if (!world.isRemote) {
			ItemStack heldItemStack = player.getHeldItem(EnumHand.MAIN_HAND);
			Item heldItem = heldItemStack.getItem();

			// Проверяем, можно ли игроку собрать блок с использованием текущего инструмента
			if (player.canHarvestBlock(state)) {
				// Проверяем, является ли удерживаемый предмет инструментом с классом инструмента pickaxe и кроме инструмента HARD_HAMMER
				if ((heldItem.getToolClasses(heldItemStack).contains("pickaxe")) && !(heldItem == HARD_HAMMER.get())) {
					switch (blockVariant) {
						case RAW:
						case SMOOTH:
						case COBBLE:
							//Block.spawnAsEntity(world, pos, new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get(LOOSE.getName() + "/" + stoneType.getName()), new Random().nextInt(2) + 3));
							break;
						case BRICK:
							//Block.spawnAsEntity(world, pos, new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get(LOOSE.getName() + "/" + stoneType.getName()), new Random().nextInt(2) + 3));
							Block.spawnAsEntity(world, pos, new ItemStack(Items.CLAY_BALL, new Random().nextInt(2))); //TODO кусочек цемента?
							break;
					}
				} else if (heldItem == HARD_HAMMER.get()) {
					switch (blockVariant) {
						case RAW:
						case SMOOTH:
							Block.spawnAsEntity(world, pos, new ItemStack(getBlockFromMap(blockType, COBBLE, stoneType), 1));
							break;
						case COBBLE:
							//Block.spawnAsEntity(world, pos, new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get(LOOSE.getName() + "/" + stoneType.getName()), new Random().nextInt(2) + 3));
							break;
						case BRICK:
							//Block.spawnAsEntity(world, pos, new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get("brick/" + stoneType.getName()), new Random().nextInt(2) + 3));
							Block.spawnAsEntity(world, pos, new ItemStack(Items.CLAY_BALL, new Random().nextInt(2))); //TODO кусочек цемента?
							break;
					}
				}
			}
		}
		return super.removedByPlayer(state, world, pos, player, willHarvest);
	}

	@Override
	public void getDrops(@Nonnull NonNullList<ItemStack> drops, @Nonnull IBlockAccess world, @Nonnull BlockPos pos, @Nonnull IBlockState state, int fortune) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onModelRegister() {
		ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
			@Nonnull
			protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
				return new ModelResourceLocation(modelLocation,
						"facing=" + state.getValue(FACING) + "," +
								"half=" + state.getValue(HALF) + "," +
								"shape=" + state.getValue(SHAPE) + "," +
								"stonetype=" + stoneType.getName());
			}
		});

		for (IBlockState state : this.getBlockState().getValidStates()) {
			ModelLoader.setCustomModelResourceLocation(
					Item.getItemFromBlock(this),
					this.getMetaFromState(state),
					new ModelResourceLocation(modelLocation,
							"facing=east," +
									"half=bottom," +
									"shape=straight," +
									"stonetype=" + stoneType.getName()));
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);

		tooltip.add(new TextComponentTranslation("stonecategory.name").getFormattedText() + ": " + stoneType.getStoneCategory().getLocalizedName());
	}
}

