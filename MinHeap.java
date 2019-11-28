import java.util.ArrayList;

public class MinHeap {

    private HeapNode heap_arr[];
    int heap_size;

    public MinHeap() {
        this.heap_arr =new HeapNode[100];
        heap_size=0;
    }

    //Inserts data into heap node with execution time as heap_key
    public void insert(int total_time,int executionTime,RB_Node node) {
        HeapNode n=new HeapNode(total_time,executionTime,node);
        if(heap_size== heap_arr.length)
            arrayIncreaseByTwo();
        heap_arr[heap_size]=n;
        heap_size++;
        int i = heap_size - 1;
        int parent = parent(i);

        while (parent != i && heap_arr[i].heap_key < heap_arr[parent].heap_key) {

            swap_values(i, parent);
            i = parent;
            parent = parent(i);
        }
        buildHeap();
    }
    //Returns the current size of min heap
    public int getSize()
    {
        return heap_size;
    }
    //doubles size of existing array
    public void arrayIncreaseByTwo()
    {
        HeapNode cur_node[]=new HeapNode[2*heap_size];
        if(heap_size== heap_arr.length)
        {
            for(int i=0;i<heap_size;i++)
                cur_node[i]= heap_arr[i];
        }
        heap_arr =cur_node;
    }

    public void buildHeap() {

        for (int i = heap_size / 2; i >= 0; i--) {
            Heapify(i);
        }
    }
    //returns minimum elemnt from Heap
    public HeapNode extractMin() {

        if (heap_size == 0) {

            throw new IllegalStateException("MinHeap is currently empty");

        } else if (heap_size == 1) {

            HeapNode min = remove(0);
            --heap_size;

            return min;
        }
        HeapNode minBuilding = heap_arr[0];
        HeapNode lastBuilding = remove(heap_size - 1);
        heap_arr[0]=lastBuilding;

        Heapify(0);
        --heap_size;
        return minBuilding;
    }

    public HeapNode remove(int pos)
    {
        HeapNode cur_node= heap_arr[pos];
        for(int i=pos;i<heap_size-1;i++)
        {
            heap_arr[i]= heap_arr[i+1];
        }
        return  cur_node;
    }

    //find comparison of executions of buildings
    private int compare(int j,int h){
        int leftJ = left(j), leftH = left(h);
        int rightJ = rightNode(j), rightH = rightNode(j);
        while(j <= heap_size -1 && h <= heap_size-1) {
            if (heap_arr[j].heap_key <= heap_arr[h].heap_key)
                return h;
            else
                return j;
        }
        return -1;
    }

    //Heapify operation for rebalancing of Heap
    private void Heapify(int i) {
        int left = left(i);
        int rightNode = rightNode(i);
        int smallest = -1;
        //Logic to handle tie breaking for buildings with same execution time
        if (left <= heap_size - 1 && heap_arr[left].heap_key <= heap_arr[i].heap_key) {
            if(heap_arr[left].heap_key == heap_arr[i].heap_key) {
                if (heap_arr[left].rb_Node.ID < heap_arr[i].rb_Node.ID) {
                    smallest = left;
                }
                else {
                    smallest = i;
                }
            } else {
                smallest = left;
            }
        }
        else {
            smallest = i;
        }


        if (rightNode <= heap_size - 1 && heap_arr[rightNode].heap_key <= heap_arr[smallest].heap_key) {
            if(heap_arr[rightNode].heap_key == heap_arr[smallest].heap_key) {
                if (heap_arr[rightNode].rb_Node.ID < heap_arr[smallest].rb_Node.ID) {
                    smallest = rightNode;
                }
                else {
                    smallest = smallest;
                }
            } else {
                smallest = rightNode;
            }
        } else {
            smallest = smallest;
        }
        if (smallest != i) {
            swap_values(i, smallest);
            Heapify(smallest);
        }
    }

    public HeapNode getMin() {

        return heap_arr[0];
    }

    public boolean isEmpty() {
        
        return(heap_size == 0);
    }

    private int rightNode(int i) {

        return 2 * i + 2;
    }

    private int left(int i) {

        return 2 * i + 1;
    }

    private int parent(int i) {

        if (i % 2 == 1) {
            return i / 2;
        }

        return (i - 1) / 2;
    }

    private void swap_values(int i, int parent) {

        HeapNode temp = heap_arr[parent];
        heap_arr[parent]= heap_arr[i];
        heap_arr[i]=temp;
    }
}