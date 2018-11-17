package main.java;

import java.util.Scanner;

public class InOutConsole implements InputOutput{

	public String input() {
		Scanner in = new Scanner(System.in);
		return in.nextLine().trim();
	}

	public void output(String word) {
		System.out.print(word);
	}

}
