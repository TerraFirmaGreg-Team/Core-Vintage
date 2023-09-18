package net.dries007.tfc.module.core.submodule.wood.common.blocks;

import net.dries007.tfc.module.core.submodule.wood.api.type.WoodType;
import net.dries007.tfc.module.core.submodule.wood.api.variant.block.IWoodBlock;
import net.dries007.tfc.module.core.submodule.wood.api.variant.block.WoodBlockVariant;
import net.dries007.tfc.client.util.CustomStateMap;
import net.dries007.tfc.common.objects.CreativeTabsTFC;
import net.dries007.tfc.common.objects.blocks.TFCBlock;
import net.dries007.tfc.util.OreDictionaryHelper;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class BlockWood extends TFCBlock implements IWoodBlock {

    private final WoodBlockVariant variant;
    private final WoodType type;

    protected BlockWood(WoodBlockVariant variant, WoodType type) {
        super(Material.WOOD);

        this.variant = variant;
        this.type = type;

        setRegistryName(getRegistryLocation());
        setTranslationKey(getTranslationName());
        setCreativeTab(CreativeTabsTFC.WOOD);
        setSoundType(SoundType.WOOD);

        OreDictionaryHelper.register(this, variant.toString(), type.toString());
    }

    @Override
    public WoodBlockVariant getBlockVariant() {
        return variant;
    }

    @Override
    public WoodType getType() {
        return type;
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
