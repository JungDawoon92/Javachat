package March_2020;
import java.util.Random;
import java.util.Scanner;

public class Tighten_weapon {

	public static void main(String[] args) {
//		   double d = Math.random();
		   Scanner sc = new Scanner(System.in);
		   int tri;
		   int n = 2;
		   Random r = new Random();
//----------------------------------------------------------		   
		   int money = 5000;
//		   String Buy =  "B";
//		   String Sell =  "S";
//----------------------------------------------------------
		   
		   for(int i=1; i>0; i++) {
			   String trS =sc.nextLine();
			   
			   if(trS.equals("B")) {
				   money = money - 1000;
				   System.out.printf("용무기를 구매하셨습니다. 현재 자본금 %d \n",money);
			   }
			   
			   else if(trS.equals("S")) {
				   System.out.printf("용무기를 판매하셨습니다. 현재 자본금 %d \n", money);
				   money = money + 1000;
			   }
			   else if(money == 0) {
				   break;
			   }
		   }

		   
//----------------------------------------------------------
		   for(int i = 9; i>1; i--) {
			   if(r.nextInt(10)<i) {
					System.out.printf("무기강화에 성공하셨습니다. %d강 \n",n);
					System.out.printf("%d에 도전하시겠습니까?(1번입력) 확률 %d%%",n+1,(10-n)*10);
					tri =sc.nextInt();
					if(tri == 1) {
					}
				}
				else {
					System.out.println("실패하셨습니다.");
					break;
				}
			   n++;
		   }
	}
}
