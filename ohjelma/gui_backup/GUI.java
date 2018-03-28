/**
 * Graafisen käyttöliittymän luokka
 * @author	Sami Saada
 * @version	%I%, %G%
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.filechooser.FileFilter;

public class GUI {

	/**
	 * Metodi luo ja käynnistää käyttöliittymän
	 */
	private static void startGUI() {

		GUIControl guiControl = new GUIControl("Avatar Generator");

		guiControl.accessFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Kerrotaan, että ohjelma sammuu suljettaessa

		createMenuBar(guiControl.accessFrame(), guiControl);
		createContentPane(guiControl.accessFrame());

		guiControl.accessFrame().setSize(800, 600); // 800*600 on sopiva ikkuna
		guiControl.accessFrame().setVisible(true); // laitetaan ikkuna näkyviin

	}

	/**
	 * Luo valikkorivin
	 * @param frame	pohja, jolle valikkorivi luodaan
	 */
	private static void createMenuBar(JFrame frame, GUIControl guiControl) {
		JMenuBar menubar = new JMenuBar();
		

		/* Hahmosääntövalikko alkaa */
		JMenu avatarRuleMenu = new JMenu("Avatar rule"); // Luodaan valikko

		GUIMenuItem newAvatarRule = new GUIMenuItem("New Avatar rule"); // Luodaan valikkoitemi
		newAvatarRule.addAction(new GUINewAvatarRule(guiControl));
		avatarRuleMenu.add(newAvatarRule.getMenuItem()); // Lisää valikkoon

		GUIMenuItem loadAvatarRule = new GUIMenuItem("Load Avatar rule"); // Luodaan valikkoitemi
		loadAvatarRule.addAction(new GUIOpenFile(guiControl, GUIOpenFile.LOAD_RULE));
		avatarRuleMenu.add(loadAvatarRule.getMenuItem()); // Lisää valikkoon

		GUIMenuItem saveAvatarRule = new GUIMenuItem("Save Avatar rule"); // Luodaan valikkoitemi
		saveAvatarRule.addAction(new GUISaveFile(guiControl, GUISaveFile.SAVE_RULE));
		avatarRuleMenu.add(saveAvatarRule.getMenuItem()); // Lisää valikkoon

		menubar.add(avatarRuleMenu); // Lisätään valikkoriville
		/* Hahmosääntövalikko loppuu */


		/* Hahmovalikko alkaa */
		GUIMenu avatarMenu = new GUIMenu("Avatar"); // Luodaan valikko

		GUIMenuItem newAvatar = new GUIMenuItem("New Avatar"); // Luodaan valikkoitemi
		newAvatar.addAction(new GUINewAvatar(guiControl));
		avatarMenu.addMenuItem(newAvatar.getMenuItem()); // Lisää valikkoon

		GUIMenuItem loadAvatar = new GUIMenuItem("Load Avatar"); // Luodaan valikkoitemi
		loadAvatar.addAction(new GUIOpenFile(guiControl, GUIOpenFile.LOAD_AVATAR));
		avatarMenu.addMenuItem(loadAvatar.getMenuItem()); // Lisää valikkoon

		GUIMenuItem saveAvatar = new GUIMenuItem("Save Avatar"); // Luodaan valikkoitemi
		saveAvatar.addAction(new GUISaveFile(guiControl, GUISaveFile.SAVE_AVATAR));
		avatarMenu.addMenuItem(saveAvatar.getMenuItem()); // Lisää valikkoon

		menubar.add(avatarMenu.getMenu()); // Lisätään valikkoriville
		/* Hahmovalikko loppuu */


		frame.setJMenuBar(menubar); // Lisää valikkorivin
	}

	/**
	 * Luo sisältöalustan
	 * @param frame	pohja
	 */
	private static void createContentPane(JFrame frame) {
		JPanel contentPane = new JPanel();
		contentPane.setOpaque(true);

		frame.setContentPane(contentPane);
	}

	/**
	 * Käynnistysmetodi
	 */
	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				startGUI();
			}
		});
	}

}

class GUIMenuBar {

	/**
	 * <code>menubar</code> on valikkorivi
	 */
	private JMenuBar menubar;

	/**
	 * Luo valikkorivin
	 */
	public GUIMenuBar() {
		this.menubar = new JMenuBar(); // Luo valikkorivin
	}

