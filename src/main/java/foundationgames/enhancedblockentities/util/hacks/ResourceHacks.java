package foundationgames.enhancedblockentities.util.hacks;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

/*
                    SUFFER
 */

public enum ResourceHacks {;
    public static void addChestParticleTexture(String chestName, String chestTexture, ResourceManager manager, RuntimeResourcePack pack) throws IOException {
        InputStream image;
        try {
            Identifier resourceId = new Identifier("textures/"+chestTexture+".png");
            image = openAsset(manager, resourceId);
        } catch (IOException e) {
            EnhancedBlockEntities.LOG.warn("Unable to generate a chest particle texture as no such texture exists in any loaded resources");
            return;
        }
        if (image == null) return;

        pack.addAsset(new Identifier("textures/block/"+chestName+"_particle.png"), TextureHacks.cropImage(image, 42f/64, 33f/64, 58f/64, 49f/64));
        image.close();
    }

    @Nullable
    public static InputStream openAsset(ResourceManager manager, Identifier resource) throws IOException {
        List<ResourcePack> packs = manager.streamResourcePacks().collect(Collectors.toList());
        for (int i = packs.size(); i-- > 0;) {
            ResourcePack p = packs.get(i);
            if (p.contains(ResourceType.CLIENT_RESOURCES, resource)) {
                return p.open(ResourceType.CLIENT_RESOURCES, resource);
            }
        }
        throw new IOException("Resource "+resource+" cannot be found in "+manager);
    }
}
