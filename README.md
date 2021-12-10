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
**Can I use it with Sodium?** Yes you can however, you are **required** to install [Indium](https://modrinth.com/mod/indium) as well. <br/><br/> 

## Downloading the mod

For stable releases, you can check out the [CurseForge](https://www.curseforge.com/minecraft/mc-mods/enhanced-block-entities) or [Modrinth](https://modrinth.com/mod/OVuFYfre) page. If you want the newest bleeding edge build, you can use GitHub Actions (or alternatively, you can build yourself). This mod requires [Fabric API](https://modrinth.com/mod/fabric-api) <br/><br/>

## FAQ and Help

**Q: I need help with the mod/need to report a bug!** <br/>
**A:** If you're having trouble setting up the mod or using it alongside other mods, I'd recommend you join our [Discord Server](https://discord.gg/7Aw3y4RtY9) and ask for help there. *If the issue is a BUG* please report it on our issue tracker ("Issues" tab at the top of the page)<br/><br/>

**Q: Does this mod glitch the chest animation or turn chests invisible?**
**A:** This bug has been completely eradicated in EBE versions 0.5 and above. If the issue still persists (it shouldn't), leave an issue on GitHub or join the [Discord Server](https://discord.gg/7Aw3y4RtY9). <br/><br/>

**Q: My chests are still invisible!** <br/>
**A:** You're likely using a Sodium version lower than 0.4, which doesn't support certain Fabric Rendering features by default. If you need to use a Sodium version lower than 0.4 with EBE, you should install [Indium](https://modrinth.com/mod/indium). <br/><br/>

**Q: How is this different from [FastChest](https://www.curseforge.com/minecraft/mc-mods/fastchest)?** <br/>
**A:** FastChest does not preserve chest animations, and only optimizes chests (in a similar way to EBE). EBE also optimizes other block entities:
- Ender Chests
- Signs
- Bells
- Beds
- Shulker Boxes
<br/><br/>

## FPS Boost
Rendering 1700 chests:
### Vanilla
![Before](https://github.com/FoundationGames/EnhancedBlockEntities/raw/116_indev/img/before.png)
### With EBE
![After](https://github.com/FoundationGames/EnhancedBlockEntities/raw/116_indev/img/after.png) <br/>
A 155% frame rate increase!

## Resource Packs
Here's an example of how you can customize chests with resource packs using EBE. <br/><br/>
![Custom Chest GIF](https://user-images.githubusercontent.com/55095883/112942134-f67fe780-912f-11eb-8b11-cf316544c22b.gif)
