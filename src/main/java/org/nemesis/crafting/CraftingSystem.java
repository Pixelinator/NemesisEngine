package org.nemesis.crafting;

import org.nemesis.entities.Character;
import org.nemesis.items.Item;

import java.util.Map;

public class CraftingSystem {
	public void craft ( CraftingRecipe recipe, Character character ) {
		// Check if the player has all the required items
		if ( canCraft( recipe, character ) ) {
			// If so, remove the items from the inventory and add the result item

		}
	}

	public boolean canCraft ( CraftingRecipe recipe, Character character ) {
		for ( Map.Entry<Item, Integer> entry : recipe.getRequiredItems().entrySet() ) {
			Item requiredItem = entry.getKey();
			int requiredQuantity = entry.getValue();
			int playerQuantity = 0;

			for ( Item inventoryItem : character.getInventory() ) {
				if ( inventoryItem.equals( requiredItem ) ) {
					playerQuantity++;
				}
			}

			if ( playerQuantity < requiredQuantity ) {
				return false;
			}
		}
		return true;
	}
}
