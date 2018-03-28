/**
 * SkillRule -luokka sisältää taidon tiedot
 * @author	Sami Saada
 * @version	%I%, %G%
 */
public class SkillRule implements Rule {

	/**
	 * <code>name</code> sisältää taidon nimen
	 */
	private String name;

	/**
	 * Luo uuden taidon
	 * @param name	taidon nimi
	 */
	public SkillRule(String name) {
		this.name=name;
	}

	/**
	 * Palauttaa taidon nimen
	 * @return	taidon nimen
	 */
	public String getName() {
		return new String(this.name);
	}

	/**
	 * Palauttaa taidon tiedot
	 * @return	<code>name</code>
	 */
	public String toString() {
		return getName();
	}

}
