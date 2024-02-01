package su.terrafirmagreg.core.mixin.tfc.jei;

import net.minecraft.item.ItemStack;

import gregtech.api.GTValues;
import gregtech.api.unification.material.Materials;
import gregtech.common.items.ToolItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import net.dries007.tfc.TerraFirmaCraft;
import net.dries007.tfc.api.registries.TFCRegistries;
import net.dries007.tfc.api.types.Metal;
import net.dries007.tfc.api.types.Ore;
import net.dries007.tfc.api.types.Rock;
import net.dries007.tfc.compat.jei.TFCJEIPlugin;
import net.dries007.tfc.objects.blocks.stone.BlockOreTFC;
import net.dries007.tfc.objects.items.metal.ItemMetalTool;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Mixin(value = TFCJEIPlugin.class, remap = false)
public class TFCJEIPluginMixin implements IModPlugin {

    @Shadow
    @Final
    public static final String KNAP_CLAY_UID = TerraFirmaCraft.MOD_ID + ".knap.clay";
    @Shadow
    @Final
    public static final String SCRAPING_UID = TerraFirmaCraft.MOD_ID + ".scraping";

    private static final String GT_ORE_SPAWN_UID = GTValues.MODID + ":ore_spawn_location";

    @Inject(method = "register", at = @At(value = "TAIL"), remap = false)
    private void onRegister(IModRegistry registry, CallbackInfo ci) {

        // Hide TFC Ores in HEI
        IIngredientBlacklist blacklist = registry.getJeiHelpers().getIngredientBlacklist();
        for (Rock rock : TFCRegistries.ROCKS.getValuesCollection()) {
            for (Ore ore : TFCRegistries.ORES.getValuesCollection()) {
                blacklist.addIngredientToBlacklist(new ItemStack(BlockOreTFC.get(ore, rock)));
            }
        }

        // Add TFC Propicks to HEI GT ore spawn Tab
        List<Metal> tierOrdered = TFCRegistries.METALS.getValuesCollection()
                .stream()
                .sorted(Comparator.comparingInt(metal -> metal.getTier()
                        .ordinal()))
                .collect(Collectors.toList());

        for (Metal metal : tierOrdered)
            if (Metal.ItemType.PROPICK.hasType(metal))
                registry.addRecipeCatalyst(new ItemStack(ItemMetalTool.get(metal, Metal.ItemType.PROPICK)), GT_ORE_SPAWN_UID);

        // Add GT Knife to Knapping Tab
        registry.addRecipeCatalyst(ToolItems.KNIFE.get(Materials.Iron), SCRAPING_UID);
    }
}
