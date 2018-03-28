/**
 * Avatar -luokka sisältää hahmon kaikki tiedot
 * @author	Sami Saada
 * @version	%I%, %G%
 */

import java.util.*;

public class Avatar {

	/**
	 * <code>attributes</code> sisältää kaikki ominaisuudet, mitä hahmolla on
	 * <code>skills</code> sisältää kaikki taidot, mitä hahmolla on
	 * <code>info</code> sisältää hahmon perustiedot
	 */
	private ArrayList<Attribute> attributes;
	private ArrayList<Skill> skills;
	private Information info;
	private AvatarRule rule;
	private String avatarpath;

	/**
	 * Luo uuden hahmon
	 */
	public Avatar(AvatarRule rule) {
		this.attributes=new ArrayList<Attribute>();
		this.skills=new ArrayList<Skill>();
		this.info=new Information();
		this.rule=rule;
		this.avatarpath=null;
		resetAttributes();
	}

	/**
	 * Alustaa ominaisuudet
	 */
	private void resetAttributes() {
		for (int i=0; i<this.rule.getArrayAttributes().size(); ++i) {
			this.attributes.add(new Attribute(this.rule.getArrayAttributes().get(i).getName(), 0));
		}
	}

	/**
	 * Luo uuden hahmon valmiin hahmon tiedoista
	 * @param attributes	sisältää lisättävät ominaisuudet
	 * @param skills	sisältää lisättävät taidot
	 * @param info		sisältää lisättävät perustiedot
	 */
	public Avatar(ArrayList<Attribute> attributes, ArrayList<Skill> skills, Information info, AvatarRule rule, String avatarpath) {
		this.attributes=attributes;
		this.skills=skills;
		this.info=info;
		this.rule=rule;
		this.avatarpath=avatarpath;
	}

	/**
	 * Muuttaa ominaisuuden arvoa
	 * @param nro	ominaisuuden numero
	 * @param value	ominaisuuden arvo
	 */
	public boolean setAttribute(int nro, int value) {
		try {
			Attribute attribute=attributes.get(nro);
			attribute.setValue(value);
			if (attributes.set(nro, attribute) != null)
				return true;
			else
				return false;
		}
		catch(ArrayIndexOutOfBoundsException e) {
			return false;
		}
	}

	/**
	 * Lisää uuden taidon
	 * @param item	lisättävä taito
	 * @return	<code>true</code> onnistuessa, muuten <code>false</code>
	 */
	public boolean addSkill(Skill item) {
		if (this.rule.searchSkill(item.getName()) != null) {
			this.skills.add(item);
			return true;
		}
		else
			return false;
	}

	/**
	 * Hakee taidon nimen perusteella
	 * @param name	hakusana
	 * @return	palauttaa löytyessään viitteen taitoon, muutoin <code>null</code>
	 */
	public Skill searchSkill(String name) {
		for (int i=0; i<this.skills.size(); ++i) {
			if (this.skills.get(i).getName().equals(name))
				return this.skills.get(i);
		}
		return null;
	}

	/**
	 * Poistaa taidon
	 * @param item	taito
	 * @return	<code>true</code> onnisutessa, muuten <code>false</code>
	 */
	public boolean removeSkill(Skill item) {
		return this.skills.remove(item);
	}

	/**
	 * Muuta hahmon nimeä
	 * @param name	uusi nimi
	 * @return	<code>true</code> onnistuessa, muuten <code>false</code>
	 */
	public boolean changeName(String name) {
		return this.info.setName(name);
	}

	/**
	 * Muuta pelaajan nimeä
	 * @param name	uusi nimi
	 * @return	<code>true</code> onnistuessa, muuten <code>false</code>
	 */
	public boolean changePlayer(String name) {
		return this.info.setPlayer(name);
	}

	/**
	 * Muuta hahmon rotua
	 * @param race	uusi rotu
	 * @return	<code>true</code> onnistuessa, muuten <code>false</code>
	 */
	public boolean changeRace(Race race) {
		return this.info.setRace(race, this.rule);
	}

	/**
	 * Hae hahmon nimi
	 * @return	hahmon nimi
	 */
	public String getName() {
		return info.getName();
	}

	/**
	 * Hae pelaajan nimi
	 * @return	pelaajan nimi
	 */
	public String getPlayer() {
		return info.getPlayer();
	}

	/**
	 * Hae rotu
	 * @return	rotu
	 */
	public String getRaceName() {
		return info.getRace().getName();
	}

	/**
	 * Hae ominaisuudet tallennusta varten
	 * @return	lista ominaisuuksista
	 */
	public ArrayList<Attribute> getArrayAttributes() {
		return new ArrayList<Attribute>(attributes);
	}

	/**
	 * Hae taidot tallennusta varten
	 * @return	lista taidoista
	 */
	public ArrayList<Skill> getArraySkills() {
		return new ArrayList<Skill>(skills);
	}

	/**
	 * Listaa ominaisuudet
	 * @return	lista ominaisuuksista
	 */
	public String listAttributes() {
		return attributes.toString();
	}

	/**
	 * Listaa taidot
	 * @return	lista taidoista
	 */
	public String listSkills() {
		return skills.toString();
	}

	/**
	 * Näyttää tiedot
	 * @return	tiedot tekstinä
	 */
	public String showInformation() {
		return info.toString();
	}

	/**
	 * Antaa hahmosäännön polun
	 * @return	hahmosäännön polku
	 */
	public String getRulePath() {
		return this.rule.getRulePath();
	}

	/**
	 * Antaa hahmon polun
	 * @return	hahmon polku
	 */
	public String getPath() {
		try {
			return new String(this.avatarpath);
		}
		catch(NullPointerException e) {
			return "N/A";
		}
	}

	/**
	 * Asettaa hahmon polun
	 * @param path	hahmon polku
	 * @return	hahmon polku
	 */
	public String setPath(String path) {
		this.avatarpath=path;
		return this.avatarpath;
	}

	/**
	 * Tuo sääntö
	 * @return	hahmosääntö
	 */
	public AvatarRule getRule() {
		return this.rule;
	}

	/**
	 * Tulostusoperaatio
	 * @return	tiedot
	 */
	public String toString() {
		return this.showInformation()+
			"\nAttributes:\n"+this.listAttributes()+
			"\nSkills:\n"+this.listSkills();
	}

}
