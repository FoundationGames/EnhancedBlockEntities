package foundationgames.enhancedblockentities.config.gui.screen;

import com.google.common.collect.ImmutableList;
import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.ReloadType;
import foundationgames.enhancedblockentities.config.gui.element.EBEOptionListWidget;
import foundationgames.enhancedblockentities.config.gui.option.EBEOption;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

public class EBEConfigScreen extends Screen {
    private EBEOptionListWidget options;
    private final Screen parent;

    private static final ImmutableList<String> BOOLEAN_OPTIONS = ImmutableList.of("true", "false");
    private static final ImmutableList<String> ALLOWED_FORCED_DISABLED = ImmutableList.of("allowed", "forced", "disabled");
    private static final ImmutableList<String> SIGN_TEXT_OPTIONS = ImmutableList.of("smart", "all", "most", "some", "few");

    private static final Text HOLD_SHIFT = new TranslatableText("text.ebe.shiftForDesc").formatted(Formatting.DARK_GRAY, Formatting.ITALIC);
    private static final Text CHEST_OPTIONS_TITLE = new TranslatableText("text.ebe.chest_options");
    private static final Text SIGN_OPTIONS_TITLE = new TranslatableText("text.ebe.sign_options");
    private static final Text BELL_OPTIONS_TITLE = new TranslatableText("text.ebe.bell_options");
    private static final Text BED_OPTIONS_TITLE = new TranslatableText("text.ebe.bed_options");
    private static final Text SHULKER_BOX_OPTIONS_TITLE = new TranslatableText("text.ebe.shulker_box_options");

    public EBEConfigScreen(Screen screen) {
        super(new TranslatableText("screen.ebe.config"));
        parent = screen;
    }

    @Override
    protected void init() {
        super.init();
        int bottomCenter = this.width / 2 - 50;
        boolean inWorld = this.client.world != null;

        this.options = new EBEOptionListWidget(this.client, this.width, this.height, 34, this.height - 35, 24);
        if (inWorld) {
            this.options.setRenderBackground(false);
        }
        addOptions();
        this.addDrawableChild(options);

        this.addDrawableChild(new ButtonWidget(bottomCenter + 104, this.height - 27, 100, 20, ScreenTexts.DONE, button -> {
            applyChanges();
            onClose();
        }));
        this.addDrawableChild(new ButtonWidget(bottomCenter, this.height - 27, 100, 20, new TranslatableText("text.ebe.apply"), button -> this.applyChanges()));
        this.addDrawableChild(new ButtonWidget(bottomCenter - 104, this.height - 27, 100, 20, ScreenTexts.CANCEL, button -> this.onClose()));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.client.world == null) {
            this.renderBackground(matrices);
        } else {
            this.fillGradient(matrices, 0, 0, width, height, 0x4F232323, 0x4F232323);
        }

        super.render(matrices, mouseX, mouseY, delta);

        drawCenteredText(matrices, this.textRenderer, this.title, (int)(this.width * 0.5), 8, 0xFFFFFF);
        drawCenteredText(matrices, this.textRenderer, HOLD_SHIFT, (int)(this.width * 0.5), 21, 0xFFFFFF);

        EBEOptionListWidget.EBEOptionEntry hovered = this.options.getHovered(mouseX, mouseY);
        if (hovered != null && Screen.hasShiftDown()) {
            EBEOption option = hovered.option;
            List<Text> lines;
            if (option.hasValueComments) {
                lines = new ArrayList<>();
                lines.addAll(option.getValueCommentLines());
                lines.addAll(option.commentLines);
            } else {
                lines = option.commentLines;
            }
            renderTooltip(matrices, lines, mouseX, mouseY);
        }
    }

    @Override
    public void onClose() {
        this.client.setScreen(parent);
    }

    public void applyChanges() {
        EBEConfig config = EnhancedBlockEntities.CONFIG;
        Properties properties = new Properties();
        AtomicReference<ReloadType> type = new AtomicReference<>(ReloadType.NONE);
        options.forEach(option -> {
            if (!option.isDefault()) {
                type.set(type.get().or(option.reloadType));
            }
            properties.setProperty(option.key, option.getValue());
        });
        config.readFrom(properties);
        config.save();
        EnhancedBlockEntities.reload(type.get());
    }

    public void addOptions() {
        Properties config = new Properties();
        EnhancedBlockEntities.CONFIG.writeTo(config);
        options.addTitle(CHEST_OPTIONS_TITLE);
        options.add(
                new EBEOption(EBEConfig.RENDER_ENHANCED_CHESTS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.RENDER_ENHANCED_CHESTS_KEY)), false, ReloadType.RESOURCES),
                new EBEOption(EBEConfig.CHEST_AO_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.CHEST_AO_KEY)), false, ReloadType.RESOURCES)
        );
        options.addPair(
                new EBEOption(EBEConfig.EXPERIMENTAL_CHESTS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.EXPERIMENTAL_CHESTS_KEY)), false, ReloadType.RESOURCES),
                new EBEOption(EBEConfig.CHRISTMAS_CHESTS_KEY, ALLOWED_FORCED_DISABLED, ALLOWED_FORCED_DISABLED.indexOf(config.getProperty(EBEConfig.CHRISTMAS_CHESTS_KEY)), true, ReloadType.WORLD)
        );
        options.addTitle(SIGN_OPTIONS_TITLE);
        options.add(
                new EBEOption(EBEConfig.RENDER_ENHANCED_SIGNS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.RENDER_ENHANCED_SIGNS_KEY)), false, ReloadType.RESOURCES)
        );
        options.add(
                new EBEOption(EBEConfig.SIGN_TEXT_RENDERING_KEY, SIGN_TEXT_OPTIONS, SIGN_TEXT_OPTIONS.indexOf(config.getProperty(EBEConfig.SIGN_TEXT_RENDERING_KEY)), true, ReloadType.NONE)
        );
        options.addPair(
                new EBEOption(EBEConfig.EXPERIMENTAL_SIGNS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.EXPERIMENTAL_SIGNS_KEY)), false, ReloadType.RESOURCES),
                new EBEOption(EBEConfig.SIGN_AO_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.SIGN_AO_KEY)), false, ReloadType.RESOURCES)
        );
        options.addTitle(BELL_OPTIONS_TITLE);
        options.add(
                new EBEOption(EBEConfig.RENDER_ENHANCED_BELLS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.RENDER_ENHANCED_BELLS_KEY)), false, ReloadType.RESOURCES),
                new EBEOption(EBEConfig.BELL_AO_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.BELL_AO_KEY)), false, ReloadType.RESOURCES)
        );
        options.addTitle(BED_OPTIONS_TITLE);
        options.add(
                new EBEOption(EBEConfig.RENDER_ENHANCED_BEDS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.RENDER_ENHANCED_BEDS_KEY)), false, ReloadType.RESOURCES)
        );
        options.addPair(
                new EBEOption(EBEConfig.EXPERIMENTAL_BEDS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.EXPERIMENTAL_BEDS_KEY)), false, ReloadType.RESOURCES),
                new EBEOption(EBEConfig.BED_AO_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.BED_AO_KEY)), false, ReloadType.RESOURCES)
        );
        options.addTitle(SHULKER_BOX_OPTIONS_TITLE);
        options.add(
                new EBEOption(EBEConfig.RENDER_ENHANCED_SHULKER_BOXES_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.RENDER_ENHANCED_SHULKER_BOXES_KEY)), false, ReloadType.RESOURCES),
                new EBEOption(EBEConfig.SHULKER_BOX_AO_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.SHULKER_BOX_AO_KEY)), false, ReloadType.RESOURCES)
        );
    }
}
