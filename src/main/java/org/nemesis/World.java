package org.nemesis;

import org.nemesis.entities.Entity;
import org.nemesis.systems.System;

import java.util.ArrayList;
import java.util.List;

public class World {
	private List<Entity> entities;
	private List<System> systems;

	// Collision and Physics are resolved here
	// PhysicComponent
	// GraphicComponent
	// InputComponent

	public World() {
		this.entities = new ArrayList<>();
		this.systems = new ArrayList<>();
	}

	public void addEntity(Entity entity) {
		entities.add(entity);
	}

	public void addSystem(System system) {
		systems.add(system);
	}

	public List<Entity> getEntities() {
		return entities;
	}

	public void update(float deltaTime) {
		for (System system : systems) {
			system.update(deltaTime);
		}
	}
}
