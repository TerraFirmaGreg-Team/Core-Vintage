package net.dries007.tfc.common.objects.blocks.rock;

import net.dries007.tfc.api.types.rock.IRockBlock;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.variant.RockBlockVariant;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.BlockWall;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockRockWall extends BlockWall implements IRockBlock {

    private final RockBlockVariant rockBlockVariant;
    private final RockType rockType;

    public BlockRockWall(RockBlockVariant rockBlockVariant, RockType rockType) {
        super(Blocks.COBBLESTONE);

        this.rockBlockVariant = rockBlockVariant;
        this.rockType = rockType;


        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());
        setCreativeTab(CreativeTabsTFC.ROCK);

        setSoundType(SoundType.STONE);
        setHardness(getFinalHardness());
        setHarvestLevel("pickaxe", 0);

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
    public void getSubBlocks(@Nonnull CreativeTabs itemIn, @Nonnull NonNullList<ItemStack> items) {
        items.add(new ItemStack(this));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
                    @Nonnull
                    protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                        return new ModelResourceLocation(getResourceLocation(),
                                "east=" + state.getValue(EAST) + "," +
                                        "north=" + state.getValue(NORTH) + "," +
                                        "rocktype=" + rockType.toString() + "," +
                                        "south=" + state.getValue(SOUTH) + "," +
                                        "up=" + state.getValue(UP) + "," +
                                        "west=" + state.getValue(WEST));
                    }
                }
        );

        for (IBlockState state : getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(
                    Item.getItemFromBlock(this),
                    getMetaFromState(state),
                    new ModelResourceLocation(getResourceLocation(),
                            "inventory=" + rockType.toString()));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TextComponentTranslation("rockcategory.name").getFormattedText() + ": " + rockType.getRockCategory().getLocalizedName());
    }
}
