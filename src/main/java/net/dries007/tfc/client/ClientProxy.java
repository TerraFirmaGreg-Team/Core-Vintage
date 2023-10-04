package net.dries007.tfc.client;

import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.client.button.GuiButtonPlayerInventoryTab;
import net.dries007.tfc.client.util.TFCGuiHandler;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.network.PacketSwitchPlayerInventoryTab;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.ClimateHelper;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import su.terrafirmagreg.tfc.TerraFirmaCraft;

import java.util.List;

import static net.minecraft.util.text.TextFormatting.*;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
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
//        // Register Meshers for Branches
//        for (var tree : TreeType.getTreeTypes()) {
//            ModelHelperTFC.regModel(tree.getDynamicBranch()); //Register Branch itemBlock
//            ModelHelperTFC.regModel(tree); //Register custom state mapper for branch
//        }
//
//        for (var block : BlocksCore.FLUID)
//            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());
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
//    @SubscribeEvent
//    @SideOnly(Side.CLIENT)
//    public static void registerColorHandlerBlocks(ColorHandlerEvent.Block event) {
//        var blockColors = event.getBlockColors();
//        IBlockColor grassColor = GrassColorHandler::computeGrassColor;
//
//        blockColors.registerBlockColorHandler(grassColor, BlocksCore.ROOTY_DIRT_MIMIC);
//
//    }
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

