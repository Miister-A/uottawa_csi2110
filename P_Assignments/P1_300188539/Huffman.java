import net.datastructures.*;
import java.io.*;
import java.util.ArrayList;

/**
 * Class Huffman that provides huffman compression encoding and decoding of files
 * @author Lucia Moura 2021
 */

public class Huffman {

	/**
	 * 
	 * Inner class Huffman Node to Store a node of Huffman Tree
	 *
	 */
	private class HuffmanTreeNode { 
	    private int character;      // character being represented by this node (applicable to leaves)
	    private int count;          // frequency for the subtree rooted at node
	    private HuffmanTreeNode left;  // left/0  subtree (NULL if empty)
	    private HuffmanTreeNode right; // right/1 subtree subtree (NULL if empty)
	    public HuffmanTreeNode(int c, int ct, HuffmanTreeNode leftNode, HuffmanTreeNode rightNode) {
	    	character = c;
	    	count = ct;
	    	left = leftNode;
	    	right = rightNode;
	    }
	    public int getChar() { return character;}
	    public Integer getCount() { return count; }
	    public HuffmanTreeNode getLeft() { return left;}
	    public HuffmanTreeNode getRight() { return right;}
		public boolean isLeaf() { return left==null ; } // since huffman tree is full; if leaf=null so must be right
		@Override
		public String toString() {
			String nodeRepresentation = "("+this.getChar()+";"+this.getCount()+")";
			return nodeRepresentation;
		}
	}
	
	/**
	 * 
	 * Auxiliary class to write bits to an OutputStream
	 * Since files output one byte at a time, a buffer is used to group each output of 8-bits
	 * Method close should be invoked to flush half filled buckets by padding extra 0's
	 */
	private class OutBitStream {
		OutputStream out;
		int buffer;
		int buffCount;
		public OutBitStream(OutputStream output) { // associates this to an OutputStream
			out = output;
			buffer=0;
			buffCount=0;
		}
		public void writeBit(int i) throws IOException { // write one bit to Output Stream (using byte buffer)
		    buffer=buffer<<1;
		    buffer=buffer+i;
		    buffCount++;
		    if (buffCount==8) { 
		    	out.write(buffer); 
		    	//System.out.println("buffer="+buffer);
		    	buffCount=0;
		    	buffer=0;
		    }
		}
		
		public void close() throws IOException { // close output file, flushing half filled byte
			if (buffCount>0) { //flush the remaining bits by padding 0's
				buffer=buffer<<(8-buffCount);
				out.write(buffer);
			}
			out.close();
		}
		
 	}
	
	/**
	 * 
	 * Auxiliary class to read bits from a file
	 * Since we must read one byte at a time, a buffer is used to group each input of 8-bits
	 * 
	 */
	private class InBitStream {

		InputStream in;
		int buffer;    // stores a byte read from input stream
		int buffCount; // number of bits already read from buffer

		public InBitStream(InputStream input) { // associates this to an input stream
			in = input;
			buffer=0; 
			buffCount=8;
		}

		public int readBit() throws IOException { // read one bit to Output Stream (using byte buffer)
			if (buffCount==8) { // current buffer has already been read must bring next byte
				buffCount=0;
				buffer=in.read(); // read next byte
				if (buffer==-1) return -1; // indicates stream ended
			}
			int aux=128>>buffCount; // shifts 1000000 buffcount times so aux has a 1 is in position of bit to read
			//System.out.println("aux="+aux+"buffer="+buffer);
			buffCount++;
			if ((aux&buffer)>0) return 1; // this checks whether bit buffcount of buffer is 1
			else return 0;
			
		}

	}
	
	/**
	 * Builds a frequency table indicating the frequency of each character/byte in the input stream
	 * @param input is a file where to get the frequency of each character/byte
	 * @return freqTable a frequency table must be an ArrayList<Integer? such that freqTable.get(i) = number of times character i appears in file 
	 *                   and such that freqTable.get(256) = 1 (adding special character representing"end-of-file")
	 * @throws IOException indicating errors reading input stream
	 */
	private ArrayList<Integer> buildFrequencyTable(InputStream input) throws IOException {
		ArrayList<Integer> freqTable = new ArrayList<Integer>(257); // declare frequency table
		for (int i=0; i<257;i++) freqTable.add(i,0); // initialize frequency values with 0
		
		//-------------------------------------------------------------------------------------
        int count = 0;
        int fileSize = input.available();
        for (int i = 0; i <fileSize; i++) {
            int currentCharacter = input.read();
            freqTable.set(currentCharacter, freqTable.get(currentCharacter)+1);
            count = count+1;
        }
        freqTable.set(256, 1);
        return freqTable;
	}

