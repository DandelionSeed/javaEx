import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Practice04
{
	public int[] solution(String[] genres, int[] plays) {
        int[] answer = {};
        
        Map<Integer, Integer> answerMap = new LinkedHashMap<>();
		int cursor = 1;
		//장르 별 총 플레이 횟수
		Map<String, Integer> map = new HashMap<>();
		for(int i = 0; i < genres.length ; i++) {
			int prevNum = 0;
			if(map.containsKey(genres[i])) {
				prevNum = map.get(genres[i]);
			}
			map.put(genres[i], prevNum + plays[i]);
		}//for() end
		
		//map sorting
		List<Map.Entry<String, Integer>> list = new LinkedList<>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>()
		{
			@Override
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2)
			{
				return (o1.getValue() - o2.getValue())*-1 == 0 ? o1.getKey().compareTo(o2.getKey()) : (o1.getValue() - o2.getValue())*-1;
			}
		});
		Map<String, Integer> sortedMap = new LinkedHashMap<>();
		for (Entry<String, Integer> entry : list) {
			sortedMap.put(entry.getKey(), entry.getValue());
		}//for() end
		
		//장르별 sorting
		Collection<String> key = sortedMap.keySet();
		Iterator<String> iter = key.iterator();
		for(int i = 0; i < map.size(); i++) {
			String target = iter.next();
			Map<Integer, Integer> entryMap = new HashMap<>();
			for (int j = 0; j < genres.length; j++) {
				if(genres[j].equals(target)) {
					entryMap.put(j, plays[j]);
				}//if() end
			}//for() end
			
			List<Map.Entry<Integer, Integer>> list2 = new LinkedList<>(entryMap.entrySet());
			Collections.sort(list2, new Comparator<Map.Entry<Integer, Integer>>()
			{
				@Override
				public int compare(Entry<Integer, Integer> o1, Entry<Integer, Integer> o2)
				{
					return (o1.getValue() - o2.getValue())*-1 == 0 ? o1.getKey().compareTo(o2.getKey()) : (o1.getValue() - o2.getValue())*-1;
				}
			});
			Map<Integer, Integer> sortedMap2 = new LinkedHashMap<>();
			for (Entry<Integer, Integer> entry : list2) {
				sortedMap2.put(entry.getKey(), entry.getValue());
			}//for() end
			
			Collection<Integer> key2 = sortedMap2.keySet();
			Iterator<Integer> iter2 = key2.iterator();
			
			answerMap.put(cursor++, iter2.next());
			if(iter2.hasNext()) {
				answerMap.put(cursor++, iter2.next());
			}//if() end
		}//for() end
		
		answer = new int[answerMap.size()];
		
		Collection<Integer> key3 = answerMap.values();
		Iterator<Integer> iter3 = key3.iterator();
		
		for (int i = 0; i < answer.length ; i++) {
			answer[i] = iter3.next();
		}
		
        return answer;
    }//solution() end
	
	public static void main(String[] args)
	{
		String[] genres = {"classic", "pop", "classic", "classic", "pop"};
		int[] plays = {500, 600, 150, 800, 2500};
		
		Practice04 run = new Practice04();
		
		System.out.println(run.solution(genres, plays));
		
	}//main() end
}//Practice04 end