	/**
	 * Lisää valikkoriviin valikon
	 * @param menu	lisättävä valikko
	 */
	public void addMenu(JMenu menu) {
		this.menubar.add(menu);
	}

	/**
	 * Palauttaa valikkorivin
	 * @return	valikkorivi
	 */
	public JMenuBar getMenuBar() {
		return this.menubar;
	}

}

class GUIMenu {

	/**
	 * <code>menu</code> on muokattava valikko
	 */
	private JMenu menu;

	/**
	 * Luo halutun nimisen valikon
	 * @param name	valikon nimi
	 */
	public GUIMenu(String name) {
		this.menu = new JMenu(name); // Luo valikon
	}

	/**
	 * Lisää valikkoon valikkoitemin
	 * @param menuItem	lisättävä valikkoitemi
	 */
	public void addMenuItem(JMenuItem menuItem) {
		this.menu.add(menuItem);
	}

	/**
	 * Lisää erottimen
	 */
	public void seperate() {
		this.menu.addSeparator();
	}

	/**
	 * Lisää toiminnonkuuntelijan valikkoitemille
	 * @param actionListener
	 */
	public void addAction(ActionListener actionListener) {
		this.menu.addActionListener(actionListener);
	}

	/**
	 * Palauttaa käsiteltävän valikon
	 * @return	valikko
	 */
	public JMenu getMenu() {
		return this.menu;
	}

}

class GUIMenuItem {

	/**
	 * <code>menuItem</code> on käsittelyssä oleva valikkoitemi
	 */
	private JMenuItem menuItem;

	/**
	 * Luo uuden halutunnimisen valikkoitemin
	 * @param name	haluttu nimi
	 */
	public GUIMenuItem(String name) {
		this.menuItem = new JMenuItem(name);
	}

	/**
	 * Palauttaa käsitellyn valikkoitemin
	 * @return	valikkoitemi
	 */
	public JMenuItem getMenuItem() {
		return this.menuItem;
	}

	/**
	 * Lisää toiminnonkuuntelijan valikkoitemille
	 * @param actionListener
	 */
	public void addAction(ActionListener actionListener) {
		this.menuItem.addActionListener(actionListener);
	}

}

/**
 * Hallinnoi käyttöliittymässä liikkuvia tietoja
 */
class GUIControl {

	/**
	 * <code>avatarrule</code> on käyttöliittymän hahmosääntö
	 * <code>avatar</code> on käyttöliittymän hahmo
	 * <code>ruleLoaded</code> on säännön tila
	 * <code>avatarLoaded</code> on hahmon tila
	 */
	private AvatarRule avatarrule;
	private Avatar avatar;
	private boolean ruleLoaded, avatarLoaded;

	/**
	 * <code>frame</code> on käyttöliittymän pohja
	 */
	private JFrame frame;

	/**
	 * Luo ohjaimen
	 * @param name	Ohjalman nimi
	 */
	public GUIControl(String name) {
		this.avatarrule = new AvatarRule();
		this.avatar = new Avatar(this.avatarrule);
		this.ruleLoaded=false;
		this.avatarLoaded=false;
		this.frame = new JFrame(name);
	}

	/**
	 * Palauttaa säännön
	 * @return	sääntö
	 */
	public AvatarRule getRule() {
		return this.avatarrule;
	}

	/**
	 * Asettaa säännön paikoilleen ja alustaa käyttöliittymän
	 * @return	sääntö
	 */
	public void setRule(AvatarRule avatarrule) {
		this.avatarrule=avatarrule;
		this.frame.getContentPane().setLayout(new BorderLayout());
		drawGUIRule();
	}

	/**
	 * Kokoa käyttöliittymä säännölle
	 */
	private void drawGUIRule() {
		this.frame.getContentPane().removeAll();
		this.frame.getContentPane().add(GUITransformData.fetchAttributeRules(this), BorderLayout.PAGE_START);
		this.frame.getContentPane().add(GUITransformData.fetchSkillRules(this), BorderLayout.CENTER);
		this.frame.getContentPane().add(GUITransformData.fetchRaceRules(this),BorderLayout.PAGE_END);
		updateGUI();
	}

	/**
	 * Kokoa käyttöliittymä hahmolle
	 */
	private void drawGUIAvatar() {
		this.frame.getContentPane().removeAll();
		this.frame.getContentPane().add(GUITransformData.fetchAttributes(this), BorderLayout.PAGE_START);
		this.frame.getContentPane().add(GUITransformData.fetchSkills(this), BorderLayout.CENTER);
		this.frame.getContentPane().add(GUITransformData.fetchInformation(this), BorderLayout.PAGE_END);
		updateGUI();
	}

