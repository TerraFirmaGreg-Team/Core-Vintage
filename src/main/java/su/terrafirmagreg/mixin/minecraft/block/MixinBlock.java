package su.terrafirmagreg.mixin.minecraft.block;

import su.terrafirmagreg.api.spi.block.ISettingsBlock;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.registries.IForgeRegistryEntry;


import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Block.class)
public abstract class MixinBlock extends IForgeRegistryEntry.Impl<Block> implements ISettingsBlock {

    @Unique
    protected final Settings settings = Settings.of();

    @Override
    public Settings getSettings() {
        return settings;
    }

    /**
     * @author
     * @reason
     */
    @Deprecated
    @Overwrite
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return settings != null && settings.isOpaque();
    }
    //
    //    /**
    //     * @author
    //     * @reason
    //     */
    //    @Deprecated
    //    @Overwrite
    //    public boolean isFullCube(IBlockState state) {
    //        return settings.isFullCube();
    //    }
    //
    //    /**
    //     * @author
    //     * @reason
    //     */
    //    @Overwrite
    //    public boolean isCollidable() {
    //        return settings.isCollidable();
    //    }
    //
    //    /**
    //     * @author
    //     * @reason
    //     */
    //    @Deprecated
    //    @Overwrite
    //    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
    //        return this.isOpaqueCube(state) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
    //    }
    //
    //    /**
    //     * @author
    //     * @reason
    //     */
    //    @Deprecated
    //    @Overwrite
    //    public MapColor getMapColor(IBlockState state, IBlockAccess world, BlockPos pos) {
    //        return settings.getMapColor() != null ? settings.getMapColor().apply(state, world, pos) : settings.getMaterial().getMaterialMapColor();
    //    }
}
