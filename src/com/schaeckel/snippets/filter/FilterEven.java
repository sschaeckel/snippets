package com.schaeckel.snippets.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FilterEven implements Filter<Integer>{

	@Override
	public List<Integer> filter(List<Integer> list) {
		List<Integer> values = new ArrayList<Integer>();
		values.addAll(list);
		Iterator<Integer> it = values.iterator();
		while (it.hasNext()) {
			Integer value = it.next();
			if ((value.intValue() % 2) == 0){
				it.remove();
			}
		}
		return values;
	}

	
}
