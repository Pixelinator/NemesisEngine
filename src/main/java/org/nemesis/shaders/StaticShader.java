package org.nemesis.shaders;

import org.joml.Matrix4f;
import org.nemesis.entities.Camera;
import org.nemesis.utils.Maths;

public class StaticShader extends ShaderProgram {

	private static final String VERTEX_FILE = "src/main/resources/shader/vertexShader.vert";
	private static final String FRAGMENT_FILE = "src/main/resources/shader/fragmentShader.frag";

	private int location_transformationMatrix;
	private int location_projectionMatrix;
	private int location_viewMatrix;

	public StaticShader() {
		super(VERTEX_FILE, FRAGMENT_FILE);
	}

	@Override
	protected void bindAttributes() {
		super.bindAttribute(0, "position");
		super.bindAttribute(1, "textureCoords");
	}

	@Override
	protected void getAllUniformLocations () {
		location_transformationMatrix = super.getUniformLocation("transformationMatrix");
		location_projectionMatrix = super.getUniformLocation( "projectionMatrix" );
		location_viewMatrix = super.getUniformLocation( "viewMatrix" );
	}

	public void loadTransformationMatrix( Matrix4f matrix ) {
		super.loadMatrix( location_transformationMatrix, matrix );
	}

	public void loadViewMatrix( Camera camera ) {
		Matrix4f viewMatrix = Maths.createViewMatrix(camera);
		super.loadMatrix( location_viewMatrix, viewMatrix );
	}

	public void loadProjectionMatrix( Matrix4f matrix ) {
		super.loadMatrix( location_projectionMatrix, matrix );
	}

}
