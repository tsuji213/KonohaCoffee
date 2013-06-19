package org.KonohaScript.Grammar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import org.KonohaScript.KonohaNameSpace;

public class KonohaProcessDef {

	public void DefineMethod(KonohaNameSpace ns) {
		//TODO(sekiguchi)
	}

	public static KonohaProcess NewKonohaProcess(String Command) {
		return new KonohaProcess(Command);
	}

	public static void SetArgument(KonohaProcess Process, String[] Args) {
		Process.setArgument(Args);
	}

	public static void SetArgument(KonohaProcess Process, String Arg) {
		Process.setArgument(Arg);
	}

	public static void BackGround() {
		//TODO(sekiguchi)
	}

	public static void ForeGround() {
		//TODO(sekiguchi)
	}

	public static void Start(KonohaProcess Process) {
		Process.start();
	}

	public static void Pipe(KonohaProcess Process, KonohaProcess dest) {
		Process.pipe(dest);
	}

	public static void ReadFromFile(KonohaProcess Process, String fileName) {
		Process.readFromFile(fileName);
	}

	public static int GetStatus() {
		//TODO(sekiguchi)
		return 0;
	}

	public static String GetResult() {
		//TODO(sekiguchi)
		return null;
	}

	public static String GetOut(KonohaProcess Process) {
		return Process.getStdout();
	}

	public static String GetError(KonohaProcess Process) {
		return Process.getStderr();
	}
}

class KonohaProcess {
	private Process					proc;

	private OutputStream			stdin	= null;
	private InputStream				stdout	= null;
	private InputStream				stderr	= null;

	private final String			command;
	private final ArrayList<String>	Arguments;

	public KonohaProcess(String command) {
		this.command = command;
		this.Arguments = new ArrayList<String>();
	}

	public void setArgument(String Arg) {
		this.Arguments.add(Arg);
	}

	public void setArgument(String[] Args) {
		for(int i = 0; i < Args.length; i++) {
			this.setArgument(Args[i]);
		}
	}

	public void start() {
		int size = this.Arguments.size() + 1;
		String[] cmd = new String[size];
		cmd[0] = this.command;
		for(int i = 1; i < size; i++) {
			cmd[i] = this.Arguments.get(i - 1);
		}

		try {
			this.proc = new ProcessBuilder(cmd).start();
			this.stdin = this.proc.getOutputStream();
			this.stdout = this.proc.getInputStream();
			this.stderr = this.proc.getErrorStream();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void pipe(KonohaProcess destProc) {
		new Pipe(this.stdout, destProc.stdin).start();
	}

	public void readFromFile(String fileName) {
		try {
			FileInputStream fis = new FileInputStream(fileName);
			StreamSetter stdinSetter = new StreamSetter(this.stdin, fis);
			stdinSetter.start();
			stdinSetter.join();
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private String getResult(InputStream ins) {
		StreamGetter stdoutGetter = new StreamGetter(ins);
		stdoutGetter.start();
		try {
			stdoutGetter.join();
			return stdoutGetter.getResult();
		}
		catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	public String getStdout() {
		return this.getResult(this.stdout);
	}

	public String getStderr() {
		return this.getResult(this.stderr);
	}

	public void waitFor() {
		try {
			this.proc.waitFor();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void waitFor(long timeout) {
		try {
			this.proc.exitValue();
		}
		catch (IllegalThreadStateException e) {
			try {
				Thread.sleep(timeout);
			}
			catch (InterruptedException e1) {
				e1.printStackTrace();
			} finally {
				this.proc.destroy();
			}
		}
	}

	public int getRet() {
		return this.proc.exitValue();
	}

	public void kill() {
		this.proc.destroy();
	}
}

class StreamGetter extends Thread {
	private final BufferedReader	br;
	private String					result;

	public StreamGetter(InputStream is) {
		this.br = new BufferedReader(new InputStreamReader(is));
		this.result = "";
	}

	@Override
	public void run() {
		String line = null;
		try {
			while((line = this.br.readLine()) != null) {
				this.result = this.result + line + "\n";
			}
			this.br.close();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public String getResult() {
		return this.result;
	}
}

class StreamSetter extends Thread {
	private final OutputStream		os;
	private final FileInputStream	fis;

	public StreamSetter(OutputStream os, FileInputStream fis) {
		this.os = os;
		this.fis = fis;
	}

	@Override
	public void run() {
		try {
			byte[] buffer = new byte[512];
			int read = 0;
			while(read > -1) {
				read = this.fis.read(buffer, 0, buffer.length);
				if(read > -1) {
					this.os.write(buffer, 0, read);
				}
			}
			this.fis.close();
			this.os.close();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

// copied from http://blog.art-of-coding.eu/piping-between-processes/
class Pipe extends Thread {
	private final InputStream	input;
	private final OutputStream	output;

	public Pipe(InputStream input, OutputStream output) {
		this.input = input;
		this.output = output;
	}

	@Override
	public void run() {
		try {
			byte[] buffer = new byte[512];
			int read = 0;
			while(read > -1) {
				read = this.input.read(buffer, 0, buffer.length);
				if(read > -1) {
					this.output.write(buffer, 0, read);
				}
			}
			this.input.close();
			this.output.close();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}