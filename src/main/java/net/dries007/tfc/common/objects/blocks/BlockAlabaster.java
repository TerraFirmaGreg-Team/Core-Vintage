package net.dries007.tfc.common.objects.blocks;

import net.dries007.tfc.api.capability.size.IItemSize;
import net.dries007.tfc.api.capability.size.Size;
import net.dries007.tfc.api.capability.size.Weight;
import net.dries007.tfc.api.types.rock.variant.RockBlockVariant;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.IItemProvider;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.items.itemblocks.ItemBlockTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

@ParametersAreNonnullByDefault
public class BlockAlabaster extends Block implements IItemSize, IItemProvider, IHasModel {

    private final RockBlockVariant rockBlockVariant;
    private final EnumDyeColor dyeColor;
    private final ResourceLocation modelLocation;

    public BlockAlabaster(RockBlockVariant rockBlockVariant, EnumDyeColor dyeColor) {
        super(Material.ROCK, MapColor.getBlockColor(dyeColor));

        this.rockBlockVariant = rockBlockVariant;
        this.dyeColor = dyeColor;
        this.modelLocation = new ResourceLocation(MOD_ID, "rock/alabaster/color/" + rockBlockVariant);

        setCreativeTab(CreativeTabsTFC.DECORATIONS);
        setSoundType(SoundType.STONE);
        setHardness(1.0F);

        var blockRegistryName = String.format("alabaster/%s/%s", rockBlockVariant, dyeColor.getName());
        setRegistryName(MOD_ID, blockRegistryName);
        setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));

        OreDictionaryHelper.register(this, "alabaster");
        OreDictionaryHelper.register(this, "alabaster", rockBlockVariant.toString());
        OreDictionaryHelper.register(this, "alabaster", rockBlockVariant.toString(), dyeColor.getName());
    }

    public BlockAlabaster(RockBlockVariant rockBlockVariant) {
        super(Material.ROCK, MapColor.SNOW);

        this.rockBlockVariant = rockBlockVariant;
        this.dyeColor = EnumDyeColor.WHITE;
        this.modelLocation = new ResourceLocation(MOD_ID, "rock/alabaster/" + rockBlockVariant);

        var blockRegistryName = String.format("alabaster/%s/plain", rockBlockVariant);
        setRegistryName(MOD_ID, blockRegistryName);
        setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));

        OreDictionaryHelper.register(this, "alabaster");
        OreDictionaryHelper.register(this, "alabaster", rockBlockVariant.toString());
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

    @Nullable
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
                return new ModelResourceLocation(modelLocation, "color=" + dyeColor.getName());
            }
        });

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this),
                this.getMetaFromState(this.getBlockState().getBaseState()),
                new ModelResourceLocation(modelLocation, "color=" + dyeColor.getName()));
    }
}
