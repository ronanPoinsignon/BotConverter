package botconverter.bot;

import javax.security.auth.login.LoginException;

import botconverter.commande.CommandeInfo;
import botconverter.commande.CommandeMp3;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class Bot {

	public static final String PREFIX = ";";
	
	
	public Bot(String token) throws LoginException {
		JDA jda = JDABuilder.createLight(token)
			.addEventListeners(new CommandeInfo())
			.addEventListeners(new CommandeMp3())
			.build();
		jda.getPresence().setStatus(OnlineStatus.ONLINE);
		jda.getPresence().setActivity(Activity.playing(Bot.PREFIX + "info"));
	}
	
}
