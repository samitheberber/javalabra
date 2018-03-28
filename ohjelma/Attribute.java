/**
 * Attribute -luokka sisältää omainaisuuden tiedot
 * @author	Sami Saada
 * @version	%I%, %G%
 */

import java.util.ArrayList;

public class Attribute {

	/**
	 * <code>name</code> sisältää ominaisuuden nimen
	 * <code>value</code> sisältää ominaisuuden arvon
	 */
	private String name;
	private int value;

	/**
	 * Luo uuden ominaisuuden
	 * @param name	omainaisuuden nimi
	 * @param value	omainaisuuden arvo
	 */
	public Attribute(String name, int value) {
		this.name=name;
		this.value=value;
	}

	/**
	 * Palauttaa ominaisuuden nimen
	 * @return	<code>name</code> uutena <code>String</code>-elementtinä
	 */
	public String getName() {
		return new String(name);
	}

	/**
	 * Palauttaa ominaisuuden arvon
	 * @return	<code>value</code> uutena <code>Integer</code>-elementtinä
	 */
	public int getValue() {
		return new Integer(value);
	}

	/**
	 * Vaihtaa ominaisuuden arvon
	 * @param value	uusi arvo
	 */
	public void setValue(int value) {
		this.value=value;
	}

	/**
	 * Palauttaa ominaisuuden tiedot
	 * @return	<code>name</code>: <code>value</code>
	 */
	public String toString() {
		return getName()+": "+getValue();
	}

}
