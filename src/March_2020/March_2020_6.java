package March_2020;
// project 
// 문자 + 숫자가 입력받았을때, 숫자만 출력해주는것

// * charAt() 함수 = 문자가 어디에(At)있는지 알려주는 함수.
//  charAt(함수는 char변수에 저장해야함.


import java.util.Scanner;

public class March_2020_6 {
	

	public static void main(String[] args) {
//=========================================================================================================================================
//문자열을 입력받고 문자열을 캐릭터로 다시 쪼개고, String으로 변환하는 과정.
		Scanner sc = new Scanner(System.in);
		String mama = sc.nextLine();
		int h = mama.length();

		char a =' ';
		
		String[] mama1 = new String[h];
		char[] mama2 = new char[h];
		int cont=0;
      
// 1. 반복문을 통해 char배열로 등록(baseball처럼 미련하게 쪼개고등록할필요없음) 2.조건문을 통해 숫자추출,string변환다시담기,담은걸 배열에담기위해 count		
		for(int i=0; i<h; i++) {
			mama2[i]=mama.charAt(i);
			if((mama2[i]>='0' && mama2[i]<= '9')) {
				mama1[i]=String.valueOf(mama2[i]);
				cont++;
			}
		}
// count한 크기로 null 이 아닌걸 걸러 숫자만 담기.
// 인티저로 받고 싶으면 Character.getNumericValue 을 쓴다. <초기화 인티저로 한후,>
		String[] mama3 = new String[cont];
		
		for(int z=0,y=0; z<h; z++) {
			if(mama1[z] != null) {			
				mama3[y] = mama1[z];
				y++;
			}
		}
/// 잘 완성됬는지 확인하는 과정.
		for(int i =0; i<mama3.length; i++) {
			System.out.print(mama3[i]);
		}
	}

}