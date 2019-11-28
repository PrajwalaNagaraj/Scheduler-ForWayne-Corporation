public class HeapNode {
    int heap_key;
    int WayneB_completion_timeMH;
    RB_Node rb_Node;//To keep the RED BLACK node as reference for every minheap node

    // Constructor to initialize the buildings execution_time, completion_time and RED BLACK node
    public HeapNode(int total_time, int executionTime, RB_Node rb_Node) {
        heap_key = executionTime;
        this.rb_Node = rb_Node;
        this.WayneB_completion_timeMH  = total_time;
    }


    public HeapNode() {
    }

    public boolean isTotalTimeGTExetime(HeapNode h_node) {
        return (h_node.WayneB_completion_timeMH > h_node.heap_key);
    }
}
