/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package launcherserver;

import org.apache.log4j.Logger;

/**
 *
 * @author lnix
 */
public class GameServerInfo {
	private static final Logger 		_log			= Logger.getLogger(GameServerInfo.class);
	private static GameServerInfo	_instance		= null;
        private Integer _OnlinePlayers = 0;
        private Boolean _Status = false;
        private String _TopPlayer1 = "";
        private String _TopPlayer2 = "";
        private String _TopPlayer3 = "";
        private String _TopPlayer4 = "";
        private String _TopPlayer5 = "";
        private Integer _TopPlayerRank1 = 0;
        private Integer _TopPlayerRank2 = 0;
        private Integer _TopPlayerRank3 = 0;
        private Integer _TopPlayerRank4 = 0;
        private Integer _TopPlayerRank5 = 0;
        
        public static GameServerInfo getInstance()
        {
                if(_instance == null)
                {
                        _instance = new GameServerInfo();
                }
                return _instance;
        }
        
        public void GameServerManager()
        {
        }

        public void setPlayerNick(int p, String name)
        {
            switch (p)
            {
                case 1: _TopPlayer1 = name;
                    break;
                case 2: _TopPlayer2 = name;
                    break;
                case 3: _TopPlayer3 = name;
                    break;
                case 4: _TopPlayer4 = name;
                    break;
                case 5: _TopPlayer5 = name;
                    break;
            }
        }

        public void setPlayerRank(int player_postition, Integer rank)
        {
            switch (player_postition)
            {
                case 1: _TopPlayerRank1 = rank;
                    break;
                case 2: _TopPlayerRank2 = rank;
                    break;
                case 3: _TopPlayerRank3 = rank;
                    break;
                case 4: _TopPlayerRank4 = rank;
                    break;
                case 5: _TopPlayerRank5 = rank;
                    break;
            }
        }

        public String getPlayerNick(int player_position)
        {
            String player = _TopPlayer1;
            switch (player_position) {
                case 1:  player = _TopPlayer1;
                         break;
                case 2:  player = _TopPlayer2;
                         break;
                case 3:  player = _TopPlayer3;
                         break;
                case 4:  player = _TopPlayer4;
                         break;
                case 5:  player = _TopPlayer5;
                         break;
                default: player = "";
                         break;
            }
            
            return player;
        }
        
        public Integer getPlayerRank(int p)
        {
            Integer rank = _TopPlayerRank1;
            switch (p) {
                case 1:  rank = _TopPlayerRank1;
                         break;
                case 2:  rank = _TopPlayerRank2;
                         break;
                case 3:  rank = _TopPlayerRank3;
                         break;
                case 4:  rank = _TopPlayerRank4;
                         break;
                case 5:  rank = _TopPlayerRank5;
                         break;
                default: rank = 0;
                         break;
            }
            
            return rank;
        }
        
        public boolean getStatus()
        {
            return _Status;
        }
        
        public Integer getPlayers()
        {
            return _OnlinePlayers;
        }
        
        public void setOnlinePlayers(int players)
        {
            _OnlinePlayers = players;
        }
        
        public void setStatus(boolean status)
        {
            _Status = status;
        }
}