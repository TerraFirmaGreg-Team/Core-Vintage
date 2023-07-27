/*
 * Licensed under the EUPL, Version 1.2.
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 */

package net.dries007.tfc.objects.blocks.soil;


import net.dries007.tfc.api.types2.soil.SoilType;
import net.dries007.tfc.api.types2.soil.SoilVariant;
import net.dries007.tfc.api.util.ISoilTypeBlock;
import net.dries007.tfc.api.util.Pair;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.DefaultStateMapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;
import static net.dries007.tfc.objects.blocks.soil.BlockSoil.BLOCK_SOIL_MAP;

public class BlockSoilGrass extends BlockGlass implements ISoilTypeBlock {

    private final SoilVariant soilVariant;
    private final SoilType soilType;
    private final ResourceLocation modelLocation;

    public BlockSoilGrass(SoilVariant soilVariant, SoilType soilType) {
        super(Material.GROUND, false);

        if (BLOCK_SOIL_MAP.put(new Pair<>(soilVariant, soilType), this) != null)
            throw new RuntimeException("Duplicate registry entry detected for block: " + soilVariant + " " + soilType);


        this.soilVariant = soilVariant;
        this.soilType = soilType;
        this.modelLocation = new ResourceLocation(MOD_ID, "soil/" + soilVariant);

        String blockRegistryName = String.format("%s/%s/%s", "soil", soilVariant, soilType);

        this.setCreativeTab(CreativeTabsTFC.EARTH);
        this.setSoundType(SoundType.STONE);
        this.setRegistryName(MOD_ID, blockRegistryName);
        this.setTranslationKey(MOD_ID + "." + blockRegistryName.toLowerCase().replace("/", "."));
    }

    @Override
    public SoilVariant getSoilVariant() {
        return soilVariant;
    }

    @Override
    public SoilType getSoilType() {
        return soilType;
    }

    @Override
    public ItemBlock getItemBlock() {
        return new ItemBlock(this);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onModelRegister() {
        ModelLoader.setCustomStateMapper(this, new DefaultStateMapper() {
            @Nonnull
            protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
                return new ModelResourceLocation(modelLocation,
                        "north=false,south=false,east=false,west=false," +
                                "soiltype=" + soilType.getName());
            }
        });


        ModelLoader.setCustomModelResourceLocation(
                Item.getItemFromBlock(this),
                this.getMetaFromState(this.getBlockState().getBaseState()),
                new ModelResourceLocation(modelLocation,
                        "north=false,south=false,east=false,west=false," +
                                "soiltype=" + soilType.getName()));
    }

    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
}
