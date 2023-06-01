Welcome to the **Elemental Combat** repository. 

A summary of the mod can be found on the [curseforge page](https://www.curseforge.com/minecraft/mc-mods/elemental-combat)

# Wiki 
Note: the wiki is still WIP.
## Datapacks
The directory structure:
	
	data
	 ↳ <mod_id>
	    ↳ combat_properties 
	       ↳ biomes
	       ↳ damage_sources
	       ↳ items
	       ↳ mobs
	       ↳ projectiles

### Items
The filename must be the equal to the name of the item. For example the item minecraft:apple will use the file with the name apple.json. This file will look like this
	
	{
		"attack_style": string,
		"attack_element": string,
		"defense_style": {
			"style1": int,
			"style1": int,
			...
		},
		"defense_element": {
			"element1": int,
			"element1": int,
			...
		}
	}
	
You don't have to specify all values. If they are missing in the file, the mod will fill them with the correct default values. 

### Mobs
Basically the same as the files for items. Again the file must be name accordingly. For example minecraft:pig -> pig.json. The structure is

	{
		"attack_style": string,
		"attack_element": string,
		"defense_style": {
			"style1": int,
			"style1": int,
			...
		},
		"defense_element": {
			"element1": int,
			"element1": int,
			...
		},
		"biome_dependency":boolean
	}

Note that it has the biome_dependency flag. When true, the mob will gain biome specific elemental defense properties.

### Biomes

	{	
		"defense_element":{
			"element1": int,
			...
		}
	}

a simple file containing all the values that will be added to a mob, if it has biome_dependency enabled.

### Projectiles

	{	
		"attack_style": "style1",
		"attack_element": "element1"
	}



### Damage Source
The structure is the same as for projectiles, becaus it does not need defense properties

	{	
		"attack_style": "style1",
		"attack_element": "element1"
	}

This is used as last resort, if the damage source is neither a mob nor a projectile. 

## Using the API

### Project Setup
add repository to the build.gradle file:

	repositories {
		maven {
			name = "CurseMaven"
			url "https://www.cursemaven.com"
	}

add jar as dependency:

	dependencies {
		compileOnly fg.deobf("curse.maven:elementalcombat-397825:{file-id of the api-version}")
		runtimeOnly fg.deobf("curse.maven:elementalcombat-397825:{file-id of the normal version}")
	}
		
You can find the file-id in the curseforge url:
	[curseforge.com/minecraft/mc-mods/elemental-combat/files/{file-id}](https://www.curseforge.com/minecraft/mc-mods/elemental-combat/files)

Now rerun gradlew with the '--refresh-dependency' option.
