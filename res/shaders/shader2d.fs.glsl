#version 420 core
uniform sampler2D diffuse;

in vec2 texcoord;

out vec4 fragColor;

void main(){
//    fragColor = vec4(1, 1.0, 1.0, 1.0);
//    fragColor = texture(texcoord, diffuse);
    fragColor = texture(diffuse, texcoord);
}
