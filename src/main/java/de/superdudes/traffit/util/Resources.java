package de.superdudes.traffit.util;

import java.net.URL;

public class Resources {

	public static String getClasspathResource(String location) {
		final URL resource = ClassLoader.getSystemClassLoader().getResource("./" + location);
		return resource != null ? resource.getFile() : null;
	}
}
