/**
 * CLI -luokka antaa komentorivipohjaisen käyttöliittymän
 * @author	Sami Saada
 * @version	%I%, %G%
 */

import java.util.Scanner;
import java.io.*;

public class CLI {

	/**
	 * Ohjelman ajometodi
	 * @param args	komentoriviparametrit
	 * @return	Jos on parametreja, niin kutsuu käsittelymetodin,
	 *		muuten käynnistää ohjelman
	 */
	public static void main(String[] args) {
		if (args.length>0)
			parseArgs(args);
		else {
			AGCLI start = new AGCLI();
			start.menu();
		}
	}

	/**
	 * Metodi pyytää tarkistuksen lopettaville parametreille
	 * @param args	komentoriviparametrit
	 * @return	Jos on ohjelman lopettavia parametreja, ajetaan niiden metodit,
	 *		muuten ajetaan muiden parametrien vaatimat metodit
	 */
	private static void parseArgs(String[] args) {
		if (searchEndCmds(args))
			doEndCmds(args);
		else
			doCmds(args);
	}

	/**
	 * Metodi etsii lopetusparametreja
	 * @param args	komentoriviparametrit
	 * @return	<code>true</code>, jos löytyy lopetusparametri,
	 *		<code>false</code> muuten
	 */
	private static boolean searchEndCmds(String[] args) {
		for(String cmd : args)
			if (cmd.equals("-h") || cmd.equals("--help"))
				return true;
		return false;
	}

	/**
	 * Metodi ajaa lopetusparametrit
	 * @param args	komentoriviparametrit
	 */
	private static void doEndCmds(String[] args) {
		for(String cmd : args) {
			if (cmd.equals("-h"))
				help();
			else if (cmd.equals("--help"))
				help();
		}
	}

	/**
	 * Metodi ajaa loput parametrit
	 * @param args	komentoriviparametrit
	 * @return	ajetaan metodi start() sopivilla parametreilla
	 */
	private static void doCmds(String[] args) {
		AGCLI start = new AGCLI();
		start.menu();
	}

	/**
	 * Tulostaa apuruudun
	 */
	private static void help() {
		System.out.println("Usage java CLI [args..]\n"+
			"-h or --help\t\tPrint this help screen");
	}

}

class AGCLI {

	/**
	 * <code>readKbd</code> lukee käyttäjän syötettä näppäimistöltä
	 * <code>avatarbase</code> sisältää hahmopohjan
	 */
	private static Scanner readKbd = new Scanner(System.in);
	private AvatarRule avatarrule;
	private Avatar avatar;
	
	/**
	 * Varsinaisen ohjelman luonti
	 */
	public AGCLI() {
		this.avatarrule=new AvatarRule();
		this.avatar=new Avatar(this.avatarrule);
	}

	/**
	 * Varsinaisen ohjelman käynnistys
	 */
	public void menu() {
		int command=0;
		while (command>=0) {
			showCmds();
			System.out.print("Your select: ");
			command=readCmd();
			System.out.println();
			doCmd(command);
		}
	}

	/**
	 * Metodi antaa listan mahdollisista komennoista
	 * @return	Lista komennoista
	 */
	private void showCmds() {
		System.out.println("\n\n"+
				"#-----------------------#\n"+
				"#       Main menu       #\n"+
				"# Commands available:   #\n"+
				"#  1: Show avatarbase   #\n"+
				"#  2: Modify avatarrule #\n"+
				"#  3: Modify avatar     #\n"+
				"#  4: Load avatarrule   #\n"+
				"#  5: Save avatarrule   #\n"+
				"#  6: Load avatar       #\n"+
				"#  7: Save avatar       #\n"+
				"# <0: Quit              #\n"+
				"#-----------------------#");
	}

	/**
	 * Ohjelma lukee käyttäjän syöttämän kokonaisluvun
	 * @return	kokonaisluku komentoja varten
	 */
	private int readCmd() {
		if(readKbd.hasNextInt()) {
			return readKbd.nextInt();
		}
		else {
			System.out.println("Not a number: "+readKbd.nextLine());
			return readCmd();
		}
	}

