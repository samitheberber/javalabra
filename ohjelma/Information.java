/**
 * Information -luokka
 * Sisältää perustiedot hahmoista
 * @author	Sami Saada
 * @version	%I%, %G%
 */

import java.util.*;

public class Information {

	/**
	 * <code>name</code> sisältää hahmon nimen
	 * <code>player</code> sisältää pelaajan nimen
	 * <code>race</code> sisältää hahmon rodun
	 */
	private String name, player;
	private Race race;

	/**
	 * Alustaa luokan
	 */
	public Information() {
		this.name=null;
		this.player=null;
		this.race=new Race();
	}

	/**
	 * Luo luokan haluttujen tietojen mukaan
	 * @param name		haluttu nimi
	 * @param player	haluttu pelaaja
	 * @param race		haluttu rotu
	 */
	public Information(String name, String player, Race race) {
		this.name=name;
		this.player=player;
		this.race=race;
	}

	/**
	 * Luo luokan vanhan pohjalta
	 * @param info	vanha luokka
	 */
	public Information(Information info) {
		this.name=info.getName();
		this.player=info.getPlayer();
		this.race=info.getRace();
	}

	/**
	 * Palauttaa hahmon nimen
	 * @return	hahmon nimi
	 */
	public String getName() {
		try {
			return new String(this.name);
		}
		catch(NullPointerException e) {
			return "N/A";
		}
	}

	/**
	 * Palauttaa pelaajan nimen
	 * @return	pelaajan nimi
	 */
	public String getPlayer() {
		try {
			return new String(this.player);
		}
		catch(NullPointerException e) {
			return "N/A";
		}
	}

	/**
	 * Palauttaa hahmon rodun
	 * @return	hahmon rotu
	 */
	public Race getRace() {
		return new Race(this.race);
	}

	/**
	 * Asettaa hahmolle nimen
	 * @param newName	hahmon uusi nimi
	 */
	public boolean setName(String newName) {
		this.name=newName;
		return true;
	}

	/**
	 * Asettaa pelaajalle nimen
	 * @param newPlayer	pelaajan uusi nimi
	 */
	public boolean setPlayer(String newPlayer) {
		this.player=newPlayer;
		return true;
	}

	/**
	 * Asettaa hahmolle rodun
	 * @param newRace	hahmon uusi rotu
	 * @param races		sisältää sallitut rodut
	 */
	public boolean setRace(Race newRace, AvatarRule rule) {
		return this.race.setRace(newRace, rule);
	}

	/**
	 * Antaa hahmon tiedot
	 * @return	Pelaaja: <code>player</code>
	 *		Avatar: <code>name</code> the <code>race</code>
	 */
	public String toString() {
		return "Player: "+this.getPlayer()+"\nAvatar: "+this.getName()+" the "+this.getRace().toString();
	}

}
