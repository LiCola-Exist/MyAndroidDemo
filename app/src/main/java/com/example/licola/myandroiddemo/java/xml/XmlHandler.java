package com.example.licola.myandroiddemo.java.xml;

import android.content.Context;
import android.util.Xml;
import com.licola.llogger.LLogger;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/**
 * @author LiCola
 * @date 2018/9/26
 */
public class XmlHandler {

  public static void main(Context context) throws IOException {
    InputStream inputStreamSubject = context.getAssets().open("subject.xml");
    InputStream inputStreamStudent = context.getAssets().open("student.xml");
    handlerPull(inputStreamStudent);
    handlerDom(inputStreamSubject);
  }


  private static void handlerDom(InputStream inputStream) throws IOException {

    try {
      //打开xml文件
      //得到build工厂对象
      DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
      //得到build对象
      DocumentBuilder builder = builderFactory.newDocumentBuilder();
      //根据输入 得到Document对象数据
      Document document = builder.parse(inputStream);
      //得到 根节点
      Element element = document.getDocumentElement();
      //得到根节点包含的 某个名称的全部节点
      NodeList nodeList = element.getElementsByTagName("language");
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node = nodeList.item(i);
        Element childElement = (Element) node;
        LLogger.d(childElement.getAttribute("id"));
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    }
    inputStream.close();
  }

  private static void handlerPull(InputStream inputStream) throws IOException {
    XmlPullParser parser = Xml.newPullParser();

    List<Student> students = null;
    Student student = null;

    try {
      parser.setInput(inputStream, "UTF-8");
    } catch (XmlPullParserException e) {
      e.printStackTrace();
    }

    int type = 0;
    try {
      type = parser.getEventType();

      while (type != XmlPullParser.END_DOCUMENT) {

        switch (type) {
          //开始标签
          case XmlPullParser.START_TAG:
            if ("students".equals(parser.getName())) {
              students = new ArrayList<>();
            } else if ("student".equals(parser.getName())) {
              student = new Student();
            } else if ("name".equals(parser.getName())) {
              //获取sex属性
              String sex = parser.getAttributeValue(null, "sex");
              student.setSex(sex);
              //获取name值
              String name = parser.nextText();
              student.setName(name);
            } else if ("nickName".equals(parser.getName())) {
              //获取nickName值
              String nickName = parser.nextText();
              student.setNickName(nickName);
            }
            break;
          //结束标签
          case XmlPullParser.END_TAG:
            if ("student".equals(parser.getName())) {
              students.add(student);
            }
            break;
          default:
            break;
        }

        type = parser.next();
      }


    } catch (XmlPullParserException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    LLogger.d(students);
    inputStream.close();

  }
}
