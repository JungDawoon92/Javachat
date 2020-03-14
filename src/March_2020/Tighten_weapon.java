package March_2020;
import java.util.Random;
import java.util.Scanner;

// 다음에 해야할것. 판매하기기능 + 강화시 뜨레드로 지연하기 기능

class Good {
	int tri;
	int money = 5000;
	Scanner sc = new Scanner(System.in);
	Random r = new Random();
	
	public void Goodgame() {
		
		int n = 2;
		money = money-1000;
		System.out.printf("남은돈: %d \n",money);
		
		for(int i = 9; i>1; i--) {

			if(r.nextInt(10)<i) {
				System.out.printf("무기강화에 성공하셨습니다. %d강 \n",n);
				System.out.printf("%d에 도전하시겠습니까?(1번입력) 확률 %d%%",n+1,(10-n)*10);
				tri =sc.nextInt();
				if(tri == 1) {
				}
				else if(tri == 2){
					System.out.printf("용무기를 판매하셨습니다. 현재 자본금 %d \n", money);
					   money = money + 1000;
					   break;
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



public class Tighten_weapon {

	public static void main(String[] args) {
		
		Good gms = new Good();
		
		for(int i=1; i>0; i++) {
			   int money = gms.money;
			   System.out.println("용무기 강화에 도전하시겠습니까? 1강->2강 확률90%  (도전 1 입력/게임종료 2 입력)");
			   System.out.printf("남은돈 %d원 [용무기는 1000원 입니다.] \n",money);
			   int trS =gms.sc.nextInt();
			   if(trS == 1) {
				   gms.Goodgame();
			   }
			   else if(trS == 2) {
				   //종료
			   }
		   }   
	}
}








//if(trS.equals("B")) {
//money = money - 1000;
//System.out.printf("용무기를 구매하셨습니다. 현재 자본금 %d \n",money);
//}
//
//else if(trS.equals("S")) {
//System.out.printf("용무기를 판매하셨습니다. 현재 자본금 %d \n", money);
//money = money + 1000;
//}
//else if(money == 0) {
//break;
//}
//======================================================================================================================================================


//double d = Math.random();




//----------------------------------------------------------		   

//String Buy =  "B";
//String Sell =  "S";
//----------------------------------------------------------
