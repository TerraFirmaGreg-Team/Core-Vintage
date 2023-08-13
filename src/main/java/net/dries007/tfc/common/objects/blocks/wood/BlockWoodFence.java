package net.dries007.tfc.common.objects.blocks.wood;

import net.dries007.tfc.api.types.wood.IWoodBlock;
import net.dries007.tfc.api.types.wood.type.WoodType;
import net.dries007.tfc.api.types.wood.variant.WoodBlockVariant;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.items.itemblock.ItemBlockTFC;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockWoodFence extends BlockFence implements IWoodBlock {
    private final WoodBlockVariant woodBlockVariant;
    private final WoodType woodType;

    public BlockWoodFence(WoodBlockVariant woodBlockVariant, WoodType woodType) {
        super(Material.WOOD, Material.WOOD.getMaterialMapColor());

        this.woodBlockVariant = woodBlockVariant;
        this.woodType = woodType;

        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());
        setCreativeTab(CreativeTabsTFC.WOOD);
        setHarvestLevel("axe", 0);
        setHardness(2.0F);
        setResistance(15.0F);

        Blocks.FIRE.setFireInfo(this, 5, 20);

        OreDictionaryHelper.register(this, woodBlockVariant.toString());
        OreDictionaryHelper.register(this, woodBlockVariant.toString(), woodType.toString());
    }

    @Override
    public WoodBlockVariant getWoodBlockVariant() {
        return woodBlockVariant;
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

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(getResourceLocation(), this.getPropertyString(state.getProperties()));
            }
        });

        for (IBlockState state : getBlockState().getValidStates()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this),
                    getMetaFromState(state),
                    new ModelResourceLocation(getResourceLocation(), "inventory"));
        }
    }
}
