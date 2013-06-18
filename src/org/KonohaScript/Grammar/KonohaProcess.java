package org.KonohaScript.Grammar;

import org.KonohaScript.KonohaNameSpace;
import org.KonohaScript.KLib.KonohaArray;

public abstract class KonohaProcess {
	public void DefineMethod(KonohaNameSpace ns) {
		//TODO(sekiguchi)
	}

	public static KonohaProcess NewString(String Command) {
		//TODO(sekiguchi)
		return null;
	}

	public static KonohaProcess NewStringArray(KonohaArray Command) {
		//TODO(sekiguchi)
		return null;
	}

	public static void BackGround() {
		//TODO(sekiguchi)
	}

	public static void ForeGround() {
		//TODO(sekiguchi)
	}

	public static void SetArgument(KonohaArray Args) {
		//TODO(sekiguchi)
	}

	public static void Start() {
		//TODO(sekiguchi)
	}

	public static void Pipe(KonohaProcess Process) {
		//TODO(sekiguchi)
	}

	public int getStatus() {
		//TODO(sekiguchi)
		return 0;
	}

	public String GetResult() {
		//TODO(sekiguchi)
		return null;
	}

	public KonohaProcess(String ProcessType) {
		if (ProcessType.equals("PIPE")) {

		} else if (ProcessType.equals("REDIRECT")) {

		} else {
			throw new RuntimeException();
		}
	}

}