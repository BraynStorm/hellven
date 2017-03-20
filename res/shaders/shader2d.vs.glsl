#version 420

layout (location = 0) in vec3 meshVertex;
//layout (location = 1) in vec2 meshTexcoord;

//uniform mat4 projection;
uniform mat4 model;
//uniform mat4 view;

//flat out ivec2 texcoord;

void main(){
//    texcoord = meshTexcoord;
    //projection * view * model *
    gl_Position = model * vec4(meshVertex, 1.0);
}



