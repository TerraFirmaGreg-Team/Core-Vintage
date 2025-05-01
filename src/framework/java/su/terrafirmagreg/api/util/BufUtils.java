package su.terrafirmagreg.api.util;

import su.terrafirmagreg.api.library.Pair;
import su.terrafirmagreg.api.library.serialization.EnumBufSerializer;
import su.terrafirmagreg.api.library.serialization.array.ArrayBufSerializer;

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
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings({"deprecation", "rawtypes", "unchecked"})
@UtilityClass
public final class BufUtils extends ByteBufUtils {

  private static final Map<Class<?>, Pair<BufReader, BufWriter>> HANDLERS = new Object2ObjectOpenHashMap<>();

  static {

    // Primitives
    addIOHandler(byte.class, BufUtils::readByte, BufUtils::writeByte);
    addIOHandler(short.class, BufUtils::readShort, BufUtils::writeShort);
    addIOHandler(int.class, BufUtils::readInt, BufUtils::writeInt);
    addIOHandler(long.class, BufUtils::readLong, BufUtils::writeLong);
    addIOHandler(float.class, BufUtils::readFloat, BufUtils::writeFloat);
    addIOHandler(double.class, BufUtils::readDouble, BufUtils::writeDouble);
    addIOHandler(boolean.class, BufUtils::readBoolean, BufUtils::writeBoolean);
    addIOHandler(char.class, BufUtils::readChar, BufUtils::writeChar);

    // Objects
    addIOHandler(String.class, BufUtils::readString, BufUtils::writeString);
    addIOHandler(NBTTagCompound.class, BufUtils::readNBT, BufUtils::writeNBT);
    addIOHandler(ItemStack.class, BufUtils::readItemStack, BufUtils::writeItemStack);
    addIOHandler(BlockPos.class, BufUtils::readBlockPos, BufUtils::writeBlockPos);
    addIOHandler(IBlockState.class, BufUtils::readState, BufUtils::writeState);
    addIOHandler(ResourceLocation.class, BufUtils::readResourceLocation, BufUtils::writeResourceLocation);
    addIOHandler(EnchantmentData.class, BufUtils::readEnchantmentData, BufUtils::writeEnchantmentData);
    addIOHandler(Vec3d.class, BufUtils::readVec3d, BufUtils::writeVec3d);
    addIOHandler(Color3f.class, BufUtils::readColor3f, BufUtils::writeColor3f);
    addIOHandler(ITextComponent.class, BufUtils::readTextComponent, BufUtils::writeTextComponent);
  }

  /**
   * Adds a new read/write IO handler for messages.
   *
   * @param type   The type to add the IO handler for.
   * @param reader The reader function.
   * @param writer The writer function.
   */
  public static <T> void addIOHandler(Class<T> type, BufReader<T> reader, BufWriter<T> writer) {

    HANDLERS.put(type, Pair.of(reader, writer));
  }

  public static Pair<BufReader, BufWriter> getHandler(Type type) {
    return HANDLERS.computeIfAbsent(type.getClass(), aClass -> {

      if (type instanceof Class clazz) {
        if (clazz.isEnum()) {
          EnumBufSerializer serializer = new EnumBufSerializer(clazz);
          return Pair.of(serializer, serializer);
        }
        if (clazz.isArray()) {
          Class componentType = clazz.getComponentType();
          var pair = getHandler(componentType);
          var serializer = new ArrayBufSerializer(componentType, pair);
          return Pair.of(serializer, serializer);
        }
        Pair<BufReader, BufWriter> handler = HANDLERS.get(clazz);
        if (handler == null) {
          throw new RuntimeException("No R/W handler for type: " + clazz.getName());
        }
        return handler;
      }

      throw new RuntimeException("No R/W handler for type: " + type.getTypeName());
    });
  }

  public static Pair<BufReader, BufWriter>[] getHandler(Type[] types) {
    var pairs = new Pair[types.length];
    for (int i = 0; i < types.length; i++) {
      pairs[i] = getHandler(types[i]);
    }
    return pairs;
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

    return readUTF8String(buf);
  }

  public static void writeString(String s, ByteBuf buf) {

    writeUTF8String(buf, s);
  }


  public static NBTTagCompound readNBT(ByteBuf buf) {

    return readTag(buf);
  }


  public static void writeNBT(NBTTagCompound cmp, ByteBuf buf) {

    writeTag(buf, cmp);
  }


  public static ItemStack readItemStack(ByteBuf buf) {

    return ByteBufUtils.readItemStack(buf);
  }

  public static void writeItemStack(ItemStack stack, ByteBuf buf) {

    writeItemStack(buf, stack);
  }


  public static BlockPos readBlockPos(ByteBuf buf) {

    return BlockPos.fromLong(buf.readLong());
  }


  public static void writeBlockPos(BlockPos pos, ByteBuf buf) {

    buf.writeLong(pos.toLong());
  }


  public static ResourceLocation readResourceLocation(ByteBuf buf) {

    return new ResourceLocation(readUTF8String(buf));
  }

  public static void writeResourceLocation(ResourceLocation location, ByteBuf buf) {

    writeUTF8String(buf, location.toString());
  }

  @SuppressWarnings("deprecated")
  public static IBlockState readState(ByteBuf buf) {

    final Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(readUTF8String(buf)));
    final int meta = buf.readByte();
    return block != null ? block.getStateFromMeta(meta) : Blocks.AIR.getDefaultState();
  }


  public static void writeState(IBlockState state, ByteBuf buf) {

    writeUTF8String(buf, Objects.requireNonNull(state.getBlock().getRegistryName()).toString());
    buf.writeByte(state.getBlock().getMetaFromState(state));
  }

  public static EnchantmentData readEnchantmentData(ByteBuf buf) {

    final ResourceLocation id = new ResourceLocation(readUTF8String(buf));
    final int level = buf.readInt();
    return new EnchantmentData(Objects.requireNonNull(ForgeRegistries.ENCHANTMENTS.getValue(id)), level);
  }

  public static void writeEnchantmentData(EnchantmentData data, ByteBuf buf) {

    writeUTF8String(buf, data.enchantment != null && data.enchantment.getRegistryName() != null
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

  @FunctionalInterface
  public interface BufWriter<T> {

    void write(T t, ByteBuf buf);
  }

  @FunctionalInterface
  public interface BufReader<T> {

    T read(ByteBuf buf);
  }
}
