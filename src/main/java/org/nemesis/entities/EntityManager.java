package org.nemesis.entities;

import java.util.HashMap;
import java.util.Map;

public class EntityManager {

	private int nextEntityId;
	private final Map<Integer, Entity> entities;

	public EntityManager() {
		nextEntityId = 1;
		entities = new HashMap<>();
	}

	public Entity createEntity() {
		int entityId = nextEntityId++;
		Entity entity = new Entity(entityId);
		entities.put(entityId, entity);
		return entity;
	}

	public Entity getEntity(int entityId) {
		return entities.get(entityId);
	}

	public void removeEntity(int entityId) {
		entities.remove(entityId);
	}

	public void updateAllEntities(float deltaTime) {
		for (Entity entity : entities.values()) {
			entity.update(deltaTime);
		}
	}

}
