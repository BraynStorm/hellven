#version 420 core
uniform sampler2D diffuseTexture;
uniform vec3 meshColor;

in vec2 texcoord;

out vec4 fragColor;

void main(){

    if(texture(diffuseTexture, texcoord).w < 0.2) {
        discard;
    } else {
        fragColor.xyz = meshColor;
        fragColor.w = 1;
    }

}
