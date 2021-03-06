import java.util.Arrays;

public class CustomerQueue {
    private Customer[] array;

    //constructor: set variables
    //capacity = initial capacity of array
    public CustomerQueue(int capacity) {
        this.array = new Customer[capacity];
    }

    //insert Customer c into queue
    //return the final index at which the customer is stored
    //return -1 if the customer could not be inserted
    public int insert(Customer c) {
        if (isEmpty()) {
            array[0] = c;
            return 0;
        }
        if (size() == array.length)
            return -1;

        boolean isDone = false;
        int index = 0;
        while (!isDone) {
            if (isLeftEmpty(index)) {
                index = insertLeft(index, c);
                index = floatUp(index);
                isDone = true;
            } else if (isRightEmpty(index)) {
                index = insertRight(index, c);
                index = floatUp(index);
                isDone = true;

            } else {
                index++;
            }
        }
        return index;
    }

    public int floatUp(int i) {
        int parentIndex = (i-1)/2;
        while(i > 0 && array[i].compareTo(array[parentIndex]) > 0) {
            swap(i, parentIndex);
            i = parentIndex;
            parentIndex = (i-1)/2;
        }
        return i;
    }

    public boolean isLeftEmpty(int i) {
        if (2 * i + 1 >= array.length)
            return true;
        return array[2 * i + 1] == null;
    }

    public int insertLeft(int i, Customer c) {
        array[2 * i + 1] = c;
        return 2 * i + 1;
    }

    public boolean isRightEmpty(int i) {
        if (2 * i + 2 >= array.length)
            return true;
        return array[2 * i + 2] == null;
    }

    public int insertRight(int i, Customer c) {
        array[2 * i + 2] = c;
        return 2 * i + 2;
    }

    public boolean isLeaf(int i) {
        return isRightEmpty(i) && isLeftEmpty(i);
    }


    public void swap(int i, int j) {
//        System.out.println("swapping " + array[i].name() + " and " + array[j].name());
        Customer temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    //remove and return the customer with the highest investment value
    //if there are multiple customers with the same investment value,
    //return the one who arrived first
    public Customer delMax() {
        Customer c;
        if (isEmpty())
            return null;
        if (isLeaf(0)) {
            c = array[0];
            array[0] = null;
            return c;
        }
        int i = swimDown(0);
        c = array[i];
        array[i] = null;
        return c;

    }

    public int swimDown (int i) {
        int left = i * 2 + 1;
        int right = i * 2 + 2;
        if (isLeaf(i)) {
            return i;
        }
        if (isRightEmpty(i)) {
            swap(i, left);
            return swimDown(left);
        }
        if (isLeftEmpty(i)) {
            swap(i, right);
            return swimDown(right);
        }

        if (array[right].compareTo(array[left]) > 0) {
            swap(i, right);
            i = right;
        } else {
            swap(i, left);
            i = left;
        }
        return swimDown(i);
    }


    //return but do not remove the first customer in the queue
    public Customer getMax() {
        if (isEmpty())
            return null;
        int max = 0;
        int index = 0;
        for (int i = 0; i < size(); i++) {
            if (array[i].investment() > max) {
                max = array[i].investment();
                index = i;
            }
        }
        return array[index];
    }

    //return the number of customers currently in the queue
    public int size() {
        int size = 0;
        for (Customer c : array) {
            if (c != null)
                size++;
        }
        return size;
    }

    //return true if the queue is empty; false else
    public boolean isEmpty() {
        if (array == null)
            return true;
        for (Customer c : array) {
            if (c != null)
                return false;
        }
        return true;
    }

    //used for testing underlying data structure
    public Customer[] getArray() {
        return array;
    }
}
