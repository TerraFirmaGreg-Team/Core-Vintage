package net.dries007.tfc.module.crop.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.api.util.IHasModel;
import net.minecraft.block.Block;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.dries007.tfc.module.crop.common.CropStorage.CROP_BLOCKS;
import static net.dries007.tfc.module.rock.common.RockStorage.ROCK_BLOCKS;

public class BlockInitializer {

    public static void onRegister(Registry registry) {

        for (var block : CROP_BLOCKS.values()) {
            var itemBlock = block.getItemBlock();
            if (itemBlock != null) registry.registerBlock((Block) block, block.getItemBlock(), block.getName());
            else registry.registerBlock((Block) block, block.getName());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {

        registry.registerClientModelRegistrationStrategy(() -> {
            CROP_BLOCKS.values().forEach(IHasModel::onModelRegister);
        });
    }
}
