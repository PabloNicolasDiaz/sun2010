package org.service.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.nicolas.sun2010.model.Game;
import org.nicolas.sun2010.model.Machine;
import org.nicolas.sun2010.model.ModelFactory;
import org.nicolas.sun2010.web.mapper.TableDOMExtractor;
import org.nicolas.sun2010.web.mapper.formats.Formatter;
import org.nicolas.sun2010.web.mapper.formats.adapters.DAOAdapterFormatter;
import org.nicolas.sun2010.web.mapper.formats.adapters.FormatAdapter;
import org.nicolas.sun2010.web.mapper.formats.adapters.JDOMAdapterFormatter;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 * 
	 * @param testName
	 *            name of the test case
	 */
	public AppTest(String testName) {
		super(testName);
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 */
	@SuppressWarnings("unchecked")
	public void testApp() throws JDOMException, NoSuchMethodException {

		XStream xstream = new XStream(new DomDriver());
		try {

			Map<String, Formatter<Element, ?>> recog = new HashMap<String, Formatter<Element, ?>>();
			recog.put("x:td[3]", new FormatAdapter<Element, Long>(
					DecimalFormat.class));

			Formatter<Element, Machine> someFormatter = new DAOAdapterFormatter<Element, Machine, String>(
					Machine.class, new JDOMAdapterFormatter());
			recog.put("x:td[5] | x:td[6]", someFormatter);

			TableDOMExtractor<Game, Long> gameMap = new TableDOMExtractor<Game, Long>(
					"//x:table[@id='TblMaquinas']/",
					"x:tbody/x:tr[@style='background-color: White;']", "x",
					recog, ModelFactory.class.getMethod("createGame",
							new Class<?>[] { Long.class }), 12);
			File out = new File("masterMap.xml");

			Writer f = new PrintWriter(out);
			f.write(xstream.toXML(gameMap));
			f.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (SecurityException e) {
			e.printStackTrace();
		}

		assertTrue(true);
	}
}
