package Helpers;

public class Arrays<T> {
    void Swap(T[] a, int i1, int i2) {
        T tmp = a[i1];
        a[i1] = a[i2];
        a[i2] = tmp;
    }

    public T[] Reverse(T[] a) {
        for (int i = 0; i < (a.length / 2); i++)
            Swap(a, i, a.length - 1 - i);
        return a;
    }

    public Boolean Check(T[] a, Integer index) {
        return index > -1 && index < a.length;
    }

    public T[] Copy(T[] a) {
        return java.util.Arrays.copyOf(a, a.length);
    }
}
