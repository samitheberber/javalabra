/**
 * XML -luokka käsittelee XML-tiedostoja, joka on hahmogeneraattorin tallennusmuoto
 * @author	Sami Saada
 * @version	%I%, %G%
 */
import java.io.*;
import java.util.*;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import org.xml.sax.*;

public class XML {

	/**
	 * Metodi tallentaa hahmon tiedostoon
	 * @param avatarfile		hahmon tiedot
	 * @param file			tallennustiedosto
	 * @return			<code>true</code> onnistuessa, muulloin <code>false</code>
	 * @throws NoUTF8Exception	virhe UTF-8:n puuttumisesta (nykyään mahdoton)
	 */
	public static String save (Avatar avatarfile, String file)
		throws NoUTF8Exception{
			try {
				OutputStreamWriter out = new OutputStreamWriter(new BufferedOutputStream(
							new FileOutputStream(file)
							)
						, "UTF8"
						);
				out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
				out.write("<!DOCTYPE Avatar SYSTEM \"http://www.cs.helsinki.fi/u/saada/kurssit/javalabra/dtd/avatar.dtd\">\r\n");
				out.write("<Avatar>\r\n");
				out.write("\t<Rule>"+avatarfile.getRulePath()+"</Rule>\r\n");
				out.write("\t<Attributes>\r\n");
				for (int i=0; i<avatarfile.getArrayAttributes().size(); ++i) {
					out.write("\t\t<Attribute>\r\n");
					out.write("\t\t\t<Name>"+avatarfile.getArrayAttributes().get(i).getName()+"</Name>\r\n");
					out.write("\t\t\t<Value>"+avatarfile.getArrayAttributes().get(i).getValue()+"</Value>\r\n");
					out.write("\t\t</Attribute>\r\n");
				}
				out.write("\t</Attributes>\r\n");
				out.write("\t<Skills>\r\n");
				for (int i=0; i<avatarfile.getArraySkills().size(); ++i) {
					out.write("\t\t<Skill>\r\n");
					out.write("\t\t\t<Name>"+avatarfile.getArraySkills().get(i).getName()+"</Name>\r\n");
					out.write("\t\t\t<Value>"+avatarfile.getArraySkills().get(i).getValue()+"</Value>\r\n");
					out.write("\t\t</Skill>\r\n");
				}
				out.write("\t</Skills>\r\n");
				out.write("\t<Information>\r\n");
				out.write("\t\t<Name>"+avatarfile.getName()+"</Name>\r\n");
				out.write("\t\t<Player>"+avatarfile.getPlayer()+"</Player>\r\n");
				out.write("\t\t<Race>"+avatarfile.getRaceName()+"</Race>\r\n");
				out.write("\t</Information>\r\n");
				out.write("</Avatar>");
				out.close();
				return file;
			}
			catch(UnsupportedEncodingException e) {
				throw new NoUTF8Exception();
			}
			catch (IOException e) {
				System.out.println(e);
				return null;
			}
		}

	/**
	 * Metodi tallentaa hahmosäännön tiedostoon
	 * @param avatarrulefile	hahmosääntö
	 * @param file			tallennustiedosto
	 * @return			<code>true</code> onnistuessa, muulloin <code>false</code>
	 * @throws NoUTF8Exception	Virhe UTF-8:n puuttumisesta
	 */
	public static String save (AvatarRule avatarrulefile, String file)
		throws NoUTF8Exception {
			try {
				OutputStreamWriter out = new OutputStreamWriter(new BufferedOutputStream(
							new FileOutputStream(file)
							)
						, "UTF8"
						);
				out.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
				out.write("<!DOCTYPE AvatarRule SYSTEM \"http://www.cs.helsinki.fi/u/saada/kurssit/javalabra/dtd/avatarrule.dtd\">\r\n");
				out.write("<AvatarRule>\r\n");
				out.write("\t<Attributes>\r\n");
				for (int i=0; i<avatarrulefile.getArrayAttributes().size(); ++i) {
					out.write("\t\t<Attribute>\r\n");
					out.write("\t\t\t<Name>"+avatarrulefile.getArrayAttributes().get(i).getName()+"</Name>\r\n");
					out.write("\t\t</Attribute>\r\n");
				}
				out.write("\t</Attributes>\r\n");
				out.write("\t<Skills>\r\n");
				for (int i=0; i<avatarrulefile.getArraySkills().size(); ++i) {
					out.write("\t\t<Skill>\r\n");
					out.write("\t\t\t<Name>"+avatarrulefile.getArraySkills().get(i).getName()+"</Name>\r\n");
					out.write("\t\t</Skill>\r\n");
				}
				out.write("\t</Skills>\r\n");
				out.write("\t<Races>\r\n");
				for (int i=0; i<avatarrulefile.getArrayRaces().size(); ++i) {
					out.write("\t\t<Race>\r\n");
					out.write("\t\t\t<Name>"+avatarrulefile.getArrayRaces().get(i).getName()+"</Name>\r\n");
					out.write("\t\t</Race>\r\n");
				}
				out.write("\t</Races>\r\n");
				out.write("</AvatarRule>");
				out.close();
				return file;
			}
			catch (UnsupportedEncodingException e) {
				throw new NoUTF8Exception();
			}
			catch (IOException e) {
				System.out.println(e);
				return null;
			}
		}

