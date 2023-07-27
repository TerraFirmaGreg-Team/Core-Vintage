package net.dries007.tfc.compat.gregtech.items.tools;

import gregtech.api.GTValues;
import gregtech.api.items.toolitem.IGTTool;
import gregtech.api.items.toolitem.ItemGTTool;
import gregtech.common.items.ToolItems;
import gregtech.core.sound.GTSoundEvents;

public class TFGToolItems {
    public static IGTTool TONGS;
    public static IGTTool TUYERE;
    public static IGTTool CHISEL;
    public static IGTTool PROPICK;
    public static IGTTool JAVELIN;

    public static void preInit() {
        TUYERE = ToolItems.register(ItemGTTool.Builder.of(GTValues.MODID, "tuyere")
                .toolStats(b -> b.crafting().cannotAttack().attackSpeed(-2.4F))
                .oreDict("craftingToolTuyere")
                .sound(GTSoundEvents.SOFT_MALLET_TOOL) // todo
                .toolClasses("tuyere"));
        /*
        TONGS = ToolItems.register(ItemGTTool.Builder.of(GTValues.MODID, "tongs")
                .toolStats(b -> b.crafting().cannotAttack().attackSpeed(-2.4F))
                .oreDict("craftingToolTongs")
                .sound(GTSoundEvents.SOFT_MALLET_TOOL) // todo
                .toolClasses("tongs"));*/

        /*
        CHISEL = ToolItems.register(ItemGTTool.Builder.of(GTValues.MODID, "chisel")
                .toolStats(b -> b.crafting().cannotAttack().attackSpeed(-2.4F).behaviors(InWorldChiselingBehavior.INSTANCE))
                .oreDict("craftingToolChisel")
                .sound(GTSoundEvents.SOFT_MALLET_TOOL) // todo
                .toolClasses("chisel"));*/

        /*
        PROPICK = ToolItems.register(ItemGTTool.Builder.of(GTValues.MODID, "propick")
                .toolStats(b -> b.crafting().cannotAttack().attackSpeed(-2.4F).behaviors(PropickBehavior.INSTANCE))
                .oreDict("craftingToolPropick")
                .sound(GTSoundEvents.SOFT_MALLET_TOOL) // todo
                .toolClasses("propick"));*/

        /*
        JAVELIN = ToolItems.register(ItemGTTool.Builder.of(GTValues.MODID, "javelin")
                .toolStats(b -> b.crafting().cannotAttack().attackSpeed(-2.4F).behaviors(JavelinBehavior.INSTANCE))
                .oreDict("craftingToolJavelin")
                .sound(GTSoundEvents.SOFT_MALLET_TOOL) // todo
                .toolClasses("javelin"));*/
    }
}
