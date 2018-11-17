package main.java;

import java.lang.Integer;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;

public class Main {
	private static String PROXY_HOST = System.getenv("PROXY_HOST");
	private static Integer PROXY_PORT = Integer.parseInt(System.getenv("PROXY_PORT"));
	private static String PROXY_USER = System.getenv("PROXY_USER");
	private static String PROXY_PASSWORD = System.getenv("PROXY_PASSWORD");

	public static void main(String[] args) {
		ApiContextInitializer.init();
		TelegramBotsApi botsApi = new TelegramBotsApi();


		Authenticator.setDefault(new Authenticator() {
			@Override
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(PROXY_USER, PROXY_PASSWORD.toCharArray());
			}
		});

		DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);
		botOptions.setProxyHost(PROXY_HOST);
		botOptions.setProxyPort(PROXY_PORT);
		botOptions.setProxyType(
				DefaultBotOptions.ProxyType.SOCKS5);

		try {
			botsApi.registerBot(new TelegramEntryPoint(botOptions));
		} catch (TelegramApiRequestException e) {
			e.printStackTrace();
		}

	}

}
