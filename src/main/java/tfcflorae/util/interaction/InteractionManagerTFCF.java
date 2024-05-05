package tfcflorae.util.interaction;

import su.terrafirmagreg.api.util.OreDictUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;


import net.dries007.tfc.objects.items.ItemSeedsTFC;
import net.dries007.tfc.util.interaction.IRightClickBlockAction;
import net.dries007.tfc.util.interaction.IRightClickItemAction;
import tfcflorae.client.GuiHandler;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static su.terrafirmagreg.api.data.Constants.MODID_TFCF;

@Mod.EventBusSubscriber(modid = MODID_TFCF)
public final class InteractionManagerTFCF {

    private static final Map<Predicate<ItemStack>, IRightClickBlockAction> USE_ACTIONS = new HashMap<>();
    private static final Map<Predicate<ItemStack>, IRightClickItemAction> RIGHT_CLICK_ACTIONS = new HashMap<>();
    private static final ThreadLocal<Boolean> PROCESSING_INTERACTION = ThreadLocal.withInitial(
            () -> false); // avoids stack overflows where some mods post interaction events during onBlockActivated (see #1321)

    static {
        // Pineapple Leather knapping
        putBoth(stack -> OreDictUtils.contains(stack, "leatherPineapple"), ((worldIn, playerIn, handIn) -> {
            if (OreDictUtils.playerHasItemMatchingOre(playerIn.inventory, "shears")) {
                if (!worldIn.isRemote) {
                    GuiHandler.openGui(worldIn, playerIn, GuiHandler.Type.PINEAPPLE_LEATHER);
                }
                return EnumActionResult.SUCCESS;
            }
            return EnumActionResult.FAIL;
        }));

        // Burlap Cloth knapping
        putBoth(stack -> OreDictUtils.contains(stack, "clothBurlap"), ((worldIn, playerIn, handIn) -> {
            if (OreDictUtils.playerHasItemMatchingOre(playerIn.inventory, "shears")) {
                if (!worldIn.isRemote) {
                    GuiHandler.openGui(worldIn, playerIn, GuiHandler.Type.BURLAP_CLOTH);
                }
                return EnumActionResult.SUCCESS;
            }
            return EnumActionResult.FAIL;
        }));

        // Wool Cloth knapping
        putBoth(stack -> OreDictUtils.contains(stack, "clothWool"), ((worldIn, playerIn, handIn) -> {
            if (OreDictUtils.playerHasItemMatchingOre(playerIn.inventory, "shears")) {
                if (!worldIn.isRemote) {
                    GuiHandler.openGui(worldIn, playerIn, GuiHandler.Type.WOOL_CLOTH);
                }
                return EnumActionResult.SUCCESS;
            }
            return EnumActionResult.FAIL;
        }));

        // Silk Cloth knapping
        putBoth(stack -> OreDictUtils.contains(stack, "clothSilk"), ((worldIn, playerIn, handIn) -> {
            if (OreDictUtils.playerHasItemMatchingOre(playerIn.inventory, "shears")) {
                if (!worldIn.isRemote) {
                    GuiHandler.openGui(worldIn, playerIn, GuiHandler.Type.SILK_CLOTH);
                }
                return EnumActionResult.SUCCESS;
            }
            return EnumActionResult.FAIL;
        }));

        // Sisal Cloth knapping
        putBoth(stack -> OreDictUtils.contains(stack, "clothSisal"), ((worldIn, playerIn, handIn) -> {
            if (OreDictUtils.playerHasItemMatchingOre(playerIn.inventory, "shears")) {
                if (!worldIn.isRemote) {
                    GuiHandler.openGui(worldIn, playerIn, GuiHandler.Type.SISAL_CLOTH);
                }
                return EnumActionResult.SUCCESS;
            }
            return EnumActionResult.FAIL;
        }));

        // Cotton Cloth knapping
        putBoth(stack -> OreDictUtils.contains(stack, "clothCotton"), ((worldIn, playerIn, handIn) -> {
            if (OreDictUtils.playerHasItemMatchingOre(playerIn.inventory, "shears")) {
                if (!worldIn.isRemote) {
                    GuiHandler.openGui(worldIn, playerIn, GuiHandler.Type.COTTON_CLOTH);
                }
                return EnumActionResult.SUCCESS;
            }
            return EnumActionResult.FAIL;
        }));

        // Linen Cloth knapping
        putBoth(stack -> OreDictUtils.contains(stack, "clothLinen"), ((worldIn, playerIn, handIn) -> {
            if (OreDictUtils.playerHasItemMatchingOre(playerIn.inventory, "shears")) {
                if (!worldIn.isRemote) {
                    GuiHandler.openGui(worldIn, playerIn, GuiHandler.Type.LINEN_CLOTH);
                }
                return EnumActionResult.SUCCESS;
            }
            return EnumActionResult.FAIL;
        }));

        // Hemp Cloth knapping
        putBoth(stack -> OreDictUtils.contains(stack, "clothHemp"), ((worldIn, playerIn, handIn) -> {
            if (OreDictUtils.playerHasItemMatchingOre(playerIn.inventory, "shears")) {
                if (!worldIn.isRemote) {
                    GuiHandler.openGui(worldIn, playerIn, GuiHandler.Type.HEMP_CLOTH);
                }
                return EnumActionResult.SUCCESS;
            }
            return EnumActionResult.FAIL;
        }));

        // Yucca Canvas knapping
        putBoth(stack -> OreDictUtils.contains(stack, "canvasYucca"), ((worldIn, playerIn, handIn) -> {
            if (OreDictUtils.playerHasItemMatchingOre(playerIn.inventory, "shears")) {
                if (!worldIn.isRemote) {
                    GuiHandler.openGui(worldIn, playerIn, GuiHandler.Type.YUCCA_CANVAS);
                }
                return EnumActionResult.SUCCESS;
            }
            return EnumActionResult.FAIL;
        }));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (!PROCESSING_INTERACTION.get()) {
            PROCESSING_INTERACTION.set(true);
            IRightClickBlockAction action = InteractionManagerTFCF.findItemUseAction(event.getItemStack());
            if (event.getItemStack().getItem() instanceof ItemSeedsTFC) {
                // Use alternative handling
                EnumActionResult result;
                if (event.getSide() == Side.CLIENT) {
                    result = ClientInteractionManagerTFCF.processRightClickBlock(event);
                } else {
                    result = ServerInteractionManagerTFCF.processRightClickBlock(event);
                }
                event.setCancellationResult(result);
                event.setCanceled(true);
            }
            PROCESSING_INTERACTION.set(false);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRightClickItem(PlayerInteractEvent.RightClickItem event) {
        IRightClickItemAction action = InteractionManagerTFCF.findItemRightClickAction(event.getItemStack());
        if (action != null) {
            // Use alternative handling
            EnumActionResult result;
            if (event.getSide() == Side.CLIENT) {
                result = ClientInteractionManagerTFCF.processRightClickItem(event, action);
            } else {
                result = ClientInteractionManagerTFCF.processRightClickItem(event, action);
            }
            event.setCancellationResult(result);
            event.setCanceled(true);
        }
    }

    @Nullable
    private static IRightClickBlockAction findItemUseAction(ItemStack stack) {
        return USE_ACTIONS.entrySet()
                .stream()
                .filter(e -> e.getKey().test(stack))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(null);
    }

    @Nullable
    private static IRightClickItemAction findItemRightClickAction(ItemStack stack) {
        return RIGHT_CLICK_ACTIONS.entrySet()
                .stream()
                .filter(e -> e.getKey().test(stack))
                .findFirst()
                .map(Map.Entry::getValue)
                .orElse(null);
    }

    private static void putBoth(Predicate<ItemStack> predicate, IRightClickItemAction minorAction) {
        USE_ACTIONS.put(predicate, minorAction);
        RIGHT_CLICK_ACTIONS.put(predicate, minorAction);
    }
}
