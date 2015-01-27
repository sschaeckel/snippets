package com.schaeckel.snippets.filter;

import java.util.List;

public interface Filter<T> {

	public List<T> filter(List<T> list);
}
