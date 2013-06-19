package org.KonohaScript.Grammer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import org.KonohaScript.KonohaNameSpace;


public class KonohaProcessDef {
	public static void main(String[] args) {		
		KonohaProcess.test();
	}
	
	public void DefineMethod(KonohaNameSpace ns) {
		//TODO(sekiguchi)
	}

	public static KonohaProcess NewKonohaProcess(String Command) {
		return new KonohaProcess(Command);
	}
	
	public static void SetArgument(KonohaProcess receiver, String[] Args) {
		receiver.setArgument(Args);
	}
	
	public static void SetArgument(KonohaProcess receiver, String Arg) {
		receiver.setArgument(Arg);
	}

	public static void BackGround() {
		//TODO(sekiguchi)
	}

	public static void ForeGround() {
		//TODO(sekiguchi)
	}

	public static void Start(KonohaProcess receiver) {
		receiver.start();
	}

	public static void Pipe(KonohaProcess receiver, KonohaProcess dest) {
		receiver.pipe(dest);
	}
	
	public static void WriteToFile(KonohaProcess receiver, String fileName) {
		receiver.writeToFile(fileName);
	}
	
	public static void WriteErrorToFile(KonohaProcess receiver, String fileName) {
		receiver.writeErrorToFile(fileName);
	}
	
	public static void ReadFromFile(KonohaProcess receiver, String fileName) {
		receiver.readFromFile(fileName);
	}

	public static int GetStatus() {
		//TODO(sekiguchi)
		return 0;
	}

	public static String GetResult() {
		//TODO(sekiguchi)
		return null;
	}
	
	public static String GetOut(KonohaProcess receiver) {
		return receiver.getStdout();
	}
	
	public static String GetError(KonohaProcess receiver) {
		return receiver.getStderr();
	}
}

class KonohaProcess {	
	private Process proc;
	
	public OutputStream stdin = null;
	public InputStream stdout = null;
	public InputStream stderr = null;
	
	private StreamSetter stdinSetter = null;
	private StreamGetter stdoutGetter = null;
	private StreamGetter stderrGetter = null;
	
	private String command;
	private String[] Args;
	private ArrayList<String> argsArray;
	
	public static void test() {
		String Args1[] = {"-p"};
		KonohaProcess subProc1 = new KonohaProcess("pstree");
		subProc1.setArgument(Args1);
		subProc1.start();

		String Args2[] = {"syslog"};
		KonohaProcess subProc2 = new KonohaProcess("grep");
		subProc2.setArgument(Args2);
		subProc2.start();
		
		subProc1.pipe(subProc2);
//		subProc1.writeToFile("log.txt");
		assert true: subProc2.getStdout();
		assert true: subProc2.getStderr();
		
		String Args3[] = {"public"};
		KonohaProcess subProc3 = new KonohaProcess("grep");
		subProc3.setArgument(Args3);
		subProc3.start();
		subProc3.readFromFile("src/org/KonohaScript/KonohaNameSpace.java");
		assert true: subProc3.getStdout();
		assert true: subProc3.getStderr();
	}
	
	public KonohaProcess(String command) {
		this.command = command;
		this.Args = null;
		this.argsArray = null;
	}
	
	public void setArgument(String Arg) {
		if (this.argsArray == null) {
			this.argsArray = new ArrayList<String>();
		}
		this.argsArray.add(Arg);
	}
	
	public void setArgument(String[] Args) {
		this.Args = Args;
	}
	
	public void start() {
		String[] cmd;
		
		if (argsArray == null) {
			int size = Args.length;
			if (size == 0) {
				cmd = new String[1];
			} else {
				cmd = new String[++size];
				cmd[0] = command;
				for (int i = 1; i < size; i++) {
					cmd[i] = Args[i - 1];
				}
			}			
		} else {
			int size = argsArray.size();
			cmd = new String[++size];
			cmd[0] = command;
			for (int i = 1; i < size; i++) {
				cmd[i] = argsArray.get(i - 1);
			}
		}
		
		try {
			this.proc = new ProcessBuilder(cmd).start();
			this.stdin = proc.getOutputStream();
			this.stdout = proc.getInputStream();
			this.stderr = proc.getErrorStream();			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void pipe(KonohaProcess destProc) {
		new Pipe(this.stdout, destProc.stdin).start();
	}
	
	public void writeToFile(String fileName) {
		stdoutGetter = new StreamGetter(stdout);
		stdoutGetter.start();
		try {
			stdoutGetter.join();
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			bw.write(stdoutGetter.getResult());
			bw.close();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void writeErrorToFile(String fileName) {
		stderrGetter = new StreamGetter(stderr);
		stderrGetter.start();
		try {
			stderrGetter.join();
			BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
			bw.write(stderrGetter.getResult());
			bw.close();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void readFromFile(String fileName) {
		try {
			FileInputStream fis = new FileInputStream(fileName);
			stdinSetter = new StreamSetter(stdin, fis);
			stdinSetter.start();
			stdinSetter.join();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String getStdout() {
		stdoutGetter = new StreamGetter(stdout);
		stdoutGetter.start();
		try {
			stdoutGetter.join();
			return stdoutGetter.getResult();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} 
	}
	
	public String getStderr() {
		stderrGetter = new StreamGetter(stderr);
		stderrGetter.start();
		try {
			stderrGetter.join();
			return stderrGetter.getResult();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		} 	
	}
	
	public void waitFor() {
		try {
			proc.waitFor();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void waitFor(long timeout) {
		try {
			this.proc.exitValue();
		} catch (IllegalThreadStateException e) {
			try {
				Thread.sleep(timeout);
			} catch (InterruptedException e1) {
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
		proc.destroy();
	}
}

class StreamGetter extends Thread {		
	private BufferedReader br;
	private String result;
			
	public StreamGetter(InputStream is) {
		br = new BufferedReader(new InputStreamReader(is));
		this.result = "";
	}
	
	public void run() {
		String line = null;
		try {
			while ((line = br.readLine()) != null) {
				result = result + line + "\n";
			}
			br.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public String getResult() {
		return result;
	}
}

class StreamSetter extends Thread {		
	private OutputStream os;
	private FileInputStream fis;
	
	public StreamSetter(OutputStream os, FileInputStream fis) {
		this.os = os;
		this.fis = fis;
	}
	
	public void run() {
		byte[] buffer = new byte[512];
		int read = 0;
		try {
			while (read > -1) {
				read = fis.read(buffer, 0, buffer.length);	
				if (read > -1) {
					os.write(buffer, 0, read);	
				}
			}
			fis.close();
			os.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

class Pipe extends Thread {
	private InputStream input;
	private OutputStream output;
	
	public Pipe(InputStream input, OutputStream output) {
		this.input = input;
		this.output = output;
	}
	
	public void run() {
		byte[] buffer = new byte[512];
		int read = 1;
		try {
			while (read > -1) {
				read = input.read(buffer, 0, buffer.length);
				if (read > -1) {
					output.write(buffer, 0, read);
				}
			} 
			input.close();
			output.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}