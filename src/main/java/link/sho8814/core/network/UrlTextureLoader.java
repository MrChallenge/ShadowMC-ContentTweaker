package link.sho8814.core.network;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.ResourceLocation;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

public class UrlTextureLoader {

    public static CompletableFuture<ResourceLocation> loadTexture(String url) {
        return CompletableFuture.supplyAsync(() -> {

            try {
                URL u = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) u.openConnection();

                connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                connection.connect();

                int responseCode = connection.getResponseCode();
                if (responseCode != 200) {
                    System.out.println("HTTP ERROR: " + responseCode + " | " + url);
                    return null;
                }

                String contentType = connection.getContentType();
                if (contentType == null || !contentType.startsWith("image")) {
                    System.out.println("NOT AN IMAGE: " + contentType + " | " + url);
                    return null;
                }

                try (InputStream stream = connection.getInputStream()) {

                    NativeImage image;

                    try {
                        image = NativeImage.read(stream);
                    } catch (Exception e) {
                        System.out.println("FAILED TO READ IMAGE: " + url);
                        return null;
                    }

                    if (image == null) {
                        System.out.println("IMAGE NULL: " + url);
                        return null;
                    }

                    if (image.getWidth() <= 0 || image.getHeight() <= 0) {
                        System.out.println("INVALID SIZE: " + url);
                        return null;
                    }

                    NativeImage finalImage = image;

                    return Minecraft.getInstance().submit(() -> {
                        DynamicTexture texture = new DynamicTexture(finalImage);

                        return Minecraft.getInstance()
                                .getTextureManager()
                                .register("url_texture_" + System.nanoTime(), texture);
                    }).join();
                }

            } catch (Exception e) {
                System.out.println("FAILED LOAD: " + url);
                e.printStackTrace();
                return null;
            }
        });
    }
}