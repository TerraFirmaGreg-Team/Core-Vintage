package net.dries007.tfc.module.rock.objects.blocks;

import net.dries007.tfc.Tags;
import net.dries007.tfc.module.core.ModuleCore;
import net.dries007.tfc.module.core.api.capability.size.IItemSizeAndWeight;
import net.dries007.tfc.module.core.api.capability.size.Size;
import net.dries007.tfc.module.core.api.capability.size.Weight;
import net.dries007.tfc.module.core.api.objects.block.BlockBase;
import net.dries007.tfc.module.core.api.util.EnumColor;
import net.dries007.tfc.module.core.api.util.Helpers;
import net.dries007.tfc.module.core.api.util.IHasModel;
import net.dries007.tfc.module.rock.api.types.variant.block.RockBlockVariant;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;


@ParametersAreNonnullByDefault
public class BlockAlabaster extends BlockBase implements IItemSizeAndWeight, IHasModel {

    private final RockBlockVariant variant;
    private final EnumColor color;
    private final ResourceLocation modelLocation;

    public BlockAlabaster(RockBlockVariant variant, EnumColor color) {
        super(Material.ROCK);

        this.variant = variant;
        this.color = color;
        this.modelLocation = Helpers.getID("rock/alabaster/color/" + variant);

        setCreativeTab(ModuleCore.MISC_TAB);
        setSoundType(SoundType.STONE);
        setHardness(1.0F);

        var blockRegistryName = String.format("alabaster/%s/%s", variant, color.getName());
        setRegistryName(Tags.MOD_ID, blockRegistryName);
        setTranslationKey(Tags.MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));

        OreDictionaryHelper.register(this, "alabaster");
        OreDictionaryHelper.register(this, "alabaster", variant.toString());
        OreDictionaryHelper.register(this, "alabaster", variant.toString(), color.getName());
    }

    @Nonnull
    @Override
    public Size getSize(ItemStack stack) {
        return Size.SMALL;
    }

    @Nonnull
    @Override
    public Weight getWeight(ItemStack stack) {
        return Weight.LIGHT;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(modelLocation,
                        "color=" + color.getName());
            }
        });

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(modelLocation,
                        "color=" + color.getName()));
    }
}
