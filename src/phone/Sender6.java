package phone;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Sender6 extends Thread {
	
	Socket socket;
	PrintWriter out = null;
	String name;
	
	//생성자
	public Sender6(Socket socket, String name) {
		
		this.socket = socket;
		try {
			out = new PrintWriter(this.socket.getOutputStream(), true);
			this.name = name;
		} catch(Exception e) {
			System.out.println("에외S3:"+e);
		}
	}
	
	//run()메소드의 재정의
	
	@Override
	public void run() {
		
		Scanner s = new Scanner(System.in);
		try {
			// 서버에 입력한 사용자의 이름을 보내준다.
			out.println(name);
		
		while (out != null) {
			try {
				String s2 = s.nextLine();
				if ( s2.equals("q") || s2.equals("Q" ) ) {
//					out.println(s2);
					break;
				} else {
					// 명령어||상대방ID||대화내용
					// chat||all||s2
					// to||홍길동||s2
					// /list||
					out.println(s2);
				}
			}catch (Exception e) {
				System.out.println("예외:"+e);
			}
		}

		out.close();
		socket.close();
  } catch (Exception e) {
		System.out.println("예외:"+e);
	}
}
}