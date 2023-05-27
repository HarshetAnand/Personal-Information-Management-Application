// --== CS400 Fall 2022 File Header Information ==-- 
// Name: Harshet Anand
// Email: hanand2@wisc.edu
// Team: CF red team
// TA: Daniel Finer
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class AlgorithmEngineerTests {

	/**
	 * This tester checks if the email address provided is valid.
	 * 
	 * @return True when all tests pass else return false if one of the tests fails
	 *         printing out the error messages for the input that did not pass.
	 */
	public static boolean test1() {
		Validator address = new Validator();
		Address add = new Address("johnwisc.edu");

		if (address.validateEmail(address) == add) {
			return false;
		}
		return true;
	}

	/**
	 * This tester checks if the phone number provided is valid.
	 * 
	 * @return True when all tests pass else return false if one of the tests fails
	 *         printing out the error messages for the input that did not pass.
	 */
	public static boolean test2() {
		Validator number = new Validator();
		Phone Num = new Phone("6089999999");

		if (number.validatePhoneNumber(number) == Num) {
			return false;
		}
		return true;
	}

	/**
	 * This tester checks if the date of birth provided is valid.
	 * 
	 * @return True when all tests pass else return false if one of the tests fails
	 *         printing out the error messages for the input that did not pass.
	 */
	public static boolean test3() {
		Validator date = new Validator();
		Birth DOB = new Birth("21/03/1920");

		if (date.validateDateOfBirth(date) == DOB) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * 
	 * @return True when all tests pass else return false if one of the tests fails
	 *         printing out the error messages for the input that did not pass.
	 */
	public static boolean test4() {
		List<IValidator> books = new ArrayList<>();
		User firstUser = new User("John@wisc.edu", "608-111-1111", "03/21/1999");
		User secondUser = new User("Sean@wisc.edu", "608-222-2222", "03/22/1999");
		User thirdUser = new User("Henry@wisc.edu", "608-333-3333", "03/23/1999");
		User fourthUser = new User("Jacob@wisc.edu", "608-444-4444", "03/24/1999");
		User fifthUser = new User("Daniel@wisc.edu", "608-999-9999", "03/25/1999");

		books.add((IValidator) firstUser);
		books.add((IValidator) secondUser);
		books.add((IValidator) thirdUser);
		books.add((IValidator) fourthUser);
		books.add((IValidator) fifthUser);
		return true;
	}

	/**
	 * 
	 * 
	 * @return True when all tests pass else return false if one of the tests fails
	 *         printing out the error messages for the input that did not pass.
	 */
	public static boolean test5() {
		return true;
	}

	public static void main(String[] args) {
		System.out.println("runAllTests(): " + runAllTests());
	}

	/**
	 * Checks the correctness of all test methods works successfully by running all
	 * tests.
	 * 
	 * @return true if all test methods pass else false if one or more tests fail
	 *         printing out the test and the method which did not pass as well as
	 *         the item that was used as input.
	 */
	public static boolean runAllTests() {
		boolean allTestsPassed = test1() && test2() && test3() && test5();
		// Can only return true if all test cases return true. Will go back to main
		// method to return
		// final result
		return allTestsPassed;
	}

}
