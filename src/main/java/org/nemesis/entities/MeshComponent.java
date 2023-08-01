package org.nemesis.entities;

import org.nemesis.models.TexturedModel;

public class MeshComponent implements Updateable {
	public TexturedModel mesh;

	public MeshComponent ( TexturedModel mesh ) {
		this.mesh = mesh;
	}

	@Override
	public void update ( float deltaTime ) {

	}
}