	/**
	 * Palauttaa hahmon
	 * @return	hahmo
	 */
	public Avatar getAvatar() {
		return this.avatar;
	}

	/**
	 * Asettaa hahmon paikoilleen ja alustaa käyttöliittymän
	 * @param avatar	hahmo
	 */
	public void setAvatar(Avatar avatar) {
		this.avatar=avatar;
		this.frame.getContentPane().setLayout(new GridLayout(2,2));
	}

	/**
	 * Palauttaa säännön tilan
	 */
	public boolean isRuleLoaded() {
		return this.ruleLoaded;
	}

	/**
	 * Palauttaa hahmon tilan
	 * @return	tila
	 */
	public boolean isAvatarLoaded() {
		return this.avatarLoaded;
	}

	/**
	 * Asettaa hahmon tilan
	 * @param value	uusi tila
	 */
	public void setAvatarLoaded(boolean value) {
		this.avatarLoaded=value;
		if (value)
			setRuleLoaded(value);
	}

	/**
	 * Asettaa säännön tilan
	 * @param value	uusi tila
	 */
	public void setRuleLoaded(boolean value) {
		this.ruleLoaded=value;
	}

	/**
	 * Luo uuden hahmosäännön
	 */
	public void newAvatarRule() {
		this.avatarrule=new AvatarRule();
		this.frame.getContentPane().setLayout(new GridLayout(3,1));
		drawGUIRule();
	}

	/**
	 * Luo uuden hahmon
	 */
	public void newAvatar() {
		this.avatar=new Avatar(this.avatarrule);
		this.frame.getContentPane().setLayout(new GridLayout(3,1));
		drawGUIAvatar();
	}

	/**
	 * Luo pääsyn JFrameen
	 * @return	frame
	 */
	public JFrame accessFrame() {
		return this.frame;
	}

	/**
	 * Lisää ominaisuussäännön
	 */
	public void addAttributeRule(String rule) {
		this.avatarrule.addAttribute(new AttributeRule(rule));
		drawGUIRule();
	}

	/**
	 * Poistaa ominaisuussäännön
	 * @param num	poistettavan numero
	 */
	public void removeAttributeRule(int num) {
		this.avatarrule.removeAttribute(this.avatarrule.getArrayAttributes().get(num));
		drawGUIRule();
	}

	/**
	 * Lisää taitosäännön
	 */
	public void addSkillRule(String rule) {
		this.avatarrule.addSkill(new SkillRule(rule));
		drawGUIRule();
	}

	/**
	 * Poistaa taitosäännön
	 * @param num	poistettavan numero
	 */
	public void removeSkillRule(int num) {
		this.avatarrule.removeSkill(this.avatarrule.getArraySkills().get(num));
		drawGUIRule();
	}

	/**
	 * Lisää rotusäännön
	 */
	public void addRaceRule(String rule) {
		this.avatarrule.addRace(new RaceRule(rule));
		drawGUIRule();
	}

	/**
	 * Poistaa rotusäännön
	 * @param num	poistettavan numero
	 */
	public void removeRaceRule(int num) {
		this.avatarrule.removeRace(this.avatarrule.getArrayRaces().get(num));
		drawGUIRule();
	}

	/**
	 * Lisää ominaisuuden
	 */
	public void setAttribute(int nro, int value) {
		this.avatar.setAttribute(nro, value);
		drawGUIAvatar();
	}

	/**
	 * Lisää taidon
	 */
	public void addSkill(String rule, int value) {
		this.avatar.addSkill(new Skill(rule, value));
		drawGUIAvatar();
	}

	/**
	 * Poistaa taidon
	 * @param num	poistettavan numero
	 */
	public void removeSkill(int num) {
		this.avatar.removeSkill(this.avatar.getArraySkills().get(num));
		drawGUIAvatar();
	}

	/**
	 * Lisää rodun
	 */
	public void setRace(String race) {
		this.avatar.changeRace(new Race(race));
		drawGUIAvatar();
	}

	/**
	 * Päivittää käyttöliittymän sisällön
	 */
	private void updateGUI() {
		((JPanel)this.frame.getContentPane()).updateUI();
	}

}

/**
 * Luokka luo uuden hahmosäännön
 */
