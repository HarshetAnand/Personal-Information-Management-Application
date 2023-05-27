// --== CS400 Fall 2022 File Header Information ==--
// Name: Harshet Anand
// Email: hanand2@wisc.edu
// Team: CF red team
// TA: Daniel Finer
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Stack;

/**
 * Red-Black Tree implementation with a Node inner class for representing the
 * nodes of the tree. Currently, this implements a Binary Search Tree that we
 * will turn into a red black tree by modifying the insert functionality. In
 * this activity, we will start with implementing rotations for the binary
 * search tree insert algorithm. You can use this class' insert method to build
 * a regular binary search tree, and its toString method to display a
 * level-order traversal of the tree.
 */
public class RedBlackTree<T extends Comparable<T>> {

	/**
	 * This class represents a node holding a single value within a binary tree the
	 * parent, left, and right child references are always maintained.
	 */
	protected static class Node<T> {
		public T data;
		public Node<T> parent; // null for root node
		public Node<T> leftChild;
		public Node<T> rightChild;
		public int blackHeight = 0;

		public Node(T data) {
			this.data = data;
		}

		/**
		 * @return true when this node has a parent and is the left child of that
		 *         parent, otherwise return false
		 */
		public boolean isLeftChild() {
			return parent != null && parent.leftChild == this;
		}

	}

	protected Node<T> root; // reference to root node of tree, null when empty
	protected int size = 0; // the number of values in the tree

	/**
	 * Performs a naive insertion into a binary search tree: adding the input data
	 * value to a new node in a leaf position within the tree. After this insertion,
	 * no attempt is made to restructure or balance the tree. This tree will not
	 * hold null references, nor duplicate data values.
	 * 
	 * @param data to be added into this binary search tree
	 * @return true if the value was inserted, false if not
	 * @throws NullPointerException     when the provided data argument is null
	 * @throws IllegalArgumentException when the newNode and subtree contain equal
	 *                                  data references
	 */
	public boolean insert(T data) throws NullPointerException, IllegalArgumentException {
		// null references cannot be stored within this tree
		if (data == null)
			throw new NullPointerException("This RedBlackTree cannot store null references.");

		Node<T> newNode = new Node<>(data);
		if (root == null) {
			root = newNode;
			size++;
			root.blackHeight = 1;
			return true;
		} // add first node to an empty tree
		else {
			boolean returnValue = insertHelper(newNode, root); // recursively insert into subtree
			if (returnValue)
				size++;
			else
				throw new IllegalArgumentException("This RedBlackTree already contains that value.");
			root.blackHeight = 1;
			return returnValue;
		}
	}

	/**
	 * Recursive helper method to find the subtree with a null reference in the
	 * position that the newNode should be inserted, and then extend this tree by
	 * the newNode in that position.
	 * 
	 * @param newNode is the new node that is being added to this tree
	 * @param subtree is the reference to a node within this tree which the newNode
	 *                should be inserted as a descenedent beneath
	 * @return true is the value was inserted in subtree, false if not
	 */
	private boolean insertHelper(Node<T> newNode, Node<T> subtree) {
		int compare = newNode.data.compareTo(subtree.data);
		// do not allow duplicate values to be stored within this tree
		if (compare == 0)
			return false;

		// store newNode within left subtree of subtree
		else if (compare < 0) {
			if (subtree.leftChild == null) { // left subtree empty, add here
				subtree.leftChild = newNode;
				newNode.parent = subtree;
				enforceRBTreePropertiesAfterInsert(newNode);
				return true;
				// otherwise continue recursive search for location to insert
			} else
				return insertHelper(newNode, subtree.leftChild);
		}

		// store newNode within the right subtree of subtree
		else {
			if (subtree.rightChild == null) { // right subtree empty, add here
				subtree.rightChild = newNode;
				newNode.parent = subtree;
				enforceRBTreePropertiesAfterInsert(newNode);
				return true;
				// otherwise continue recursive search for location to insert
			} else
				enforceRBTreePropertiesAfterInsert(newNode);
			return insertHelper(newNode, subtree.rightChild);
		}
	}

