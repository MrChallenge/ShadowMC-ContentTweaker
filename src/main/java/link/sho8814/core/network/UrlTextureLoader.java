package link.sho8814.core.network;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class UrlTextureLoader {

    public static CompletableFuture<ResourceLocation> loadTexture(String url) {
        return CompletableFuture.supplyAsync(() -> {
            try (InputStream stream = new URL(url).openStream()) {

                NativeImage image = NativeImage.read(stream);

                if (image.getWidth() == 0 || image.getHeight() == 0) {
                    return null;
                }

                DynamicTexture texture = new DynamicTexture(image);

                return Minecraft.getInstance()
                        .getTextureManager()
                        .register("url_texture_" + System.nanoTime(), texture);

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        });
    }
}