/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app;

import java.io.PrintStream;

/**
 * The Class AppBanner.
 */
class AppBanner {

	/** The Constant BANNER. */
	private static final String[] BANNER = { "TASKCHECK" };

	/**
	 * Prints the banner.
	 */
	static void printBanner() {
		final PrintStream printStream = System.out;
		for (String line : BANNER) {
			printStream.println(line);
		}

		printStream.printf("%5s", "v1.0");
		printStream.println();
	}

}
