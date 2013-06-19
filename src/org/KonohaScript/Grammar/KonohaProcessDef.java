package org.KonohaScript.Grammar;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;

import org.KonohaScript.KonohaConst;
import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.KonohaParam;
import org.KonohaScript.KonohaType;

public class KonohaProcessDef implements KonohaConst {

	public void DefineMethod(KonohaNameSpace ns) {
		KonohaType BaseClass = ns.LookupTypeInfo(Process.class);
		
		// define Constructor
		String MN_NewProcess = "NewProcess";
		KonohaParam Process_String_Param = KonohaParam.ParseOf(ns, "Process String x");
		BaseClass.DefineMethod(0, MN_NewProcess, 
				Process_String_Param, this, MN_NewProcess);	//FIXME
		
		// define SetArgument()
		String MN_SetArgument = "SetArgument";
		KonohaParam void_Strings_Param = KonohaParam.ParseOf(ns, "void String[] x");
		BaseClass.DefineMethod(0, MN_SetArgument, 
				void_Strings_Param, this, MN_SetArgument);	
		
		KonohaParam void_String_Param = KonohaParam.ParseOf(ns, "void String x");
		BaseClass.DefineMethod(0, MN_SetArgument, void_String_Param, this, MN_SetArgument);
		
		// define Start()
		String MN_Start = "Start";
		KonohaParam void_Param = KonohaParam.ParseOf(ns, "void");	//FIXME
		BaseClass.DefineMethod(0, MN_Start, void_Param, this, MN_Start);
	
		// define Pipe()
		String MN_Pipe = "Pipe";
		KonohaParam void_Process_Param = KonohaParam.ParseOf(ns, "void Process x");
		BaseClass.DefineMethod(0, MN_Pipe, void_Process_Param, this, MN_Pipe);
		
		// define ReadFromFile()
		String MN_ReadFromFile = "ReadFromFile";
		BaseClass.DefineMethod(0, MN_ReadFromFile, void_String_Param, this, MN_ReadFromFile);	
		
		// define GetOut()
		String MN_GetOut = "GetOut";
		KonohaParam String_Param = KonohaParam.ParseOf(ns, "String");
		BaseClass.DefineMethod(0, MN_GetOut, String_Param, this, MN_GetOut);
		
		// define GetError()
		String MN_GetError = "GetError";
		BaseClass.DefineMethod(0, MN_GetError, String_Param, this, MN_GetError);
		
		//define WaitFor()
		String MN_WaitFor = "WaitFor";
		KonohaParam void_int_Param = KonohaParam.ParseOf(ns, "void int x");
		BaseClass.DefineMethod(0, MN_WaitFor, void_int_Param, this, MN_WaitFor);
		
		// define GetRetValue()
		String MN_GetRetValue = "GetRetValue";
		KonohaParam int_Param = KonohaParam.ParseOf(ns, "int");
		BaseClass.DefineMethod(0, MN_GetRetValue, int_Param, this, MN_GetRetValue);
	}
	

	public static Process NewProcess(String Command) {
		return new Process(Command);
	}

	public static void SetArgument(Process Process, String[] Args) {
		Process.setArgument(Args);
	}

	public static void SetArgument(Process Process, String Arg) {
		Process.setArgument(Arg);
	}

	public static void Start(Process Process) {
		Process.start();
	}

	public static void Pipe(Process Process, Process dest) {
		Process.pipe(dest);
	}

	public static void ReadFromFile(Process Process, String fileName) {
		Process.readFromFile(fileName);
	}

	public static int GetStatus() {
		//TODO(sekiguchi)
		return 0;
	}

	public static String GetOut(Process Process) {
		return Process.getStdout();
	}

	public static String GetError(Process Process) {
		return Process.getStderr();
	}
	
	public static void WaitFor(Process Process, int time) {
		Process.waitFor(time);
	}
	
	public static int GetRetValue(Process Process) {
		return Process.getRet();
	}
}

class Process {
	private java.lang.Process					proc;

	private OutputStream			stdin	= null;
	private InputStream				stdout	= null;
	private InputStream				stderr	= null;

	private final String			command;
	private final ArrayList<String>	Arguments;

	public Process(String command) {
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

	public void pipe(Process destProc) {
		new StreamSetter(this.stdout, destProc.stdin).start();
	}

	public void readFromFile(String fileName) {
		try {
			FileInputStream fis = new FileInputStream(fileName);
			StreamSetter stdinSetter = new StreamSetter(fis, this.stdin);
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
		StreamGetter streamGetter = new StreamGetter(ins);
		streamGetter.start();
		try {
			streamGetter.join();
			return streamGetter.getResult();
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
				kill();
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

// copied from http://blog.art-of-coding.eu/piping-between-processes/
class StreamSetter extends Thread {
	private final InputStream	input;
	private final OutputStream	output;

	public StreamSetter(InputStream input, OutputStream output) {
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
