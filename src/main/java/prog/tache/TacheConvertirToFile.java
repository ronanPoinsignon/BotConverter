package prog.tache;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import prog.Utils;
import prog.convertisseur.ConvertisseurMusique;
import prog.video.Video;
import ws.schild.jave.EncoderException;

/**
 * Classe permettant la conversion des vidéos de la liste.
 * @author ronan
 *
 */
public class TacheConvertirToFile {

	private List<Video> listeVideos;
	private File folder;
	private List<String> listeExtensions;
	private List<File> listeFichiers = new ArrayList<>();
	private List<Video> listeMauvaisFichiers = new ArrayList<>();
	
	public TacheConvertirToFile(List<Video> listeVideos, File folder, int bitRate, List<String> listeExtensions) {
		this.listeVideos = listeVideos;
		this.folder = folder;
		this.listeExtensions = listeExtensions;
	}
	
	protected List<File> convertirToMp3() {
		
		boolean hasMp4;

		if(folder == null) {
			return new ArrayList<File>();
		}
		if(listeExtensions.isEmpty()) {
			return new ArrayList<File>();
		}
		hasMp4 = listeExtensions.contains("mp4");
		File folderMp4 = null;
		//folderMp4 = new File(folder.getPath() + "\\mp4Bot");
		folderMp4 = folder;
		try {
			folderMp4.mkdirs();
		}
		catch(SecurityException e) {
			e.printStackTrace();
		}
		if(hasMp4) {
			listeExtensions.remove("mp4");
		}
		for(Video video : listeVideos) {
			try {
				listeFichiers.add(this.convertVideo(video, folder, folderMp4, hasMp4));
			}
			catch(EncoderException e) {
				e.printStackTrace();
				if(!listeMauvaisFichiers.contains(video))
					listeMauvaisFichiers.add(video);
			}
			catch(Exception e) {
				e.printStackTrace();
				listeMauvaisFichiers.add(video);
			}
		}
		return listeFichiers;
	}
	
	/**
	 * Covertit la {@link Video} en fichier vidéo.
	 * @param video
	 * @param folder
	 * @param folderMp4
	 * @param goodQuality
	 * @return
	 * @throws Exception
	 */
	private File convertVideo(Video video, File folder, File folderMp4, boolean goodQuality) throws Exception {
		ArrayList<File> listeMp4 = new ArrayList<>();
		File fichier = null, fichierMp4 = null;
		fichierMp4 = video.convertToMp4(folderMp4);
		listeMp4.add(fichierMp4);
		String titre = Utils.getVideoTitleWithoutIllegalChar(video.getTitre());
		ConvertisseurMusique convertisseur = new ConvertisseurMusique(80000);
		for(String extension : listeExtensions) {
			//File folderVideo = new File(folder + "\\" + extension.toLowerCase());
			File folderVideo = folder;
			if(!folderVideo.exists())
				try {
				folderVideo.mkdirs();
				}
			catch(SecurityException e) {
				e.printStackTrace();
			}
			fichier = new File(folderVideo.getPath() + "\\" + titre + "." + extension.toLowerCase());
			fichier = convertisseur.convertir(fichierMp4, fichier);
		}
		return fichier;
	}
	
	public List<Video> getListeMauvaisFichiers(){
		return this.listeMauvaisFichiers;
	}

}