	/**
	 * This method is used to enforce the new nodes of the red-black tree properties
	 * after a new node is inserted into the tree.
	 * 
	 * @param node this node is used for when the new node is inserted
	 */
	protected void enforceRBTreePropertiesAfterInsert(Node<T> newNode) {
		if (newNode.parent == null) {
			if (newNode.blackHeight == 0) {
				newNode.blackHeight = 1;
				return;
			}
		}

		if (newNode.parent.parent == null) {
			newNode.parent.blackHeight = 1;
			return;
		}

		if (newNode.parent.blackHeight == 1) {
			return;
		}

		if (newNode.parent.data.compareTo(newNode.parent.parent.data) < 0) {

			if (newNode.parent.parent.rightChild != null && newNode.parent.parent.rightChild.blackHeight == 0) {
				newNode.parent.blackHeight = 1;
				newNode.parent.parent.blackHeight = 0;
				newNode.parent.parent.rightChild.blackHeight = 1;
				enforceRBTreePropertiesAfterInsert(newNode.parent.parent);

			} else {
				if (newNode.isLeftChild()) {
					Node<T> grandfather = newNode.parent.parent;
					rotate(newNode.parent, grandfather);
					newNode.parent.blackHeight = 1;
					grandfather.blackHeight = 0;
				}

				else {
					Node<T> parent = newNode.parent;
					rotate(newNode, parent);
					enforceRBTreePropertiesAfterInsert(parent);
				}
			}

		} else if (newNode.parent.data.compareTo(newNode.parent.parent.data) > 0) {
			// If uncle is red
			if (newNode.parent.parent.leftChild != null && newNode.parent.parent.leftChild.blackHeight == 0) {
				newNode.parent.blackHeight = 1;
				newNode.parent.parent.blackHeight = 0;
				newNode.parent.parent.leftChild.blackHeight = 1;

				enforceRBTreePropertiesAfterInsert(newNode.parent.parent);

			} else {

				if (!newNode.isLeftChild()) {
					Node<T> grandfather = newNode.parent.parent;
					rotate(newNode.parent, grandfather);
					newNode.parent.blackHeight = 1;
					grandfather.blackHeight = 0;
				}

				else {
					Node<T> parent = newNode.parent;
					rotate(newNode, parent);
					enforceRBTreePropertiesAfterInsert(parent);
				}
			}
		}
		return;
	}

	/**
	 * Performs the rotation operation on the provided nodes within this tree. When
	 * the provided child is a leftChild of the provided parent, this method will
	 * perform a right rotation. When the provided child is a rightChild of the
	 * provided parent, this method will perform a left rotation. When the provided
	 * nodes are not related in one of these ways, this method will throw an
	 * IllegalArgumentException.
	 * 
	 * @param child  is the node being rotated from child to parent position
	 *               (between these two node arguments)
	 * @param parent is the node being rotated from parent to child position
	 *               (between these two node arguments)
	 * @throws IllegalArgumentException when the provided child and parent node
	 *                                  references are not initially (pre-rotation)
	 *                                  related that way
	 */
	private void rotate(Node<T> child, Node<T> parent) throws IllegalArgumentException {
		if (child == null || parent == null) {
			throw new IllegalArgumentException("Error! Can not add node!");
		}
		// This set of nested if-loops performs a left rotation when the provided child
		// is a rightChild of the provided parent
		if (child == parent.rightChild) {
			parent.rightChild = child.leftChild;
			child.leftChild = parent;
			if (!(parent.parent == null)) {
				child.parent = parent.parent;
				if (parent.isLeftChild()) {
					parent.parent.leftChild = child;
				} else {
					parent.parent.rightChild = child;
				}
			} else {
				root = child;
			}
			// This set of nested if-loops performs a right rotation when the provided child
			// is a leftChild of the provided parent
		} else if (child == parent.leftChild) {
			parent.leftChild = child.rightChild;
			child.rightChild = parent;
			if (!(parent.parent == null)) {
				child.parent = parent.parent;
				if (parent.isLeftChild()) {
					parent.parent.leftChild = child;
				} else {
					parent.parent.rightChild = child;
				}
			} else {
				root = child;
			}
			// Returns IllegalArgumentException because the child and parent node references
			// are not related
		} else {
			throw new IllegalArgumentException(
					"Error! The provided child and parent " + "node references are not related!");
		}
	}

	/**
	 * Get the size of the tree (its number of nodes).
	 * 
	 * @return the number of nodes in the tree
	 */
	public int size() {
		return size;
	}

