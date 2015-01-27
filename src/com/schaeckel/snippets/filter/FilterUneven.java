package com.schaeckel.snippets.filter;

import java.util.Iterator;
import java.util.List;

public class FilterUneven implements Filter<Integer>{

	@Override
	public List<Integer> filter(List<Integer> list) {
		Iterator<Integer> it = list.iterator();
		while (it.hasNext()) {
			Integer value = it.next();
			if ((value.intValue() % 2) == 1){
				it.remove();
			}
		}
		return list;
	}

}
