package botconverter.commande;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import botconverter.bot.Bot;
import botconverter.task.TacheConvertirInstant;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import prog.video.Video;

public class CommandeMp3 extends ListenerAdapter {

	public static final File BOT_FOLDER = new File("./");
	public static final int BIT_RATE = 320000;

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		if(args[0].equals(Bot.PREFIX + "mp3")) {
			if(args.length != 2) {
				EmbedBuilder embed = new EmbedBuilder();
				embed.setTitle("Erreur dans la commande");
				embed.setColor(Color.RED);
				embed.setDescription("la commande ne doit avoir qu'un paramètre ");
				return;
			}
			String url = args[1];
			List<String> listeExtensions = new ArrayList<>();
			listeExtensions.add("mp3");
			TacheConvertirInstant tache = new TacheConvertirInstant(url, BOT_FOLDER, BIT_RATE, listeExtensions);
			List<File> listeMp3 = tache.convertir();
			List<Video> listeMauvaisesVideos = tache.getListeMauvaisesVideos();
			List<String> listeMauvaisLiens = tache.getListeMauvaisLiens();
			List<String> listeUrlsErreur = tache.getListeUrlsErreur();
			
			if(!listeMp3.isEmpty()) {
				event.getChannel().sendFile(listeMp3.get(0)).queue();
			}
			
			for(File fichier : listeMp3) {
				fichier.delete();
			}
			
			if(!listeMauvaisesVideos.isEmpty()) {
				EmbedBuilder embed = new EmbedBuilder();
				embed.setTitle("Vidéo(s) non valide(s)");
				embed.setColor(Color.RED);
				StringBuilder str = new StringBuilder("La ou les vidéos suivantes ne sont pas valides :");
				for(Video video : listeMauvaisesVideos)
					str.append("\n\t- " + video.getLien());
				embed.setDescription(str.toString());
				event.getChannel().sendMessage(embed.build()).queue();
			}
			
			if(!listeMauvaisLiens.isEmpty()) {
				EmbedBuilder embed = new EmbedBuilder();
				embed.setTitle("Lien(s) non valide(s)");
				embed.setColor(Color.RED);
				StringBuilder str = new StringBuilder("Le ou les liens suivants ne redirigent pas vers une vidéo Youtube :");
				for(String lien : listeMauvaisLiens)
					str.append("\n\t- " + lien);
				embed.setDescription(str.toString());
				event.getChannel().sendMessage(embed.build()).queue();;
			}
			
			if(!listeUrlsErreur.isEmpty()) {
				EmbedBuilder embed = new EmbedBuilder();
				embed.setTitle("Lien(s) non valide(s)");
				embed.setColor(Color.RED);
				StringBuilder str = new StringBuilder("le ou les liens suivants ont apporté une erreur à l'exécution :");
				for(String lien : listeUrlsErreur)
					str.append("\n\t- " + lien);
				embed.setDescription(str.toString());
				event.getChannel().sendMessage(embed.build()).queue();
			}
		}
	}
	
}
