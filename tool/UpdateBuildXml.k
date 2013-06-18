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
	String ext = "ext";
	String bin = "bin";
	GenerateTestRunner(src, ext);
	File xml = new File("./build.xml", "w");
	xml.println("<project name=\"KonohaCoffee\" default=\"jar\">");
	xml.println("	<!-- directory / file definition -->");
	xml.println("	<property name=\"source\" value=\"${src}\" />");
	xml.println("	<property name=\"build\" value=\"${bin}\" />");
	xml.println("	<!-- <property name=\"jarfile\" value=\"konoha.jar\" /> -->");
	xml.println("");
	xml.println("	<!-- ================================== -->");
	xml.println("	<!--                BUILD               -->");
	xml.println("	<!-- ================================== -->");
	xml.println("	<target name=\"build\">");
	xml.println("		<mkdir dir=\"${bin}\" />");
	xml.println("		<mkdir dir=\"${bin}/konoha\" />");
	xml.println("		<javac srcdir=\"${src}\" destdir=\"${bin}/konoha\" debug=\"on\"");
	xml.println("			target=\"1.6\" source=\"1.6\" includeantruntime=\"false\">");
	xml.println("			<classpath path=\"third-party/asm-all-4.0.jar\" />");
	xml.println("			<classpath path=\"third-party/jllvm.jar\" />");
	xml.println("			<include name=\"**/*.java\" />");
	xml.println("			<exclude name=\"**/*Test.java\" />");
	xml.println("			<exclude name=\"**/*TestBase.java\" />");
	xml.println("			<exclude name=\"**/Tester/*.java\" />");
	xml.println("		</javac>");

	String[] extlibs = $(find ${ext} -maxdepth 2 -name .settings).trim().split("\n");

	for (int i=0; i < extlibs.getsize(); i = i + 1) {
		String e = extlibs.get(i);
		String path = e.substring(0, e.getlength() - ".settings".getlength());
		String libname = library(path);
		String bindir = bin + "/" + libname;
		xml.println("		<mkdir dir=\"${bindir}\" />");
		xml.println("		<javac srcdir=\"${path}\" destdir=\"${bindir}\" debug=\"on\"");
		xml.println("			target=\"1.6\" source=\"1.6\" includeantruntime=\"false\">");
		xml.println("			<include name=\"**/*.java\" />");
		xml.println("			<exclude name=\"**/*Test.java\" />");
		xml.println("			<classpath path=\"${bin}/konoha\" />");
		xml.println("		</javac>");
	}
	xml.println("	</target>");
	xml.println("");
	xml.println("	<!-- ================================== -->");
	xml.println("	<!--            GENERATE JAR            -->");
	xml.println("	<!-- ================================== -->");
	xml.println("	<target name=\"jar\" depends=\"build\">");
	xml.println("		<jar jarfile=\"konoha.jar\">");
	xml.println("			<fileset dir=\"bin\" includes=\"**/*.class\" />");
	xml.println("			<zipfileset src=\"third-party/asm-all-4.0.jar\" />");
	xml.println("			<zipfileset src=\"third-party/jllvm.jar\" />");
	xml.print("			<manifest>");
	xml.print("				<attribute name=\"Main-Class\" value=\"org.KonohaScript.KonohaShell\" />");
	xml.print("			</manifest>");
	xml.println("		</jar>");

	for (int i=0; i < extlibs.getsize(); i = i + 1) {
		String e = extlibs.get(i);
		String libname = library(e.substring(0, e.getlength() - ".settings".getlength()));
		xml.println("		<jar jarfile=\"${libname}.jar\">");
		xml.println("			<fileset dir=\"${bin}/${libname}\" includes=\"**/*.class\" />");
		xml.println("		</jar>");
	}
	xml.println("	</target>");
	xml.println("");
	xml.println("	<!-- ================================== -->");
	xml.println("	<!--               CLEAN                -->");
	xml.println("	<!-- ================================== -->");
	xml.println("	<target name=\"clean\">");
	xml.println("		<delete dir=\"bin\" />");
	xml.println("		<delete file=\"konoha.jar\" />");
	for (int i=0; i < extlibs.getsize(); i = i + 1) {
		String e = extlibs.get(i);
		String libname = library(e.substring(0, e.getlength() - ".settings".getlength()));
		xml.println("		<delete file=\"${libname}.jar\" />");
	}
	xml.println("		<delete file=\"konoha-test.jar\" />");
	xml.println("	</target>");

	xml.println("	<!-- ================================== -->");
	xml.println("	<!--              TEST-BUILD            -->");
	xml.println("	<!-- ================================== -->");
	xml.println("	<target name=\"test-build\" depends=\"build\">");
	xml.println("		<mkdir dir=\"${bin}/test\" />");
	xml.println("		<javac srcdir=\".\" destdir=\"${bin}/test\" debug=\"on\"");
	xml.println("			target=\"1.6\" source=\"1.6\" includeantruntime=\"false\">");
	xml.println("			<classpath path=\"third-party/asm-all-4.0.jar\" />");
	xml.println("			<classpath path=\"third-party/jllvm.jar\" />");
	xml.println("			<classpath path=\"${bin}/konoha\" />");
	xml.println("			<classpath path=\"${bin}/test\" />");
	for (int i=0; i < extlibs.getsize(); i = i + 1) {
		String e = extlibs.get(i);
		String libname = library(e.substring(0, e.getlength() - ".settings".getlength()));
		xml.println("		<classpath path=\"${bin}/${libname}\" />");
	}
	xml.println("			<include name=\"**/*Test.java\" />");
	xml.println("			<include name=\"**/*TestBase.java\" />");
	xml.println("			<include name=\"**/Tester/*.java\" />");
	xml.println("			<include name=\"Test/**/*.java\" />");
	xml.println("		</javac>");
	xml.println("	</target>");

	String testbin = bin + "/test";
	xml.println("	<!-- ================================== -->");
	xml.println("	<!--               TEST                 -->");
	xml.println("	<!-- ================================== -->");
	xml.println("	<target name=\"test\" depends=\"build,jar,test-build\">");
	xml.println("		<jar jarfile=\"konoha-test.jar\">");
	xml.println("			<fileset dir=\"${testbin}\" includes=\"**/*.class\" />");
	xml.println("			<zipfileset src=\"konoha.jar\" />");
	for (int i=0; i < extlibs.getsize(); i = i + 1) {
		String e = extlibs.get(i);
		String libname = library(e.substring(0, e.getlength() - ".settings".getlength()));
		xml.println("		<zipfileset src=\"${libname}.jar\" />");
	}

	xml.println("			<manifest>");
	xml.println("				<attribute name=\"Main-Class\" value=\"org.KonohaScript.Tester.KTestRunner\" />");
	xml.println("			</manifest>");

	xml.println("		</jar>");
	xml.println("	</target>");

	xml.println("</project>");
}

main();
