package com.chenzuhuang.zomail.core;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;


public class XMLUtil {
	
	private Document dom;
	
	private String configFileName;
	
	public static XMLUtil getXMLUtil(String configFileName){
		try {
			return new XMLUtil(configFileName);
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static XMLUtil getXMLUtil(InputStream input){
		try {
			return new XMLUtil(input);
		} catch (DocumentException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public XMLUtil(String configFileName) throws DocumentException{
		this.setConfigFileName(configFileName);
		SAXReader reader = new SAXReader();
		dom = reader.read(new File(configFileName));
	}
	
	public XMLUtil(InputStream input) throws DocumentException {
		SAXReader reader = new SAXReader();
		dom = reader.read(input);
	}
	
	/**
	 * 获取指定节点的配置值
	 * @param elementName
	 * @return 获取到的节点
	 */
	public String getParam(String elementName){
		Element root = dom.getRootElement();
		return root.element(elementName).getTextTrim();
	}
	
	/**
	 * 设置配置文件某个节点的值，务必手动提交
	 * @param elementName, value
	 * @return
	 */
	public void setParam(String elementName, String value){
		Element root = dom.getRootElement();
		root.element(elementName).setText(value);
	}
	
	/**
	 * 修改配置文件后的提交操作
	 */
	public void commit(){
		XMLWriter writer = new XMLWriter();
		try {
			writer.setOutputStream(new FileOutputStream(new File("")));
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public Document getDom() {
		return dom;
	}

	public void setDom(Document dom) {
		this.dom = dom;
	}


	public String getConfigFileName() {
		return configFileName;
	}


	public void setConfigFileName(String configFileName) {
		this.configFileName = configFileName;
	}
}
