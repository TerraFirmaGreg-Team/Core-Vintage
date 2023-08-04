package net.dries007.tfc.mixins.gregtech;

import gregtech.api.GTValues;
import gregtech.api.items.toolitem.*;
import gregtech.common.items.ToolItems;
import net.dries007.tfc.compat.gregtech.items.tools.behaviors.HoeBehavior;
import net.minecraft.init.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ToolItems.class, remap = false)
public class ToolItemsMixin {

	@Redirect(method = "init", at = @At(value = "INVOKE", target = "Lgregtech/common/items/ToolItems;register(Lgregtech/api/items/toolitem/ToolBuilder;)Lgregtech/api/items/toolitem/IGTTool;", ordinal = 4))
	private static IGTTool init(ToolBuilder<?> builder) {
		return ToolItems.register(ItemGTHoe.Builder.of(GTValues.MODID, "hoe")
			.toolStats(b ->
				b.cannotAttack()
					.attackSpeed(-1.0F)
					.behaviors(HoeBehavior.INSTANCE)
			)
			.sound(SoundEvents.ITEM_HOE_TILL)
			.toolClasses(ToolClasses.HOE)
			.oreDict(ToolOreDict.toolHoe));
	}
}
