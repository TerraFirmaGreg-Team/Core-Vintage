package su.terrafirmagreg.api.library.serialization.array;

import su.terrafirmagreg.api.library.Pair;
import su.terrafirmagreg.api.util.BufUtils.BufReader;
import su.terrafirmagreg.api.util.BufUtils.BufWriter;

import io.netty.buffer.ByteBuf;

import java.lang.reflect.Array;

@SuppressWarnings({"rawtypes", "unchecked"})
public class ArrayBufSerializer implements BufWriter<Object>, BufReader<Object> {

  private final Class componentClazz;
  private final BufReader reader;
  private final BufWriter writer;

  public ArrayBufSerializer(Class componentClazz, Pair<BufReader, BufWriter> handler) {
    this.componentClazz = componentClazz;
    this.reader = handler.getLeft();
    this.writer = handler.getRight();
  }

  @Override
  public void write(Object array, ByteBuf buf) {
    int length = Array.getLength(array);
    buf.writeInt(length);
    for (int i = 0; i < length; i++) {
      writer.write(Array.get(array, i), buf);
    }
  }

  @Override
  public Object read(ByteBuf buf) {
    int length = buf.readInt();
    Object array = Array.newInstance(componentClazz, length);
    if (length == 0) {return array;}

    for (int i = 0; i < length; i++) {
      Array.set(array, i, reader.read(buf));
    }
    return array;
  }
}
