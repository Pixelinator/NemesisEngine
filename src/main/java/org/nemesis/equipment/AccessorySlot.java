package org.nemesis.equipment;

import org.nemesis.items.Accessory;
import org.nemesis.items.Item;

public class AccessorySlot extends EquipmentSlot{
	public AccessorySlot ( Item item ) {
		super( item );
	}

	@Override
	public void setItem ( Item item ) {
		if ( item instanceof Accessory ) {
			super.setItem( item );
		}
	}
}
