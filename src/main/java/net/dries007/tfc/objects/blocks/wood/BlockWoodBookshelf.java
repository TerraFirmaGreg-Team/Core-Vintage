package net.dries007.tfc.objects.blocks.wood;

import net.dries007.tfc.api.types.wood.IWoodBlock;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.WoodBlockVariant;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.dries007.tfc.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.BlockBookshelf;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public class BlockWoodBookshelf extends BlockBookshelf implements IWoodBlock {
    private final WoodBlockVariant woodVariant;
    private final WoodType woodType;

    public BlockWoodBookshelf(WoodBlockVariant woodVariant, WoodType woodType) {
        this.woodVariant = woodVariant;
        this.woodType = woodType;

        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());
        setCreativeTab(CreativeTabsTFC.WOOD);
        setSoundType(SoundType.WOOD);
        setHardness(2.0F);
        setResistance(5.0F);
        setHarvestLevel("axe", 0);

        OreDictionaryHelper.register(this, "bookshelf");
        OreDictionaryHelper.register(this, "bookshelf", woodType.toString());
        Blocks.FIRE.setFireInfo(this, 30, 20);
    }

    @Override
    public WoodBlockVariant getWoodBlockVariant() {
        return woodVariant;
    }

    @Override
    public WoodType getWoodType() {
        return woodType;
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTFC(this);
    }

    @SideOnly(Side.CLIENT)
    @Override
    @Nonnull
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public float getEnchantPowerBonus(@Nonnull World world, @Nonnull BlockPos pos) {
        return 1.0F;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
            return new ModelResourceLocation(getResourceLocation(), getPropertyString(state.getProperties()));
            }
        });

        for (IBlockState state : this.getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), getMetaFromState(state), new ModelResourceLocation(getResourceLocation(), "normal"));
        }
    }
}
