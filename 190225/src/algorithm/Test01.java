package algorithm;

import java.util.Arrays;

public class Test01 {

	
	public static void main(String[] args) {
		
		int[] arr = new int[] {-20,-15,-5,10,20};
		
		// 정답 출력할 새로운 배열 선언
		int[] answerArr = new int[2];
		// 정답용 배열에 잘 들어가는지 확인
//		answerArr[0] = arr[0];
//		answerArr[1] = arr[1];
		
		//가장 큰 값 저장용 변수
		int biggest = 0;
		
		for(int i = 0 ; i < arr.length ; i++) {
			
			for(int j = 0 ; j < arr.length ; j++) {
				
				//같은 번지의 숫자를 곱하는 오류를 피하기 위해
				if(i!=j) {
					int mul = arr[i] * arr[j];
					
					if(mul > biggest) {
						//가장 큰 값을 갱신하고
						biggest = mul;
//						System.out.println(mul);
						
						//해당 값을 만든 숫자들을 정답용 배열에 저장
						
						answerArr[0] = arr[i];
						answerArr[1] = arr[j];
						
					}//if end
					
				}//if end
				
			}//for end
			
		}//for end
		
		System.out.println(Arrays.toString(answerArr));
		
	}//main() end
	
	
}//Test01 end
