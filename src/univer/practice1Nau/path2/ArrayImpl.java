package ua.univer.practice1.path2;

import java.util.Iterator;

public class ArrayImpl implements Array{

    private Object[] objects = new Object[0];

    public static void main(String[] args){

        ArrayImpl array = new ArrayImpl();
        array.add("A");
        array.add("B");
        array.add("C");

        System.out.print("Array before changing: ");
        array.set(3, "D");
        System.out.print("Array after changing: ");
        System.out.print(array);
        array.remove(2);
        System.out.print("Array after changing: ");
        System.out.print("Index of A: " + array.indexOf("A"));
        System.out.print("Get element of 1: " + array.get(1));
        System.out.print("Size of array: " + array.size());

    }

    public void add(Object element) {
        Object[] object = new Object[this.objects.length + 1];
        System.arraycopy(this.objects, 0, object, 0, this.objects.length);
        object[object.length - 1] = element;
        this.objects = object;
    }

    public void set(int index, Object element) {this.objects[index] = element;}

    public Object get(int index) {return this.objects[index];}

    public int indexOf(Object element) {
        for (int i = 0; i < this.objects.length; i++) {
            if (this.objects[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    public void remove(int index) {
        Object[] object = new Object[this.objects.length - 1];
        System.arraycopy(this.objects, 0, object, 0, index);
        System.arraycopy(this.objects, index + 1, object, index, object.length - index);
        this.objects = object;
    }

    public void clear() {this.objects = new Object[0];}

    public int size() {return this.objects.length;}

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");

        for (int i = 0; i < this.objects.length; i++){
            stringBuilder.append(this.objects[i]);
        }

        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public Iterator<Object> iterator() {return ua.univer.practice1.path2.ArrayImpl.IteratorImpl(this);}

}
