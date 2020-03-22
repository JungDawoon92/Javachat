import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;


public class ChatApp {
	
	ServerSocket serverSocket = null;
	Socket socket = null;
	Map<String, PrintWriter> clientMap;
	
	//생성자
	public ChatApp() {
		// 클라이언트의 출력스트림을 저장할 해쉬맵 생성
		clientMap = new HashMap<String, PrintWriter>();
		//해쉬맵 동기화 설정
		Collections.synchronizedMap(clientMap); // 지금 느끼는건. 누락없이 순서대로 In 하기위해 하는것 같음.
	}
		
		
	
	// #2
	Connection con;
	PreparedStatement pstmt1;
	PreparedStatement pstmt2;
	PreparedStatement pstmt3;
	
	
	
	// #1
	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("연결되었습니다.1");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}
	
	// #3
	public void connectDatabase() {
		try {
			con = DriverManager.getConnection(
				"jdbc:oracle:thin:@localhost:1521:xe",
				"scott",
				"tiger");
			System.out.println("연결되었습니다.2");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	public static void main(String[] args) {
		//서버 객체 생성.
		ChatApp ms = new ChatApp();
		ms.init(); //소캣 자동연결 쓰래드
//		ms.doRun(); //실행. 3/22 버려도될것같음.
	}
	
	public void init() {
		try {
			serverSocket = new ServerSocket(9999); //9999 포트로 서버소켓 객체생성.
			System.out.println("서버가 시작되었습니다.");
			
			while(true) {
				socket = serverSocket.accept();
				System.out.println(socket.getInetAddress()+":"+socket.getPort());
				
				Thread mst = new MultiServerT(socket); // 쓰레드 생성.
				mst.start(); // 쓰레드 시동.
			}
		
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	////////////////////////////////////////////////////////////
	// 내부 클래스
	// 클라이언트로 부터 읽어온 메세지를 다른 클라이언트(socket)에 보내는 역할을 하는 메서드
	
	class MultiServerT extends Thread{
		Socket socket;
		PrintWriter out = null;
		BufferedReader in = null;
		
		// 생성자.
		public MultiServerT(Socket socket) {
			this.socket = socket;
			try {
				out = new PrintWriter(this.socket.getOutputStream(), true);
				in = new BufferedReader(new InputStreamReader(
												this.socket.getInputStream() ));
			} catch (Exception e) {
				System.out.println("예외:"+e);
			}
		}
		
		
		// 쓰레드를 사용하기 위해서 run() 메서드 재정의
		
		@Override
		public void run() {
					
			//String s = "";
			String name = ""; // 클라이언트로부터 받은 이름을 저장할 변수.
			String choice ="";
			connectDatabase();
			
			try {
				doRun();
				// 작동되는 부분 확인 case문으로 바꾸자.
				
				choice = in.readLine();
				if(choice.equals("1")) {
					String sql = "insert into ClientInfo values(?, ?, ?)";
					
					out.println("ID : ");
					String ID = in.readLine();
					out.println("비밀번호  : ");
					String NAME = in.readLine();
					out.println("별명 : ");
					String CHA = in.readLine();
					String s = "";
					
					try {
						pstmt1 = con.prepareStatement(sql);
						pstmt1.setString(1, ID);
						pstmt1.setString(2, NAME);
						pstmt1.setString(3, CHA);
						int updateCount = pstmt1.executeUpdate();
						System.out.println("데이터베이스에 추가되었습니다.");
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("데이터베이스 입력 에러입니다.");
					}
					
					
					while (in!=null) {
						s = in.readLine();
						System.out.println(s);
	
						if(s.equals("/list"))
							list(out);
						else
							sendAllMsg(name, s);
					}
				}
				
				
				
				else if(choice.equals("2")) {
					name =in.readLine(); // 클라이언트에서 처음으로 보내는 메세지는
					 // 클라이언트가 사용할 이름이다.
					sendAllMsg("", name + "님이 입장하셨습니다.");

					//현재 객체가 가지고 있는 소캣을 제외하고 다른 소켓(클라이언트)들에게 접속을 알림.
					clientMap.put(name, out); //해쉬맵에 키를 name 으로 출력스트림 객체를 저장.

					//System.out.println("현재 접속자 수는 " +clientMap.size()+"명 입니다.");

					// 입력스트림이 null이 아니면 반복.
					String s = "";
					while (in!=null) {
						s = in.readLine();
						System.out.println(s);
	
						if(s.equals("/list"))
							list(out);
						else
							sendAllMsg(name, s);
					}

					//System.out.println("Bye...");
					
					
				}
				
				
				
			
			} catch(Exception e) {
				System.out.println("예외:"+e);
			} finally {
				//예외가 발생할때 퇴장. 해수맵에서 해당 데이터 제거.
				//보통 종료하거나 나가면 java.net.SocketException : 예외 발생.
				
				clientMap.remove(name);
				sendAllMsg("", name + "님이 퇴장하셨습니다.");
				System.out.println("현재 접속자 수는 " +clientMap.size()+"명 입니다.");
				
				try {
					in.close();
					out.close();
					
					socket.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		
		// 접속된 모든 클라이언트들에게 메세지를 전달.
		public void sendAllMsg(String user, String msg) {
			
			// 출력 스트림을 순차적으로 얻어와서 해당 메세지를 출력한다.
			Iterator<String> it = clientMap.keySet().iterator();
			
			while (it.hasNext()) {
				try {
					PrintWriter it_out = (PrintWriter) clientMap.get(it.next());
					if (user.equals(""))
						it_out.println(msg);
					else
						it_out.println("["+user+"]"+ msg);
				} catch(Exception e) {
					System.out.println("예외 :"+e);
				}
			}
		}
		
		// 접속자 리스트 보내기
		public void list(PrintWriter out) {
			// 출력스트림을 순차적으로 얻어와서 해당 메세지를 출력한다.
			Iterator<String> it = clientMap.keySet().iterator();
			String msg = "사용자 리스트[";
			while (it.hasNext()) {
				msg += (String)it.next() + ",";
			}
			msg = msg.substring(0,msg.length()-1) +"]";
			try {
				out.println(msg);
			} catch (Exception e) {
			}
		}
		
		public void NewMember() {
			
		}
		
		public void Member() {
			
		}
		
		public void DelMember() {
			
		}
		
		public void ShowMenu() {
			
		}
		
		public void doRun() {
			String choice;
			while(true) {
				ShowMenu();
				Scanner sc = new Scanner(System.in);
				choice = sc.nextLine();
				
				switch (choice) {
				case "1":
					NewMember();
					break;
				case "2":
					Member();
					break;
				case "3":
					DelMember();
					break;
				case "4":
					System.out.println("프로그램을 종료합니다.");
					return;
				default :
					System.out.println("잘 못 입력하셨습니다.");
					break;
				}
			}
		}
		
		
		
	}

}











