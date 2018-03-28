/**
 * Skill -luokka sisältää taidon tiedot
 * @author	Sami Saada
 * @version	%I%, %G%
 */
public class Skill {

	/**
	 * <code>name</code> sisältää taidon nimen
	 * <code>value</code> sisältää taidon arvon
	 */
	private String name;
	private int value;

	/**
	 * Luo uuden taidon
	 * @param name	taidon nimi
	 * @param value	taidon arvo
	 */
	public Skill(String name, int value) {
		this.name=name;
		this.value=value;
	}

	/**
	 * Palauttaa taidon nimen
	 * @return	taidon nimen
	 */
	public String getName() {
		return new String(this.name);
	}

	/**
	 * Palauttaa taidon arvon
	 * @return	taidon arvo
	 */
	public int getValue() {
		return new Integer(this.value);
	}

	/**
	 * Palauttaa taidon tiedot
	 * @return	<code>name</code>: <code>value</code>
	 */
	public String toString() {
		return getName()+": "+getValue();
	}

}
