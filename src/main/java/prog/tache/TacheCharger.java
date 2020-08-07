package prog.tache;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.kiulian.downloader.YoutubeException;
import com.github.kiulian.downloader.YoutubeException.BadPageException;

import prog.video.Video;
import prog.video.VideoFichier;
import prog.video.VideoYtb;

/**
 * Classe permettant de charger une ou plusieurs vidéos dans la liste de vidéo.
 * @author ronan
 *
 */
public class TacheCharger {

	private List<String> listeUrls = new ArrayList<>();

	private ArrayList<String> listeUrlsMauvaisLien = new ArrayList<>(), listeUrlsErreur = new ArrayList<>();
	
	public TacheCharger(List<String> listeUrls) {
		this.listeUrls = listeUrls;
	}
	
	public TacheCharger(String url) {
		listeUrls.add(url);
	}
	
	public List<Video> charger() {
		listeUrlsMauvaisLien = new ArrayList<>();
		listeUrlsErreur = new ArrayList<>();
		ArrayList<Video> listeVideos = new ArrayList<>();
		for(String url : listeUrls) {
			try {
				File fichier = new File(url);
				if(fichier.exists()) {
					if(fichier.isFile())
						listeVideos.add(new VideoFichier(url));
					else {
						//TODO
					}
				}
				else
					listeVideos.add(new VideoYtb(url));
			} catch(BadPageException e) {
				listeUrlsMauvaisLien.add(url);
			} catch (YoutubeException | IOException e) {
				listeUrlsErreur.add(url);
			}
		}
		return listeVideos;
	}

	/**
	 * Retourne un clone de la liste d'url de vidéos étant déjà présentes dans la liste de vidéos.
	 * @return
	 */
	public ArrayList<String> getListeUrlsMauvaisLien() {
		ArrayList<String> nouvelleListeUrls = new ArrayList<>();
		nouvelleListeUrls.addAll(listeUrlsMauvaisLien);
		return nouvelleListeUrls;
	}

	/**
	 * Retourne un clone de la liste d'url ayant eu une erreur après avoir charger la liste de vidéos.
	 * @return
	 */
	public ArrayList<String> getListeUrlsErreur() {
		ArrayList<String> nouvelleListeUrls = new ArrayList<>();
		nouvelleListeUrls.addAll(listeUrlsErreur);
		return nouvelleListeUrls;
	}
	
}
