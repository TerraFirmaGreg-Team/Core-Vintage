package dev.quarris.tfcalloycalc;

import net.dries007.tfc.objects.CreativeTabsTFC;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = ModRef.ID, name = ModRef.NAME, version = ModRef.VERSION, dependencies = ModRef.DEPENDENCIES)
public class TFCAlloyCalc {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModRef.logger = event.getModLog();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        GameRegistry.registerTileEntity(TileEntityCalc.class, ModRef.res("alloy_calculator"));
    }

    @Mod.EventBusSubscriber(modid = ModRef.ID)
    public static class EventHandler {

        static Block calc;

        @SubscribeEvent
        public static void registerBlocks(RegistryEvent.Register<Block> event) {
            event.getRegistry().register(calc = new BlockCalc(Material.IRON).setTranslationKey(ModRef.ID + ".alloy_calculator").setRegistryName(ModRef.res("alloy_calculator")));
        }

        @SubscribeEvent
        public static void registerItems(RegistryEvent.Register<Item> event) {
            event.getRegistry().register(new ItemBlock(calc).setCreativeTab(CreativeTabsTFC.CT_MISC).setTranslationKey(ModRef.ID + ".alloy_calculator").setRegistryName(ModRef.res("alloy_calculator")));
        }

        @SideOnly(Side.CLIENT)
        @SubscribeEvent
        public static void registerModels(ModelRegistryEvent event) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(calc), 0, new ModelResourceLocation(calc.getRegistryName(), "inventory"));
        }
    }
}
