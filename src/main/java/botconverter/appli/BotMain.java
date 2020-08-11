package botconverter.appli;

import javax.security.auth.login.LoginException;

import botconverter.bot.Bot;

public class BotMain {
	
	private static String token = System.getenv("TOKEN");

	public static void main(String[] args) {
		BotMain bot = new BotMain();
		bot.lancer();
	}
	
	public void lancer() {
		try {
			System.out.println(System.getProperties());
			System.out.println(System.getProperty("file.encoding"));
			@SuppressWarnings("unused")
			Bot bot = new Bot(token);
		} catch (LoginException e) {
			e.printStackTrace();
		}
	}

}
