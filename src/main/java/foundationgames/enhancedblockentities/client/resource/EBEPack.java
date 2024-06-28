package foundationgames.enhancedblockentities.client.resource;

import com.google.gson.JsonObject;
import foundationgames.enhancedblockentities.client.resource.template.TemplateLoader;
import foundationgames.enhancedblockentities.client.resource.template.TemplateProvider;
import net.minecraft.SharedConstants;
import net.minecraft.client.texture.atlas.AtlasSource;
import net.minecraft.client.texture.atlas.DirectoryAtlasSource;
import net.minecraft.client.texture.atlas.SingleAtlasSource;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourcePack;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

public class EBEPack implements ResourcePack {
    public static final Identifier BLOCK_ATLAS = new Identifier("blocks");

    private final Map<Identifier, AtlasResourceBuilder> atlases = new HashMap<>();
    private final Map<Identifier, InputSupplier<InputStream>> resources = new HashMap<>();
    private final Set<String> namespaces = new HashSet<>();

    private final TemplateLoader templates;

    private final JsonObject packMeta;
    private final String name;

    public EBEPack(Identifier id, TemplateLoader templates) {
        this.templates = templates;

        this.packMeta = new JsonObject();
        this.packMeta.addProperty("pack_format", SharedConstants.getGameVersion().getResourceVersion(ResourceType.CLIENT_RESOURCES));
        this.packMeta.addProperty("description", "Enhanced Block Entities Resources");

        this.name = id.toString();
    }

    public void addAtlasSprite(Identifier atlas, AtlasSource source) {
        var resource = this.atlases.computeIfAbsent(atlas, id -> new AtlasResourceBuilder());
        resource.put(source);

        this.addResource(new Identifier(atlas.getNamespace(), "atlases/" + atlas.getPath() + ".json"), resource::toBytes);
    }

    public void addSingleBlockSprite(Identifier path) {
        this.addAtlasSprite(BLOCK_ATLAS, new SingleAtlasSource(path, Optional.empty()));
    }

    public void addDirBlockSprites(String dir, String prefix) {
        this.addAtlasSprite(BLOCK_ATLAS, new DirectoryAtlasSource(dir, prefix));
    }

    public void addResource(Identifier id, InputSupplier<byte[]> resource) {
        this.namespaces.add(id.getNamespace());
        this.resources.put(id, new LazyBufferedResource(resource));
    }

    public void addResource(Identifier id, byte[] resource) {
        this.namespaces.add(id.getNamespace());
        this.resources.put(id, () -> new ByteArrayInputStream(resource));
    }

    public void addPlainTextResource(Identifier id, String plainText) {
        this.addResource(id, plainText.getBytes(StandardCharsets.UTF_8));
    }

    public void addTemplateResource(Identifier id, TemplateProvider.TemplateApplyingFunction template) {
        this.addResource(id, () -> template.getAndApplyTemplate(new TemplateProvider(this.templates)).getBytes(StandardCharsets.UTF_8));
    }

    public void addTemplateResource(Identifier id, String templatePath) {
        this.addTemplateResource(id, t -> t.load(templatePath, d -> {}));
    }

    @Nullable
    @Override
    public InputSupplier<InputStream> openRoot(String... segments) {
        return null; // Provide no root resources
    }

    @Nullable
    @Override
    public InputSupplier<InputStream> open(ResourceType type, Identifier id) {
        if (type != ResourceType.CLIENT_RESOURCES) return null;

        return this.resources.get(id);
    }

    @Override
    public void findResources(ResourceType type, String namespace, String prefix, ResultConsumer consumer) {
        if (type != ResourceType.CLIENT_RESOURCES) return;

        for (var entry : this.resources.entrySet()) {
            var id = entry.getKey();

            if (id.getNamespace().startsWith(namespace) && id.getPath().startsWith(prefix)) {
                consumer.accept(id, entry.getValue());
            }
        }
    }

    @Override
    public Set<String> getNamespaces(ResourceType type) {
        if (type != ResourceType.CLIENT_RESOURCES) return Set.of();

        return this.namespaces;
    }

    @Nullable
    @Override
    public <T> T parseMetadata(ResourceMetadataReader<T> meta) throws IOException {
        if ("pack".equals(meta.getKey())) {
            return meta.fromJson(this.packMeta);
        }

        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void close() {
    }

    public void dump(Path dir) throws IOException {
        dir = dir.resolve("assets");

        for (var entry : this.resources.entrySet()) {
            var id = entry.getKey();
            var file = dir.resolve(id.getNamespace()).resolve(id.getPath());

            Files.createDirectories(file.getParent());

            try (var out = Files.newOutputStream(file)) {
                var in = entry.getValue().get();

                int i;
                while ((i = in.read()) >= 0) {
                    out.write(i);
                }
            }
        }
    }

    public static class PropertyBuilder {
        private Properties properties = new Properties();

        private PropertyBuilder() {}

        public PropertyBuilder def(String k, String v) {
            if (this.properties != null) {
                this.properties.setProperty(k, v);
            }

            return this;
        }

        private Properties build() {
            var properties = this.properties;
            this.properties = null;

            return properties;
        }
    }

    public static class LazyBufferedResource implements InputSupplier<InputStream> {
        private final InputSupplier<byte[]> backing;
        private byte[] buffer = null;

        public LazyBufferedResource(InputSupplier<byte[]> backing) {
            this.backing = backing;
        }

        @Override
        public InputStream get() throws IOException {
            if (buffer == null) {
                buffer = backing.get();
            }

            return new ByteArrayInputStream(buffer);
        }
    }
}
