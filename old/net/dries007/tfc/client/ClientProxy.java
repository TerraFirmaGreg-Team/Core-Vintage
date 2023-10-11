package net.dries007.tfc.client;

import net.dries007.tfc.api.capability.IMoldHandler;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class ClientProxy {

    public static final IItemColor moldItemColors = (stack, tintIndex) -> {
        if (tintIndex != 1) return 0xFFFFFF;

        IFluidHandler cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
        if (cap != null) {
            if (cap instanceof IMoldHandler) {
                var material = ((IMoldHandler) cap).getMaterial();
                if (material != null) {
                    return material.getMaterialRGB();
                }
            }
        }
        return 0xFFFFFF;
    };

//    @SubscribeEvent
//    @SuppressWarnings("ConstantConditions")
//    public static void registerModels(ModelRegistryEvent event) {
//
//        //==== BLOCKS ================================================================================================//
//
//        for (var block : BlocksCore.BLOCKS) {
//            if (block instanceof IHasModel blockModel) {
//                blockModel.onModelRegister();
//            } else {
//                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "normal"));
//            }
//        }
//
//
//        //==== ITEMS =================================================================================================//
//
//        for (var item : StorageCeramic.UNFIRED_MOLDS.values())
//            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));
//
////        for (var item : TFCItems.ITEMS) {
////            if (getBlockFromItem(item) instanceof IHasModel itemBlockModel) {
////                itemBlockModel.onModelRegister();
////            } else if (item instanceof IHasModel itemModel) {
////                itemModel.onModelRegister();
////            } else {
////                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));
////            }
////        }
//
//        //==== TESRs =================================================================================================//
//
//
//        // Registering fluid containers
//        ModelLoader.setCustomModelResourceLocation(ItemsTFC_old.WOODEN_BUCKET, 0, new ModelResourceLocation(ItemsTFC_old.WOODEN_BUCKET.getRegistryName(), "inventory"));
//        ModelLoader.setCustomModelResourceLocation(ItemsTFC_old.FIRED_JUG, 0, new ModelResourceLocation(ItemsTFC_old.FIRED_JUG.getRegistryName(), "inventory"));
//
//        // Simple Items
//        for (Item item : ItemsTFC_old.getAllSimpleItems())
//            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));
//
//        // Dye color Items
//        for (EnumDyeColor color : EnumDyeColor.values()) {
//            ModelLoader.setCustomModelResourceLocation(ItemsTFC_old.UNFIRED_VESSEL_GLAZED, color.getDyeDamage(), new ModelResourceLocation(ItemsTFC_old.UNFIRED_VESSEL_GLAZED.getRegistryName().toString()));
//            ModelLoader.setCustomModelResourceLocation(ItemsTFC_old.FIRED_VESSEL_GLAZED, color.getDyeDamage(), new ModelResourceLocation(ItemsTFC_old.FIRED_VESSEL_GLAZED.getRegistryName().toString()));
//        }
//
//        // Ceramic Molds
//        for (var orePrefix : OrePrefix.values()) {
//            var extendedOrePrefix = (IOrePrefixExtension) orePrefix;
//
//            if (extendedOrePrefix.getHasMold()) {
//                var clayMold = StorageCeramic.FIRED_MOLDS.get(orePrefix);
//
//                ModelBakery.registerItemVariants(clayMold, new ModelResourceLocation(clayMold.getRegistryName().toString() + "_empty"));
//                ModelBakery.registerItemVariants(clayMold, new ModelResourceLocation(clayMold.getRegistryName().toString() + "_filled"));
//
//                ModelLoader.setCustomMeshDefinition(clayMold, new ItemMeshDefinition() {
//                    @Override
//                    @Nonnull
//                    public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {
//                        IFluidHandler cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
//                        if (cap instanceof IMoldHandler) {
//                            Material material = ((IMoldHandler) cap).getMaterial();
//                            if (material != null) {
//                                return new ModelResourceLocation(clayMold.getRegistryName().toString() + "_filled");
//                            }
//                        }
//                        return new ModelResourceLocation(clayMold.getRegistryName().toString() + "_empty");
//                    }
//                });
//            }
//        }
//
//
//    }
//
//
//    @SubscribeEvent
//    @SideOnly(Side.CLIENT)
//    @SuppressWarnings("deprecation")
//    public static void registerColorHandlerItems(ColorHandlerEvent.Item event) {
//        var itemColors = event.getItemColors();
//
//        var blockColors = event.getBlockColors();
//
//        //==== Other =================================================================================================//
//
//        itemColors.registerItemColorHandler((s, i) -> i == 1 ? EnumDyeColor.byDyeDamage(s.getItemDamage()).getColorValue() : 0xFFFFFF,
//                ItemsTFC_old.UNFIRED_VESSEL_GLAZED, ItemsTFC_old.FIRED_VESSEL_GLAZED);
//
//        // Colorize clay molds
//        itemColors.registerItemColorHandler(moldItemColors, StorageCeramic.FIRED_MOLDS.values().toArray(new Item[0]));
//    }


}
