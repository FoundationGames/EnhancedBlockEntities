package foundationgames.enhancedblockentities.util.hacks;

import foundationgames.enhancedblockentities.client.resource.ExperimentalResourcePack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.io.InputStream;

public enum ResourceHacks {;
    public static void addChestParticleTexture(String chestName, String chestTexture, ResourceManager manager, ExperimentalResourcePack pack) throws IOException {
        InputStream image;
        try {
            Identifier resourceId = new Identifier("textures/"+chestTexture+".png");
            image = manager.getResource(resourceId).getInputStream();
        } catch (IOException e) {
            return;
        }
        if (image == null) return;

        pack.put(new Identifier("textures/block/"+chestName+"_particle.png"), TextureHacks.cropImage(image, 42f/64, 33f/64, 58f/64, 49f/64));
        image.close();
    }
}
