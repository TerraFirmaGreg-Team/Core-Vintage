package net.dries007.tfc.objects.blocks.rock;

import gregtech.common.items.ToolItems;
import mcp.MethodsReturnNonnullByDefault;
import net.dries007.tfc.ConfigTFC;
import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.registries.TFCStorage;
import net.dries007.tfc.api.types.rock.Rock;
import net.dries007.tfc.api.types.rock.RockVariant;
import net.dries007.tfc.api.types.rock.util.IRockBlock;
import net.dries007.tfc.api.util.FallingBlockManager;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.util.GemsFromRawRocks;
import net.dries007.tfc.util.Helpers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
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
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.rock.RockType.ORDINARY;
import static net.dries007.tfc.api.types.rock.RockVariant.ANVIL;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockRockRaw extends Block implements IRockBlock, IItemSize {
    /* This is for the not-surrounded-on-all-sides-pop-off mechanic. It's a dirty fix to the stack overflow caused by placement during water / lava collisions in world gen */
    public static final PropertyBool CAN_FALL = PropertyBool.create("can_fall");
    private final Rock rock;
    private final RockVariant rockVariant;
    private final ResourceLocation modelLocation;

    public BlockRockRaw(RockVariant rockVariant, Rock rock) {
        super(Material.ROCK);

        this.rockVariant = rockVariant;
        this.rock = rock;
        this.modelLocation = new ResourceLocation(MOD_ID, "rock/" + rockVariant);

        FallingBlockManager.Specification spec = new FallingBlockManager.Specification(rockVariant.getFallingSpecification()); // Copy as each raw stone has an unique resultingState
        FallingBlockManager.registerFallable(this, spec);

        var blockRegistryName = String.format("rock/%s/%s", rockVariant, rock);
        this.setCreativeTab(CreativeTabsTFC.ROCK);
        this.setSoundType(SoundType.STONE);
        this.setHardness(getFinalHardness());
        this.setHarvestLevel("pickaxe", 0);
        this.setRegistryName(MOD_ID, blockRegistryName);
        this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
        this.setDefaultState(getBlockState().getBaseState().withProperty(CAN_FALL, true));
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
                var anvil = TFCStorage.getRockBlock(ORDINARY, ANVIL, rock);
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

        if (RANDOM.nextDouble() < ConfigTFC.General.MISC.stoneGemDropChance) {
            drops.add(GemsFromRawRocks.getRandomGem());
        }
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        return 1 + random.nextInt(3);
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
