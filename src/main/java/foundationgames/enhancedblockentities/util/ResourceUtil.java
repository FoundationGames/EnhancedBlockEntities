package foundationgames.enhancedblockentities.util;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.client.resource.EBEPack;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.block.DecoratedPotPatterns;
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

    public static String createChestItemModelResource(String centerChest) {
        return "{\"parent\":\"block/"+centerChest+"\",\"overrides\":[{\"predicate\":{\"ebe:is_christmas\":1},\"model\": \"item/christmas_chest\"}]}";
    }

    private static JVariant variantRotation16(JVariant variant, String keyPrefix, String modelPrefix) {
        return variant
                .put(keyPrefix+"0", JState.model(modelPrefix+"_0").y(180))
                .put(keyPrefix+"1", JState.model(modelPrefix+"_67_5").y(270))
                .put(keyPrefix+"2", JState.model(modelPrefix+"_45").y(270))
                .put(keyPrefix+"3", JState.model(modelPrefix+"_22_5").y(270))
                .put(keyPrefix+"4", JState.model(modelPrefix+"_0").y(270))
                .put(keyPrefix+"5", JState.model(modelPrefix+"_67_5"))
                .put(keyPrefix+"6", JState.model(modelPrefix+"_45"))
                .put(keyPrefix+"7", JState.model(modelPrefix+"_22_5"))
                .put(keyPrefix+"8", JState.model(modelPrefix+"_0"))
                .put(keyPrefix+"9", JState.model(modelPrefix+"_67_5").y(90))
                .put(keyPrefix+"10", JState.model(modelPrefix+"_45").y(90))
                .put(keyPrefix+"11", JState.model(modelPrefix+"_22_5").y(90))
                .put(keyPrefix+"12", JState.model(modelPrefix+"_0").y(90))
                .put(keyPrefix+"13", JState.model(modelPrefix+"_67_5").y(180))
                .put(keyPrefix+"14", JState.model(modelPrefix+"_45").y(180))
                .put(keyPrefix+"15", JState.model(modelPrefix+"_22_5").y(180));
    }

    private static JVariant variantHFacing(JVariant variant, String keyPrefix, String model) {
        return variant
                .put(keyPrefix+"north", JState.model(model))
                .put(keyPrefix+"west", JState.model(model).y(270))
                .put(keyPrefix+"south", JState.model(model).y(180))
                .put(keyPrefix+"east", JState.model(model).y(90));
    }

    public static void addSingleChestModels(String texture, String chestName, EBEPack pack) {
        pack.addModel(JModel.model().parent("block/template_chest_center").textures(withChestParticle(JModel.textures().var("chest", texture), chestName)), new Identifier("block/" + chestName + "_center"));
        pack.addModel(JModel.model().parent("block/template_chest_center_lid").textures(withChestParticle(JModel.textures().var("chest", texture), chestName)), new Identifier("block/" + chestName + "_center_lid"));
        pack.addModel(JModel.model().parent("block/template_chest_center_trunk").textures(withChestParticle(JModel.textures().var("chest", texture), chestName)), new Identifier("block/" + chestName + "_center_trunk"));
    }

    public static void addDoubleChestModels(String leftTex, String rightTex, String chestName, EBEPack pack) {
        pack.addModel(JModel.model().parent("block/template_chest_left").textures(withChestParticle(JModel.textures().var("chest", leftTex), chestName)), new Identifier("block/" + chestName + "_left"));
        pack.addModel(JModel.model().parent("block/template_chest_left_lid").textures(withChestParticle(JModel.textures().var("chest", leftTex), chestName)), new Identifier("block/" + chestName + "_left_lid"));
        pack.addModel(JModel.model().parent("block/template_chest_left_trunk").textures(withChestParticle(JModel.textures().var("chest", leftTex), chestName)), new Identifier("block/" + chestName + "_left_trunk"));
        pack.addModel(JModel.model().parent("block/template_chest_right").textures(withChestParticle(JModel.textures().var("chest", rightTex), chestName)), new Identifier("block/" + chestName + "_right"));
        pack.addModel(JModel.model().parent("block/template_chest_right_lid").textures(withChestParticle(JModel.textures().var("chest", rightTex), chestName)), new Identifier("block/" + chestName + "_right_lid"));
        pack.addModel(JModel.model().parent("block/template_chest_right_trunk").textures(withChestParticle(JModel.textures().var("chest", rightTex), chestName)), new Identifier("block/" + chestName + "_right_trunk"));
    }

    private static JTextures withChestParticle(JTextures textures, String chestName) {
        if (EnhancedBlockEntities.CONFIG.experimentalChests) textures.var("particle", "block/"+chestName+"_particle");
        return textures;
    }

    private static JTextures withBedParticle(JTextures textures, String bedColor) {
        if (EnhancedBlockEntities.CONFIG.experimentalBeds) textures.var("particle", "block/"+bedColor+"_bed_particle");
        return textures;
    }

    private static JTextures withSignParticle(JTextures textures, String signName) {
        if (EnhancedBlockEntities.CONFIG.experimentalSigns) textures.var("particle", "block/"+signName+"_particle");
        return textures;
    }

    public static void addChestBlockStates(String chestName, EBEPack pack) {
        var variant = JState.variant();
        variantHFacing(variant, "type=single,facing=", "builtin:"+chestName+"_center");
        variantHFacing(variant, "type=left,facing=", "builtin:"+chestName+"_left");
        variantHFacing(variant, "type=right,facing=", "builtin:"+chestName+"_right");

        pack.addBlockState(JState.state(variant), new Identifier(chestName));
    }

    public static void addSingleChestOnlyBlockStates(String chestName, EBEPack pack) {
        pack.addBlockState(
                JState.state(
                        variantHFacing(JState.variant(), "facing=", "builtin:"+chestName+"_center")
                ), new Identifier(chestName)
        );
    }

    public static void addSignTypeModels(String signType, EBEPack pack) {
        var signName = signType+"_sign";
        var signTex = "entity/signs/"+signType;
        addRotation16Models(
                withSignParticle(new JTextures().var("sign", signTex), signName),
                "block/template_sign", "block/"+signName, ResourceUtil::signAOSuffix, pack);

        var hangingTexDef = new JTextures()
                .var("sign", "entity/signs/hanging/"+signType)
                .var("particle", "block/particle_hanging_sign_"+signType);
        addRotation16Models(hangingTexDef, "block/template_hanging_sign", "block/"+signType+"_hanging_sign",
                ResourceUtil::signAOSuffix, pack);
        addRotation16Models(hangingTexDef, "block/template_hanging_sign_attached", "block/"+signType+"_hanging_sign_attached",
                ResourceUtil::signAOSuffix, pack);

        pack.addModel(JModel.model().parent(signAOSuffix("block/template_wall_sign"))
                .textures(withSignParticle(JModel.textures().var("sign", signTex), signName)), new Identifier("block/"+signType+"_wall_sign"));
        pack.addModel(JModel.model().parent(signAOSuffix("block/template_wall_hanging_sign"))
                .textures(hangingTexDef), new Identifier("block/"+signType+"_wall_hanging_sign"));
    }

    public static void addRotation16Models(JTextures textures, String templatePrefix, String modelPrefix, Function<String, String> suffix, EBEPack pack) {
        pack.addModel(JModel.model().parent(suffix.apply(templatePrefix+"_0")).textures(textures), new Identifier(modelPrefix + "_0"));
        pack.addModel(JModel.model().parent(suffix.apply(templatePrefix+"_22_5")).textures(textures), new Identifier(modelPrefix + "_22_5"));
        pack.addModel(JModel.model().parent(suffix.apply(templatePrefix+"_45")).textures(textures), new Identifier(modelPrefix + "_45"));
        pack.addModel(JModel.model().parent(suffix.apply(templatePrefix+"_67_5")).textures(textures), new Identifier(modelPrefix + "_67_5"));
    }

    private static String signAOSuffix(String model) {
        if (EnhancedBlockEntities.CONFIG.signAO) model += "_ao";
        return model;
    }

    public static void addSignBlockStates(String signName, String wallSignName, EBEPack pack) {
        pack.addBlockState(
                JState.state(
                        variantRotation16(JState.variant(), "rotation=", "block/"+signName)
                ), new Identifier(signName)
        );
        pack.addBlockState(
                JState.state(
                        variantHFacing(JState.variant(), "facing=", "block/"+wallSignName)
                ), new Identifier(wallSignName)
        );
    }

    public static void addHangingSignBlockStates(String signName, String wallSignName, EBEPack pack) {
        var variant = JState.variant();
        variantRotation16(variant, "attached=false,rotation=", "block/"+signName);
        variantRotation16(variant, "attached=true,rotation=", "block/"+signName+"_attached");

        pack.addBlockState(JState.state(variant), new Identifier(signName));
        pack.addBlockState(
                JState.state(
                        variantHFacing(JState.variant(), "facing=", "block/"+wallSignName)
                ), new Identifier(wallSignName)
        );
    }

    public static void addBellBlockState(EBEPack pack) {
        JVariant variant = JState.variant();
        for (Direction dir : EBEUtil.HORIZONTAL_DIRECTIONS) {
            int rot = (int)dir.asRotation() + 90;
            variant
                    .put("attachment=double_wall,facing="+dir.getName(), JState.model("builtin:bell_between_walls").y(rot))
                    .put("attachment=ceiling,facing="+dir.getName(), JState.model("builtin:bell_ceiling").y(rot + 90)) // adding 90 here and below to maintain Parity with vanilla's weird choice of rotations
                    .put("attachment=floor,facing="+dir.getName(), JState.model("builtin:bell_floor").y(rot + 90))
                    .put("attachment=single_wall,facing="+dir.getName(), JState.model("builtin:bell_wall").y(rot))
            ;
        }
        pack.addBlockState(JState.state(variant), new Identifier("bell"));
    }

    public static void addBedModels(DyeColor bedColor, EBEPack pack) {
        String color = bedColor.getName();
        pack.addModel(
                JModel.model()
                        .parent(bedAOSuffix("block/template_bed_head"))
                        .textures(withBedParticle(JModel.textures().var("bed", "entity/bed/" + color), color)),
                new Identifier("block/" + color + "_bed_head")
        );
        pack.addModel(
                JModel.model()
                        .parent(bedAOSuffix("block/template_bed_foot"))
                        .textures(withBedParticle(JModel.textures().var("bed", "entity/bed/" + color), color)),
                new Identifier("block/" + color + "_bed_foot")
        );
    }

    public static void addBedBlockState(DyeColor bedColor, EBEPack pack) {
        String color = bedColor.getName();
        JVariant variant = JState.variant();
        for (Direction dir : EBEUtil.HORIZONTAL_DIRECTIONS) {
            int rot = (int)dir.asRotation() + 180;
            variant
                    .put("part=head,facing="+dir.getName(), JState.model("block/" + bedColor + "_bed_head").y(rot))
                    .put("part=foot,facing="+dir.getName(), JState.model("block/" + bedColor + "_bed_foot").y(rot));
        }
        pack.addBlockState(JState.state(variant), new Identifier(color + "_bed"));
    }

    private static String bedAOSuffix(String model) {
        if (EnhancedBlockEntities.CONFIG.bedAO) model += "_ao";
        return model;
    }

    public static void addShulkerBoxModels(@Nullable DyeColor color, EBEPack pack) {
        var texture = color != null ? "entity/shulker/shulker_"+color.getName() : "entity/shulker/shulker";
        var shulkerBoxStr = color != null ? color.getName()+"_shulker_box" : "shulker_box";
        var particle = "block/"+shulkerBoxStr;
        pack.addModel(JModel.model().parent("block/template_shulker_box").textures(JModel.textures().var("shulker", texture).var("particle", particle)), new Identifier("block/"+shulkerBoxStr));
        pack.addModel(JModel.model().parent("block/template_shulker_box_bottom").textures(JModel.textures().var("shulker", texture).var("particle", particle)), new Identifier("block/"+shulkerBoxStr+"_bottom"));
        pack.addModel(JModel.model().parent("block/template_shulker_box_lid").textures(JModel.textures().var("shulker", texture).var("particle", particle)), new Identifier("block/"+shulkerBoxStr+"_lid"));
    }

    public static void addShulkerBoxBlockStates(@Nullable DyeColor color, EBEPack pack) {
        var shulkerBoxStr = color != null ? color.getName()+"_shulker_box" : "shulker_box";
        var variant = JState.variant()
                .put("facing=up", JState.model("builtin:"+shulkerBoxStr))
                .put("facing=down", JState.model("builtin:"+shulkerBoxStr).x(180));
        for (Direction dir : EBEUtil.HORIZONTAL_DIRECTIONS) {
            int rot = (int)dir.asRotation() + 180;
            variant.put("facing="+dir.getName(), JState.model("builtin:"+shulkerBoxStr).x(90).y(rot));
        }
        pack.addBlockState(JState.state(variant), new Identifier(shulkerBoxStr));
    }

    public static void addDecoratedPotBlockState(EBEPack pack) {
        pack.addBlockState(JState.state(variantHFacing(JState.variant(), "facing=", "builtin:decorated_pot")), new Identifier("decorated_pot"));
    }

    public static void addDecoratedPotPatternModels(RegistryKey<String> patternKey, EBEPack pack) {
        for (Direction dir : EBEUtil.HORIZONTAL_DIRECTIONS) {
            pack.addModel(JModel.model().parent("block/template_pottery_pattern_" + dir.getName())
                    .textures(
                            JModel.textures()
                                    .var("pattern", DecoratedPotPatterns.getTextureId(patternKey).toString())
                    ),
                    new Identifier("block/" + patternKey.getValue().getPath() + "_" + dir.getName())
            );
        }
    }

    public static void resetBasePack() {
        BASE_PACK = new EBEPack(EBEUtil.id("base_resources"));
    }

    public static void resetTopLevelPack() {
        TOP_LEVEL_PACK = new EBEPack(EBEUtil.id("top_level_resources"));
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
        getBasePack().dumpDirect(dest);
        getTopLevelPack().dumpDirect(dest);
        dumpModAssets(dest);
    }

    static {
        resetBasePack();
        resetTopLevelPack();
    }
}
