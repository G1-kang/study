package com.update.semi.dto;

import java.util.Properties;
import java.util.Random;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpRequest;
import org.springframework.web.servlet.resource.HttpResource;

import com.fasterxml.jackson.core.io.IOContext;

public class SMTPDto {
	
	private static final int port=465;
	private String host; 
	private String user; 
	private String tail; 
	private String password;
	
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getTail() {
		return tail;
	}
	public void setTail(String tail) {
		this.tail = tail;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public static int getPort() {
		return port;
	}
	
	private Properties props = System.getProperties();
	
	private boolean setEnv() {
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.psrt", port);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.enable", "true");
		props.put("mail.smtp.trust", host);
		return true; 
	}
	
	 //파일 없이 전송
		public boolean sendMail(String receiver,String title, String text) throws Exception{
			setEnv();
			Message msg = sendingHead();
			sendingBody(msg, receiver, title, text);
			
			msg.setText(text);
	        Transport.send(msg);	
			return true;
		}

		//파일과 함께 전송
		public boolean sendMail(String receiver,String title, String text, String filePath, String fileName) throws Exception{
			setEnv();
			Message msg = sendingHead();
			sendingBody(msg, receiver, title, text);
			
			if(filePath != null && filePath.length() > 0){  
		        Multipart multipart = new MimeMultipart();
		        MimeBodyPart textBodyPart = new MimeBodyPart();
		        textBodyPart.setText(text,"UTF-8");
		        MimeBodyPart attachmentBodyPart= new MimeBodyPart();
		        DataSource source = new FileDataSource(filePath); 
		        attachmentBodyPart.setDataHandler(new DataHandler(source));
		        attachmentBodyPart.setFileName(MimeUtility.encodeText(fileName, "UTF-8", null));
		        multipart.addBodyPart(textBodyPart);  // add the text part
		        multipart.addBodyPart(attachmentBodyPart); // add the attachement part
		        msg.setContent(multipart);			
			}	
			Transport.send(msg);	
	        return true;
		}
		
		//인증번호 만들기 
		public StringBuffer createAuthNo() throws Exception {
			
			//인증번호 생성 
			//MimeMessage 세션을 생성한 뒤 메세지 작성할 때 
			StringBuffer temp = new StringBuffer();
			Random rnd = new Random();
			for(int i =0; i < 10; i++) {
				int rIndex = rnd.nextInt(3); 
				switch (rIndex) {
				case 0:
                    // a-z
                    temp.append((char) ((int) (rnd.nextInt(26)) + 97));
                    break;
                case 1:
                    // A-Z
                    temp.append((char) ((int) (rnd.nextInt(26)) + 65));
                    break;
                case 2:
                    // 0-9
                    temp.append((rnd.nextInt(10)));
                    break;
				}
			}			

			return temp;
		}

		//인증번호 전송 
		public boolean sendAuthNo(String receiver, String temp) throws Exception {
			
			setEnv();
			Message msg = sendingHead();//발신자 로그인 정보를 담아준다-발신자, 수신자, 제목을 세팅하기 위하여 msg생성 
			
			
			//메일제목
			msg.setSubject("안녕하세요 study의 인증 메일입니다");
			//메일내용
			msg.setText("인증번호는 : "+temp+"\n"+"를 입력해 주세요");
			//발신자 셋팅 , 보내는 사람의 이메일주소를 한번 더 입력합니다. 이때는 이메일 풀 주소를 다 작성해주세요.
			msg.setFrom(new InternetAddress(user + tail));  
			//수신자셋팅
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver));   
			
			Transport.send(msg); //메세지 전송 
			return true;
		}
		
		private Message sendingHead(){
			Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
				String un = user;
				String pw = password;
				protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
					return new javax.mail.PasswordAuthentication(un, pw);
				}
			});
			session.setDebug(true); //for debug  
			Message msg = new MimeMessage(session); //MimeMessage 생성 
			return msg;
		}

		private void sendingBody(Message msg, String receiver, String title, String text) throws Exception{
			msg.setFrom(new InternetAddress(user + tail)); //발신자 셋팅 , 보내는 사람의 이메일주소를 한번 더 입력합니다. 이때는 이메일 풀 주소를 다 작성해주세요.  
			msg.setRecipient(Message.RecipientType.TO, new InternetAddress(receiver)); //수신자셋팅  
			msg.setSubject(title); //제목셋팅  
		}
		

		
	}
	

