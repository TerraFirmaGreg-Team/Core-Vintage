package su.terrafirmagreg.api.library.tag;


import su.terrafirmagreg.api.util.ModUtils;

import net.minecraft.util.ResourceLocation;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

import org.jetbrains.annotations.NotNull;


public class TagKey extends ResourceLocation {

  private static final Interner<TagKey> INTERNER = Interners.newStrongInterner();

  private TagKey(String namespaceIn, String pathIn) {
    super(namespaceIn, pathIn);
  }


  public static TagKey of(ResourceLocation tagId) {
    return INTERNER.intern(new TagKey(tagId.getNamespace(), tagId.getPath()));
  }

  public static TagKey of(String tagId) {
    return of(ModUtils.resource(tagId));
  }

  public static TagKey oredict(String oreName) {
    String reformattedName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, oreName);
    return of(ModUtils.resource("oredict/" + reformattedName));
  }

  @Override
  public int hashCode() {
    return 38 * this.namespace.hashCode() * this.path.hashCode();
  }

  @Override
  public @NotNull String toString() {
    return '#' + this.namespace + ':' + this.path;
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    return super.equals(other) && (other instanceof TagKey);
  }
}
