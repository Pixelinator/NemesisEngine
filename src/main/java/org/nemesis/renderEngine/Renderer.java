package org.nemesis.renderEngine;

import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.nemesis.entities.Entity;
import org.nemesis.entities.MeshComponent;
import org.nemesis.entities.TransformComponent;
import org.nemesis.models.RawModel;
import org.nemesis.models.TexturedModel;
import org.nemesis.shaders.StaticShader;
import org.nemesis.utils.Maths;

/**
 * Handles the rendering of a model to the screen.
 */
public class Renderer {

	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	private Matrix4f projectionMatrix;

	public Renderer ( StaticShader shader ) {
		createProjectionMatrix();
		shader.start();
		shader.loadProjectionMatrix( projectionMatrix );
		shader.stop();
	}

	/**
	 * This method must be called each frame, before any rendering is carried
	 * out. It basically clears the screen of everything that was rendered last
	 * frame (using the glClear() method). The glClearColor() method determines
	 * the colour that it uses to clear the screen. In this example it makes the
	 * entire screen red at the start of each frame.
	 */
	public void prepare () {
		GL11.glEnable( GL11.GL_DEPTH_TEST );
		GL11.glClear( GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT );
		GL11.glClearColor( 1, 0, 0, 1 );
	}

	/**
	 * Renders a model to the screen.
	 * <p>
	 * Before we can render a VAO it needs to be made active, and we can do this
	 * by binding it. We also need to enable the relevant attributes of the VAO,
	 * which in this case is just attribute 0 where we stored the position data.
	 * <p>
	 * The VAO can then be rendered to the screen using glDrawElements(). Using
	 * this draw method tells OpenGL that we want to use the index buffer to
	 * determine how the vertices should be connected instead of just connecting
	 * the vertices together in the order that they are stored in the VAO.
	 * <p>
	 * We tell it what type of shapes to render and the number of vertices that
	 * it needs to render. We also tell it was format the index data is in (we
	 * used ints) and finally we indicate where in the index buffer it should
	 * start rendering. We want it to start right at the beginning and render
	 * everything, so we put 0.
	 * <p>
	 * After rendering we unbind the VAO and disable the attribute.
	 *
	 * @param entity - The entity to be rendered.
	 * @param shader
	 */
	public void render ( Entity entity, StaticShader shader ) {
		TransformComponent transform = entity.getComponent( TransformComponent.class );
		TexturedModel texturedModel = entity.getComponent( MeshComponent.class ).mesh;
		RawModel model = texturedModel.getRawModel();
		GL30.glBindVertexArray( model.getVaoID() );
		GL20.glEnableVertexAttribArray( 0 );
		GL20.glEnableVertexAttribArray( 1 );
		GL20.glEnableVertexAttribArray( 2 );
		Matrix4f transformationMatrix = Maths.createTransformationMatrix( transform.position, transform.rotation.x, transform.rotation.y, transform.rotation.z, transform.scale.x );
		shader.loadTransformationMatrix( transformationMatrix );
		GL13.glActiveTexture( GL13.GL_TEXTURE0 );
		GL11.glBindTexture( GL11.GL_TEXTURE_2D, texturedModel.getTexture().getTextureID() );
		GL11.glDrawElements( GL11.GL_TRIANGLES, model.getVertexCount(), GL11.GL_UNSIGNED_INT, 0 );
		GL20.glDisableVertexAttribArray( 0 );
		GL20.glDisableVertexAttribArray( 1 );
		GL20.glDisableVertexAttribArray( 2 );
		GL30.glBindVertexArray( 0 );
	}

	private void createProjectionMatrix () {
		projectionMatrix = new Matrix4f().perspective( ( float ) Math.toRadians( FOV ), ( float ) DisplayManager.getWindowWidth() / ( float ) DisplayManager.getWindowHeight(), NEAR_PLANE, FAR_PLANE );
	}

}