//    @SubscribeEvent
//    public static void onModelBake(ModelBakeEvent event) {
//        Block block = BlocksCore.ROOTY_DIRT_MIMIC;
//        if (block.getRegistryName() != null) {
//            BakedModelBlockRooty rootyModel = new BakedModelBlockRootyTFC();
//            event.getModelRegistry().putObject(new ModelResourceLocation(block.getRegistryName(), "normal"), rootyModel);
//        }
//    }


    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onInitGuiPost(GuiScreenEvent.InitGuiEvent.Post event) {
        if (event.getGui() instanceof GuiInventory) {
            int buttonId = event.getButtonList().size();
            int guiLeft = ((GuiInventory) event.getGui()).getGuiLeft();
            int guiTop = ((GuiInventory) event.getGui()).getGuiTop();

            event.getButtonList().add(new GuiButtonPlayerInventoryTab(TFCGuiHandler.Type.INVENTORY, guiLeft, guiTop, ++buttonId, false));
            event.getButtonList().add(new GuiButtonPlayerInventoryTab(TFCGuiHandler.Type.SKILLS, guiLeft, guiTop, ++buttonId, true));
            event.getButtonList().add(new GuiButtonPlayerInventoryTab(TFCGuiHandler.Type.CALENDAR, guiLeft, guiTop, ++buttonId, true));
            event.getButtonList().add(new GuiButtonPlayerInventoryTab(TFCGuiHandler.Type.NUTRITION, guiLeft, guiTop, ++buttonId, true));
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onGuiButtonPressPre(GuiScreenEvent.ActionPerformedEvent.Pre event) {
        if (event.getGui() instanceof GuiInventory) {
            if (event.getButton() instanceof GuiButtonPlayerInventoryTab button) {
                // This is to prevent the button from immediately firing after moving (enabled is set to false then)
                if (button.isActive() && button.enabled) {
                    TerraFirmaCraft.network.sendToServer(new PacketSwitchPlayerInventoryTab(button.getGuiType()));
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onGuiButtonPressPost(GuiScreenEvent.ActionPerformedEvent.Post event) {
        if (event.getGui() instanceof GuiInventory) {
            // This is necessary to catch the resizing of the inventory gui when you open the recipe book
            for (GuiButton button : event.getButtonList()) {
                if (button instanceof GuiButtonPlayerInventoryTab) {
                    ((GuiButtonPlayerInventoryTab) button).updateGuiLeft(((GuiInventory) event.getGui()).getGuiLeft());
                }
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onRenderGameOverlayText(RenderGameOverlayEvent.Text event) {
        Minecraft mc = Minecraft.getMinecraft();
        List<String> list = event.getRight();
        if (mc.gameSettings.showDebugInfo) {
            //noinspection ConstantConditions
            BlockPos blockpos = new BlockPos(mc.getRenderViewEntity().posX, mc.getRenderViewEntity().getEntityBoundingBox().minY, mc.getRenderViewEntity().posZ);
            Chunk chunk = mc.world.getChunk(blockpos);
            if (mc.world.isBlockLoaded(blockpos) && !chunk.isEmpty()) {
                final int x = blockpos.getX() & 15, z = blockpos.getZ() & 15;
                ChunkDataTFC data = chunk.getCapability(ChunkDataProvider.CHUNK_DATA_CAPABILITY, null);

                list.add("");
                list.add(AQUA + "TerraFirmaCraft");
                boolean chunkDataValid = data != null && data.isInitialized();

                if (chunkDataValid) {
                    list.add(String.format("%sRegion: %s%.1f\u00b0C%s Avg: %s%.1f\u00b0C%s Min: %s%.1f\u00b0C%s Max: %s%.1f\u00b0C",
                            GRAY, WHITE, data.getRegionalTemp(), GRAY,
                            WHITE, data.getAverageTemp(), GRAY,
                            WHITE, ClimateHelper.monthFactor(data.getRegionalTemp(), Month.JANUARY.getTemperatureModifier(), blockpos.getZ()), GRAY,
                            WHITE, ClimateHelper.monthFactor(data.getRegionalTemp(), Month.JULY.getTemperatureModifier(), blockpos.getZ())));
                    list.add(String.format("%sTemperature: %s%.1f\u00b0C Daily: %s%.1f\u00b0C",
                            GRAY, WHITE, ClimateTFC.getMonthlyTemp(blockpos),
                            WHITE, ClimateTFC.getActualTemp(blockpos)));
                    list.add(GRAY + "Rainfall: " + WHITE + data.getRainfall());
                    list.add(GRAY + "Spawn Protection = " + WHITE + data.isSpawnProtected());
                } else if (mc.world.provider.getDimension() == 0) {
                    list.add("Invalid Chunk Data (?)");
                }

                // Always add calendar info
                list.add(I18n.format("tfc.tooltip.date", CalendarTFC.CALENDAR_TIME.getTimeAndDate()));

                if (ConfigTFC.General.DEBUG.enable) {
                    list.add(I18n.format("tfc.tooltip.debug_times", CalendarTFC.PLAYER_TIME.getTicks(), CalendarTFC.CALENDAR_TIME.getTicks()));

                    if (chunkDataValid) {
                        list.add(GRAY + "Flora Density: " + WHITE + data.getFloraDensity());
                        list.add(GRAY + "Flora Diversity: " + WHITE + data.getFloraDiversity());

                        list.add(GRAY + "Valid Trees: ");
                        data.getValidTrees().forEach(t -> list.add(String.format("%s %s (%.1f)", WHITE, t.toString(), t.getDominance())));

                        list.add(GRAY + "Sea level offset: " + WHITE + data.getSeaLevelOffset(x, z));
                        list.add(GRAY + "Spawn Protection: " + WHITE + data.getSpawnProtection());
                    }
                }
            }
        }
    }

//    @SubscribeEvent
//    @SideOnly(Side.CLIENT)
//    public static void onItemTooltip(ItemTooltipEvent event) {
//        var stack = event.getItemStack();
//        var toolTip = event.getToolTip();
//
//        // GuiScreen.isShiftKeyDown()
//
//        if (!stack.isEmpty()) {
//            // Size
//            var size = CapabilityItemSize.getIItemSize(stack);
//            if (size != null) {
//                size.addSizeInfo(stack, toolTip);
//            }
//
//            // Temperature
//            var heat = stack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
//            if (heat != null) {
//                heat.addHeatInfo(stack, toolTip);
//            }
//
//            // Forging steps
//            var forging = stack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
//            if (forging != null && forging.getWork() > 0) {
//                toolTip.add(I18n.format("tfc.tooltip.forging_in_progress"));
//            }
//
//            // Nutriens
//            var nutrients = stack.getCapability(CapabilityFood.CAPABILITY, null);
//            if (nutrients != null) {
//                nutrients.addTooltipInfo(stack, toolTip);
//            }
//
//            // Egg
//            var eggInfo = stack.getCapability(CapabilityEgg.CAPABILITY, null);
//            if (eggInfo != null) {
//                eggInfo.addEggInfo(stack, toolTip);
//            }
//
//            // Metal
//            var metalObject = CapabilityMetalItem.getMaterialItem(stack);
//            if (metalObject != null) {
//                metalObject.addMetalInfo(stack, toolTip);
//            }
//
//            if (event.getFlags().isAdvanced()) {
//                if (ConfigTFC.Client.TOOLTIP.showOreDictionaryTooltip) {
//                    int[] ids = OreDictionary.getOreIDs(stack);
//                    if (ids.length == 1) {
//                        toolTip.add(I18n.format("tfc.tooltip.oredictionaryentry", OreDictionary.getOreName(ids[0])));
//                    } else if (ids.length > 1) {
//                        toolTip.add(I18n.format("tfc.tooltip.oredictionaryentries"));
//                        ArrayList<String> names = new ArrayList<>(ids.length);
//                        for (int id : ids) {
//                            names.add("+ " + OreDictionary.getOreName(id));
//                        }
//                        names.sort(null); // Natural order (String.compare)
//                        toolTip.addAll(names);
//                    }
//                }
//            }
//        }
//    }


}