class GUINewAvatarRule implements ActionListener {

	/**
	 * <code>guiControl</code> ohjaimen kopio
	 */
	private GUIControl guiControl;

	public GUINewAvatarRule(GUIControl guiControl) {
		this.guiControl=guiControl;
	}

	public void actionPerformed(ActionEvent e) {
		this.guiControl.newAvatarRule();
	}

}

/**
 * Luokka luo uuden hahmon
 */
class GUINewAvatar implements ActionListener {

	/**
	 * <code>guiControl</code> ohjaimen kopio
	 */
	private GUIControl guiControl;

	public GUINewAvatar(GUIControl guiControl) {
		this.guiControl=guiControl;
	}

	public void actionPerformed(ActionEvent e) {
		this.guiControl.newAvatar();
	}

}

/**
 * Luokka luo dialogin latausta varten
 */
class GUIOpenFile extends JPanel implements ActionListener {

	/**
	 * Luokan literaalit
	 * <code>LOAD_RULE</code> tunnus säännön lataukseen
	 * <code>LOAD_AVATAR</code> tunnus hahmon lataukseen
	 */
	final static int LOAD_RULE = 0;
	final static int LOAD_AVATAR = 1;

	/**
	 * Luokan muuttujat
	 * <code>fc</code> dialogi
	 * <code>guiControl</code> ohjaimen kopio
	 * <code>command</code> saatu komento (katso literaalit)
	 */
	private JFileChooser fc;
	private GUIControl guiControl;
	private int command;

	/**
	 * Dialogin luoja
	 * @param guiControl	ohjain
	 * @param command	komento
	 */
	public GUIOpenFile(GUIControl guiControl, int command) {
		fc = new JFileChooser();
		fc.addChoosableFileFilter(new XMLFilter());
		this.guiControl=guiControl;
		this.command=command;
	}

	/**
	 * Kuuntelija, avaa dialogin ja lataa oikeat tiedostot
	 * @param e	nappi, joka kuuntelee
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			int returnVal = fc.showOpenDialog(GUIOpenFile.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				if (command == 1) {
					this.guiControl.setAvatar(XML.loadAvatar(file.getAbsolutePath()));
					guiControl.setAvatarLoaded(true);
				}
				else if (command == 0) {
					this.guiControl.setRule(XML.loadAvatarRule(file.getAbsolutePath()));
					guiControl.setRuleLoaded(true);
				}
			} else {}
		}
		catch(AGException error) {}
	}

}

/**
 * Luokka luo dialogin latausta varten
 */
class GUISaveFile extends JPanel implements ActionListener {

	/**
	 * Luokan literaalit
	 * <code>LOAD_RULE</code> tunnus säännön lataukseen
	 * <code>LOAD_AVATAR</code> tunnus hahmon lataukseen
	 */
	final static int SAVE_RULE = 0;
	final static int SAVE_AVATAR = 1;

	/**
	 * Luokan muuttujat
	 * <code>fc</code> dialogi
	 * <code>guiControl</code> ohjaimen kopio
	 * <code>command</code> saatu komento (katso literaalit)
	 */
	private JFileChooser fc;
	private GUIControl guiControl;
	private int command;

	/**
	 * Dialogin luoja
	 * @param guiControl	ohjain
	 * @param command	komento
	 */
	public GUISaveFile(GUIControl guiControl, int command) {
		fc = new JFileChooser();
		fc.addChoosableFileFilter(new XMLFilter());
		this.guiControl=guiControl;
		this.command=command;
	}

	/**
	 * Kuuntelija, avaa dialogin ja lataa oikeat tiedostot
	 * @param e	nappi, joka kuuntelee
	 */
	public void actionPerformed(ActionEvent e) {
		try {
			int returnVal = fc.showSaveDialog(GUISaveFile.this);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				if (command == 1) {
					if (XML.save(this.guiControl.getAvatar(), file.getAbsolutePath()))
						this.guiControl.getAvatar().setPath(file.getAbsolutePath());
				}
				else if (command == 0) {
					if (XML.save(this.guiControl.getRule(), file.getAbsolutePath()))
						this.guiControl.getRule().setRulePath(file.getAbsolutePath());
				}
			} else {}
		}
		catch(AGException error) {}
	}

}

/**
 * Luokka liitetään filtterinä, joka suodattaa tiedostoista XML-tiedostot
 */
class XMLFilter extends FileFilter {

