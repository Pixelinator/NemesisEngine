package org.nemesis.equipment;

import org.nemesis.items.Armor;
import org.nemesis.items.Item;

public class ArmorSlot extends EquipmentSlot{
	public ArmorSlot ( Item item ) {
		super( item );
	}

	@Override
	public void setItem ( Item item ) {
		if ( item instanceof Armor ) {
			super.setItem( item );
		}
	}
}
