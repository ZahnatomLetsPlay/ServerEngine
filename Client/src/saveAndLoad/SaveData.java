package saveAndLoad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SaveData implements Serializable {

	private static final long serialVersionUID = 8799586945404931738L;

	private File filePath;

	/**
	 * Saves the given Object.
	 * <p>
	 * FileName: assigns the name to a .ser file for identifying
	 * </p>
	 * 
	 * @param obj
	 * @param filename
	 */
	public void saveData(Object obj, String fileName) {
		filePath = new File("Gamefiles//" + fileName + ".ser");
		if(filePath.exists()) {
			filePath.delete();
			System.out.println("file deleted");
		}
		try {
			FileOutputStream fileOutput = new FileOutputStream(filePath);
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutput);
			objectOutputStream.writeObject(obj);
			fileOutput.close();
			objectOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