	/**
	 * Hyväksyttää tiedoston
	 * @param f	tarkastettava tiedosto
	 * @return	<code>true</code>, jos sallittu tiedosto tai hakemisto
	 *		<code>false</code>. muulloin
	 */
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String extension = getExtension(f);
		if (extension != null) {
			if (extension.equals("xml"))
				return true;
			else
				return false;
		}
		return false;
	}

	/**
	 * Tarkistaa tiedoston päätteet tiedostosta
	 * @param f	tiedosto
	 * @return	tiedoston pääte
	 */
	private String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');
		if (i > 0 &&  i < s.length() - 1) {
			ext = s.substring(i+1).toLowerCase();
		}
		return ext;
	}

	/**
	 * Pakollinen selitys, mitä tiedosto tekee
	 */
	public String getDescription() {
		return "Filter approves only XML-files";
	}
}

/**
 * Luokka muuttaa tietoja sopivaan muotoon
 */
class GUITransformData {

	private static JList getList(ArrayList<AttributeRule> rules) {
		JList rulelist = new JList();
		rulelist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		rulelist.setSelectedIndex(0);
		return null;
	}

	public static JPanel fetchAttributeRules(GUIControl control) {
		try {
			JPanel mainpanel = new JPanel(new BorderLayout());
			mainpanel.add(new JLabel("Attribute rules:"), BorderLayout.PAGE_START);

			ArrayList<AttributeRule> rules = control.getRule().getArrayAttributes();

			JPanel panel = new JPanel();
			panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

			JLabel label;
			JButton button;
			JPanel attributes;
			SpringLayout attributesLayout;

			for (int i=0; i<rules.size(); ++i) {
				attributesLayout = new SpringLayout();
				attributes = new JPanel(attributesLayout);
				label = new JLabel(rules.get(i).getName());
				attributes.add(label);
				button = new JButton("Remove");
				button.addActionListener(new GUIRemoveAttributeRule(control, i));
				attributes.add(button);
				attributesLayout.putConstraint(SpringLayout.WEST, label, 5, SpringLayout.WEST, attributes);
				attributesLayout.putConstraint(SpringLayout.NORTH, label, 5, SpringLayout.NORTH, attributes);
				attributesLayout.putConstraint(SpringLayout.WEST, button, 50, SpringLayout.EAST, label);
				attributesLayout.putConstraint(SpringLayout.NORTH, button, 5, SpringLayout.NORTH, attributes);
				panel.add(attributes);
			}

			SpringLayout addItemLayout = new SpringLayout();
			JPanel addItem = new JPanel(addItemLayout);

			JTextField newAttribute = new JTextField(null,10);
			addItem.add(newAttribute);

			button = new JButton("Add new Attribute rule");
			button.addActionListener(new GUIAddAttributeRule(control, newAttribute));
			addItem.add(button);

			addItemLayout.putConstraint(SpringLayout.WEST, newAttribute, 5, SpringLayout.WEST, addItem);
			addItemLayout.putConstraint(SpringLayout.NORTH, newAttribute, 5, SpringLayout.NORTH, addItem);
			addItemLayout.putConstraint(SpringLayout.WEST, button, 50, SpringLayout.EAST, newAttribute);
			addItemLayout.putConstraint(SpringLayout.NORTH, button, 5, SpringLayout.NORTH, addItem);

			panel.add(addItem);
			mainpanel.add(panel, BorderLayout.CENTER);
			return mainpanel;
		}
		catch(IndexOutOfBoundsException e) {
			return null;
		}
	}

	public static JPanel fetchAttributes(GUIControl control) {
		try {
			JPanel mainpanel = new JPanel(new BorderLayout());
			mainpanel.add(new JLabel("Attributes:"), BorderLayout.PAGE_START);
			ArrayList<Attribute> rules = control.getAvatar().getArrayAttributes();
			JPanel panel = new JPanel(new GridLayout(rules.size()+1,3));
			JButton button;
			for (int i=0; i<rules.size(); ++i) {
				panel.add(new JLabel(rules.get(i).getName()));
				JTextField value = new JTextField();
				panel.add(value);
				button = new JButton("Set");
				button.addActionListener(new GUISetAttribute(control, i, value));
				panel.add(button);
			}
			mainpanel.add(panel, BorderLayout.CENTER);
			return mainpanel;
		}
		catch(IndexOutOfBoundsException e) {
			return null;
		}
	}

