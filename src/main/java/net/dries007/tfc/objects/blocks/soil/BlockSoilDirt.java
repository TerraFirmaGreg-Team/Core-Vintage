/*
 * Licensed under the EUPL, Version 1.2.
 * You may obtain a copy of the Licence at:
 * https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
 */

package net.dries007.tfc.objects.blocks.soil;


import net.dries007.tfc.api.types2.soil.SoilType;
import net.dries007.tfc.api.types2.soil.SoilVariant;
import net.dries007.tfc.api.util.FallingBlockManager.Specification;
import net.dries007.tfc.api.util.ISoilTypeBlock;
import net.dries007.tfc.objects.CreativeTabsTFC;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.function.Supplier;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public class BlockSoilDirt extends Block implements ISoilTypeBlock {

    private final SoilVariant soilVariant;
    private final SoilType soilType;
    private final ResourceLocation modelLocation;

    public BlockSoilDirt(SoilVariant soilVariant, SoilType soilType) {
        super(Material.GROUND);

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
    public void onModelRegister() {

    }
}
