package net.dries007.tfc.common.objects.blocks.rock;

import net.dries007.tfc.api.types.rock.IRockBlock;
import net.dries007.tfc.api.types.rock.type.RockType;
import net.dries007.tfc.api.types.rock.variant.RockBlockVariant;
import net.dries007.tfc.client.util.CustomStateMap;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.items.itemblocks.ItemBlockTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.BlockButtonStone;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockRockButton extends BlockButtonStone implements IRockBlock {
    private final RockBlockVariant variant;
    private final RockType type;

    public BlockRockButton(RockBlockVariant variant, RockType type) {
        this.variant = variant;
        this.type = type;

        setSoundType(SoundType.STONE);
        setCreativeTab(CreativeTabsTFC.ROCK);
        setHardness(0.5f);
        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());

        OreDictionaryHelper.register(this, variant.toString(), type.toString());
    }

    @Nonnull
    @Override
    public RockBlockVariant getBlockVariant() {
        return variant;
    }

    @Nonnull
    @Override
    public RockType getType() {
        return type;
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTFC(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
//        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
//            @Nonnull
//            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
//                return new ModelResourceLocation(getResourceLocation(),
//                        "facing=" + state.getValue(FACING) + "," +
//                                "powered=" + state.getValue(POWERED) + "," +
//                                "rocktype=" + getType());
//            }
//        });
        ModelLoader.setCustomStateMapper(this,
                new CustomStateMap.Builder()
                        .customPath(getResourceLocation())
                        .customVariant("rocktype=" + getType())
                        .build());

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getResourceLocation(), "inventory=" + getType()));
    }

    @Nonnull
    @Override
    public IBlockState getStateForPlacement(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull EnumFacing facing, float hitX, float hitY,
                                            float hitZ, int meta, @Nonnull EntityLivingBase placer) {
        IBlockState state = getStateFromMeta(meta);
        return canPlaceBlock(worldIn, pos, facing) ? state.withProperty(FACING, facing)
                : state.withProperty(FACING, EnumFacing.DOWN);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TextComponentTranslation("rockcategory.name").getFormattedText() + ": " + getType().getCategory().getLocalizedName());
    }
}
