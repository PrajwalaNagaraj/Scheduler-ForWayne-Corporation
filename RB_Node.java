public class RB_Node {
	int ID;
	int count;
	int WayneB_completion_time;
	int WayneB_exe_time;
	RB_Node left, right, parent;
    Color color;
	public RB_Node(int ID, int count, RB_Node left, RB_Node right,
			RB_Node parent, Color color,int total_time,int execution_time) {
		
		this.ID = ID;
		this.count = count;
		this.left = left;
		this.right = right;
		this.parent = parent;
		this.color = color;
		this.WayneB_completion_time=total_time;
		this.WayneB_exe_time= execution_time;
	}
	public RB_Node(){}
	public boolean istotal_time_gt_exe_time(RB_Node p){
		return (p.WayneB_completion_time > p.WayneB_exe_time);
	}
	public boolean isEmpty(){
		return false;
	}
}
