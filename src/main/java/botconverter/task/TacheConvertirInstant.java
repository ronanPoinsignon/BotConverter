package botconverter.task;

import java.io.File;
import java.util.List;

import prog.video.Video;

/**
 * {@link Tache} permettant une conversion instantannée en vidéo depuis un lien donné sans passer par les actions 
 * normales d'ajout dans la liste d'une vidéo puis de conversion.
 * @author ronan
 *
 */
public class TacheConvertirInstant {

	private String url;
	private File folder;
	private int bitRate;
	private List<String> listeExtensions;
	
	private List<Video> listeVideoChargees;
	private List<String> listeUrlsErreur;
	private List<String> listeMauvaisLiens;
	private List<Video> listeMauvaisesVideos;
	
	public TacheConvertirInstant(String url, File folder, int bitRate, List<String> listeExtensions) {
		this.url = url;
		this.folder = folder;
		this.bitRate = bitRate;
		this.listeExtensions = listeExtensions;
	}
	
	public List<File> convertir() {
		TacheCharger tache = new TacheCharger(url);
		listeVideoChargees = tache.charger();
		listeUrlsErreur = tache.getListeUrlsErreur();
		listeMauvaisLiens = tache.getListeUrlsMauvaisLien();
		TacheConvertirToFile tacheConvertir = new TacheConvertirToFile(listeVideoChargees, folder, bitRate, listeExtensions);
		List<File> listeFichiers =  tacheConvertir.convertirToMp3();
		listeMauvaisesVideos = tacheConvertir.getListeMauvaisFichiers();
		return listeFichiers;
	}

	public List<String> getListeUrlsErreur(){
		return this.listeUrlsErreur;
	}
	
	public List<String> getListeMauvaisLiens(){
		return this.listeMauvaisLiens;
	}
	
	public List<Video> getListeMauvaisesVideos(){
		return this.listeMauvaisesVideos;
	}
	
}
