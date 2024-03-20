package org.nemesis.renderer.resources.buffers;

import org.lwjgl.opengl.GL30;

public class VertexArray {
	private final int vaoId;
	private int vertexCount;

	public VertexArray() {
		vaoId = GL30.glGenVertexArrays();
	}

	public void bind() {
		GL30.glBindVertexArray(vaoId);
	}

	public void unbind() {
		GL30.glBindVertexArray(0);
	}

	public void delete() {
		GL30.glDeleteVertexArrays(vaoId);
	}

	public void setVertexCount(int vertexCount) {
		this.vertexCount = vertexCount;
	}

	public int getVaoId() {
		return vaoId;
	}

	public int getVertexCount() {
		return vertexCount;
	}
}
