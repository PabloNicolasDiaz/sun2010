package org.service.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.Format;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jdom.JDOMException;
import org.nicolas.sun2010.model.Game;
import org.nicolas.sun2010.model.ModelFactory;
import org.nicolas.sun2010.web.mapper.DocumentProcessor;
import org.nicolas.sun2010.web.mapper.TableExtractor;

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
	public void testApp() throws JDOMException, NoSuchMethodException {
		XStream xstream = new XStream(new DomDriver());
		try {

			Map<String, Class<? extends Format>> recog = new HashMap<String, Class<? extends Format>>();
			recog.put("x:td[3]", MessageFormat.class);

			DocumentProcessor gameMap = new TableExtractor<Game, Long>(
					"//x:table[@id='TblMaquinas']/x:tbody/x:tr[@style='background-color: White;']",
					"x", recog, ModelFactory.class.getMethod("createGame",
							new Class<?>[] { Long.class }));
			File out = new File("masterMap.xml");

			Writer f = new PrintWriter(out);
			f.write(xstream.toXML(gameMap));
			f.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(true);
	}
}
