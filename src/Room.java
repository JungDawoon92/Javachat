import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Room {
	String title;
	String id;
	Map<String, PrintWriter> clientMap;
	
	
	public Room() {
		clientMap = new HashMap<String, PrintWriter>();
		Collections.synchronizedMap(clientMap);
	}
	
	public void newRoom(String title, String id) {
		this.title = title;
		this.id = id;
	}
	
	public void RoomAllMsg(String user, String msg) {
		
		// 출력 스트림을 순차적으로 얻어와서 해당 메세지를 출력한다.
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
	
	
	

}
