package humine.utils.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class CommandFile {

	public static List<String> loadCommandFile(File file) throws IOException {
		if(!file.exists())
			throw new FileNotFoundException("Impossible de load le fichier de palier");
		
		List<String> commands = new ArrayList<>();
		@SuppressWarnings("resource")
		BufferedReader reader = new BufferedReader(new FileReader(file));
		
		if(reader.ready()) {
			String line = reader.readLine();
			while(line != null) {
				if(!line.isEmpty() && line.charAt(0) != '#')
					commands.add(line);
				line = reader.readLine();
			}
		}
		
		return commands;
	}
}
