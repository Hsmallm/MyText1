package com.example.administrator.text1.newAndroid.other.web;

import com.example.administrator.text1.newAndroid.other.LogUtil;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author HuangMing on 2017/12/31.
 *         功能描述：解析XML文件之SAX解析(注：相较与Pull确实比较复杂，但是语义会更加清晰)
 */

public class MyHandler extends DefaultHandler {

    private String nodeName;
    private StringBuffer id;
    private StringBuffer name;
    private StringBuffer version;

    /**
     * 开始解析XML时候调用
     *
     * @throws SAXException
     */
    @Override
    public void startDocument() throws SAXException {
        id = new StringBuffer();
        name = new StringBuffer();
        version = new StringBuffer();
    }

    /**
     * 开始解析某个节点时候调用
     *
     * @param uri
     * @param localName
     * @param qName
     * @param attributes
     * @throws SAXException
     */
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);
        nodeName = localName;
    }

    /**
     * 开始获取某个节点内容时候调用
     *
     * @param ch
     * @param start
     * @param length
     * @throws SAXException
     */
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if ("id".equals(nodeName)) {
            id.append(ch, start, length);
        } else if ("name".equals(nodeName)) {
            name.append(ch, start, length);
        } else if ("version".equals(nodeName)) {
            version.append(ch, start, length);
        }
    }

    /**
     * 完成解析某个节点时调用
     *
     * @param uri
     * @param localName
     * @param qName
     * @throws SAXException
     */
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if ("app".equals(localName)) {
            LogUtil.d("MyHandler", "id is" + id.toString().trim());
            LogUtil.d("MyHandler", "name is" + name.toString().trim());
            LogUtil.d("MyHandler", "version is" + version.toString().trim());

            //最后清空相应的StringBuilder
            id.setLength(0);
            name.setLength(0);
            version.setLength(0);
        }
    }

    /**
     * 完成解析整个XML时调用
     *
     * @throws SAXException
     */
    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
}
