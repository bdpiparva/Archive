package com.crypt.bhupendra;

public class ACrypt {

	private final static int KEY = 5;
	private final static String ALPHABATES = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

	public static String encrypt(String text) throws Exception {
		StringBuilder crypt = new StringBuilder();
		for (char c : text.toCharArray()) {
			crypt.append(encrypt(c));
		}
		return crypt.toString();
	}

	public static String decrypt(String encrypted) {
		StringBuilder plain = new StringBuilder();
		for (char c : encrypted.toCharArray()) {
			plain.append(decrypt(c));
		}
		return plain.toString();
	}

	private static boolean isAlphabate(char character) {
		return ALPHABATES.indexOf(character) != -1;
	}

	private static boolean isOutOfAlphabate(int index) {
		return index >= ALPHABATES.length() || index < 0;
	}

	public static char encrypt(char character) {
		if (!isAlphabate(character))
			return character;
		int padded = ALPHABATES.indexOf(character) + KEY;
		if (isOutOfAlphabate(padded)) {
			padded = padded % ALPHABATES.length();
		}
		return ALPHABATES.charAt(padded);
	}

	public static char decrypt(char character) {
		if (!isAlphabate(character))
			return character;
		int padded = ALPHABATES.indexOf(character) - KEY;
		if (isOutOfAlphabate(padded)) {
			padded = ALPHABATES.length() - Math.abs(padded);
		}
		return ALPHABATES.charAt(padded);
	}
}
