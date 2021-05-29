#version 330

// This is for game objects whose positions are updated by the camera.

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;

uniform vec2 cameraLoc;
uniform float zoom;

out vec2 outTexCoord;

void main() {
    gl_Position = vec4(position, 1.0);
    gl_Position.xy -= cameraLoc; // Update with camera position
    gl_Position.xy /= zoom;
    outTexCoord = texCoord;
}
