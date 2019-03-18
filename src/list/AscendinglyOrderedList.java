package list;

/**
 * Write a description of class AscendinglyOrderedStringList here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class AscendinglyOrderedList<E extends Comparable<E>>
{
    E[] arr;
    int numItems = 0;

    @SuppressWarnings("unchecked")
    public AscendinglyOrderedList()   {
        arr = (E[]) new Comparable[3];
    }

    public boolean isEmpty()   {
        return numItems == 0;
    }

    public int size()  {
        return numItems;
    }

    public void add(E item)   {
        if(numItems == arr.length)  {
            alloc();
        }

        if(numItems == 0)   {
            arr[0] = item;
        }
        else if(numItems == 1)   {
            if(arr[0].compareTo(item) < 0)  {
                arr[1] = item;
            }
            else    {
                arr[1] = arr[0];
                arr[0] = item;
            }
        }
        else    {
            int left = 0;
            int right = numItems;
            int mid = -1;
            boolean adding = true;

            while(adding)   {
                mid = (right + left) / 2;
                if(left >= right)   {
                    adding = false;
                }
                else if(arr[mid].compareTo(item) == 0)  {
                    adding = false;
                }
                else if(arr[mid].compareTo(item) > 0)  {
                    right = mid;
                }
                else if(arr[mid].compareTo(item) < 0)    {
                    left = mid + 1;
                }
            }

            for(int i = numItems; i > mid; i--) {
                arr[i] = arr[i-1];
            }
            arr[mid] = item;
        }
        numItems++;
    }

    public E get(int ndx) {
        return arr[ndx];
    }

    public void remove(int ndx)    {
        if(!isEmpty())   {
            for(int i = ndx; i < numItems-1; i++) {
                arr[i] = arr[i+1];
            }
            arr[numItems-1] = null;
            numItems--;
        }
    }

    public int search(E item) {
    	if(numItems == 0)	{
    		return -1;
    	}
        int left = 0;
        int right = numItems;
        int mid = -1;
        boolean searching = true;

        while(searching)   {
            mid = (right + left) / 2;
            //System.out.println("M: " + mid + "\nL: " + left + "\nR: " + right + "\n");
            if(left >= right)   {
                mid = -1;
                searching = false;
            }
            else if(arr[mid].compareTo(item) == 0)  {
                searching = false;
            }
            else if(arr[mid].compareTo(item) > 0)  {
                right = mid;
            }
            else if(arr[mid].compareTo(item) < 0)    {
                left = mid + 1;
            }
        }
        return mid;
    }

    @SuppressWarnings("unchecked")
	public void clear()    {
        arr = (E[]) new Comparable[3];
    }

    public String toString()    {
        String result = "";
        for(int i = 0; i < numItems; i++)   {
            result += arr[i] + ", ";
        }
        return result;
    }

    @SuppressWarnings("unchecked")
	private void alloc()    {
        E[] temp = (E[]) new Comparable[arr.length*2];
        for(int i = 0; i < arr.length; i++) {
            temp[i] = arr[i];
        }
        arr = temp;
    }
}

