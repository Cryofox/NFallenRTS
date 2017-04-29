package coffeefoxstudios.model.utils;

/**
 * Created by Ryder Stancescu on 4/28/2017.
 */
public class Tuple<T1,T2> {

    public T1 getElement1() {
        return element1;
    }

    public void setElement1(T1 element1) {
        this.element1 = element1;
    }

    private T1 element1 = null;

    public T2 getElement2() {
        return element2;
    }

    public void setElement2(T2 element2) {
        this.element2 = element2;
    }

    private T2 element2 = null;


}
