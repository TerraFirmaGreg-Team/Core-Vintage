package net.dries007.tfc.api.types.wood;

import net.dries007.tfc.api.types.wood.util.IWoodBlock;
import net.dries007.tfc.objects.blocks.wood.*;
import net.dries007.tfc.objects.blocks.wood.tree.BlockWoodLeaves;
import net.dries007.tfc.objects.blocks.wood.tree.BlockWoodLog;
import net.dries007.tfc.objects.blocks.wood.tree.BlockWoodSapling;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;
import java.util.function.BiFunction;

public enum WoodVariant implements IStringSerializable {
    LOG(BlockWoodLog::new),
    //	STRIPPED_LOG(),
//	WOOD(),
//	STRIPPED_WOOD(),
    LEAVES(BlockWoodLeaves::new),
    PLANKS(BlockWoodPlanks::new),
    SAPLING(BlockWoodSapling::new),
    //	POTTED_SAPLING(),
    BOOKSHELF(BlockWoodBookshelf::new),
    DOOR(BlockWoodDoor::new),
    TRAPDOOR(BlockWoodTrapDoor::new),
    FENCE(BlockWoodFence::new),
    //	LOG_FENCE(),
    FENCE_GATE(BlockWoodFenceGate::new),
    BUTTON(BlockWoodButton::new),
    PRESSURE_PLATE(BlockWoodPressurePlate::new),
    SLAB_DOUBLE(BlockWoodSlab.Double::new),
    SLAB(BlockWoodSlab.Half::new),
    STAIRS(BlockWoodStairs::new),
    TOOL_RACK(BlockWoodToolRack::new),
    SUPPORT(BlockWoodSupport::new),
    WORKBENCH(BlockWoodWorkbench::new),
    CHEST_TRAP(BlockWoodChest::new),
    CHEST(BlockWoodChest::new),
    LOOM(BlockWoodLoom::new),
    //	SLUICE(),
//	SIGN(),
    BARREL(BlockWoodBarrel::new);
//	LECTERN(),
//	SCRIBING_TABLE();

    public static final WoodVariant[] VALUES = WoodVariant.values();
    private final BiFunction<WoodVariant, Wood, IWoodBlock> blockFactory;

    WoodVariant(BiFunction<WoodVariant, Wood, IWoodBlock> blockFactory) {
        this.blockFactory = blockFactory;
    }

    public IWoodBlock create(Wood wood) {
        return this.blockFactory.apply(this, wood);
    }

    /**
     * Возвращает имя перечисления в нижнем регистре.
     */
    @Nonnull
    @Override
    public String getName() {
        return name().toLowerCase();
    }
}
