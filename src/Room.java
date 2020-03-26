import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Room {
	String title; // 사람들에게 보여지는 방이름.
	String hiddentitle; // 도전자가 나갔을때 변경할 방이름
	
	
	int count = 0;
	
	int gamecount=0; // 게임에 응해야지만 시작된다.
	
	int startcount = 0; // 상대방 입력 대기 카운트.
	
	
	
	Map<String, PrintWriter> RoomMap;
	
	Map<String, PrintWriter> GameMap;
	
	Map<String, String> AnwserMap;
	
	public Room() {
		RoomMap = new HashMap<String, PrintWriter>();
		
		GameMap = new LinkedHashMap<String, PrintWriter>();
		
		AnwserMap = new LinkedHashMap<String, String>();
		
		Collections.synchronizedMap(RoomMap); // 모든뜨레드를 동기화하는 안전장치인듯함 3/25
		Collections.synchronizedMap(GameMap);
		Collections.synchronizedMap(AnwserMap);
	}
	
	public void newRoom(String title, String id, PrintWriter out) {
		hiddentitle = title;
		title = title+"(대기중)";
		this.title = title;
		RoomMap.put(id, out);
	}
	public void RoomChat(String id, PrintWriter out) {
		String h=changeroomname();
		this.title = h;
		RoomMap.put(id, out);
		count ++;
	}
	
	public String changeroomname() {
		return hiddentitle+"(게임중)";
	}
	
	public int fullroom() {
		return this.count;
	}
	public void emptyroom() {
		count--;
	}
	
	public String game(String id, String msg) {
		if(gamecount ==2) {
			System.out.println("스타트");
			return "start";
		}
		return "";
	}
	
	
	public String gameready(String id, PrintWriter out) {
		GameMap.put(id, out); // 없애도될꺼같음.
		gamecount++;
		return "상대방을 기다리는중입니다.. 잠시만 기다리세요";
	}

	
	public void startcount(String id, String msg) {
		AnwserMap.put(id, msg);
		startcount++;
	}
	

	public String gamestart() {
		
		if(startcount ==2) {
			System.out.println("스타트");
			return "start";
		}
		else
			return "";
	}
	
	
	public String result(String id) {
		String x ="";
		Iterator<String> it = AnwserMap.keySet().iterator();
		while (it.hasNext()) {
			x = it.next();
			if(x != id) {
				return AnwserMap.get(x);
			}	
		}
		return AnwserMap.get(x);
	}
	
	
	
	public void RoomAllMsg(String msg, String id) {
		
		// 출력 스트림을 순차적으로 얻어와서 해당 메세지를 출력한다.
		Iterator<String> it = RoomMap.keySet().iterator();
		
		while (it.hasNext()) {
			try {
				PrintWriter it_out = (PrintWriter) RoomMap.get(it.next());
				if (id.equals(""))
					it_out.println(msg);// ~님이 입장하셨습니다. 퇴장하셨습니다 를 만들기위한 장치.
				else if(msg.equals("/out")) {
					RoomMap.remove(id);
					System.out.println("실행되었습니다.");
					count--;
					return;
				}
				else
					it_out.println("["+id+"]"+ msg);
			} catch(Exception e) {
				System.out.println("예외 :"+e);
			}
		}
		
	}
}