	/**
	 * Metodi lataa hahmon tiedostosta
	 * @param filename	tiedoston nimi
	 * @return		hahmo
	 * @throws AGException	Yleinen virheilmoitus
	 */
	public static Avatar loadAvatar (String filename) throws AGException {
		try {
			if (!(new File(filename)).exists())
				throw new AGException("File not exist.");
			DocumentBuilderFactory docBF = DocumentBuilderFactory.newInstance(); //Luodaan uusi dokumentinrakennustehdas
			if ( !docBF.isValidating() ) //Tarkistetaam validaattorin päällä ono
				docBF.setValidating(true); //Laitetaan päälle, jos ei ole päällä
			DocumentBuilder docB = docBF.newDocumentBuilder(); //Luodaan dokumentinrakentaja
			Document doc = docB.parse (new File(filename)); //Luodaan dokumentti parsimalla annetusta xml-tiedostosta

			/* Hahmosäännön polku */
			String rulepath = (
					(Node)(
						(
						 (Element)doc.getElementsByTagName("Rule").item(0)
						 ).getChildNodes()
						).item(0)
					).getNodeValue().trim();


			/* Lisätään ominaisuudet */
			ArrayList<Attribute> attributes = new ArrayList<Attribute>();
			NodeList allAttributes = doc.getElementsByTagName("Attribute");
			Node attributeNode;
			Element attributeElement;
			String attributeName;
			int attributeValue;
			for (int i=0; i<allAttributes.getLength(); ++i) {
				attributeNode = allAttributes.item(i);
				if (attributeNode.getNodeType() == Node.ELEMENT_NODE) {
					attributeElement=(Element)attributeNode;
					attributeName=(
							(Node)(
								(
								 attributeElement.getElementsByTagName("Name").item(0)
								 ).getChildNodes()
								).item(0)
							).getNodeValue().trim();
					attributeValue=Integer.parseInt(
							(
							 (Node)(
								 (
								  (Element)(
									  attributeElement.getElementsByTagName("Value")
									  ).item(0)
								  ).getChildNodes()
								 ).item(0)
							 ).getNodeValue().trim()
							);
					attributes.add(new Attribute(attributeName, attributeValue));
				}
			}
			/* Lisätään taidot */
			ArrayList<Skill> skills = new ArrayList<Skill>();
			NodeList allSkills = doc.getElementsByTagName("Skill");
			Node skillNode;
			Element skillElement;
			String skillName;
			int skillValue;
			for (int i=0; i<allSkills.getLength(); ++i) {
				skillNode = allSkills.item(i);
				if (skillNode.getNodeType() == Node.ELEMENT_NODE) {
					skillElement = (Element)skillNode;
					skillName=(
							(Node)(
								(
								 (Element)(
									 skillElement.getElementsByTagName("Name")
									 ).item(0)
								 ).getChildNodes()
								).item(0)
							).getNodeValue().trim();
					skillValue=Integer.parseInt(
							(
							 (Node)(
								 (
								  (Element)(
									  skillElement.getElementsByTagName("Value")
									  ).item(0)
								  ).getChildNodes()
								 ).item(0)
							 ).getNodeValue().trim()
							);
					skills.add(new Skill(skillName,skillValue));
				}
			}
			/* Lisätään rodut */
			NodeList allInfos = doc.getElementsByTagName("Information");
			Node infoNode;
			Element infoElement;
			String name=null, player=null, race=null;
			for (int i=0; i<allInfos.getLength(); ++i) {
				infoNode = allInfos.item(i);
				if (infoNode.getNodeType() == Node.ELEMENT_NODE) {
					infoElement = (Element)infoNode;
					name=(
							(Node)(
								(
								 (Element)(
									 infoElement.getElementsByTagName("Name")
									 ).item(0)
								 ).getChildNodes()
								).item(0)
							).getNodeValue().trim();
					player=(
							(Node)(
								(
								 (Element)(
									 infoElement.getElementsByTagName("Player")
									 ).item(0)
								 ).getChildNodes()
								).item(0)
							).getNodeValue().trim();
					race=(
							(Node)(
								(
								 (Element)(
									 infoElement.getElementsByTagName("Race")
									 ).item(0)
								 ).getChildNodes()
								).item(0)
							).getNodeValue().trim();
				}
			}
			return new Avatar(attributes, skills, new Information(name, player, new Race(race)), loadAvatarRule(rulepath), filename);
		}
		catch(SAXParseException e) {
			throw new AGException(e);
		}
		catch(SAXException e) {
			throw new AGException(e);
		}
		catch(ParserConfigurationException e) {
			throw new AGException(e);
		}
		catch(IOException e) {
			throw new AGException(e);
		}
		catch(AGException e) {
			throw new AGException(e);
		}
	}

