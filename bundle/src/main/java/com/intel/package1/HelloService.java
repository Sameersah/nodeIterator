package com.intel.package1;

import java.util.List;

/**
 * A simple service interface
 */
public interface HelloService {
    
    /**
     * @return the name of the underlying JCR repository implementation
     */
    public String getRepositoryName();
    public List<String> getNodeNames(String relativePath);
    public List<String> getNodePaths(String relativePath);
    public List<Integer> getNodeDepths(String relativePath);

}