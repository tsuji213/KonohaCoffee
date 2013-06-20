package org.KonohaScript.Grammar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class KonohaProcess {
	private Process					proc;

	private OutputStream			stdin	= null;
	private InputStream				stdout	= null;
	private InputStream				stderr	= null;

	private String commandName;
	private ArrayList<String>	commandList;
	private boolean enableSyscallTrace = false;
	private final String logdirPath = "/tmp/strace-log";
	private String logFilePath;
//	private Logger logger;
	
	
	public KonohaProcess(String command) {
		this.commandList = new ArrayList<String>();
		this.commandList.add(command);
	}
	
	public KonohaProcess(String command, boolean enableSyscallTrace) {
		this.commandList = new ArrayList<String>();
		this.enableSyscallTrace = enableSyscallTrace;
		
		if (this.enableSyscallTrace) {
			this.commandName = command;
//			this.logger = Logger.getLogger("D-Shell"); 
			
			String currentLogdirPath = createLogDirectory();
			String logNameHeader = createLogNameHeader();
			logFilePath = new String(currentLogdirPath + "/" + logNameHeader + ".log");
			
			String[] straceCmd = {"strace", "-t", "-f", "-F", "-o", logFilePath};
			for (int i = 0; i < straceCmd.length; i++) {
				this.commandList.add(straceCmd[i]);
			}
		}
		this.commandList.add(command);
	}
	
	private String createLogDirectory() {
		Calendar cal = Calendar.getInstance();
		StringBuilder pathBuilder = new StringBuilder();
		
		pathBuilder.append(logdirPath + "/");
		pathBuilder.append(cal.get(Calendar.YEAR) + "-");
		pathBuilder.append((cal.get(Calendar.MONTH) + 1) + "-");
		pathBuilder.append(cal.get(Calendar.DATE));
		
		String subdirPath = pathBuilder.toString();
		File subdir = new File(subdirPath);
		subdir.mkdirs();
		
		return subdirPath;
	}
	
	private String createLogNameHeader() {
		Calendar cal = Calendar.getInstance();
		StringBuilder logNameHeader = new StringBuilder();
		
		logNameHeader.append(cal.get((Calendar.HOUR) + 1) + ":");
		logNameHeader.append(cal.get(Calendar.MINUTE) + "-");
		logNameHeader.append(cal.get(Calendar.MILLISECOND));
	
		return logNameHeader.toString();
	}

	public void setArgument(String Arg) {
		this.commandList.add(Arg);
	}

	public void setArgument(String[] Args) {
		for(int i = 0; i < Args.length; i++) {
			this.setArgument(Args[i]);
		}
	}

	public void start() {		
		int size = this.commandList.size();
		String[] cmd = new String[size];
		for (int i = 0; i < size; i++) {
			cmd[i] = this.commandList.get(i);
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
				this.kill();
			}
		}
	}

	public int getRet() {
		return this.proc.exitValue();
	}

	public void kill() {
		this.proc.destroy();
	}
	
//	public void parseTraceLog() {
//		System.out.println("show systemcall error!!");
//		logger.error("error happened at " + commandName);
//		try {
//			BufferedReader br = new BufferedReader(new FileReader(this.logFilePath));
//			
//			String regex1 = "^[1-9][0-9]* .+(.+) = -[1-9].+";
//			Pattern p1 = Pattern.compile(regex1);
//			
//			String regex2 = "^.+(.+/locale.+).+";
//			Pattern p2 = Pattern.compile(regex2);
//			
//			String regex3 = "(^[1-9][0-9]*)( *)([0-9][0-9]:[0-9][0-9]:[0-9][0-9])( *)(.+)";
//			Pattern p3 = Pattern.compile(regex3);
//			
//			Matcher m1, m2, m3;
//			
//			String line;
//			while((line = br.readLine()) != null){
//				m1 = p1.matcher(line);
//				if (m1.find()) {
//					m3 = p3.matcher(line);
//					logger.error(m3.replaceAll("[pid $1] [time $3] $5"));
//				}
//			}
//			br.close();
//		} catch (FileNotFoundException e) {
//			throw new RuntimeException(e);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
//	}
}