	/**
	 * Metodi lataa hahmosäännön tiedostosta ja validoi sen. Jos tiedosto ei ole validi, niin metodi heittää virheen. Muuten metodi palauttaa hahmosäännön
	 * @param filename	tiedoston nimi
	 * @return		hahmosääntö
	 * @throws AGException	Yleinen virheilmoitus
	 */
	public static AvatarRule loadAvatarRule (String filename) throws AGException {

		try {
			if (!(new File(filename)).exists())
				throw new AGException("File not exist.");
			DocumentBuilderFactory docBF = DocumentBuilderFactory.newInstance(); //Luodaan uusi dokumentinrakennustehdas
			if ( !docBF.isValidating() ) //Tarkistetaam validaattorin päällä ono
				docBF.setValidating(true); //Laitetaan päälle, jos ei ole päällä
			DocumentBuilder docB = docBF.newDocumentBuilder(); //Luodaan dokumentinrakentaja
			Document doc = docB.parse (new File(filename)); //Luodaan dokumentti parsimalla annetusta xml-tiedostosta


			/* Lisätään ominaisuudet */
			ArrayList<AttributeRule> attributes = new ArrayList<AttributeRule>();
			NodeList allAttributes = doc.getElementsByTagName("Attribute");
			Node attributeNode;
			for (int i=0; i<allAttributes.getLength(); ++i) {
				attributeNode = allAttributes.item(i);
				if (attributeNode.getNodeType() == Node.ELEMENT_NODE) {
					attributes.add(
							new AttributeRule(
								(
								 (Node)(
									 (
									  (Element)(
										  (
										   (Element)attributeNode
										   ).getElementsByTagName("Name")
										  ).item(0)
									  ).getChildNodes()
									 ).item(0)
								 ).getNodeValue().trim()
								)
							);
				}
			}
			/* Lisätään taidot */
			ArrayList<SkillRule> skills = new ArrayList<SkillRule>();
			NodeList allSkills = doc.getElementsByTagName("Skill");
			Node skillNode;
			for (int i=0; i<allSkills.getLength(); ++i) {
				skillNode = allSkills.item(i);
				if (skillNode.getNodeType() == Node.ELEMENT_NODE) {
					skills.add(
							new SkillRule(
								(
								 (Node)(
									 (
									  (Element)(
										  (
										   (Element)skillNode
										   ).getElementsByTagName("Name")
										  ).item(0)
									  ).getChildNodes()
									 ).item(0)
								 ).getNodeValue().trim()
								)
							);
				}
			}
			/* Lisätään rodut */
			ArrayList<RaceRule> races = new ArrayList<RaceRule>();
			NodeList allRaces = doc.getElementsByTagName("Race");
			Node raceNode;
			for (int i=0; i<allRaces.getLength(); ++i) {
				raceNode = allRaces.item(i);
				if (raceNode.getNodeType() == Node.ELEMENT_NODE) {
					races.add(
							new RaceRule(
								(
								 (Node)(
									 (
									  (Element)(
										  (
										   (Element)raceNode
										   ).getElementsByTagName("Name")
										  ).item(0)
									  ).getChildNodes()
									 ).item(0)
								 ).getNodeValue().trim()
								)
							);
				}
			}
			return new AvatarRule(attributes, skills, races, filename);
		}
		catch(Exception e) {
			throw new AGException(e);
		}
	}

}
