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

	private ArrayList<String>	commandList;
	private boolean enableSyscallTrace = false;
	private final String logdirPath = "/tmp/strace-log";
	private String logFilePath;

	public KonohaProcess() {
		this.commandList = new ArrayList<String>();
	}

	public KonohaProcess(String command) {
		this.commandList = new ArrayList<String>();
		this.commandList.add(command);
	}

	public KonohaProcess(String command, boolean enableSyscallTrace) {
		this.commandList = new ArrayList<String>();
		this.enableSyscallTrace = enableSyscallTrace;

		if(this.enableSyscallTrace) {
			String currentLogdirPath = createLogDirectory();
			String logNameHeader = createLogNameHeader();
			logFilePath = new String(currentLogdirPath + "/" + logNameHeader + ".log");

			String[] straceCmd = {"strace", "-t", "-f", "-F", "-o", logFilePath};
			for(int i = 0; i < straceCmd.length; i++) {
				this.commandList.add(straceCmd[i]);
			}
		}
		this.commandList.add(command);
	}

	private String createLogDirectory() {
		Calendar cal = Calendar.getInstance();
		String subdirName = "";

		subdirName = subdirName.concat(cal.get(Calendar.YEAR) + "-");
		subdirName = subdirName.concat((cal.get(Calendar.MONTH) + 1) + "-");
		subdirName = subdirName.concat(cal.get(Calendar.DATE) + "");

		String subdirPath = new String(logdirPath + "/" + subdirName);
		File subdir = new File(subdirPath);
		subdir.mkdirs();

		return subdirPath;
	}

	private String createLogNameHeader() {
		Calendar cal = Calendar.getInstance();
		String logNameHeader = "";

		logNameHeader = logNameHeader.concat(cal.get((Calendar.HOUR) + 1) + ":");
		logNameHeader = logNameHeader.concat(cal.get(Calendar.MINUTE) + "-");
		logNameHeader = logNameHeader.concat(cal.get(Calendar.MILLISECOND) + "");

		return logNameHeader;
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
		for(int i = 0; i < size; i++) {
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
}
