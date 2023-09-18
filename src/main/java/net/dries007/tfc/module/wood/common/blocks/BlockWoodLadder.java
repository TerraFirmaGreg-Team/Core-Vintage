package net.dries007.tfc.module.wood.common.blocks;

import net.dries007.tfc.module.wood.api.type.WoodType;
import net.dries007.tfc.module.wood.api.variant.block.IWoodBlock;
import net.dries007.tfc.module.wood.api.variant.block.WoodBlockVariant;
import net.dries007.tfc.client.util.CustomStateMap;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.items.itemblocks.ItemBlockTFC;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.SoundType;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nullable;

public class BlockWoodLadder extends BlockLadder implements IWoodBlock {

    private final WoodBlockVariant variant;
    private final WoodType type;

    public BlockWoodLadder(WoodBlockVariant variant, WoodType type) {
        this.variant = variant;
        this.type = type;

        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());
        setCreativeTab(CreativeTabsTFC.WOOD);
        setSoundType(SoundType.LADDER);
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
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this,
                new CustomStateMap.Builder()
                        .customPath(getResourceLocation())
                        .build());

        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this), 0,
                new ModelResourceLocation(getResourceLocation(),
                        "normal"));
    }
}
