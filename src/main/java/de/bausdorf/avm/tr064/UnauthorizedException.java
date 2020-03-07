package de.bausdorf.avm.tr064;

public class UnauthorizedException extends Exception {
	private static final long serialVersionUID = 1L;


	public UnauthorizedException(Exception cause) {
		super (cause);
	}
}
