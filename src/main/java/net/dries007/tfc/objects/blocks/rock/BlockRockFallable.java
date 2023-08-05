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
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.api.types.rock.RockVariant.GRAVEL;
import static net.dries007.tfc.api.types.rock.RockVariant.SAND;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockRockFallable extends Block implements IRockBlock, IItemSize {

    private final RockVariant rockVariant;
    private final Rock rock;
    private final ResourceLocation modelLocation;

    public BlockRockFallable(RockVariant rockVariant, Rock rock) {
        super(Material.SAND);

        if (rockVariant.canFall()) {
            FallingBlockManager.registerFallable(this, rockVariant.getFallingSpecification());
        }

        this.rockVariant = rockVariant;
        this.rock = rock;
        this.modelLocation = new ResourceLocation(MOD_ID, "rock/" + rockVariant);

        var blockRegistryName = String.format("rock/%s/%s", rockVariant, rock);
        if (rockVariant == SAND)
            this.setSoundType(SoundType.SAND);
        else
            this.setSoundType(SoundType.GROUND);
        this.setCreativeTab(CreativeTabsTFC.ROCK);
        this.setHardness(getFinalHardness() * 0.2F);
        this.setHarvestLevel("shovel", 0);
        this.setRegistryName(MOD_ID, blockRegistryName);
        this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));

        //OreDictionaryModule.register(this, rockVariant.getName(), rockVariant.getName() + WordUtils.capitalize(rockType.getName()));
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
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if (rockVariant == GRAVEL) {
            if (fortune > 3)
                fortune = 3;

            if (rand.nextInt(10 - fortune * 3) == 0)
                return Items.FLINT;
        }
        return super.getItemDropped(state, rand, fortune);
    }

    @SideOnly(Side.CLIENT)
    @Override
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

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        if (this.rockVariant.canFall() && rand.nextInt(16) == 0 && FallingBlockManager.shouldFall(world, pos, pos, state, false)) {
            double d0 = (float) pos.getX() + rand.nextFloat();
            double d1 = (double) pos.getY() - 0.05D;
            double d2 = (float) pos.getZ() + rand.nextFloat();
            world.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, Block.getStateId(state));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TextComponentTranslation("stonecategory.name").getFormattedText() + ": " + getRock().getRockCategory().getLocalizedName());
    }
}
