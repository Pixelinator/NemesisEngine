package org.nemesis;

import org.lwjgl.BufferUtils;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWNativeGLX;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.glFlush;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

;

/**
 * This class contains all the methods needed to set-up, maintain, and close a LWJGL display.
 * <p>
 * Usage:
 * <p>
 * DisplayManager.createDisplay();
 * <p>
 * while (!glfwWindowShouldClose(DisplayManager.window)) {
 * DisplayManager.updateDisplay();
 * }
 * <p>
 * DisplayManager.closeDisplay();
 */
public class DisplayManager {


	private static final int WIDTH = 1280;
	private static final int HEIGHT = 720;
	private static final String TITLE = "OpenGL 3D Game Engine";

	private static long lastFrameTime;
	private static float delta;

	// The window handle
	public static long window;

	/**
	 * Creates a display window on which we can render our game. The dimensions
	 * of the window are determined by setting the display mode. By using
	 * "glViewport" we tell OpenGL which part of the window we want to render
	 * our game onto. We indicated that we want to use the entire window.
	 */
	public static void createDisplay () {
		System.out.println( "LWJGL " + Version.getVersion() );

		GLFWNativeGLX.setPath( GL.getFunctionProvider() );
		// Setup an error callback. The default implementation
		// will print the error message in System.err.
		GLFWErrorCallback.createPrint( System.err ).set();

		// Initialize GLFW. Most GLFW functions will not work before doing this.
		if ( !glfwInit() ) {
			throw new IllegalStateException( "Unable to initialize GLFW" );
		}

		// Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint( GLFW_CONTEXT_VERSION_MAJOR, 3 );
		glfwWindowHint( GLFW_CONTEXT_VERSION_MINOR, 2 );
		glfwWindowHint( GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE );
		glfwWindowHint( GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE );
		glfwWindowHint( GLFW_VISIBLE, GLFW_FALSE ); // the window will stay hidden after creation
		glfwWindowHint( GLFW_RESIZABLE, GLFW_TRUE ); // the window will be resizable
		glfwWindowHint( GLFW_DOUBLEBUFFER, GLFW_FALSE );

		// Create the window
		window = glfwCreateWindow( WIDTH, HEIGHT, TITLE, NULL, NULL );
		if ( window == NULL ) {
			throw new RuntimeException( "Failed to create the GLFW window" );
		}

		// Get the thread stack and push a new frame
		try ( MemoryStack stack = stackPush() ) {
			IntBuffer pWidth = stack.mallocInt( 1 ); // int*
			IntBuffer pHeight = stack.mallocInt( 1 ); // int*

			// Get the window size passed to glfwCreateWindow
			glfwGetWindowSize( window, pWidth, pHeight );

			// Get the resolution of the primary monitor
			GLFWVidMode vidmode = glfwGetVideoMode( glfwGetPrimaryMonitor() );

			// Center the window
			if ( vidmode != null ) {
				glfwSetWindowPos( window, ( vidmode.width() - pWidth.get( 0 ) ) / 2, ( vidmode.height() - pHeight.get( 0 ) ) / 2
				);
			}
		} // the stack frame is popped automatically

		// Make the OpenGL context current
		glfwMakeContextCurrent( window );
		// Enable v-sync
		glfwSwapInterval( 0 );

		// Make the window visible
		glfwShowWindow( window );

		// This line is critical for LWJGL's interoperation with GLFW's
		// OpenGL context, or any context that is managed externally.
		// LWJGL detects the context that is current in the current thread,
		// creates the GLCapabilities instance and makes the OpenGL
		// bindings available for use.
		GL.createCapabilities();
		lastFrameTime = getCurrentTime();
	}

	/**
	 * This method is used to update the display at the end of every frame. When
	 * we have set up a rendering process this method will display whatever
	 * we've been rendering onto the screen. The "sync" method is used here to
	 * cap the frame rate. Without this the computer would just try to run the
	 * game as fast as it possibly can, doing more work than it needs to.
	 */
	public static void updateDisplay () {
		glfwSwapBuffers( window ); // swap the color buffers
		glFlush();
		// Poll for window events. The key callback above will only be
		// invoked during this call.
		glfwPollEvents();
		long currentFrameTime = getCurrentTime();
		delta = ( currentFrameTime - lastFrameTime ) / 1000f;
		lastFrameTime = currentFrameTime;
	}

	public static float getFrameTimeSeconds () {
		return delta;
	}

	public static int getWindowWidth () {
		IntBuffer w = BufferUtils.createIntBuffer( 1 );
		glfwGetWindowSize( window, w, null );
		return w.get( 0 );
	}

	public static int getWindowHeight () {
		IntBuffer h = BufferUtils.createIntBuffer( 1 );
		glfwGetWindowSize( window, null, h );
		return h.get( 0 );
	}

	/**
	 * This closes the window when the game is closed.
	 */
	public static void closeDisplay () {
		// Free the window callbacks and destroy the window
		glfwFreeCallbacks( window );
		glfwDestroyWindow( window );

		// Terminate GLFW and free the error callback
		glfwTerminate();
		glfwSetErrorCallback( null ).free();
	}

	public static long getWindow () {
		return window;
	}

	public static long getCurrentTime () {
		return ( long ) ( org.lwjgl.glfw.GLFW.glfwGetTime() * 1000 );
	}
}
