/**
 * AGException -luokka sisältää virheet
 * @author	Sami Saada
 * @version	%I%, %G%
 */

import java.io.*;

public class AGException extends Exception {

	private String errorMessage;

	public AGException() {
		super();
		this.errorMessage="unknown";
	}

	public AGException(String error) {
		super(error);
		this.errorMessage=new String(error);
	}

	public AGException(Exception e) {
		this(e.toString());
	}

	public String toString() {
		return new String(this.errorMessage);
	}

}

class NoAvatarRuleSetException extends AGException {

	public NoAvatarRuleSetException() {
		super("You don't have any avatarrule yet.");
	}

}

class NoUTF8Exception extends AGException {

	public NoUTF8Exception() {
		super("You don't have UTF-8 ?!");
	}

}

class RuleException extends AGException {

	public RuleException() {
		super("Not allowed, because of the rules.");
	}

}
