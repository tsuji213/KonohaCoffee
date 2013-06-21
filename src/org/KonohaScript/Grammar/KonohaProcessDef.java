package org.KonohaScript.Grammar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.KonohaScript.KonohaDef;
import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.KonohaParam;
import org.KonohaScript.KonohaType;
import org.KonohaScript.JUtils.KonohaConst;


public class KonohaProcessDef extends KonohaDef implements KonohaConst {

	@Override
	public void MakeDefinition(KonohaNameSpace ns) {
		KonohaType BaseClass = ns.LookupHostLangType(KonohaProcess.class);

		// define Constructor
		String MN_newKonohaProcess = "newKonohaProcess";
		KonohaParam Process_String_Param = KonohaParam.ParseOf(ns, "KonohaProcess String x");
		BaseClass.DefineMethod(0, MN_newKonohaProcess, Process_String_Param, this, MN_newKonohaProcess); //FIXME

		// define SetArgument()
		String MN_SetArgument = "SetArgument";
		KonohaParam void_Strings_Param = KonohaParam.ParseOf(ns, "void String[] x");
		BaseClass.DefineMethod(0, MN_SetArgument, void_Strings_Param, this, MN_SetArgument);

		KonohaParam void_String_Param = KonohaParam.ParseOf(ns, "void String x");
		BaseClass.DefineMethod(0, MN_SetArgument, void_String_Param, this, MN_SetArgument);

		// define Start()
		String MN_Start = "Start";
		KonohaParam void_Param = KonohaParam.ParseOf(ns, "void");
		BaseClass.DefineMethod(0, MN_Start, void_Param, this, MN_Start);

		// define Pipe()
		String MN_Pipe = "Pipe";
		KonohaParam void_Process_Param = KonohaParam.ParseOf(ns, "void KonohaProcess x");
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

		// define WaitFor()
		String MN_WaitFor = "WaitFor";
		KonohaParam void_int_Param = KonohaParam.ParseOf(ns, "void int x");
		BaseClass.DefineMethod(0, MN_WaitFor, void_int_Param, this, MN_WaitFor);

		// define GetRetValue()
		String MN_GetRetValue = "GetRetValue";
		KonohaParam int_Param = KonohaParam.ParseOf(ns, "int");
		BaseClass.DefineMethod(0, MN_GetRetValue, int_Param, this, MN_GetRetValue);
	}

	public static KonohaProcess newProcess(String Command) {
		return new KonohaProcess(Command);
	}

	public static void SetArgument(KonohaProcess Process, String[] Args) {
		Process.setArgument(Args);
	}

	public static void SetArgument(KonohaProcess Process, String Arg) {
		Process.setArgument(Arg);
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

	public static String GetOut(KonohaProcess Process) {
		return Process.getStdout();
	}

	public static String GetError(KonohaProcess Process) {
		return Process.getStderr();
	}

	public static void WaitFor(KonohaProcess Process, int time) {
		Process.waitFor(time);
	}

	public static int GetRetValue(KonohaProcess Process) {
		return Process.getRet();
	}

	// main()
	public static void main(String[] args) {
		KonohaProcess proc1 = new KonohaProcess("ls", true);
		proc1.setArgument("/root");
		proc1.start();
		System.out.print("<<stdout>>\n" + proc1.getStdout());
		System.err.print("<<stderr>>\n" + proc1.getStderr());
	}
}
