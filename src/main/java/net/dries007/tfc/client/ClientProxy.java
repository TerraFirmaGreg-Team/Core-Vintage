package net.dries007.tfc.client;

import com.ferreusveritas.dynamictrees.blocks.LeavesPaging;
import com.ferreusveritas.dynamictrees.models.bakedmodels.BakedModelBlockRooty;
import com.google.common.collect.ImmutableMap;
import gregtech.api.unification.material.Material;
import gregtech.api.unification.ore.OrePrefix;
import net.dries007.tfc.Tags;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.capability.IMoldHandler;
import net.dries007.tfc.api.capability.egg.CapabilityEgg;
import net.dries007.tfc.api.capability.food.CapabilityFood;
import net.dries007.tfc.api.capability.forge.CapabilityForgeable;
import net.dries007.tfc.api.capability.heat.CapabilityItemHeat;
import net.dries007.tfc.api.capability.metal.CapabilityMetalItem;
import net.dries007.tfc.api.capability.size.CapabilityItemSize;
import net.dries007.tfc.api.types.metal.variant.block.IMetalBlock;
import net.dries007.tfc.api.types.tree.type.TreeType;
import net.dries007.tfc.module.core.submodule.wood.api.variant.block.IWoodBlock;
import net.dries007.tfc.module.core.submodule.wood.api.variant.item.IWoodItem;
import net.dries007.tfc.api.util.IHasModel;
import net.dries007.tfc.client.button.GuiButtonPlayerInventoryTab;
import net.dries007.tfc.client.gui.overlay.PlayerDataOverlay;
import net.dries007.tfc.client.render.*;
import net.dries007.tfc.client.render.animal.*;
import net.dries007.tfc.client.render.projectile.RenderThrownJavelin;
import net.dries007.tfc.client.util.FluidSpriteCache;
import net.dries007.tfc.client.util.GrassColorHandler;
import net.dries007.tfc.client.util.TFCGuiHandler;
import net.dries007.tfc.common.CommonProxy;
import net.dries007.tfc.common.objects.blocks.BlockThatchBed;
import net.dries007.tfc.common.objects.blocks.TFCBlocks;
import net.dries007.tfc.module.core.submodule.wood.common.entity.EntityWoodBoat;
import net.dries007.tfc.common.objects.entity.EntityFallingBlockTFC;
import net.dries007.tfc.common.objects.entity.animal.*;
import net.dries007.tfc.common.objects.entity.projectile.EntityThrownJavelin;
import net.dries007.tfc.common.objects.items.ItemAnimalHide;
import net.dries007.tfc.common.objects.items.ItemsTFC_old;
import net.dries007.tfc.common.objects.items.TFCItems;
import net.dries007.tfc.common.objects.tileentities.*;
import net.dries007.tfc.compat.dynamictrees.client.BakedModelBlockRootyTFC;
import net.dries007.tfc.compat.dynamictrees.client.ModelHelperTFC;
import net.dries007.tfc.compat.gregtech.oreprefix.IOrePrefixExtension;
import net.dries007.tfc.config.ConfigTFC;
import net.dries007.tfc.module.core.submodule.soil.common.blocks.BlockSoilFarmland;
import net.dries007.tfc.module.core.submodule.wood.common.WoodStorage;
import net.dries007.tfc.module.core.submodule.wood.common.tileentities.TEWoodBarrel;
import net.dries007.tfc.module.core.submodule.wood.common.tileentities.TEWoodChest;
import net.dries007.tfc.module.core.submodule.wood.common.tileentities.TEWoodLoom;
import net.dries007.tfc.network.*;
import net.dries007.tfc.util.Helpers;
import net.dries007.tfc.util.calendar.CalendarTFC;
import net.dries007.tfc.util.calendar.Month;
import net.dries007.tfc.util.climate.ClimateHelper;
import net.dries007.tfc.util.climate.ClimateTFC;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataProvider;
import net.dries007.tfc.world.classic.chunkdata.ChunkDataTFC;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.entity.RenderFallingBlock;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.*;
import net.minecraft.util.IThreadListener;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.*;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static net.dries007.tfc.api.types.plant.variant.block.PlantBlockVariant.SHORT_GRASS;
import static net.dries007.tfc.api.types.plant.variant.block.PlantBlockVariant.TALL_GRASS;
import static net.dries007.tfc.common.objects.blocks.BlockPlacedHide.SIZE;
import static net.dries007.tfc.common.objects.blocks.TFCBlocks.ROOTY_DIRT_MIMIC;
import static net.dries007.tfc.module.core.submodule.soil.api.variant.block.SoilBlockVariants.FARMLAND;
import static net.dries007.tfc.module.core.submodule.soil.common.SoilStorage.SOIL_BLOCKS;
import static net.minecraft.block.Block.getBlockFromItem;
import static net.minecraft.util.text.TextFormatting.*;

