package foundationgames.enhancedblockentities.config.gui.screen;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.lambdaurora.spruceui.Position;
import dev.lambdaurora.spruceui.background.DirtTexturedBackground;
import dev.lambdaurora.spruceui.option.SpruceCyclingOption;
import dev.lambdaurora.spruceui.option.SpruceOption;
import dev.lambdaurora.spruceui.option.SpruceSeparatorOption;
import dev.lambdaurora.spruceui.screen.SpruceScreen;
import dev.lambdaurora.spruceui.util.RenderUtil;
import dev.lambdaurora.spruceui.widget.SpruceButtonWidget;
import dev.lambdaurora.spruceui.widget.container.SpruceOptionListWidget;
import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.ReloadType;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.config.gui.option.EBEOption;
import foundationgames.enhancedblockentities.config.gui.option.TextPalette;
import foundationgames.enhancedblockentities.util.EBEUtil;
import foundationgames.enhancedblockentities.util.GuiUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.RotatingCubeMapRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

public class EBEConfigScreen extends SpruceScreen {
    private SpruceOptionListWidget optionsWidget;
    private List<EBEOption> options = new ArrayList<>();
    private final Screen parent;

    private static final ImmutableList<String> BOOLEAN_OPTIONS = ImmutableList.of("true", "false");
    private static final ImmutableList<String> ALLOWED_FORCED_DISABLED = ImmutableList.of("allowed", "forced", "disabled");
    private static final ImmutableList<String> SIGN_TEXT_OPTIONS = ImmutableList.of("smart", "all", "most", "some", "few");

    private static final Text HOLD_SHIFT = Text.translatable("text.ebe.descriptions").formatted(Formatting.DARK_GRAY, Formatting.ITALIC);
    private static final String CHEST_OPTIONS_TITLE = "text.ebe.chest_options";
    private static final String SIGN_OPTIONS_TITLE = "text.ebe.sign_options";
    private static final String BELL_OPTIONS_TITLE = "text.ebe.bell_options";
    private static final String BED_OPTIONS_TITLE = "text.ebe.bed_options";
    private static final String SHULKER_BOX_OPTIONS_TITLE = "text.ebe.shulker_box_options";
    private static final String DECORATED_POT_OPTIONS_TITLE = "text.ebe.decorated_pot_options";
    private static final String ADVANCED_TITLE = "text.ebe.advanced";

    private static final Text DUMP_LABEL = Text.translatable("option.ebe.dump");

    private final Text dumpTooltip = GuiUtil.shorten(I18n.translate("option.ebe.dump.comment"), 20);
    private final RotatingCubeMapRenderer background = new RotatingCubeMapRenderer(TitleScreen.PANORAMA_CUBE_MAP);

    public EBEConfigScreen(Screen screen) {
        super(Text.translatable("screen.ebe.config"));
        parent = screen;
    }

    @Override
    protected void init() {
        super.init();
        int bottomCenter = this.width / 2 - 50;

        this.optionsWidget = new SpruceOptionListWidget(Position.of(0, 34), this.width, this.height - 69);
        this.options.clear();
        this.optionsWidget.setBackground(new DirtTexturedBackground(32, 32, 32, 0));
        addOptions();
        this.addDrawableChild(optionsWidget);

        this.addDrawableChild(new SpruceButtonWidget(Position.of(bottomCenter - 104, this.height - 27), 100, 20, ScreenTexts.CANCEL, button -> this.close()));
        this.addDrawableChild(new SpruceButtonWidget(Position.of(bottomCenter, this.height - 27), 100, 20, Text.translatable("text.ebe.apply"), button -> this.applyChanges()));
        this.addDrawableChild(new SpruceButtonWidget(Position.of(bottomCenter + 104, this.height - 27), 100, 20, ScreenTexts.DONE, button -> {
            applyChanges();
            close();
        }));
    }

    @Override
    public void renderBackground(DrawContext context) {
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if (this.client.world == null) {
            this.background.render(delta, 1);
            RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        }

        fillGradient(matrices, 0, 0, width, height, 0x4F141414, 0x4F141414);
        RenderUtil.renderBackgroundTexture(0, 0, this.width, 34, 0);
        RenderUtil.renderBackgroundTexture(0, this.height - 35, this.width, 35, 0);

        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(this.textRenderer, this.title, (int)(this.width * 0.5), 8, 0xFFFFFF);
        context.drawCenteredTextWithShadow(this.textRenderer, HOLD_SHIFT, (int)(this.width * 0.5), 21, 0xFFFFFF);
    }

