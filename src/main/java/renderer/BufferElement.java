package renderer;

enum ShaderDataType {
    None,
    Int, Int2, Int3, Int4, Boolean,
    Float, Float2, Float3, Float4,
    Mat2, Mat3, Mat4
};

public class BufferElement {
    public String Name;
    public ShaderDataType Type;
    public int Size = 0;
    public int Offset = 0;
    public boolean Normalized = false;

    public BufferElement(ShaderDataType type, String name, boolean normalized){
        Type = type;
        Name = name;
        Offset = 0;
        Size = ShaderDataTypeSize(Type);
        Normalized = normalized;
    }

    // Get Shader Type in size
    static int ShaderDataTypeSize(ShaderDataType type){
        switch (type){
            case Boolean:  return 1;
            case Int:      return 4;
            case Int2:     return 4 * 2;
            case Int3:     return 4 * 3;
            case Int4:     return 4 * 4;
            case Float:    return 4;
            case Float2:   return 4 * 2;
            case Float3:   return 4 * 3;
            case Float4:   return 4 * 4;
            case Mat2:	   return 2;
            case Mat3:	   return 3;
            case Mat4:	   return 4;
            case None:     return 0;
        }
        return 0;
    }

    public int getComponentCount(){
        switch (Type) {
            case Boolean:   return 1;
            case Int:	    return 1;
            case Int2:	    return 2;
            case Int3:	    return 3;
            case Int4:	    return 4;
            case Float:	    return 1;
            case Float2:    return 2;
            case Float3:    return 3;
            case Float4:    return 4;
            case Mat2:	    return 2 * 2;
            case Mat3:	    return 3 * 3;
            case Mat4:      return 4 * 4;
            case None:      return 0;
        }
        return 0;
    }
}