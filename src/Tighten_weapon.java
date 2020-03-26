
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

//용무기 9강이 되면 축하합니다 메소드

class Good {
	int tri;
	int money = 5000;
	Random r = new Random();
	
	public void Goodgame(BufferedReader in,PrintWriter out) throws NumberFormatException, IOException {
		
		int n = 2;
		double a =1;
		double fac=1;
		
		int b =5;//time wait
		
		money = money-1000;
		out.printf("남은돈: %d \n",money);
		
		for(int i = 9; i>1; i--) {
			
			a = i*0.1;
			fac = fac*a;
			
			if(i==b) {
				int timeToWait = 5; //second
				out.print("(강화중) \n");
		        try {
		            for (int q=0; q<timeToWait ; q++) {
		                Thread.sleep(1000);
		                out.println("대장장이가 혼신의 힘을다해 만드는중입니다!");
		                out.flush();
		            }
		        } catch (InterruptedException ie)
		        {
		            Thread.currentThread().interrupt();
		        }
		        b--;
			}
			
			if(r.nextInt(10)<i) {
				int sell = ((1000+((int)(5000*(1-(fac)))))/100)*100;
				out.println("");
				out.printf("무기강화에 성공하셨습니다. %d강 \n",n);
				out.printf("☆%d강☆ 에 도전하시겠습니까?(도전 : 1번입력) \n 확률 %d%% \n",n+1,(10-n)*10);
				out.printf("소지금 %d원  \\ 용무기 판매금액: %d원(판매 : 2번입력)  \n",money,sell);
				
				tri =Integer.parseInt(in.readLine());
				
				if(tri == 1) {
				}
				
				else if(tri == 2){
					out.printf("용무기를 판매하셨습니다.  \n");
					money = money +sell;
					break;
				}
			}
			
			else {
				out.println("");
				out.println("XXXXXXXXXXXXXX실패되어 용무기가 ○파괴○되었습니다.XXXXXXXXXXXXXXXXX");
				break;
			}
			   n++;
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
