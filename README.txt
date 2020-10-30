Welcome to the Elemental Combat repository.

Goal of this mod is to enhance the combat system by adding elemental and style properties to combat. This means, that players, mobs and items all have new attack and defense properties. Upon hitting something, these properties will be checked and a new damage value will be calculated.

I made this mod, because I noticed, that in a lot of modpacks you end up using the same weapon. With this mod I hope there is an incentiv to switch your weapon and armor depending on the monster you are about to fight.

API:
I also designed this mod to be used as an API for other mods by exposing easy to use functions for other devs. First of you can easily interact with the properties and change them as you want (for example changing attack/defense properties of an enemy midfight). You can also add special behaviour to your own mobs like creating aura buffs and debuffs, tho this is still WIP.

DataPacks:
Another method to interact with these properties are datapacks, because those defines the default values for any items and mobs, even non vanilla ones. I will provide datapacks, for other well known mods, which you can then use from the get go. If you are creating a modpack and you want the datapack to be loaded globally, then you can use other mods, that do just that. One example would be Global Data- & Resourcepacks.

Damage Calculation (technical stuff):
See here: https://github.com/Tavi007/ElementalCombat/blob/bc6f6654d808bd804aa7b8a3c2e2f544da3d92ae/src/main/java/Tavi007/ElementalCombat/events/ServerEvents.java#L85

Enchantments:
I added a few enchantments, so the player can change armor and weapon properties from the get go. For example the vanilla Fire Resistance now uses this new system, which means the player can now have more then the 80% damage reduction from vanilla minecraft.

Configs:
A few things are controlled by client and server configs. The client configs control the new HUD-element (which also can be toggled on/off with the J-key), while server config changes some gameplay aspects like the default style and the default element. Also the server configs contain the value maxFactor (see spoiler above) and level-scaling for enchantments (basically the effectiveness of an enchantment). The default values for both maxFactor and enchantmentScaling are the same as the vanilla Enchantment Protection Factor-System, so my system can mimic it. Lastly you can toggle, if the enchantment should use strings such like "fire" or "water", or if they should use emojies (❄, 🔥, 💧 and so on). Sadly not every emoji is supported by the minecraft font yet, so I would advice against turning emojies on. If you do however want emoji turned on, you should also use the provided emoji-datapack. Otherwise the mod will be useless.

So please tell me, what you think of this mod and how I could make it better.

Best regards, Tavi007.

 

PS: I do not plan on backporting it. Mostly because 1.16 introduced the ProjectileEntity-class, which is a parent class for ArrowProjectile/FireBallProjectile and so. In 1.15 and earlier this didn't exist and I would have to rewrite quite a lot of code.