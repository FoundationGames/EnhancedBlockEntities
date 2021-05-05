package foundationgames.enhancedblockentities.config.gui.screen;

import com.google.common.collect.ImmutableList;
import foundationgames.enhancedblockentities.EnhancedBlockEntities;
import foundationgames.enhancedblockentities.config.EBEConfig;
import foundationgames.enhancedblockentities.config.gui.element.EBEOptionListWidget;
import foundationgames.enhancedblockentities.config.gui.option.EBEOption;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;

import java.util.Properties;

public class EBEConfigScreen extends Screen {
    private EBEOptionListWidget options;
    private final Screen parent;

    private final ImmutableList<String> BOOLEAN_OPTIONS = ImmutableList.of("true", "false");
    private final ImmutableList<String> ALLOWED_FORCED_DISABLED = ImmutableList.of("allowed", "forced", "disabled");

    public EBEConfigScreen(Screen screen) {
        super(new TranslatableText("screen.ebe.config"));
        parent = screen;
    }

    @Override
    protected void init() {
        super.init();
        int bottomCenter = this.width / 2 - 50;
        boolean inWorld = this.client.world != null;

        this.options = new EBEOptionListWidget(this.client, this.width, this.height, 26, this.height - 35, 22);
        if (inWorld) {
            this.options.method_31322(false);
        }
        addOptions();
        this.children.add(options);

        this.addButton(new ButtonWidget(bottomCenter + 104, this.height - 27, 100, 20, ScreenTexts.DONE, button -> {
            applyChanges();
            onClose();
        }));
        this.addButton(new ButtonWidget(bottomCenter, this.height - 27, 100, 20, new TranslatableText("text.ebe.apply"), button -> this.applyChanges()));
        this.addButton(new ButtonWidget(bottomCenter - 104, this.height - 27, 100, 20, ScreenTexts.CANCEL, button -> this.onClose()));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        if (this.client.world == null) {
            this.renderBackground(matrices);
        } else {
            this.fillGradient(matrices, 0, 0, width, height, 0x4F232323, 0x4F232323);
        }

        this.options.render(matrices, mouseX, mouseY, delta);

        drawCenteredText(matrices, this.textRenderer, this.title, (int)(this.width * 0.5), 8, 0xFFFFFF);

        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void onClose() {
        this.client.openScreen(parent);
    }

    public void applyChanges() {
        EBEConfig config = EnhancedBlockEntities.CONFIG;
        Properties properties = new Properties();
        options.forEach(option -> properties.setProperty(option.key, option.getValue()));
        config.readFrom(properties);
        config.save();
        EnhancedBlockEntities.reload();
    }

    public void addOptions() {
        Properties config = new Properties();
        EnhancedBlockEntities.CONFIG.writeTo(config);
        options.add(
                new EBEOption("render_enhanced_chests", BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty("render_enhanced_chests")), false),
                new EBEOption("render_enhanced_signs", BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty("render_enhanced_signs")), false),
                new EBEOption("use_ao", BOOLEAN_OPTIONS, BOOLEAN_OPTIONS.indexOf(config.getProperty("use_ao")), false),
                new EBEOption("christmas_chests", ALLOWED_FORCED_DISABLED, ALLOWED_FORCED_DISABLED.indexOf(config.getProperty("christmas_chests")), true)
        );
    }
}
