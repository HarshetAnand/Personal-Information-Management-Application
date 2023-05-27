// --== CS400 Fall 2022 File Header Information ==--
// Name: Harshet Anand
// Email: hanand2@wisc.edu
// Team: CF red team
// TA: Daniel Finer
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

public interface SortedCollectionInterface<T extends Comparable<T>> {

	public boolean insert(T data) throws NullPointerException, IllegalArgumentException;

	public boolean contains(T data);

	public int size();

	public boolean isEmpty();

}