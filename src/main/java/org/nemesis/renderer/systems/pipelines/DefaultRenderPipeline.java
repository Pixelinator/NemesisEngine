package org.nemesis.renderer.systems.pipelines;

import org.nemesis.renderer.resources.Shader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.glBindVertexArray;

public class DefaultRenderPipeline implements RenderPipeline {
	private Shader shader;

	@Override
	public void initShaders () throws Exception {
		shader = new Shader();
		shader.createVertexShader( """
				#version 330
				    
				layout (location=0) in vec3 position;
				    
				void main()
				{
				    gl_Position = vec4(position, 1.0);
				}
				""" );
		shader.createFragmentShader( """
				#version 330
				    
				out vec4 fragColor;
				    
				void main()
				{
				    fragColor = vec4(0.0, 0.5, 0.5, 1.0);
				}
				""" );
		shader.link();
	}

	@Override
	public void initPipeline () {

	}

	@Override
	public void setAttribLayout () {
		glVertexAttribPointer( 0, 3, GL_FLOAT, false, 0, 0 );
	}

	@Override
	public void setBlending () {

	}

	@Override
	public void executePass ( int vaoId ) {
		shader.bind();

		// Bind to the VAO
		glBindVertexArray( vaoId );
		glEnableVertexAttribArray( 0 );

		// Draw the vertices
		glDrawArrays( GL_TRIANGLES, 0, 3 );

		// Restore state
		glDisableVertexAttribArray( 0 );
		glBindVertexArray( 0 );

		shader.unbind();
	}

	@Override
	public void cleanup () {
		if ( shader != null ) {
			shader.cleanup();
		}
	}
}
