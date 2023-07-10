package renderer;
import java.util.ArrayList;

public class BufferLayout {

    public ArrayList<BufferElement> mElements;
    public int Stride = 0;

    public  BufferLayout(ArrayList<BufferElement> elements){
        mElements = elements;
        calculateOffsetAndStride();
    }


    public void calculateOffsetAndStride(){
        int offset = 0;
        Stride = 0;

        for(BufferElement element : mElements){

            element.Offset = offset;
            offset += element.Size;
            Stride += element.Size;
        }
    }

    public ArrayList<BufferElement> getElements(){
        return mElements;
    }
}
