package org.nemesis.shaders;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.nemesis.entities.Camera;
import org.nemesis.entities.Light;
import org.nemesis.utils.Maths;

public class TerrainShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/main/resources/shader/terrainVertexShader.vert";
	private static final String FRAGMENT_FILE = "src/main/resources/shader/terrainFragmentShader.frag";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;
	private int location_lightPosition;
	private int location_lightColor;
	private int location_shineDamper;
	private int location_reflectivity;
	private int location_skyColor;
	private int location_backgroundTexture;
	private int location_rTexture;
	private int location_gTexture;
	private int location_bTexture;
	private int location_blendMap;

	public TerrainShader () {
		super( VERTEX_FILE, FRAGMENT_FILE );
	}

	@Override
	protected void bindAttributes () {
		super.bindAttribute( 0, "position" );
		super.bindAttribute( 1, "textureCoords" );
		super.bindAttribute( 2, "normal" );
	}

	@Override
	protected void getAllUniformLocations () {
		location_transformationMatrix = super.getUniformLocation( "transformationMatrix" );
		location_projectionMatrix = super.getUniformLocation( "projectionMatrix" );
		location_viewMatrix = super.getUniformLocation( "viewMatrix" );
		location_lightPosition = super.getUniformLocation( "lightPosition" );
		location_lightColor = super.getUniformLocation( "lightColor" );
		location_shineDamper = super.getUniformLocation( "shineDamper" );
		location_reflectivity = super.getUniformLocation( "reflectivity" );
		location_skyColor = super.getUniformLocation( "skyColor" );
		location_backgroundTexture = super.getUniformLocation( "backgroundTexture" );
		location_rTexture = super.getUniformLocation( "rTexture" );
		location_gTexture = super.getUniformLocation( "gTexture" );
		location_bTexture = super.getUniformLocation( "bTexture" );
		location_blendMap = super.getUniformLocation( "blendMap" );
	}

	public void connectTextureUnits() {
		super.loadInt( location_backgroundTexture, 0 );
		super.loadInt( location_rTexture, 1 );
		super.loadInt( location_gTexture, 2 );
		super.loadInt( location_bTexture, 3 );
		super.loadInt( location_blendMap, 4 );
	}

	public void loadSkyColor ( float r, float g, float b ) {
		super.loadVector( location_skyColor, new Vector3f( r, g, b ) );
	}

	public void loadShineVariables ( float shineDamper, float reflectivity ) {
		super.loadFloat( location_shineDamper, shineDamper );
		super.loadFloat( location_reflectivity, reflectivity );
	}

	public void loadTransformationMatrix ( Matrix4f matrix ) {
		super.loadMatrix( location_transformationMatrix, matrix );
	}

	public void loadLight ( Light light ) {
		super.loadVector( location_lightPosition, light.getPosition() );
		super.loadVector( location_lightColor, light.getColor() );
	}

	public void loadViewMatrix ( Camera camera ) {
		Matrix4f viewMatrix = Maths.createViewMatrix( camera );
		super.loadMatrix( location_viewMatrix, viewMatrix );
	}

	public void loadProjectionMatrix ( Matrix4f matrix ) {
		super.loadMatrix( location_projectionMatrix, matrix );
	}

}
