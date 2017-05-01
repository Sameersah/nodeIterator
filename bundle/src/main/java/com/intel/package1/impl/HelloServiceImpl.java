package com.intel.package1.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import javax.jcr.LoginException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.SimpleCredentials;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.felix.scr.annotations.Service;
import org.apache.sling.jcr.api.SlingRepository;

import com.intel.package1.HelloService;



/**
 * One implementation of the {@link HelloService}. Note that
 * the repository is injected, not retrieved.
 */
@Service
@Component(metatype = false)
public class HelloServiceImpl implements HelloService {
    
    @Reference
    private SlingRepository repository;
    
    private static List<String> nodeNames; 
    private static List<String> nodePaths ;
    private static List<Integer> nodeDepths; 

    public String getRepositoryName() {
        return repository.getDescriptor(Repository.REP_NAME_DESC);
    }

    
    
	@Override
	public List<String> getNodeNames(String relativePath) {
		
		//Create a Session instance
		try {
			 nodeNames = new ArrayList<String>();
			 nodePaths = new ArrayList<String>();
			 nodeDepths = new ArrayList<Integer>();
			
			javax.jcr.Session session = repository.login( new SimpleCredentials("admin", "admin".toCharArray()),"crx.default");
			
			Node root = session.getRootNode();
			
			Node node = root.getNode(relativePath); 
			
			new HelloServiceImpl().iterator(node);
			//System.out.println(nodeNames.size());
			
			session.save();
			session.logout();
       
			return nodeNames;
			
			
			
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



public void iterator(Node currentNode){
		
		try {
			Integer parentDepth = currentNode.getParent().getDepth();
			    nodeDepths.add(parentDepth);
			//for(int i=0;i<parentDepth;i++){
			//	System.out.print("\t");
		//	}
		//	System.out.println("Node Name: "+currentNode.getName());
			nodeNames.add(currentNode.getName());
	
		//	for(int i=0;i<parentDepth;i++){
		//		System.out.print("\t");
		//	}
		//	System.out.println("Node Path: "+currentNode.getPath());
			nodePaths.add(currentNode.getPath());
			
		//	System.out.println();
       
			NodeIterator childNodes = currentNode.getNodes();
			
			Queue<Node> q = new LinkedList<Node>();
			  
			 while (childNodes.hasNext()){
				 q.add(childNodes.nextNode());
			 }
			
			 while(!q.isEmpty()){
				 Node nextNode = q.remove();
			      new HelloServiceImpl().iterator(nextNode);
			 }
			
			
		} catch (RepositoryException e) {
			
			e.printStackTrace();
		}
		  
	}



@Override
public List<String> getNodePaths(String relativePath) {
	// TODO Auto-generated method stub
	return nodePaths;
	
	
}



@Override
public List<Integer> getNodeDepths(String relativePath) {
	// TODO Auto-generated method stub
	return nodeDepths;
}


}
