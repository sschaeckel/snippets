package com.schaeckel.snippets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import com.schaeckel.snippets.filter.DefaultFilter;
import com.schaeckel.snippets.filter.Filter;

public class RemoveItemsFromListExample {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ConfigurationException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		/*
		 * oder Einstellung in DB
		 * 
		 * night shift premium filter class
		 * 
		 * besser wirds dann mit Java 8, aber das steht gerade nicht zur Verfügung
		 * 
		 */
		PropertiesConfiguration configuration = 
				new PropertiesConfiguration("/Users/Steffen/workspace/snippets/src/com/schaeckel/snippets/app.properties");
		
		List<Integer> values = Arrays.asList(1,2,3,4,5,6); 

		String filterClass = configuration.getString("filter.class");
		Filter<Integer> filter = null;
		Class<Filter<Integer>> clazz = null;
		try {
			// scheinbar bekommt man diese Warning nicht ohne SuppressWarning weg
			clazz = (Class<Filter<Integer>>) ClassLoader.getSystemClassLoader().loadClass(filterClass);
			filter = clazz.newInstance();
		} catch (ClassNotFoundException e) {
			System.out.println("Klasse " + filterClass + " nicht gefunden, verwende " + DefaultFilter.class.getName());
			filter = new DefaultFilter();
		}
		System.out.println(filter.filter(values));

	}
}
