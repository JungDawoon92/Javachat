package March_2020;
import java.util.Random;

public class Tighten_weapon {

	public static void main(String[] args) {
//		   double d = Math.random();
		   
		   
		   Random r = new Random();

			if(r.nextInt(10)<7) {
				System.out.println("무기강화에 성공하셨습니다.");
			}
			else System.out.println("실패하셨습니다.");
	}
}
