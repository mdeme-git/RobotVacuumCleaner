package Solver;

import java.io.Serializable;

public class Action implements Serializable {

	private String name;

	public Piece piece;

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public Action(String name) {
		this.name = name;
	}

	/**
	 * Returns the value of the name attribute.
	 * 
	 * @return the value of the name attribute.
	 */
	public String getName() {
		return name;
	}

	public String toString() {
		return name;
	}

	/**
	 * 
	 * @return true if the action isn't possible
	 */

	public boolean isNoOp() {
		return false;
	}

}
