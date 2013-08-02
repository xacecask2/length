import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class Length {
	HashMap<String, Double> converter = new HashMap<String, Double>(); // e.g. <"mile", 1609.344>
	
	public void add(String s, Double d) {
		converter.put(s, d);
	}
	
	public double get(String s) {
		return converter.get(s);
	}
	
	public String parse(String s) {
		if (s.equals("feet"))
			return "foot";
		else if (s.equals("inches"))
			return "inch";
		else if (s.charAt(s.length() - 1) == 's')
			return s.substring(0, s.length() - 1);
		else
			return s;		
	}
	
	public String calculate(String expr) {
		// Calculating part. Because no parathenses would occur
		// in the expression, so we needn't convert the expression 
		// to postfix expression, just iterate and calculate
		String components[] = expr.split(" ");
		double result = 0.0;
		boolean isAdd = true;
		for (int i = 0; i < components.length;) {
			double temp = Double.parseDouble(components[i]) * get(parse(components[i+1]));
			if (isAdd)
				result += temp;
			else
				result -= temp;
			i += 2;
			if (i < components.length) {
				if (components[i] == "+")
					isAdd = true;
				else
					isAdd = false;
				i++;
			}
		}
		return (new DecimalFormat("0.00").format(result));
	}
	
	public static void main(String []args) {
		Length length = new Length();
		Vector<String> vResult = new Vector<String>();
		
		try {
			FileReader fr = new FileReader("input.txt");
			BufferedReader br = new BufferedReader(fr);
			String line;
			try {
				// Read input file, add how to convert a different
				// length unit to meter into a map. the key is the
				// name of the length unit and the value is 
				while ((line = br.readLine().trim()) != null) {
					if (line.length() == 0)
						break;
					String tempStrings[] = line.split(" ");
					length.add(tempStrings[1], Double.parseDouble(tempStrings[3]));
				}
				
				while ((line = br.readLine().trim()) != null) {
					if (line.length() == 0)
						break;
					String lineResult = length.calculate(line);
					vResult.add(lineResult);
				} 
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			FileWriter fw = null;
			try {
				fw = new FileWriter("output.txt", true);
				String c = "tingxun.xacecask2@gmail.com\r\n\r\n";
				fw.write(c);
				for (String r : vResult)
					fw.write(r + " m\r\n");
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
