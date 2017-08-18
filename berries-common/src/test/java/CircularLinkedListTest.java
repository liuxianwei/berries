import org.junit.Test;

import com.lee.berries.common.list.CircularLinkedList;
import com.lee.berries.common.list.ConcurrentCircularLinkedList;

public class CircularLinkedListTest {

	@Test
	public void Test(){
		CircularLinkedList<String> list = new ConcurrentCircularLinkedList<String>();
		for(int i = 1; i < 101; i++){
			list.add("" + i);
		}
		int count = 1;
		while(list.size() > 1){
			list.next();
			count++;
			if(count % 3 == 0){
				System.out.println(list.remove() + "退出！");
				count++;
			}
		}
		System.out.println(list.toString());
	}
}
