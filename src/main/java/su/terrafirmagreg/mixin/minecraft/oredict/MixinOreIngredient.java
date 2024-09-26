package su.terrafirmagreg.mixin.minecraft.oredict;

import net.minecraftforge.oredict.OreIngredient;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = OreIngredient.class, remap = false)
public class MixinOreIngredient {

  @Unique
  private String oreDict;

  @Inject(at = @At("TAIL"), method = "<init>(Ljava/lang/String;)V")
  private void init(String ore, CallbackInfo ci) {
    oreDict = ore;
  }

  @Unique
  public String getOreDict() {
    return oreDict;
  }
}
