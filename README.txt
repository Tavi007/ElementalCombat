Welcome to the Elemental Combat repository.

Goal of this mod is to enhance the combat system by adding elemental attack and defense properties to mobs and players. Upon hitting something, this properties will be checked and a new value will be calculated. 
While these properties are usually final for mobs, the player can choose his elemental properties by equipping armor and attacking with the right weapon.
New particles will tell you, how the enemy reacted to your attack.

The defense properties include 4 sets: Weakness, Resistance, Immunity, Absorb. Each can contain strings of element names (I choose sets, so no duplicated entries are possible).  
Weakness will double the damage, while 'Resistance' will half it. Immunity means no damage will be dealt, and absorb will even heal the target.
The attack property is a set of mappings, that maps element names to a value. Take a look at this example:
["fire":1, "ice":2]
This sword will deal 2/3 ice damage and 1/3 fire damage. 
On hit, a loop will go through the attack set and check if the key is in any of the defense sets. Highest priority has the absorb set. (then immunity, resistance and last weakness) If the key isn't included in any of these list, the default value will be used.
The value, which is mapped through the key, is used to scale the corresponding elemental damage. If the mob happens to absorb ice damage, but is weak to fire the new damage vaule will be computed like this:
scaleFire = 1/3
sclaeIce = 2/3
newDmg = oldDmg*scaleFire*2 - oldDmg*scaleIce
If it is still unclear, how the new damage value is calculated, then take a look at 
https://github.com/Tavi007/ElementalCombat/blob/master/src/main/java/Tavi007/ElementalCombat/events/ElementifyLivingHurtEvent.java
All in all I'm open to discuss other method to compute a new damage value.
There is also a 'hidden' element called 'natural', which means, that the attack list is actually empty. You can use this element to make mob resistance/immune etc to non elemental damage.

This mod also provides an API for other mods to interact with. Add special behaviour to your own mobs like aura buffs and debuffs or changing attack/defense properties midfight.
I'm planing to support other peoples mods directly by adding default values for theire mobs just like I did with vanilla mobs. 

These default values can also be set through the help of datapacks. Take a look at 
https://github.com/Tavi007/ElementalCombat/tree/master/src/main/resources/data/minecraft/elementalcombat/elementalproperties/entities
for some examples. This way, everyone can change the values to their heart's contents.

It's also planned to add default values for items the same way (minus the biomeDependency), but that's still WIP.


I hope, I can polish this mod, so it can be used as a base mod like baubles. I also hope, that other mods take interest in this mod and use the provided API to change some of their mob behaviour.

Best regards,
Tavi007

PS: This is first time modding minecraft. So some stuff might be solved in a suboptimal fashion :-D
