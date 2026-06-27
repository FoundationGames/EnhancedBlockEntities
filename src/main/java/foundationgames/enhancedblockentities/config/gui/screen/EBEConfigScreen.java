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
import net.minecraft.ChatChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.GridLayout;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

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

    private static final Component HOLD_SHIFT = Component.translatable("text.ebe.descriptions").formatted(ChatFormatting.GRAY, ChatFormatting.ITALIC);
    private static final Component CHEST_OPTIONS_TITLE = Component.translatable("text.ebe.chest_options");
    private static final Component SIGN_OPTIONS_TITLE = Component.translatable("text.ebe.sign_options");
    private static final Component BELL_OPTIONS_TITLE = Component.translatable("text.ebe.bell_options");
    private static final Component BED_OPTIONS_TITLE = Component.translatable("text.ebe.bed_options");
    private static final Component SHULKER_BOX_OPTIONS_TITLE = Component.translatable("text.ebe.shulker_box_options");
    private static final Component DECORATED_POT_OPTIONS_TITLE = Component.translatable("text.ebe.decorated_pot_options");
    private static final Component ADVANCED_TITLE = Component.translatable("text.ebe.advanced");

    private static final Component DUMP_LABEL = Component.translatable("option.ebe.dump");

    private final Component dumpTooltip = GuiUtil.shorten(I18n.translate("option.ebe.dump.comment"), 20);

    public EBEConfigScreen(Screen screen) {
        super(Component.translatable("screen.ebe.config"));
        parent = screen;
    }

    @Override
    protected void init() {
        super.init();

        this.optionsWidget = new WidgetRowListWidget(this.minecraft, this.width, this.height - 69, 34, 316, 20);
        this.options.clear();

        addOptions();
        this.addRenderableWidget(optionsWidget);

        var menuButtons = new GridLayout();
        menuButtons.setColumnSpacing(4);

        var menuButtonAdder = menuButtons.createAdder(3);

        menuButtonAdder.add(Button.builder(CommonComponents.GUI_CANCEL, button -> this.close())
                .size(100, 20).build());
        menuButtonAdder.add(Button.builder(Component.translatable("text.ebe.apply"), button -> this.applyChanges())
                .size(100, 20).build());
        menuButtonAdder.add(Button.builder(CommonComponents.GUI_DONE,
                button -> {
                    applyChanges();
                    close();
                })
                .size(100, 20).build());

        menuButtons.refreshPositions();
        menuButtons.setPosition((this.width - menuButtons.getWidth()) / 2, this.height - 27);
        menuButtons.forEachChild((child) -> {
            child.setNavigationOrder(1);
            this.addRenderableWidget(child);
        });
    }

    @Override
    protected void renderDarkening(GuiGraphics context) {
        renderDarkening(context, 0, 0, this.width, 34);
        renderDarkening(context, 0, this.height - 35, this.width, 35);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);

        context.drawCenteredString(this.font, this.title, (int)(this.width * 0.5), 8, 0xFFFFFF);
        context.drawCenteredString(this.font, HOLD_SHIFT, (int)(this.width * 0.5), 21, 0xFFFFFF);
    }

    @Override
    public void close() {
        this.minecraft.setScreen(parent);

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

        final var configView = new EBEOption.ConfigView(config, EnhancedBlockEntities.CONFIG.overrides);
        final var textRenderer = this.minecraft.font;

        optionsWidget.add(new SectionTextWidget(CHEST_OPTIONS_TITLE, textRenderer));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.RENDER_ENHANCED_CHESTS_KEY, BOOLEAN_OPTIONS, configView, false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.CHEST_AO_KEY, BOOLEAN_OPTIONS, configView, false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.EXPERIMENTAL_CHESTS_KEY, BOOLEAN_OPTIONS, configView, false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ), option(
                new EBEOption(EBEConfig.CHRISTMAS_CHESTS_KEY, ALLOWED_FORCED_DISABLED, configView, true, TextPalette.rainbow(0.35f), ReloadType.WORLD)
        ));

        optionsWidget.add(new SectionTextWidget(SIGN_OPTIONS_TITLE, textRenderer));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.RENDER_ENHANCED_SIGNS_KEY, BOOLEAN_OPTIONS, configView, false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.SIGN_TEXT_RENDERING_KEY, SIGN_TEXT_OPTIONS, configView, true, TextPalette.rainbow(0.45f), ReloadType.NONE)
        ));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.EXPERIMENTAL_SIGNS_KEY, BOOLEAN_OPTIONS, configView, false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ), option(
                new EBEOption(EBEConfig.SIGN_AO_KEY, BOOLEAN_OPTIONS, configView, false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));

        optionsWidget.add(new SectionTextWidget(BELL_OPTIONS_TITLE, textRenderer));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.RENDER_ENHANCED_BELLS_KEY, BOOLEAN_OPTIONS, configView, false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.BELL_AO_KEY, BOOLEAN_OPTIONS, configView, false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));

        optionsWidget.add(new SectionTextWidget(BED_OPTIONS_TITLE, textRenderer));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.RENDER_ENHANCED_BEDS_KEY, BOOLEAN_OPTIONS, configView, false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.EXPERIMENTAL_BEDS_KEY, BOOLEAN_OPTIONS, configView, false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ), option(
                new EBEOption(EBEConfig.BED_AO_KEY, BOOLEAN_OPTIONS, configView, false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));

        optionsWidget.add(new SectionTextWidget(SHULKER_BOX_OPTIONS_TITLE, textRenderer));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.RENDER_ENHANCED_SHULKER_BOXES_KEY, BOOLEAN_OPTIONS, configView, false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.SHULKER_BOX_AO_KEY, BOOLEAN_OPTIONS, configView, false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));

        optionsWidget.add(new SectionTextWidget(DECORATED_POT_OPTIONS_TITLE, textRenderer));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.RENDER_ENHANCED_DECORATED_POTS_KEY, BOOLEAN_OPTIONS, configView, false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.DECORATED_POT_AO_KEY, BOOLEAN_OPTIONS, configView, false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));

        optionsWidget.add(new SectionTextWidget(ADVANCED_TITLE, textRenderer));
        optionsWidget.add(option(
                new EBEOption(EBEConfig.FORCE_RESOURCE_PACK_COMPAT_KEY, BOOLEAN_OPTIONS, configView, false, TextPalette.ON_OFF, ReloadType.RESOURCES)
        ));
        optionsWidget.add(Button.builder(DUMP_LABEL, b -> {
            try {
                EBEUtil.dumpResources();
            } catch (IOException e) {
                EnhancedBlockEntities.LOG.error(e);
            }
        }).tooltip(Tooltip.of(dumpTooltip)).build());
    }

    private Button option(EBEOption option) {
        options.add(option);

        var button = Button.builder(option.getText(), b -> {
            option.next();
            b.setMessage(option.getText());
            b.setTooltip(option.getTooltip());
        }).tooltip(option.getTooltip()).build();

        if (option.override != null) {
            button.active = false;
        }

        return button;
    }
}
