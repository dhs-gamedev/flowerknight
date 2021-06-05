#version 330

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;

out vec2 outTexCoord;

uniform float width, height;

void main() {

    gl_Position = vec4(position, 1.0);

    // Width and height are the width and height of the window. We want to have
    // everything rendered in a square, so everything is rendered in the largest
    // square possible. To do this, we scale the larger coordinate space down to
    // the smaller one.
    if (width < height) {
        gl_Position.y *= (width / height);
    } else {
        gl_Position.x *= (height / width);
    }

    outTexCoord = texCoord;

}
