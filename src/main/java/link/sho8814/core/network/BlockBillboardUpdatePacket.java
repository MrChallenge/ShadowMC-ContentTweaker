package link.sho8814.core.network;

import link.sho8814.core.blocks.entity.BlockBillboardEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class BlockBillboardUpdatePacket {
    private final BlockPos pos;
    private final float scale;
    private final float offsetX;
    private final float offsetY;
    private final String url;
    private final boolean flipX;
    private final boolean flipY;

    public BlockBillboardUpdatePacket(BlockPos pos, float scale, float offsetX, float offsetY, String url, boolean flipX, boolean flipY) {
        this.pos = pos;
        this.scale = scale;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.url = url;
        this.flipX = flipX;
        this.flipY = flipY;
    }

    public static void encode(BlockBillboardUpdatePacket msg, FriendlyByteBuf buf) {
        buf.writeBlockPos(msg.pos);
        buf.writeFloat(msg.scale);
        buf.writeFloat(msg.offsetX);
        buf.writeFloat(msg.offsetY);
        buf.writeUtf(msg.url, 1024);
        buf.writeBoolean(msg.flipX);
        buf.writeBoolean(msg.flipY);
    }

    public static BlockBillboardUpdatePacket decode(FriendlyByteBuf buf) {
        return new BlockBillboardUpdatePacket(
                buf.readBlockPos(),
                buf.readFloat(),
                buf.readFloat(),
                buf.readFloat(),
                buf.readUtf(1024),
                buf.readBoolean(),
                buf.readBoolean()
        );
    }

    public static void handle(BlockBillboardUpdatePacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player == null) return;

            Level level = player.level();

            if (level.getBlockEntity(msg.pos) instanceof BlockBillboardEntity be) {
                be.setScale(msg.scale);
                be.setOffsetX(msg.offsetX);
                be.setOffsetY(msg.offsetY);
                be.setImageUrl(msg.url);
                be.setFlip(msg.flipX, msg.flipY);
                be.setChanged();
                level.sendBlockUpdated(msg.pos, level.getBlockState(msg.pos), level.getBlockState(msg.pos), 3);
            }
        });

        ctx.get().setPacketHandled(true);
    }
}