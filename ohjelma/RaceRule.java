/**
 * RaceRule -luokka sisältää rodun tiedot
 * @author	Sami Saada
 * @version	%I%, %G%
 */

public class RaceRule implements Rule {

	/**
	 * <code>name</code> sisältää rodun nimen
	 */
	private String name;

	/**
	 * Luo uuden rodun nimen perusteella
	 */
	public RaceRule(String name) {
		this.name=name;
	}

	/**
	 * Luo uuden rodun toisen rodun pohjalta
	 * @param race	vanha rotu
	 */
	public RaceRule(RaceRule race) {
		this.name=race.getName();
	}

	/**
	 * Luo uuden rotusäännön rodun pohjalta
	 * @param race	rotu
	 */
	public RaceRule(Race race) {
		this.name=new String(race.getName());
	}

	/**
	 * Antaa rodun nimen
	 * @return	rodun nimi
	 */
	public String getName() {
		return new String(this.name);
	}

	/**
	 * Antaa rodun nimen
	 * @return	rodun nimi
	 */
	public String toString() {
		return this.getName();
	}

}
