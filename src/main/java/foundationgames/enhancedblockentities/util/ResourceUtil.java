package foundationgames.enhancedblockentities.util;

import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.client.resource.ExperimentalResourcePack;
import net.devtech.arrp.api.RuntimeResourcePack;
import net.devtech.arrp.json.blockstate.JState;
import net.devtech.arrp.json.blockstate.JVariant;
import net.devtech.arrp.json.models.JModel;
import net.devtech.arrp.json.models.JTextures;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
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
import java.util.stream.Collectors;

public enum ResourceUtil {;
    private static RuntimeResourcePack PACK;
    private static ExperimentalResourcePack EXPERIMENTAL_PACK;

    public static final String CHEST_ITEM_MODEL_RESOURCE = "{\"parent\":\"block/chest_center\",\"overrides\":[{\"predicate\":{\"is_christmas\":1},\"model\": \"item/christmas_chest\"}]}";

    public static void addSingleChestModels(String texture, String chestName, RuntimeResourcePack pack) {
        pack.addModel(JModel.model().parent("block/template_chest_center").textures(withChestParticle(JModel.textures().var("chest", texture), chestName)), new Identifier("block/" + chestName + "_center"));
        pack.addModel(JModel.model().parent("block/template_chest_center_lid").textures(withChestParticle(JModel.textures().var("chest", texture), chestName)), new Identifier("block/" + chestName + "_center_lid"));
        pack.addModel(JModel.model().parent("block/template_chest_center_trunk").textures(withChestParticle(JModel.textures().var("chest", texture), chestName)), new Identifier("block/" + chestName + "_center_trunk"));
    }

