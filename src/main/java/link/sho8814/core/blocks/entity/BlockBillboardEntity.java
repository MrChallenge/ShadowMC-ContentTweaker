package link.sho8814.core.blocks.entity;

import link.sho8814.core.blocks.ModBlocksEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockBillboardEntity extends BlockEntity {
    private float scale = 1.0f;
    private float offsetX = 0.0f;
    private float offsetY = 0.0f;
    private float offsetZ = 0.0f;
    private String texturePath = "block/test/block_billboard";

    public BlockBillboardEntity(BlockPos pos, BlockState state) {
        super(ModBlocksEntities.ENTITY_BILLBOARD.get(), pos, state);
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
        setChanged();
    }

    public float getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
        setChanged();
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
        setChanged();
    }

    public float getOffsetZ() {
        return offsetZ;
    }

    public void setOffsetZ(float offsetZ) {
        this.offsetZ = offsetZ;
        setChanged();
    }

    public String getTexturePath() {
        return texturePath;
    }

    public void setTexturePath(String texturePath) {
        this.texturePath = texturePath;
        setChanged();
    }

    public void setClientValues(float scale, float offsetX, float offsetY, float offsetZ) {
        this.scale = scale;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.offsetZ = offsetZ;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);

        tag.putFloat("scale", scale);
        tag.putFloat("offsetX", offsetX);
        tag.putFloat("offsetY", offsetY);
        tag.putFloat("offsetZ", offsetZ);
        tag.putString("texture", texturePath);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        if (tag.contains("scale")) scale = tag.getFloat("scale");
        if (tag.contains("offsetX")) offsetX = tag.getFloat("offsetX");
        if (tag.contains("offsetY")) offsetY = tag.getFloat("offsetY");
        if (tag.contains("offsetZ")) offsetZ = tag.getFloat("offsetZ");
        if (tag.contains("texture")) texturePath = tag.getString("texture");
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public net.minecraft.network.protocol.Packet<net.minecraft.network.protocol.game.ClientGamePacketListener> getUpdatePacket() {
        return net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket.create(this);
    }
}