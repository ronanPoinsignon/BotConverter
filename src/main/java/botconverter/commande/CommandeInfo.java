package botconverter.commande;

import botconverter.bot.Bot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandeInfo extends ListenerAdapter {

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		if(args[0].equals(Bot.PREFIX + "info")) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					EmbedBuilder embed = new EmbedBuilder();
					embed.setTitle("Liste des commandes");
					embed.addField("Commande Info", "Donne la liste des commandes disponibles.\nex : "
							+ Bot.PREFIX + "info", false);
					embed.addField("Commande mp3", "Convertit une vidéo Youtube en fichier mp3.\nex : " 
							+ Bot.PREFIX + "mp3 https://www.youtube.com/watch?v=hT_nvWreIhg", false);
					event.getChannel().sendMessage(embed.build()).queue();
					try {
						Thread.currentThread().interrupt();
					}
					catch(SecurityException e) {
						
					}
				}
			}).start();
		}
	}
	
}
