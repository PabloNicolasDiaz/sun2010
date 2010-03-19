package org.service.client;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.Collection;
import java.util.LinkedList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.jdom.Element;
import org.jdom.JDOMException;
import org.nicolas.sun2010.model.Game;
import org.nicolas.sun2010.model.Machine;
import org.nicolas.sun2010.model.ModelFactory;
import org.nicolas.sun2010.web.mapper.TableDOMExtractor;
import org.nicolas.sun2010.web.mapper.formats.ChainedFormatter;
import org.nicolas.sun2010.web.mapper.formats.Formatter;
import org.nicolas.sun2010.web.mapper.formats.wrappers.dao.DAOAdapterFormatter;
import org.nicolas.sun2010.web.mapper.formats.wrappers.java.JavaFormatWrapperFormatter;
import org.nicolas.sun2010.web.mapper.formats.wrappers.jdom.JDOMSingleFormatter;

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

			/*
			 * Map<String, Formatter<List<Element>, ?>> recog = new
			 * HashMap<String, Formatter<List<Element>, ?>>(); recog .put(
			 * "x:td[3]", new ChainedFormatter<List<Element>, List<String>,
			 * Long>( new JDOMAdapter(), new ChainedFormatter<List<String>,
			 * String, Long>( new ListExtractorFormatter<String>( 0), new
			 * JavaFormatWrapperFormatter<String, Long>(
			 * DecimalFormat.class))));
			 */
			/*
			 * Formatter<Element, Machine> someFormatter = new
			 * DAOAdapterFormatter<Element, Machine, String>( Machine.class, new
			 * JDOMAdapterFormatter()); recog.put("x:td[5] | x:td[6]",
			 * someFormatter);
			 */

			Collection<Formatter<Element, ?>> recog = new LinkedList<Formatter<Element, ?>>();
			recog.add(new ChainedFormatter(new JDOMSingleFormatter("x:td[3]"),
					new JavaFormatWrapperFormatter<String, Long>(
							DecimalFormat.class)));

			recog.add(new ChainedFormatter<Element, String, Machine>(
					new JDOMSingleFormatter("x:td[6]"),
					new DAOAdapterFormatter<Machine, String>(Machine.class)));

			TableDOMExtractor<Game, Long> gameMap = new TableDOMExtractor<Game, Long>(
					"//x:table[@id='TblMaquinas']",
					"x:tbody/x:tr[@style='background-color: White;']", "x",
					recog, ModelFactory.class.getMethod("createGame",
							new Class<?>[] { Long.class, Machine.class }), 13);
			File out = new File("masterMap.xml");

			Writer f = new PrintWriter(out);
			f.write(xstream.toXML(gameMap));
			f.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertTrue(true);
	}
}
