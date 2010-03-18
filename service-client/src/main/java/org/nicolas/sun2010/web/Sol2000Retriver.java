package org.nicolas.sun2010.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthPolicy;
import org.apache.commons.httpclient.auth.AuthScope;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.cookie.CookieSpec;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.xpath.XPath;
import org.nicolas.sun2010.model.DailyState;
import org.nicolas.sun2010.model.DailyStatePK;
import org.nicolas.sun2010.model.Game;
import org.nicolas.sun2010.model.Machine;
import org.nicolas.sun2010.model.Manufacturer;
import org.nicolas.sun2010.model.ModelFactory;
import org.nicolas.sun2010.persistence.GenericDAO;
import org.nicolas.sun2010.persistence.HibernateDAOFactory;

import com.ibm.icu.text.NumberFormat;

/**
 * 
 */

/**
 * @author nicolas
 * 
 */
public class Sol2000Retriver {

	private URI address = null;
	private final HttpClient client;
	private final SAXBuilder builder;
	private final HibernateDAOFactory fact;
	private static Log log = LogFactory.getLog(Sol2000Retriver.class);

	public Sol2000Retriver() throws URISyntaxException {
		address = new URI("http://www.sielcon.net:8045/sol2000/");
		client = new HttpClient();
		builder = new SAXBuilder("org.ccil.cowan.tagsoup.Parser");

		client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER,
				new DefaultHttpMethodRetryHandler());
		// This is to make HttpClient pick the Digest authentication for asp.net
		List<String> authPrefs = new ArrayList<String>(3);

		authPrefs.add(AuthPolicy.DIGEST);

		authPrefs.add(AuthPolicy.BASIC);

