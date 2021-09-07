## Enhanced Block Entities

EBE is a **100% client side** Minecraft mod for the **[Fabric](https://fabricmc.net/use/)** mod loader which aims to increase the performance of block entity rendering, as well as offer customizability via resource packs. <br/><br/>
**How does it work?** EBE Makes some block entities use baked block models rather than laggy entity models. <br/><br/>
**Is it just an optimization mod?** No, it also allows you to customise more block entities with resource packs and makes their lighting smoother. This mod also allows for toggling christmas chests among other nice improvements.<br/><br/>
**What about animations?** The best part about EBE is that you still get to keep animations, while gaining the performance boost of baked models! Most animated block entity models will only render when absolutely necessary. <br/><br/>
**Can I use it with Sodium?** Yes you can, but it requires you to install the [Indium mod](https://modrinth.com/mod/indium) as well. <br/><br/>
**What block entities does it support?**
- Chests
- Ender Chests
- Signs
- Bells
- Beds
<br/><br/>

Requires [Fabric API](https://modrinth.com/mod/fabric-api) <br/><br/>

## FAQ and Help

**Q: How do I download the mod?**<br/>
**A:** For stable releases, you can check out the [Modrinth Page](https://modrinth.com/mod/OVuFYfre). If you want the newest bleeding edge build, you can use GitHub Actions (or alternatively, you can build yourself).<br/><br/>

**Q: I need help with the mod/need to report a bug!** <br/>
**A:** If you're having trouble setting up the mod or using it alongside other mods, I'd recommend you join our [Discord Server](https://discord.gg/7Aw3y4RtY9) and ask for help there. *If the issue is a BUG* please report it on our issue tracker ("Issues" tab at the top of the page)<br/><br/>

**Q: My chests are invisible!** <br/>
**A:** You're using Sodium, which doesn't support certain Fabric Rendering features by default. If you want to use Sodium with EBE, you should Install the indium mod. <br/><br/>

**Q: How is this different from [FastChest](https://www.curseforge.com/minecraft/mc-mods/fastchest)?** <br/>
**A:** FastChest does not preserve chest animations, and only optimizes chests (in a similar way to EBE). EBE also optimizes other block entities, e.g. bells and signs. <br/><br/>

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
