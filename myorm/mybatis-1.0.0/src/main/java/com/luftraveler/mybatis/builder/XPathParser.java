package com.luftraveler.mybatis.builder;

import com.luftraveler.mybatis.exception.BuilderException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import java.io.InputStream;

public class XPathParser {
    /**
     * XPath 是一门在 XML 文档中查找信息的语言
     */
    private XPath xPath;

    private Document document;

    public XPathParser(InputStream inputStream) {
        this.xPath = cerateXPath();
        this.document = createDocument(new InputSource(inputStream));
    }

    public XPath getxPath() {
        return xPath;
    }

    public void setxPath(XPath xPath) {
        this.xPath = xPath;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public XPath cerateXPath() {
        XPathFactory xPathFactory = XPathFactory.newInstance();
        return xPathFactory.newXPath();
    }

    /**
     * jdk自带的dom解析
     *
     * @param inputSource
     * @return
     */
    private Document createDocument(InputSource inputSource) {
        // important: this must only be called AFTER common constructor
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

            factory.setNamespaceAware(false);
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(false);
            factory.setCoalescing(false);
            factory.setExpandEntityReferences(true);

            DocumentBuilder builder = factory.newDocumentBuilder();
//            builder.setEntityResolver(entityResolver);
            builder.setErrorHandler(new ErrorHandler() {
                @Override
                public void error(SAXParseException exception) throws SAXException {
                    throw exception;
                }

                @Override
                public void fatalError(SAXParseException exception) throws SAXException {
                    throw exception;
                }

                @Override
                public void warning(SAXParseException exception) throws SAXException {
                    // NOP
                }
            });
            return builder.parse(inputSource);
        } catch (Exception e) {
            throw new BuilderException("Error creating document instance.  Cause: " + e, e);
        }
    }

    /**
     * 解析节点
     *
     * @param exp 表达式
     * @return
     */
    public Node xNode(String exp) {
        Node node = null;
        try {
            Object evaluate = xPath.evaluate(exp, document, XPathConstants.NODE);
            node = (Node) evaluate;
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        }
        return node;
    }
}
