package com.schaeckel.snippets.files;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReadFileExample {
	
	public static void main(String[] args) throws IOException {
		
		System.out.println("Default Encoding = " + System.getProperty("file.encoding"));
		Path path = FileSystems.getDefault().getPath("/Users/Steffen/Dropbox/Deutsche Post/Leitdaten/B1306152.DAT");
		
		long start = System.currentTimeMillis();
		List<String> lines = Files.readAllLines( path, Charset.forName("CP850") );
		System.out.println("readAllLines: " + (System.currentTimeMillis()-start)+" ms");
		
		start = System.currentTimeMillis();
		long count = lines.stream().count();
		System.out.println("Anzahl: " + count + " in : " + (System.currentTimeMillis() - start) + " ms");

		System.out.println("---------------------------------");
		start = System.currentTimeMillis();
		Stream<String> keys = lines.stream().parallel()
				//.peek(e -> System.out.println(Thread.currentThread().getName() + " -> " + e.substring(0, 8)))
				.map(e -> e.substring(0, 8))
				.distinct();
		keys.forEach(System.out::println);
		System.out.println("distinct(): " + (System.currentTimeMillis() - start) + " ms");

		/*---------------------------------------------------*/
		copyFileWithReadAllLines(path);

		/*---------------------------------------------------*/
		copyFileWithStreamTrimList(path);

		/*---------------------------------------------------*/
		copyFileAsStreamToOutputStream(path);

	}

	private static void copyFileWithReadAllLines(Path path) throws IOException {
		long start = System.currentTimeMillis();
		List<String> lines = Files.readAllLines( path, Charset.forName("CP850") );
		Path outputPath = FileSystems.getDefault().getPath("c:/tmp/example.txt");
		Files.write(outputPath, lines, StandardOpenOption.CREATE);
		System.out.println("Datei schreiben (Liste): " + (System.currentTimeMillis() - start) + " ms");
	}

	private static void copyFileWithStreamTrimList(Path path)
			throws IOException {
		long start = System.currentTimeMillis();
		List<String> collect = Files.lines( path, Charset.forName("CP850") )
			.map(e -> e.trim())
			.collect(Collectors.toList());
		Path outputPath2 = FileSystems.getDefault().getPath("c:/tmp/example2.txt");
		Files.write(outputPath2, collect, StandardOpenOption.CREATE);
		System.out.println("Datei schreiben (Liste) + Trim: " + (System.currentTimeMillis() - start) + " ms");
	}

	private static void copyFileAsStreamToOutputStream(Path path)
			throws IOException {
		Path outputPath3 = FileSystems.getDefault().getPath("c:/tmp/example3.txt");
		OutputStream newOutputStream = Files.newOutputStream(outputPath3, StandardOpenOption.CREATE);
		Consumer<? super String> action = new Consumer<String>() {
			@Override
			public void accept(String t) {
				try {
					//Files.write(outputPath3, t.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
					newOutputStream.write((t+"\r\n").getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		long start = System.currentTimeMillis();
		Files.lines( path, Charset.forName("CP850") )
				.map(e -> e.trim())
				.forEach(action);
				//.collect(Collectors.toList());
		newOutputStream.close();
		System.out.println("Datei schreiben (Stream) + Trim: " + (System.currentTimeMillis() - start) + " ms");
	}


}
