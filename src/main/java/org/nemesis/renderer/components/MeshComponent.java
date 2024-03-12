package org.nemesis.renderer.components;

import org.nemesis.renderer.lowRenderer.Vertex;
import org.nemesis.renderer.resources.Texture;

import java.util.List;

public class MeshComponent {
	private List<Vertex> vertices;
	private List<Integer> indices;
	private List<Texture> textures;
	private int vao;
	private int vbo;
	private int ebo;
}
