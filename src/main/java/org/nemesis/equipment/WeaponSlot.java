package org.nemesis.equipment;

import org.nemesis.items.Item;
import org.nemesis.items.Weapon;

public class WeaponSlot extends EquipmentSlot{

	public WeaponSlot ( Item item ) {
		super( item );
	}

	@Override
	public void setItem ( Item item ) {
		if ( item instanceof Weapon ) {
			super.setItem( item );
		}
	}
}
