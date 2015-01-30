package com.schaeckel.snippets.lists.filter;

import java.util.List;

public interface Filter<T> {

	public List<T> filter(List<T> list);
}
