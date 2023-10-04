package net.dries007.tfc.module.metal.init;

import su.terrafirmagreg.util.registry.Registry;
import net.dries007.tfc.module.core.api.util.IHasModel;
import net.dries007.tfc.module.core.api.util.RegistryHelper;
import net.dries007.tfc.module.metal.StorageMetal;
import net.dries007.tfc.module.metal.api.types.variant.block.IMetalBlock;
import net.dries007.tfc.module.metal.client.render.TESRMetalAnvil;
import net.dries007.tfc.module.metal.objects.tiles.TEMetalAnvil;
import net.dries007.tfc.module.metal.objects.tiles.TEMetalSheet;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static net.dries007.tfc.module.metal.StorageMetal.METAL_BLOCKS;

public class BlocksMetal {

    public static void onRegister(Registry registry) {
        for (var block : METAL_BLOCKS.values()) {
            var itemBlock = block.getItemBlock();
            if (itemBlock != null) registry.registerBlock((Block) block, block.getItemBlock(), block.getName());
            else registry.registerBlock((Block) block, block.getName());
        }

        RegistryHelper.registerTileEntities(
                registry,
                TEMetalAnvil.class,
                TEMetalSheet.class//todo
        );
    }

    @SideOnly(Side.CLIENT)
    public static void onClientRegister(Registry registry) {
        registry.registerClientModelRegistrationStrategy(() -> {
            METAL_BLOCKS.values().forEach(IHasModel::onModelRegister);
        });

        //==== TESRs =================================================================================================//

        ClientRegistry.bindTileEntitySpecialRenderer(TEMetalAnvil.class, new TESRMetalAnvil());
    }

    @SideOnly(Side.CLIENT)
    public static void onClientInitialization() {
        var itemColors = Minecraft.getMinecraft().getItemColors();
        var blockColors = Minecraft.getMinecraft().getBlockColors();

        blockColors.registerBlockColorHandler((s, w, p, i) -> i == 0 ? ((IMetalBlock) s.getBlock()).getType().getMaterial().getMaterialRGB() : 0xFFFFFF,
                StorageMetal.METAL_BLOCKS.values()
                        .stream()
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        itemColors.registerItemColorHandler((s, i) -> i == 0 ? ((IMetalBlock) ((ItemBlock) s.getItem()).getBlock()).getType().getMaterial().getMaterialRGB() : 0xFFFFFF,
                METAL_BLOCKS.values()
                        .stream()
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));
    }
}