	/**
	 * Suorita valittu komento
	 * @param cmd	suoritettavan komennon rumero
	 */
	private void doCmd(int cmd) {
		if (cmd==1)
			System.out.println("Avatar rule: "+this.avatarrule.getRulePath()+"\nAvatar: "+this.avatar.getPath());
		else if (cmd==2) {
			ARCLI arEditor = new ARCLI(this.avatarrule);
			arEditor.menu();
		}
		else if (cmd==3) {
			if (this.avatar.getRulePath().equals("N/A"))
				System.out.println("You haven't set static rule yet.");
			else {
				ACLI aEditor = new ACLI(this.avatar);
				aEditor.menu();
			}
		}
		else if (cmd==4) {
			String filename = getFilename();
			try {
				this.avatarrule=XML.loadAvatarRule(filename);
				System.out.println("Avatarrule path set to '"+this.avatarrule.getRulePath()+"'.");
				this.avatar=new Avatar(this.avatarrule);
			}
			catch(AGException e) {
				System.out.println(e);
			}
		}
		else if (cmd==5) {
			String filename, choice;
			boolean state=false; //Luodaan tilamuuttuja ja asetetaan se epätodeksi
			if (avatarrule.getRulePath().contains(".xml")) {
				filename=avatarrule.getRulePath();
				while (!state) { //Käydään silmukkaa lävitse, kunnes tila on tosi
					System.out.print("Your current path is '"+filename+"', do you want to overwrite it? (YES/NO) ");
					choice = readKbd.next();
					if (choice.equalsIgnoreCase("YES"))
						state=true; //Hyvä vastaus saatu, joten muutetaan tila todeksi
					else if (choice.equalsIgnoreCase("NO")) {
						filename = getFilename();
						state=true; //Hyvä vastaus saatu, joten muutetaan tila todeksi
					}
					else
						readKbd.nextLine(); //Tyhjennetään puskuri
				}
			}
			else
				filename = getFilename();
			try {
				String path=XML.save(avatarrule, filename);
				avatarrule.setRulePath(path);
			}
			catch (NoUTF8Exception e) {
				System.out.println(e);
			}
		}
		else if (cmd==6) {
			String filename = getFilename();try {
				this.avatar=XML.loadAvatar(filename);
				System.out.println("Avatar path set to '"+avatar.getPath()+"'.");
				this.avatarrule=new AvatarRule(this.avatar.getRule());
				System.out.println("Avatar uses rule from: "+this.avatarrule.getRulePath());
			}
			catch(AGException e) {
				System.out.println(e);
			}
		}
		else if (cmd==7) {
			String filename, choice;
			boolean state=false; //Luodaan tilamuuttuja ja asetetaan se epätodeksi
			if (avatar.getPath().contains(".xml")) {
				filename=avatar.getPath();
				while (!state) { //Käydään silmukkaa lävitse, kunnes tila on tosi
					System.out.print("Your current path is '"+filename+"', do you want to overwrite it? (YES/NO) ");
					choice = readKbd.next();
					if (choice.equalsIgnoreCase("YES"))
						state=true; //Hyvä vastaus saatu, joten muutetaan tila todeksi
					else if (choice.equalsIgnoreCase("NO")) {
						filename = getFilename();
						state=true; //Hyvä vastaus saatu, joten muutetaan tila todeksi
					}
					else
						readKbd.nextLine(); //Tyhjennetään puskuri
				}
			}
			else
				filename = getFilename();
			try {
				System.out.println("Avatar has been saved in '"+this.avatar.setPath(XML.save(avatar, filename))+"'.");
				System.out.println("Avatar uses rule from: "+this.avatarrule.setRulePath(this.avatar.getRulePath()));
			}
			catch (NoUTF8Exception e) {
				System.out.println(e);
			}
		}

	}

	/**
	 * Kysyy haluttua tiedoston nimeä
	 * @return	tiedoston nimi
	 */
	private String getFilename() {
		System.out.print("Which file to use (must be xml-file)? ");
		String filename = readKbd.next();
		if (filename.contains(".xml")) {
			readKbd.nextLine();
			return fileExist(filename);
		}
		else {
			readKbd.nextLine();
			System.out.println("You have to enter the file name in format: filename.xml");
			return getFilename();
		}
	}

