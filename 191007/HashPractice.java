import java.util.HashMap;
import java.util.HashSet;
import java.util.StringTokenizer;

public class HashPractice
{
	public static String solution(String[] participant, String[] completion) {

		HashSet<String> ptcpSet = new HashSet<>();
		HashSet<String> cptSet = new HashSet<>();
		
		HashMap<String, Integer> ptcpSameName = new HashMap<>();
		HashMap<String, Integer> cptSameName = new HashMap<>();
		
		for (String str : participant) {
			if(!ptcpSet.contains(str)) {
				ptcpSet.add(str);
			}else {
				if(!ptcpSameName.containsKey(str)) {
					ptcpSameName.put(str, 1);
					cptSameName.put(str, 0);
				}else {
					int sameNum = ptcpSameName.get(str);
					ptcpSameName.replace(str, ++sameNum);
				}//if~else end
			}//if~else end
		}//for() end
		
		for (String str : completion) {
			if(!cptSet.contains(str)) {
				cptSet.add(str);
			}else {
				int sameNum = cptSameName.get(str);
				cptSameName.replace(str, ++sameNum);
			}//if~else end
		}//for() end
		
		for (String ptcp : cptSet) {
			if(!ptcpSet.add(ptcp)) {
				ptcpSet.remove(ptcp);
			}//if() end
		}//for() end
		
		if(ptcpSet.size() == 0) {
			String tmpStr = ptcpSameName.entrySet().toString();
			
			StringTokenizer stk = new StringTokenizer(tmpStr.substring(1,tmpStr.indexOf("]")), ", ");
			
			for(int i = 0; stk.hasMoreTokens(); i++) {
				String tmpToken = stk.nextToken();
				
				int result = ptcpSameName.get(tmpToken.substring(0,tmpToken.indexOf("="))) -
						cptSameName.get(tmpToken.substring(0,tmpToken.indexOf("=")));
				
				if(result != 0) {
					ptcpSet.add(tmpToken.substring(0,tmpToken.indexOf("=")));
				}
			}//for() end
			
		}//if() end
		return ptcpSet.toString().substring(1,ptcpSet.toString().length()-1);
    }//solution() end
	
	public static void main(String[] args)
	{
		String[] part = {"leo", "kiki", "eden"};
		String[] comp = {"eden", "kiki"};
		
		System.out.println(solution(part, comp));
	}
	
}//HashPractice end
