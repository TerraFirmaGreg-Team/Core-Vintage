package su.terrafirmagreg.core.mixin.tfc;

import net.dries007.tfc.network.PacketProspectResult;
import net.dries007.tfc.objects.items.metal.ItemProspectorPick;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value = PacketProspectResult.class, remap = false)
public interface IPacketProspectResultAccessor {
	@Accessor
	BlockPos getPos();

	@Accessor
	ItemProspectorPick.ProspectResult.Type getType();

	@Accessor
	ItemStack getVein();
}
