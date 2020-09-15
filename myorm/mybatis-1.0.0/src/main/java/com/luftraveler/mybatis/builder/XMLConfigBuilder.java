package com.luftraveler.mybatis.builder;

import com.luftraveler.mybatis.config.Configuration;
import com.luftraveler.mybatis.config.Environment;
import com.luftraveler.mybatis.config.MappedStatement;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.util.ConcurrentModificationException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 构建器模式
 */
public class XMLConfigBuilder {

    /**
     * 解析器 解析xml
     */
    private XPathParser xPathParser;

    public XMLConfigBuilder(InputStream inputStream) {
        this.xPathParser = new XPathParser(inputStream);
    }

    public Configuration parse() {
        Node dataSourceNode =xPathParser.xNode("/configuration/environments/environment/dataSource");

        Properties properties = new Properties();
        NodeList propertiesNodeList = dataSourceNode.getChildNodes();
        for (int i = 0; i < propertiesNodeList.getLength(); i++) {
            Node propertyNode = propertiesNodeList.item(i);
            if (propertyNode.getNodeType() == Node.ELEMENT_NODE) {
                properties.setProperty(propertyNode.getAttributes().getNamedItem("name").getNodeValue(),
                        propertyNode.getAttributes().getNamedItem("value").getNodeValue());
            }
        }

        Environment catEnvironment = new Environment();
        catEnvironment.setDriver(properties.getProperty("driver"));
        catEnvironment.setUrl(properties.getProperty("url"));
        catEnvironment.setUsername(properties.getProperty("username"));
        catEnvironment.setPassword(properties.getProperty("password"));


        //读取mapper映射文件配置信息
        Map<String, MappedStatement> mappedStatementMap = new ConcurrentHashMap<String, MappedStatement>();
        Node mappersNode =xPathParser.xNode("/configuration/mappers");
        NodeList mapperNodeList = mappersNode.getChildNodes();
        for (int i = 0; i < mapperNodeList.getLength(); i++) {
            Node mapperNode = mapperNodeList.item(i);
            if (mapperNode.getNodeType() == Node.ELEMENT_NODE) {
                //mapper.xml 文件的位置
                String resource = mapperNode.getAttributes().getNamedItem("resource").getNodeValue();
                //解析该mapper.xml文件
                InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(resource);

                this.xPathParser = new XPathParser(inputStream);

                Element element = xPathParser.getDocument().getDocumentElement();
                String namespace = element.getAttribute("namespace");

                NodeList sqlNodeList = element.getChildNodes();

                for (int i1 = 0; i1 < sqlNodeList.getLength(); i1++) {
                    Node sqlNode = sqlNodeList.item(i1);
                    if (sqlNode.getNodeType() == Node.ELEMENT_NODE) {
                        String id = "";
                        String resultType = "";
                        String parameterType = "";

                        Node idNode = sqlNode.getAttributes().getNamedItem("id");
                        if(idNode == null){
                            throw new RuntimeException("sql id is null");
                        }else{
                            id = idNode.getNodeValue();
                        }

                        Node resultTypeNode = sqlNode.getAttributes().getNamedItem("resultType");
                        if(resultTypeNode != null){
                            resultType = resultTypeNode.getNodeValue();
                        }

                        Node parameterTypeNode = sqlNode.getAttributes().getNamedItem("parameterType");
                        if(parameterTypeNode != null){
                            parameterType = parameterTypeNode.getNodeValue();
                        }

                        String sql = sqlNode.getTextContent();

                        MappedStatement mappedStatement = new MappedStatement();
                        mappedStatement.setId(id);
                        mappedStatement.setNamespace(namespace);
                        mappedStatement.setParameterType(parameterType);
                        mappedStatement.setResultType(resultType);
                        mappedStatement.setSql(sql);
                        //命名空间 加查询的id
                        mappedStatementMap.put(namespace+"."+id,mappedStatement);
                    }
                }
            }
        }

        Configuration catConfiguration = new Configuration();
        catConfiguration.setEnvironment(catEnvironment);
        catConfiguration.setMappedStatementMap(mappedStatementMap);
        return catConfiguration;
    }
}
