/**
 * Race -luokka sisältää rodun tiedot
 * @author	Sami Saada
 * @version	%I%, %G%
 */

public class Race {

	/**
	 * <code>name</code> sisältää rodun nimen
	 */
	private String name;

	/**
	 * Luo tyhjän rodun
	 */
	public Race() {
		this.name=null;
	}

	/**
	 * Luo uuden rodun nimen perusteella
	 */
	public Race(String name) {
		this.name=name;
	}

	/**
	 * Luo uuden rodun toisen rodun pohjalta
	 * @param race	vanha rotu
	 */
	public Race(Race race) {
		this.name=race.getName();
	}

	/**
	 * Antaa rodun nimen
	 * @return	rodun nimi
	 */
	public String getName() {
		if (this.name == null)
			return "N/A";
		else
			return new String(this.name);
	}

	/**
	 * Asettaa uuden rodun
	 * @param newRace	haluttu rotu
	 * @param races		kaikki mahdolliset rodut
	 * @return		vaihdon onnistuessa <code>true</code>, muuten <code>false</code>
	 */
	public boolean setRace(Race newRace, AvatarRule rule) {
		if (rule.searchRace(newRace.getName()) != null) { /* Tarkistus, että rotu on olemassa */
			this.name=newRace.getName(); //Jos on olemassa
			return true;
		}
		else
			return false;
	}

	/**
	 * Antaa rodun nimen
	 * @return	rodun nimi
	 */
	public String toString() {
		return this.getName();
	}

}
