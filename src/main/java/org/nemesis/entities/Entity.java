package org.nemesis.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entity {

	private final int id;
	private final Map<Class<?>, Object> components;


	public Entity ( int id ) {
		this.id = id;
		components = new HashMap<>();
	}

	public int getId () {
		return id;
	}

	public void addComponent(Object component) {
		components.put( component.getClass(), component );
	}

	public <T> T getComponent(Class<T> componentClass) {
		return componentClass.cast( components.get( componentClass ) );
	}

	public <T> List<T> getComponents( Class<T> componentClass ) {
		List<T> result = new ArrayList<>();
		for ( Object component : components.values() ) {
			if(componentClass.isInstance( component )) {
				result.add( componentClass.cast( component ) );
			}
		}
		return result;
	}

	public void update(float deltaTime) {
		for(Updateable component : getComponents( Updateable.class )) {
			component.update(deltaTime);
		}
	}
}
