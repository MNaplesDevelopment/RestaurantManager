package list;

public class ListRA<T> implements ListInterface<T>	{
	T[] arr;
	int numItems;
	int cap;
	
	public ListRA()	{
		this(3);
	}
	
	@SuppressWarnings("unchecked")
	public ListRA(int cap)	{
		this.cap = cap;
		arr = (T[]) new Object[cap];
		numItems = 0;
	}
	
	public boolean isEmpty()	{
		return (numItems == 0) ? true : false;
	}
	
	public int size()	{
		return numItems;
	}
	
	public void add(T item)	{
		if(numItems == arr.length)	{
			alloc();
		}
		arr[numItems] = item;
		numItems++;
	}
	
	public void add(int ndx, T item)	{
		if(numItems == arr.length)	{
			alloc();
		}
		for(int i = numItems; i < ndx; i--)	{
			arr[i] = arr[i-1];
		}
		arr[ndx] = item;
		numItems++;
	}
	
	public T get(int ndx)	{
		return arr[ndx];
	}
	
	public void remove(int ndx)	{
		if(!isEmpty())	{
			for(int i = ndx; i < numItems - 1; i++)	{
				arr[i] = arr[i+1];
			}
			arr[numItems-1] = null;
			numItems--;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void removeAll()	{
		arr = (T[])	new Object[cap];
	}
	
	@SuppressWarnings("unchecked")
	private void alloc()	{
		T[] temp = (T[]) new Object[arr.length * 2];
		for(int i = 0; i < arr.length; i++)	{
			temp[i] = arr[i];
		}
		arr = temp;
	}
}
