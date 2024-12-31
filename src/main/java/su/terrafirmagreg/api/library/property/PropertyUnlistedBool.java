package su.terrafirmagreg.api.library.property;

import net.minecraftforge.common.property.IUnlistedProperty;

public class PropertyUnlistedBool implements IUnlistedProperty<Boolean> {

  private final String name;

  public PropertyUnlistedBool(String name) {
    this.name = name;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public boolean isValid(Boolean value) {
    return value;
  }

  @Override
  public Class<Boolean> getType() {
    return Boolean.class;
  }

  @Override
  public String valueToString(Boolean value) {
    return value.toString();
  }
}
