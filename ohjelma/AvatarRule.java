/**
 * AvatarRule -luokka
 * Luo hahmosäännön hahmogeneraattorin käytettäväksi.
 * @author	Sami Saada
 * @version	%I%, %G%
 */
import java.util.*;

public class AvatarRule {

	/**
	 * <code>attributes</code> sisältää kaikki ominaisuussäännöt
	 * <code>skills</code> sisältää kaikki taitosäännöt
	 * <code>races</code> sisältää kaikki rotusäännöt
	 * <code>rulepath</code> sisältää säännön polun
	 */
	private ArrayList<AttributeRule> attributes;
	private ArrayList<SkillRule> skills;
	private ArrayList<RaceRule> races;
	private String rulepath;

	/**
	 * Luo hahmosäännön
	 */
	public AvatarRule() {
		this.attributes=new ArrayList<AttributeRule>();
		this.skills=new ArrayList<SkillRule>();
		this.races=new ArrayList<RaceRule>();
		this.rulepath=null;
	}

	/**
	 * Luo hahmosäännön
	 * @param attributeslist	valmis lista ominaisuussäännöistä
	 * @param skillslist		valmis lista taitisäännöistä
	 * @param raceslist		valmis lista rotusäännöistä
	 */
	public AvatarRule (ArrayList<AttributeRule> attributeslist, ArrayList<SkillRule> skillslist, ArrayList<RaceRule> raceslist, String rulepath) {
		this.attributes=new ArrayList<AttributeRule>(attributeslist);
		this.skills=new ArrayList<SkillRule>(skillslist);
		this.races=new ArrayList<RaceRule>(raceslist);
		this.rulepath=rulepath;
	}

	/**
	 * Luo hahmosäännön
	 * @param rule	valmis hahmosääntö
	 */
	public AvatarRule(AvatarRule rule) {
		this(rule.getArrayAttributes(), rule.getArraySkills(), rule.getArrayRaces(), rule.getRulePath());
	}

	/**
	 * Lisää ominaisuuden
	 * @param item	ominaisuussääntö, joka lisätään
	 * @return	<code>true</code>, jos lisäys onnistui, muuten <code>false</code>
	 */
	public boolean addAttribute(AttributeRule item) {
		return this.attributes.add(item);
	}

	/**
	 * Lisää taidon
	 * @param item	taitosääntö, joka lisätään
	 * @return	<code>true</code>, jos lisäys onnistui, muuten <code>false</code>
	 */
	public boolean addSkill(SkillRule item) {
		return this.skills.add(item);
	}

	/**
	 * @param item	rotusääntö, joka lisätään
	 * @return	<code>true</code>, jos lisäys onnistui, muuten <code>false</code>
	 */
	public boolean addRace(RaceRule item) {
		return this.races.add(item);
	}

	/**
	 * @param item	ominaisuussääntö, joka poistetaan
	 * @return	<code>true</code>, jos poisto onnistui, muuten <code>false</code>
	 */
	public boolean removeAttribute(AttributeRule item) {
		return this.attributes.remove(item);
	}

	/**
	 * @param item	taitosääntö, joka poistetaan
	 * @return	<code>true</code>, jos poisto onnistui, muuten <code>false</code>
	 */
	public boolean removeSkill(SkillRule item) {
		return this.skills.remove(item);
	}

	/**
	 * @param item	rotusääntö, joka poistetaan
	 * @return	<code>true</code>, jos poisto onnistui, muuten <code>false</code>
	 */
	public boolean removeRace(RaceRule item) {
		return this.races.remove(item);
	}

	/**
	 * Hakee nimeä vastaavan ominaisuussäännön
	 * @param name	ominaisuussäännön nimi
	 * @return	ominaisuussääntö
	 */
	public AttributeRule searchAttribute(String name) {
		for (int i=0; i<this.attributes.size(); ++i) {
			if (this.attributes.get(i).getName().equals(name))
				return this.attributes.get(i);
		}
		return null;
	}

	/**
	 * Hakee nimeä vastaavan taitosäännön
	 * @param name	taitosäännön nimi
	 * @return	taitosääntö
	 */
	public SkillRule searchSkill(String name) {
		for (int i=0; i<this.skills.size(); ++i) {
			if (this.skills.get(i).getName().equals(name))
				return this.skills.get(i);
		}
		return null;
	}

	/**
	 * Hakee nimeä vastaavan rotusäännön
	 * @param name	rotusäännön nimi
	 * @return	rotusääntö
	 */
	public RaceRule searchRace(String name) {
		for (int i=0; i<this.races.size(); ++i) {
			if (this.races.get(i).getName().equals(name))
				return this.races.get(i);
		}
		return null;
	}

	/**
	 * @return	lista ominaisuussäännöistä
	 */
	public String listAttributes() {
		return attributes.toString();
	}

	/**
	 * @return	lista taitosäännöistä
	 */
	public String listSkills() {
		return skills.toString();
	}

	/**
	 * @return	lista rotusäännöistä
	 */
	public String listRaces() {
		return races.toString();
	}

	/**
	 * XML-tallenusta varten lista ominaisuussäännöistä
	 * @return	lista ominaisuussäännöistä
	 */
	public ArrayList<AttributeRule> getArrayAttributes() {
		return new ArrayList<AttributeRule>(this.attributes);
	}

	/**
	 * XML-tallenusta varten lista taitosäännöistä
	 * @return	lista taitosäännöistä
	 */
	public ArrayList<SkillRule> getArraySkills() {
		return new ArrayList<SkillRule>(this.skills);
	}

	/**
	 * XML-tallenusta varten lista rotusäännöistä
	 * @return	lista rotusäännöistä
	 */
	public ArrayList<RaceRule> getArrayRaces() {
		return new ArrayList<RaceRule>(this.races);
	}

	/**
	 * Antaa säännön polun
	 * @return	säännön polku
	 */
	public String getRulePath() {
		try {
			return new String(this.rulepath);
		}
		catch(NullPointerException e) {
			return "N/A";
		}
	}

	/**
	 * Asettaa säännön polun
	 * @param path	polku
	 * @return	asetettu polku
	 */
	public String setRulePath(String path) {
		this.rulepath=path;
		return this.rulepath;
	}

	/**
	 * @return	lista kaikista säännöistä
	 */
	public String toString() {
		return "Attributes:\n"+listAttributes()+"\n"+
			"Skills:\n"+listSkills()+"\n"+
			"Races:\n"+listRaces();
	}

}
