package net.dries007.tfc.module.agriculture.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.api.util.RegistryHelper;
import net.dries007.tfc.module.agriculture.common.tile.TECropBase;
import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.dries007.tfc.module.agriculture.StorageAgriculture.BUSH_BLOCKS;
import static net.dries007.tfc.module.agriculture.StorageAgriculture.CROP_BLOCKS;

public class BlocksCrop {

    public static void onRegister(Registry registry) {

        for (var block : CROP_BLOCKS.values()) {
            var itemBlock = block.getItemBlock();
            if (itemBlock != null) registry.registerBlock((Block) block, block.getItemBlock(), block.getName());
            else registry.registerBlock((Block) block, block.getName());
        }

        for (var block : BUSH_BLOCKS.values()) {
            var itemBlock = block.getItemBlock();
            if (itemBlock != null) registry.registerBlock((Block) block, block.getItemBlock(), block.getName());
            else registry.registerBlock((Block) block, block.getName());
        }

        RegistryHelper.registerTileEntities(
                registry,
                TECropBase.class
        );
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {

        registry.registerClientModelRegistrationStrategy(() -> {
            CROP_BLOCKS.values().forEach(IHasModel::onModelRegister);
            BUSH_BLOCKS.values().forEach(IHasModel::onModelRegister);
        });
    }
}
