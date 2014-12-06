package com.GooglePP.app.GooglePP;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.lucene.queryparser.xml.DOMUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XmlParser {

	static void removeWhiteSpaceNodes(Document doc) {
		removeWhiteSpaceNodes(doc.getFirstChild());
	}

	static void removeWhiteSpaceNodes(Node node) {
		NodeList children = node.getChildNodes();
		for (int i = 0; i < children.getLength(); ++i) {
			boolean removed = false;
			Node currentNode = children.item(i);
			if (currentNode.getNodeType() == Node.TEXT_NODE) {
				removed = currentNode.getTextContent().trim().length() == 0;
				if (removed) {
					currentNode.getParentNode().removeChild(currentNode);
					--i;
				}
			}
			if (!removed) {
				removeWhiteSpaceNodes(currentNode);
			}
		}
	}

	public static Document loadDocument(String filePath) {
		File file = new File(filePath);
		Document doc = null;
		if (file.exists() && file.canRead()) {
			try {
				FileInputStream iStream = new FileInputStream(file);
				InputStreamReader inputReader = new InputStreamReader(iStream);
				doc = DOMUtils.loadXML(inputReader);
				inputReader.close();
			} catch (IOException e) {
				System.err.println("Couldn't open: " + file.getAbsolutePath());
				e.printStackTrace();
				return null;
			}
		} else {
			System.err.println("Couldn't open: " + file.getAbsolutePath());
		}
		return doc;
	}

	public static List<Doc> getDocs(String filePath) {
		Document doc = loadDocument(filePath);

		removeWhiteSpaceNodes(doc);
		NodeList nodeList = doc.getChildNodes().item(0).getChildNodes();
		List<Doc> docs = new ArrayList<Doc>();
		DateFormat dateFormat = new SimpleDateFormat("DD-MMM-YYYY HH:mm:ss.SS",
				Locale.ENGLISH);

		for (int i = 0; i < nodeList.getLength(); ++i) {
			Node currentNode = nodeList.item(i);
			int id = Integer.valueOf(currentNode.getAttributes()
					.getNamedItem("NEWID").getNodeValue());
			Date date = null;
			String title = "";
			String text = "";
			NodeList children = currentNode.getChildNodes();

			for (int childIndex = 0; childIndex < children.getLength(); ++childIndex) {
				Node childNode = children.item(childIndex);
				if (childNode.getNodeName().equals("DATE")) {
					try {
						String dateString = childNode.getFirstChild()
								.getNodeValue().trim();
						date = dateFormat.parse(dateString);
					} catch (ParseException e) {
						e.printStackTrace();
					}
				} else if (childNode.getNodeName().equals("TEXT")) {
					NodeList textChildren = childNode.getChildNodes();
					for (int textChildIndex = 0; textChildIndex < textChildren
							.getLength(); ++textChildIndex) {
						Node textChildNode = textChildren.item(textChildIndex);
						if (textChildNode.getNodeName().equals("TITLE")) {
							title = textChildNode.getFirstChild()
									.getNodeValue();
						} else if (textChildNode.getNodeName().equals("BODY")) {
							text = textChildNode.getFirstChild().getNodeValue();
						}
					}
				}
			}
			docs.add(new Doc(id, date, title, text));
		}

		return docs;
	}

//	public static void main(String[] args) {
//		String filePath = "reut2-000.xml";
//		List<Doc> docs = getDocs(filePath);
//		for (int i = 0; i < docs.size(); ++i) {
//			Doc currentDoc = docs.get(i);
//			System.out.println("ID: " + currentDoc.getId());
//			System.out.println("DATE: " + currentDoc.getDate());
//			System.out.println("TITLE: " + currentDoc.getTitle());
//			System.out.println("TEXT: " + currentDoc.getText().split("\n")[0]);
//			System.out.println("####################################################################");
//		}
//	}
}
