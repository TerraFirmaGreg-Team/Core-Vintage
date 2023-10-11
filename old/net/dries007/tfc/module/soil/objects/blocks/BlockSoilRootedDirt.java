package net.dries007.tfc.module.soil.objects.blocks;

import com.ferreusveritas.dynamictrees.systems.DirtHelper;
import net.dries007.tfc.module.soil.api.types.type.SoilType;
import net.dries007.tfc.module.soil.api.types.variant.block.SoilBlockVariant;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public class BlockSoilRootedDirt extends BlockSoil {

    public BlockSoilRootedDirt(SoilBlockVariant variant, SoilType type) {
        super(variant, type);

        DirtHelper.registerSoil(this, DirtHelper.DIRTLIKE);
    }

    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
