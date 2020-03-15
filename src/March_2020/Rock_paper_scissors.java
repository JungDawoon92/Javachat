package March_2020;

import java.util.Scanner;

public class Rock_paper_scissors {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int MaxNum = 100;
		int MinNum = 0;
		int UserNum = (MaxNum+MinNum)/2;
		//1은 큰거고 2는 작은거다
		System.out.println("0부터 100 사이의 값중에 하나를 생각하세요.");
		System.out.println("당신이 생각한 숫자가 제시한 숫자보다 크면 l");
		System.out.println("당신이 생각한 숫자가 제시한 숫자보다 크면 h");
		System.out.println("제(컴)가 맞췄다면 y를 입력하세요.");
		System.out.printf("당신이 선택한 숫자는 %d입니까?",UserNum);
		for(int i=1; i>0; i++) {
			String ans =sc.nextLine();
			if("h".equals(ans)) {
				MaxNum = UserNum;
				UserNum = (MaxNum+MinNum)/2;
				System.out.printf("당신이 선택한 숫자는 %d 입니까? ",UserNum);
			}
			else if("l".equals(ans)) {
				MinNum = UserNum;
				UserNum = (MaxNum+MinNum)/2;
				System.out.printf("당신이 선택한 숫자는 %d 입니까? ",UserNum);
			}
			else if("y".equals(ans)) {
				System.out.printf("정답입니다~게임을 종료합니다.[%d회차]",i );
				break;
			}
		}
	}
}
