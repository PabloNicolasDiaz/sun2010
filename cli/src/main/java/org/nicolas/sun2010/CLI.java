package org.nicolas.sun2010;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

import org.apache.log4j.PropertyConfigurator;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.nicolas.sun2010.model.Game;
import org.nicolas.sun2010.web.FailedParseException;
import org.nicolas.sun2010.web.Sol2000Retriver;
import org.nicolas.sun2010.web.mapper.BadDocumentException;
import org.nicolas.sun2010.web.mapper.TableDOMExtractor;
import org.nicolas.sun2010.web.mapper.TableExtractor;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class CLI {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws JDOMException,
			FailedParseException, ParseException, BadDocumentException {
		/*
		 * Hacer un toco de cosas ...
		 */
		Sol2000Retriver retriver;
		PropertyConfigurator.configure("src/main/resources/log4j.properties");
		try {
			// retriver = new Sol2000Retriver();
			File master = new File(
					"src/main/resources/reporte_maestro_maquinas.aspx.html");
			File report = new File(
					"src/main/resources/reporte_ganancias_sala_fab_f2.aspx.html");
			/*
			 * retriver.parseMaster(new FileInputStream(master));
			 * retriver.parseDaily(new FileInputStream(report));
			 */

			XStream str = new XStream(new DomDriver());
			TableExtractor<Game, Document, Element, Element> theMapper = (TableDOMExtractor<Game, Long>) str
					.fromXML(new FileInputStream(
							"src/main/resources/masterMap.xml"));

			SAXBuilder builder = new SAXBuilder("org.ccil.cowan.tagsoup.Parser");

			Document masterdoc = builder.build(new FileInputStream(master));
			theMapper.process(masterdoc);

			// retriver.terminate();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
