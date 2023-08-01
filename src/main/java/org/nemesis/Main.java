package org.nemesis;

import org.joml.Vector3f;
import org.nemesis.entities.*;
import org.nemesis.models.RawModel;
import org.nemesis.models.TexturedModel;
import org.nemesis.renderEngine.DisplayManager;
import org.nemesis.renderEngine.Loader;
import org.nemesis.renderEngine.OBJLoader;
import org.nemesis.renderEngine.Renderer;
import org.nemesis.shaders.StaticShader;
import org.nemesis.textures.ModelTexture;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class Main {

	public static void main ( String[] args ) {
		DisplayManager.createDisplay();
		EntityManager entityManager = new EntityManager();
		Loader loader = new Loader();
		StaticShader shader = new StaticShader();
		Renderer renderer = new Renderer( shader );

		RawModel model = OBJLoader.loadObjModel( "stall", loader );
		ModelTexture texture = new ModelTexture( loader.loadTexture( "stallTexture" ) );
		TexturedModel texturedModel = new TexturedModel( model, texture );

		Entity entity = entityManager.createEntity();
		entity.addComponent( new TransformComponent( new Vector3f( 0, 0, 0 ), new Vector3f( 0, 0, 0 ), new Vector3f( 1, 1, 1 ) ) );
		entity.addComponent( new MeshComponent( texturedModel ) );

		Camera camera = new Camera();

		while ( !glfwWindowShouldClose( DisplayManager.window ) ) {
			camera.move();
			renderer.prepare();
			shader.start();
			entityManager.updateAllEntities( 0f );
			shader.loadViewMatrix( camera );
			renderer.render( entity, shader );
			shader.stop();
			DisplayManager.updateDisplay();
		}

		shader.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}