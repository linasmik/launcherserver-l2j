/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package launcherserver;

import launcherserver.tools.Rnd;
import org.apache.log4j.Logger;

/**
 *
 * @author lnix
 */
public class LauncherManager {
        private static final Logger _log = Logger.getLogger(LauncherManager.class);
	protected byte[][] _blowfishKeys;
	private static final int BLOWFISH_KEYS = 100;
        private static LauncherManager _instance;

        public static void load()
	{
		if (_instance == null)
			_instance = new LauncherManager();
		else
			throw new IllegalStateException("LauncherManager can only be loaded a single time.");
	}
        
	private void generateBlowFishKeys()
	{
		_blowfishKeys = new byte[BLOWFISH_KEYS][16];

		for (int i = 0; i < BLOWFISH_KEYS; i++)
		{
			for (int j = 0; j < _blowfishKeys[i].length; j++)
				_blowfishKeys[i][j] = (byte) (Rnd.nextInt(255) + 1);
		}
		_log.info("Stored " + _blowfishKeys.length + " keys for Blowfish communication");
	}

	public byte[] getBlowfishKey()
	{
		return _blowfishKeys[(int) (Math.random() * BLOWFISH_KEYS)];
	}

        public LauncherManager()
        {
		try
		{
			_log.info("LauncherManager initiating");
			// Store keys for blowfish communication
			generateBlowFishKeys();
		}
		catch (Exception e)
		{
			_log.fatal("FATAL: Failed initializing LauncherManager. Reason: " + e.getMessage(), e);
			System.exit(1);
		}
        }
        
	public static LauncherManager getInstance()
	{
		return _instance;
	}
}
