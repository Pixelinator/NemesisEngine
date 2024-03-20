package org.nemesis.renderer.resources;

import org.lwjgl.opengl.GL30;
import org.nemesis.renderer.resources.buffers.VertexArray;
import org.nemesis.renderer.resources.buffers.VertexBuffer;

public class Mesh {
	private final VertexArray vao;
	private final VertexBuffer vbo;
	private final VertexBuffer idxVbo;
	private final int vertexCount;

	public Mesh(float[] positions, int[] indices) {
		vertexCount = indices.length;

		// Create VAO
		vao = new VertexArray();
		vao.bind();

		// Create VBO for positions
		vbo = new VertexBuffer(positions);
		vbo.bind();
		vbo.setVertexAttribPointer(0, 3, 3 * Float.BYTES, 0);

		// Create VBO for indices
		idxVbo = new VertexBuffer(indices);
		idxVbo.bind();

		GL30.glBindVertexArray(0);
	}

	public VertexArray getVao () {
		return vao;
	}

	public void cleanUp() {
		vao.delete();
		vbo.delete();
		idxVbo.delete();
	}
}
