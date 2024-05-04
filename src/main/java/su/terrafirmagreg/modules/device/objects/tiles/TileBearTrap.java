package su.terrafirmagreg.modules.device.objects.tiles;

import su.terrafirmagreg.api.spi.tile.BaseTile;
import su.terrafirmagreg.api.util.NBTUtils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.world.WorldServer;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import lombok.Getter;

import java.util.UUID;

public class TileBearTrap extends BaseTile {

    private EntityLivingBase capturedEntity;
    private UUID capturedId;
    @Getter
    private boolean open;

    public TileBearTrap() {
        super();
        this.open = true;
    }

    @Override
    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 3, this.getUpdateTag());
    }

    @Override
    public void onDataPacket(@NotNull NetworkManager net, @NotNull SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        handleUpdateTag(pkt.getNbtCompound());
    }

    @Override
    public @NotNull NBTTagCompound writeToNBT(@NotNull NBTTagCompound nbt) {
        NBTUtils.setGenericNBTValue(nbt, "open", open);
        if (this.capturedEntity != null) {
            NBTUtils.setGenericNBTValue(nbt, "capturedId", this.capturedEntity.getUniqueID());
        }
        return super.writeToNBT(nbt);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        this.open = nbt.getBoolean("open");
        this.capturedId = nbt.getUniqueId("capturedId");
        super.readFromNBT(nbt);
    }

    protected void sendUpdates() {
        this.world.markBlockRangeForRenderUpdate(pos, pos);
        this.world.notifyBlockUpdate(pos, this.world.getBlockState(pos), this.world.getBlockState(pos), 3);
        this.world.scheduleBlockUpdate(pos, this.getBlockType(), 0, 0);
        markDirty();
    }

    private void readCapturedEntity() {
        if (this.capturedId != null) {
            if (this.world.getPlayerEntityByUUID(capturedId) != null) {
                this.capturedEntity = this.world.getPlayerEntityByUUID(capturedId);

            } else if (this.world instanceof WorldServer worldServer) {
                Entity entity = worldServer.getEntityFromUuid(this.capturedId);

                if (entity instanceof EntityLivingBase entityLivingBase) {
                    this.capturedEntity = entityLivingBase;
                }
            }
        }
    }

    public void setOpen(boolean isOpen) {
        this.open = isOpen;
        sendUpdates();
    }

    public EntityLivingBase getCapturedEntity() {
        readCapturedEntity();
        return this.capturedEntity;
    }

    public void setCapturedEntity(@Nullable EntityLivingBase entity) {

        this.capturedEntity = entity;
        if (entity != null) {
            this.capturedId = entity.getUniqueID();
        } else {
            this.capturedId = UUID.randomUUID();
        }
        sendUpdates();
    }
}
