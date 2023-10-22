package org.nemesis.components;

import org.lwjgl.PointerBuffer;
import org.lwjgl.assimp.*;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.assimp.Assimp.aiReleaseImport;

public class ModelComponent implements Component {
	private AIScene scene;
	private AIMesh[] meshes;
	private AIAnimation[] animations;
	private float[] vertices;
	private int[] indices;

	public ModelComponent(String modelPath) {
		scene = Assimp.aiImportFile("src/main/resources/model/" + modelPath + ".obj", Assimp.aiProcess_Triangulate);
		if (scene == null) {
			throw new RuntimeException("Failed to import model: " + modelPath);
		}

		PointerBuffer meshesBuffer = scene.mMeshes();
		meshes = new AIMesh[meshesBuffer.remaining()];
		for (int i = 0; i < meshesBuffer.remaining(); i++) {
			meshes[i] = AIMesh.create(meshesBuffer.get(i));
		}

		PointerBuffer animationsBuffer = scene.mAnimations();
		if (animationsBuffer != null && animationsBuffer.remaining() > 0) {
			animations = new AIAnimation[animationsBuffer.remaining()];
			for (int i = 0; i < animationsBuffer.remaining(); i++) {
				animations[i] = AIAnimation.create(animationsBuffer.get(i));
			}
		} else {
			animations = new AIAnimation[0];
		}

		// Extract the vertices and indices from the meshes

		List<Float> verticesList = new ArrayList<>();
		List<Integer> indicesList = new ArrayList<>();

		for (AIMesh mesh : meshes) {
			AIVector3D.Buffer verticesBuffer = mesh.mVertices();
			while (verticesBuffer.hasRemaining()) {
				AIVector3D vertex = verticesBuffer.get();
				verticesList.add(vertex.x());
				verticesList.add(vertex.y());
				verticesList.add(vertex.z());
			}

			AIFace.Buffer facesBuffer = mesh.mFaces();
			while (facesBuffer.hasRemaining()) {
				AIFace face = facesBuffer.get();
				IntBuffer indicesBuffer = face.mIndices();
				while (indicesBuffer.hasRemaining()) {
					indicesList.add(indicesBuffer.get());
				}
			}
		}

		vertices = new float[verticesList.size()];
		for (int i = 0; i < verticesList.size(); i++) {
			vertices[i] = verticesList.get(i);
		}

		indices = new int[indicesList.size()];
		for (int i = 0; i < indicesList.size(); i++) {
			indices[i] = indicesList.get(i);
		}
	}

	public float[] getVertices () {
		return vertices;
	}

	public int[] getIndices () {
		return indices;
	}

	public AIScene getScene () {
		return scene;
	}

	public AIMesh[] getMeshes () {
		return meshes;
	}

	public AIAnimation[] getAnimations () {
		return animations;
	}

	public void cleanup() {
		aiReleaseImport(scene);
		scene = null;
		meshes = null;
		animations = null;
		vertices = null;
		indices = null;
	}
}
