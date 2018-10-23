package com.github.onlynight.chatim.server.config;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;
import java.io.InputStream;

public abstract class BaseConfigReader {

    public static final String GATE_PATH = "/server/gate";
    public static final String AUTH_PATH = "/server/auth";
    public static final String LOGIC_PATH = "/server/logic";

    public static final String IP_ATTR = "ip";
    public static final String PORT_ATTR = "port";

    private InputStream configFile;

    private String gateIp;
    private String authIp;
    private String logicIp;

    private int gatePort;
    private int authPort;
    private int logicPort;

    public BaseConfigReader(InputStream configFile) {
        this.configFile = configFile;
        try {
//            BufferedReader reader = new BufferedReader(new InputStreamReader(configFile));
//            String line;
//            StringBuilder sb = new StringBuilder();
//            while ((line = reader.readLine()) != null) {
//                sb.append(line);
//            }
//            System.out.println(sb.toString());
            parse();
        } catch (IOException | SAXException | ParserConfigurationException | XPathExpressionException e) {
            e.printStackTrace();
        }
    }

    protected void parse() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException {
        Element rootElement = getDocRootElement();

        XPath xPath = XPathFactory.newInstance().newXPath();
        XPathExpression xPathExpression = null;
        NodeList nodeList = null;
        Element element = null;

        xPathExpression = xPath.compile(GATE_PATH);
        nodeList = (NodeList) xPathExpression.evaluate(rootElement, XPathConstants.NODESET);
        element = (Element) nodeList.item(0);

        gateIp = element.getAttribute(IP_ATTR);
        gatePort = Integer.parseInt(element.getAttribute(PORT_ATTR));

        xPathExpression = xPath.compile(AUTH_PATH);
        nodeList = (NodeList) xPathExpression.evaluate(rootElement, XPathConstants.NODESET);
        element = (Element) nodeList.item(0);

        authIp = element.getAttribute(IP_ATTR);
        authPort = Integer.parseInt(element.getAttribute(PORT_ATTR));

        xPathExpression = xPath.compile(LOGIC_PATH);
        nodeList = (NodeList) xPathExpression.evaluate(rootElement, XPathConstants.NODESET);
        element = (Element) nodeList.item(0);

        logicIp = element.getAttribute(IP_ATTR);
        logicPort = Integer.parseInt(element.getAttribute(PORT_ATTR));

    }

    protected Element getDocRootElement() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder builder = DocumentBuilderFactory
                .newInstance().newDocumentBuilder();
        return builder.parse(configFile).getDocumentElement();
    }

    public String getGateIp() {
        return gateIp;
    }

    public String getAuthIp() {
        return authIp;
    }

    public String getLogicIp() {
        return logicIp;
    }

    public int getGatePort() {
        return gatePort;
    }

    public int getAuthPort() {
        return authPort;
    }

    public int getLogicPort() {
        return logicPort;
    }
}
