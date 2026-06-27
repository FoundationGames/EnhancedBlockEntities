## ⚠️ Archival Notice (June 2026)

> **This mod is likely no longer needed as of Minecraft Java Edition 26.2 (internal: 1.21.11).**
>
> Mojang completely overhauled the rendering pipeline in 26.x. The new architecture already addresses the core problems EBE was solving — see details below.
>
> **We propose this repository be archived.** If you disagree or have evidence the performance/visual gaps still exist in 26.x vanilla, please open an issue before archival.

---

### What happened to MC 26.2 support?

A port to MC 26.2 was attempted in [PR #321](https://github.com/FoundationGames/EnhancedBlockEntities/pull/321). After ~8 hours of work, it became clear that a 1:1 port is not possible — the rendering system EBE is built on has been replaced:

| EBE relied on | Removed/replaced in MC 26.x |
|---|---|
| `BakedModel` interface | Replaced by `BlockStateModel` (`net.minecraft.client.renderer.block.dispatch`) |
| `BlockEntityRenderDispatcher.render(BlockEntityRenderer, BlockEntity, ...)` | Two-phase render: `BlockEntity` → `BlockEntityRenderState` → pixels |
| `FabricBakedModelManager.getModel(Identifier)` | `FabricModelManager.getModel(ExtraModelKey<T>)` — typed key-based API |
| `BlockRenderLayerMap` (Fabric API) | Removed entirely from Fabric API for 26.x |
| `VertexConsumerProvider` / `MultiBufferSource` | Replaced by `SubmitNodeCollector` and new pipeline |

Mojang's new **Entity Render State** system separates state extraction (game thread) from rendering (render thread), which provides the same caching and parallelism benefits EBE was delivering as a workaround. The new `BlockStateModel` pipeline already gets proper ambient occlusion and chunk-integrated lighting.

The one remaining gap worth investigating: whether block entity render states get equivalent AO/lighting quality compared to static chunk geometry. If that gap exists in 26.x vanilla, a slimmer mod focused only on lighting quality (not full static baking) could still have value. If you want to explore this, check [PR #321](https://github.com/FoundationGames/EnhancedBlockEntities/pull/321) for a starting point.

---

## Enhanced Block Entities

EBE is a **100% client side** Minecraft mod for the **[Fabric](https://fabricmc.net/use/)** mod loader which aims to increase the performance of block entity rendering, as well as offer customizability via resource packs. <br/><br/>
**How does it work?** EBE Makes some block entities use baked block models rather than laggy entity models. <br/><br/>
**Is it just an optimization mod?** EBE isn't *just* an optimization mod, some side effects of its optimizations are many visual improvements. <br/>
These may include:
- Smooth lighting on block entities
- Being able to remodel block entities with block models
- Toggling features like christmas chests
- Being able to see block entities from as far away as possible
<br/><br/>

**What about animations?** The best part about EBE is that you still get to keep animations, while gaining the performance boost of baked models! Most animated block entity models will only render when absolutely necessary. <br/><br/>
**Can I use it with Sodium?** Yes.<br/><br/> 
- EBE 0.10.2 and above are **fully compatible with Sodium 0.6+**
- Earlier EBE versions require installing [Indium](https://modrinth.com/mod/indium) along with Sodium 0.5.11 or below.

## Downloading the mod

For stable releases, you can check out the [CurseForge](https://www.curseforge.com/minecraft/mc-mods/enhanced-block-entities) or [Modrinth](https://modrinth.com/mod/OVuFYfre) page. If you want the newest bleeding edge build, you can use GitHub Actions (or alternatively, you can build yourself). This mod requires [Fabric API](https://modrinth.com/mod/fabric-api) <br/><br/>

## FAQ and Help

**Q: I need help with the mod/need to report a bug!** <br/>
**A:** If you're having trouble setting up the mod or using it alongside other mods, I'd recommend you join our [Discord Server](https://discord.gg/7Aw3y4RtY9) and ask for help there. *If the issue is a BUG* please report it on our issue tracker ("Issues" tab at the top of the page)<br/><br/>

**Q: Does this mod glitch the chest animation or turn chests invisible?**
**A:** This bug has been completely eradicated in EBE versions 0.5 and above. If the issue still persists (it shouldn't), leave an issue on GitHub or join the [Discord Server](https://discord.gg/7Aw3y4RtY9). The chest lid may flash when using with Sodium. <br/><br/>

**Q: My chests are still invisible!** <br/>
**A:** You're likely using a Sodium version lower than 0.4, which doesn't support certain Fabric Rendering features by default. If you need to use a Sodium version lower than 0.4 with EBE, you should install [Indium](https://modrinth.com/mod/indium). <br/><br/>
<br/><br/>

## FPS Boost
Rendering 1700 chests:
### Vanilla
![Before](https://github.com/FoundationGames/EnhancedBlockEntities/raw/116_indev/img/before.png)
### With EBE
![After](https://github.com/FoundationGames/EnhancedBlockEntities/raw/116_indev/img/after.png) <br/>
A 155% frame rate increase!

## Is your mod incompatible with EBE?
If you are the developer of a mod that makes changes to block entity rendering, your mod will be broken by EBE. Fortunately, EBE provides an API that allows you to force-disable its features, allowing your mod to function instead.
<br/>
**You don't need to add EBE as a dependency in your development environment either!**

### Add the Entrypoint
`fabric.mod.json`:
```json
{
    "entrypoints": {
        "main": [...],
        "client": [...],
        "ebe_v1": [
            "my.mod.compat.EBECompatibility"
        ]
    }
}
```

### Need to modify config values? Implement `BiConsumer<Properties, Map<String, Text>>`
`my.mod.compat.EBECompatibility`:
```java
public class EBECompatibility implements BiConsumer<Properties, Map<String, Text>>, ... {
    @Override
    public void accept(Properties overrideConfigValues, Map<String, Text> overrideReasons) {
        overrideConfigValues.setProperty("render_enhanced_chests", "false");

        overrideReasons.put("render_enhanced_chests",
                Text.literal("EBE Enhanced Chests are not compatible with my mod!")
                        .formatted(Formatting.YELLOW));
    }
    
    ...
}
```
The `accept(Properties, Map<String, Text>)` function is called when EBE loads config values. You can override a desired config value by setting the corresponding property of `overrideConfigValues`. This will also gray out the option in the config menu.
<br/>
To explain to users why your mod made that change, you can add a text component to the `overrideReasons` map corresponding to the key of the option you changed.
<br/>
`Text` is `net.minecraft.text.Text` when using Yarn mappings.

### Need to manually reload EBE? Implement `Consumer<Runnable>`
`my.mod.compat.EBECompatibility`:
```java
public class EBECompatibility implements Consumer<Runnable>, ... {
    private static Runnable ebeReloader = () -> {};
    
    ...

    @Override
    public void accept(Runnable ebeConfigReloader) {
        ebeReloader = ebeConfigReloader;
    }
}
```
If your mod needs to modify EBE's config values depending on loaded resources, it may encounter load order problems. This can be somewhat fixed by manually reloading EBE.
<br/>
The `accept(Runnable)` function is called when EBE is first loaded. The `Runnable` executes `EnhancedBlockEntities.load()`. Store this in a field so you can execute it whenever necessary.
```java
void onMyModResourceReload() {
    EBECompatibility.someParameter = true;
    EBECompatibility.ebeReloader.run();
    // Your config modification handler in EBECompatibility can change 
    // its behavior based on EBECompatibility.someParameter.
}
```