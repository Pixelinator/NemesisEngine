package org.nemesis;

import org.joml.Vector3f;
import org.nemesis.entities.*;
import org.nemesis.models.RawModel;
import org.nemesis.models.TexturedModel;
import org.nemesis.renderEngine.*;
import org.nemesis.shaders.StaticShader;
import org.nemesis.terrains.Terrain;
import org.nemesis.textures.ModelTexture;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class Main {

	public static void main ( String[] args ) {
		DisplayManager.createDisplay();
		EntityManager entityManager = new EntityManager();
		Loader loader = new Loader();
		MasterRenderer masterRenderer = new MasterRenderer();

		RawModel model = OBJLoader.loadObjModel( "dragon", loader );
		ModelTexture texture = new ModelTexture( loader.loadTexture( "purple" ) );
		texture.setShineDamper( 10 );
		texture.setReflectivity( 1 );
		TexturedModel texturedModel = new TexturedModel( model, texture );

		Entity entity = entityManager.createEntity();
		entity.addComponent( new TransformComponent( new Vector3f( 0, 0, -25 ), new Vector3f( 0, 0, 0 ), new Vector3f( 1, 1, 1 ) ) );
		entity.addComponent( new MeshComponent( texturedModel ) );

		Light light = new Light( new Vector3f(0,0,-20), new Vector3f(1,1,1) );

		Terrain terrain1 = new Terrain( 0,0,loader, new ModelTexture( loader.loadTexture( "grass" ) ) );
		Terrain terrain2 = new Terrain( -1,0,loader, new ModelTexture( loader.loadTexture( "grass" ) ) );
		Terrain terrain3 = new Terrain( 0,-1,loader, new ModelTexture( loader.loadTexture( "grass" ) ) );
		Terrain terrain4 = new Terrain( -1,-1,loader, new ModelTexture( loader.loadTexture( "grass" ) ) );

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