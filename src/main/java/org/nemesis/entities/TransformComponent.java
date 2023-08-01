package org.nemesis.entities;

import org.joml.Vector3f;

public class TransformComponent implements Updateable {

	public Vector3f position;
	public Vector3f rotation;
	public Vector3f scale;

	public TransformComponent ( Vector3f position, Vector3f rotation, Vector3f scale ) {
		this.position = position;
		this.rotation = rotation;
		this.scale = scale;
	}

	@Override
	public void update ( float deltaTime ) {

	}
}