	public static JPanel fetchSkillRules(GUIControl control) {
		try {
			JPanel mainpanel = new JPanel(new BorderLayout());
			mainpanel.add(new JLabel("Skill rules:"), BorderLayout.PAGE_START);
			ArrayList<SkillRule> rules = control.getRule().getArraySkills();
			JPanel panel = new JPanel(new GridLayout(rules.size()+1,2));
			JButton button;
			for (int i=0; i<rules.size(); ++i) {
				panel.add(new JLabel(rules.get(i).getName()));
				button = new JButton("Remove");
				button.addActionListener(new GUIRemoveSkillRule(control, i));
				panel.add(button);
			}
			JTextField newSkill = new JTextField();
			panel.add(newSkill);
			button = new JButton("Add new Skill rule");
			button.addActionListener(new GUIAddSkillRule(control, newSkill));
			panel.add(button);
			mainpanel.add(panel, BorderLayout.CENTER);
			return mainpanel;
		}
		catch(IndexOutOfBoundsException e) {
			return null;
		}
	}

	public static JPanel fetchSkills(GUIControl control) {
		try {
			JPanel mainpanel = new JPanel(new BorderLayout());
			mainpanel.add(new JLabel("Skills:"), BorderLayout.PAGE_START);
			ArrayList<Skill> rules = control.getAvatar().getArraySkills();
			JPanel panel = new JPanel(new GridLayout(rules.size()+1,2));
			JButton button;
			for (int i=0; i<rules.size(); ++i) {
				panel.add(new JLabel(rules.get(i).getName()));
				button = new JButton("Remove");
				button.addActionListener(new GUIRemoveSkill(control, i));
				panel.add(button);
			}
			JTextField newSkill = new JTextField();
			panel.add(newSkill);
			JTextField value = new JTextField();
			panel.add(value);
			button = new JButton("Add new Skill");
			button.addActionListener(new GUIAddSkill(control, newSkill, value));
			panel.add(button);
			mainpanel.add(panel, BorderLayout.CENTER);
			return mainpanel;
		}
		catch(IndexOutOfBoundsException e) {
			return null;
		}
	}

	public static JPanel fetchRaceRules(GUIControl control) {
		try {
			JPanel mainpanel = new JPanel(new BorderLayout());
			mainpanel.add(new JLabel("Race rules:"), BorderLayout.PAGE_START);
			ArrayList<RaceRule> rules = control.getRule().getArrayRaces();
			JPanel panel = new JPanel(new GridLayout(rules.size()+1,2));
			JButton button;
			for (int i=0; i<rules.size(); ++i) {
				panel.add(new JLabel(rules.get(i).getName()));
				button = new JButton("Remove");
				button.addActionListener(new GUIRemoveRaceRule(control, i));
				panel.add(button);
			}
			JTextField newRace = new JTextField();
			panel.add(newRace);
			button = new JButton("Add new Race rule");
			button.addActionListener(new GUIAddRaceRule(control, newRace));
			panel.add(button);
			mainpanel.add(panel, BorderLayout.CENTER);
			return mainpanel;
		}
		catch(IndexOutOfBoundsException e) {
			return null;
		}
	}

	public static JPanel fetchInformation(GUIControl control) {
		try {
			JPanel mainpanel = new JPanel(new BorderLayout());
			mainpanel.add(new JLabel("Race rules:"), BorderLayout.PAGE_START);
			ArrayList<RaceRule> rules = control.getRule().getArrayRaces();
			JPanel panel = new JPanel(new GridLayout(rules.size()+1,2));
			JButton button;
			for (int i=0; i<rules.size(); ++i) {
				panel.add(new JLabel(rules.get(i).getName()));
				button = new JButton("Remove");
				button.addActionListener(new GUIRemoveRaceRule(control, i));
				panel.add(button);
			}
			JTextField newRace = new JTextField();
			panel.add(newRace);
			button = new JButton("Add new Race rule");
			button.addActionListener(new GUIAddRaceRule(control, newRace));
			panel.add(button);
			mainpanel.add(panel, BorderLayout.CENTER);
			return mainpanel;
		}
		catch(IndexOutOfBoundsException e) {
			return null;
		}
	}

}

/**
 * Luokka kuuntelee ominaisuussäännön poistajaa
 */
class GUIRemoveAttributeRule implements ActionListener {

	private GUIControl guiControl;
	private int rule;

