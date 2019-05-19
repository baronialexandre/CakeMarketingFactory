package l3info.projet.cakemarketingfactory.utils;

import java.util.ArrayList;


//permet de faire next() et de parcourir l'array en boucle
public class RotationSelector<T> {

    private ArrayList<T> arrayList;
    private int index;

    public RotationSelector(ArrayList arrayList)
    {
        this.arrayList = arrayList;
        index=0;
    }
    public T next()
    {
        T result = arrayList.get(index);
        if(index==arrayList.size()-1)
        {
            index = 0;
        }
        else
        {
            index++;
        }
        return result;
    }

    public int getIndex() {
        return index;
    }

    public T getCurrent() {
        if(index==0)
        {
            return arrayList.get(arrayList.size()-1);
        }
        else
        {
            return arrayList.get(index-1);
        }

    }
}
