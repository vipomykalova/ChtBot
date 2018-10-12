package ChatBot;

import java.io.IOException;

public interface InputOutput {
	String input() throws IOException;
	void output(String str) throws IOException;
}
