package com.yuhi.core.zk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.zookeeper.AsyncCallback.StatCallback;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.KeeperException.Code;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.ZooKeeper.States;
import org.apache.zookeeper.data.Stat;



/**
 * ZooKeeperManager
 */
public class ZooKeeperManager implements Watcher, StatCallback {

    private String connectString;
    private int sessionTimeout;

    private ZooKeeper zk = null;
	private String connecturl="183.57.43.3:10026";

    private static boolean initFlag = false;

    private static ZooKeeperManager zooKeeperManager = null;

    private static final ArrayList<String> znodePathList = new ArrayList<String>();
	private static final int ZK_TIMEOUT = 5000;


    private ZooKeeperManager() {

    }

    public synchronized static ZooKeeperManager getInstance() {
    	if (zooKeeperManager == null) {
            zooKeeperManager = new ZooKeeperManager();
        }
        return zooKeeperManager;
    }

    public void init() {
        if (initFlag) {
            return;
        }
        connectString=connecturl;
        sessionTimeout=ZK_TIMEOUT;
        try {
            zk = new ZooKeeper(connectString, sessionTimeout, this);
            
            while(zk.getState().equals(States.CONNECTING)){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {}
            }
            
            if(!zk.getState().equals(States.CONNECTED)){
                throw new IOException("Can not connect to :" + connectString);
            }
            initFlag = true;
        } catch (IOException e) {
            e.printStackTrace();
            initFlag = false;
        }
    }

    public void close() {
        if(zk != null) {
            try {
                zk.close();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
            }
        }
        //防止过高频次的重试链接.
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {}
        
        //重新初始化
        initFlag = false;
        init();
    }

    private void exists() {
        for (int i = 0; i < znodePathList.size(); i++) {
            zk.exists(znodePathList.get(i), true, this, null);
        }
    }

    public void register(String path) {
        if (path == null) {
            return;
        }
        zk.exists(path, true, this, null);
        if (znodePathList.indexOf(path) == -1) {
            znodePathList.add(path);
        }
    }

    public void register(List<String> pathList) {
        if (pathList == null) {
            return;
        }
        for (int i = 0; i < pathList.size(); i++) {
            register(pathList.get(i));
        }
    }
    
    public void register(Set<String> pathSet) {
        if (pathSet == null) {
            return;
        }
        Iterator<String> iterator = pathSet.iterator();
        while (iterator.hasNext()) {
            register(iterator.next());
        }
    }
    
    @Override
    public void process(WatchedEvent event) {
        try {
            if (event.getType().equals(Event.EventType.None)) {
                switch (event.getState()) {
                case SyncConnected:
                    exists();
                    break;
                case Expired:
                    close();
                    break;
                default:
                    break;
                }
            } else {
                if (znodePathList.indexOf(event.getPath()) != -1) {
                	zk.exists(event.getPath(), true, this, null);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void processResult(int rc, String path, Object ctx, Stat stat) {
        boolean exists;
        switch (rc) {
        case Code.Ok:
            exists = true;
            break;
        case Code.NoNode:
            exists = false;
            break;
        case Code.SessionExpired:
        case Code.ConnectionLoss:
        case Code.NoAuth:
            close();
            return;
        default:
            // Retry errors
            zk.exists(path, true, this, null);
            return;
        }

        byte b[] = null;
        if (exists) {
            try {
                b = zk.getData(path, false, null);
            } catch (KeeperException e) {
                // We don't need to worry about recovering now. The watch
                // callbacks will kick off any exception handling
                e.printStackTrace();
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    public ZooKeeper getZk() {
        return zk;
    }

    public void setConnectString(String connectString) {
        this.connectString = connectString;
    }

    public void setSessionTimeout(int sessionTimeout) {
        this.sessionTimeout = sessionTimeout;
    }

}
