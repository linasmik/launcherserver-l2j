/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package launcherserver;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import launcherserver.mmocore.HeaderInfo;
import launcherserver.mmocore.IAcceptFilter;
import launcherserver.mmocore.IClientFactory;
import launcherserver.mmocore.IMMOExecutor;
import launcherserver.mmocore.ISocket;
import launcherserver.mmocore.ReceivablePacket;
import launcherserver.mmocore.SelectorThread;
import launcherserver.mmocore.TCPHeaderHandler;
import launcherserver.network.clientpackets.send.InitLauncher;
import launcherserver.thread.GameServerThread;
import org.apache.log4j.Logger;

/**
 *
 * @author lnix
 */
public class SelectorHelper extends TCPHeaderHandler<LauncherClient> implements IMMOExecutor<LauncherClient>, IClientFactory<LauncherClient>, IAcceptFilter
{
        private static final Logger     _log = Logger.getLogger(GameServerThread.class.getName());
	private final ThreadPoolExecutor _generalPacketsThreadPool = new ThreadPoolExecutor(1, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

	public SelectorHelper()
	{
		super(null);
	}

	public void execute(ReceivablePacket<LauncherClient> packet)
	{
		_generalPacketsThreadPool.execute(packet);
	}

	public LauncherClient create(SelectorThread<LauncherClient> selectorThread, ISocket socket, SelectionKey key)
	{
		LauncherClient client = new LauncherClient(selectorThread, socket, key);
                client.sendPacket(new InitLauncher(client));
		return client;
	}

	public boolean accept(SocketChannel sc)
	{
		return true;
	}

	@Override
	public HeaderInfo<LauncherClient> handleHeader(SelectionKey key, ByteBuffer buf)
	{
		if (buf.remaining() >= 2)
		{
			int dataPending = (buf.getShort() & 0xffff) - 2;
			return getHeaderInfoReturn().set(0, dataPending, false, (LauncherClient)key.attachment());
		}
		else
			return getHeaderInfoReturn().set(2 - buf.remaining(), 0, false, (LauncherClient)key.attachment());
	}
}