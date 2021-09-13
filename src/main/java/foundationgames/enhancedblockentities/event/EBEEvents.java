package foundationgames.enhancedblockentities.event;

import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;
import net.minecraft.client.render.model.ModelLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.profiler.Profiler;

public enum EBEEvents {;
    public static final Event<Reload> RELOAD_MODELS = EventFactory.createArrayBacked(Reload.class, (callbacks) -> (loader, manager, profiler) -> {
        for(Reload event : callbacks) {
            event.onReload(loader, manager, profiler);
        }
    });

    public interface Reload {
        void onReload(ModelLoader loader, ResourceManager manager, Profiler profiler);
    }
}
