package March_2020;

import java.util.Random;
import java.util.Scanner;

interface Baseballgame {
	
	public void Comnum();
	public void UserNum();
	public int Answer();
	
}

class Game implements Baseballgame {
	
	int[] Com = new int[3];
	int[] numX = new int[3];
	int count = 0;
	
	public void Comnum() {
		Random randomV1 = new Random();
		int ran1 = (randomV1.nextInt(9)+1);
		Com[0]= ran1;
		for(int i=1; i>0; i++) {
			int ran2 = (randomV1.nextInt(9)+1);
			if(ran1 != ran2) {
				Com[1]=ran2;
				for(int k=1; k>0; k++) {
					int ran3 = (randomV1.nextInt(9)+1);
					if(ran2 != ran3 && ran1 !=ran3) {
						Com[2]=ran3;
						i= -1;
						break;
					}
				}
			}
		}
		System.out.println("컴퓨터 숫자(답안지):");
		System.out.print(Com[0]);
		System.out.print(Com[1]);
		System.out.println(Com[2]);
	};
	
	public void UserNum() {
		
		while(true) {
			Scanner sc = new Scanner(System.in);
			String num2 =sc.nextLine();
			int y = num2.length();
			char[]num3 = new char[y];
			int h=0;

			for(int x=0; x<y; x++) {
				num3[x]=num2.charAt(x);
				if(num3[x]>='0' && '9'>=num3[x]) {
				h++;}
		}
			
			int[] num5 = new int[h];
			
			
			try {
				for(int i =0,z=0; i<y; i++) {
					if(num3[i]>='0' && '9'>=num3[i]) {
						num5[z]= Character.getNumericValue(num3[i]); //캐릭터를 인티저로 바꾸는 방법.
						numX[z]=num5[z];
						z++;
					}	
				}
				
			} catch(Exception e) {
				System.out.println("숫자를 너무 많이 입력하셨습니다. 3개만 입력하세요.");
			}
			
			
			if(h>=0 && 3>h) {
				System.out.println("숫자 3개를 입력하셔야 합니다.");
			}
			else if(h==3) {
				break;
			}
		}
		count++;
		System.out.printf(" %d : %d : %d \n" ,numX[0],numX[1],numX[2]);
	};
	
	
	public int Answer() {
		
		int stk=0; //스트라이크
		int ball=0; //볼
		boolean out = false;
		
		
		for(int i=0; i<3; i++) {
			for(int z = 0; z<3; z++) {
				if(Com[i]==numX[z]) {
					if(i == z) {
						stk++;
						out = true;
					}
					else if(i != z){
						ball++;
						out = true;
					}
				}
			}
		}
		if(out==false) {
			System.out.printf("OUT!! %d회",count);	
		}
		else {
			System.out.printf("%d Strike %d Ball %d회",stk,ball,count); 
		}
		return stk;
	};
}



public class March_2020_7 {

	public static void main(String[] args) {
		
		int cnt = 0;
		Game io = new Game();
		io.Comnum();
		
		for(int i = 1; i>0; i++) {
			io.UserNum();
			cnt = io.Answer();
			if(cnt ==3) {
				break;
			}
		}
		System.out.println("");
		System.out.println("게임을 종료합니다.");
	}
}
