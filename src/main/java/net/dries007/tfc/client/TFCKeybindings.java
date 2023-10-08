package net.dries007.tfc.client;

import net.dries007.tfc.module.core.ModuleCore;
import net.dries007.tfc.module.core.network.SCPacketCycleItemMode;
import net.dries007.tfc.module.core.network.SCPacketOpenCraftingGui;
import net.dries007.tfc.module.core.network.SCPacketPlaceBlockSpecial;
import net.dries007.tfc.module.core.network.SCPacketStackFood;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.inventory.Slot;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import static net.dries007.tfc.Tags.MOD_ID;
import static net.dries007.tfc.Tags.MOD_NAME;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = MOD_ID)
@SideOnly(Side.CLIENT)
public class TFCKeybindings {
    public static final KeyBinding OPEN_CRAFTING_TABLE = new KeyBinding("tfc.key.craft", KeyConflictContext.IN_GAME, Keyboard.KEY_C, MOD_NAME);
    public static final KeyBinding PLACE_BLOCK = new KeyBinding("tfc.key.placeblock", KeyConflictContext.IN_GAME, Keyboard.KEY_V, MOD_NAME);
    public static final KeyBinding CHANGE_ITEM_MODE = new KeyBinding("tfc.key.itemmode", KeyConflictContext.IN_GAME, Keyboard.KEY_M, MOD_NAME);
    public static final KeyBinding STACK_FOOD = new KeyBinding("tfc.key.stack", KeyConflictContext.GUI, Keyboard.KEY_X, MOD_NAME);

    public static void onInit() {
        ClientRegistry.registerKeyBinding(OPEN_CRAFTING_TABLE);
        ClientRegistry.registerKeyBinding(PLACE_BLOCK);
        ClientRegistry.registerKeyBinding(CHANGE_ITEM_MODE);
        ClientRegistry.registerKeyBinding(STACK_FOOD);
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onKeyEvent(InputEvent event) {
        // todo: move this to a button on the inventory GUI
        if (TFCKeybindings.OPEN_CRAFTING_TABLE.isPressed()) {
            ModuleCore.PACKET_SERVICE.sendToServer(new SCPacketOpenCraftingGui());
        }
        if (TFCKeybindings.PLACE_BLOCK.isPressed()) {
            ModuleCore.PACKET_SERVICE.sendToServer(new SCPacketPlaceBlockSpecial());
        }
        if (TFCKeybindings.CHANGE_ITEM_MODE.isPressed()) {
            ModuleCore.PACKET_SERVICE.sendToServer(new SCPacketCycleItemMode());
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
                    ModuleCore.PACKET_SERVICE.sendToServer(new SCPacketStackFood(slotUnderMouse.slotNumber));
                }
            }
        }
    }
}
