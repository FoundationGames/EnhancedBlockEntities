package foundationgames.enhancedblockentities.util;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.client.resource.EBEPack;
import foundationgames.enhancedblockentities.client.resource.template.TemplateProvider;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum ResourceUtil {;
    private static EBEPack BASE_PACK;
    private static EBEPack TOP_LEVEL_PACK;

    public static void addChestItemModel(Identifier id, String centerChest, EBEPack pack) {
        pack.addTemplateResource(id, t -> t.load("chest_item_model.json", d -> d.def("chest", centerChest)));
    }

    private static String list(String ... els) {
        return String.join(",", els);
    }

    private static String kv(String k, String v) {
        return String.format("\""+k+"\":\""+v+"\"");
    }

    private static String kv(String k, int v) {
        return String.format("\""+k+"\":"+v+"");
    }

    private static String variant(TemplateProvider t, String state, String model) throws IOException {
        return t.load("blockstate/var.json", d -> d
                .def("state", state)
                .def("model", model)
                .def("extra", ""));
    }

    private static String variantY(TemplateProvider t, String state, String model, int y) throws IOException {
        return t.load("blockstate/var.json", d -> d
                .def("state", state)
                .def("model", model)
                .def("extra", kv("y", y) + ","));
    }

    private static String variantXY(TemplateProvider t, String state, String model, int x, int y) throws IOException {
        return t.load("blockstate/var.json", d -> d
                .def("state", state)
                .def("model", model)
                .def("extra", list(
                        kv("x", x),
                        kv("y", y)
                ) + ","));
    }

    private static String variantRotation16(TemplateProvider t, String keyPrefix, String modelPrefix) throws IOException {
        return list(
                variantY(t, keyPrefix+"0", modelPrefix+"_0", 180),
                variantY(t, keyPrefix+"1", modelPrefix+"_67_5", 270),
                variantY(t, keyPrefix+"2", modelPrefix+"_45", 270),
                variantY(t, keyPrefix+"3", modelPrefix+"_22_5", 270),
                variantY(t, keyPrefix+"4", modelPrefix+"_0", 270),
                variant(t, keyPrefix+"5", modelPrefix+"_67_5"),
                variant(t, keyPrefix+"6", modelPrefix+"_45"),
                variant(t, keyPrefix+"7", modelPrefix+"_22_5"),
                variant(t, keyPrefix+"8", modelPrefix+"_0"),
                variantY(t, keyPrefix+"9", modelPrefix+"_67_5", 90),
                variantY(t, keyPrefix+"10", modelPrefix+"_45", 90),
                variantY(t, keyPrefix+"11", modelPrefix+"_22_5", 90),
                variantY(t, keyPrefix+"12", modelPrefix+"_0", 90),
                variantY(t, keyPrefix+"13", modelPrefix+"_67_5", 180),
                variantY(t, keyPrefix+"14", modelPrefix+"_45", 180),
                variantY(t, keyPrefix+"15", modelPrefix+"_22_5", 180)
        );
    }

    private static String variantHFacing(TemplateProvider t, String keyPrefix, String model) throws IOException {
        return list(
                variant(t, keyPrefix+"north", model),
                variantY(t, keyPrefix+"west", model, 270),
                variantY(t, keyPrefix+"south", model, 180),
                variantY(t, keyPrefix+"east", model, 90)
        );
    }

    private static void addChestLikeModel(String parent, String chestTex, String chestName, Identifier id, EBEPack pack) {
        pack.addTemplateResource(new Identifier(id.getNamespace(), "models/" + id.getPath() + ".json"),
                t -> t.load("model/chest_like.json", d -> d
                        .def("parent", parent)
                        .def("chest_tex", chestTex)
                        .def("particle", chestParticle(chestName))
                )
        );
    }

    public static void addSingleChestModels(String chestTex, String chestName, EBEPack pack) {
        addChestLikeModel("template_chest_center", chestTex, chestName, new Identifier("block/" + chestName + "_center"), pack);
        addChestLikeModel("template_chest_center_lid", chestTex, chestName, new Identifier("block/" + chestName + "_center_lid"), pack);
        addChestLikeModel("template_chest_center_trunk", chestTex, chestName, new Identifier("block/" + chestName + "_center_trunk"), pack);
    }

    public static void addDoubleChestModels(String leftTex, String rightTex, String chestName, EBEPack pack) {
        addChestLikeModel("template_chest_left", leftTex, chestName, new Identifier("block/" + chestName + "_left"), pack);
        addChestLikeModel("template_chest_left_lid", leftTex, chestName, new Identifier("block/" + chestName + "_left_lid"), pack);
        addChestLikeModel("template_chest_left_trunk", leftTex, chestName, new Identifier("block/" + chestName + "_left_trunk"), pack);
        addChestLikeModel("template_chest_right", rightTex, chestName, new Identifier("block/" + chestName + "_right"), pack);
        addChestLikeModel("template_chest_right_lid", rightTex, chestName, new Identifier("block/" + chestName + "_right_lid"), pack);
        addChestLikeModel("template_chest_right_trunk", rightTex, chestName, new Identifier("block/" + chestName + "_right_trunk"), pack);
    }

    private static String chestParticle(String chestName) {
        if (EnhancedBlockEntities.CONFIG.experimentalChests) return kv("particle", "block/"+chestName+"_particle") + ",";
        return "";
    }

    private static String bedParticle(String bedColor) {
        if (EnhancedBlockEntities.CONFIG.experimentalBeds) return kv("particle", "block/"+bedColor+"_bed_particle") + ",";
        return "";
    }

    private static String signParticle(String signName) {
        if (EnhancedBlockEntities.CONFIG.experimentalSigns) return kv("particle", "block/"+signName+"_particle") + ",";
        return "";
    }

    private static void addBlockState(Identifier id, TemplateProvider.TemplateApplyingFunction vars, EBEPack pack) {
        pack.addTemplateResource(new Identifier(id.getNamespace(), "blockstates/" + id.getPath() + ".json"),
                t -> t.load("blockstate/base.json", d -> d.def("vars", vars)));
    }

    public static void addChestBlockStates(String chestName, EBEPack pack) {
        addBlockState(new Identifier(chestName),
                t0 -> list(
                        variantHFacing(t0, "type=single,facing=", "builtin:"+chestName+"_center"),
                        variantHFacing(t0, "type=left,facing=", "builtin:"+chestName+"_left"),
                        variantHFacing(t0, "type=right,facing=", "builtin:"+chestName+"_right")
                ), pack);
    }

    public static void addSingleChestOnlyBlockStates(String chestName, EBEPack pack) {
        addBlockState(new Identifier(chestName),
                t0 -> list(
                        variantHFacing(t0, "facing=", "builtin:"+chestName+"_center")
                ), pack);
    }

    public static void addParentModel(String parent, Identifier id, EBEPack pack) {
        pack.addTemplateResource(new Identifier(id.getNamespace(), "models/" + id.getPath() + ".json"), t ->
                "{" + kv("parent", parent) + "}");
    }

    public static void addParentTexModel(String parent, String textures, Identifier id, EBEPack pack) {
        pack.addTemplateResource(new Identifier(id.getNamespace(), "models/" + id.getPath() + ".json"), t ->
                t.load("model/parent_and_tex.json", d -> d.def("parent", parent).def("textures", textures)));
    }

    public static void addSignTypeModels(String signType, EBEPack pack) {
        var signName = signType+"_sign";
        var signTex = "entity/signs/"+signType;
        addRotation16Models(
                signParticle(signName) + kv("sign", signTex),
                "block/template_sign", "block/"+signName, ResourceUtil::signAOSuffix, pack);

        var hangingTexDef = list(
                kv("sign", "entity/signs/hanging/"+signType),
                kv("particle", "block/particle_hanging_sign_"+signType)
        );
        addRotation16Models(hangingTexDef, "block/template_hanging_sign", "block/"+signType+"_hanging_sign",
                ResourceUtil::signAOSuffix, pack);
        addRotation16Models(hangingTexDef, "block/template_hanging_sign_attached", "block/"+signType+"_hanging_sign_attached",
                ResourceUtil::signAOSuffix, pack);

        addParentTexModel(signAOSuffix("block/template_wall_sign"),
                signParticle(signName) + kv("sign", signTex), new Identifier("block/"+signType+"_wall_sign"), pack);
        addParentTexModel(signAOSuffix("block/template_wall_hanging_sign"),
                hangingTexDef, new Identifier("block/"+signType+"_wall_hanging_sign"), pack);
    }

    public static void addRotation16Models(String textures, String templatePrefix, String modelPrefix, Function<String, String> suffix, EBEPack pack) {
        addParentTexModel(suffix.apply(templatePrefix+"_0"), textures, new Identifier(modelPrefix + "_0"), pack);
        addParentTexModel(suffix.apply(templatePrefix+"_22_5"), textures, new Identifier(modelPrefix + "_22_5"), pack);
        addParentTexModel(suffix.apply(templatePrefix+"_45"), textures, new Identifier(modelPrefix + "_45"), pack);
        addParentTexModel(suffix.apply(templatePrefix+"_67_5"), textures, new Identifier(modelPrefix + "_67_5"), pack);
    }

    private static String signAOSuffix(String model) {
        if (EnhancedBlockEntities.CONFIG.signAO) model += "_ao";
        return model;
    }

    public static void addSignBlockStates(String signName, String wallSignName, EBEPack pack) {
        addBlockState(new Identifier(signName),
                t -> variantRotation16(t, "rotation=", "block/"+signName), pack);
        addBlockState(new Identifier(wallSignName),
                t -> variantHFacing(t, "facing=", "block/"+wallSignName), pack);
    }

    public static void addHangingSignBlockStates(String signName, String wallSignName, EBEPack pack) {
        addBlockState(new Identifier(signName),
                t -> list(
                        variantRotation16(t, "attached=false,rotation=", "block/"+signName),
                        variantRotation16(t, "attached=true,rotation=", "block/"+signName+"_attached")
                ), pack);

        addBlockState(new Identifier(wallSignName),
                t -> variantHFacing(t, "facing=", "block/"+wallSignName), pack);
    }

    public static void addBellBlockState(EBEPack pack) {
        addBlockState(new Identifier("bell"),
                t -> {
                    var vars = new DelimitedAppender(",");
                    for (Direction dir : EBEUtil.HORIZONTAL_DIRECTIONS) {
                        int rot = (int)dir.asRotation() + 90;
                        vars
                                .append(variantY(t, "attachment=double_wall,facing="+dir.getName(), "builtin:bell_between_walls", rot))
                                .append(variantY(t, "attachment=ceiling,facing="+dir.getName(), "builtin:bell_ceiling", rot + 90)) // adding 90 here and below to maintain Parity with vanilla's weird choice of rotations
                                .append(variantY(t, "attachment=floor,facing="+dir.getName(), "builtin:bell_floor", rot + 90))
                                .append(variantY(t, "attachment=single_wall,facing="+dir.getName(), "builtin:bell_wall", rot));
                    }
                    return vars.get();
                }, pack);
    }

    public static void addBedModels(DyeColor bedColor, EBEPack pack) {
        String color = bedColor.getName();

        addParentTexModel(bedAOSuffix("block/template_bed_head"),
                bedParticle(color) + kv("bed", "entity/bed/" + color),
                new Identifier("block/" + color + "_bed_head"), pack);
        addParentTexModel(bedAOSuffix("block/template_bed_foot"),
                bedParticle(color) + kv("bed", "entity/bed/" + color),
                new Identifier("block/" + color + "_bed_foot"), pack);
    }

    public static void addBedBlockState(DyeColor bedColor, EBEPack pack) {
        String color = bedColor.getName();
        addBlockState(new Identifier(color + "_bed"),
                t -> {
                    var vars = new DelimitedAppender(",");
                    for (Direction dir : EBEUtil.HORIZONTAL_DIRECTIONS) {
                        int rot = (int)dir.asRotation() + 180;
                        vars
                                .append(variantY(t, "part=head,facing="+dir.getName(), "block/" + bedColor + "_bed_head", rot))
                                .append(variantY(t, "part=foot,facing="+dir.getName(), "block/" + bedColor + "_bed_foot", rot));
                    }
                    return vars.get();
                }, pack);
    }

    private static String bedAOSuffix(String model) {
        if (EnhancedBlockEntities.CONFIG.bedAO) model += "_ao";
        return model;
    }

    public static void addShulkerBoxModels(@Nullable DyeColor color, EBEPack pack) {
        var texture = color != null ? "entity/shulker/shulker_"+color.getName() : "entity/shulker/shulker";
        var shulkerBoxStr = color != null ? color.getName()+"_shulker_box" : "shulker_box";
        var particle = "block/"+shulkerBoxStr;
        addParentTexModel("block/template_shulker_box",
                list(kv("shulker", texture), kv("particle", particle)),
                new Identifier("block/"+shulkerBoxStr), pack);
        addParentTexModel("block/template_shulker_box_bottom",
                list(kv("shulker", texture), kv("particle", particle)),
                new Identifier("block/"+shulkerBoxStr+"_bottom"), pack);
        addParentTexModel("block/template_shulker_box_lid",
                list(kv("shulker", texture), kv("particle", particle)),
                new Identifier("block/"+shulkerBoxStr+"_lid"), pack);
    }

    public static void addShulkerBoxBlockStates(@Nullable DyeColor color, EBEPack pack) {
        var shulkerBoxStr = color != null ? color.getName()+"_shulker_box" : "shulker_box";
        addBlockState(new Identifier(shulkerBoxStr),
                t -> {
                    var vars = new DelimitedAppender(",");
                    vars
                            .append(variant(t, "facing=up", "builtin:"+shulkerBoxStr))
                            .append(variantXY(t, "facing=down", "builtin:"+shulkerBoxStr, 180, 0));
                    for (Direction dir : EBEUtil.HORIZONTAL_DIRECTIONS) {
                        int rot = (int)dir.asRotation() + 180;
                        vars.append(variantXY(t, "facing="+dir.getName(), "builtin:"+shulkerBoxStr, 90, rot));
                    }
                    return vars.get();
                }, pack);
    }

    public static void addDecoratedPotBlockState(EBEPack pack) {
        addBlockState(new Identifier("decorated_pot"),
                t -> variantHFacing(t, "facing=", "builtin:decorated_pot"), pack);
    }

    public static void addDecoratedPotPatternModels(RegistryKey<String> patternKey, EBEPack pack) {
        for (Direction dir : EBEUtil.HORIZONTAL_DIRECTIONS) {
            addParentTexModel("block/template_pottery_pattern_" + dir.getName(),
                    kv("pattern", TexturedRenderLayers.getDecoratedPotPatternTextureId(patternKey).getTextureId().toString()),
                    new Identifier("block/" + patternKey.getValue().getPath() + "_" + dir.getName()),
                    pack);
        }
    }

    public static void resetBasePack() {
        BASE_PACK = new EBEPack(EBEUtil.id("base_resources"), EnhancedBlockEntities.TEMPLATE_LOADER);
    }

    public static void resetTopLevelPack() {
        TOP_LEVEL_PACK = new EBEPack(EBEUtil.id("top_level_resources"), EnhancedBlockEntities.TEMPLATE_LOADER);
    }

    public static EBEPack getBasePack() {
        return BASE_PACK;
    }

    public static EBEPack getTopLevelPack() {
        return TOP_LEVEL_PACK;
    }

    /**
     * @return the pack most appropriate for resources that might be accidentally overwritten by resource packs
     */
    public static EBEPack getPackForCompat() {
        if (EnhancedBlockEntities.CONFIG.forceResourcePackCompat) {
            return getTopLevelPack();
        }

        return getBasePack();
    }

    public static void dumpModAssets(Path dest) throws IOException {
        var roots = FabricLoader.getInstance().getModContainer(EnhancedBlockEntities.ID)
                .map(ModContainer::getRootPaths).orElse(List.of());

        for (var root : roots) {
            var sourceAssets = Files.walk(root.resolve("assets"));
            for (var asset : sourceAssets.collect(Collectors.toSet())) {
                if (!Files.isDirectory(asset)) {
                    var out = dest.resolve(root.relativize(asset));
                    if (!Files.exists(out.getParent())) {
                        Files.createDirectories(out.getParent());
                    }
                    Files.copy(asset, out, Files.exists(out) ? new CopyOption[] {StandardCopyOption.REPLACE_EXISTING} : new CopyOption[] {});
                }
            }
        }
    }

    public static void dumpAllPacks(Path dest) throws IOException {
        getBasePack().dump(dest);
        getTopLevelPack().dump(dest);
        dumpModAssets(dest);
    }

    private static class DelimitedAppender {
        private final StringBuilder builder = new StringBuilder();
        private final CharSequence delimiter;

        private DelimitedAppender(CharSequence delimiter) {
            this.delimiter = delimiter;
        }

        public DelimitedAppender append(CharSequence seq) {
            if (!this.builder.isEmpty()) this.builder.append(this.delimiter);

            this.builder.append(seq);
            return this;
        }

        public String get() {
            return this.builder.toString();
        }
    }

    static {
        resetBasePack();
        resetTopLevelPack();
    }
}
