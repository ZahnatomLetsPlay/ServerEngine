package saveAndLoad;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class LoadData {

	/**
	 * loads an Object which has already been saved.
	 * 
	 * @param fileName
	 * @return Object variant of a saved Obj
	 */
	public Object loadObject(String fileName) {
		File file = new File("Gamefiles//" + fileName);

		Object obj = null;

		if (file.exists()) {
			try {
				FileInputStream input = new FileInputStream(file);

				ObjectInputStream objectStream = new ObjectInputStream(input);

				obj = objectStream.readObject();

				input.close();
				objectStream.close();

			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

}
