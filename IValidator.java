// --== CS400 Fall 2022 File Header Information ==--
// Name: Harshet Anand
// Email: hanand2@wisc.edu
// Team: CF red team
// TA: Daniel Finer
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

public interface IValidator {

	/**
	 * Checks whether the given email is valid
	 *
	 * @param email the email given
	 * @return true if the email is valid, false otherwise
	 */
	public boolean validateEmail(String email);

	/**
	 * Checks whether the given phone number is valid
	 *
	 * @param phoneNumber the phone number given
	 * @return true if the phone number is valid, false otherwise
	 */
	public boolean validatePhoneNumber(String phoneNumber);

	/**
	 * Checks whether the given date of birth is valid
	 *
	 * @param dateOfBirth the date of birth given
	 * @return true if the date of birth is valid, false otherwise
	 */
	public boolean validateDateOfBirth(String dateOfBirth);
}