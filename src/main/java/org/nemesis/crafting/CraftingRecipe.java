package org.nemesis.crafting;

import org.nemesis.items.Item;

import java.util.Map;

public class CraftingRecipe {
	private Map<Item, Integer> requiredItems;
	private Item resultItem;

	public CraftingRecipe ( Map<Item, Integer> requiredItems, Item resultItem ) {
		this.requiredItems = requiredItems;
		this.resultItem = resultItem;
	}

	public Map<Item, Integer> getRequiredItems () {
		return requiredItems;
	}

	public void setRequiredItems ( Map<Item, Integer> requiredItems ) {
		this.requiredItems = requiredItems;
	}

	public Item getResultItem () {
		return resultItem;
	}

	public void setResultItem ( Item resultItem ) {
		this.resultItem = resultItem;
	}
}