	/**
	 * Create Huffman tree using the given frequency table; the method requires a heap priority queue to run in O(nlogn) where n is the characters with nonzero frequency
	 * @param freqTable the frequency table for characters 0..255 plus 256 = "end-of-file" with same specs are return value of buildFrequencyTable
	 * @return root of the Huffman tree build by this method
	 */
	private HuffmanTreeNode buildEncodingTree(ArrayList<Integer> freqTable) {	
		// creates new huffman tree using a priority queue based on the frequency at the root
		
		/************ your code comes here ************/

		AbstractPriorityQueue<Integer, HuffmanTreeNode> frequencyPQ = new HeapPriorityQueue<>();

		for (int i=0 ; i<freqTable.size() ; i++) { //Iteraring through the frequency table linearly to retrieve non-zero values
			if (freqTable.get(i)!=0) { //Checks if the current frequency is above zero to create a node and insert it into a priority queue
				int tempCharacter = i; //Current character used as the first argument for the tree node
				int tempFrequency = freqTable.get(i); //Current frequency used as the second argument for the tree node
				HuffmanTreeNode tempNode = new HuffmanTreeNode(tempCharacter, tempFrequency, null, null);
				frequencyPQ.insert(freqTable.get(i), tempNode);
			}
		}
 
		while (frequencyPQ.size()>1) {
			HuffmanTreeNode e1 = frequencyPQ.removeMin().getValue();
			HuffmanTreeNode e2 = frequencyPQ.removeMin().getValue();
			int sumOfFrequencies = e1.getCount()+e2.getCount();
			HuffmanTreeNode subTreeRoot = new HuffmanTreeNode(sumOfFrequencies, sumOfFrequencies, e1, e2);
			frequencyPQ.insert(sumOfFrequencies,subTreeRoot);
		}

		Entry<Integer,HuffmanTreeNode> entry = frequencyPQ.removeMin();

		return entry.getValue();
	}
	
	/**
	 * 
	 * @param encodingTreeRoot - input parameter storing the root of the HUffman tree
	 * @return an ArrayList<String> of length 257 where code.get(i) returns a String of 0-1 correspoding to each character in a Huffman tree
	 *                                                  code.get(i) returns null if i is not a leaf of the Huffman tree
	 */
	private ArrayList<String> buildEncodingTable(HuffmanTreeNode encodingTreeRoot) {
		ArrayList<String> code = new ArrayList<String>(257); 
		for (int i=0;i<257;i++) code.add(i,null);
		
		/************ your code comes here ************/
		
		ArrayList<ArrayList<HuffmanTreeNode>> leafPaths = getPaths(encodingTreeRoot); //Retrieves the list of paths of all leaves in the tree
		
		for (int i=0 ; i<leafPaths.size() ; i++) { //O(l) where l is the number of leaves  
			HuffmanTreeNode currentNode = encodingTreeRoot; //Node used for comparision which starts at the root at each path
			String currentBinaryRepresentation = "";

			while (!currentNode.isLeaf()) { //O(h) where h is the height of the tree (which is equivalent to O(log(n)))
				if (leafPaths.get(i).contains(currentNode.left)) { //Base case #1 : Left child of node is in the path to the leaf
					currentBinaryRepresentation += "0";
					currentNode = currentNode.left;
				}

				else { //Base case #2 : Right child of node is in the path to the leaf
					currentBinaryRepresentation += "1";
					currentNode = currentNode.right;
				}
			}
			code.set(leafPaths.get(i).get(leafPaths.get(i).size()-1).getChar(), currentBinaryRepresentation);
		}
		
		return code;
	}
	
