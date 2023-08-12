package org.nemesis.renderEngine;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.nemesis.entities.Camera;
import org.nemesis.entities.Entity;
import org.nemesis.entities.Light;
import org.nemesis.entities.MeshComponent;
import org.nemesis.models.TexturedModel;
import org.nemesis.shaders.StaticShader;
import org.nemesis.shaders.TerrainShader;
import org.nemesis.terrains.Terrain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MasterRenderer {

	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	private Matrix4f projectionMatrix;
	private StaticShader entityShader = new StaticShader();
	private TerrainShader terrainShader = new TerrainShader();
	private EntityRenderer entityRenderer;
	private TerrainRenderer terrainRenderer;
	private Map<TexturedModel, List<Entity>> entities = new HashMap<>();
	private List<Terrain> terrains = new ArrayList<>();

	public MasterRenderer () {
		enableCulling();
		createProjectionMatrix();
		this.entityRenderer = new EntityRenderer( entityShader, projectionMatrix );
		this.terrainRenderer = new TerrainRenderer( terrainShader, projectionMatrix );
	}

	public static void enableCulling() {
		GL11.glEnable( GL11.GL_CULL_FACE );
		GL11.glCullFace( GL11.GL_BACK );
	}

	public static void disableCulling() {
		GL11.glDisable( GL11.GL_CULL_FACE );
	}

	public void render( Light sun, Camera camera)
	{
		this.prepare();
		entityShader.start();
		entityShader.loadLight( sun );
		entityShader.loadViewMatrix( camera );
		entityRenderer.render( entities );
		entityShader.stop();
		terrainShader.start();
		terrainShader.loadLight( sun );
		terrainShader.loadViewMatrix( camera );
		terrainRenderer.render( terrains );
		terrainShader.stop();
		entities.clear();
		terrains.clear();
	}

	public void processTerrain(Terrain terrain)
	{
		terrains.add( terrain );
	}

	public void processEntity(Entity entity)
	{
		TexturedModel entityModel = entity.getComponent( MeshComponent.class ).mesh;
		List<Entity> batch = entities.get( entityModel );
		if(batch != null)
		{
			batch.add( entity );
		}
		else
		{
			List<Entity> newBatch = new ArrayList<>();
			newBatch.add( entity );
			entities.put( entityModel, newBatch );
		}
	}

	public void cleanUp() {
		entityShader.cleanUp();
		terrainShader.cleanUp();
	}

	public void prepare () {
		GL11.glEnable( GL11.GL_DEPTH_TEST );
		GL11.glClear( GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT );
		GL11.glClearColor( 0.5f, 0.16f, 0.12f, 1 );
	}

	private void createProjectionMatrix () {
		projectionMatrix = new Matrix4f().perspective( ( float ) Math.toRadians( FOV ), ( float ) DisplayManager.getWindowWidth() / ( float ) DisplayManager.getWindowHeight(), NEAR_PLANE, FAR_PLANE );
	}
}
