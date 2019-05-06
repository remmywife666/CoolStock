package com.example.administrator.coolstock.Handler;

import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class RegisteredInfoHandler extends DefaultHandler {

    /**
     * 定义String 用来记录节点 StringBuilder
     *用户名，用户密码
     */
    private String nodeName;
    private StringBuilder userId;
    private  StringBuilder userPassword;

    @Override
    public void startDocument()throws SAXException{ //在开始解析的时候调用
        //初始化
        userId=new StringBuilder();
        userPassword=new StringBuilder();

    }

    @Override
    public void startElement(String uri,String localName,String qName
    ,Attributes attributes)throws SAXException{ //解析某个节点时调用
        Log.d("RegisteredInfoHandler","解析了吗"+nodeName);
        //记录当前节点名
        nodeName=localName;
    }
    @Override
    public void characters(char[]ch,int start,int length)throws SAXException{
        //获取节点内容的时候调用

        Log.d("RegisteredInfoHandler","内容或去了吗"+userId+userPassword);
        /**
         * 根据当前节点名判断将内容添加到哪一个StringBuilder对象中
         */
        if("userId".equals(nodeName)){
            userId.append(ch,start,length);
        }else if("userPassword".equals(nodeName)){
            userPassword.append(ch,start,length);
        }
    }

    @Override
    public void endElement(String uri,String localName,String qName)throws
            SAXException{//完成解析某个节点的时候调用
            if ("userInfo".equals(localName)){
                Log.d("RegisteredInfoHandler","userId is"+userId);
                Log.d("RegisteredInfoHandler","userPassword is"+userPassword.toString().trim());
                //清除StringBuilder里的内容
                userId.setLength(0);
                userPassword.setLength(0);
            }
    }

    @Override
    public void endDocument()throws SAXException{//完成整个XML解析时调用
        super.endDocument();
    }

}
