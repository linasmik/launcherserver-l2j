/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package launcherserver;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import launcherserver.mmocore.SelectorConfig;
import launcherserver.mmocore.SelectorThread;
import launcherserver.network.clientpackets.handler.ClientPacketHandler;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

import launcherserver.thread.GameServerListener;
/**
 *
 * @author lnix
 */
public class LauncherServer {

        private static LauncherServer   _instance;
	private static Logger 		_log	= Logger.getLogger(LauncherServer.class);
        private GameServerListener	_gameServerListener;
        private SelectorThread<LauncherClient>	_selectorThread;
        
        public static LauncherServer getInstance()
	{
		return _instance;
	}

        public static void main(String[] args) {
                _instance = new LauncherServer();
        }

        public GameServerListener getGameServer()
        {
                return _gameServerListener;
        }

        public LauncherServer()
        {
                new File("log").mkdirs();
                LauncherConfig.loadConfig();
                DOMConfigurator.configure("./config/log4j.xml");

                GameServerManager.getInstance();
                LauncherManager.load();

                initClientNetworkLayer();
                initGSListener();
                startServer();
        }

        private void startServer()
	{
		try
		{
                        _log.info("Listening for Clients on " + LauncherConfig.CLIENT_LISTENING_HOSTNAME + ":" + LauncherConfig.CLIENT_LISTENINT_PORT);
			_selectorThread.openServerSocket(InetAddress.getByName(LauncherConfig.CLIENT_LISTENING_HOSTNAME), LauncherConfig.CLIENT_LISTENINT_PORT);
		}
		catch (IOException e)
		{
			_log.fatal("FATAL: Failed to open server socket. Reason: " + e.getMessage(), e);
			System.exit(1);
		}
		_selectorThread.start();
	}

        private void initClientNetworkLayer()
        {
                ClientPacketHandler clientPacketHandler = new ClientPacketHandler();
		SelectorHelper sh = new SelectorHelper();
		SelectorConfig<LauncherClient> ssc = new SelectorConfig<LauncherClient>(null, null, sh, clientPacketHandler);
		try
		{
			_selectorThread = new SelectorThread<LauncherClient>(ssc, sh, sh, sh);
		}
		catch (IOException e)
		{
			_log.fatal("FATAL: Failed to open Selector. Reason: " + e.getMessage(), e);
			System.exit(1);
		}
        }

	private void initGSListener()
	{
		_gameServerListener = new GameServerListener(LauncherConfig.SERVERS_LISTENING_HOSTNAME, LauncherConfig.GAMESERVER_LISTENING_PORT);
		_gameServerListener.start();
		_log.info("Listening for GameServers on " + LauncherConfig.SERVERS_LISTENING_HOSTNAME + ":" + LauncherConfig.GAMESERVER_LISTENING_PORT);
	}
        
        public GameServerListener getGameServerListener()
        {
                return _gameServerListener;
        }
}