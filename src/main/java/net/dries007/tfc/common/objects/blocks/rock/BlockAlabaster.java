package net.dries007.tfc.common.objects.blocks.rock;

import net.dries007.tfc.TerraFirmaCraft;
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

    private final RockBlockVariant variant;
    private final EnumDyeColor dyeColor;
    private final ResourceLocation modelLocation;

    public BlockAlabaster(RockBlockVariant variant, EnumDyeColor dyeColor) {
        super(Material.ROCK, MapColor.getBlockColor(dyeColor));

        this.variant = variant;
        this.dyeColor = dyeColor;
        this.modelLocation = TerraFirmaCraft.identifier("rock/alabaster/color/" + variant);

        setCreativeTab(CreativeTabsTFC.ROCK);
        setSoundType(SoundType.STONE);
        setHardness(1.0F);

        var blockRegistryName = String.format("alabaster/%s/%s", variant, dyeColor.getName());
        setRegistryName(MOD_ID, blockRegistryName);
        setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));

        OreDictionaryHelper.register(this, "alabaster");
        OreDictionaryHelper.register(this, "alabaster", variant.toString());
        OreDictionaryHelper.register(this, "alabaster", variant.toString(), dyeColor.getName());
    }

    public BlockAlabaster(RockBlockVariant variant) {
        super(Material.ROCK, MapColor.SNOW);

        this.variant = variant;
        this.dyeColor = EnumDyeColor.WHITE;
        this.modelLocation = TerraFirmaCraft.identifier("rock/alabaster/" + variant);

        var blockRegistryName = String.format("alabaster/%s/plain", variant);
        setRegistryName(MOD_ID, blockRegistryName);
        setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));

        OreDictionaryHelper.register(this, "alabaster");
        OreDictionaryHelper.register(this, "alabaster", variant.toString());
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
                return new ModelResourceLocation(modelLocation,
                        "color=" + dyeColor.getName());
            }
        });

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(modelLocation,
                        "color=" + dyeColor.getName()));
    }
}
