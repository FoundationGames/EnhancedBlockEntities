package foundationgames.enhancedblockentities.client.resource;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.impl.resource.loader.ModResourcePackUtil;
import net.minecraft.resource.AbstractFileResourcePack;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Predicate;

public class ExperimentalResourcePack implements ResourcePack {
    private static final String PACK_DESC = "Experimental Resource Pack for Enhanced Block Entities";

    public static final String PACK_META = String.format("{\"pack\":{\"pack_format\":%s,\"description\":\"%s\"}}", ModResourcePackUtil.PACK_FORMAT_VERSION, PACK_DESC);

    private final Map<Identifier, byte[]> resources = new Object2ObjectOpenHashMap<>();
    private final Set<String> namespaces = new HashSet<>();

    public void put(Identifier id, byte[] resource) {
        this.resources.put(id, resource);
        namespaces.add(id.getNamespace());
    }

    @Override
    public InputStream openRoot(String fileName) throws IOException {
        if ("pack.mcmeta".equals(fileName)) {
            return IOUtils.toInputStream(PACK_META, Charsets.UTF_8);
        }
        throw new FileNotFoundException("No such file '"+fileName+"' in EBE experimental resources");
    }

    @Override
    public InputStream open(ResourceType type, Identifier id) throws IOException {
        if (type == ResourceType.CLIENT_RESOURCES) {
            if (resources.containsKey(id)) {
                return new ByteArrayInputStream(resources.get(id));
            }
        }
        throw new FileNotFoundException("No such resource '"+id.toString()+"' of type '"+type.toString()+"' in EBE experimental resources");
    }

    @Override
    public Collection<Identifier> findResources(ResourceType type, String namespace, String prefix, int maxDepth, Predicate<String> pathFilter) {
        if (type == ResourceType.CLIENT_RESOURCES) {
            ImmutableList.Builder<Identifier> r = ImmutableList.builder();
            resources.keySet().forEach(id -> {
                if (
                        id.getNamespace().equals(namespace) &&
                        id.getPath().startsWith(prefix) &&
                        pathFilter.test(id.getPath()) &&
                        (id.getPath().split("[/\\\\]").length <= maxDepth)
                ) {
                    r.add(id);
                }
            });
        }
        return Collections.emptyList();
    }

    @Override
    public boolean contains(ResourceType type, Identifier id) {
        if (type == ResourceType.CLIENT_RESOURCES) {
            return resources.containsKey(id);
        }
        return false;
    }

    @Override
    public Set<String> getNamespaces(ResourceType type) {
        return this.namespaces;
    }

    @Nullable
    @Override
    public <T> T parseMetadata(ResourceMetadataReader<T> metaReader) throws IOException {
        return AbstractFileResourcePack.parseMetadata(metaReader, openRoot("pack.mcmeta"));
    }

    @Override
    public String getName() {
        return "ebe:exp_resources";
    }

    @Override
    public void close() {
    }
}
