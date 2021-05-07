package foundationgames.enhancedblockentities.config.gui;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import foundationgames.enhancedblockentities.config.gui.screen.EBEConfigScreen;

public class EBEModMenuPlugin implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return EBEConfigScreen::new;
    }
}
