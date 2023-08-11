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
import org.nemesis.textures.ModelTexture;
import org.nemesis.utils.Maths;

import java.util.List;
import java.util.Map;

/**
 * Handles the rendering of a model to the screen.
 */
public class Renderer {

	private static final float FOV = 70;
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	private Matrix4f projectionMatrix;
	private StaticShader shader;

	public Renderer ( StaticShader shader ) {
		this.shader = shader;
		GL11.glEnable( GL11.GL_CULL_FACE );
		GL11.glCullFace( GL11.GL_BACK );
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
		GL11.glClearColor( 0.5f, 0.16f, 0.12f, 1 );
	}

	public void render ( Map<TexturedModel, List<Entity>> entities ) {
//		TexturedModel texturedModel = entity.getComponent( MeshComponent.class ).mesh;
		for ( TexturedModel model : entities.keySet() ) {
			prepareTexturedModel( model );
			List<Entity> batch = entities.get( model );
			for ( Entity entity : batch ) {
				prepareInstance( entity );
				GL11.glDrawElements( GL11.GL_TRIANGLES, model.getRawModel().getVertexCount(), GL11.GL_UNSIGNED_INT, 0 );
			}
			unbindTexturedModel();
		}
	}

	private void prepareTexturedModel ( TexturedModel texturedModel ) {
		RawModel model = texturedModel.getRawModel();
		GL30.glBindVertexArray( model.getVaoID() );
		GL20.glEnableVertexAttribArray( 0 );
		GL20.glEnableVertexAttribArray( 1 );
		GL20.glEnableVertexAttribArray( 2 );

		ModelTexture texture = texturedModel.getTexture();
		shader.loadShineVariables( texture.getShineDamper(), texture.getReflectivity() );
		GL13.glActiveTexture( GL13.GL_TEXTURE0 );
		GL11.glBindTexture( GL11.GL_TEXTURE_2D, texturedModel.getTexture().getTextureID() );
	}

	private void unbindTexturedModel () {
		GL20.glDisableVertexAttribArray( 0 );
		GL20.glDisableVertexAttribArray( 1 );
		GL20.glDisableVertexAttribArray( 2 );
		GL30.glBindVertexArray( 0 );
	}

	private void prepareInstance ( Entity entity ) {
		TransformComponent transform = entity.getComponent( TransformComponent.class );
		Matrix4f transformationMatrix = Maths.createTransformationMatrix( transform.position, transform.rotation.x, transform.rotation.y, transform.rotation.z, transform.scale.x );
		shader.loadTransformationMatrix( transformationMatrix );
	}

	private void createProjectionMatrix () {
		projectionMatrix = new Matrix4f().perspective( ( float ) Math.toRadians( FOV ), ( float ) DisplayManager.getWindowWidth() / ( float ) DisplayManager.getWindowHeight(), NEAR_PLANE, FAR_PLANE );
	}

}
