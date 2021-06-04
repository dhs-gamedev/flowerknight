#version 330

in  vec2 outTexCoord;
out vec4 fragColor;

uniform sampler2D texture_sampler;

uniform float time;

void main() {
    fragColor = texture(texture_sampler, outTexCoord);
    float x = 0.5 + cos(time) / 2, y = 0.5 + sin(time) / 2;
    float dist = distance(vec3(x, y, 1.0), vec3(outTexCoord, 0));
    fragColor *= 1 / (dist * dist);
}
