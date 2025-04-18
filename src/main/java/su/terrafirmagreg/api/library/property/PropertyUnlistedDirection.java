package su.terrafirmagreg.api.library.property;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.property.IUnlistedProperty;

public class PropertyUnlistedDirection implements IUnlistedProperty<EnumFacing> {

  private final String name;

  protected PropertyUnlistedDirection(String name) {
    this.name = name;
  }

  public static PropertyUnlistedDirection create(String name) {
    return new PropertyUnlistedDirection(name);
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean isValid(EnumFacing value) {
    return value != null;
  }

  @Override
  public Class<EnumFacing> getType() {
    return EnumFacing.class;
  }

  @Override
  public String valueToString(EnumFacing value) {
    return value.getName();
  }
}
