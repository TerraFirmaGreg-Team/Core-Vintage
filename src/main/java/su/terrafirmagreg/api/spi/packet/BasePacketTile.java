package su.terrafirmagreg.api.spi.packet;

import su.terrafirmagreg.TerraFirmaGreg;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public abstract class BasePacketTile<T extends TileEntity> extends BasePacketSerializable {

    /**
     * The serial version UID.
     */
    private static final long serialVersionUID = -8474561253790105901L;

    /**
     * The position of the TileEntity.
     */
    public BlockPos pos;

    /**
     * The TileEntity.
     */
    public transient T tile;

    /**
     * The message context.
     */
    public transient MessageContext context;

    /**
     * Blank constructor required for all messages.
     */
    public BasePacketTile() {}

    /**
     * Basic constructor for a tile entity update message.
     *
     * @param pos The position of the tile entity.
     */
    public BasePacketTile(BlockPos pos) {

        this.pos = pos;
    }

    @Override
    public IMessage handleMessage(MessageContext context) {

        this.context = context;
        final World world = context.getServerHandler().player.getEntityWorld();
        final TileEntity tileEntity = world.getTileEntity(this.pos);

        if (tileEntity != null && world.isBlockLoaded(this.pos)) {
            try {

                final T castTile = (T) tileEntity;
                this.tile = castTile;
                ((WorldServer) world).addScheduledTask(this::getAction);
            } catch (final ClassCastException e) {

                TerraFirmaGreg.LOGGER.warn(e, "Tile entity could not be cast.");
            }
        }

        return null;
    }

    public abstract void getAction();
}
