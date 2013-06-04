package org.KonohaScript;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.KonohaScript.MiniKonoha.MiniKonohaGrammar;

@KonohaPure
class KConsole {
	public final InputStream	stdin	= System.in;
	public final PrintStream	stdout	= System.out;
	public final PrintStream	stderr	= System.err;

	public KConsole() {
	}

	// FIXME support println(int), println(float)...
	void print(String s) {
		this.stdout.print(s);
	}

	void println(String s) {
		this.stdout.println(s);
	}
}

public class KonohaShell {

	Konoha	ShellContext;
	boolean	IsInteractiveMode;

	public KonohaShell(String DefaultBuilder) {
		this.ShellContext = new Konoha(new MiniKonohaGrammar(), DefaultBuilder);
		this.IsInteractiveMode = false;
	}

	boolean ProcessSource(String Source) {
		this.ShellContext.Eval(Source, 0);
		return true;
	}

	boolean ProcessFile(String FileName) {
		File f = new File(FileName);
		byte[] b = new byte[(int) f.length()];
		FileInputStream fi;
		try {
			fi = new FileInputStream(f);
			fi.read(b);
			fi.close();
			String source = new String(b);
			return this.ProcessSource(source);
		}
		catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	void printHelp() {
		/* FIXME */
	}

	String[] ProcessOptions(String[] origArgs) {
		ArrayList<String> Args = new ArrayList<String>();
		for (int i = 0; i < origArgs.length; i++) {
			String arg = origArgs[i];
			if (arg.equals("-h")) {
				this.printHelp();
				return null;
			}
			if (arg.equals("-i")) {
				this.IsInteractiveMode = true;
			} else if (arg.startsWith("-arch=")) {
				String BuilderName = arg.substring("-arch=".length());
				this.ShellContext.DefaultNameSpace.LoadBuilder(BuilderName);
			} else {
				Args.add(arg);
			}
		}

		this.IsInteractiveMode = true;
		String[] newArgs = new String[Args.size()];
		for (int i = 0; i < Args.size(); i++) {
			newArgs[i] = Args.get(i);
		}
		return newArgs;
	}

	public static void main(String[] origArgs) {
		String DefaultBuilder = "org.KonohaScript.CodeGen.ASTInterpreter";
		KonohaShell shell = new KonohaShell(DefaultBuilder);
		String[] args = shell.ProcessOptions(origArgs);
		if (args == null) {
			return;
		}

		for (int i = 0; i < args.length; i++) {
			shell.ProcessFile(args[i]);
		}
		if (shell.IsInteractiveMode) {
			shell.ProcessConsole();

		}
	}

	static int Count(String begin, String terminator, String source) {
		int level = 0;
		int start = source.indexOf(begin);
		if (start >= 0) {
			int i = start;
			while (i < source.length()) {
				level = level + 1;
				// System.out.println("start = " + start + ",i = " + i +":inc");
				i = source.indexOf(begin, i + 1);
				if (i < 0) {
					break;
				}
			}
		}

		int end = source.indexOf(terminator);
		if (end >= 0) {
			int i = end;
			while (i < source.length()) {
				level = level - 1;
				// System.out.println("end = " + end + ",i = " + i + ":dec");
				i = source.indexOf(end, i + 1);
				if (i < 0) {
					break;
				}
			}
		}
		return level;
	}

	private void ProcessConsole() {
		KConsole console = new KConsole();
		console.println("Konoha version 3.0");
		while (true) {
			console.print(">>>");
			String source = "";
			Scanner s = new Scanner(console.stdin);
			int level = 0;

			while (s.hasNext()) {
				String line = s.next();
				source = source + line + "\n";
				level = level + Count("(", ")", line);
				level = level + Count("{", "}", line);
				if (level == 0) {
					break;
				}
				console.println("b:" + source);
			}
			if (this.ProcessSource(source) == false) {
				break;
			}
		}
	}
}
