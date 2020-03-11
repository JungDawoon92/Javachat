package March_2020;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
// 1팀 숫자를 섞는팀.
// 2팀 게임 로직짜는팀.
// 3팀 게임판 보여주는팀.
interface ThreePuzzle{
	public void ShuffleNum();
	public void Puzzle();
	public void Map();
}

class ThreePuzzleGmae implements ThreePuzzle{
	String Master = "";
	int MasterNum=0;
	String[] file = {"1","2","3","4","5","6","7","8","X"};
	String[] array;
	
	public void ShuffleNum() {
		
		ArrayList<String> fileList = new ArrayList<String>(Arrays.asList(file));

		Collections.shuffle(fileList, new Random());

		Collections.shuffle(fileList);
		
		array= new String[fileList.size()];
		int size =0; 
		for(String temp:fileList) {
			array[size++]=temp;
		}
/////////////////////////////////////////////////////////////////////////////////////
		//섞은거에서 x추출+x배열번호 추출
		for(int i=0; i<9;i++) {
			if(array[i]=="X") {
				Master=array[i]; 
				MasterNum=i;
			}
		}
	};
	public void Puzzle() {
		Scanner sc = new Scanner(System.in);

		for(int z=0; z>=0;) {
			String Up ="w";
			String Down = "z";
			String Right ="s";
			String Left = "a";
			int m = MasterNum;
			String map =sc.nextLine();
			
			/////////////////////////UP//////////////////////////
			if(Up.equals(map)) {
				if(m-3>=0) {
					array[m]=array[m-3];
					array[m-3]=Master;
					Master=array[m-3];
					MasterNum = m-3 ;
				}
				else if(m-3<0) {
					System.out.println("밖으로 넘어갈수 없습니다.");
					z++;
				}
			}
			///////////////////////////Up//////////////////////
			if(Right.equals(map)) {
				if(m+1 != 3 && m+1 != 6 && m+1 != 9) {
					array[m]=array[m+1];
					array[m+1]=Master;
					Master=array[m+1];
					MasterNum = m+1 ;
				}
				else if(m+1==3 || m+1==6 || m+1==9 ) {
					System.out.println("밖으로 넘어갈수 없습니다.");
					z++;
				}
			}
			///////////////////////////Down//////////////////////////
			if(Down.equals(map)) {
				if(m+3<9) {
					array[m]=array[m+3];
					array[m+3]=Master;
					Master=array[m+3];
					MasterNum = m+3 ;
				}
				else if(m+3>=9) {
					System.out.println("밖으로 넘어갈수 없습니다.");
					z++;
				}
			}
			///////////////////////////Left////////////////////////////
			if(Left.equals(map)) {
				if(m-1 != -1 && m-1 != 2 && m-1 != 5) {
					array[m]=array[m-1];
					array[m-1]=Master;
					Master=array[m-1];
					MasterNum = m-1 ;
				}
				else if(m-1==-1 || m-1==2 || m-1==5 ) {
					System.out.println("밖으로 넘어갈수 없습니다.");
					z++;
				}
			}
			///////////////////////////////////////////////////////////
			
			if(Arrays.equals(file,array)){
				break;
			}
			

			
			
			Map();
			
		}
		
	};
	public void Map() {
		for(int i=0; i<3; i++) {
			System.out.print(array[i]);
		}
		System.out.println("");
		for(int i=3; i<6; i++) {
			System.out.print(array[i]);
		}
		System.out.println("");
		for(int i=6; i<9; i++) {
			System.out.print(array[i]);
		}
		
		
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("[Move] a : Left s : Right w : Up z : Down");
		System.out.println("이동키를 입력하세요 :");
		System.out.println("");
	};
}


public class ThreebyThreePuzzle{

	public static void main(String[] args) {
		
		ThreePuzzleGmae puzzle = new ThreePuzzleGmae();
		puzzle.ShuffleNum();
		puzzle.Map();
		puzzle.Puzzle();
		
			System.out.println("정답입니다. 게임을 종료합니다.");
			System.out.println("Good Bye~");
	}
	
}	