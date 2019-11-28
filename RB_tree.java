
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


class  Empty extends RB_Node{
	public Empty() {
		super();
		this.color = Color.black;
	}
	public boolean isEmpty(){
		return true;
	}	
}

public class RB_tree {
	RB_Node root=new Empty();
		
	//Return Buildings in the range between ID1 to ID2
	public ArrayList<Integer> PrintBuildings(int ID1, int ID2){
		  ArrayList<Integer> res = new ArrayList<Integer>();
		  findBuildings( root, ID1, ID2,res);
		  return res;         
	}
	//Return if RB node with given ID exist
	public int PrintBuilding(int ID){
		  ArrayList<Integer> res = new ArrayList<Integer>();
		  findBuildings2( root, ID,res);
		  int total = 0;
		  if(res.size()==0)
		  {
			  return 0;
		  }
		  else
			return res.get(0);		      
	}
	//Function finds all the buildings in the range min,max
	private void findBuildings(RB_Node root, int min, int max, ArrayList<Integer> res){
        if (root == null) {
            return;
        }
        if (root.ID > min) {
            findBuildings(root.left, min, max,res);
        }
        if (root.ID >= min && root.ID <= max) {
            res.add(root.ID);
        }
        if (root.ID < max) {
            findBuildings(root.right, min, max,res);
        }
    
	}
	//this function finds the buildings with matching id to search node  
	private void findBuildings2(RB_Node root, int k, ArrayList<Integer> res){
        if (root == null) {
            return;
        }
        if (root.ID > k) {
            findBuildings2(root.left, k,res);
        }
        if (root.ID == k) {
            res.add(root.ID);
        }
        if (root.ID < k) {
            findBuildings2(root.right, k,res);
        }
    
	}
		
	//find min node in RB_tree
	public  RB_Node getMin(RB_Node root){
		RB_Node current = root;
		while(!current.left.isEmpty()){
			current = current.left;	
		}
		return current;
	}
	
	//find max node in RB_tree
	public  RB_Node getMax(RB_Node root){
		RB_Node current = root;
		while(!current.right.isEmpty()){
			current = current.right;
		}
		return current;
	}

	//search a node, if exsits, return the node, otherwise return null;
	public  RB_Node Search(int ID, RB_Node root){
		while(!root.isEmpty()){
			if(root.ID == ID){
				return root;
			}
		    else if(root.ID > ID){
			    root = root.left;
		    }
			else{
				root = root.right;
			}
		}
		return root;
	}

	public  int insertSearch(int ID, RB_Node root){
		while(!root.isEmpty()){
			if(root.ID == ID){
				return 1;
			}
			else if(root.ID > ID){
				root = root.left;
			}
			else{
				root = root.right;
			}
		}
		return 0;
	}

   // insertion into RB_tree
	public RB_Node Insert(int ID,int total_time,int executionTime){
		RB_Node tmp = this.root;
		RB_Node last = tmp;
		while(!tmp.isEmpty()){
			last = tmp;
			if(ID > tmp.ID){
				tmp = tmp.right;
			}
			else{ 
				tmp = tmp.left;
			}
		}
		Empty Empty = new Empty();
		RB_Node z = new RB_Node(ID,0,Empty,Empty,Empty,Color.red,total_time,executionTime);
		z.parent = last;
		if(last.isEmpty()){
			this.root = z;
		}
		if(last.ID > ID){
			last.left = z;
		}
		else{
			last.right = z;
		}
		return z;
	}

	// left rotate of a given node;	
	public void rotate_left(RB_Node x){
		RB_Node y = x.right;
		x.right = y.left;
		if(!y.left.isEmpty()){
			y.left.parent = x;
		}
		if(x.parent.isEmpty()){
			this.root = y;
			y.parent = x.parent;
		}
		else if(x == x.parent.left){
			x.parent.left = y;
			y.parent = x.parent;
		}
		else{
			x.parent.right = y;
			y.parent = x.parent;
		}
		y.left = x;
		x.parent = y;
			
	}
	//right rotate of a given node;
	public void rotate_right(RB_Node y){
		RB_Node x = y.left;
		y.left = x.right;
		if(!x.right.isEmpty()){
			x.right.parent = y;	
		}
		if(y.parent.isEmpty()){
			this.root = x;
			x.parent = y.parent;
		}
		else if(y == y.parent.left){
			y.parent.left = x;
			x.parent = y.parent;
		}
		else{
			y.parent.right = x;
			x.parent = y.parent;
		}
		x.right = y;
		y.parent = x;
	}

	
	//replace the two given nodes
	public void replace_RB(RB_Node u, RB_Node v){
		if(u.parent.isEmpty()){
			this.root = v;
		}
		else if(u == u.parent.left){
			u.parent.left = v;
		}
		else{
			u.parent.right = v;
		}
		v.parent = u.parent;
	}
	//deletion of node from RB_tree
	public void delete(RB_Node z){
		RB_Node y = z;
		RB_Node copy_y = y;
		RB_Node x;
		copy_y.color = y.color;
		if(z.left.isEmpty()){
			x = z.right;
			replace_RB(z,z.right);
		}
		else if(z.right.isEmpty()){
			x= z.left;
			replace_RB(z,z.left);
		}
		else{
			y = getMin(z.right);
		    x = y.right;
		    copy_y.color = y.color;
			replace_RB(y,y.right);
			replace_RB(z,y);
			y.left = z.left;
			y.left.parent = y;
			y.right = z.right;
			y.right.parent = y;
			y.color = z.color;
		}	
		if(copy_y.color == Color.black){
			rearrange(x);
		}
	}
	//After deletion, do the fixup to satisfy the property of red black tree
	public void rearrange(RB_Node x){
		while(x != this.root && x.color == Color.black){
			//x is left child
			if(x == x.parent.left){
				RB_Node w = x.parent.right;
				//when sibling of x is red
				if(w.color == Color.red){			
					w.color = Color.black;
					x.parent.color = Color.red;
					rotate_left(x.parent);
					w = x.parent.right;
				}
				if(w.left.color == Color.black  &&  w.right.color == Color.black){
					//when sibling of x is black, and both children of sibling are black
					w.color = Color.red;			
					x = x.parent;
				}
				else{
					//sibling of x is black and left child of sibling  is red and right child is black
					if(w.right.color == Color.black){
													
						w.left.color = Color.black;
						w.color = Color.red;
						rotate_right(w);
						w = x.parent.right;
					}
					//sibling of x is black and right child is black
					w.color = x.parent.color;		
					x.parent.color = Color.black;
					w.right.color = Color.black;
					rotate_left(x.parent);
					x = this.root;
						
				}
			}
			//x is the right child
			else if(x == x.parent.right){
				RB_Node w = x.parent.left;
				if(w.color == Color.red){
					w.color = Color.black;
					x.parent.color = Color.red;
					rotate_right(x.parent);
					w = x.parent.left;
				}
				if(w.left.color == Color.black  &&  w.right.color == Color.black){
					w.color = Color.red;
					x = x.parent;
				}
				else{ 
					if(w.left.color == Color.black){
						w.right.color = Color.black;
						w.color = Color.red;
						rotate_left(w);
						w = x.parent.left;
					}
					w.color = x.parent.color;
					x.parent.color = Color.black;
					w.left.color = Color.black;
					rotate_right(x.parent);
					x = this.root;
				}
						
			}
					
		}
			x.color = Color.black;
	}

}

	
