import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Room {
	String title; // 사람들에게 보여지는 방이름.
	String hiddentitle; // 도전자가 나갔을때 변경할 방이름
	int count = 0;
	
	Map<String, PrintWriter> RoomMap;
	
	public Room() {
		RoomMap = new HashMap<String, PrintWriter>();
		Collections.synchronizedMap(RoomMap); // 모든뜨레드를 동기화하는 안전장치인듯함 3/25
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
