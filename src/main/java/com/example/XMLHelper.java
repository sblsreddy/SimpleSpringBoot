package sunlife.us.dc.messagecenter.plugin.gcsdocgengateway.utils;

import com.ctc.wstx.exc.WstxIOException;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import sunlife.us.dc.log.LogFactory;
import sunlife.us.dc.messagecenter.plugin.gcsdocgengateway.processor.JAXBContextFactory;
import sunlife.us.dc.messagecenter.ws.webservice.impl.gcs.readclaimdata.ReadClaimData;

import javax.xml.bind.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.stream.StreamSource;
import java.io.*;

/**
 * XMLHelper class.
 */
public final class XMLHelper {

    private static Logger LOGGER = LogFactory.getLogger(XMLHelper.class);

    /**
     * Marshal input Object to String xml.
     * 
     * @param type Class type to be marshaled.
     * @param object Object to be marshaled. 
     * @return String xml.
     */
    public static String marshalObjToXml(final Class type, final Object object) {
        LOGGER.debug("XMLHelper.marshalObjToXml()");
        final StringWriter writer = new StringWriter();
        try {
            final JAXBContext jaxbContext = JAXBContext.newInstance(type);
            final Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
            marshaller.marshal(object, writer);
        }
        catch (JAXBException e) {
            LOGGER.error(e.getMessage(), e);
        }

        return writer.toString();
    } 

    /**
     * Unmarshal target element of input String xml to Class type object.
     * 
     * @param type Class type to unmarshal.
     * @param inputXml String input xml to unmarshal.
     * @param element String target element of input xml to unmarshal.  
     * @return Object object.
     */
    public static Object unmarshalXmlToObj(final Class type, final String inputXml, final String element) {
        LOGGER.debug("XMLHelper.unmarshalXmlToObj()");

        final InputStream is = IOUtils.toInputStream(inputXml);
        final XMLInputFactory factory = XMLInputFactory.newInstance();

        Object object = null;
        try {
            final XMLStreamReader reader = factory.createXMLStreamReader(is);
            while (reader.hasNext()) {
                final int event = reader.next();
                if (event == XMLStreamConstants.START_ELEMENT && reader.getLocalName().equals(element)) {
                    break;
                }
            }

            final JAXBContext jaxbContext = JAXBContext.newInstance(type);
            final Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            JAXBElement jaxbElement = (JAXBElement) jaxbUnmarshaller.unmarshal(reader, type);
            object = jaxbElement.getValue();
        }catch (WstxIOException e){
            LOGGER.debug("Exception in XMLHelper.unmarshalXmlToObj() : "+ e.toString());
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

        return object;
    }
    
    /**
     * Get target element value of input String xml.
     * 
     * @param inputXml String input xml.
     * @param element String target element of input xml.  
     * @return String value.
     */
    public static String getElementValue(final String inputXml, final String element) {
        //LOGGER.debug("XMLHelper.getElementValue()");

        String value = null;
        final InputStream is = IOUtils.toInputStream(inputXml);
        final XMLInputFactory factory = XMLInputFactory.newInstance();
        
        try {
            final XMLStreamReader reader = factory.createXMLStreamReader(is);
            while (reader.hasNext()) {
                final int event = reader.next();
                if (event == XMLStreamConstants.START_ELEMENT && reader.getLocalName().equals(element)) {
                    value = StringHelper.safeTrim(reader.getElementText());
                    break;
                }
            }
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        
        return value;
    }
    
    /**
     * Encode String xml.
     * 
     * @param str xml to be encoded.
     * @return string value
     */
    public static String encodeXmlAttribute(final String str) {
        if (str == null) {
            return null;
        }

        final int len = str.length();
        if (len == 0) {
            return str;
        }

        final StringBuffer encoded = new StringBuffer();
        for (int i = 0; i < len; i++) {
            char c = str.charAt(i);
            if (c == '<') {
                encoded.append("&lt;");
            }
            else if (c == '\"') {
                encoded.append("&quot;");
            }
            else if (c == '>') {
                encoded.append("&gt;");
            }
            else if (c == '\'') {
                encoded.append("&apos;");
            }
            else if (c == '&') {
                encoded.append("&amp;");
            }
            else {
                encoded.append(c);
            }
        }
        return encoded.toString();
    }

    /**
     * Removes xml tag and ns2 prefix
     * @param input
     * @return
     */
    public static String removeInvalidCharacters(String input) {
        String x = input.replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
        return x;
    }

    /**
     * @param inputXML
     * @param
     * @param
     * @return Object
     * @throws Exception
     */
    public static Object unMarshallerXMLtoJavaObject(final String inputXML){
        try {
            JAXBContextFactory factory = JAXBContextFactory.getInstance();
            JAXBContext jaxbContext = factory.getJaxBContext(ReadClaimData.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
            InputStreamReader inputStreamReader = new InputStreamReader(new ByteArrayInputStream(inputXML.getBytes()));
            XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(inputStreamReader);
            JAXBElement<Object> jaxbElement = unmarshaller.unmarshal(xmlStreamReader, Object.class);
            // MCUtilities.logIfDebug(LOGGER, "Inside unmarshall method " );
            return jaxbElement.getValue();

        } catch (JAXBException e){
            e.printStackTrace();
            LOGGER.error(LOGGER,  e);
        } catch (XMLStreamException xmle) {
            LOGGER.error(LOGGER,  xmle);
        } catch (Exception oe){
            LOGGER.error(LOGGER,  oe);
        }
        return null;
    }

    /**
     * @param inputXML
     * @param
     * @param
     * @return Object
     * @throws Exception
     */
    public static Document convertStringToXMLDocument(final String inputXML){

        //Parser that produces DOM object trees from XML content
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        //API to obtain DOM Document instance
        DocumentBuilder builder = null;
        Document doc = null;
        try
        {
            //Create DocumentBuilder with default configuration
            builder = factory.newDocumentBuilder();

            //Parse the content to Document object
            doc = builder.parse( new InputSource( new StringReader( inputXML ) ) );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return doc;
    }
    private final static String precedingUTFCharactersReplacement = "\\[|\\]";
    public static String invalidCharacters(String input) {
        return input.replaceAll(precedingUTFCharactersReplacement,"");
    }

    /**
     *
     * @param xml
     * @param cls
     * @return
     * @throws Exception
     */
    public static Object transformWebComposerXmlToObject(final String xml, final Class cls) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(sunlife.us.dc.messagecenter.ws.webservice.impl.gcs.readdocdata.ReadDocData.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        StringReader reader = new StringReader(xml);
        final StreamSource source = new StreamSource(reader);
        JAXBElement<?> jaxbElement = unmarshaller.unmarshal(source, cls);
        return jaxbElement.getValue();
    }

    public static boolean checkIfElementPresent(final String inputXml, final String elementTag) {
        //LOGGER.debug("XMLHelper.getElementValue()");

        Boolean value = false;

        try {
            Document document = convertStringToXMLDocument( inputXml );
            if (document != null) {
                NodeList nodeList = document.getElementsByTagName( "*" );
                for (int i = 0; i < nodeList.getLength(); i++) {
                    if (nodeList.item( i ).getNodeName().equalsIgnoreCase( elementTag )) {
                        value = Boolean.TRUE;
                    }
                }
            }

        } catch (Exception e) {
            LOGGER.error( e.getMessage(), e );
        }

        return value;
    }
    /**
     * Private constructor.
     */
    private XMLHelper() { }
}