@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {

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

    @SubscribeEvent
    @SuppressWarnings("ConstantConditions")
    public static void registerModels(ModelRegistryEvent event) {

        //==== BLOCKS ================================================================================================//

        TFCBlocks.METAL_BLOCKS.values().forEach(IHasModel::onModelRegister);

        for (var block : TFCBlocks.BLOCKS) {
            if (block instanceof IHasModel blockModel) {
                blockModel.onModelRegister();
            } else {
                ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), 0, new ModelResourceLocation(block.getRegistryName(), "normal"));
            }
        }

        // Register Meshers for Branches
        for (var tree : TreeType.getTreeTypes()) {
            ModelHelperTFC.regModel(tree.getDynamicBranch()); //Register Branch itemBlock
            ModelHelperTFC.regModel(tree); //Register custom state mapper for branch
        }

        for (var block : TFCBlocks.FLUID)
            ModelLoader.setCustomStateMapper(block, new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());


        //==== ITEMS =================================================================================================//

        for (var item : TFCItems.UNFIRED_MOLDS.values())
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));

        for (var item : TFCItems.ITEMS) {
            if (getBlockFromItem(item) instanceof IHasModel itemBlockModel) {
                itemBlockModel.onModelRegister();
            } else if (item instanceof IHasModel itemModel) {
                itemModel.onModelRegister();
            } else {
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));
            }
        }

        //==== TESRs =================================================================================================//

        ClientRegistry.bindTileEntitySpecialRenderer(TEWoodChest.class, new TESRChestTFC());
        ClientRegistry.bindTileEntitySpecialRenderer(TEToolRack.class, new TESRToolRack());
        ClientRegistry.bindTileEntitySpecialRenderer(TEPitKiln.class, new TESRPitKiln());
        ClientRegistry.bindTileEntitySpecialRenderer(TEPlacedItemFlat.class, new TESRPlacedItemFlat());
        ClientRegistry.bindTileEntitySpecialRenderer(TEPlacedItem.class, new TESRPlacedItem());
        ClientRegistry.bindTileEntitySpecialRenderer(TEPlacedHide.class, new TESRPlacedHide());
        ClientRegistry.bindTileEntitySpecialRenderer(TEQuern.class, new TESRQuern());
        ClientRegistry.bindTileEntitySpecialRenderer(TEBellows.class, new TESRBellows());
        ClientRegistry.bindTileEntitySpecialRenderer(TEWoodBarrel.class, new TESRBarrel());
        ClientRegistry.bindTileEntitySpecialRenderer(TEAnvilTFC.class, new TESRAnvil());
        ClientRegistry.bindTileEntitySpecialRenderer(TEWoodLoom.class, new TESRLoom());
        ClientRegistry.bindTileEntitySpecialRenderer(TECrucible.class, new TESRCrucible());
        ClientRegistry.bindTileEntitySpecialRenderer(TEFirePit.class, new TESRFirePit());


        // Registering fluid containers
        ModelLoader.setCustomModelResourceLocation(ItemsTFC_old.WOODEN_BUCKET, 0, new ModelResourceLocation(ItemsTFC_old.WOODEN_BUCKET.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(ItemsTFC_old.FIRED_JUG, 0, new ModelResourceLocation(ItemsTFC_old.FIRED_JUG.getRegistryName(), "inventory"));

        // Simple Items
        for (Item item : ItemsTFC_old.getAllSimpleItems())
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName().toString()));

        // Dye color Items
        for (EnumDyeColor color : EnumDyeColor.values()) {
            ModelLoader.setCustomModelResourceLocation(ItemsTFC_old.UNFIRED_VESSEL_GLAZED, color.getDyeDamage(), new ModelResourceLocation(ItemsTFC_old.UNFIRED_VESSEL_GLAZED.getRegistryName().toString()));
            ModelLoader.setCustomModelResourceLocation(ItemsTFC_old.FIRED_VESSEL_GLAZED, color.getDyeDamage(), new ModelResourceLocation(ItemsTFC_old.FIRED_VESSEL_GLAZED.getRegistryName().toString()));
        }

        // Ceramic Molds
        for (var orePrefix : OrePrefix.values()) {
            var extendedOrePrefix = (IOrePrefixExtension) orePrefix;

            if (extendedOrePrefix.getHasMold()) {
                var clayMold = TFCItems.FIRED_MOLDS.get(orePrefix);

                ModelBakery.registerItemVariants(clayMold, new ModelResourceLocation(clayMold.getRegistryName().toString() + "_empty"));
                ModelBakery.registerItemVariants(clayMold, new ModelResourceLocation(clayMold.getRegistryName().toString() + "_filled"));

                ModelLoader.setCustomMeshDefinition(clayMold, new ItemMeshDefinition() {
                    @Override
                    @Nonnull
                    public ModelResourceLocation getModelLocation(@Nonnull ItemStack stack) {
                        IFluidHandler cap = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
                        if (cap instanceof IMoldHandler) {
                            Material material = ((IMoldHandler) cap).getMaterial();
                            if (material != null) {
                                return new ModelResourceLocation(clayMold.getRegistryName().toString() + "_filled");
                            }
                        }
                        return new ModelResourceLocation(clayMold.getRegistryName().toString() + "_empty");
                    }
                });
            }
        }

        // Blocks with Ignored Properties

        ModelLoader.setCustomStateMapper(TFCBlocks.THATCH_BED, new StateMap.Builder().ignore(BlockThatchBed.OCCUPIED).build());

        // Empty Models
        final ModelResourceLocation empty = new ModelResourceLocation(Tags.MOD_ID + ":empty");
        // todo: switch to hide rack (involves changing mechanics, etc)
        final ModelResourceLocation hideRack = new ModelResourceLocation(Tags.MOD_ID + ":hide_rack");

        ModelLoader.setCustomStateMapper(TFCBlocks.PIT_KILN, blockIn -> ImmutableMap.of(TFCBlocks.PIT_KILN.getDefaultState(), empty));
        ModelLoader.setCustomStateMapper(TFCBlocks.PLACED_ITEM_FLAT, blockIn -> ImmutableMap.of(TFCBlocks.PLACED_ITEM_FLAT.getDefaultState(), empty));
        ModelLoader.setCustomStateMapper(TFCBlocks.PLACED_ITEM, blockIn -> ImmutableMap.of(TFCBlocks.PLACED_ITEM.getDefaultState(), empty));
        ModelLoader.setCustomStateMapper(TFCBlocks.PLACED_HIDE, blockIn -> ImmutableMap.of(TFCBlocks.PLACED_HIDE.getDefaultState()
                .withProperty(SIZE, ItemAnimalHide.HideSize.SMALL), empty, TFCBlocks.PLACED_HIDE.getDefaultState()
                .withProperty(SIZE, ItemAnimalHide.HideSize.MEDIUM), empty, TFCBlocks.PLACED_HIDE.getDefaultState()
                .withProperty(SIZE, ItemAnimalHide.HideSize.LARGE), empty));

    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void registerColorHandlerBlocks(ColorHandlerEvent.Block event) {
        var blockColors = event.getBlockColors();

        // Grass Colors
        IBlockColor grassColor = GrassColorHandler::computeGrassColor;

        // Foliage Color
        // todo: do something different for conifers - they should have a different color mapping through the seasons
        IBlockColor foliageColor = GrassColorHandler::computeGrassColor;

        //==== Soil ==================================================================================================//

        blockColors.registerBlockColorHandler(grassColor,
                SOIL_BLOCKS.values()
                        .stream()
                        .filter(x -> x.getBlockVariant().isGrass())
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        blockColors.registerBlockColorHandler((s, w, p, i) ->
                        BlockSoilFarmland.TINT[s.getValue(BlockSoilFarmland.MOISTURE)],
                SOIL_BLOCKS.values()
                        .stream()
                        .filter(x -> x.getBlockVariant() == FARMLAND)
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        blockColors.registerBlockColorHandler(grassColor, TFCBlocks.PEAT_GRASS);
        blockColors.registerBlockColorHandler(grassColor, ROOTY_DIRT_MIMIC);

        //==== Plant =================================================================================================//

        blockColors.registerBlockColorHandler(grassColor,
                TFCBlocks.PLANT_BLOCKS.values()
                        .stream()
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        //==== Wood ==================================================================================================//

        blockColors.registerBlockColorHandler((s, w, p, i) -> {
                    // цвет листвы
                    if (i == 0) return foliageColor.colorMultiplier(s, w, p, i);
                    // цвет дерева
                    if (i == 1) return ((IWoodBlock) s.getBlock()).getType().getColor();
                    // Если не указан индекс
                    return 0xFFFFFF;
                },
                WoodStorage.WOOD_BLOCKS.values()
                        .stream()
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        blockColors.registerBlockColorHandler(foliageColor,
                LeavesPaging.getLeavesMapForModId(Tags.MOD_ID).values()
                        .toArray(new Block[0]));

        //==== Metal =================================================================================================//

        blockColors.registerBlockColorHandler((s, w, p, i) -> i == 0 ? ((IMetalBlock) s.getBlock()).getMaterial().getMaterialRGB() : 0xFFFFFF,
                TFCBlocks.METAL_BLOCKS.values()
                        .stream()
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("deprecation")
    public static void registerColorHandlerItems(ColorHandlerEvent.Item event) {
        var itemColors = event.getItemColors();

        var blockColors = event.getBlockColors();

        //==== Soil ==================================================================================================//

        itemColors.registerItemColorHandler((s, i) -> blockColors.colorMultiplier(((ItemBlock) s.getItem()).getBlock().getStateFromMeta(s.getMetadata()), null, null, i),
                SOIL_BLOCKS.values()
                        .stream()
                        .filter(x -> x.getBlockVariant().isGrass())
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        itemColors.registerItemColorHandler((s, i) -> blockColors.colorMultiplier(((ItemBlock) s.getItem()).getBlock().getStateFromMeta(s.getMetadata()), null, null, i),
                TFCBlocks.PEAT_GRASS);

        //==== Plant =================================================================================================//

        itemColors.registerItemColorHandler((s, i) -> blockColors.colorMultiplier(((ItemBlock) s.getItem()).getBlock().getStateFromMeta(s.getMetadata()), null, null, i),
                TFCBlocks.PLANT_BLOCKS.values()
                        .stream()
                        .filter(x -> x.getBlockVariant() == SHORT_GRASS || x.getBlockVariant() == TALL_GRASS)
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        //==== Wood ==================================================================================================//

        itemColors.registerItemColorHandler((s, i) -> {
                    // цвет листвы
                    if (i == 0)
                        return blockColors.colorMultiplier(((ItemBlock) s.getItem()).getBlock().getStateFromMeta(s.getMetadata()), null, null, i);
                    // цвет дерева
                    if (i == 1)
                        return ((IWoodBlock) ((ItemBlock) s.getItem()).getBlock()).getType().getColor();
                    // Если не указан индекс
                    return 0xFFFFFF;
                },
                WoodStorage.WOOD_BLOCKS.values()
                        .stream()
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        itemColors.registerItemColorHandler((s, i) -> ((IWoodItem) s.getItem()).getType().getColor(),
                WoodStorage.WOOD_ITEMS.values()
                        .stream()
                        .map(s -> (Item) s)
                        .toArray(Item[]::new));

        //==== Metal =================================================================================================//

        itemColors.registerItemColorHandler((s, i) -> i == 0 ? ((IMetalBlock) ((ItemBlock) s.getItem()).getBlock()).getMaterial().getMaterialRGB() : 0xFFFFFF,
                TFCBlocks.METAL_BLOCKS.values()
                        .stream()
                        .map(s -> (Block) s)
                        .toArray(Block[]::new));

        //==== Other =================================================================================================//


        itemColors.registerItemColorHandler((s, i) -> i == 1 ? EnumDyeColor.byDyeDamage(s.getItemDamage()).getColorValue() : 0xFFFFFF,
                ItemsTFC_old.UNFIRED_VESSEL_GLAZED, ItemsTFC_old.FIRED_VESSEL_GLAZED);

        itemColors.registerItemColorHandler((s, i) -> {
            var food = s.getCapability(CapabilityFood.CAPABILITY, null);
            if (food != null) {
                return food.isRotten() ? ConfigTFC.Client.DISPLAY.rottenFoodOverlayColor : 0xFFFFFF;
            }
            return 0xFFFFFF;
        }, ForgeRegistries.ITEMS.getValuesCollection().stream().filter(x -> x instanceof ItemFood).toArray(Item[]::new));

        // Colorize clay molds
        itemColors.registerItemColorHandler(moldItemColors, TFCItems.FIRED_MOLDS.values().toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void onModelBake(ModelBakeEvent event) {
        Block block = ROOTY_DIRT_MIMIC;
        if (block.getRegistryName() != null) {
            BakedModelBlockRooty rootyModel = new BakedModelBlockRootyTFC();
            event.getModelRegistry().putObject(new ModelResourceLocation(block.getRegistryName(), "normal"), rootyModel);
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onKeyEvent(InputEvent event) {
        // todo: move this to a button on the inventory GUI
        if (TFCKeybindings.OPEN_CRAFTING_TABLE.isPressed()) {
            TerraFirmaCraft.NETWORK.sendToServer(new PacketOpenCraftingGui());
        }
        if (TFCKeybindings.PLACE_BLOCK.isPressed()) {
            TerraFirmaCraft.NETWORK.sendToServer(new PacketPlaceBlockSpecial());
        }
        if (TFCKeybindings.CHANGE_ITEM_MODE.isPressed()) {
            TerraFirmaCraft.NETWORK.sendToServer(new PacketCycleItemMode());
        }
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onKeyEvent(GuiScreenEvent.KeyboardInputEvent.Pre event) {
        //Only handle when key was pressed, ignore release and hold
        if (!Keyboard.isRepeatEvent() && Keyboard.getEventKeyState() && Keyboard.getEventKey() == TFCKeybindings.STACK_FOOD.getKeyCode()) {
            if (event.getGui() instanceof GuiContainer) {
                Slot slotUnderMouse = ((GuiContainer) event.getGui()).getSlotUnderMouse();
                if (slotUnderMouse != null) {
                    TerraFirmaCraft.NETWORK.sendToServer(new PacketStackFood(slotUnderMouse.slotNumber));
                }
            }
        }
    }

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
                    TerraFirmaCraft.NETWORK.sendToServer(new PacketSwitchPlayerInventoryTab(button.getGuiType()));
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

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onItemTooltip(ItemTooltipEvent event) {
        var stack = event.getItemStack();
        var toolTip = event.getToolTip();

        // GuiScreen.isShiftKeyDown()

        if (!stack.isEmpty()) {
            // Size
            var size = CapabilityItemSize.getIItemSize(stack);
            if (size != null) {
                size.addSizeInfo(stack, toolTip);
            }

            // Temperature
            var heat = stack.getCapability(CapabilityItemHeat.ITEM_HEAT_CAPABILITY, null);
            if (heat != null) {
                heat.addHeatInfo(stack, toolTip);
            }

            // Forging steps
            var forging = stack.getCapability(CapabilityForgeable.FORGEABLE_CAPABILITY, null);
            if (forging != null && forging.getWork() > 0) {
                toolTip.add(I18n.format("tfc.tooltip.forging_in_progress"));
            }

            // Nutriens
            var nutrients = stack.getCapability(CapabilityFood.CAPABILITY, null);
            if (nutrients != null) {
                nutrients.addTooltipInfo(stack, toolTip);
            }

            // Egg
            var eggInfo = stack.getCapability(CapabilityEgg.CAPABILITY, null);
            if (eggInfo != null) {
                eggInfo.addEggInfo(stack, toolTip);
            }

            // Metal
            var metalObject = CapabilityMetalItem.getMaterialItem(stack);
            if (metalObject != null) {
                metalObject.addMetalInfo(stack, toolTip);
            }

            if (event.getFlags().isAdvanced()) {
                if (ConfigTFC.Client.TOOLTIP.showOreDictionaryTooltip) {
                    int[] ids = OreDictionary.getOreIDs(stack);
                    if (ids.length == 1) {
                        toolTip.add(I18n.format("tfc.tooltip.oredictionaryentry", OreDictionary.getOreName(ids[0])));
                    } else if (ids.length > 1) {
                        toolTip.add(I18n.format("tfc.tooltip.oredictionaryentries"));
                        ArrayList<String> names = new ArrayList<>(ids.length);
                        for (int id : ids) {
                            names.add("+ " + OreDictionary.getOreName(id));
                        }
                        names.sort(null); // Natural order (String.compare)
                        toolTip.addAll(names);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void textureStitched(TextureStitchEvent.Post event) {
        FluidSpriteCache.clear();
    }

    public static void registerEntityRenderer() {
        RenderingRegistry.registerEntityRenderingHandler(EntityFallingBlockTFC.class, RenderFallingBlock::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityThrownJavelin.class, RenderThrownJavelin::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySheepTFC.class, RenderSheepTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCowTFC.class, RenderCowTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGrizzlyBearTFC.class, RenderGrizzlyBearTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityChickenTFC.class, RenderChickenTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPheasantTFC.class, RenderPheasantTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityDeerTFC.class, RenderDeerTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPigTFC.class, RenderPigTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWolfTFC.class, RenderWolfTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityRabbitTFC.class, RenderRabbitTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHorseTFC.class, RenderHorseTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityDonkeyTFC.class, RenderAbstractHorseTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMuleTFC.class, RenderAbstractHorseTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWoodBoat.class, RenderBoatTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPolarBearTFC.class, RenderPolarBearTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityParrotTFC.class, RenderParrotTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityLlamaTFC.class, RenderLlamaTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityOcelotTFC.class, RenderOcelotTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityPantherTFC.class, RenderPantherTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityDuckTFC.class, RenderDuckTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityAlpacaTFC.class, RenderAlpacaTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGoatTFC.class, RenderGoatTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySaberToothTFC.class, RenderSaberToothTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCamelTFC.class, RenderCamelTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityLionTFC.class, RenderLionTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHyenaTFC.class, RenderHyenaTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityDireWolfTFC.class, RenderDireWolfTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityHareTFC.class, RenderHareTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBoarTFC.class, RenderBoarTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityZebuTFC.class, RenderZebuTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGazelleTFC.class, RenderGazelleTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityWildebeestTFC.class, RenderWildebeestTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityQuailTFC.class, RenderQuailTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityGrouseTFC.class, RenderGrouseTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMongooseTFC.class, RenderMongooseTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTurkeyTFC.class, RenderTurkeyTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityJackalTFC.class, RenderJackalTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityMuskOxTFC.class, RenderMuskOxTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityYakTFC.class, RenderYakTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityBlackBearTFC.class, RenderBlackBearTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCougarTFC.class, RenderCougarTFC::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityCoyoteTFC.class, RenderCoyoteTFC::new);
    }

    @Override
    public void onPreInit(FMLPreInitializationEvent event) {
        super.onPreInit(event);

        registerEntityRenderer();
    }

    @Override
    public void onInit(FMLInitializationEvent event) {
        super.onInit(event);

        TFCKeybindings.onInit();
        // Enable overlay to render health, thirst and hunger bars, TFC style.
        // Also renders animal familiarity
        MinecraftForge.EVENT_BUS.register(PlayerDataOverlay.getInstance());
    }

    @Override
    public void onPostInit(FMLPostInitializationEvent event) {
        super.onPostInit(event);
    }

    public void onLoadComplete(FMLLoadCompleteEvent event) {
        super.onLoadComplete(event);
    }

    public void onServerStarting(FMLServerStartingEvent event) {
        super.onServerStarting(event);
    }

    @Nonnull
    @Override
    public IThreadListener getThreadListener(MessageContext context) {
        if (context.side.isClient()) {
            return Minecraft.getMinecraft();
        } else {
            return context.getServerHandler().player.server;
        }
    }

    @Override
    @Nullable
    public EntityPlayer getPlayer(MessageContext context) {
        if (context.side.isClient()) {
            return Minecraft.getMinecraft().player;
        } else {
            return context.getServerHandler().player;
        }
    }

    @Override
    @Nullable
    public World getWorld(MessageContext context) {
        if (context.side.isClient()) {
            return Minecraft.getMinecraft().world;
        } else {
            return context.getServerHandler().player.getEntityWorld();
        }
    }

    @Nonnull
    @Override
    public String getMonthName(Month month, boolean useSeasons) {
        return I18n.format(useSeasons ? "tfc.enum.season." + month.name().toLowerCase() : Helpers.getEnumName(month));
    }

    @Nonnull
    @Override
    public String getDayName(int dayOfMonth, long totalDays) {
        String date = CalendarTFC.CALENDAR_TIME.getMonthOfYear().name() + dayOfMonth;
        String birthday = CalendarTFC.BIRTHDAYS.get(date);
        if (birthday != null) {
            return birthday;
        }
        return I18n.format("tfc.enum.day." + CalendarTFC.DAY_NAMES[(int) (totalDays % 7)]);
    }

    @Nonnull
    @Override
    public String getDate(int hour, int minute, String monthName, int day, long years) {
        // We call an additional String.format for the time, because vanilla doesn't support %02d format specifiers
        return I18n.format("tfc.tooltip.calendar_full_date", String.format("%02d:%02d", hour, minute), monthName, day, years);
    }
}