	/**
	 * Method to check if the tree is empty (does not contain any node).
	 * 
	 * @return true of this.size() return 0, false if this.size() > 0
	 */
	public boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * Checks whether the tree contains the value *data*.
	 * 
	 * @param data the data value to test for
	 * @return true if *data* is in the tree, false if it is not in the tree
	 */
	public boolean contains(T data) {
		// null references will not be stored within this tree
		if (data == null)
			throw new NullPointerException("This RedBlackTree cannot store null references.");
		return this.containsHelper(data, root);
	}

	/**
	 * Recursive helper method that recurses through the tree and looks for the
	 * value *data*.
	 * 
	 * @param data    the data value to look for
	 * @param subtree the subtree to search through
	 * @return true of the value is in the subtree, false if not
	 */
	private boolean containsHelper(T data, Node<T> subtree) {
		if (subtree == null) {
			// we are at a null child, value is not in tree
			return false;
		} else {
			int compare = data.compareTo(subtree.data);
			if (compare < 0) {
				// go left in the tree
				return containsHelper(data, subtree.leftChild);
			} else if (compare > 0) {
				// go right in the tree
				return containsHelper(data, subtree.rightChild);
			} else {
				// we found it :)
				return true;
			}
		}
	}

	/**
	 * This method performs an inorder traversal of the tree. The string
	 * representations of each data value within this tree are assembled into a
	 * comma separated string within brackets (similar to many implementations of
	 * java.util.Collection, like java.util.ArrayList, LinkedList, etc). Note that
	 * this RedBlackTree class implementation of toString generates an inorder
	 * traversal. The toString of the Node class class above produces a level order
	 * traversal of the nodes / values of the tree.
	 * 
	 * @return string containing the ordered values of this tree (in-order
	 *         traversal)
	 */
	public String toInOrderString() {
		// generate a string of all values of the tree in (ordered) in-order
		// traversal sequence
		StringBuffer sb = new StringBuffer();
		sb.append("[ ");
		sb.append(toInOrderStringHelper("", this.root));
		if (this.root != null) {
			sb.setLength(sb.length() - 2);
		}
		sb.append(" ]");
		return sb.toString();
	}

	private String toInOrderStringHelper(String str, Node<T> node) {
		if (node == null) {
			return str;
		}
		str = toInOrderStringHelper(str, node.leftChild);
		str += (node.data.toString() + ", ");
		str = toInOrderStringHelper(str, node.rightChild);
		return str;
	}

	/**
	 * This method performs a level order traversal of the tree rooted at the
	 * current node. The string representations of each data value within this tree
	 * are assembled into a comma separated string within brackets (similar to many
	 * implementations of java.util.Collection). Note that the Node's implementation
	 * of toString generates a level order traversal. The toString of the
	 * RedBlackTree class below produces an inorder traversal of the nodes / values
	 * of the tree. This method will be helpful as a helper for the debugging and
	 * testing of your rotation implementation.
	 * 
	 * @return string containing the values of this tree in level order
	 */
	public String toLevelOrderString() {
		String output = "[ ";
		if (this.root != null) {
			LinkedList<Node<T>> q = new LinkedList<>();
			q.add(this.root);
			while (!q.isEmpty()) {
				Node<T> next = q.removeFirst();
				if (next.leftChild != null)
					q.add(next.leftChild);
				if (next.rightChild != null)
					q.add(next.rightChild);
				output += next.data.toString();
				if (!q.isEmpty())
					output += ", ";
			}
		}
		return output + " ]";
	}

	public String toString() {
		return "level order: " + this.toLevelOrderString() + "\nin order: " + this.toInOrderString();
	}

	/**
	 * this method removes the node, given its data
	 *
	 * @param personRemove data of the node to be removed
	 */
	public T remove(T personRemove) {
		Node<T> toBeRemove = searchNode(personRemove, root);
		if (toBeRemove == null)
			return null;
		return removeHelper(toBeRemove);
	}

	/**
	 * Method to search for a required node
	 *
	 * @param similarData data of node to remove
	 * @param similarNode node being checked for
	 * @return the node that is similar the given data
	 */
	protected Node<T> searchNode(T similarData, Node<T> similarNode) {
		if (similarNode == null)
			return null;
		if (similarNode.data.compareTo(similarData) > 0) {
			if (similarNode.leftChild == null)
				return null;
			return searchNode(similarData, similarNode.leftChild);
		}
		if (similarNode.data.compareTo(similarData) < 0) {
			if (similarNode.rightChild == null)
				return null;
			return searchNode(similarData, similarNode.rightChild);
		}
		return similarNode;
	}

