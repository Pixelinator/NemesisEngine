package org.nemesis.renderer.entities;

import org.nemesis.renderer.resources.Mesh;
import org.nemesis.renderer.systems.pipelines.PipelinePass;
import org.nemesis.renderer.systems.pipelines.RenderPipeline;

import java.util.HashMap;
import java.util.Map;

public interface RenderGameObject {
	Map<PipelinePass, RenderPipeline> pipelinePassMap = new HashMap<>();

	void addPipelineToPass(PipelinePass pipelinePass, RenderPipeline renderPipeline);
	RenderPipeline getPipelineForPass(PipelinePass pipelinePass);

	public Mesh getMesh();
}
