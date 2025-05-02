package su.terrafirmagreg.api.util;

import su.terrafirmagreg.api.library.Pair;
import su.terrafirmagreg.framework.manager.network.NetworkManager;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

import lombok.experimental.UtilityClass;

import javax.vecmath.Color3f;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings({"deprecation", "rawtypes", "unchecked"})
@UtilityClass
public final class BufUtils extends ByteBufUtils {

  public static final Map<Class<?>, Pair<BufReader, BufWriter>> HANDLERS = new Object2ObjectOpenHashMap<>();
  private static final Map<Class<?>, Pair<BufReader, BufWriter>> HANDLER_CACHE = new ConcurrentHashMap<>();

  static {

    // Primitives
    addHandler(byte.class, BufUtils::readByte, BufUtils::writeByte);
    addHandler(short.class, BufUtils::readShort, BufUtils::writeShort);
    addHandler(int.class, BufUtils::readInt, BufUtils::writeInt);
    addHandler(long.class, BufUtils::readLong, BufUtils::writeLong);
    addHandler(float.class, BufUtils::readFloat, BufUtils::writeFloat);
    addHandler(double.class, BufUtils::readDouble, BufUtils::writeDouble);
    addHandler(boolean.class, BufUtils::readBoolean, BufUtils::writeBoolean);
    addHandler(char.class, BufUtils::readChar, BufUtils::writeChar);

    // Primitive Arrays
    addHandler(byte[].class, BufUtils::readByteArray, BufUtils::writeByteArray);
    addHandler(short[].class, BufUtils::readShortArray, BufUtils::writeShortArray);
    addHandler(int[].class, BufUtils::readIntArray, BufUtils::writeIntArray);
    addHandler(long[].class, BufUtils::readLongArray, BufUtils::writeLongArray);
    addHandler(float[].class, BufUtils::readFloatArray, BufUtils::writeFloatArray);
    addHandler(double[].class, BufUtils::readDoubleArray, BufUtils::writeDoubleArray);
    addHandler(boolean[].class, BufUtils::readBooleanArray, BufUtils::writeBooleanArray);
    addHandler(char[].class, BufUtils::readCharArray, BufUtils::writeCharArray);

    // Objects
    addHandler(String.class, BufUtils::readString, BufUtils::writeString);
    addHandler(NBTTagCompound.class, BufUtils::readNBT, BufUtils::writeNBT);
    addHandler(ItemStack.class, BufUtils::readItemStack, BufUtils::writeItemStack);
    addHandler(BlockPos.class, BufUtils::readBlockPos, BufUtils::writeBlockPos);
    addHandler(IBlockState.class, BufUtils::readState, BufUtils::writeState);
    addHandler(ResourceLocation.class, BufUtils::readResourceLocation, BufUtils::writeResourceLocation);
    addHandler(EnchantmentData.class, BufUtils::readEnchantmentData, BufUtils::writeEnchantmentData);
    addHandler(Vec3d.class, BufUtils::readVec3d, BufUtils::writeVec3d);
    addHandler(Color3f.class, BufUtils::readColor3f, BufUtils::writeColor3f);
    addHandler(ITextComponent.class, BufUtils::readTextComponent, BufUtils::writeTextComponent);

    // Object Arrays
    addHandler(String[].class, BufUtils::readStringArray, BufUtils::writeStringArray);
    addHandler(NBTTagCompound[].class, BufUtils::readNBTArray, BufUtils::writeNBTArray);
    addHandler(ItemStack[].class, BufUtils::readItemStackArray, BufUtils::writeItemStackArray);
    addHandler(BlockPos[].class, BufUtils::readBlockPosArray, BufUtils::writeBlockPosArray);
    addHandler(IBlockState[].class, BufUtils::readStateArray, BufUtils::writeStateArray);
    addHandler(ResourceLocation[].class, BufUtils::readResourceLocationArray, BufUtils::writeResourceLocationArray);
    addHandler(EnchantmentData[].class, BufUtils::readEnchantmentDataArray, BufUtils::writeEnchantmentDataArray);
    addHandler(Vec3d[].class, BufUtils::readVec3dArray, BufUtils::writeVec3dArray);
    addHandler(Color3f[].class, BufUtils::readColor3fArray, BufUtils::writeColor3fArray);
    addHandler(ITextComponent[].class, BufUtils::readTextComponentArray, BufUtils::writeTextComponentArray);
  }

