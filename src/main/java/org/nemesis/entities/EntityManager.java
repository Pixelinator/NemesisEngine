package org.nemesis.entities;

import org.nemesis.components.MeshComponent;
import org.nemesis.components.TransformComponent;

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

	public Player createPlayer( TransformComponent transformComponent, MeshComponent meshComponent) {
		Entity entity = this.createEntity();
		return new Player( entity.getId(), transformComponent, meshComponent );
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
