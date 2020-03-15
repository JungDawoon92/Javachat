package March_2020;
import java.util.Random;
import java.util.Scanner;

//용무기 9강이 되면 축하합니다 메소드

class Good {
	int tri;
	int money = 5000;
	Scanner sc = new Scanner(System.in);
	Random r = new Random();
	
	public void Goodgame() {
		
		int n = 2;
		double a =1;
		double fac=1;
		
		int b =5;//time wait
		
		money = money-1000;
		System.out.printf("남은돈: %d \n",money);
		
		for(int i = 9; i>1; i--) {
			
			a = i*0.1;
			fac = fac*a;
			
			if(i==b) {
				int timeToWait = 5; //second
				System.out.print("(강화중) \n");
		        try {
		            for (int q=0; q<timeToWait ; q++) {
		                Thread.sleep(1000);
		                System.out.print(".");
		            }
		        } catch (InterruptedException ie)
		        {
		            Thread.currentThread().interrupt();
		        }
		        b--;
			}
			
			if(r.nextInt(10)<i) {
				int sell = ((1000+((int)(4000*(1-(fac)))))/100)*100;
				System.out.println("");
				System.out.printf("무기강화에 성공하셨습니다. %d강 \n",n);
				System.out.printf("☆%d강☆ 에 도전하시겠습니까?(도전 : 1번입력) \n 확률 %d%% \n",n+1,(10-n)*10);
				System.out.printf("판매금액: %d원(판매 : 2번입력)  \n",sell);
				
				tri =sc.nextInt();
				
				if(tri == 1) {
				}
				
				else if(tri == 2){
					System.out.printf("용무기를 판매하셨습니다.  \n");
					money = money +sell;
					break;
				}
			}
			
			else {
				System.out.println("");
				System.out.println("실패되어 용무기가 파괴되었습니다.");
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
			   if(money < 1000) {
				   System.out.println("돈이 부족합니다. Game Over");
				   break;
			   }
			   
			   System.out.println("용무기 강화에 도전하시겠습니까? \n 1강->2강 확률90%  (도전 1 입력/게임종료 2 입력)");
			   System.out.printf("남은돈 %d원 [용무기는 1000원 입니다.] \n",money);
			   
			   int trS =gms.sc.nextInt();
			   
			   if(trS == 1) {
				   gms.Goodgame();
			   }
			   else if(trS == 2) {
				  System.out.println("게임을 종료합니다.");
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
