package net.dries007.tfc.objects.blocks.rock;

import net.dries007.tfc.api.types.rock.block.type.RockBlockType;
import net.dries007.tfc.api.types.rock.block.variant.RockBlockVariant;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.util.IRockBlock;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
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

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public class BlockRockWall extends BlockWall implements IRockBlock {

    private final RockBlockType rockBlockType;
    private final RockBlockVariant rockBlockVariant;
    private final RockType rockType;

    public BlockRockWall(RockBlockType rockBlockType, RockBlockVariant rockBlockVariant, RockType rockType) {
        super(Blocks.COBBLESTONE);

        this.rockBlockType = rockBlockType;
        this.rockBlockVariant = rockBlockVariant;
        this.rockType = rockType;


        this.setRegistryName(MOD_ID, getRegistryString());
        this.setTranslationKey(getTranslationString());
        this.setCreativeTab(CreativeTabsTFC.ROCK);

        this.setSoundType(SoundType.STONE);
        this.setHardness(getFinalHardness());
        this.setHarvestLevel("pickaxe", 0);
    }

    @Nonnull
    @Override
    public RockBlockType getRockBlockType() {
        return rockBlockType;
    }

    @Nullable
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

        for (IBlockState state : this.getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(
                    Item.getItemFromBlock(this),
                    this.getMetaFromState(state),
                    new ModelResourceLocation(getResourceLocation(),
                            "inventory=" + rockType.toString()));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TextComponentTranslation("stonecategory.name").getFormattedText() + ": " + rockType.getRockCategory().getLocalizedName());
    }
}
