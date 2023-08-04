package net.dries007.tfc.objects.blocks.rock;

import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.rock.Rock;
import net.dries007.tfc.api.types.rock.RockVariant;
import net.dries007.tfc.api.types.rock.util.IRockBlock;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.util.ITooltipFlag;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockRock extends Block implements IRockBlock, IItemSize {

    private final Rock rock;
    private final RockVariant rockVariant;
    private final ResourceLocation modelLocation;

    public BlockRock(RockVariant rockVariant, Rock rock) {
        super(Material.ROCK);

        if (rockVariant.canFall())
            FallingBlockManager.registerFallable(this, rockVariant.getFallingSpecification());

        this.rockVariant = rockVariant;
        this.rock = rock;
        this.modelLocation = new ResourceLocation(MOD_ID, "rock/" + rockVariant);

        var blockRegistryName = String.format("rock/%s/%s", rockVariant, rock);
        this.setCreativeTab(CreativeTabsTFC.ROCK_STUFFS);
        this.setSoundType(SoundType.STONE);
        this.setHardness(getFinalHardness());
        this.setHarvestLevel("pickaxe", 0);
        this.setRegistryName(MOD_ID, blockRegistryName);
        this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));

        //OreDictionaryModule.register(this, blockVariant.getName(), blockVariant.getName() + WordUtils.capitalize(stoneType.getName()));
    }

    @Nonnull
    @Override
    public RockVariant getRockVariant() {
        return rockVariant;
    }

    @Nonnull
    @Override
    public Rock getRock() {
        return rock;
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTFC(this);
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
    public int damageDropped(IBlockState state) {
        return getMetaFromState(state);
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return super.quantityDropped(state, fortune, random);
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
//				if ((heldItem.getToolClasses(heldItemStack).contains("pickaxe")) && !(heldItem instanceof ItemRockHammer)) {
//					switch (rockVariant) {
//						case RAW:
//						case SMOOTH:
//							//Block.spawnAsEntity(world, pos, new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get(LOOSE.getName() + "/" + stoneType.getName()), new Random().nextInt(3) + 2));
//							break;
//						case COBBLE:
//							//Block.spawnAsEntity(world, pos, new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get(LOOSE.getName() + "/" + stoneType.getName()), new Random().nextInt(3) + 4));
//							break;
//						case BRICK:
//							//Block.spawnAsEntity(world, pos, new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get(LOOSE.getName() + "/" + stoneType.getName()), new Random().nextInt(2) + 4));
//							break;
//					}
//				} else if (heldItem instanceof ItemRockHammer) {
//					switch (rockVariant) {
//						case RAW:
//						case SMOOTH:
//							//Block.spawnAsEntity(world, pos, new ItemStack(getBlockSoilMap(blockType, COBBLE, stoneType), 1));
//							break;
//						case COBBLE:
//							//Block.spawnAsEntity(world, pos, new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get(LOOSE.getName() + "/" + stoneType.getName()), new Random().nextInt(3) + 4));
//							Block.spawnAsEntity(world, pos, new ItemStack(Items.CLAY_BALL, new Random().nextInt(2))); //TODO кусочек глины?
//							break;
//						case BRICK:
//							//Block.spawnAsEntity(world, pos, new ItemStack(StoneTypeItems.ITEM_STONE_MAP.get("brick/" + stoneType.getName()), new Random().nextInt(3) + 3));
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
                return new ModelResourceLocation(modelLocation, "rocktype=" + rock.getName());
            }
        });


        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this),
                this.getMetaFromState(this.getBlockState().getBaseState()),
                new ModelResourceLocation(modelLocation, "rocktype=" + rock.getName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TextComponentTranslation("stonecategory.name").getFormattedText() + ": " + getRock().getRockCategory().getLocalizedName());
    }
}
