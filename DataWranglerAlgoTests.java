// --== CS400 Fall 2022 File Header Information ==-- 
// Name: Harshet Anand
// Email: hanand2@wisc.edu
// Team: CF red team
// TA: Daniel Finer
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import static org.junit.Assert.assertEquals;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.List;
import org.junit.Test;

public class DataWranglerAlgoTests {

	/**
	 * This tester inserts new people into the tree and saves these as node in the
	 * xml file called person.xml which is used for when we search all the names.
	 */
	@Test
	public void CodeReviewOfDataWrangler1() {
		Storage<Person> rbt = new Storage<>();
		// Inserts users into the tree like nodes
		rbt.insert(new Person("Gary Dahl", "dahl@cs.wisc.edu", "111-111-1111", "01/01/1980"));
		rbt.insert(new Person("Ian Nilles", "inilles@wisc.edu", "608-999-9997", "03/22/2000"));
		rbt.insert(new Person("Harshet Anand", "hanand2@wisc.edu", "608-999-9998", "03/23/2000"));
		rbt.insert(new Person("Jane Doe", "jane@gmail.com", "608-999-9999", "03/21/2000"));
		rbt.saveNodes("person.xml");

		List<INode> list = rbt.loadNodes("person.xml");
		assertEquals("Gary Dahl", list.get(0).getName());
		assertEquals("Ian Nilles", list.get(1).getName());
		assertEquals("Harshet Anand", list.get(2).getName());
		assertEquals("Jane Doe", list.get(3).getName());
	}

	/**
	 * This tester tests if a new node can be inserted into the tree using the
	 * assertEquals import.
	 */
	@Test
	public void CodeReviewOfDataWrangler2() {
		RedBlackTree<Integer> rbt = new RedBlackTree<>();
		rbt.insert(4); // Inserts nodes into the tree

		assertEquals(1, rbt.root.blackHeight);
	}

	/**
	 * This tester tests if the new node inserted has red parents and that the aunt
	 * is red.
	 */
	@Test
	public void CodeReviewOfDataWrangler3() {
		RedBlackTree<Integer> rbt = new RedBlackTree<>();
		rbt.insert(4); // Inserts nodes into the tree
		rbt.insert(2);
		rbt.insert(9);
		rbt.insert(1);

		assertEquals(1, rbt.root.leftChild.blackHeight);
		assertEquals(1, rbt.root.rightChild.blackHeight);
		assertEquals(1, rbt.root.blackHeight);
		RedBlackTree.Node<Integer> node = rbt.root.leftChild.leftChild;
		assertEquals(1, node.data.intValue());
		assertEquals(0, node.blackHeight);
		assertEquals("[ 1, 2, 4, 9 ]", rbt.toInOrderString());
	}

	/**
	 * This tester tests if the new node inserted has parents that are black as they
	 * can not have red parents.
	 */
	@Test
	public void CodeReviewOfDataWrangler4() {
		RedBlackTree<Integer> rbt = new RedBlackTree<>();
		rbt.insert(4); // Inserts nodes into the tree
		rbt.insert(2);
		rbt.insert(9);

		assertEquals(1, rbt.root.blackHeight);
		assertEquals(0, rbt.root.leftChild.blackHeight);
		assertEquals(0, rbt.root.rightChild.blackHeight);
		assertEquals("[ 2, 4, 9 ]", rbt.toInOrderString());
	}
}