    public static void addDoubleChestModels(String leftTex, String rightTex, String chestName, RuntimeResourcePack pack) {
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

    public static void addChestBlockStates(String chestName, RuntimeResourcePack pack) {
        pack.addBlockState(
                JState.state(
                        JState.variant()
                                .put("facing=east,type=single", JState.model("builtin:"+chestName+"_center").y(90))
                                .put("facing=north,type=single", JState.model("builtin:"+chestName+"_center"))
                                .put("facing=south,type=single", JState.model("builtin:"+chestName+"_center").y(180))
                                .put("facing=west,type=single", JState.model("builtin:"+chestName+"_center").y(270))
                                .put("facing=east,type=left", JState.model("builtin:"+chestName+"_left").y(90))
                                .put("facing=north,type=left", JState.model("builtin:"+chestName+"_left"))
                                .put("facing=south,type=left", JState.model("builtin:"+chestName+"_left").y(180))
                                .put("facing=west,type=left", JState.model("builtin:"+chestName+"_left").y(270))
                                .put("facing=east,type=right", JState.model("builtin:"+chestName+"_right").y(90))
                                .put("facing=north,type=right", JState.model("builtin:"+chestName+"_right"))
                                .put("facing=south,type=right", JState.model("builtin:"+chestName+"_right").y(180))
                                .put("facing=west,type=right", JState.model("builtin:"+chestName+"_right").y(270))
                ), new Identifier(chestName)
        );
    }

    public static void addSingleChestOnlyBlockStates(String chestName, RuntimeResourcePack pack) {
        pack.addBlockState(
                JState.state(
                        JState.variant()
                                .put("facing=east", JState.model("builtin:"+chestName+"_center").y(90))
                                .put("facing=north", JState.model("builtin:"+chestName+"_center"))
                                .put("facing=south", JState.model("builtin:"+chestName+"_center").y(180))
                                .put("facing=west", JState.model("builtin:"+chestName+"_center").y(270))
                ), new Identifier(chestName)
        );
    }

    public static void addSignModels(String texture, String signName, String wallSignName, RuntimeResourcePack pack) {
        pack.addModel(JModel.model().parent(signAOSuffix("block/template_sign_0")).textures(withSignParticle(JModel.textures().var("sign", texture), signName)), new Identifier("block/" + signName + "_0"));
        pack.addModel(JModel.model().parent(signAOSuffix("block/template_sign_22_5")).textures(withSignParticle(JModel.textures().var("sign", texture), signName)), new Identifier("block/" + signName + "_22_5"));
        pack.addModel(JModel.model().parent(signAOSuffix("block/template_sign_45")).textures(withSignParticle(JModel.textures().var("sign", texture), signName)), new Identifier("block/" + signName + "_45"));
        pack.addModel(JModel.model().parent(signAOSuffix("block/template_sign_67_5")).textures(withSignParticle(JModel.textures().var("sign", texture), signName)), new Identifier("block/" + signName + "_67_5"));
        pack.addModel(JModel.model().parent(signAOSuffix("block/template_wall_sign")).textures(withSignParticle(JModel.textures().var("sign", texture), signName)), new Identifier("block/" + wallSignName));
    }

    private static String signAOSuffix(String model) {
        if (EnhancedBlockEntities.CONFIG.signAO) model += "_ao";
        return model;
    }

    public static void addSignBlockStates(String signName, String wallSignName, RuntimeResourcePack pack) {
        pack.addBlockState(
                JState.state(
                        JState.variant()
                                .put("rotation=0", JState.model("block/"+signName+"_0").y(180))
                                .put("rotation=1", JState.model("block/"+signName+"_67_5").y(270))
                                .put("rotation=2", JState.model("block/"+signName+"_45").y(270))
                                .put("rotation=3", JState.model("block/"+signName+"_22_5").y(270))
                                .put("rotation=4", JState.model("block/"+signName+"_0").y(270))
                                .put("rotation=5", JState.model("block/"+signName+"_67_5"))
                                .put("rotation=6", JState.model("block/"+signName+"_45"))
                                .put("rotation=7", JState.model("block/"+signName+"_22_5"))
                                .put("rotation=8", JState.model("block/"+signName+"_0"))
                                .put("rotation=9", JState.model("block/"+signName+"_67_5").y(90))
                                .put("rotation=10", JState.model("block/"+signName+"_45").y(90))
                                .put("rotation=11", JState.model("block/"+signName+"_22_5").y(90))
                                .put("rotation=12", JState.model("block/"+signName+"_0").y(90))
                                .put("rotation=13", JState.model("block/"+signName+"_67_5").y(180))
                                .put("rotation=14", JState.model("block/"+signName+"_45").y(180))
                                .put("rotation=15", JState.model("block/"+signName+"_22_5").y(180))
                ), new Identifier(signName)
        );
        pack.addBlockState(
                JState.state(
                        JState.variant()
                                .put("facing=north", JState.model("block/"+wallSignName))
                                .put("facing=west", JState.model("block/"+wallSignName).y(270))
                                .put("facing=south", JState.model("block/"+wallSignName).y(180))
                                .put("facing=east", JState.model("block/"+wallSignName).y(90))
                ), new Identifier(wallSignName)
        );
    }

    public static void addBellBlockState(RuntimeResourcePack pack) {
        JVariant variant = JState.variant();
        for (Direction dir : new Direction[] {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST}) {
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

    public static void addBedModels(DyeColor bedColor, RuntimeResourcePack pack) {
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

    public static void addBedBlockState(DyeColor bedColor, RuntimeResourcePack pack) {
        String color = bedColor.getName();
        JVariant variant = JState.variant();
        for (Direction dir : new Direction[] {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST}) {
            int rot = (int)dir.asRotation() + 180;
            variant
                    .put("part=head,facing="+dir.getName(), JState.model("block/" + bedColor + "_bed_head").y(rot))
                    .put("part=foot,facing="+dir.getName(), JState.model("block/" + bedColor + "_bed_foot").y(rot))
            ;
        }
        pack.addBlockState(JState.state(variant), new Identifier(color + "_bed"));
    }

    private static String bedAOSuffix(String model) {
        if (EnhancedBlockEntities.CONFIG.bedAO) model += "_ao";
        return model;
    }

    public static void addShulkerBoxModels(@Nullable DyeColor color, RuntimeResourcePack pack) {
        var texture = color != null ? "entity/shulker/shulker_"+color.getName() : "entity/shulker/shulker";
        var shulkerBoxStr = color != null ? color.getName()+"_shulker_box" : "shulker_box";
        var particle = "block/"+shulkerBoxStr;
        pack.addModel(JModel.model().parent("block/template_shulker_box").textures(JModel.textures().var("shulker", texture).var("particle", particle)), new Identifier("block/"+shulkerBoxStr));
        pack.addModel(JModel.model().parent("block/template_shulker_box_bottom").textures(JModel.textures().var("shulker", texture).var("particle", particle)), new Identifier("block/"+shulkerBoxStr+"_bottom"));
        pack.addModel(JModel.model().parent("block/template_shulker_box_lid").textures(JModel.textures().var("shulker", texture).var("particle", particle)), new Identifier("block/"+shulkerBoxStr+"_lid"));
    }

    public static void addShulkerBoxBlockStates(@Nullable DyeColor color, RuntimeResourcePack pack) {
        var shulkerBoxStr = color != null ? color.getName()+"_shulker_box" : "shulker_box";
        var variant = JState.variant()
                .put("facing=up", JState.model("builtin:"+shulkerBoxStr))
                .put("facing=down", JState.model("builtin:"+shulkerBoxStr).x(180));
        for (Direction dir : new Direction[] {Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST}) {
            int rot = (int)dir.asRotation() + 180;
            variant.put("facing="+dir.getName(), JState.model("builtin:"+shulkerBoxStr).x(90).y(rot));
        }
        pack.addBlockState(JState.state(variant), new Identifier(shulkerBoxStr));
    }

    public static void resetPack() {
        PACK = RuntimeResourcePack.create("ebe:pack");
    }

    public static void resetExperimentalPack() {
        EXPERIMENTAL_PACK = new ExperimentalResourcePack();
    }

    public static RuntimeResourcePack getPack() {
        return PACK;
    }

    public static ExperimentalResourcePack getExperimentalPack() {
        return EXPERIMENTAL_PACK;
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
        getPack().dumpDirect(dest);
        dumpModAssets(dest);
    }

    static {
        resetPack();
        resetExperimentalPack();
    }
}
