package foundationgames.enhancedblockentities.config.gui.screen;

import com.google.common.collect.ImmutableList;
import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.ReloadType;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.config.gui.option.EBEOption;
import foundationgames.enhancedblockentities.config.gui.option.TextPalette;
import foundationgames.enhancedblockentities.config.gui.widget.SectionTextWidget;
import foundationgames.enhancedblockentities.config.gui.widget.WidgetRowListWidget;
import foundationgames.enhancedblockentities.util.EBEUtil;
import foundationgames.enhancedblockentities.util.GuiUtil;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

public class EBEConfigScreen extends Screen {
    private WidgetRowListWidget optionsWidget;
    private final List<EBEOption> options = new ArrayList<>();
    private final Screen parent;

    private static final ImmutableList<String> BOOLEAN_OPTIONS = ImmutableList.of("true", "false");
    private static final ImmutableList<String> ALLOWED_FORCED_DISABLED = ImmutableList.of("allowed", "forced", "disabled");
    private static final ImmutableList<String> SIGN_TEXT_OPTIONS = ImmutableList.of("smart", "all", "most", "some", "few");

    private static final Text HOLD_SHIFT = Text.translatable("text.ebe.descriptions").formatted(Formatting.GRAY, Formatting.ITALIC);
    private static final Text CHEST_OPTIONS_TITLE = Text.translatable("text.ebe.chest_options");
    private static final Text SIGN_OPTIONS_TITLE = Text.translatable("text.ebe.sign_options");
    private static final Text BELL_OPTIONS_TITLE = Text.translatable("text.ebe.bell_options");
    private static final Text BED_OPTIONS_TITLE = Text.translatable("text.ebe.bed_options");
    private static final Text SHULKER_BOX_OPTIONS_TITLE = Text.translatable("text.ebe.shulker_box_options");
    private static final Text DECORATED_POT_OPTIONS_TITLE = Text.translatable("text.ebe.decorated_pot_options");
    private static final Text ADVANCED_TITLE = Text.translatable("text.ebe.advanced");

    private static final Text DUMP_LABEL = Text.translatable("option.ebe.dump");

    private final Text dumpTooltip = GuiUtil.shorten(I18n.translate("option.ebe.dump.comment"), 20);

    public EBEConfigScreen(Screen screen) {
        super(Text.translatable("screen.ebe.config"));
        parent = screen;
    }

    @Override
    protected void init() {
        super.init();

        this.optionsWidget = new WidgetRowListWidget(this.client, this.width, this.height, 34, this.height - 34, 316, 20);
        this.options.clear();

        addOptions();
        this.addDrawableChild(optionsWidget);

        var menuButtons = new GridWidget();
        menuButtons.setColumnSpacing(4);

        var menuButtonAdder = menuButtons.createAdder(3);

        menuButtonAdder.add(ButtonWidget.builder(ScreenTexts.CANCEL, button -> this.close())
                .size(100, 20).build());
        menuButtonAdder.add(ButtonWidget.builder(Text.translatable("text.ebe.apply"), button -> this.applyChanges())
                .size(100, 20).build());
        menuButtonAdder.add(ButtonWidget.builder(ScreenTexts.DONE,
                button -> {
                    applyChanges();
                    close();
                })
                .size(100, 20).build());

        menuButtons.refreshPositions();
        menuButtons.setPosition((this.width - menuButtons.getWidth()) / 2, this.height - 27);
        menuButtons.forEachChild((child) -> {
            child.setNavigationOrder(1);
            this.addDrawableChild(child);
        });
    }

//    @Override
//    protected void renderDarkening(DrawContext context) {
//        renderDarkening(context, 0, 0, this.width, 34);
//        renderDarkening(context, 0, this.height - 35, this.width, 35);
//    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
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

        final var textRenderer = this.client.textRenderer;

        optionsWidget.add(new SectionTextWidget(CHEST_OPTIONS_TITLE, textRenderer));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.RENDER_ENHANCED_CHESTS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.RENDER_ENHANCED_CHESTS_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.CHEST_AO_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.CHEST_AO_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.EXPERIMENTAL_CHESTS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.EXPERIMENTAL_CHESTS_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ), option(
                new EBEOption(EBEConfig.CHRISTMAS_CHESTS_KEY, ALLOWED_FORCED_DISABLED, ALLOWED_FORCED_DISABLED.indexOf(config.getProperty(EBEConfig.CHRISTMAS_CHESTS_KEY)), true, TextPalette.rainbow(0.35f), ReloadType.WORLD)
        ));

        optionsWidget.add(new SectionTextWidget(SIGN_OPTIONS_TITLE, textRenderer));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.RENDER_ENHANCED_SIGNS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.RENDER_ENHANCED_SIGNS_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.SIGN_TEXT_RENDERING_KEY, SIGN_TEXT_OPTIONS, SIGN_TEXT_OPTIONS.indexOf(config.getProperty(EBEConfig.SIGN_TEXT_RENDERING_KEY)), true, TextPalette.rainbow(0.45f), ReloadType.NONE)
        ));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.EXPERIMENTAL_SIGNS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.EXPERIMENTAL_SIGNS_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ), option(
                new EBEOption(EBEConfig.SIGN_AO_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.SIGN_AO_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));

        optionsWidget.add(new SectionTextWidget(BELL_OPTIONS_TITLE, textRenderer));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.RENDER_ENHANCED_BELLS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.RENDER_ENHANCED_BELLS_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.BELL_AO_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.BELL_AO_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));

        optionsWidget.add(new SectionTextWidget(BED_OPTIONS_TITLE, textRenderer));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.RENDER_ENHANCED_BEDS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.RENDER_ENHANCED_BEDS_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.EXPERIMENTAL_BEDS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.EXPERIMENTAL_BEDS_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ), option(
                new EBEOption(EBEConfig.BED_AO_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.BED_AO_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));

        optionsWidget.add(new SectionTextWidget(SHULKER_BOX_OPTIONS_TITLE, textRenderer));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.RENDER_ENHANCED_SHULKER_BOXES_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.RENDER_ENHANCED_SHULKER_BOXES_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.SHULKER_BOX_AO_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.SHULKER_BOX_AO_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));

        optionsWidget.add(new SectionTextWidget(DECORATED_POT_OPTIONS_TITLE, textRenderer));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.RENDER_ENHANCED_DECORATED_POTS_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.RENDER_ENHANCED_DECORATED_POTS_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.DECORATED_POT_AO_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.DECORATED_POT_AO_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));

        optionsWidget.add(new SectionTextWidget(ADVANCED_TITLE, textRenderer));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.FORCE_RESOURCE_PACK_COMPAT_KEY, BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty(EBEConfig.FORCE_RESOURCE_PACK_COMPAT_KEY)), false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.add(ButtonWidget.builder(DUMP_LABEL, b -> {
            try {
                EBEUtil.dumpResources();
            } catch (IOException e) {
                EnhancedBlockEntities.LOG.error(e);
            }
        }).tooltip(Tooltip.of(dumpTooltip)).build());
    }

    private ButtonWidget option(EBEOption option) {
        options.add(option);

        return ButtonWidget.builder(option.getText(), b -> {
            option.next();
            b.setMessage(option.getText());
            b.setTooltip(option.getTooltip());
        }).tooltip(option.getTooltip()).build();
    }
}
