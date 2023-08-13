package net.dries007.tfc.api.types.agriculture;

import net.dries007.tfc.TerraFirmaCraft;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import static net.dries007.tfc.TerraFirmaCraft.MOD_ID;

public interface IGrowingPlant {
    GrowthStatus getGrowingStatus(IBlockState state, World world, BlockPos pos);

    enum GrowthStatus {
        /**
         * The plant is dead.
         */
        DEAD,
        /**
         * The plant is done growing.
         */
        GROWING,
        /**
         * The plant is done growing.
         */
        FULLY_GROWN,
        /**
         * If the plant would grow but requires something, like a specific season.
         */
        CAN_GROW,
        /**
         * The plant cannot grow at the moment.
         */
        NOT_GROWING;

        @Override
        public String toString() {
            return MOD_ID + ".enum.growstatus." + name().toLowerCase();
        }
    }

}
