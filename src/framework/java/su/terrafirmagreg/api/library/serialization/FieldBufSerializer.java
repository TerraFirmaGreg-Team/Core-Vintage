package su.terrafirmagreg.api.library.serialization;

import su.terrafirmagreg.api.util.BufUtils.BufReader;
import su.terrafirmagreg.api.util.BufUtils.BufWriter;

import io.netty.buffer.ByteBuf;

import lombok.Getter;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;

@SuppressWarnings({"rawtypes", "unchecked"})
public class FieldBufSerializer {

  @Getter
  private final Field field;

  private final MethodHandle setter;
  private final BufReader reader;

  private final MethodHandle getter;
  private final BufWriter writer;

  public FieldBufSerializer(Field field, MethodHandle setter, BufReader reader, MethodHandle getter, BufWriter writer) {
    this.field = field;
    this.setter = setter;
    this.reader = reader;
    this.getter = getter;
    this.writer = writer;
  }

  public void deserialize(Object object, ByteBuf buf) throws Throwable {

    setter.invoke(object, reader.read(buf));
  }

  public void serialize(Object object, ByteBuf buf) throws Throwable {

    Object invoke = getter.invoke(object);
    writer.write(invoke, buf);
  }
}