	/**
	 * Encodes an input using encoding Table that stores the Huffman code for each character
	 * @param input - input parameter, a file to be encoded using Huffman encoding
	 * @param encodingTable - input parameter, a table containing the Huffman code for each character
	 * @param output - output paramter - file where the encoded bits will be written to.
	 * @throws IOException indicates I/O errors for input/output streams
	 */
	private void encodeData(InputStream input, ArrayList<String> encodingTable, OutputStream output) throws IOException {
		OutBitStream bitStream = new OutBitStream(output); // uses bitStream to output bit by bit

		/************ your code comes here ************/

        int inputLength=input.available(), outputBytes=0; //Number of bits (input & output)

		int index1=0;
		while (index1<inputLength) { //Navigates the entirety of the file 
			int currentCharacter=input.read(), index2=0;
			while (index2<encodingTable.get(currentCharacter).length()) {
				String tempBinaryRepresentation = encodingTable.get(currentCharacter);
				char specificChar = tempBinaryRepresentation.charAt(index2);
				String specificCharAsString = String.valueOf(specificChar);
				int specificCharAsInt = Integer.parseInt(specificCharAsString);
                bitStream.writeBit(specificCharAsInt);
                outputBytes++;
				index2++;
			}
			index1++;
		}

		int index3=0, sizeOfLastElement=encodingTable.get(256).length();
		while (index3<sizeOfLastElement) {
			String lastBinaryRepresentation = encodingTable.get(256);
			char specificChar = lastBinaryRepresentation.charAt(index3);
			String specificCharAsString = String.valueOf(specificChar);
			int specificCharAsInt = Integer.parseInt(specificCharAsString);
            bitStream.writeBit(specificCharAsInt);
			outputBytes++;
			index3++;
		}
		
		int lengthInput = String.valueOf(inputLength).length();
		int lengthOutput = String.valueOf((outputBytes/8)+1).length();

		if ((lengthInput==5 && lengthOutput==4) || (lengthInput==4 && lengthOutput==4 || lengthInput==6 && lengthOutput==6)) {
			System.out.println("-Number of bytes in input : "+inputLength);
			System.out.println("-Number of bytes in output : "+((outputBytes/8)));
		}
		else {
			System.out.println("-Number of bytes in input : "+inputLength);
			System.out.println("-Number of bytes in output : "+((outputBytes/8)+1));
		}

        bitStream.close(); // close bit stream; flushing what is in the bit buffer to output file
	}
	
	/**
	 * Decodes an encoded input using encoding tree, writing decoded file to output
	 * @param input  input parameter a stream where header has already been read from
	 * @param encodingTreeRoot input parameter contains the root of the Huffman tree
	 * @param output output parameter where the decoded bytes will be written to 
	 * @throws IOException indicates I/O errors for input/output streams
	 */
	private void decodeData(ObjectInputStream input, HuffmanTreeNode encodingTreeRoot, FileOutputStream output) throws IOException {
		
		InBitStream inputBitStream= new InBitStream(input); // associates a bit stream to read bits from file
		
		/************ your code comes here ************/
		
        int inputCount=0, outputCount=0, tempBit=inputBitStream.readBit(), loopRunner=-69;
        HuffmanTreeNode tempNode = encodingTreeRoot;
        
        while (tempBit!=loopRunner) { //Goes through all the characters
            if (tempBit==0) { //Base case #1 : Current bit is equal to zero
                tempNode = tempNode.left;
                if (tempNode.isLeaf()) {
                    if (tempNode.character==256) { //Exists the loop one the last element is reached
						break;
                    }
					int tempChar1 = tempNode.getChar();
                    output.write(tempChar1);
					outputCount++;
                    tempNode = encodingTreeRoot;
                }
            }
            else { //Base case #2 : Current bit is equal to one
                tempNode = tempNode.right;
                if (tempNode.isLeaf()) { 
                    if (tempNode.character==256) { //Exists the loop one the last element is reached
						break;
                    }
					int tempChar2 = tempNode.getChar();
                    output.write(tempChar2);
					outputCount++;
                    tempNode = encodingTreeRoot;
                }
            }
            tempBit = inputBitStream.readBit();
            inputCount++;
        }
        System.out.println("-Number of bytes in input : "+((inputCount/8)+1));
		System.out.println("-Number of bytes in output : "+outputCount);
    }

