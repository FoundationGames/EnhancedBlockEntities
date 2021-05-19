package foundationgames.enhancedblockentities.util.hacks;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/*
                    SUFFER
 */

public enum ResourceHacks {;
    public static void addChestParticleTexture(String chestName, String chestTexture, List<ResourcePack> packs, RuntimeResourcePack pack) throws IOException {
        InputStream image = ResourceHacks.openTopResource(packs, ResourceType.CLIENT_RESOURCES, new Identifier("textures/"+chestTexture+".png"));
        if (image != null) {
            pack.addResource(ResourceType.CLIENT_RESOURCES, new Identifier("textures/block/"+chestName+"_particle.png"), TextureHacks.cropImage(image, 42f/64, 33f/64, 58f/64, 49f/64));
            image.close();
        } else EnhancedBlockEntities.LOG.warn("Unable to generate a chest particle texture as no such texture exists in any loaded resources");
    }

    /*
        utterly horrific
     */
    @Nullable
    public static InputStream openTopResource(List<ResourcePack> packs, ResourceType type, Identifier resource) throws IOException {
        ResourcePack pack;
        for (int i = packs.size(); i-- > 0;) {
            pack = packs.get(i);
            if (pack.contains(type, resource)) {
                return pack.open(type, resource);
            }
        }
        return null;
    }
}
