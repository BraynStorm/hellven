#version 420 core

uniform vec3 meshColor;

in vec2 texcoord;

out vec4 fragColor;

void main(){
    fragColor.xyz = meshColor;
    fragColor.w = 1.0;
}
