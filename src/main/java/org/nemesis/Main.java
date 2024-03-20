package org.nemesis;

import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.nemesis.renderer.entities.MeshGameObject;
import org.nemesis.renderer.lowRenderer.Vertex;
import org.nemesis.renderer.resources.Mesh;
import org.nemesis.renderer.resources.buffers.VertexArray;
import org.nemesis.renderer.resources.buffers.VertexBuffer;
import org.nemesis.renderer.systems.RenderSystem;
import org.nemesis.renderer.systems.pipelines.DefaultRenderPipeline;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class Main {

	public static void main ( String[] args ) {
		DisplayManager.createDisplay();
		RenderSystem renderSystem = getRenderSystem();
		while ( !glfwWindowShouldClose( DisplayManager.window ) ) {
			// #12212B
			GL11.glClearColor( 0.07f, 0.13f, 0.17f, 0.0f );
			GL11.glClear( GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT );
			renderSystem.render();
			DisplayManager.updateDisplay();
		}
		DisplayManager.closeDisplay();
	}

	private static RenderSystem getRenderSystem () {
		DefaultRenderPipeline defaultRenderPipeline = new DefaultRenderPipeline();
		// TODO refactor

		// create a vertex buffer from this
		float[] vertices = new float[]{
				0.0f, 0.5f, 0.0f,
				-0.5f, -0.5f, 0.0f,
				0.5f, -0.5f, 0.0f
		};

		int[] indices = {
				0, 1, 2
		};

		// Create a Mesh
		Mesh mesh = new Mesh(vertices, indices);

		MeshGameObject meshGameObject = new MeshGameObject(mesh, new Vector3f( 0.0f, 0.0f, 0.0f ), 1, new Vector3f( 0.0f, 0.0f, 0.0f ));
		// TODO end refactor

		RenderSystem renderSystem = new RenderSystem();
		renderSystem.addPipeline( defaultRenderPipeline );
		renderSystem.addRenderGameObject( meshGameObject );
		renderSystem.init();
		return renderSystem;
	}
}