	/**
	 * Tarkistaa löytyykö syötettyä tiedostoa
	 * @param filename	tiedoston nimi
	 * @return		tiedoston nimi
	 */
	private String fileExist(String filename) {
		File file = new File(filename);
		if (file.exists()) {
			System.out.print("File '"+filename+"' exists. Do you want to use it? (YES/NO) ");
			String answer = readKbd.next();
			if (answer.equalsIgnoreCase("YES")) {
				readKbd.nextLine();
				return filename;
			}
			else if (answer.equalsIgnoreCase("NO")) {
				readKbd.nextLine();
				return getFilename();
			}
			else
				return fileExist(filename);
		}
		else
			return filename;
	}

}

/**
 * Luo cli-käyttöliittymän hahmosäännön muokkaamiseen
 */
class ARCLI {

	/**
	 * <code>readKbd</code> lukee käyttäjän syötettä näppäimistöltä
	 * <code>rule</code> sisältää hahmosäännön
	 */
	private static Scanner readKbd = new Scanner(System.in);
	private AvatarRule rule;

	/**
	 * Luo käyttöliittymän annetulla hahmosäännöllä
	 * @param rule	hahmosääntö, jota muokataan
	 */
	public ARCLI(AvatarRule rule) {
		this.rule=rule;
	}

	/**
	 * Ajaa hahmosäännön valikkoa
	 */
	public void menu() {
		int command=0;
		while (command>=0) {
			showCmds();
			System.out.print("Your select: ");
			command=readCmd();
			System.out.println();
			doCmd(command);
		}
	}

	/**
	 * Tulostaa hahmosäännölle tarkoitetut komennot
	 */
	private void showCmds() {
		System.out.println("\n\n"+
				"#-------------------------#\n"+
				"#      Avatarrule menu    #\n"+
				"# Commands available:     #\n"+
				"#  1: Show avatarrule     #\n"+
				"#  2: Add an attribute    #\n"+
				"#  3: Add a skill         #\n"+
				"#  4: Add a race          #\n"+
				"#  5: Remove an attribute #\n"+
				"#  6: Remove a skill      #\n"+
				"#  7: Remove a race       #\n"+
				"# <0: Main menu           #\n"+
				"#-------------------------#");
	}

	/**
	 * Ohjelma lukee käyttäjän syöttämän kokonaisluvun
	 * @return	kokonaisluku komentoja varten
	 */
	private int readCmd() {
		if(readKbd.hasNextInt()) {
			return readKbd.nextInt();
		}
		else {
			System.out.println("Not a number: "+readKbd.nextLine());
			return readCmd();
		}
	}

	/**
	 * Suorittaa valitun hahmosääntöön liittyvän komennon
	 * @param cmd   suoritettavan komennon numero
	 */
	private void doCmd(int cmd) {
		if (cmd==1)
			System.out.println(this.rule);
		else if (cmd==2)
			add(1); //Lisää ominaisuus
		else if (cmd==3)
			add(2); //Lisää taito
		else if (cmd==4)
			add(3); //Lisää rotu
		else if (cmd==5)
			remove(1); //Poista ominaisuus
		else if (cmd==6)
			remove(2); //Poista taito
		else if (cmd==7)
			remove(3); //Poista rotu
	}

	/**
	 * Metodi lisää uuden tietueen valittuun ominaisuuteen
	 * @param item	1: attribute
	 *		2: skill
	 *		3: race
	 */
	private void add(int item) {
		if (item==1) {
			System.out.print("Enter the name of attribute, you want to add: ");
			rule.addAttribute(
					new AttributeRule(
						this.readName()
						)
					);
		}
		else if (item==2) {
			System.out.print("Enter the name of skill, you want to add: ");
			rule.addSkill(
					new SkillRule(
						this.readName()
						)
					);
		}
		else if (item==3) {
			System.out.print("Enter the name of race, you want to add: ");
			rule.addRace(
					new RaceRule(
						this.readName()
						)
					);
		}
	}

