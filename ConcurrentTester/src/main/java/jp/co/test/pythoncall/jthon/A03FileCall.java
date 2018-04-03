package jp.co.test.pythoncall.jthon;
import java.util.Properties;

import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyUnicode;
import org.python.util.PythonInterpreter;
public class A03FileCall {

	public static void main(String[] args) {
	    Properties props = new Properties();

//        props.put("python.path", "【sample.pyのあるディレクトリ】");
        props.put("python.path", "./");        
	    props.put("python.console.encoding", "UTF-8");

        PythonInterpreter.initialize(System.getProperties(), props, new String[0]);
        try (PythonInterpreter interp = new PythonInterpreter()) {
            interp.exec("import sample"); //sample.pyをimport
            interp.exec("a = sample.add_numbers(2, 3)");
            interp.exec("print(a)");
        }

	}

}
