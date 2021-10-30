package Tavi007.ElementalCombat.capabilities.attack;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import Tavi007.ElementalCombat.api.BasePropertiesAPI;
import Tavi007.ElementalCombat.config.ServerConfig;
import Tavi007.ElementalCombat.init.EnchantmentList;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class AttackData {

	private HashMap<ResourceLocation, AttackLayer> attackLayers = new HashMap<>();
	private boolean isInitialized = false;
	
	// for itemstack
	private boolean areEnchantmentChangesApplied = false;

	public AttackData() {
	}	
	
	public void set(AttackData data) {
		attackLayers = data.getLayers();
	}
	
	public String getElement() {
		for(ResourceLocation rl : attackLayers.keySet()) {
			AttackLayer layer = attackLayers.get(rl);
			String element = layer.getElement();
			if(!element.equals(ServerConfig.getDefaultElement())) {
				return element;
			}
		}
		return ServerConfig.getDefaultElement();
	}
	
	public String getStyle() {
		for(ResourceLocation rl : attackLayers.keySet()) {
			AttackLayer layer = attackLayers.get(rl);
			String style = layer.getStyle();
			if(!style.equals(ServerConfig.getDefaultStyle())) {
				return style;
			}
		}
		return ServerConfig.getDefaultStyle();
	}
	
	public AttackLayer toLayer() {
		return new AttackLayer(getStyle(), getElement());
	}
	
	public AttackLayer getLayer(ResourceLocation rl) {
		if(attackLayers.containsKey(rl)) {
			return attackLayers.get(rl);
		} else {
			return new AttackLayer();
		}
	}
	
	public void putLayer(ResourceLocation rl, AttackLayer layer) {
		if(layer.isDefault()) {
			attackLayers.remove(rl);
		} else {
			attackLayers.put(rl, layer);
		}
	}

	public boolean isDefault() {
		return getElement().equals(ServerConfig.getDefaultElement())
			&& getStyle().equals(ServerConfig.getDefaultStyle());
	}
	
	public void applyEnchantmentChanges(Map<Enchantment, Integer> currentEnchantments) {
		AttackLayer layer = new AttackLayer();
		for (Enchantment ench : currentEnchantments.keySet()) {
			if(ench.getName().equals(Enchantments.FIRE_ASPECT.getName())) {
				layer.setElement("fire");
				break;
			} else if(ench.getName().equals(EnchantmentList.ICE_ASPECT.get().getName())) {
				layer.setElement("ice");
				break;
			} else if(ench.getName().equals(EnchantmentList.WATER_ASPECT.get().getName())) {
				layer.setElement("water");
				break;
			} else if(ench.getName().equals(EnchantmentList.THUNDER_ASPECT.get().getName())) {
				layer.setElement("thunder");
				break;
			} else if(ench.getName().equals(EnchantmentList.DARKNESS_ASPECT.get().getName())) {
				layer.setElement("darkness");
				break;
			} else if(ench.getName().equals(EnchantmentList.LIGHT_ASPECT.get().getName())) {
				layer.setElement("light");
				break;
			} else if(ench.getName().equals(Enchantments.FLAME.getName())) {
				layer.setElement("fire");
				break;
			} else if(ench.getName().equals(Enchantments.CHANNELING.getName())) {
				layer.setElement("thunder");
				break;
			}
		}
		attackLayers.put(new ResourceLocation("enchantment"), layer);
		areEnchantmentChangesApplied = true;
	}
	
	public void initialize(ItemStack stack) {
		AttackLayer base = BasePropertiesAPI.getAttackData(stack);
		attackLayers.put(new ResourceLocation("base"), base);
		isInitialized = true;
	}
	
	public void initialize(LivingEntity entity) {
		AttackLayer base = BasePropertiesAPI.getAttackData(entity);
		attackLayers.put(new ResourceLocation("base"), base);
		isInitialized = true;
	}
	
	public void initialize(ProjectileEntity entity) {
		AttackLayer base = BasePropertiesAPI.getAttackData(entity);
		attackLayers.put(new ResourceLocation("base"), base);
		isInitialized = true;
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof AttackData) {
			AttackData other = (AttackData) object;
			Set<ResourceLocation> keysThis = this.attackLayers.keySet();
			Set<ResourceLocation> keysOther = other.attackLayers.keySet();
			if(keysThis.size() == keysOther.size()) {
				Iterator<ResourceLocation> iteratorThis = keysThis.iterator();
				Iterator<ResourceLocation> iteratorOther = keysOther.iterator();
				while(iteratorThis.hasNext()) {
					ResourceLocation keyThis = iteratorThis.next();
					ResourceLocation keyOther = iteratorOther.next();
					if(!keyThis.equals(keyOther)) {
						return false;
					}
					AttackLayer layerThis = this.attackLayers.get(keyThis);
					AttackLayer layerOther = other.attackLayers.get(keyOther);
					if(!layerThis.equals(layerOther)) {
						return false;
					}
				}
				return true;
			}
		}
		return false;
	}
	
	public boolean isInitialized() {return isInitialized;}
	public boolean areEnchantmentChangesApplied() {return areEnchantmentChangesApplied;}
	public HashMap<ResourceLocation, AttackLayer> getLayers() {return attackLayers;}
}