	/**
	 * Metodi poistaa halutun tietueen valitusta ominaisuudesta
	 * @param item	1: attribute
	 *		2: skill
	 *		3: race
	 */
	private void remove(int item) {
		System.out.print("Enter the name of item, you want to remove: ");
		if (item==1) {
			if (
					rule.removeAttribute(
						rule.searchAttribute(
							readName()
							)
						)
			   )
				System.out.println("Item successfully removed.");
			else
				System.out.println("No items removed.");
		}
		else if (item==2) {
			if (
					rule.removeSkill(
						rule.searchSkill(
							readName()
							)
						)
			   )
				System.out.println("Item successfully removed.");
			else
				System.out.println("No items removed.");
		}
		else if (item==3) {
			if (
					rule.removeRace(
						rule.searchRace(
							readName()
							)
						)
			   )
				System.out.println("Item successfully removed.");
			else
				System.out.println("No items removed.");
		}
	}

	/**
	 * Metodi lukee sopivan nimen
	 * @return	sopiva nimi
	 */
	private String readName() {
		String name=readKbd.next();
		readKbd.nextLine();
		return name;
	}

}

/**
 * Luo cli-käyttöliittymän hahmon muokkaamiseen
 */
class ACLI {

	/**
	 * <code>readKbd</code> lukee käyttäjän syötettä näppäimistöltä
	 * <code>rule</code> sisältää hahmosäännön
	 */
	private static Scanner readKbd = new Scanner(System.in);
	private Avatar avatar;

	/**
	 * Hahmonäkymän luonti
	 * @param avatar	hahmo
	 */
	public ACLI(Avatar avatar) {
		this.avatar=avatar;
	}

	/**
	 * Ajaa hahmonmuokkausvalikkoa
	 */
	public void menu() {
		int command=0;
		while (command>=0) {
			showCmds();
			System.out.print("Your select: ");
			command=readCmd();
			System.out.println();
			doCmd(command);
		}
	}

	/**
	 * Näyttää satavilla olevat komennot
	 */
	private void showCmds() {
		System.out.println("\n\n"+
				"#---------------------#\n"+
				"#     Avatar menu     #\n"+
				"# Commands available: #\n"+
				"#  1: Show avatar     #\n"+
				"#  2: Change name     #\n"+
				"#  3: Change player   #\n"+
				"#  4: Change race     #\n"+
				"#  5: Attributes menu #\n"+
				"#  6: Add skill       #\n"+
				"#  7: Remove skill    #\n"+
				"# <0: Main menu       #\n"+
				"#---------------------#");
	}

	/**
	 * Ohjelma lukee käyttäjän syöttämän kokonaisluvun
	 * @return	kokonaisluku komentoja varten
	 */
	private int readCmd() {
		if(readKbd.hasNextInt()) {
			int value = readKbd.nextInt();
			readKbd.nextLine();
			return value;
		}
		else {
			System.out.println("Not a number: "+readKbd.nextLine());
			return readCmd();
		}
	}

	/**
	 * Suorittaa valitun hahmosääntöön liittyvän komennon
	 * @param cmd   suoritettavan komennon numero
	 */
	private void doCmd(int cmd) {
		if (cmd==1)
			System.out.println(this.avatar);
		else if (cmd==2)
			edit(1); // Muokkaa nimeä
		else if (cmd==3)
			edit(2); // Muokkaa pelaajaa
		else if (cmd==4)
			edit(3); // Vaihda rotua
		else if (cmd==5)
			attributeMenu(); // Ominaisuuksien muokkaaminen
		else if (cmd==6)
			skillMenu(); // Taitojen lisäys
		else if (cmd==7)
			skillRemoveMenu(); // Taitojen poisto
	}

