#version 420

layout (location = 0) in vec3 meshVertex;
layout (location = 1) in vec2 meshTexcoord;

uniform mat4 projection;
uniform mat4 model;
uniform mat4 view;

out vec2 texcoord;

void main(){
    texcoord = meshTexcoord;

    gl_Position =  projection* view * model * vec4(meshVertex, 1.0);
}



