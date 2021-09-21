/** 
 * Note to TAs : I had to implement a node from scratch because the provided code wasn't compiling on my machine
 * @author Alae Boufarrachene
 */

public class Node {

  int nodeData;  
  Node nodeNext;  
  Node nodePrevious;  
    
  public Node(int data) {  
      this.nodeData = data;  
      this.nodeNext = null;
      this.nodePrevious = null;
  }  

}  