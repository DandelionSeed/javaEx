package algorithm;

public class Gugudan {

	public static void main(String[] args) {
		
		int num = 1000;
		
		for(int i = 1 ; i <= num ; i++) {
			
			int count = calcul(i);
			
			if(count==0) {
				System.out.print("");
				continue;
			}//if end
			
			System.out.print(i + " ");
			
			for(int j = 0 ; j < count ; j++) {
				System.out.print("짝");
			}//for end
			
			System.out.println();
			
		}//for end
		
	}//main() end

	private static int calcul(int num) {
		
		String numStr = String.valueOf(num);
		if(numStr.contains("3")||numStr.contains("6")||numStr.contains("9")) {
			
			int count = 0;
			
			for(char ch : numStr.toCharArray()) {
				if(ch == '3'||ch == '6'||ch == '9') {
					count++;
				}
			}//for end
			
			return count;
		}else {
			return -1;
		}
	}
	
}//Gugudan end
