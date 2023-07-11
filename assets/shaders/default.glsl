//type vertex
#version 330 core
layout(location = 0) in vec3 aPos;
layout(location = 1) in vec3 aColor;
layout(location = 2) in vec2 aTexCoord;

out vec3 vColor;
out vec2 vTexCoord;

uniform mat4 uViewProjection;
uniform mat4 uTransform;

void main()
{
	gl_Position = uViewProjection * uTransform * vec4(aPos, 1.0);

	vColor = aColor;
	vTexCoord = aTexCoord;
}

//type fragment
#version 330 core
layout(location = 0) out vec4 fragColor;

in vec3 vColor;
in vec2 vTexCoord;

uniform sampler2D uTexture;
uniform vec4 uTintColor;

void main()
{
	fragColor = texture(uTexture, vTexCoord) * uTintColor;
}