	public GUIRemoveAttributeRule(GUIControl guiControl, int rule) {
		this.guiControl=guiControl;
		this.rule=rule;
	}

	public void actionPerformed(ActionEvent e) {
		this.guiControl.removeAttributeRule(this.rule);
	}

}

/**
 * Luokka kuuntelee taitosäännön poistajaa
 */
class GUIRemoveSkillRule implements ActionListener {

	private GUIControl guiControl;
	private int rule;

	public GUIRemoveSkillRule(GUIControl guiControl, int rule) {
		this.guiControl=guiControl;
		this.rule=rule;
	}

	public void actionPerformed(ActionEvent e) {
		this.guiControl.removeSkillRule(this.rule);
	}

}

/**
 * Luokka kuuntelee taitosäännön poistajaa
 */
class GUIRemoveSkill implements ActionListener {

	private GUIControl guiControl;
	private int rule;

	public GUIRemoveSkill(GUIControl guiControl, int rule) {
		this.guiControl=guiControl;
		this.rule=rule;
	}

	public void actionPerformed(ActionEvent e) {
		this.guiControl.removeSkill(this.rule);
	}

}

/**
 * Luokka kuuntelee rotusäännön poistajaa
 */
class GUIRemoveRaceRule implements ActionListener {

	private GUIControl guiControl;
	private int rule;

	public GUIRemoveRaceRule(GUIControl guiControl, int rule) {
		this.guiControl=guiControl;
		this.rule=rule;
	}

	public void actionPerformed(ActionEvent e) {
		this.guiControl.removeRaceRule(this.rule);
	}

}

/**
 * Luokka kuuntelee ominaisuussäännön lisääjää
 */
class GUIAddAttributeRule implements ActionListener {

	private GUIControl guiControl;
	private JTextField rule;

	public GUIAddAttributeRule(GUIControl guiControl, JTextField rule) {
		this.guiControl=guiControl;
		this.rule=rule;
	}

	public void actionPerformed(ActionEvent e) {
		this.guiControl.addAttributeRule(this.rule.getText());
	}

}

/**
 * Luokka kuuntelee taitosäännön lisääjää
 */
class GUIAddSkillRule implements ActionListener {

	private GUIControl guiControl;
	private JTextField rule;

	public GUIAddSkillRule(GUIControl guiControl, JTextField rule) {
		this.guiControl=guiControl;
		this.rule=rule;
	}

	public void actionPerformed(ActionEvent e) {
		this.guiControl.addSkillRule(this.rule.getText());
	}

}

/**
 * Luokka kuuntelee rotusäännön lisääjää
 */
class GUIAddRaceRule implements ActionListener {

	private GUIControl guiControl;
	private JTextField rule;

	public GUIAddRaceRule(GUIControl guiControl, JTextField rule) {
		this.guiControl=guiControl;
		this.rule=rule;
	}

	public void actionPerformed(ActionEvent e) {
		this.guiControl.addRaceRule(this.rule.getText());
	}

}/**
 * Luokka kuuntelee ominaisuussäännön lisääjää
 */
class GUISetAttribute implements ActionListener {

	private GUIControl guiControl;
	private int rule;
	private JTextField value;

	public GUISetAttribute(GUIControl guiControl, int rule, JTextField value) {
		this.guiControl=guiControl;
		this.rule=rule;
		this.value=value;
	}

	public void actionPerformed(ActionEvent e) {
		this.guiControl.setAttribute(this.rule, Integer.parseInt(this.value.getText()));
	}

}

/**
 * Luokka kuuntelee taitosäännön lisääjää
 */
class GUIAddSkill implements ActionListener {

	private GUIControl guiControl;
	private JTextField rule, value;

	public GUIAddSkill(GUIControl guiControl, JTextField rule, JTextField value) {
		this.guiControl=guiControl;
		this.rule=rule;
		this.value=value;
	}

	public void actionPerformed(ActionEvent e) {
		this.guiControl.addSkill(this.rule.getText(), Integer.parseInt(this.value.getText()));
	}

}

/**
 * Luokka kuuntelee rotusäännön lisääjää
 */
class GUISetRace implements ActionListener {

	private GUIControl guiControl;
	private JTextField rule;

	public GUISetRace(GUIControl guiControl, JTextField rule) {
		this.guiControl=guiControl;
		this.rule=rule;
	}

	public void actionPerformed(ActionEvent e) {
		this.guiControl.setRace(this.rule.getText());
	}

}
