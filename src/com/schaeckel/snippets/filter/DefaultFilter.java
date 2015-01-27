package com.schaeckel.snippets.filter;

import java.util.List;

public class DefaultFilter implements Filter<Integer> {

	@Override
	public List<Integer> filter(List<Integer> list) {
		return list;
	}

}
