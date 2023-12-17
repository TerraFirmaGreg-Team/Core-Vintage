package com.lumintorious.tfcstacks;

import net.dries007.tfc.api.capability.size.Weight;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.lang.reflect.Field;

@Mod(modid = TFCStacks.MODID, name = TFCStacks.NAME, version = TFCStacks.VERSION)
public class TFCStacks {
    public static final String MODID = "tfcstacks";
    public static final String NAME = "TFC Stacks";
    public static final String VERSION = "1.0";

    public static void setStackSize(Weight weight, int stackSize) {
        try {
            Class<?> c = Weight.class;
            Field field = c.getDeclaredField("stackSize");
            field.setAccessible(true);
            field.set(weight, stackSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        setStackSize(Weight.VERY_HEAVY, ConfigTFCStacks.GENERAL.VERY_HEAVY);
        setStackSize(Weight.HEAVY, ConfigTFCStacks.GENERAL.HEAVY);
        setStackSize(Weight.MEDIUM, ConfigTFCStacks.GENERAL.MEDIUM);
        setStackSize(Weight.LIGHT, ConfigTFCStacks.GENERAL.LIGHT);
        setStackSize(Weight.VERY_LIGHT, ConfigTFCStacks.GENERAL.VERY_LIGHT);
    }
}
