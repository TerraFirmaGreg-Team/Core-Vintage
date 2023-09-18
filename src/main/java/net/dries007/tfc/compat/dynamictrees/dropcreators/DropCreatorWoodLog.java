package net.dries007.tfc.compat.dynamictrees.dropcreators;

import com.ferreusveritas.dynamictrees.systems.dropcreators.DropCreator;
import com.ferreusveritas.dynamictrees.trees.Species;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.module.wood.common.blocks.BlockWoodLog;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;


public class DropCreatorWoodLog extends DropCreator {
    public DropCreatorWoodLog() {
        super(TerraFirmaCraft.identifier("logs"));
    }

    @Override
    public List<ItemStack> getLogsDrop(World world, Species species, BlockPos breakPos, Random random, List<ItemStack> dropList, float volume) {
        Species.LogsAndSticks las = species.getLogsAndSticks(volume);

        var logs = species.getFamily().getPrimitiveLogItemStack(1);
        var block = species.getFamily().getPrimitiveLog().getBlock();
        int stackSize = ((BlockWoodLog) block).getStackSize(logs);

        int numLogs = las.logs;
        while (numLogs > 0) {
            dropList.add(species.getFamily().getPrimitiveLogItemStack(Math.min(numLogs, stackSize)));
            numLogs -= stackSize;
        }
        int numSticks = las.sticks;
        if (numSticks > 0) dropList.add(species.getFamily().getStick(numSticks));

        return dropList;
    }

}
