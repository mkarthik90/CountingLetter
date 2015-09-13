import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) throws Exception {

		Map<String, Integer> wordList = new HashMap<String, Integer>();
		try {
			for (int i = 0; i < args.length; i++) {
				File file = new File(args[i]);
				Scanner sc = new Scanner(file);
				while (sc.hasNextLine()) {
					String line = sc.nextLine();
					String[] keyValue = line.split(" ");
					if (wordList.get(keyValue[0]) != null) {
						int count = Integer.parseInt(keyValue[1])
								+ wordList.get(keyValue[0]);
						wordList.put(keyValue[0], count);
					} else {
						wordList.put(keyValue[0], Integer.parseInt(keyValue[1]));
					}
				}

			}

			// File is locked and written because other output process might
			// change the file

			File file = new File("Output.txt");
			if (file.exists() == false) {
				file.createNewFile();
			}

			FileInputStream in = new FileInputStream(file);
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));

			try {
				String line;
				Map<String, String> wordListFromFileMap = new HashMap<String, String>();
				while ((line = reader.readLine()) != null) {
					if(line.contains("Eye")){
						System.out.println("Stop");
					}
					String[] keyValue = line.split(" ");
					wordListFromFileMap.put(keyValue[0], keyValue[1]);
				}
				
				System.out.println("**"+wordListFromFileMap.size());
				
				//Getting the intersection variables of wordList and wordListFromFileMap and storing in wordList
				for (Map.Entry<String, Integer> entry : wordList.entrySet()) {
					if (wordListFromFileMap.get(entry.getKey()) != null) {
						int count = Integer.parseInt(wordListFromFileMap
								.get(entry.getKey())) + entry.getValue();
						wordList.put(entry.getKey(), count);
					}
				}
				
				//Getting the missing arguments from wordListFileMap and storing in wordList
				for (Map.Entry<String, String> entry : wordListFromFileMap.entrySet()) {
					if(wordList.get(entry.getKey()) == null){
						wordList.put(entry.getKey(),Integer.parseInt(entry.getValue()));
					}
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				reader.close();
				in.close();
			}

			FileOutputStream out = new FileOutputStream(file);
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(out));
			java.nio.channels.FileLock lock2 = out.getChannel().lock();
			try {

				// File is locked and written because other output process might
				// change the file
				// Clearing the contents of the file before writing the
				// contents

				

				for (Map.Entry<String, Integer> entry : wordList.entrySet()) {
					bw.write(entry.getKey() + " " + entry.getValue() + "\n");
				}
				
			} finally {
				lock2.release();
				bw.close();
				out.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}