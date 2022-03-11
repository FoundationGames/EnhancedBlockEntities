package foundationgames.enhancedblockentities.util.hacks;

import foundationgames.enhancedblockentities.client.resource.ExperimentalResourcePack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStream;

public class ResourceHacks {
    private static void cropAndPutTexture(Identifier source, Identifier result, ResourceManager manager, ExperimentalResourcePack pack, float u0, float v0, float u1, float v1) throws IOException {
        InputStream image;
        try {
            image = manager.getResource(source).getInputStream();
        } catch (IOException e) {
            return;
        }
        if (image == null) return;

        pack.put(result, TextureHacks.cropImage(image, u0, v0, u1, v1));
        image.close();
    }

    public static void addChestParticleTexture(String chestName, String chestTexture, ResourceManager manager, ExperimentalResourcePack pack) throws IOException {
        cropAndPutTexture(
                new Identifier("textures/"+chestTexture+".png"), new Identifier("textures/block/"+chestName+"_particle.png"),
                manager, pack,
                42f/64, 33f/64, 58f/64, 49f/64
        );
    }

    public static void addBedParticleTexture(String bedColor, String bedTexture, ResourceManager manager, ExperimentalResourcePack pack) throws IOException {
        cropAndPutTexture(
                new Identifier("textures/"+bedTexture+".png"), new Identifier("textures/block/"+bedColor+"_bed_particle.png"),
                manager, pack,
                18f/64, 6f/64, 34f/64, 22f/64
        );
    }

    public static void addSignParticleTexture(String signType, String signTexture, ResourceManager manager, ExperimentalResourcePack pack) throws IOException {
        cropAndPutTexture(
                new Identifier("textures/"+signTexture+".png"), new Identifier("textures/block/"+signType+"_sign_particle.png"),
                manager, pack,
                0, 3f/32, 16f/64, 19f/32
        );
    }
}
