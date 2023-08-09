package net.dries007.tfc.objects.blocks.rock;

import net.dries007.tfc.api.types.rock.IRockBlock;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.variant.RockBlockVariant;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.util.ITooltipFlag;
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

public class BlockRockPressurePlate extends BlockPressurePlate implements IRockBlock {

    private final RockBlockVariant rockBlockVariant;
    private final RockType rockType;

    public BlockRockPressurePlate(RockBlockVariant rockBlockVariant, RockType rockType) {
        super(Material.ROCK, Sensitivity.MOBS);

        this.rockBlockVariant = rockBlockVariant;
        this.rockType = rockType;

        setCreativeTab(CreativeTabsTFC.ROCK);
        setSoundType(SoundType.STONE);
        setHardness(0.5f);
        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());

        OreDictionaryHelper.register(this, rockBlockVariant.name(), rockType.name());
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
                        "powered=" + state.getValue(POWERED) + "," +
                                "rocktype=" + rockType.toString());
            }
        });

        for (IBlockState state : getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(
                    Item.getItemFromBlock(this),
                    getMetaFromState(state),
                    new ModelResourceLocation(getResourceLocation(), "inventory=" + rockType.toString()));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TextComponentTranslation("rockcategory.name").getFormattedText() + ": " + rockType.getRockCategory().getLocalizedName());
    }
}
