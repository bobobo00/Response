package Http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 获取响应协议
 * @author dell
 *
 */

public class Respones {	
	private Socket client;
	private BufferedWriter bw;
	private final String BLANK=" ";
	private final String  CRLF = "\r\n";
	private int len=0;
	private StringBuilder content;
	private StringBuilder responseInfo;
	public Respones(Socket client) {
		try {
			bw=new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
			content=new StringBuilder();
			responseInfo =new StringBuilder();
		} catch (IOException e) {
			responseInfo=null;
		}
	}
	public Respones() {
		
	}
	public void print(String ss) {
		content.append(ss);
		len+=content.toString().getBytes().length;
	}
	public void println(String ss) {
		content.append(ss).append(CRLF);
		len+=content.toString().getBytes().length+CRLF.length();
	}
	public void pushToBroswer(int code) {
		if(null==responseInfo) {
			code=505;
		}
		if(null!=responseInfo) {
			createHeadInfo(code);
			try {
				bw.append(responseInfo.toString());
				bw.append(content.toString());
				bw.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void createHeadInfo(int code) {
		//1、响应行: HTTP/1.1 200 OK
		responseInfo.append("HTTP/1.1").append(BLANK);
		responseInfo.append(code).append(BLANK);
		switch(code) {
			case 200:
				responseInfo.append("OK").append(CRLF);
				break;
			case 404:
				responseInfo.append("NOT FOUND").append(CRLF);
				break;
			case 505:
				responseInfo.append("SERVER ERROR").append(CRLF);
				break;
		}
		//2、响应头(最后一行存在空行):
		/*
		 Date:Mon,31Dec209904:25:57GMT
		Server:shsxt Server/0.0.1;charset=GBK
		Content-type:text/html
		Content-length:39725426
		 */
		responseInfo.append("Date:").append(new Date()).append(CRLF);
		responseInfo.append("Server:").append("shsxt Server/0.0.1;charset=GBK").append(CRLF);
		responseInfo.append("Content-type:text/html").append(CRLF);
		responseInfo.append("Content-length:").append(len).append(CRLF);
		responseInfo.append(CRLF);
	}
	
	
	

}
