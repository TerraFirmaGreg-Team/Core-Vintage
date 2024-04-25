package su.terrafirmagreg.api.spi.packet;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.common.registry.ForgeRegistries;


import io.netty.buffer.ByteBuf;
import org.apache.commons.lang3.tuple.Pair;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("rawtypes")
public abstract class PacketSerializable<REQ extends PacketSerializable> implements Serializable, IMessage, IMessageHandler<REQ, IMessage> {

    /**
     * The serial version for this UID.
     */
    private static final long serialVersionUID = 3214832642504369023L;

    /**
     * A map which holds all the serialization handlers.
     */
    private static final Map<Class<?>, Pair<Reader, Writer>> handlers = new HashMap<>();

    /**
     * A cache of fields in a given class for faster lookup.
     */
    private static final Map<Class<?>, Field[]> fieldCache = new HashMap<>();

    static {

        // Primitives
        addIOHandler(byte.class, PacketSerializable::readByte, PacketSerializable::writeByte);
        addIOHandler(short.class, PacketSerializable::readShort, PacketSerializable::writeShort);
        addIOHandler(int.class, PacketSerializable::readInt, PacketSerializable::writeInt);
        addIOHandler(long.class, PacketSerializable::readLong, PacketSerializable::writeLong);
        addIOHandler(float.class, PacketSerializable::readFloat, PacketSerializable::writeFloat);
        addIOHandler(double.class, PacketSerializable::readDouble, PacketSerializable::writeDouble);
        addIOHandler(boolean.class, PacketSerializable::readBoolean, PacketSerializable::writeBoolean);
        addIOHandler(char.class, PacketSerializable::readChar, PacketSerializable::writeChar);

        // Primitive Arrays
        addIOHandler(byte[].class, PacketSerializable::readByteArray, PacketSerializable::writeByteArray);
        addIOHandler(short[].class, PacketSerializable::readShortArray, PacketSerializable::writeShortArray);
        addIOHandler(int[].class, PacketSerializable::readIntArray, PacketSerializable::writeIntArray);
        addIOHandler(long[].class, PacketSerializable::readLongArray, PacketSerializable::writeLongArray);
        addIOHandler(float[].class, PacketSerializable::readFloatArray, PacketSerializable::writeFloatArray);
        addIOHandler(double[].class, PacketSerializable::readDoubleArray, PacketSerializable::writeDoubleArray);
        addIOHandler(boolean[].class, PacketSerializable::readBooleanArray, PacketSerializable::writeBooleanArray);
        addIOHandler(char[].class, PacketSerializable::readCharArray, PacketSerializable::writeCharArray);

        // Objects
        addIOHandler(String.class, PacketSerializable::readString, PacketSerializable::writeString);
        addIOHandler(NBTTagCompound.class, PacketSerializable::readNBT, PacketSerializable::writeNBT);
        addIOHandler(ItemStack.class, PacketSerializable::readItemStack, PacketSerializable::writeItemStack);
        addIOHandler(BlockPos.class, PacketSerializable::readBlockPos, PacketSerializable::writeBlockPos);
        addIOHandler(IBlockState.class, PacketSerializable::readState, PacketSerializable::writeState);
        addIOHandler(ResourceLocation.class, PacketSerializable::readResourceLocation, PacketSerializable::writeResourceLocation);
        addIOHandler(EnchantmentData.class, PacketSerializable::readEnchantmentData, PacketSerializable::writeEnchantmentData);

        // Object Arrays
        addIOHandler(String[].class, PacketSerializable::readStringArray, PacketSerializable::writeStringArray);
        addIOHandler(NBTTagCompound[].class, PacketSerializable::readNBTArray, PacketSerializable::writeNBTArray);
        addIOHandler(ItemStack[].class, PacketSerializable::readItemStackArray, PacketSerializable::writeItemStackArray);
        addIOHandler(BlockPos[].class, PacketSerializable::readBlockPosArray, PacketSerializable::writeBlockPosArray);
        addIOHandler(IBlockState[].class, PacketSerializable::readStateArray, PacketSerializable::writeStateArray);
        addIOHandler(ResourceLocation[].class, PacketSerializable::readResourceLocationArray, PacketSerializable::writeResourceLocationArray);
        addIOHandler(EnchantmentData[].class, PacketSerializable::readEnchantmentDataArray, PacketSerializable::writeEnchantmentDataArray);
    }

    /**
     * Gets an array of fields from a class. These arrays will be cached.
     *
     * @param clazz The class to get fields for.
     * @return An array of fields held by the class.
     */
    private static Field[] getClassFields(Class<?> clazz) {

        if (fieldCache.containsValue(clazz)) {
            return fieldCache.get(clazz);
        } else {
            final Field[] fields = clazz.getFields();
            Arrays.sort(fields, (Field f1, Field f2) -> {
                return f1.getName().compareTo(f2.getName());
            });
            fieldCache.put(clazz, fields);
            return fields;
        }
    }

