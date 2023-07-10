//type vertex
#version 330 core
layout(location = 0) in vec3 aPos;
layout(location = 1) in vec3 aColor;

out vec3 vColor;
uniform mat4 uViewProjection;

void main()
{
	gl_Position = uViewProjection * vec4(aPos, 1.0);
	vColor = aColor;
}

//type fragment
#version 330 core
layout(location = 0) out vec4 fragColor;

in vec3 vColor;

void main()
{
	fragColor = vec4(vColor, 1.0);
}
