package botconverter.commande;

import botconverter.bot.Bot;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandePong extends ListenerAdapter {

	@Override
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		if(args[0].equals(Bot.PREFIX + "ping")) {
			long time = System.currentTimeMillis();
			event.getChannel().sendMessage("pong").queue(response -> {
				response.editMessageFormat("Pong: %d ms", System.currentTimeMillis() - time).queue();
			});
		}
	}

}
