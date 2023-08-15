package net.dries007.tfc.client;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import static net.dries007.tfc.TerraFirmaCraft.MOD_NAME;

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
}
