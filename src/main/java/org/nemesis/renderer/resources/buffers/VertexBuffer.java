package org.nemesis.renderer.resources.buffers;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class VertexBuffer {
	private final int vboId;
	private final int vertexCount;

	public VertexBuffer(float[] vertices) {
		FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(vertices.length);
		vertexBuffer.put(vertices).flip();

		vboId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertexBuffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		vertexCount = vertices.length / 3; // Assuming 3 components per vertex (x, y, z)
	}

	public VertexBuffer(int[] vertices) {
		IntBuffer indexBuffer = BufferUtils.createIntBuffer(vertices.length);
		indexBuffer.put(vertices).flip();

		vboId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, indexBuffer, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);

		vertexCount = vertices.length / 3; // Assuming 3 components per vertex (x, y, z)
	}

	public void bind() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
	}

	public void unbind() {
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
	}

	public void delete() {
		GL15.glDeleteBuffers(vboId);
	}

	public void setVertexAttribPointer(int attribute, int dimensions, int stride, int offset) {
		GL20.glVertexAttribPointer(attribute, dimensions, GL11.GL_FLOAT, false, stride, offset);
		GL20.glEnableVertexAttribArray(attribute);
	}

	public int getVertexCount() {
		return vertexCount;
	}
}
