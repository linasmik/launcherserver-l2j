/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package launcherserver;

import launcherserver.thread.GameServerThread;
import org.apache.log4j.Logger;

/**
 *
 * @author lnix
 */
public class GameServerManager {
	private static final Logger 		_log			= Logger.getLogger(GameServerManager.class);
	private static GameServerManager	_instance		= null;
        private static GameServerThread         _gst = null;
        
        public static GameServerManager getInstance()
        {
                if(_instance == null)
                {
                        _instance = new GameServerManager();
                }
                return _instance;
        }
        
        public void GameServerManager()
        {
        }

        public void setGameServer(GameServerThread gst)
        {
                _gst = gst;
        }

        public GameServerThread getGameServerThread()
	{
		return _gst;
	}
}