    /**
     * Gets the read/write handler for a type.
     *
     * @param type The type to get the IO handler for.
     * @return The read and write handler for the class.
     */
    private static Pair<Reader, Writer> getHandler(Class<?> type) {

        final Pair<Reader, Writer> pair = handlers.get(type);
        if (pair == null) {
            throw new RuntimeException("No R/W handler for  " + type);
        }
        return pair;
    }

    /**
     * Checks if a field is acceptable for message IO.
     *
     * @param field The field to check.
     * @param type  The type for the field.
     * @return Whether or not the field should be accepted.
     */
    private static boolean acceptField(Field field, Class<?> type) {

        final int mods = field.getModifiers();
        if (Modifier.isFinal(mods) || Modifier.isStatic(mods) || Modifier.isTransient(mods)) {
            return false;
        }

        return handlers.containsKey(type);
    }

    /**
     * Adds a new read/write IO handler for messages.
     *
     * @param type   The type to add the IO handler for.
     * @param reader The reader function.
     * @param writer The writer function.
     */
    public static <T extends Object> void addIOHandler(Class<T> type, Reader<T> reader, Writer<T> writer) {

        handlers.put(type, Pair.of(reader, writer));
    }

    private static byte readByte(ByteBuf buf) {

        return buf.readByte();
    }

    private static void writeByte(byte b, ByteBuf buf) {

        buf.writeByte(b);
    }

    private static short readShort(ByteBuf buf) {

        return buf.readShort();
    }

    private static void writeShort(short s, ByteBuf buf) {

        buf.writeShort(s);
    }

    private static int readInt(ByteBuf buf) {

        return buf.readInt();
    }

    private static void writeInt(int i, ByteBuf buf) {

        buf.writeInt(i);
    }

    private static long readLong(ByteBuf buf) {

        return buf.readLong();
    }

    private static void writeLong(long l, ByteBuf buf) {

        buf.writeLong(l);
    }

    private static float readFloat(ByteBuf buf) {

        return buf.readFloat();
    }

    private static void writeFloat(float f, ByteBuf buf) {

        buf.writeFloat(f);
    }

    private static double readDouble(ByteBuf buf) {

        return buf.readDouble();
    }

    private static void writeDouble(double d, ByteBuf buf) {

        buf.writeDouble(d);
    }

    private static boolean readBoolean(ByteBuf buf) {

        return buf.readBoolean();
    }

    private static void writeBoolean(boolean b, ByteBuf buf) {

        buf.writeBoolean(b);
    }

    private static char readChar(ByteBuf buf) {

        return buf.readChar();
    }

    private static void writeChar(char c, ByteBuf buf) {

        buf.writeChar(c);
    }

    private static String readString(ByteBuf buf) {

        return ByteBufUtils.readUTF8String(buf);
    }

    private static void writeString(String s, ByteBuf buf) {

        ByteBufUtils.writeUTF8String(buf, s);
    }

    private static NBTTagCompound readNBT(ByteBuf buf) {

        return ByteBufUtils.readTag(buf);
    }

    private static void writeNBT(NBTTagCompound cmp, ByteBuf buf) {

        ByteBufUtils.writeTag(buf, cmp);
    }

    private static ItemStack readItemStack(ByteBuf buf) {

        return ByteBufUtils.readItemStack(buf);
    }

    private static void writeItemStack(ItemStack stack, ByteBuf buf) {

        ByteBufUtils.writeItemStack(buf, stack);
    }

    private static BlockPos readBlockPos(ByteBuf buf) {

        return BlockPos.fromLong(buf.readLong());
    }

    private static void writeBlockPos(BlockPos pos, ByteBuf buf) {

        buf.writeLong(pos.toLong());
    }

    private static ResourceLocation readResourceLocation(ByteBuf buf) {

        return new ResourceLocation(ByteBufUtils.readUTF8String(buf));
    }

    private static void writeResourceLocation(ResourceLocation location, ByteBuf buf) {

        ByteBufUtils.writeUTF8String(buf, location.toString());
    }

