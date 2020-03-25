import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Room {
	String title;
	Map<String, PrintWriter> RoomMap;
	
	public Room() {
		RoomMap = new HashMap<String, PrintWriter>();
		Collections.synchronizedMap(RoomMap);
	}
	
	public void newRoom(String title, String id, PrintWriter out) {
		this.title = title;
		
		RoomMap.put(id, out);
	}
	public void RoomChat(String id, PrintWriter out) {
		
		RoomMap.put(id, out);
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