	/**
	 * Muokkaa haluttua tietoa
	 * @param item	1: nimi
	 *		2: pelaaja
	 *		3: rotu
	 */
	private void edit(int item) {
		if (item==1) {
			System.out.print("Name of avatar: "+this.avatar.getName()+"\nWhich name you want to set? ");
			if (this.avatar.changeName(readText()))
				System.out.println("Name changed, new name: "+this.avatar.getName());
			else
				System.out.println("Old name kept: "+this.avatar.getName());
		}
		else if (item==2) {
			System.out.print("Name of player: "+this.avatar.getPlayer()+"\nWhich name you want to set? ");
			if (this.avatar.changePlayer(readText()))
				System.out.println("Player changed, new player: "+this.avatar.getPlayer());
			else
				System.out.println("Old player kept: "+this.avatar.getPlayer());
		}
		else if (item==3) {
			System.out.print("Race of "+this.avatar.getName()+": "+this.avatar.getRaceName()+"\nWhich race you want to set? ");
			String race = readText();
			if (this.avatar.changeRace(new Race(race)))
				System.out.println("Race changed, new race: "+this.avatar.getRaceName());
			else
				System.out.println("Old race kept: "+this.avatar.getRaceName());
		}
	}

	/**
	 * Hakee tekstin
	 * @return	saatu teksti
	 */
	private String readText() {
		String value = readKbd.next();
		readKbd.nextLine();
		return value;
	}

	/**
	 * Ominaisuuksien muokkaamista varten tarkoitettu valikko
	 */
	private void attributeMenu() {
		boolean state=false;
		int nro=0, value=0;
		String answer;
		while (!state) {
			System.out.println("\nAttributes:");
			for (int i=0; i<this.avatar.getArrayAttributes().size(); ++i) {
				System.out.println(i+": "+this.avatar.getArrayAttributes().get(i));
			}
			System.out.print("In which attribute you want to set a value: ");
			nro=readValue();
			System.out.print("What is the value: ");
			value=readValue();
			if (this.avatar.setAttribute(nro,value))
				System.out.println("Value added.");
			else
				System.out.println("Value not added.");
			System.out.print("Do you want to continue modifing attributes? (YES/NO) ");
			answer=readText();
			if (answer.equalsIgnoreCase("NO"))
				state=true;
		}
	}

	/**
	 * Lukee kokonaislukuarvon
	 * @return	kokonaisluku
	 */
	private int readValue() {
		if (readKbd.hasNextInt()) {
			int value = readKbd.nextInt();
			readKbd.nextLine();
			return value;
		}
		else {
			readKbd.nextLine();
			return readValue();
		}
	}

	/**
	 * Taitojen muokkaamista varten tarkoitettu valikko
	 */
	private void skillMenu() {
		boolean state=false;
		int value=0;
		String answer, name;
		while (!state) {
			System.out.println("\nSkills available:\n"+this.avatar.getRule().getArraySkills());
			System.out.println("\nSkills setted:");
			for (int i=0; i<this.avatar.getArraySkills().size(); ++i) {
				System.out.println(i+": "+this.avatar.getArraySkills().get(i));
			}
			System.out.print("In which skill you want to add: ");
			name=readText();
			System.out.print("What is the value: ");
			value=readValue();
			if (this.avatar.addSkill(new Skill(name,value)))
				System.out.println("Skill added.");
			else
				System.out.println("Skill not added.");
			System.out.print("Do you want to continue modifing skills? (YES/NO) ");
			answer=readText();
			if (answer.equalsIgnoreCase("NO"))
				state=true;
		}
	}

	/**
	 * Taitojen poistamista varten tarkoitettu valikko
	 */
	private void skillRemoveMenu() {
		boolean state=false;
		int value=0;
		String answer;
		while (!state) {
			System.out.println("\nSkills setted:");
			for (int i=0; i<this.avatar.getArraySkills().size(); ++i) {
				System.out.println(this.avatar.getArraySkills().get(i).getName());
			}
			System.out.print("In which skill you want to add: ");
			if (this.avatar.removeSkill(readText()))
				System.out.println("Skill removed.");
			else
				System.out.println("Skill not removed.");
			System.out.print("Do you want to continue modifing skills? (YES/NO) ");
			answer=readText();
			if (answer.equalsIgnoreCase("NO"))
				state=true;
		}
	}

}
