package org.nemesis;

import org.joml.Vector3f;
import org.nemesis.entities.*;
import org.nemesis.models.RawModel;
import org.nemesis.models.TexturedModel;
import org.nemesis.renderEngine.DisplayManager;
import org.nemesis.renderEngine.Loader;
import org.nemesis.renderEngine.MasterRenderer;
import org.nemesis.renderEngine.OBJLoader;
import org.nemesis.terrains.Terrain;
import org.nemesis.textures.ModelTexture;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class Main {

	public static void main ( String[] args ) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		EntityManager entityManager = new EntityManager();
		MasterRenderer masterRenderer = new MasterRenderer();

		ModelTexture texture = new ModelTexture( loader.loadTexture( "purple" ) );
		ModelTexture grassTexture = new ModelTexture( loader.loadTexture( "grass" ) );
		texture.setShineDamper( 10 );
		texture.setReflectivity( 1 );
		RawModel model = OBJLoader.loadObjModel( "dragon", loader );
		TexturedModel texturedModel = new TexturedModel( model, texture );

		Entity entity = entityManager.createEntity();
		entity.addComponent( new TransformComponent( new Vector3f( 0, 0, -25 ), new Vector3f( 0, 0, 0 ), new Vector3f( 1, 1, 1 ) ) );
		entity.addComponent( new MeshComponent( texturedModel ) );

		Light light = new Light( new Vector3f( 0, 0, -20 ), new Vector3f( 1, 1, 1 ) );

		Terrain terrain1 = new Terrain( 0, 0, loader, grassTexture );
		Terrain terrain2 = new Terrain( -1, 0, loader, grassTexture );
		Terrain terrain3 = new Terrain( 0, -1, loader, grassTexture );
		Terrain terrain4 = new Terrain( -1, -1, loader, grassTexture );

		Camera camera = new Camera();

		while ( !glfwWindowShouldClose( DisplayManager.window ) ) {
			camera.move();
			entityManager.updateAllEntities( 0f );
			masterRenderer.processEntity( entity );
			masterRenderer.processTerrain( terrain1 );
			masterRenderer.processTerrain( terrain2 );
			masterRenderer.processTerrain( terrain3 );
			masterRenderer.processTerrain( terrain4 );
			masterRenderer.render( light, camera );
			DisplayManager.updateDisplay();
		}
		masterRenderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}