		authPrefs.add(AuthPolicy.NTLM);
		client.getParams().setParameter(AuthPolicy.AUTH_SCHEME_PRIORITY,
				authPrefs);
		fact = HibernateDAOFactory.getDaoFactory();

	}

	public void Login(String user, String password)
			throws FailedAuthentificationException {
		Credentials defaultcreds = new UsernamePasswordCredentials(user,
				password);
		client.getState().setCredentials(
				new AuthScope(address.getHost(), address.getPort(),
						AuthScope.ANY_REALM), defaultcreds);
		try {

			GetMethod get_meth = new GetMethod(address.toString()
					+ "Login.aspx");

			client.executeMethod(get_meth);
			get_meth.releaseConnection();
			// See if we got any cookies
			CookieSpec cookiespec = CookiePolicy.getDefaultSpec();
			Cookie[] initcookies = cookiespec.match(address.getHost(), address
					.getPort(), "/", false, client.getState().getCookies());
			System.out.println("Initial set of cookies:");
			if (initcookies.length == 0) {
				System.out.println("None");
			} else {
				for (int i = 0; i < initcookies.length; i++) {
					System.out.println("- " + initcookies[i].toString());
				}
			}

			client.getParams().setCookiePolicy(
					CookiePolicy.BROWSER_COMPATIBILITY);
			/*
			 * PostMethod meth = new PostMethod(address.toString() +
			 * "Login.aspx"); meth.addParameter("login_password", password);
			 * meth.addParameter("login_username", user);
			 * meth.setDoAuthentication(true);
			 */

		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	};

	public void extractMaster() {
		GetMethod meth = new GetMethod(address.toString()
				+ "reporte_maestro_maquinas.aspx");
		try {
			client.executeMethod(meth);

			InputStream str = meth.getResponseBodyAsStream();
			System.out.println(meth.getResponseBodyAsString());

		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	};

	public void parseMaster(InputStream input) throws JDOMException,
			IOException {
		Document doc = builder.build(input);

		GenericDAO<Game, Long> gdao = fact.createDAO(Game.class);
		GenericDAO<Manufacturer, String> mdao = fact
				.createDAO(Manufacturer.class);
		GenericDAO<Machine, String> machdao = fact.createDAO(Machine.class);

		XPath path = getTablePath(doc, "TblMaquinas",
				"background-color: White;", true);
		XPath tds = getRowPath(doc);

		/* por cada entrada (fila) */

		for (Object e : path.selectNodes(doc)) {
			Element elem = (Element) e;
			List<?> vals = tds.selectNodes(elem);
			if (vals.size() > 2) {
				long UID = Long.parseLong(((Element) vals.get(2)).getValue());
				// System.out.println(UID);
				String machine = ((Element) vals.get(5)).getValue();
				String manufacturer = ((Element) vals.get(4)).getValue();

				Manufacturer manu = mdao.findById(manufacturer);
				Machine mach = machdao.findById(machine);
				Game d = gdao.findById(UID);

				if (manu == null) {
					manu = ModelFactory.createManufacturer(manufacturer);
					mdao.create(manu);
				}

				if (mach == null) {
					mach = ModelFactory.createMachine(manu, machine);
					machdao.create(mach);
				}

				if (d == null) {
					gdao.create(ModelFactory.createGame(UID, mach));
				}
			}
		}
	}

	private XPath getRowPath(Document doc) throws JDOMException {
		XPath tds = XPath.newInstance("x:td");
		tds.addNamespace("x", doc.getRootElement().getNamespaceURI());
		return tds;
	}

	private XPath getTablePath(Document doc, String tablename, String style,
			boolean tbody) throws JDOMException {
		String string = "//x:table[@id='" + tablename + "']"
				+ (tbody ? "/x:tbody" : "") + "/x:tr[@style='" + style + "']";
		log.info(string);
		XPath path = XPath.newInstance(string);
		path.addNamespace("x", doc.getRootElement().getNamespaceURI());
		return path;
	}

	@SuppressWarnings("unchecked")
	public void parseDaily(InputStream input) throws JDOMException,
			IOException, FailedParseException, ParseException {
		Document doc = builder.build(input);
		/** por cada mapper que conozca, mando a doc ... */
		/* GenericDAO<Game, Long> gdao = fact.createDAO(Game.class); */
		GenericDAO<DailyState, DailyStatePK> ddao = fact
				.createDAO(DailyState.class);

		DateFormat dtf = new SimpleDateFormat("dd/MM/yyyy");

		NumberFormat f = NumberFormat.getInstance();

		XPath pathDate = getInput(doc, "TxtFechaDesde");

		XPath path1 = getTablePath(doc, "TblReportes",
				"background-color: White;", true);
		XPath path2 = getTablePath(doc, "TblReportes",
				"background-color: LightCoral;", true);
		XPath rowpath = getRowPath(doc);

		Date date = dtf.parse(((Element) pathDate.selectSingleNode(doc))
				.getAttribute("value").getValue());
		System.out.println(date);

		LinkedList list = new LinkedList(path1.selectNodes(doc));
		list.addAll(path2.selectNodes(doc));

		for (Object elem : list) {
			Element Elem = (Element) elem;

			List<?> vals = rowpath.selectNodes(Elem);
			if (vals.size() == 9) {
				String strs = ((Element) vals.get(0)).getValue().replace(
						"(*) ", "").trim();

				Long UID = f.parse(strs).longValue();
				Double Bet = f.parse(((Element) vals.get(7)).getValue())
						.doubleValue();
				Double Yield = f.parse(((Element) vals.get(8)).getValue())
						.doubleValue();

				if (ddao.findById(ModelFactory.createDailyStatePK(UID, date)) == null)
					ddao.create(new DailyState(UID, date, Bet, Yield));
			}
		}

	}

	private XPath getInput(Document doc, String thing) throws JDOMException {
		XPath pathDate = XPath.newInstance("//y:input[@id=\"" + thing + "\"]");
		pathDate.addNamespace("y", doc.getRootElement().getNamespaceURI());
		return pathDate;
	}

	public void terminate() {
		fact.terminate();
	}
}
