/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package launcherserver;

import java.io.IOException;
import launcherserver.security.NewCrypt;
import launcherserver.tools.Rnd;
import org.apache.log4j.Logger;

/**
 *
 * @author lnix
 */
public class LauncherCrypt {
    private static final Logger _log	= Logger.getLogger(LauncherCrypt.class);
private static final byte[] STATIC_BLOWFISH_KEY	= {
			(byte) 0x75,
			(byte) 0x5D,
			(byte) 0x37,
			(byte) 0x97,
			(byte) 0x19,
			(byte) 0x87,
			(byte) 0x11,
			(byte) 0xDA,
			(byte) 0xE7,
			(byte) 0xA5,
			(byte) 0xD1,
			(byte) 0xB8,
			(byte) 0xE5,
			(byte) 0xE7,
			(byte) 0xF8,
			(byte) 0x11};

	private NewCrypt			_staticCrypt		= new NewCrypt(STATIC_BLOWFISH_KEY);
	private NewCrypt			_crypt;
	private boolean				_static				= true;

	public void setKey(byte[] key)
	{
		_crypt = new NewCrypt(key);
	}

	public boolean decrypt(byte[] raw, final int offset, final int size) throws IOException
	{
            return true;/*
		_crypt.decrypt(raw, offset, size);
		return NewCrypt.verifyChecksum(raw, offset, size);*/
	}

	public int encrypt(byte[] raw, final int offset, int size) throws IOException
	{
                _log.info("SIZE PRIES: "+Integer.toString(size));
		// reserve checksum
		size += 4;

		if (_static)
		{
			// reserve for XOR "key"
			//size += 4;

			// padding
			size += 8 - size % 8;
			//NewCrypt.encXORPass(raw, offset, size, Rnd.nextInt(Integer.MAX_VALUE));
                        NewCrypt.appendChecksum(raw, offset, size);
			_staticCrypt.crypt(raw, offset, size);
			_static = false;
		}
		else
		{
			// padding
			size += 8 - size % 8;
			NewCrypt.appendChecksum(raw, offset, size);
			_crypt.crypt(raw, offset, size);
		}
                _log.info("SIZE PO: "+Integer.toString(size));
		return size;
	}
}
