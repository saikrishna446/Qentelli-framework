package com.qentelli.automation.utilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.lang3.StringUtils;

public class TextUtils {
  public class ConsoleColors {
    // Reset
    public static final String RESET = "\033[0m"; // Text Reset

    // Regular Colors
    public static final String BLACK = "\033[0;30m"; // BLACK
    public static final String RED = "\033[0;31m"; // RED
    public static final String GREEN = "\033[0;32m"; // GREEN
    public static final String YELLOW = "\033[0;33m"; // YELLOW
    public static final String BLUE = "\033[0;34m"; // BLUE
    public static final String PURPLE = "\033[0;35m"; // PURPLE
    public static final String CYAN = "\033[0;36m"; // CYAN
    public static final String WHITE = "\033[0;37m"; // WHITE

    // Bold
    public static final String BLACK_BOLD = "\033[1;30m"; // BLACK
    public static final String RED_BOLD = "\033[1;31m"; // RED
    public static final String GREEN_BOLD = "\033[1;32m"; // GREEN
    public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
    public static final String BLUE_BOLD = "\033[1;34m"; // BLUE
    public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
    public static final String CYAN_BOLD = "\033[1;36m"; // CYAN
    public static final String WHITE_BOLD = "\033[1;37m"; // WHITE

    // Underline
    public static final String BLACK_UNDERLINED = "\033[4;30m"; // BLACK
    public static final String RED_UNDERLINED = "\033[4;31m"; // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m"; // GREEN
    public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
    public static final String BLUE_UNDERLINED = "\033[4;34m"; // BLUE
    public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
    public static final String CYAN_UNDERLINED = "\033[4;36m"; // CYAN
    public static final String WHITE_UNDERLINED = "\033[4;37m"; // WHITE

    // Background
    public static final String BLACK_BACKGROUND = "\033[40m"; // BLACK
    public static final String RED_BACKGROUND = "\033[41m"; // RED
    public static final String GREEN_BACKGROUND = "\033[42m"; // GREEN
    public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
    public static final String BLUE_BACKGROUND = "\033[44m"; // BLUE
    public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
    public static final String CYAN_BACKGROUND = "\033[46m"; // CYAN
    public static final String WHITE_BACKGROUND = "\033[47m"; // WHITE

    // High Intensity
    public static final String BLACK_BRIGHT = "\033[0;90m"; // BLACK
    public static final String RED_BRIGHT = "\033[0;91m"; // RED
    public static final String GREEN_BRIGHT = "\033[0;92m"; // GREEN
    public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
    public static final String BLUE_BRIGHT = "\033[0;94m"; // BLUE
    public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
    public static final String CYAN_BRIGHT = "\033[0;96m"; // CYAN
    public static final String WHITE_BRIGHT = "\033[0;97m"; // WHITE

    // Bold High Intensity
    public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
    public static final String RED_BOLD_BRIGHT = "\033[1;91m"; // RED
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m"; // BLUE
    public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
    public static final String CYAN_BOLD_BRIGHT = "\033[1;96m"; // CYAN
    public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

    // High Intensity backgrounds
    public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
    public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
    public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
    public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
    public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
    public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
    public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m"; // CYAN
    public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m"; // WHITE
  }
	public static String format(String key, Object val) {
		return String.format("%-40s%-40s", key + ": ", val);
	}

	public static String center(String note) {
		int i = 70 - note.length();
		i = i / 2;
		i = (int) Math.floor(i);
        String repeatedLeft = StringUtils.repeat("<", i);
		String repeatedRight = StringUtils.repeat(">", i);
		note = repeatedLeft + "|" + note + "|" + repeatedRight;
		return note;
	}

	public static String centerClear(String note) {
		int i = 70 - note.length();
		i = i / 2;
		i = (int) Math.floor(i);
		String repeatedLeft = StringUtils.repeat(" ", i);
		String repeatedRight = StringUtils.repeat(" ", i);
		note = repeatedLeft + " " + note + " " + repeatedRight;
		return note;
	}

	public static String makeURLCompatible(String text) {
		text = text.replaceAll("\"", "").replaceAll("'", "").replaceAll("<", "").replaceAll(">", "").replaceAll("#", "")
				.replaceAll("%", "").replaceAll("\\{", "").replaceAll("}", "").replaceAll("\\|", "").replaceAll("/", "")
				.replaceAll("\\\\", "").replaceAll("\\[", "").replaceAll("]", "");
		return text;
	}

	public static String cleanupExceptionNames(String text) {
		text = text.replaceAll("(\\w+\\.){3,}", "");
		for (int i = 1; i < 11; i++) {
			text = text.replaceAll("(\\w+:)\\s+\\1", "$1");
		}
		return text;
	}

	public static String writeTmp(final String filename, final String value) {
		String ffileName = System.getProperty("java.io.tmpdir") + File.separator + filename ;
		System.out.println(ffileName);
		try (FileOutputStream fileOutputStream = new FileOutputStream(ffileName)) {
			fileOutputStream.write(value.getBytes());
			fileOutputStream.close();
		} catch (IOException exception) {
			exception.printStackTrace();
		}
		return ffileName ; 
	}

	public static String[] readTmp(final String filename) {
		System.out.println(System.getProperty("java.io.tmpdir") + File.separator + filename);
		String everything = null;
		try {
			everything = new String(
					Files.readAllBytes(Paths.get(System.getProperty("java.io.tmpdir") + File.separator + filename)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (everything == null) {
			System.out.println("nothing found @" + System.getProperty("java.io.tmpdir") + File.separator + filename);

		}
		return everything.split("\\n");
	}

    public static String logDiamonds(String s, String c) {
      return 
      String.format("%-2s%s%-2s",
          c + "<>" + ConsoleColors.RESET, s, c + "<>" + ConsoleColors.RESET);
    }
}

