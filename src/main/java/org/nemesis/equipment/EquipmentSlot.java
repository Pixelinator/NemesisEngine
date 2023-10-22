package org.nemesis.equipment;

import org.nemesis.items.Item;

public class EquipmentSlot {
	private Item item;

	public EquipmentSlot ( Item item ) {
		this.item = item;
	}

	public Item getItem () {
		return item;
	}

	public void setItem ( Item item ) {
		this.item = item;
	}
}
