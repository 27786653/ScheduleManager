package com.yuhi.core.zk;

import java.nio.charset.Charset;

import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;



/**
 * ZooKeeper工具类
 * 
 *
 * 
 */
public class ZooKeeperUtil {
	
	  public static boolean deleteNodebyPath(ZooKeeper zk, String path) throws KeeperException, InterruptedException {
	        if (path.equals("/")) {
	            return true;
	        }
	        Stat exists = zk.exists(path, false);
	        if(exists!=null)
	       zk.delete(path, exists.getVersion());
	        return true;
	    }
	  
	public static String getNodeData(ZooKeeper zk, String path){
		byte[] data=null;
		try {
			data=zk.getData(path, false, null);
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(data!=null&&data.length>0){
			return new String(data, Charset.forName("UTF-8"));
		}
		return null;
	}
	
	
	public static boolean updateNotedata(ZooKeeper zk, String path,byte data[]){
		try {
			Stat exists = zk.exists(path, false);
			if(exists != null){
				zk.setData(path, data, exists.getVersion()+1);
				return true;
			}
		} catch (KeeperException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 递归create node
	 * @param zk
	 * @param path
	 * @param data
	 * @return
	 * @throws KeeperException
	 * @throws InterruptedException
	 */
    public static String createNodeWithRecurrence(ZooKeeper zk, String path,
            byte data[]) throws KeeperException, InterruptedException {
        if (path.equals("/")) {// now path is root
            return path;
        }
        int index = path.lastIndexOf("/");
        if (index == 0) { 
            return zk.create(path, data, Ids.OPEN_ACL_UNSAFE,
                    CreateMode.PERSISTENT);
        }
        String parentPath = path.substring(0, index);
        Stat stat = zk.exists(parentPath, false);
        if (stat == null) {
            createNodeWithRecurrence(zk, parentPath, "".getBytes());
        }
        
        return zk.create(path, data, Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }
    /**
     * 递归delete node
     * @param zk
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public static boolean deleteNodeWithRecurrence(ZooKeeper zk, String path) throws KeeperException, InterruptedException {
        if (path.equals("/")) {
            return true;
        }
        Stat stat;
        int index = path.lastIndexOf("/");
        if (index == 0) { 
        	stat = zk.exists(path, false);
        	zk.delete(path, stat.getVersion());
            return true;
        }
        stat = zk.exists(path, false);
        if (stat != null) {
        	zk.delete(path, stat.getVersion());
        }
        String parentPath = path.substring(0, index);
    	deleteNodeWithRecurrence(zk, parentPath);
        return true;
    }
    /**
     * 循环创建znode，即如果parent znode不存在，则先创建parent znode
     * 
     * @param zk
     * @param path
     * @param data
     * @param acl
     * @param createMode
     * @param cb
     * @param ctx
     * @throws InterruptedException
     * @throws KeeperException
     */
    public static void createNodeWithRecurrence(ZooKeeper zk, String path,
            byte data[], AsyncCallback.StringCallback cb, Object ctx) {

    }

    public static void main(String[] args) {
        ZooKeeperManager zooKeeperManager = ZooKeeperManager.getInstance();
        zooKeeperManager.init();

        try {
            String str = createNodeWithRecurrence(zooKeeperManager.getZk(),
                    "/pms/schedule/task", "".getBytes());
//            System.out.println(str);
//        	deleteNodeWithRecurrence(zooKeeperManager.getZk(),"/dubbo");
//        	String path =Constants.ZK_ROOT+Constants.ZK_CONFIGMANAGER;
//        	String str = createNodeWithRecurrence(zooKeeperManager.getZk(),
//        			path+"/dubbo.cache.dir", "/output/".getBytes());
        } catch (KeeperException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}