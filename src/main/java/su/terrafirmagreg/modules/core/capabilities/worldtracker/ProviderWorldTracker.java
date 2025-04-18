package su.terrafirmagreg.modules.core.capabilities.worldtracker;

import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.dries007.tfc.client.TFCSounds;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import su.terrafirmagreg.api.util.MathUtils;
import su.terrafirmagreg.modules.core.ConfigCore;
import su.terrafirmagreg.modules.core.capabilities.worldtracker.spi.CollapseData;
import su.terrafirmagreg.modules.core.feature.falling.FallingBlockManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProviderWorldTracker implements ICapabilityWorldTracker {

    private static final String TAG_COLLAPSES_IN_PROGRESS = "collapsesInProgress";

    private final List<CollapseData> collapsesInProgress;

    public ProviderWorldTracker() {
        this.collapsesInProgress = new ArrayList<>();
    }

    public void addCollapseData(CollapseData collapse) {
        collapsesInProgress.add(collapse);
    }

    public void tick(World world) {
        if (!world.isRemote) {
            if (!collapsesInProgress.isEmpty() && MathUtils.RNG.nextInt(20) == 0) {
                for (CollapseData collapse : collapsesInProgress) {
                    Set<BlockPos> updatedPositions = new ObjectOpenHashSet<>();
                    for (BlockPos posAt : collapse.nextPositions) {
                        // Check the current position for collapsing
                        IBlockState stateAt = world.getBlockState(posAt);
                        FallingBlockManager.Specification specAt = FallingBlockManager.getSpecification(stateAt);
                        if (specAt != null && specAt.isCollapsable() && FallingBlockManager.canFallThrough(
                                world, posAt.down(), Material.ROCK) &&
                                specAt.canCollapse(world, posAt)
                                && posAt.distanceSq(collapse.centerPos) < collapse.radiusSquared &&
                                MathUtils.RNG.nextFloat() < ConfigCore.MISC.FALLABLE.propagateCollapseChance) {
                            IBlockState fallState = specAt.getResultingState(stateAt);
                            world.setBlockState(posAt, fallState);
                            FallingBlockManager.checkFalling(world, posAt, fallState, true);
                            // This column has started to collapse. Mark the next block above as unstable for the "follow up"
                            updatedPositions.add(posAt.up());
                        }
                    }
                    collapse.nextPositions.clear();
                    if (!updatedPositions.isEmpty()) {
                        world.playSound(null, collapse.centerPos, TFCSounds.ROCK_SLIDE_SHORT,
                                SoundCategory.BLOCKS, 0.6f, 1.0f);
                        collapse.nextPositions.addAll(updatedPositions);
                        collapse.radiusSquared *= 0.8; // lower radius each successive time
                    }
                }
                collapsesInProgress.removeIf(collapse -> collapse.nextPositions.isEmpty());
            }
        }
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        NBTTagList list = new NBTTagList();
        for (CollapseData collapse : collapsesInProgress) {
            list.appendTag(collapse.serializeNBT());
        }
        nbt.setTag(TAG_COLLAPSES_IN_PROGRESS, list);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if (nbt != null) {
            collapsesInProgress.clear();
            NBTTagList list = nbt.getTagList(TAG_COLLAPSES_IN_PROGRESS, 10);
            for (int i = 0; i < list.tagCount(); i++) {
                collapsesInProgress.add(new CollapseData(list.getCompoundTagAt(i)));
            }
        }
    }

    @Override
    public boolean hasCapability(@NotNull Capability<?> capability, @Nullable EnumFacing enumFacing) {
        return capability == CapabilityWorldTracker.CAPABILITY;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <T> T getCapability(@NotNull Capability<T> capability, @Nullable EnumFacing enumFacing) {
        return hasCapability(capability, enumFacing) ? (T) this : null;
    }
}
