/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package launcherserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * # Susijungimas su klientais
ClientListeningHostName = 192.168.22.169
ClientListeningPort = 12010

# Susijungimas su login ir game serveriais
ServersListeningHostName = 192.168.22.169
LoginServerListeningPort = 9040
GameServerListeningPort = 9050
 * @author lnix
 */
public class LauncherConfig {
	//==========================================================================================
	public static final String	CONFIG_FILE	= "./config/launcherserver.properties";
	//===========================================================================================
	public static String			CLIENT_LISTENING_HOSTNAME;
	public static int			CLIENT_LISTENINT_PORT;
	public static String			SERVERS_LISTENING_HOSTNAME;
	public static int			LOGINSERVER_LISTENING_PORT;
        public static int			GAMESERVER_LISTENING_PORT;
        public static boolean                   DEBUG;
	public static void loadConfig()
	{
		System.out.println("Loading: " + CONFIG_FILE + ".");
		try
		{
			Properties ConfigSettings = new Properties();
			InputStream is = new FileInputStream(new File(CONFIG_FILE));
			ConfigSettings.load(is);
			is.close();

			CLIENT_LISTENINT_PORT = Integer.parseInt(ConfigSettings.getProperty("ClientListeningPort", "12010"));
			LOGINSERVER_LISTENING_PORT = Integer.parseInt(ConfigSettings.getProperty("LoginServerListeningPort", "9040"));
			GAMESERVER_LISTENING_PORT = Integer.parseInt(ConfigSettings.getProperty("GameServerListeningPort", "9050"));
                        CLIENT_LISTENING_HOSTNAME = ConfigSettings.getProperty("ClientListeningHostName", "127.0.0.1");
                        SERVERS_LISTENING_HOSTNAME = ConfigSettings.getProperty("ServersListeningHostName", "127.0.0.1");
                        DEBUG = Boolean.parseBoolean(ConfigSettings.getProperty("Debug", "false"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new Error("Failed to Load " + CONFIG_FILE + " File.");
		}
	}
}
