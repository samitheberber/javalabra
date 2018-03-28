/**
 * AttributeRule -luokka sisältää omainaisuuden tiedot
 * @author	Sami Saada
 * @version	%I%, %G%
 */
public class AttributeRule implements Rule {

	/**
	 * <code>name</code> sisältää ominaisuuden nimen
	 */
	private String name;

	/**
	 * Luo uuden ominaisuuden
	 * @param name	omainaisuuden nimi
	 */
	public AttributeRule(String name) {
		this.name=name;
	}

	/**
	 * Palauttaa ominaisuuden nimen
	 * @return	<code>name</code> uutena <code>String</code>-elementtinä
	 */
	public String getName() {
		return new String(name);
	}

	/**
	 * Palauttaa ominaisuuden tiedot
	 * @return	<code>name</code>
	 */
	public String toString() {
		return getName();
	}

}
