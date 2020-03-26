import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class ChatApp {
	
	ServerSocket serverSocket = null;
	Socket socket = null;
	Map<String, PrintWriter> clientMap;
	int count = 0;
	Room[] RoomTotal = new Room[3]; 

	
	//생성자
	public ChatApp() {
		// 클라이언트의 출력스트림을 저장할 해쉬맵 생성
		clientMap = new HashMap<String, PrintWriter>();
		//해쉬맵 동기화 설정
		Collections.synchronizedMap(clientMap); // 지금 느끼는건. 누락없이 순서대로 In 하기위해 하는것 같음. 3/21
	}
		
	Connection con;
	PreparedStatement pstmt1;
	PreparedStatement pstmt2;
	PreparedStatement pstmt3;
	String ip;
	

	static {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("연결되었습니다.1");
		} catch (ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}
	}
	
	
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
	}
	
	public void init() {
		try {
			serverSocket = new ServerSocket(9999); //9999 포트로 서버소켓 객체생성.
			System.out.println("서버가 시작되었습니다.");
			
			while(true) {
				socket = serverSocket.accept();
				System.out.println(socket.getInetAddress()+":"+socket.getPort());
				ip = socket.getInetAddress().toString();
				
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
		
		@Override
		public void run() {
					
//			String s = "";
			String name = ""; 
			connectDatabase();
			
			try {
				String wow="";
				wow=in.readLine();
				out.println(wow+"님 환영합니다.");
				
				
				while(true) {                                                      //메인메소드
					name = doRun(in,out);
					
					if(name.equals("")) {
						out.println("프로그램을 종료합니다. 이용해주셔서 감사합니다.");
						break;
					} else {
						ShowRoom(name,out);
					}
				}
				
			
			} catch(Exception e) {
				System.out.println("예외:"+e);
			} finally {
				
				try {
					in.close();
					out.close();
					socket.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		public void NewMember(BufferedReader in, PrintWriter out) throws IOException {
		
			String id ="";
			String sql ="";
			
			out.println("=====================================================");
			out.println(" ");
			out.println("*주의* ID는 가입하면, 채팅창에 표시되는이름입니다. 신중하게 결정해주세요. ");
			out.println(" ");
			out.println("*주의* IP 하나당 한개의 아이디만 만드실수 있습니다. ");
			out.println(" ");
			out.println("*아이디 제한* 2자~10자 숫자영어한글 모두가능");
			out.println(" ");
			out.println("=====================================================");
			
			
			while(true) {
	
				out.println("ID 중복가입확인 : ");
				
				while(true) {
					id = in.readLine();
					if(id.length() >= 2 && id.length() <= 10) {
						break;
					}
					else {
						out.println("아이디는 2자이상 10자이하여야 합니다.");
					}
				}
				
				sql = "select * from clientinfo where id = ?";
				
				try {
					pstmt2 = con.prepareStatement(sql);
					pstmt2.setString(1, id);
					ResultSet rs = pstmt2.executeQuery();
					
					if(rs.next()) {
						out.println("============================="+rs.getString(1)+"<<해당아이디는 이미 가입되어있습니다>>=======================");
					} else {
						out.println("축하합니다. 해당아이디로 가입을 진행하겠습니다.");
						break;
					}
					rs.close();
				} catch (Exception e) {
					System.out.println("알 수 없는 에러가 발생했습니다.");
				}
			}
			
			sql = "insert into ClientInfo values(?, ?, ?, ?)";
			out.println("비밀번호  : ");
			String pwd = in.readLine();
			out.println("별명 : ");
			String cha = in.readLine();
			
			
			try {
				pstmt1 = con.prepareStatement(sql);
				pstmt1.setString(1, id);
				pstmt1.setString(2, pwd);
				pstmt1.setString(3, ip);
				pstmt1.setString(4, cha);
				int updateCount = pstmt1.executeUpdate();
				System.out.println("데이터베이스에 추가되었습니다.");
				
				
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("데이터베이스 입력 에러입니다.");
			}
		}
			

		
		public String Member(BufferedReader in, PrintWriter out) throws IOException {
			
			while(true) {
				out.println("아이디를 입력하세요.");
				String id = in.readLine();
				out.println("비밀번호를 입력하세요.");
				String pwd = in.readLine();
				
				String Did = ""; // 데이터베이스에있는 id
				String Dpw = ""; // 데이터베이스에있는 비밀번호

				
				String sql = "select * from ClientInfo where id = ?";
				try {
					pstmt3 = con.prepareStatement(sql);
					pstmt3.setString(1, id);
					ResultSet rs = pstmt3.executeQuery();
//					Right.equals(map)
					if(rs.next()) {
						Did = rs.getString(1);
						Dpw = rs.getString(2);
						
						if(id.equals(Did) && pwd.equals(Dpw)) {
							out.println("로그인되셨습니다.");
							
							return Did; // 로그인이되면 return이아닌 채팅방 입장.
						} else {
							out.println("아이디나 비밀번호가 틀립니다.");
						}
					} else {
						out.println("아이디나 비밀번호가 틀립니다.");
					}
					
					rs.close();
				} catch (Exception e) {
					System.out.println("알 수 없는 에러가 발생했습니다.");
				}
				
			}
		}
			
			
		public void DelMember() {
			//(구현예정)
			
		}
		
		public void ShowMenu() {
			//(구현예정)
		}
		
		public String doRun(BufferedReader in, PrintWriter out) throws IOException {
			while(true) {
				
				//ShowMenu();// 아래문구 showmenu로 변경예정.
				out.println("=====================================================");
				out.println("숫자로 입력해주세요.");
				out.println("1번 :  회원가입 ");
				out.println(" ");
				out.println("2번 :  기존회원");
				out.println(" ");
				out.println("3번 :  회원탈퇴(구현예정)");
				out.println(" ");
				out.println("4번 :  종료하기");
				out.println("=====================================================");
		
				
				String ch = "";
				String choice = in.readLine();
				
				if(choice.equals("1")) {
					out.println("회원가입을 진행하겠습니다.");
				}
				else if(choice.equals("2")) {
					out.println("로그인창");
				}
		
				
				switch (choice) {
				case "1":
					NewMember(in,out);
					break;
				case "2":
					ch = Member(in,out);
					return ch;
				case "3":
					DelMember();
					break;
				case "4":
					return ch;
				default :
					out.println("잘 못 입력하셨습니다.");
					break;
				}
			}
		}
		
		public void ShowRoom(String name, PrintWriter out) throws IOException{
			String choice ="" ;
			String roomname ="";
			String a = "";
			String id = name;
			
			while(true) {
				if(count != 3 ) {
					out.println("=====================================================");
					out.println(" ");
					out.println("1.게임방만들기");
					out.println(" ");
					out.println("2.(승부)게임방입장하기");
					out.println(" ");
					out.println("3.(대화)대기실로가기-----꼭 먼저 이용해서 채팅을쳐보세요.");
					out.println(" ");
					out.println("4.돈벌기(용무기강화게임)--9강성공시 3만원!");
					out.println(" ");
					out.println("5.로그인창으로 돌아가기");
					out.println(" ");
					out.println("현재 만들어진 방의갯수"+count+"개");
					out.println(" ");
					out.println("=====================================================");
				}
				
				else {
					out.println("=====================================================");
					out.println(" ");
					out.println("1.게임방만들기(더이상 방을 만들 수 없습니다.)");
					out.println(" ");
					out.println("2.(승부)게임방입장하기");
					out.println(" ");
					out.println("3.(채팅방)대기실로가기-----꼭 먼저 이용해서 채팅을쳐보세요.");
					out.println(" ");
					out.println("4.돈벌기(용무기강화게임)--9강성공시 3만원!");
					out.println(" ");
					out.println("5.로그인창으로 돌아가기");
					out.println(" ");
					out.println("현재 만들어진 방의갯수"+count+"개");
					out.println(" ");
					out.println("=====================================================");
				}
				
				choice = in.readLine();
				
				if(choice.equals("1")) {
					
					int i = 0;
					int b = -1;
			
					for(i =0; i < RoomTotal.length; i++) {
						if(RoomTotal[i] == null) {
							b=i;
							break;
						}
					}
					int z=0;
					if(b>=0) {
						out.println("만드실 방이름을 입력해주세요. (나가기는 숫자2)");
						roomname = in.readLine();
						if(roomname.equals("2"))
							return;
						out.println("방이 성공적으로 완성되었습니다.");
						out.println("   ");
						out.println("참가자를 기다리는 중입니다...");
						RoomTotal[b] = new Room();
						z=count;
						count++;
					}
					
					
					else if(0 > b){
						out.println("더이상 방을 만들 수 없습니다.");
						break;
					}
					
								
					RoomTotal[z].newRoom(roomname, id, out);
					System.out.println((z+1) + "."+"방제목:" + "["+RoomTotal[z].title+"]");
							
					//채팅
					String s = "";
					
					int h = count;//카운트 숫자조절 안전장치
					
					try {
						while (in != null) {
							s = in.readLine();
							System.out.println(s);
			
							if(s.equals("/out")) {
								s="방장님이 나가셨습니다";
								RoomTotal[z].RoomAllMsg(s,id);
								RoomTotal[z]=null;
								h=count;
								count--;
								ShowRoom(name,out);
							}
							
							else
								RoomTotal[z].RoomAllMsg(s,id);
						}
					} catch(Exception e) {
						System.out.println("예외:"+e);
					} finally {
						clientMap.remove(name);
						RoomTotal[z]=null;
						
						if(h==count)
						count--;
						
						try {
							in.close();
							out.close();
							socket.close();
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
					
	//========================================================================================================================================
					
				} else if (choice.equals("2")){
					
					try {
						for(int i = 0; i < RoomTotal.length; i++) {
								out.println((i+1) + "."+"방제목:" + "["+RoomTotal[i].title+"]");	
							
						}
					} catch (Exception e) {
						out.println("====================================");
					}
					
					int num;
					out.println("들어가실 방번호를 입력하세요. 나가기  숫자3");
				
					String s = "";
					while(true) {
						s = in.readLine();
						num =  Integer.parseInt(s);
						if(num==3) {
							ShowRoom(name,out);
						}
						
						if(RoomTotal[num-1].fullroom()==0) {
							break;
						}
						else
							out.println("방이 가득 찾습니다.");
					}

					
					out.println("방에 입장하셨습니다.");
					RoomTotal[num-1].RoomAllMsg("",id+"님이 입장하셨습니다.");
					RoomTotal[num-1].RoomChat(id,out); 
					
					try {
						while (in!=null) {
							s = in.readLine();
							
							if(RoomTotal[num-1]==null) {
								out.println("방장님이 방을 나갔습니다. 로비로이동합니다.");
								ShowRoom(name,out);
							}
							else if(s.equals("/out")) {
								out.println("채팅방에서 나가셨습니다.");
								RoomTotal[num-1].RoomAllMsg("", name + "님이 퇴장하셨습니다.");
								RoomTotal[num-1].RoomAllMsg(s,id);
								ShowRoom(name,out);
							}
							else
								RoomTotal[num-1].RoomAllMsg(s,id);
						}
					} catch(Exception e) {
						System.out.println("예외:"+e);
					} finally {
						RoomTotal[num-1].RoomAllMsg("", name + "님이 퇴장하셨습니다.");
						RoomTotal[num-1].RoomAllMsg(s,id);
						RoomTotal[num-1].emptyroom();
						clientMap.remove(name);
						
						try {
							in.close();
							out.close();
							socket.close();
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				else if(choice.equals("3")) {
					out.println("=====================================================");
					out.println(" ");
					out.println("  공개채팅방에 입장하셨습니다   ");
					out.println(" ");
					out.println("*기능* /list 참여자보기 /out 로비로가기 ");
					out.println(" ");
					out.println("다른기능 없음 구현 예정 ㅎ-ㅎ");
					out.println(" ");
					out.println("=====================================================");
					out.println("채팅방");
					try {
						sendAllMsg("", name + "님이 입장하셨습니다.");
						
						clientMap.put(name, out); 
						
						String s = "";
						while (in!=null) {
							s = in.readLine();
							System.out.println(s);
							
							if(s.equals("/list"))
								list(out);
							
							else if(s.equals("/out")) {
								out.println("선택지로 이동합니다.");
								clientMap.remove(name);
								sendAllMsg("", name + "님이 퇴장하셨습니다.");
								ShowRoom(name,out);
							}
							else
								sendAllMsg(name, s);
						}
						
					} catch(Exception e) {
						System.out.println("예외:"+e);
					} finally {
						
						clientMap.remove(name);
						sendAllMsg("", name + "님이 퇴장하셨습니다.");
						
						try {
							in.close();
							out.close();
							
							socket.close();
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				else if(choice.equals("4")) {
					
					
					try {
						out.println("용무기 강화 게임을 시작합니다.");
						out.println("X공지사항X");
						out.println("게임속 돈은 원래 계정의 돈에 영향을 주지않습니다. 9강성공시에 30000원을 드립니다!");
						out.println("  ");
						out.println("처음에 시작하거나, 무기가 파괴되었을때 다시 try하는 비용은 1000원입니다.");
						out.println("소지금이 0원이면 Game Over 입니다.");
						out.println("  ");
						
						
						Good gms = new Good();
						
						for(int i=1; i>0; i++) {	
							   int money = gms.money;
							   if(money < 1000) {
								   out.println("돈이 부족합니다. Game Over");
								   ShowRoom(name,out);
							   }
							   out.println("  ");
							   out.println("용무기 강화에 도전하시겠습니까? \n 1강->2강 확률90%  (도전 1 입력/게임종료 2 입력)");
							   out.println("  ");
							   out.printf("소지금  %d원 [용무기는 1000원 입니다.] \n",money);
							   
							   String s = in.readLine();
							   int trS =Integer.parseInt(s);
							   
							   if(trS == 1) {
								   gms.Goodgame(in,out);
							   }
							   else if(trS == 2) {
								  System.out.println("게임을 종료합니다.");
								  ShowRoom(name,out);
							   }
						   }
					} catch(Exception e) {
						System.out.println("예외:"+e);
					} finally {
						try {
							in.close();
							out.close();
							socket.close();
						} catch(Exception e) {
							e.printStackTrace();
						}
					}
				}
				
				else if(choice.equals("5")) {
					return;
				}
			}
		}
		
		
		//===============================================================================================================
		//대기방 전용
		public void sendAllMsg(String user, String msg) {
			
			Iterator<String> it = clientMap.keySet().iterator();
			
			while (it.hasNext()) {
				try {
					PrintWriter it_out = (PrintWriter) clientMap.get(it.next());
					if (user.equals(""))
						it_out.println(msg);// ~님이 입장하셨습니다. 퇴장하셨습니다 를 만들기위한 장치.
					else
						it_out.println("["+user+"]"+ msg);
				} catch(Exception e) {
					System.out.println("예외 :"+e);
				}
			}
			
		}
		        //대기방 전용
				public void list(PrintWriter out) {
					
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
	}
}











