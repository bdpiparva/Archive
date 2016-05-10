package com.crypt.bhupendra;

public class Crypt {

	private final static int key = 5;

	public static String encrypt(String text) throws Exception {
		StringBuilder crypt = new StringBuilder();
		for (char character : text.toCharArray()) {
			Character ch = new Character(caeserCipher(character));
			crypt.append(ch.toString());
		}
		System.out.println("Text: " + text + "\t Encrypt: " + crypt.toString() + "\t Decrypt: " + decrypt(crypt.toString()));
		return crypt.toString();
	}

	private static boolean isAlphabate(char character) {
		return isLowerCaseAlphabate(character) || isUpperCaseAlphabate(character);
	}

	private static boolean isLowerCaseAlphabate(char character) {
		return (97 <= character && character <= 122);
	}

	private static boolean isUpperCaseAlphabate(char character) {
		return (65 <= character && character <= 90);
	}

	private static int getStartAlphabate(char character) {
		return isLowerCaseAlphabate(character) ? 97 : 65;
	}

	public static char caeserCipher(char character) {
		if (!isAlphabate(character))
			return character;
		int padded = character + key;
		int start = getStartAlphabate(character);
		if (!isAlphabate((char) padded)) {
			padded = ((padded - start) % 26) + getStartAlphabate(character);
		}

		return (char) padded;
	}

	public static String decrypt(String str) {
		String decrypted = "";
		for (int i = 0; i < str.length(); i++) {
			int c = str.charAt(i);
			if (Character.isUpperCase(c)) {
				c = c - (key % 26);
				if (c < 'A')
					c = c + 26;
			} else if (Character.isLowerCase(c)) {
				c = c - (key % 26);
				if (c < 'a')
					c = c + 26;
			}
			decrypted += (char) c;
		}
		return decrypted;
	}

}
