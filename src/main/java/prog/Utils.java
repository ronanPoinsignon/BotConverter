package prog;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * Classe Fourre-tout.
 * @author ronan
 *
 */
public class Utils {

	public static final String DIRECTORY_CHOOSER_SAVE = "sauvegarder";
	public static final String DIRECTORY_CHOOSER_CONVERTIR = "convertir";
	private static final int[] illegalFileChars = {34, 60, 62, 124, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 58, 42, 63, 92, 47};
	
	/**
	 * Convertit une Url en une map correspondant aux paramètres de cette Url.
	 * @param query
	 * @return
	 */
	public static Map<String, String> getQueryMap(String query) {
		Map<String, String> map = new HashMap<String, String>();
		if(query.split("\\?").length < 2)
			return map;
		String[] params = query.split("\\?")[1].split("&");  
	    for (String param : params) {
	    	try {
		        String name = param.split("=")[0];  
		        String value = param.split("=")[1];  
		        map.put(name, value);
	    	}
	    	catch(ArrayIndexOutOfBoundsException e) {
	    		
	    	}
	    }  
	    return map;  
	}
	
	/**
	 * Récupère l'id de la vidéo Youtube.
	 * @param url
	 * @return
	 */
	public static String getvideoId(String url) {
		String id = Utils.getQueryMap(url).get("v");
		if(id == null) {
			id = url.substring(url.lastIndexOf("/") + 1);
		};
		if(id == null) {
			id = url;
		}
		return id;
	}
	
	/**
	 * Retourne une chaîne de caractère ne contenant pas de caractères problématiques pour la 
	 * création de fichiers / dossiers.
	 * @param title
	 * @return
	 */
	public static String getVideoTitleWithoutIllegalChar(String title) {
		Arrays.sort(illegalFileChars);
		StringBuilder newTitle = new StringBuilder();
	    for (int i = 0; i < title.length(); i++) {
	        int c = (int)title.charAt(i);
	        if (Arrays.binarySearch(illegalFileChars, c) < 0) {
	        	newTitle.append((char)c);
	        }
	    }
	    return newTitle.toString().replaceAll("\\(", "[").replaceAll("\\)", "]");
	}
	
	public static void deleteFile(File folder) throws IOException {
		Files.walk(folder.toPath())
			.sorted(Comparator.reverseOrder())
			.map(Path::toFile)
			.forEach(File::delete);
			folder.delete();
	}
	
}
