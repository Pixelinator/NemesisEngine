package org.nemesis.components;

import org.nemesis.models.TexturedModel;

public class MeshComponent implements Component {
	public TexturedModel mesh;

	public MeshComponent ( TexturedModel mesh ) {
		this.mesh = mesh;
	}
}