  /**
   * Adds a new read/write IO handler for messages.
   *
   * @param type   The type to add the IO handler for.
   * @param reader The reader function.
   * @param writer The writer function.
   */
  public static <T> void addHandler(Class<T> type, BufReader<T> reader, BufWriter<T> writer) {

    HANDLERS.put(type, Pair.of(reader, writer));
  }

  public static Pair<BufReader, BufWriter> getHandler(Class<?> type) {
    return HANDLER_CACHE.computeIfAbsent(type, aClass -> {
      Pair<BufReader, BufWriter> handler = HANDLERS.get(aClass);
      if (handler == null) {
        throw new RuntimeException("No R/W handler for type: " + aClass.getName());
      }
      return handler;
    });
  }

  public static void readField(Object target, Field field, ByteBuf buffer) {
    try {
      Pair<BufReader, BufWriter> handler = getHandler(field.getType());
      if (handler.getLeft() == null) {throw new IllegalStateException("No reader for field: " + field.getName());}
      Object value = handler.getLeft().read(buffer);
//      boolean accessible = field.isAccessible();
//      if (!accessible) {field.setAccessible(true);}
      ClassUtils.unreflectSetter(field).invoke(target, value);
//      if (!accessible) {field.setAccessible(false);}

    } catch (final Throwable e) {
      NetworkManager.LOGGER.error(e, "Error processing packet: {}", target.getClass().getSimpleName());
      throw new RuntimeException(e);
    }
  }

  public static void writeField(Object target, Field field, ByteBuf buffer) {
    try {
      Pair<BufReader, BufWriter> handler = getHandler(field.getType());
      if (handler.getRight() == null) {throw new IllegalStateException("No writer for field: " + field.getName());}

//      boolean accessible = field.isAccessible();
//      if (!accessible) {field.setAccessible(true);}
      Object value = ClassUtils.unreflectGetter(field).invoke(target);
//      if (!accessible) {field.setAccessible(false);}
      handler.getRight().write(value, buffer);

    } catch (final Throwable e) {
      NetworkManager.LOGGER.error(e, "Error processing packet: {}", target.getClass().getSimpleName());
      throw new RuntimeException(e);
    }

  }


  public static byte readByte(ByteBuf buf) {

    return buf.readByte();
  }

  public static void writeByte(byte b, ByteBuf buf) {

    buf.writeByte(b);
  }

  public static short readShort(ByteBuf buf) {

    return buf.readShort();
  }

  public static void writeShort(short s, ByteBuf buf) {

    buf.writeShort(s);
  }

  public static int readInt(ByteBuf buf) {

    return buf.readInt();
  }

  public static void writeInt(int i, ByteBuf buf) {

    buf.writeInt(i);
  }

  public static long readLong(ByteBuf buf) {

    return buf.readLong();
  }

  public static void writeLong(long l, ByteBuf buf) {

    buf.writeLong(l);
  }

  public static float readFloat(ByteBuf buf) {

    return buf.readFloat();
  }

  public static void writeFloat(float f, ByteBuf buf) {

    buf.writeFloat(f);
  }

  public static double readDouble(ByteBuf buf) {

    return buf.readDouble();
  }

  public static void writeDouble(double d, ByteBuf buf) {

    buf.writeDouble(d);
  }

  public static boolean readBoolean(ByteBuf buf) {

    return buf.readBoolean();
  }

  public static void writeBoolean(boolean b, ByteBuf buf) {

    buf.writeBoolean(b);
  }

  public static char readChar(ByteBuf buf) {

    return buf.readChar();
  }

  public static void writeChar(char c, ByteBuf buf) {

    buf.writeChar(c);
  }

  public static String readString(ByteBuf buf) {

    return ByteBufUtils.readUTF8String(buf);
  }

