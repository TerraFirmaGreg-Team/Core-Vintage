package net.dries007.tfc.module.rock.common.blocks;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.module.rock.api.type.RockType;
import net.dries007.tfc.module.rock.api.variant.block.IRockBlock;
import net.dries007.tfc.module.rock.api.variant.block.RockBlockVariant;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.blocks.TFCBlock;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class BlockRock extends TFCBlock implements IRockBlock, IItemSize {

    private final RockBlockVariant variant;
    private final RockType type;

    public BlockRock(Material material, RockBlockVariant variant, RockType type) {
        super(material);

        this.variant = variant;
        this.type = type;

        setCreativeTab(CreativeTabsTFC.ROCK);
        setSoundType(SoundType.STONE);
        setHardness(getFinalHardness());
        setHarvestLevel("pickaxe", 0);
        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());

        if (getItemBlock() != null) {
            OreDictionaryHelper.register(this, variant.toString());
            OreDictionaryHelper.register(this, variant.toString(), type.toString());
        }

    }

    public BlockRock(RockBlockVariant variant, RockType type) {
        this(Material.ROCK, variant, type);
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

    @Nonnull
    @Override
    public Size getSize(@Nonnull ItemStack stack) {
        return Size.SMALL;
    }

    @Nonnull
    @Override
    public Weight getWeight(@Nonnull ItemStack stack) {
        return Weight.LIGHT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(getResourceLocation(), "rocktype=" + getType());
            }
        });

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getResourceLocation(), "rocktype=" + getType()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        tooltip.add(new TextComponentTranslation("rockcategory.name").getFormattedText() + ": " + getType().getCategory().getLocalizedName());
    }
}
