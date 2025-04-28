package su.terrafirmagreg.api.library.serialization;

import su.terrafirmagreg.api.util.BufUtils.BufReader;
import su.terrafirmagreg.api.util.BufUtils.BufWriter;

import io.netty.buffer.ByteBuf;

@SuppressWarnings({"rawtypes"})
public class EnumBufSerializer implements BufWriter<Enum>, BufReader<Enum> {

  private final Enum[] values;

  public EnumBufSerializer(Class clazz) {
    values = (Enum[]) clazz.getEnumConstants();
  }

  @Override
  public void write(Enum value, ByteBuf buf) {
    buf.writeInt(value.ordinal());
  }

  @Override
  public Enum read(ByteBuf buf) {
    return values[buf.readInt()];
  }
}