  public static void writeString(String s, ByteBuf buf) {

    ByteBufUtils.writeUTF8String(buf, s);
  }

  public static String[] readStringArray(ByteBuf buf) {

    final String[] strings = new String[buf.readInt()];

    for (int index = 0; index < strings.length; index++) {

      strings[index] = ByteBufUtils.readUTF8String(buf);
    }

    return strings;
  }

  public static void writeStringArray(String[] strings, ByteBuf buf) {

    buf.writeInt(strings.length);

    for (final String string : strings) {

      ByteBufUtils.writeUTF8String(buf, string);
    }
  }

  public static byte[] readByteArray(ByteBuf buf) {

    final byte[] objects = new byte[buf.readInt()];

    for (int index = 0; index < objects.length; index++) {

      objects[index] = buf.readByte();
    }

    return objects;
  }

  public static void writeByteArray(byte[] objects, ByteBuf buf) {

    buf.writeInt(objects.length);

    for (final byte object : objects) {

      buf.writeByte(object);
    }
  }

  public static short[] readShortArray(ByteBuf buf) {

    final short[] objects = new short[buf.readInt()];

    for (int index = 0; index < objects.length; index++) {

      objects[index] = buf.readShort();
    }

    return objects;
  }

  public static void writeShortArray(short[] objects, ByteBuf buf) {

    buf.writeInt(objects.length);

    for (final short object : objects) {

      buf.writeShort(object);
    }
  }

  public static int[] readIntArray(ByteBuf buf) {

    final int[] objects = new int[buf.readInt()];

    for (int index = 0; index < objects.length; index++) {

      objects[index] = buf.readInt();
    }

    return objects;
  }

  public static void writeIntArray(int[] objects, ByteBuf buf) {

    buf.writeInt(objects.length);

    for (final int object : objects) {

      buf.writeInt(object);
    }
  }

  public static long[] readLongArray(ByteBuf buf) {

    final long[] objects = new long[buf.readInt()];

    for (int index = 0; index < objects.length; index++) {

      objects[index] = buf.readLong();
    }

    return objects;
  }

  public static void writeLongArray(long[] objects, ByteBuf buf) {

    buf.writeInt(objects.length);

    for (final long object : objects) {

      buf.writeLong(object);
    }
  }

  public static float[] readFloatArray(ByteBuf buf) {

    final float[] objects = new float[buf.readInt()];

    for (int index = 0; index < objects.length; index++) {

      objects[index] = buf.readFloat();
    }

    return objects;
  }

  public static void writeFloatArray(float[] objects, ByteBuf buf) {

    buf.writeInt(objects.length);

    for (final float object : objects) {

      buf.writeFloat(object);
    }
  }

  public static double[] readDoubleArray(ByteBuf buf) {

    final double[] objects = new double[buf.readInt()];

    for (int index = 0; index < objects.length; index++) {

      objects[index] = buf.readDouble();
    }

    return objects;
  }

  public static void writeDoubleArray(double[] objects, ByteBuf buf) {

    buf.writeInt(objects.length);

    for (final double object : objects) {

      buf.writeDouble(object);
    }
  }

  public static boolean[] readBooleanArray(ByteBuf buf) {

    final boolean[] objects = new boolean[buf.readInt()];

    for (int index = 0; index < objects.length; index++) {

      objects[index] = buf.readBoolean();
    }

    return objects;
  }

  public static void writeBooleanArray(boolean[] objects, ByteBuf buf) {

    buf.writeInt(objects.length);

    for (final boolean object : objects) {

      buf.writeBoolean(object);
    }
  }

  public static char[] readCharArray(ByteBuf buf) {

    final char[] objects = new char[buf.readInt()];

    for (int index = 0; index < objects.length; index++) {

      objects[index] = buf.readChar();
    }

    return objects;
  }

  public static void writeCharArray(char[] objects, ByteBuf buf) {

    buf.writeInt(objects.length);

    for (final char object : objects) {

      buf.writeChar(object);
    }
  }

