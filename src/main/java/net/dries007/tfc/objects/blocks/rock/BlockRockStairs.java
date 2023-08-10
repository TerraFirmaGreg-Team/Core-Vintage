package net.dries007.tfc.objects.blocks.rock;

import net.dries007.tfc.api.types.rock.IRockBlock;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.variant.RockBlockVariant;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockRockStairs extends BlockStairs implements IRockBlock {

    private final RockBlockVariant rockBlockVariant;
    private final RockType rockType;

    public BlockRockStairs(RockBlockVariant rockBlockVariant, RockType rockType) {
        super(Blocks.COBBLESTONE.getDefaultState());

        this.rockBlockVariant = rockBlockVariant;
        this.rockType = rockType;

        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());
        setCreativeTab(CreativeTabsTFC.ROCK);
        setSoundType(SoundType.STONE);
        setHardness(getFinalHardness());
        setHarvestLevel("pickaxe", 0);
        useNeighborBrightness = true;

        OreDictionaryHelper.register(this, rockBlockVariant.toString(), rockType.toString());
    }

    @Nonnull
    @Override
    public RockBlockVariant getRockBlockVariant() {
        return rockBlockVariant;
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
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(getResourceLocation(),
                        "facing=" + state.getValue(FACING) + "," +
                                "half=" + state.getValue(HALF) + "," +
                                "rocktype=" + rockType.toString() + "," +
                                "shape=" + state.getValue(SHAPE));
            }
        });

        for (IBlockState state : getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(
                    Item.getItemFromBlock(this),
                    getMetaFromState(state),
                    new ModelResourceLocation(getResourceLocation(),
                            "facing=east," +
                                    "half=bottom," +
                                    "rocktype=" + rockType.toString() + "," +
                                    "shape=straight"));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TextComponentTranslation("rockcategory.name").getFormattedText() + ": " + rockType.getRockCategory().getLocalizedName());
    }


}
