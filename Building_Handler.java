import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
public class Building_Handler {

	public RB_tree rbTree;
	public MinHeap heap;
	public int jobID;
	ArrayList<String> result;
	public Building_Handler()
	{
		this.heap=new MinHeap();
		this.rbTree=new RB_tree();
		this.result=new ArrayList<String>();
		
	}
	public void WritetoFile() 
	{
		try{
		BufferedWriter writer=new BufferedWriter(new FileWriter("output_file.txt"));

		for(int i=0;i<result.size();i++){
				writer.append(result.get(i));
				writer.append('\n');
		}
		writer.close();
		}

		catch(IOException e)
		{
		e.printStackTrace();
		}
	}

	public void Insert(int ID,int total_time,int executionTime){
		if(rbTree.insertSearch(ID, rbTree.root) == 0) {
			rbTree.Insert(ID, total_time, executionTime);
			RB_Node newNode = rbTree.Search(ID, rbTree.root);
			heap.insert(total_time, executionTime, newNode);
		}

	}
	public  void Print_WayneB(int ID){
		RB_Node newNode=rbTree.Search(ID, rbTree.root);
		if(!newNode.isEmpty())
		{
			result.add("(" + rbTree.PrintBuilding(ID) + "," + newNode.WayneB_exe_time + "," + newNode.WayneB_completion_time + ")");
		}else{
			result.add("(0,0,0)");
		}
	}
	public  void print_completed_buildings(int ID, long completed_time){
			result.add("("+ID+","+completed_time+")");

	}
	public  void Print_WayneBInRange(int ID1, int ID2){
		ArrayList<Integer>res=rbTree.PrintBuildings(ID1,ID2);

		if(res.size()!=0) {

			String s="";
		for(int i = 0; i < res.size(); i++){
			if(res.get(i) != 0) {
				RB_Node newNode = rbTree.Search(res.get(i), rbTree.root);
				s += "(" + res.get(i) + "," + newNode.WayneB_exe_time + "," + newNode.WayneB_completion_time + ")";
				if (i != res.size() - 1) {
					s += ",";
				}
			}
		}
		char[] checkarr = s.toCharArray();
		if(checkarr[checkarr.length-1] == ','){
			s=s.substring(0,checkarr.length-1);
		}
		result.add(s);
	 }else{
			result.add("(0,0,0)");
		}
	}
}
