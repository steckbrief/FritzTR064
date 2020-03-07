package de.bausdorf.avm.tr064;

import de.bausdorf.avm.tr064.beans.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import java.io.InputStream;

/**
 * helper to create a JAXB unmarshal which removed the namespace, namespaces
 * seem to be different across different fritzOS versions and we don't seem to
 * need them, so we use a filter to remove them
 */
public class JAXBUtilities {

	public static JAXBContext getContext() throws ParseException {
		try {

			return JAXBContext.newInstance(RootType.class, ScpdType.class, ActionType.class,
					AllowedValueRangeType.class, ArgumentType.class, DeviceDesc.class, ServiceDesc.class,
					IconType.class, SpecVersionType.class, StateVariableType.class

			);
		} catch (JAXBException e) {
			throw new ParseException(e);
		}
	}

	public static Object unmarshalInput(InputStream in) throws ParseException {
		try {
			JAXBContext context = getContext();
			javax.xml.bind.Unmarshaller um = context.createUnmarshaller();
			XMLReader reader = SAXParserFactory.newInstance().newSAXParser().getXMLReader();
			NamespaceFilter inFilter = new NamespaceFilter(null, false);
			inFilter.setParent(reader);
			InputSource is = new InputSource(in);
			SAXSource source = new SAXSource(inFilter, is);
			return um.unmarshal(source);
		} catch (SAXException | JAXBException | ParserConfigurationException e) {
			throw new ParseException(e);
		}
	}

	// utility class
	private JAXBUtilities() {
	}
}