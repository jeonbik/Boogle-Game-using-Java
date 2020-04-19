import java.util.ArrayList;
import java.util.List;

class Node{
    char data;
    boolean visited;
    List<Node> neighbours;

    Node(char data)
		{
			this.data=data;
			this.neighbours=new ArrayList<>();
 
		}
	public void addneighbours(Node neighbourNode)
		{
			if(!this.getNeighbours().contains(neighbourNode))
            	this.neighbours.add(neighbourNode);
		}
		public List<Node> getNeighbours() {
			return neighbours;
		}
}