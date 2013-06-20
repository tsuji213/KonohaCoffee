import("JavaScript.Regexp");
import("JavaScript.Array");
import("JavaScript.String");
import("Type.File");
import("Type.Bytes");
import("Syntax.CStyleWhile");
import("Syntax.CStyleFor");
import("Syntax.ShellStyle");
import("Konoha.NameSpace");

String readFile(String path) {
	FILE file = new FILE(path, "rb");
	int len = 0;
	String tmp = "";
	while (true) {
		int ch = file.getc();
		if (ch == -1) {
			break;
		}
		len = len + 1;
	}
	file.close();

	if (len == 0) {
		return "";
	}

	Bytes buf = new Bytes(len);
	file = new FILE(path, "r");
	file.read(buf, 0, len);
	file.close();
	return new String(buf);
}

String replace(String path, String data, String pattern, String before, String after) {
	String tmp = data;
	RegExp r1 = new RegExp(pattern);
	while (true) {
		String[] res = tmp.match(r1);
		if (res.getSize() == 0) {
			break;
		}
		tmp = tmp.replace(res.get(0), res.get(0).replace(before, after));
	}
	return tmp;
}

void emit(String path, String data) {
	FILE file = new FILE(path, "w");
	file.print(data);
}

String[] javafiles(String path) {
	String[] javas = $(find ${path} -iname "*.java").trim().split("\n");
	return javas;
}
String library(String path) {
	String[] p = path.split("/");
	return p.get(p.getlength() - 2);
}


void GenerateTestRunner(String src, String ext) {
	String[] files0 = $(find ${src} -iname "*Test.java").trim().split("\n");
	String[] files1 = $(find ${ext} -iname "*Test.java").trim().split("\n");
	String[] list = [];
	for (int i=0; i < files0.getlength(); i = i + 1) {
		list.add(files0.get(i));
	}
	for (int i=0; i < files1.getlength(); i = i + 1) {
		list.add(files1.get(i));
	}
	File file = new File("Test/src/org/KonohaScript/Tester/KTestRunner.java", "w");
	file.println("package org.KonohaScript.Tester;");
	file.println("");
	for (int i=0; i < list.getlength(); i = i + 1) {
		String name = list.get(i);
		File f = new File(name, "r");
		String pkg = f.readLine().trim().split(" ").get(1).replace(";", ".");
		String l = name.split("/").pop();
		String cname = l.split(".").get(0);
		file.println("import ${pkg}${cname};");
	}

	file.println("public class KTestRunner extends KTestRunnerBase {");
	file.println("	public static void main(String[] args) {");
	file.println("		KTestRunner Runner = new KTestRunner();");

	for (int i=0; i < list.getlength(); i = i + 1) {
		String name = list.get(i);
		File f = new File(name, "r");
		String pkg = f.readLine().trim().split(" ").get(1).replace(";", ".");
		String l = name.split("/").pop();
		String cname = l.split(".").get(0);
		file.println("		Runner.Run(new ${cname}());");
	}
	file.println("	}");
	file.println("}");
}

void main() {
	String src = "src";
	String bin = "bin";
	String ext = "ext";
	GenerateTestRunner(src, ext);
}

main();
