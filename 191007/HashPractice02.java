import java.util.HashSet;

public class Practice02
{
    public boolean solution(String[] phone_book) {
        boolean answer = true;
        HashSet<String> phones = new HashSet<>();
        
        for (String str : phone_book) {
        	phones.add(str);
        }
        
        for (String str : phone_book) {
        	phones.remove(str);
        	
        	int flag = 0;
        	
        	for(String string : phones) {
        		if(string.startsWith(str)) {
        			flag = 1;
        		}//if() end
        	}//for() end
        	
        	phones.clear();
        	
        	for (String rebuild : phone_book) {
            	phones.add(rebuild);
            }
        	if(flag == 1) {
        		answer = false;
        		break;
        	}
		}//for() end
        
        return answer;
    }
	
	public static void main(String[] args)
	{
		Practice02 sol = new Practice02();
		
		String[] strArr = {"119", "97674223", "1195524421"};
		
		System.out.println(sol.solution(strArr));
	}
}