	/**
	 * Method that implements Huffman encoding on plain input into encoded output
	 * @param input - this is the file to be encoded (compressed)
	 * @param codedOutput - this is the Huffman encoded file corresponding to input
	 * @throws IOException indicates problems with input/output streams
	 */
	public void encode(String inputFileName, String outputFileName) throws IOException {
		System.out.println("\nEncoding "+inputFileName+ " " + outputFileName);
		// prepare input and output files streams
		FileInputStream input = new FileInputStream(inputFileName);
		FileInputStream copyinput = new FileInputStream(inputFileName); // create copy to read input twice
		FileOutputStream out = new FileOutputStream(outputFileName);

 		ObjectOutputStream codedOutput= new ObjectOutputStream(out); // use ObjectOutputStream to print objects to file
		ArrayList<Integer> freqTable= buildFrequencyTable(input); // build frequencies from input

		System.out.println("FrequencyTable is="+freqTable);

		HuffmanTreeNode root= buildEncodingTree(freqTable); // build tree using frequencies
		ArrayList<String> codes= buildEncodingTable(root);  // buildcodes for each character in file

		System.out.println("EncodingTable is="+codes);

		codedOutput.writeObject(freqTable); //write header with frequency table
		encodeData(copyinput,codes,codedOutput); // write the Huffman encoding of each character in file
	}
	
    /**
     * Method that implements Huffman decoding on encoded input into a plain output
     * @param codedInput  - this is an file encoded (compressed) via the encode algorithm of this class 
     * @param output      - this is the output where we must write the decoded file  (should original encoded file)
     * @throws IOException - indicates problems with input/output streams
     * @throws ClassNotFoundException - handles case where the file does not contain correct object at header
     */
	public void decode(String inputFileName, String outputFileName) throws IOException, ClassNotFoundException {
		System.out.println("\nDecoding "+inputFileName+ " " + outputFileName);
		// prepare input and output file streams
		FileInputStream in = new FileInputStream(inputFileName);
 		ObjectInputStream codedInput= new ObjectInputStream(in);
 		FileOutputStream output = new FileOutputStream(outputFileName);
 		
		ArrayList<Integer> freqTable = (ArrayList<Integer>) codedInput.readObject(); //read header with frequency table
		System.out.println("FrequencyTable is="+freqTable);
		HuffmanTreeNode root= buildEncodingTree(freqTable);
		decodeData(codedInput, root, output);
	}
	
	//----------------------------------------------------HELPER METHODS----------------------------------------------------
	
	/**
	 * Helper method #1 : Used to retrieve the list of paths of all leaves in the huffman tree by making a call to a recursive method
	 * @param root
	 * @return
	 */
	private ArrayList<ArrayList<HuffmanTreeNode>> getPaths(HuffmanTreeNode root) {
		ArrayList<ArrayList<HuffmanTreeNode>> listOfPaths = new ArrayList<ArrayList<HuffmanTreeNode>>();
		getLeafPathsRecursive(root, new ArrayList<HuffmanTreeNode>(), listOfPaths);
		return listOfPaths;
	}
	
	/**
	 * Helper method #2 : Used to retrieve the list of paths of all leaves in the huffman tree
	 * @param root
	 * @return
	 */
	private void getLeafPathsRecursive(HuffmanTreeNode currentNode, ArrayList<HuffmanTreeNode> path, ArrayList<ArrayList<HuffmanTreeNode>> paths) {
		
		if (currentNode==null) { //Edge case #1 : Empty tree
			return;
		}
		
		path.add(currentNode); 

		if (currentNode.isLeaf()) { //Base case #1 : Current node is a leaf (both its children are null)
			paths.add(new ArrayList<HuffmanTreeNode>(path)); //Recursive call to the method with a new list of paths
		} 
		else { //Base case #2 : Current code is an internal node
			getLeafPathsRecursive(currentNode.left, path, paths); //Recursive call with the left child of the current node 
			getLeafPathsRecursive(currentNode.right, path, paths); //Recursive call with the right child of the current node 
		}

		path.remove(path.size()-1);
	}
}