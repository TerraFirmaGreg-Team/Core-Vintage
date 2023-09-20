package net.dries007.tfc.module.wood.common.blocks;

import net.dries007.tfc.module.core.client.util.CustomStateMap;
import net.dries007.tfc.module.core.common.objects.items.itemblocks.ItemBlockTFC;
import net.dries007.tfc.module.wood.api.type.WoodType;
import net.dries007.tfc.module.wood.api.variant.block.IWoodBlock;
import net.dries007.tfc.module.wood.api.variant.block.WoodBlockVariant;
import net.dries007.tfc.module.wood.api.variant.block.WoodBlockVariants;
import net.dries007.tfc.module.wood.common.WoodStorage;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class BlockWoodStairs extends BlockStairs implements IWoodBlock {
    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockWoodStairs(WoodBlockVariant variant, WoodType type) {
        super(WoodStorage.getWoodBlock(WoodBlockVariants.PLANKS, type).getDefaultState());

        this.variant = variant;
        this.type = type;

        useNeighborBrightness = true;

        Blocks.FIRE.setFireInfo(this, 5, 20);
        //OreDictionaryHelper.register(this, variant.toString(), type.toString());
    }

    @Override
    public WoodBlockVariant getBlockVariant() {
        return variant;
    }

    @Override
    public WoodType getType() {
        return type;
    }

    @Nullable
    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlockTFC(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this,
                new CustomStateMap.Builder()
                        .customPath(getResourceLocation())
                        .build());

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getResourceLocation(), "normal"));
    }
}
