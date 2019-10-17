package test;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Practice03
{
	public int solution(String[][] clothes) {
        int answer = 1;
        
        Map<String, String> map = new HashMap<>();
        
        for (String[] strings : clothes) {
        	map.put(strings[1],strings[0]);
		}//for() end
        
        int[] numsArr = new int[map.size()];
        int cursor = 0;
        
        for (Entry<String, String> str : map.entrySet()) {
        	
        	String key = str.getKey();
        	
        	for (String[] strings : clothes) {
				if(strings[1].equals(key)) {
					numsArr[cursor]++;
				}//if() end
			}//for() end
        	
        	cursor++;
        	
		}//for() end
        
        for (int i : numsArr) {
			answer = answer * (i + 1);
		}//for() end
        
        return answer - 1;
    }//solution() end
	
	public static void main(String[] args)
	{
		Practice03 sol = new Practice03();
		
		String[][] clothes = {
					{"yellow_hat", "headgear"}, {"blue_sunglasses", "eyewear"}, {"green_turban", "headgear"},
					{"blue_jeans","pants"}, {"blue_turban","headgear"}, {"glasses","eyewear"}
				};
		
		System.out.println(sol.solution(clothes));
	}//main() end
}//Practice03 end
