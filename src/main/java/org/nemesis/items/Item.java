package org.nemesis.items;

public class Item {
	private String name;
	private String description;
	private int level;

	public Item ( String name, String description, int level ) {
		this.name = name;
		this.description = description;
		this.level = level;
	}

	public String getName () {
		return name;
	}

	public String getDescription () {
		return description;
	}

	public int getLevel () {
		return level;
	}
}
