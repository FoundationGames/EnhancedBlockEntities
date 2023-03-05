package foundationgames.enhancedblockentities.client.resource;

import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.animation.JAnimation;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.lang.JLang;
import net.devtech.arrp.json.loot.JLootTable;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.recipe.JRecipe;
import net.devtech.arrp.json.tags.JTag;
import net.devtech.arrp.util.CallableFunction;
import net.minecraft.client.texture.atlas.AtlasSource;
import net.minecraft.client.texture.atlas.DirectoryAtlasSource;
import net.minecraft.client.texture.atlas.SingleAtlasSource;
import net.minecraft.resource.InputSupplier;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.metadata.ResourceMetadataReader;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.IntUnaryOperator;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class EBEPack implements RuntimeResourcePack {
    public static final Identifier BLOCK_ATLAS = new Identifier("blocks");

    private final RuntimeResourcePack resourcePack;
    private final Map<Identifier, AtlasResourceBuilder> atlases = new HashMap<>();

    public EBEPack(Identifier id) {
        this.resourcePack = RuntimeResourcePack.create(id);
    }

    public void addAtlasSprite(Identifier atlas, AtlasSource source) {
        var resource = this.atlases.computeIfAbsent(atlas, id -> new AtlasResourceBuilder());
        resource.put(source);

        this.addLazyResource(ResourceType.CLIENT_RESOURCES,
                new Identifier(atlas.getNamespace(), "atlases/" + atlas.getPath() + ".json"),
                (pack, id) -> resource.toBytes());
    }

    public void addSingleBlockSprite(Identifier path) {
        this.addAtlasSprite(BLOCK_ATLAS, new SingleAtlasSource(path, Optional.empty()));
    }

    public void addDirBlockSprites(String dir, String prefix) {
        this.addAtlasSprite(BLOCK_ATLAS, new DirectoryAtlasSource(dir, prefix));
    }

    @Override
    public void addRecoloredImage(Identifier identifier, InputStream target, IntUnaryOperator pixel) {
        this.resourcePack.addRecoloredImage(identifier, target, pixel);
    }

    @Override
    public byte[] addLang(Identifier identifier, JLang lang) {
        return this.resourcePack.addLang(identifier, lang);
    }

    @Override
    public void mergeLang(Identifier identifier, JLang lang) {
        this.resourcePack.mergeLang(identifier, lang);
    }

    @Override
    public byte[] addLootTable(Identifier identifier, JLootTable table) {
        return this.resourcePack.addLootTable(identifier, table);
    }

    @Override
    public Future<byte[]> addAsyncResource(ResourceType type, Identifier identifier, CallableFunction<Identifier, byte[]> data) {
        return this.resourcePack.addAsyncResource(type, identifier, data);
    }

    @Override
    public void addLazyResource(ResourceType type, Identifier path, BiFunction<RuntimeResourcePack, Identifier, byte[]> data) {
        this.resourcePack.addLazyResource(type, path, data);
    }

    @Override
    public byte[] addResource(ResourceType type, Identifier path, byte[] data) {
        return this.resourcePack.addResource(type, path, data);
    }

    @Override
    public Future<byte[]> addAsyncRootResource(String path, CallableFunction<String, byte[]> data) {
        return this.resourcePack.addAsyncRootResource(path, data);
    }

    @Override
    public void addLazyRootResource(String path, BiFunction<RuntimeResourcePack, String, byte[]> data) {
        this.resourcePack.addLazyRootResource(path, data);
    }

    @Override
    public byte[] addRootResource(String path, byte[] data) {
        return this.resourcePack.addRootResource(path, data);
    }

    @Override
    public byte[] addAsset(Identifier path, byte[] data) {
        return this.resourcePack.addAsset(path, data);
    }

    @Override
    public byte[] addData(Identifier path, byte[] data) {
        return this.resourcePack.addData(path, data);
    }

    @Override
    public byte[] addModel(JModel model, Identifier path) {
        return this.resourcePack.addModel(model, path);
    }

    @Override
    public byte[] addBlockState(JState state, Identifier path) {
        return this.resourcePack.addBlockState(state, path);
    }

    @Override
    public byte[] addTexture(Identifier id, BufferedImage image) {
        return this.resourcePack.addTexture(id, image);
    }

    @Override
    public byte[] addAnimation(Identifier id, JAnimation animation) {
        return this.resourcePack.addAnimation(id, animation);
    }

    @Override
    public byte[] addTag(Identifier id, JTag tag) {
        return this.resourcePack.addTag(id, tag);
    }

    @Override
    public byte[] addRecipe(Identifier id, JRecipe recipe) {
        return this.resourcePack.addRecipe(id, recipe);
    }

    @Override
    public Future<?> async(Consumer<RuntimeResourcePack> action) {
        return this.resourcePack.async(action);
    }

    @Override
    public void dumpDirect(Path path) {
        this.resourcePack.dumpDirect(path);
    }

    @Override
    public void load(Path path) throws IOException {
        this.resourcePack.load(path);
    }

    @Override
    public void dump(File file) {
        this.resourcePack.dump(file);
    }

    @Override
    public void dump(ZipOutputStream stream) throws IOException {
        this.resourcePack.dump(stream);
    }

    @Override
    public void load(ZipInputStream stream) throws IOException {
        this.resourcePack.load(stream);
    }

    @Override
    public Identifier getId() {
        return this.resourcePack.getId();
    }

    @Nullable
    @Override
    public InputSupplier<InputStream> openRoot(String... segments) {
        return this.resourcePack.openRoot(segments);
    }

    @Nullable
    @Override
    public InputSupplier<InputStream> open(ResourceType type, Identifier id) {
        return this.resourcePack.open(type, id);
    }

    @Override
    public void findResources(ResourceType type, String namespace, String prefix, ResultConsumer consumer) {
        this.resourcePack.findResources(type, namespace, prefix, consumer);
    }

    @Override
    public Set<String> getNamespaces(ResourceType type) {
        return this.resourcePack.getNamespaces(type);
    }

    @Nullable
    @Override
    public <T> T parseMetadata(ResourceMetadataReader<T> metaReader) throws IOException {
        return this.resourcePack.parseMetadata(metaReader);
    }

    @Override
    public String getName() {
        return this.resourcePack.getName();
    }

    @Override
    public void close() {
        this.resourcePack.close();
    }
}
