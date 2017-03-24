#version 420 core

layout(location = 0) in vec3 meshVertex;
layout(location = 1) in vec4 meshColor;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

out vec4 color;

void main() {
    color = meshColor ;
    gl_Position = projection * view * model * vec4(meshVertex, 1.0);
}
