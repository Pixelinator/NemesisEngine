package org.nemesis.entities;

import org.nemesis.components.Component;
import org.nemesis.systems.System;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Entity {

	private final int id;
	private final Map<Class<?>, Component> components;


	public Entity ( int id ) {
		this.id = id;
		components = new HashMap<>();
	}

	public int getId () {
		return id;
	}

	public <T extends Component> void addComponent(T component) {
		components.put(component.getClass(), component);
	}

	public <T extends Component> T getComponent(Class<T> componentClass) {
		return componentClass.cast(components.get(componentClass));
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
		for( System component : getComponents( System.class )) {
			component.update(deltaTime);
		}
	}
}