  public static NBTTagCompound[] readNBTArray(ByteBuf buf) {

    final NBTTagCompound[] objects = new NBTTagCompound[buf.readInt()];

    for (int index = 0; index < objects.length; index++) {

      objects[index] = readNBT(buf);
    }

    return objects;
  }

  public static NBTTagCompound readNBT(ByteBuf buf) {

    return ByteBufUtils.readTag(buf);
  }

  public static void writeNBTArray(NBTTagCompound[] objects, ByteBuf buf) {

    buf.writeInt(objects.length);

    for (final NBTTagCompound object : objects) {

      writeNBT(object, buf);
    }
  }


  public static void writeNBT(NBTTagCompound cmp, ByteBuf buf) {

    ByteBufUtils.writeTag(buf, cmp);
  }

  public static ItemStack[] readItemStackArray(ByteBuf buf) {

    final ItemStack[] objects = new ItemStack[buf.readInt()];

    for (int index = 0; index < objects.length; index++) {

      objects[index] = readItemStack(buf);
    }

    return objects;


  }

  public static ItemStack readItemStack(ByteBuf buf) {

    return ByteBufUtils.readItemStack(buf);
  }

  public static void writeItemStackArray(ItemStack[] objects, ByteBuf buf) {

    buf.writeInt(objects.length);

    for (final ItemStack object : objects) {

      writeItemStack(object, buf);
    }
  }

  public static void writeItemStack(ItemStack stack, ByteBuf buf) {

    ByteBufUtils.writeItemStack(buf, stack);
  }

  public static BlockPos[] readBlockPosArray(ByteBuf buf) {

    final BlockPos[] objects = new BlockPos[buf.readInt()];

    for (int index = 0; index < objects.length; index++) {

      objects[index] = readBlockPos(buf);
    }

    return objects;
  }

  public static BlockPos readBlockPos(ByteBuf buf) {

    return BlockPos.fromLong(buf.readLong());
  }

  public static void writeBlockPosArray(BlockPos[] objects, ByteBuf buf) {

    buf.writeInt(objects.length);

    for (final BlockPos object : objects) {

      writeBlockPos(object, buf);
    }
  }

  public static void writeBlockPos(BlockPos pos, ByteBuf buf) {

    buf.writeLong(pos.toLong());
  }

  public static ResourceLocation[] readResourceLocationArray(ByteBuf buf) {

    final ResourceLocation[] objects = new ResourceLocation[buf.readInt()];

    for (int index = 0; index < objects.length; index++) {

      objects[index] = readResourceLocation(buf);
    }

    return objects;
  }

  public static ResourceLocation readResourceLocation(ByteBuf buf) {

    return new ResourceLocation(ByteBufUtils.readUTF8String(buf));
  }

  public static void writeResourceLocationArray(ResourceLocation[] objects, ByteBuf buf) {

    buf.writeInt(objects.length);

    for (final ResourceLocation object : objects) {

      writeResourceLocation(object, buf);
    }
  }

  public static void writeResourceLocation(ResourceLocation location, ByteBuf buf) {

    ByteBufUtils.writeUTF8String(buf, location.toString());
  }

  public static IBlockState[] readStateArray(ByteBuf buf) {

    final IBlockState[] objects = new IBlockState[buf.readInt()];

    for (int index = 0; index < objects.length; index++) {

      objects[index] = readState(buf);
    }

    return objects;
  }

