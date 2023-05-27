// --== CS400 Fall 2022 File Header Information ==--
// Name: Harshet Anand
// Email: hanand2@wisc.edu
// Team: CF red team
// TA: Daniel Finer
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Validator implements IValidator {

	private boolean email;
	private boolean phoneNumber;
	private boolean dateOfBirth;

	public Validator(boolean email, boolean phoneNumber, boolean dateOfBirth) {
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.dateOfBirth = dateOfBirth;
	}

	public Validator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean validateEmail(String email) {
		String email1 = "";
		if (email1.contains("@wisc.edu") && email1.indexOf(0) != '@') {
			return true;
		}
		return false;
	}

	@Override
	public boolean validatePhoneNumber(String phoneNumber) {
		String format = "\\d{3}-\\d{3}-\\d{4}";
		if (phoneNumber.length() != 12 || phoneNumber != format) {
			return false;
		}
		return true;
	}

	@Override
	public boolean validateDateOfBirth(String dateOfBirth) {
		{
			if (dateOfBirth.trim().equals("")) {
				return true;
			} else {
				SimpleDateFormat format = new SimpleDateFormat("MM/DD/YYYY");
				format.setLenient(false);
				try {
					Date userDate = format.parse(dateOfBirth);
					System.out.println(dateOfBirth + " is valid");
				} catch (ParseException e) {
					System.out.println(dateOfBirth + " is invalid");
					return false;
				}
				return true;
			}
		}
	}

	public Address validateEmail(Validator address) {
		return null;
	}

	public Phone validatePhoneNumber(Validator number) {
		return null;
	}

	public Birth validateDateOfBirth(Validator date) {
		return null;
	}


}
