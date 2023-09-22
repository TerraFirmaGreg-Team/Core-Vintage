package net.dries007.tfc.module.rock.init;

import com.codetaylor.mc.athenaeum.registry.Registry;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.module.core.client.util.GrassColorHandler;
import net.dries007.tfc.module.rock.api.variant.block.RockBlockVariantHandler;
import net.dries007.tfc.module.soil.api.variant.block.SoilBlockVariantHandler;
import net.dries007.tfc.module.soil.common.blocks.BlockSoilFarmland;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.dries007.tfc.module.core.common.objects.blocks.TFCBlocks.PEAT_GRASS;
import static net.dries007.tfc.module.core.common.objects.blocks.TFCBlocks.ROOTY_DIRT_MIMIC;
import static net.dries007.tfc.module.rock.common.RockStorage.ROCK_BLOCKS;
import static net.dries007.tfc.module.soil.api.variant.block.SoilBlockVariants.FARMLAND;
import static net.dries007.tfc.module.soil.common.SoilStorage.SOIL_BLOCKS;

public class BlockInitializer {

    public static void onRegister(Registry registry) {

        for (var block : ROCK_BLOCKS.values()) {
            var itemBlock = block.getItemBlock();
            if (itemBlock != null) registry.registerBlock((Block) block, block.getItemBlock(), block.getName());
            else registry.registerBlock((Block) block, block.getName());
        }
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {

        registry.registerClientModelRegistrationStrategy(() -> {
            ROCK_BLOCKS.values().forEach(IHasModel::onModelRegister);
        });
    }
}