    @Override
    public void close() {
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

        optionsWidget.addSingleOptionEntry(new SpruceSeparatorOption(CHEST_OPTIONS_TITLE, true, null));
        optionsWidget.addSingleOptionEntry(option(
                new EBEOption(EBEConfig.RENDER_ENHANCED_CHESTS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.RENDER_ENHANCED_CHESTS_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.addSingleOptionEntry(option(
                new EBEOption(EBEConfig.CHEST_AO_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.CHEST_AO_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.addOptionEntry(option(
                new EBEOption(EBEConfig.EXPERIMENTAL_CHESTS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.EXPERIMENTAL_CHESTS_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ), option(
                new EBEOption(EBEConfig.CHRISTMAS_CHESTS_KEY, ALLOWED_FORCED_DISABLED, ALLOWED_FORCED_DISABLED.indexOf(config.getProperty(EBEConfig.CHRISTMAS_CHESTS_KEY)), true, TextPalette.rainbow(0.35f), ReloadType.WORLD)
        ));

        optionsWidget.addSingleOptionEntry(new SpruceSeparatorOption(SIGN_OPTIONS_TITLE, true, null));
        optionsWidget.addSingleOptionEntry(option(
                new EBEOption(EBEConfig.RENDER_ENHANCED_SIGNS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.RENDER_ENHANCED_SIGNS_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.addSingleOptionEntry(option(
                new EBEOption(EBEConfig.SIGN_TEXT_RENDERING_KEY, SIGN_TEXT_OPTIONS, SIGN_TEXT_OPTIONS.indexOf(config.getProperty(EBEConfig.SIGN_TEXT_RENDERING_KEY)), true, TextPalette.rainbow(0.45f), ReloadType.NONE)
        ));
        optionsWidget.addOptionEntry(option(
                new EBEOption(EBEConfig.EXPERIMENTAL_SIGNS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.EXPERIMENTAL_SIGNS_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ), option(
                new EBEOption(EBEConfig.SIGN_AO_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.SIGN_AO_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));

        optionsWidget.addSingleOptionEntry(new SpruceSeparatorOption(BELL_OPTIONS_TITLE, true, null));
        optionsWidget.addSingleOptionEntry(option(
                new EBEOption(EBEConfig.RENDER_ENHANCED_BELLS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.RENDER_ENHANCED_BELLS_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.addSingleOptionEntry(option(
                new EBEOption(EBEConfig.BELL_AO_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.BELL_AO_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));

        optionsWidget.addSingleOptionEntry(new SpruceSeparatorOption(BED_OPTIONS_TITLE, true, null));
        optionsWidget.addSingleOptionEntry(option(
                new EBEOption(EBEConfig.RENDER_ENHANCED_BEDS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.RENDER_ENHANCED_BEDS_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.addOptionEntry(option(
                new EBEOption(EBEConfig.EXPERIMENTAL_BEDS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.EXPERIMENTAL_BEDS_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ), option(
                new EBEOption(EBEConfig.BED_AO_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.BED_AO_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));

        optionsWidget.addSingleOptionEntry(new SpruceSeparatorOption(SHULKER_BOX_OPTIONS_TITLE, true, null));
        optionsWidget.addSingleOptionEntry(option(
                new EBEOption(EBEConfig.RENDER_ENHANCED_SHULKER_BOXES_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.RENDER_ENHANCED_SHULKER_BOXES_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.addSingleOptionEntry(option(
                new EBEOption(EBEConfig.SHULKER_BOX_AO_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.SHULKER_BOX_AO_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));

        optionsWidget.addSingleOptionEntry(new SpruceSeparatorOption(DECORATED_POT_OPTIONS_TITLE, true, null));
        optionsWidget.addSingleOptionEntry(option(
                new EBEOption(EBEConfig.RENDER_ENHANCED_DECORATED_POTS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.RENDER_ENHANCED_DECORATED_POTS_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.addSingleOptionEntry(option(
                new EBEOption(EBEConfig.DECORATED_POT_AO_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.DECORATED_POT_AO_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));

        optionsWidget.addSingleOptionEntry(new SpruceSeparatorOption(ADVANCED_TITLE, true, null));
        optionsWidget.addSingleOptionEntry(option(
                new EBEOption(EBEConfig.FORCE_RESOURCE_PACK_COMPAT_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.FORCE_RESOURCE_PACK_COMPAT_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.addSingleOptionEntry(new SpruceCyclingOption("option.ebe.dump", i -> {
            try {
                EBEUtil.dumpResources();
            } catch (IOException e) {
                EnhancedBlockEntities.LOG.error(e);
            }
        }, o -> DUMP_LABEL, dumpTooltip));
    }

    private SpruceOption option(EBEOption option) {
        options.add(option);
        return new SpruceCyclingOption(option.getOptionKey(), i -> option.next(), o -> option.getText(), option.getTooltip());
    }
}
