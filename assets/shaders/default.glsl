//type vertex
#version 330 core
layout(location = 0) in vec3 aPos;

out vec2 vTexCoord;

uniform mat4 uViewProjection;
uniform mat4 uTransform;

void main()
{
	gl_Position = uViewProjection * uTransform * vec4(aPos, 1.0);
	vTexCoord = vec2((aPos.x + 0.5)/2.0, (aPos.y + 0.5) / 2.0);
}

//type fragment
#version 330 core
layout(location = 0) out vec4 fragColor;

in vec3 vColor;
in vec2 vTexCoord;

uniform sampler2D uTexture;

void main()
{
	fragColor = texture(uTexture, vTexCoord);
}