  @SuppressWarnings("deprecated")
  public static IBlockState readState(ByteBuf buf) {

    final Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ByteBufUtils.readUTF8String(buf)));
    final int meta = buf.readByte();
    return block != null ? block.getStateFromMeta(meta) : Blocks.AIR.getDefaultState();
  }

  public static void writeStateArray(IBlockState[] objects, ByteBuf buf) {

    buf.writeInt(objects.length);

    for (final IBlockState object : objects) {

      writeState(object, buf);
    }
  }

  public static void writeState(IBlockState state, ByteBuf buf) {

    ByteBufUtils.writeUTF8String(buf, Objects.requireNonNull(state.getBlock().getRegistryName()).toString());
    buf.writeByte(state.getBlock().getMetaFromState(state));
  }

  public static EnchantmentData[] readEnchantmentDataArray(ByteBuf buf) {

    final EnchantmentData[] objects = new EnchantmentData[buf.readInt()];

    for (int index = 0; index < objects.length; index++) {

      objects[index] = readEnchantmentData(buf);
    }

    return objects;
  }

  public static EnchantmentData readEnchantmentData(ByteBuf buf) {

    final ResourceLocation id = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
    final int level = buf.readInt();
    return new EnchantmentData(Objects.requireNonNull(ForgeRegistries.ENCHANTMENTS.getValue(id)), level);
  }

  public static void writeEnchantmentDataArray(EnchantmentData[] objects, ByteBuf buf) {

    buf.writeInt(objects.length);

    for (final EnchantmentData object : objects) {

      writeEnchantmentData(object, buf);
    }
  }

  public static void writeEnchantmentData(EnchantmentData data, ByteBuf buf) {

    ByteBufUtils.writeUTF8String(buf, data.enchantment != null && data.enchantment.getRegistryName() != null
                                      ? data.enchantment.getRegistryName().toString()
                                      : "invalid");
    buf.writeInt(data.enchantmentLevel);
  }

  public static void writeColor3f(Color3f value, ByteBuf buf) {
    buf.writeFloat(value.x);
    buf.writeFloat(value.y);
    buf.writeFloat(value.z);
  }

  public static Color3f readColor3f(ByteBuf buf) {
    float x = buf.readFloat();
    float y = buf.readFloat();
    float z = buf.readFloat();
    return new Color3f(x, y, z);
  }

  public static void writeColor3fArray(Color3f[] objects, ByteBuf buf) {

    buf.writeInt(objects.length);

    for (final Color3f object : objects) {

      writeColor3f(object, buf);
    }
  }

  public static Color3f[] readColor3fArray(ByteBuf buf) {

    final Color3f[] objects = new Color3f[buf.readInt()];

    for (int index = 0; index < objects.length; index++) {

      objects[index] = readColor3f(buf);
    }

    return objects;
  }


  public static void writeVec3d(Vec3d value, ByteBuf buf) {
    buf.writeDouble(value.x);
    buf.writeDouble(value.y);
    buf.writeDouble(value.z);
  }

  public static Vec3d readVec3d(ByteBuf buf) {
    double x = buf.readDouble();
    double y = buf.readDouble();
    double z = buf.readDouble();
    return new Vec3d(x, y, z);
  }

  public static void writeVec3dArray(Vec3d[] objects, ByteBuf buf) {

    buf.writeInt(objects.length);

    for (final Vec3d object : objects) {

      writeVec3d(object, buf);
    }
  }

  public static Vec3d[] readVec3dArray(ByteBuf buf) {

    final Vec3d[] objects = new Vec3d[buf.readInt()];

    for (int index = 0; index < objects.length; index++) {

      objects[index] = readVec3d(buf);
    }

    return objects;
  }

  public static void writeTextComponent(ITextComponent value, ByteBuf buf) {
    String json = ITextComponent.Serializer.componentToJson(value);
    buf.writeInt(json.length());
    buf.writeCharSequence(json, Charset.defaultCharset());
  }

  public static ITextComponent readTextComponent(ByteBuf buf) {
    int length = buf.readInt();
    String json = buf.readCharSequence(length, Charset.defaultCharset()).toString();
    return ITextComponent.Serializer.jsonToComponent(json);
  }

  public static void writeTextComponentArray(ITextComponent[] objects, ByteBuf buf) {
    buf.writeInt(objects.length);

    for (final ITextComponent object : objects) {

      writeTextComponent(object, buf);
    }
  }

  public static ITextComponent[] readTextComponentArray(ByteBuf buf) {
    final ITextComponent[] objects = new ITextComponent[buf.readInt()];
    for (int index = 0; index < objects.length; index++) {

      objects[index] = readTextComponent(buf);
    }
    return objects;
  }

  @FunctionalInterface
  public interface BufWriter<T> {

    void write(T t, ByteBuf buf);
  }

  @FunctionalInterface
  public interface BufReader<T> {

    T read(ByteBuf buf);
  }

}
