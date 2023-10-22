package org.nemesis.entities;

import org.nemesis.equipment.AccessorySlot;
import org.nemesis.equipment.ArmorSlot;
import org.nemesis.equipment.WeaponSlot;
import org.nemesis.items.Item;
import org.nemesis.models.TexturedModel;

import java.util.ArrayList;
import java.util.List;

public class Character {
	private String name;
	private int health;
	private int strength;
	private List<Item> inventory;
	private List<WeaponSlot> weaponSlots;
	private List<ArmorSlot> armorSlots;
	private List<AccessorySlot> accessorySlots;
	private TexturedModel currentModel;

	public Character ( String name, int health, int strength ) {
		this.name = name;
		this.health = health;
		this.strength = strength;
		this.inventory = new ArrayList<>();
		this.weaponSlots = new ArrayList<>();
		this.armorSlots = new ArrayList<>();
		this.accessorySlots = new ArrayList<>();
	}

	public void equipItem ( Item item, int slotIndex, String slotType ) {
		if ( inventory.contains( item ) ) {
			inventory.remove( item );
			switch ( slotType ) {
				case "weapon":
					weaponSlots.get( slotIndex ).setItem( item );
					updateModel();
					break;
				case "armor":
					armorSlots.get( slotIndex ).setItem( item );
					updateModel();
					break;
				case "accessory":
					accessorySlots.get( slotIndex ).setItem( item );
					updateModel();
					break;
			}
		}
	}

	public void unequipItem ( int slotIndex, String slotType ) {
		Item item;
		switch ( slotType ) {
			case "weapon":
				item = weaponSlots.get( slotIndex ).getItem();
				weaponSlots.get( slotIndex ).setItem( null );
				updateModel();
				break;
			case "armor":
				item = armorSlots.get( slotIndex ).getItem();
				armorSlots.get( slotIndex ).setItem( null );
				updateModel();
				break;
			case "accessory":
				item = accessorySlots.get( slotIndex ).getItem();
				accessorySlots.get( slotIndex ).setItem( null );
				updateModel();
				break;
			default:
				return;
		}
		if ( item != null ) {
			inventory.add( item );
		}
	}

	private void updateModel () {
		// Create a new model based on the currently equipped items
//		Model newModel = new BaseModel();
		for ( WeaponSlot slot : weaponSlots ) {
			Item item = slot.getItem();
			if ( item != null ) {
//				newModel = newModel.add(item.getModel());
			}
		}
		for ( ArmorSlot slot : armorSlots ) {
			Item item = slot.getItem();
			if ( item != null ) {
//				newModel = newModel.add(item.getModel());
			}
		}
		for ( AccessorySlot slot : accessorySlots ) {
			Item item = slot.getItem();
			if ( item != null ) {
//				newModel = newModel.add(item.getModel());
			}
		}
//		this.currentModel = newModel;
	}

	public String getName () {
		return name;
	}

	public int getHealth () {
		return health;
	}

	public int getStrength () {
		return strength;
	}

	public List<Item> getInventory () {
		return inventory;
	}

	public List<WeaponSlot> getWeaponSlots () {
		return weaponSlots;
	}

	public List<ArmorSlot> getArmorSlots () {
		return armorSlots;
	}

	public List<AccessorySlot> getAccessorySlots () {
		return accessorySlots;
	}

	public TexturedModel getCurrentModel () {
		return currentModel;
	}
}
