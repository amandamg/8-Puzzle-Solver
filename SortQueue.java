package cs_420_project1;

import java.util.Comparator;

public class SortQueue implements Comparator<Node>{

    public int compare(Node one, Node two) {
    	if(one.getPriority() < two.getPriority()){
    		return -1;
    	}
    	if(one.getPriority() > two.getPriority()){
    		return 1;
    	}
        return 0;
    }
}
