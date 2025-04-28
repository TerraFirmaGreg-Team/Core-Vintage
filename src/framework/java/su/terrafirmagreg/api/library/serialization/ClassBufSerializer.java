package su.terrafirmagreg.api.library.serialization;

import su.terrafirmagreg.api.base.network.packet.api.INetworkPacket;
import su.terrafirmagreg.api.helper.LoggingHelper;
import su.terrafirmagreg.api.util.BufUtils;
import su.terrafirmagreg.api.util.BufUtils.BufReader;
import su.terrafirmagreg.api.util.BufUtils.BufWriter;
import su.terrafirmagreg.api.util.ClassUtils;

import io.netty.buffer.ByteBuf;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SuppressWarnings({"rawtypes"})
public class ClassBufSerializer {

  public static final HashMap<Class, ClassBufSerializer> SERIALIZERS = new HashMap<>();

  private final Class clazz;
  private final FieldBufSerializer[] fieldBufSerializers;

  public ClassBufSerializer(Class clazz) {
    this.clazz = clazz;

    var fields = ClassUtils.getMutableFields(clazz);
    var fieldTypes = ClassUtils.getFieldTypes(fields);
    var handler = BufUtils.getHandler(fieldTypes);

    List<FieldBufSerializer> fieldBufSerializerList = new ArrayList<>();

    for (int i = 0; i < fields.length; i++) {
      Field field = fields[i];
      BufWriter writer = handler[i].getRight();
      if (writer == null) {
        LoggingHelper.LOGGER.error("Can't find buf writer for for field {} in class {}", field.getName(), clazz.getName());
        continue;
      }
      BufReader reader = handler[i].getLeft();
      if (reader == null) {
        LoggingHelper.LOGGER.error("Can't find buf reader for for field {} in class {}", field.getName(), clazz.getName());
        continue;
      }

      boolean accessible = field.isAccessible();
      if (!accessible) {field.setAccessible(true);}

      MethodHandle getter = ClassUtils.unreflectGetter(field);
      MethodHandle setter = ClassUtils.unreflectSetter(field);

      if (!accessible) {field.setAccessible(false);}

      if (getter != null && setter != null) {
        fieldBufSerializerList.add(new FieldBufSerializer(field, setter, reader, getter, writer));
      }
    }

    fieldBufSerializers = fieldBufSerializerList.toArray(new FieldBufSerializer[0]);
  }


  public static void read(INetworkPacket packet, ByteBuf buffer) {
    ClassBufSerializer serializer = SERIALIZERS.computeIfAbsent(packet.getClass(), ClassBufSerializer::new);
    serializer.fromBytes(packet, buffer);
  }

  public static void write(INetworkPacket packet, ByteBuf buffer) {
    ClassBufSerializer serializer = SERIALIZERS.computeIfAbsent(packet.getClass(), ClassBufSerializer::new);
    serializer.toBytes(packet, buffer);
  }


  public final void fromBytes(Object object, ByteBuf buf) {
    try {
      for (FieldBufSerializer serializer : fieldBufSerializers) {
        serializer.deserialize(object, buf);
      }
    } catch (Throwable throwable) {
      throw new RuntimeException("Field deserialization error in class " + clazz.getName(), throwable);
    }
  }

  public final void toBytes(Object object, ByteBuf buf) {
    try {
      for (FieldBufSerializer serializer : fieldBufSerializers) {
        serializer.serialize(object, buf);
      }
    } catch (Throwable throwable) {
      throw new RuntimeException("Field serialization error in class " + clazz.getName(), throwable);
    }
  }
}