    private static IBlockState readState(ByteBuf buf) {

        final Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(ByteBufUtils.readUTF8String(buf)));
        final int meta = buf.readByte();
        return block != null ? block.getStateFromMeta(meta) : Blocks.AIR.getDefaultState();
    }

    private static void writeState(IBlockState state, ByteBuf buf) {

        ByteBufUtils.writeUTF8String(buf, state.getBlock().getRegistryName().toString());
        buf.writeByte(state.getBlock().getMetaFromState(state));
    }

    private static EnchantmentData readEnchantmentData(ByteBuf buf) {

        final ResourceLocation id = new ResourceLocation(ByteBufUtils.readUTF8String(buf));
        final int level = buf.readInt();
        return new EnchantmentData(ForgeRegistries.ENCHANTMENTS.getValue(id), level);
    }

    private static void writeEnchantmentData(EnchantmentData data, ByteBuf buf) {

        ByteBufUtils.writeUTF8String(buf,
                data.enchantment != null && data.enchantment.getRegistryName() != null ? data.enchantment.getRegistryName().toString() : "invalid");
        buf.writeInt(data.enchantmentLevel);
    }

    // Arrays
    private static String[] readStringArray(ByteBuf buf) {

        final String[] strings = new String[buf.readInt()];

        for (int index = 0; index < strings.length; index++) {

            strings[index] = ByteBufUtils.readUTF8String(buf);
        }

        return strings;
    }

    private static void writeStringArray(String[] strings, ByteBuf buf) {

        buf.writeInt(strings.length);

        for (final String string : strings) {

            ByteBufUtils.writeUTF8String(buf, string);
        }
    }

    private static byte[] readByteArray(ByteBuf buf) {

        final byte[] objects = new byte[buf.readInt()];

        for (int index = 0; index < objects.length; index++) {

            objects[index] = buf.readByte();
        }

        return objects;
    }

    private static void writeByteArray(byte[] objects, ByteBuf buf) {

        buf.writeInt(objects.length);

        for (final byte object : objects) {

            buf.writeByte(object);
        }
    }

    private static short[] readShortArray(ByteBuf buf) {

        final short[] objects = new short[buf.readInt()];

        for (int index = 0; index < objects.length; index++) {

            objects[index] = buf.readShort();
        }

        return objects;
    }

    private static void writeShortArray(short[] objects, ByteBuf buf) {

        buf.writeInt(objects.length);

        for (final short object : objects) {

            buf.writeShort(object);
        }
    }

    private static int[] readIntArray(ByteBuf buf) {

        final int[] objects = new int[buf.readInt()];

        for (int index = 0; index < objects.length; index++) {

            objects[index] = buf.readInt();
        }

        return objects;
    }

    private static void writeIntArray(int[] objects, ByteBuf buf) {

        buf.writeInt(objects.length);

        for (final int object : objects) {

            buf.writeInt(object);
        }
    }

    private static long[] readLongArray(ByteBuf buf) {

        final long[] objects = new long[buf.readInt()];

        for (int index = 0; index < objects.length; index++) {

            objects[index] = buf.readLong();
        }

        return objects;
    }

    private static void writeLongArray(long[] objects, ByteBuf buf) {

        buf.writeInt(objects.length);

        for (final long object : objects) {

            buf.writeLong(object);
        }
    }

    private static float[] readFloatArray(ByteBuf buf) {

        final float[] objects = new float[buf.readInt()];

        for (int index = 0; index < objects.length; index++) {

            objects[index] = buf.readFloat();
        }

        return objects;
    }

    private static void writeFloatArray(float[] objects, ByteBuf buf) {

        buf.writeInt(objects.length);

        for (final float object : objects) {

            buf.writeFloat(object);
        }
    }

    private static double[] readDoubleArray(ByteBuf buf) {

        final double[] objects = new double[buf.readInt()];

        for (int index = 0; index < objects.length; index++) {

            objects[index] = buf.readDouble();
        }

        return objects;
    }

    private static void writeDoubleArray(double[] objects, ByteBuf buf) {

        buf.writeInt(objects.length);

        for (final double object : objects) {

            buf.writeDouble(object);
        }
    }

    private static boolean[] readBooleanArray(ByteBuf buf) {

        final boolean[] objects = new boolean[buf.readInt()];

        for (int index = 0; index < objects.length; index++) {

            objects[index] = buf.readBoolean();
        }

        return objects;
    }

    private static void writeBooleanArray(boolean[] objects, ByteBuf buf) {

        buf.writeInt(objects.length);

        for (final boolean object : objects) {

            buf.writeBoolean(object);
        }
    }

    private static char[] readCharArray(ByteBuf buf) {

        final char[] objects = new char[buf.readInt()];

        for (int index = 0; index < objects.length; index++) {

            objects[index] = buf.readChar();
        }

        return objects;
    }

    private static void writeCharArray(char[] objects, ByteBuf buf) {

        buf.writeInt(objects.length);

        for (final char object : objects) {

            buf.writeChar(object);
        }
    }

    private static NBTTagCompound[] readNBTArray(ByteBuf buf) {

        final NBTTagCompound[] objects = new NBTTagCompound[buf.readInt()];

        for (int index = 0; index < objects.length; index++) {

            objects[index] = readNBT(buf);
        }

        return objects;
    }

    private static void writeNBTArray(NBTTagCompound[] objects, ByteBuf buf) {

        buf.writeInt(objects.length);

        for (final NBTTagCompound object : objects) {

            writeNBT(object, buf);
        }
    }

    private static ItemStack[] readItemStackArray(ByteBuf buf) {

        final ItemStack[] objects = new ItemStack[buf.readInt()];

        for (int index = 0; index < objects.length; index++) {

            objects[index] = readItemStack(buf);
        }

        return objects;
    }

    private static void writeItemStackArray(ItemStack[] objects, ByteBuf buf) {

        buf.writeInt(objects.length);

        for (final ItemStack object : objects) {

            writeItemStack(object, buf);
        }
    }

    private static BlockPos[] readBlockPosArray(ByteBuf buf) {

        final BlockPos[] objects = new BlockPos[buf.readInt()];

        for (int index = 0; index < objects.length; index++) {

            objects[index] = readBlockPos(buf);
        }

        return objects;
    }

    private static void writeBlockPosArray(BlockPos[] objects, ByteBuf buf) {

        buf.writeInt(objects.length);

        for (final BlockPos object : objects) {

            writeBlockPos(object, buf);
        }
    }

    private static ResourceLocation[] readResourceLocationArray(ByteBuf buf) {

        final ResourceLocation[] objects = new ResourceLocation[buf.readInt()];

        for (int index = 0; index < objects.length; index++) {

            objects[index] = readResourceLocation(buf);
        }

        return objects;
    }

    private static void writeResourceLocationArray(ResourceLocation[] objects, ByteBuf buf) {

        buf.writeInt(objects.length);

        for (final ResourceLocation object : objects) {

            writeResourceLocation(object, buf);
        }
    }

    private static IBlockState[] readStateArray(ByteBuf buf) {

        final IBlockState[] objects = new IBlockState[buf.readInt()];

        for (int index = 0; index < objects.length; index++) {

            objects[index] = readState(buf);
        }

        return objects;
    }

    private static void writeStateArray(IBlockState[] objects, ByteBuf buf) {

        buf.writeInt(objects.length);

        for (final IBlockState object : objects) {

            writeState(object, buf);
        }
    }

    private static EnchantmentData[] readEnchantmentDataArray(ByteBuf buf) {

        final EnchantmentData[] objects = new EnchantmentData[buf.readInt()];

        for (int index = 0; index < objects.length; index++) {

            objects[index] = readEnchantmentData(buf);
        }

        return objects;
    }

    private static void writeEnchantmentDataArray(EnchantmentData[] objects, ByteBuf buf) {

        buf.writeInt(objects.length);

        for (final EnchantmentData object : objects) {

            writeEnchantmentData(object, buf);
        }
    }

    /**
     * Called when the message is received and handled. This is where you process the message.
     *
     * @param context The context for the message.
     * @return A message to send as a response.
     */
    public abstract IMessage handleMessage(MessageContext context);

    @Override
    public final IMessage onMessage(REQ message, MessageContext context) {

        return message.handleMessage(context);
    }

    @Override
    public final void fromBytes(ByteBuf buf) {

        try {
            final Class<?> clazz = this.getClass();
            final Field[] clFields = getClassFields(clazz);
            for (final Field f : clFields) {
                final Class<?> type = f.getType();
                if (acceptField(f, type)) {
                    this.readField(f, type, buf);
                }
            }
        } catch (final Exception e) {
            throw new RuntimeException("Error at reading packet " + this, e);
        }
    }

    @Override
    public final void toBytes(ByteBuf buf) {

        try {
            final Class<?> clazz = this.getClass();
            final Field[] clFields = getClassFields(clazz);
            for (final Field f : clFields) {
                final Class<?> type = f.getType();
                if (acceptField(f, type)) {
                    this.writeField(f, type, buf);
                }
            }
        } catch (final Exception e) {
            throw new RuntimeException("Error at writing packet " + this, e);
        }
    }

    /**
     * Writes the value of a field to the packet byte buffer.
     *
     * @param field The field to write.
     * @param type  The type for the field.
     * @param buf   The buffer to write to.
     */
    private final void writeField(Field field, Class<?> type, ByteBuf buf) throws IllegalArgumentException, IllegalAccessException {

        final Pair<Reader, Writer> handler = getHandler(type);
        handler.getRight().write(field.get(this), buf);
    }

    /**
     * Reads the value of a field from the packet byte buffer.
     *
     * @param field The field to read.
     * @param type  The type for the field.
     * @param buf   The buffer to read from.
     */
    private final void readField(Field field, Class<?> type, ByteBuf buf) throws IllegalArgumentException, IllegalAccessException {

        final Pair<Reader, Writer> handler = getHandler(type);
        field.set(this, handler.getLeft().read(buf));
    }

    // Functional interfaces
    public interface Writer<T extends Object> {

        public void write(T t, ByteBuf buf);
    }

    public interface Reader<T extends Object> {

        public T read(ByteBuf buf);
    }
}
