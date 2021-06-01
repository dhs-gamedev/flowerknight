#version 330

in  vec2 outTexCoord;
out vec4 fragColor;

uniform sampler2D texture_sampler;

uniform float time;

void main() {
    fragColor = texture(texture_sampler, outTexCoord);
    float x = 0.5 + cos(time) / 2, y = 0.5 + sin(time) / 2;
    float distance = sqrt ( pow(x - outTexCoord.x, 2) + pow(y - outTexCoord.y, 2) );
    fragColor *= 1 - distance;
}
