/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package launcherserver;

import java.io.IOException;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import launcherserver.mmocore.ISocket;
import launcherserver.mmocore.MMOConnection;
import launcherserver.mmocore.SelectorThread;
import launcherserver.network.clientpackets.handler.ClientPacketHandler;
import launcherserver.network.clientpackets.send.LauncherFail;
import launcherserver.network.clientpackets.send.LauncherFailReason;
import launcherserver.tools.Rnd;

import org.apache.log4j.Logger;

/**
 *
 * @author lnix
 */
public class LauncherClient extends MMOConnection<LauncherClient>{
    
        private static final Logger _log	= Logger.getLogger(LauncherClient.class);
        private long _lastActive = 0;
        private String _ipAddress;
        private boolean _activated = false;
        private String _hwid;
        private int _serial;
        private boolean _isAuthed = false;
        private final byte[] _blowfishKey;
        private String _SSAdmin; // admin name witch required screenshot
        private String _SSName; // Player char name when admin required screenshot
        private String _SSAdminIp;
        private String _SSAdminHwid;
        private LauncherCrypt _launcherCrypt;

        public LauncherClient(SelectorThread<LauncherClient> selectorThread, ISocket socket, SelectionKey key)
        {
                super(selectorThread, socket, key);
            
                _lastActive = System.currentTimeMillis();
                _ipAddress = socket.getInetAddress().getHostAddress();
                _blowfishKey = LauncherManager.getInstance().getBlowfishKey();
                _serial = Rnd.get((int)100000,(int)990000);
                _hwid = "dbc7bb4a";
                ClientPacketHandler.getInstance().addClient(this); 
        }

        public boolean isAuthed()
        {
                return _isAuthed;
        }

        public void setAuthed(boolean status)
        {
                _isAuthed = status;
        }

        public void setHWID(String hwid)
        {
                _hwid = hwid;
        }

        public void setActivated(boolean status)
        {
                _activated = status;
        }

        public boolean isActivated()
        {
                return _activated;
        }

        public byte[] getBlowfishKey()
        {
                return _blowfishKey;
        }

        public String getHWID()
        {
                return _hwid;
        }

        public int getSerial()
        {
                return _serial;
        }

        public String getIpAddress()
        {
                return _ipAddress;
        }

        public void close(LauncherFailReason reason)
	{
		close(new LauncherFail(reason));
	}

        public long lastActive()
        {
                return _lastActive;
        }

        public void lastActive(long time)
        {
                _lastActive = 0;
        }
        
        public void updateActive()
        {
                _lastActive = System.currentTimeMillis();
        }

        public void setSSAdmin(String admin_name)
        {
            _SSAdmin = admin_name;
        }
        
        public String getSSadmin()
        {
            return _SSAdmin;
        }
        
        public void setSSName(String player_name)
        {
            _SSName = player_name;
        }
        
        public String getSSName()
        {
            return _SSName;
        }
        public void setSSAdminIp(String ip_address)
        {
            _SSAdminIp = ip_address;
        }
        
        public String getSSAdminIp()
        {
            return _SSAdminIp;
        }
        
        public void setSSAdminHwid(String hwid)
        {
            _SSAdminHwid = hwid;
        }
        
        public String getSSAdminHwid()
        {
            return _SSAdminHwid;
        }

	private LauncherCrypt getLauncherCrypt()
	{
		if (_launcherCrypt == null)
		{
			_launcherCrypt = new LauncherCrypt();
			_launcherCrypt.setKey(_blowfishKey);
		}

		return _launcherCrypt;
	}

        @Override
        public boolean decrypt(ByteBuffer buf, int size)
	{
		boolean ret = false;

		try
		{
			ret = getLauncherCrypt().decrypt(buf.array(), buf.position(), size);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			closeNow();
			return false;
		}

		if (!ret)
		{
			byte[] dump = new byte[size];
			System.arraycopy(buf.array(), buf.position(), dump, 0, size);
			_log.warn("Wrong checksum from client: " + toString());
			closeNow();
		}

		return ret;
        }
        
        @Override
	public boolean encrypt(ByteBuffer buf, int size)
	{
            
		final int offset = buf.position();
                
                _log.info("Encrypt offset: " + Integer.toString(offset));
		try
		{
			size = getLauncherCrypt().encrypt(buf.array(), offset, size);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}

		buf.position(offset + size);
		return true;
        }

	@Override
	public void onDisconnection()
	{

	}

	@Override
	protected void onForcedDisconnection(){}

	@Override
	public String toString()
	{
                // TODO: patikrinti ka isspausdina
		InetAddress address = getSocket().getInetAddress();
                return address.getHostAddress();
	}
}
