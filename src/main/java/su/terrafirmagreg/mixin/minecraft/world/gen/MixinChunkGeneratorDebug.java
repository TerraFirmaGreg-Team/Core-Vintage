package su.terrafirmagreg.mixin.minecraft.world.gen;

import net.minecraft.world.gen.ChunkGeneratorDebug;


import org.spongepowered.asm.mixin.Mixin;

@Mixin(value = ChunkGeneratorDebug.class, remap = false)
public abstract class MixinChunkGeneratorDebug {

}
