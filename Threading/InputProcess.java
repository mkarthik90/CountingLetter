import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InputProcess {

	public static void main(String[] args) {

		Map<String, Integer> bucketWordList = new HashMap<String, Integer>();
		try {
			for (int i = 0; i < args.length; i++) {
				if (args[i].contains("output")) {
					// Writing to the file if the filename is output which will
					// be
					// read by the outputProcessor
					File file = new File(args[i]);
					if (file.exists() == false) {
						file.createNewFile();
					}
					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					
					
					for (Map.Entry<String, Integer> entry : bucketWordList.entrySet()) {
						bw.write(entry.getKey()+" "+entry.getValue()+"\n");
					}
					bw.close();
				} else {

					Scanner sc = new Scanner(new File(args[i]));
					while (sc.hasNextLine()) {
						Scanner s2 = new Scanner(sc.nextLine());
						while (s2.hasNext()) {
							String word = s2.next();
							if (bucketWordList.get(word) != null) {
								int count = Integer.parseInt(bucketWordList
										.get(word).toString());
								count++;
								bucketWordList.put(word, count);
								/* System.out.println(word + " " + count); */
							} else {
								bucketWordList.put(word, 1);
							}

						}
					}
				}
			}
		} 
		
		catch (Exception ex) {
			ex.printStackTrace();

		}

	}

}
