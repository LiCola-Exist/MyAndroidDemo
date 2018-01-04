package com.example.licola.myandroiddemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipOutputStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link XmlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class XmlFragment extends Fragment {
  private static final String ARG_PARAM1 = "param1";

  private String mParam1;

  private TextView txtResult;

  public XmlFragment() {
    // Required empty public constructor
  }


  public static XmlFragment newInstance(String param1) {
    XmlFragment fragment = new XmlFragment();
    Bundle args = new Bundle();
    args.putString(ARG_PARAM1, param1);
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mParam1 = getArguments().getString(ARG_PARAM1);
    }
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View viewRoot=inflater.inflate(R.layout.fragment_xml, container, false);
    txtResult= (TextView) viewRoot.findViewById(R.id.txt_result);
    return viewRoot;
  }

  @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    try {
      //打开xml文件
      InputStream inputStream= getContext().getAssets().open("subject.xml");
      //得到build工厂对象
      DocumentBuilderFactory builderFactory=DocumentBuilderFactory.newInstance();
      //得到build对象
      DocumentBuilder builder=builderFactory.newDocumentBuilder();
      //根据输入 得到Document对象数据
      Document document=builder.parse(inputStream);
      //得到 根节点
      Element element=document.getDocumentElement();
      //得到根节点包含的 某个名称的全部节点
      NodeList nodeList= element.getElementsByTagName("language");
      for (int i = 0; i < nodeList.getLength(); i++) {
        Node node= nodeList.item(i);
        Element chidElement= (Element) node;
        txtResult.append(chidElement.getAttribute("id")+"\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParserConfigurationException e) {
      e.printStackTrace();
    } catch (SAXException e) {
      e.printStackTrace();
    }
  }
}
