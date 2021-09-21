/** 
 * Note to TAs : I had to implement a doubly-linked list from scratch because the provided code for the singly-linked list wasn't compiling
 * and kept crashing on my machine.
 * @author Alae Boufarrachene
 */

class LinkList {

	private Node head, tail=null;
    private int value;
    private int size=0;

	public LinkList() { 
		this.size = size;
	}

	public void addToEnd(int value) { //Common method #1 : Adding an element to the end of the DLL

        Node addedNode = new Node(value);

        if (head==null) { //Edge case when the list is empty
            head = addedNode;
            size++;
        }

        else { //Base case when the list isn't empty
			Node curr = head;
			
            while (curr.nodeNext != null) { //Will travel through the list until it reachs its last element (the one with a null .next)
                curr = curr.nodeNext;
            }
            curr.nodeNext = addedNode; //Links the last node of the list with the newly-added one
            addedNode.nodePrevious = curr; //Links the newly-added node with the last one
            size++;
        }
    }

	public void print() { //Common method #2 : A printer method that displays all elements of the list

        Node current = head;  

        if (head==null) { //Edge case when the list is empty
            System.out.println("The Doubly-Linked List is empty !");  
            return;  
        }  

        System.out.println("Here are all the nodes of the Doubly-Linked List : ");  

        while (current != null) { //Base case when the list isn't empty
            System.out.print(current.nodeData + " ");  
            current = current.nodeNext;  
        }  
    }

	public void deleteLast() { 
        if (head==null) {  
            return;  
        }  
        else {
            if (head != tail) {  
                tail = tail.nodePrevious;  
                tail.nodeNext = null;  
            }  
            else {  
                head = tail = null;  
            }  
        }  
	}

	public static void main(String[] args) {
		LinkList testList = new LinkList();
		testList.addToEnd(69);
		testList.addToEnd(420);
		testList.addToEnd(666);
		System.out.println("-----List before removing last element-----");
		testList.print();
		testList.deleteLast();
		System.out.println("-----List after removing last element-----");
		testList.print();
	}
}
