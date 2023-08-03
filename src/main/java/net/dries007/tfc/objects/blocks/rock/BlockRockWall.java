package net.dries007.tfc.objects.blocks.rock;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types2.rock.RockBlockType.ORDINARY;

import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types2.rock.RockBlockType;
import net.dries007.tfc.api.types2.rock.RockType;
import net.dries007.tfc.api.types2.rock.RockVariant;
import net.dries007.tfc.api.types2.rock.util.IRockBlock;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRockWall extends BlockWall implements IRockBlock {
    private final RockVariant rockVariant;
    private final RockType rockType;
    private final ResourceLocation modelLocation;

    public BlockRockWall(RockBlockType rockBlockType, RockVariant rockVariant, RockType rockType) {
        super(TFCStorage.getRockBlock(ORDINARY, rockVariant, rockType));

        this.rockVariant = rockVariant;
        this.rockType = rockType;
        this.modelLocation = new ResourceLocation(MOD_ID, "rock/" + rockBlockType + "/" + rockVariant);

        var blockRegistryName = String.format("rock/%s/%s/%s", rockBlockType, rockVariant, rockType);
        this.setSoundType(SoundType.STONE);
        this.setCreativeTab(CreativeTabsTFC.ROCK_STUFFS);
        this.setHardness(getFinalHardness());
        this.setHarvestLevel("pickaxe", 0);
        this.setRegistryName(MOD_ID, blockRegistryName);
        this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));

        //OreDictionaryHelper.register(this, rockBlockType.getName(), rockVariant.getName(), rockVariant.getName() + WordUtils.capitalize(rockType.getName()));
    }

    @Nonnull
    @Override
    public RockVariant getRockVariant() {
        return rockVariant;
    }

    @Nonnull
    @Override
    public RockType getRockType() {
        return rockType;
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTFC(this);
    }

    @Override
    public void getSubBlocks(@Nonnull CreativeTabs itemIn, @Nonnull NonNullList<ItemStack> items) {
        items.add(new ItemStack(this));
    }

//	@Override
//	public boolean removedByPlayer(@Nonnull IBlockState state, World world, @Nonnull BlockPos pos, @Nonnull EntityPlayer player, boolean willHarvest) {
//		if (!world.isRemote) {
//			ItemStack heldItemStack = player.getHeldItem(EnumHand.MAIN_HAND);
//			Item heldItem = heldItemStack.getItem();
//
//			// Проверяем, можно ли игроку собрать блок с использованием текущего инструмента
//			if (player.canHarvestBlock(state)) {
//				// Проверяем, является ли удерживаемый предмет инструментом с классом инструмента pickaxe и кроме инструмента HARD_HAMMER
//				if ((heldItem.getToolClasses(heldItemStack).contains("pickaxe")) && !(heldItem == HARD_HAMMER.get())) {
//					switch (rockVariant) {
//						case RAW:
//						case SMOOTH:
//						case COBBLE:
//							//Block.spawnAsEntity(world, pos, new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get(LOOSE.getName() + "/" + rockType.getName()), new Random().nextInt(2) + 2));
//							break;
//						case BRICK:
//							//Block.spawnAsEntity(world, pos, new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get(LOOSE.getName() + "/" + rockType.getName()), new Random().nextInt(2) + 2));
//							Block.spawnAsEntity(world, pos, new ItemStack(Items.CLAY_BALL, new Random().nextInt(2))); //TODO кусочек цемента?
//							break;
//					}
//				} else if (heldItem == HARD_HAMMER.get()) {
//					switch (rockVariant) {
//						case RAW:
//						case SMOOTH:
//							Block.spawnAsEntity(world, pos, new ItemStack(getBlockRockMap(ORD, COBBLE, rockType), 1));
//							break;
//						case COBBLE:
//							//Block.spawnAsEntity(world, pos, new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get(LOOSE.getName() + "/" + rockType.getName()), new Random().nextInt(2) + 2));
//							break;
//						case BRICK:
//							//Block.spawnAsEntity(world, pos, new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get(LOOSE.getName() + "/" + rockType.getName()), new Random().nextInt(2) + 2));
//							Block.spawnAsEntity(world, pos, new ItemStack(Items.CLAY_BALL, new Random().nextInt(2))); //TODO кусочек цемента?
//							break;
//					}
//				}
//			}
//		}
//		return super.removedByPlayer(state, world, pos, player, willHarvest);
//	}

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
                                "east=" + state.getValue(EAST) + "," +
                                        "north=" + state.getValue(NORTH) + "," +
                                        "rocktype=" + rockType.getName() + "," +
                                        "south=" + state.getValue(SOUTH) + "," +
                                        "up=" + state.getValue(UP) + "," +
                                        "west=" + state.getValue(WEST));
                    }
                }
        );

        for (IBlockState state : this.getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(
                    Item.getItemFromBlock(this),
                    this.getMetaFromState(state),
                    new ModelResourceLocation(modelLocation,
                            "inventory=" + rockType.getName()));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TextComponentTranslation("stonecategory.name").getFormattedText() + ": " + getRockType().getRockCategory().getLocalizedName());
    }
}