	/**
	 * Helper of remove method
	 *
	 * @param nodeRemove node to remove
	 * @return data of the node removed
	 */
	protected T removeHelper(Node<T> nodeRemove) {

		if (nodeRemove.leftChild == null && nodeRemove.rightChild == null) {
			if (nodeRemove == root) {
				root = null;
				this.size--;
				return nodeRemove.data;
			}
			if (nodeRemove.blackHeight == 0) {
				deleteNode(nodeRemove);
				this.size--;
				return nodeRemove.data;
			}
			nodeRemove.blackHeight = 2;
			resolveDoubleBlack(nodeRemove);
			deleteNode(nodeRemove);
			this.size--;
			return nodeRemove.data;
		}
		T data = nodeRemove.data;
		if (nodeRemove.rightChild == null) {
			nodeRemove.data = nodeRemove.leftChild.data;
			deleteNode(nodeRemove.leftChild);
			this.size--;
			return data;
		}
		if (nodeRemove.leftChild == null) {
			nodeRemove.data = nodeRemove.rightChild.data;
			deleteNode(nodeRemove.rightChild);
			this.size--;
			return data;
		}
		Node<T> successorOfNode = nodeRemove.rightChild;
		while (successorOfNode.leftChild != null) {
			successorOfNode = successorOfNode.leftChild;
		}
		nodeRemove.data = successorOfNode.data;
		removeHelper(successorOfNode);
		return data;
	}

	protected void deleteNode(Node<T> nodeRemove) {
		if (nodeRemove.isLeftChild()) {
			nodeRemove.parent.leftChild = null;
			return;
		}
		nodeRemove.parent.rightChild = null;
	}

	/**
	 * this method handles the resolution of the double black node
	 *
	 * @param doubleBlackNode reference to the double black node
	 */
	protected void resolveDoubleBlack(Node<T> doubleBlackNode) {
		Node<T> parent = doubleBlackNode.parent;
		Node<T> sibling;
		if (doubleBlackNode.isLeftChild())
			sibling = parent.rightChild;
		else
			sibling = parent.leftChild;
		if (sibling.blackHeight == 0) {
			rotate(sibling, parent);
			int var = parent.blackHeight;
			parent.blackHeight = sibling.blackHeight;
			sibling.blackHeight = var;
			resolveDoubleBlack(doubleBlackNode);
			return;
		}
		if ((sibling.leftChild == null || sibling.leftChild.blackHeight == 1)
				&& (sibling.rightChild == null || sibling.rightChild.blackHeight == 1)) {
			doubleBlackNode.blackHeight--;
			sibling.blackHeight--;
			parent.blackHeight++;
			if (parent.blackHeight == 1)
				return;
			if (parent == root) {
				root.blackHeight = 1;
				return;
			}
			resolveDoubleBlack(parent);
			return;
		}
		if (sibling.isLeftChild() && (sibling.leftChild == null || sibling.leftChild.blackHeight == 1)) {
			int var = sibling.blackHeight;
			sibling = sibling.parent;
			sibling.blackHeight = sibling.leftChild.blackHeight;
			sibling.leftChild.blackHeight = var;
			rotate(sibling.rightChild, sibling);
		} else if (!sibling.isLeftChild() && (sibling.rightChild == null || sibling.rightChild.blackHeight == 1)) {
			rotate(sibling.leftChild, sibling);
			sibling = sibling.parent;
			int var = sibling.blackHeight;
			sibling.blackHeight = sibling.rightChild.blackHeight;
			sibling.rightChild.blackHeight = var;
		}

		doubleBlackNode.blackHeight--;
		if (sibling.isLeftChild())
			sibling.leftChild.blackHeight++;
		else
			sibling.rightChild.blackHeight++;

		rotate(sibling, parent);
		int var = sibling.blackHeight;
		sibling.blackHeight = parent.blackHeight;
		parent.blackHeight = var;
	}

	/**
	 * Main method to run tests and insert new nodes.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
	}
}
