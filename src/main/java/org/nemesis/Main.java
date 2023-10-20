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
import org.nemesis.textures.TerrainTexture;
import org.nemesis.textures.TerrainTexturePack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;

public class Main {

	public static void main ( String[] args ) {
		DisplayManager.createDisplay();
		Loader loader = new Loader();
		EntityManager entityManager = new EntityManager();
		MasterRenderer masterRenderer = new MasterRenderer();

		// put this into a config
		TerrainTexture backgroundTexture = new TerrainTexture( loader.loadTexture("grassy2") );
		TerrainTexture rTexture = new TerrainTexture( loader.loadTexture("mud") );
		TerrainTexture gTexture = new TerrainTexture( loader.loadTexture("grassFlowers") );
		TerrainTexture bTexture = new TerrainTexture( loader.loadTexture("path") );
		TerrainTexturePack texturePack = new TerrainTexturePack( backgroundTexture, rTexture, gTexture, bTexture );
		TerrainTexture blendMap = new TerrainTexture( loader.loadTexture("blendMap") );

		ModelTexture texture = new ModelTexture( loader.loadTexture( "purple" ) );
		texture.setShineDamper( 10 );
		texture.setReflectivity( 1 );
		ModelTexture grassTexture = new ModelTexture( loader.loadTexture( "grass" ) );
		ModelTexture grassTex = new ModelTexture( loader.loadTexture( "grassTexture" ) );
		grassTex.setHasTransparency( true );
		grassTex.setUseFakeLighting( true );
		ModelTexture fernTex = new ModelTexture( loader.loadTexture( "fern" ) );
		RawModel model = OBJLoader.loadObjModel( "dragon", loader );
		RawModel grassRaw = OBJLoader.loadObjModel( "grassModel", loader );
		RawModel fernRaw = OBJLoader.loadObjModel( "fern", loader );
		TexturedModel texturedModel = new TexturedModel( model, texture );
		TexturedModel grassModel = new TexturedModel( grassRaw, grassTex );
		TexturedModel fernModel = new TexturedModel( fernRaw, fernTex );
		Entity entity = entityManager.createEntity();
		entity.addComponent( new TransformComponent( new Vector3f( 0, 0, -25 ), new Vector3f( 0, 0, 0 ), new Vector3f( 1, 1, 1 ) ) );
		entity.addComponent( new MeshComponent( texturedModel ) );
		List<Entity> entities = new ArrayList<>();
		Random random = new Random();
		for ( int i = 0 ; i < 500 ; i++ ) {
			Entity g = entityManager.createEntity();
			g.addComponent( new TransformComponent( new Vector3f( random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600 ), new Vector3f( 0, 0, 0 ), new Vector3f( 1, 1, 1 ) ) );
			g.addComponent( new MeshComponent( grassModel ) );
			entities.add( g );
			Entity f = entityManager.createEntity();
			f.addComponent( new TransformComponent( new Vector3f( random.nextFloat() * 800 - 400, 0, random.nextFloat() * -600 ), new Vector3f( 0, 0, 0 ), new Vector3f( 1, 1, 1 ) ) );
			f.addComponent( new MeshComponent( fernModel ) );
			entities.add( f );
		}
		Light light = new Light( new Vector3f( 0, 0, -20 ), new Vector3f( 1, 1, 1 ) );
		List<Terrain> terrains = new ArrayList<>();
		Terrain terrain1 = new Terrain( 0, 0, loader, texturePack, blendMap );
		Terrain terrain2 = new Terrain( -1, 0, loader, texturePack, blendMap );
		Terrain terrain3 = new Terrain( 0, -1, loader, texturePack, blendMap );
		Terrain terrain4 = new Terrain( -1, -1, loader, texturePack, blendMap );
		terrains.add( terrain1 );
		terrains.add( terrain2 );
		terrains.add( terrain3 );
		terrains.add( terrain4 );
		// end of config

		Camera camera = new Camera();

		while ( !glfwWindowShouldClose( DisplayManager.window ) ) {
			camera.move();
			entityManager.updateAllEntities( 0f );
			masterRenderer.processEntity( entity );
			for ( Entity e : entities ) {
				masterRenderer.processEntity( e );
			}
			for ( Terrain t : terrains ) {
				masterRenderer.processTerrain( t );
			}
			masterRenderer.render( light, camera );
			DisplayManager.updateDisplay();
		}
		masterRenderer.cleanUp();
		loader.cleanUp();
		DisplayManager.closeDisplay();
	}
}