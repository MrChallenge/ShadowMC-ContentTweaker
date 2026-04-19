package link.sho8814.core.blocks.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class BlockBillboardEntity extends BlockEntity {
    private float scale = 1.0f;
    private float offsetX = 0.0f;
    private float offsetY = 0.0f;
    private float offsetZ = 0.0f;

    private String texturePath = "block/block_billboard";
    private String imageUrl = "";

    private boolean flipX = false;
    private boolean flipY = false;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String url) {
        this.imageUrl = url;
        setChanged();
    }

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

    public boolean isFlipX() {
        return flipX;
    }

    public boolean isFlipY() {
        return flipY;
    }

    public void setFlip(boolean flipX, boolean flipY) {
        this.flipX = flipX;
        this.flipY = flipY;
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
        tag.putString("imageUrl", imageUrl);
        tag.putBoolean("flipX", flipX);
        tag.putBoolean("flipY", flipY);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);

        if (tag.contains("scale")) scale = tag.getFloat("scale");
        if (tag.contains("offsetX")) offsetX = tag.getFloat("offsetX");
        if (tag.contains("offsetY")) offsetY = tag.getFloat("offsetY");
        if (tag.contains("offsetZ")) offsetZ = tag.getFloat("offsetZ");
        if (tag.contains("texture")) texturePath = tag.getString("texture");
        if (tag.contains("imageUrl")) imageUrl = tag.getString("imageUrl");
        if (tag.contains("flipX")) flipX = tag.getBoolean("flipX");
        if (tag.contains("flipY")) flipY = tag.getBoolean("flipY");
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        this.load(tag);
    }

    @Override
    public net.minecraft.network.protocol.Packet<net.minecraft.network.protocol.game.ClientGamePacketListener> getUpdatePacket() {
        return net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket.create(this);
    }
}