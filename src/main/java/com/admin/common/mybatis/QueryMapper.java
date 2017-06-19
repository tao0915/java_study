package com.admin.common.mybatis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class QueryMapper {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    
	@Autowired
    private SqlSessionTemplate sqlSession;
     
    protected void printQueryId(String queryId) {
        if(logger.isDebugEnabled()){
            logger.debug("\t QueryId  \t:  " + queryId);
        }
    }
     
    public int insert(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.insert(queryId, params);
    }
     
    public int update(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.update(queryId, params);
    }
     
    public int delete(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.delete(queryId, params);
    }
     
    public Object selectOne(String queryId){
        printQueryId(queryId);
        return sqlSession.selectOne(queryId);
    }
     
    public Object selectOne(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.selectOne(queryId, params);
    }
     
    @SuppressWarnings("rawtypes")
    public List selectList(String queryId){
        printQueryId(queryId);
        return sqlSession.selectList(queryId);
    }
     
    @SuppressWarnings("rawtypes")
    public List selectList(String queryId, Object params){
        printQueryId(queryId);
        return sqlSession.selectList(queryId,params);
    }
    
    @SuppressWarnings("unchecked")
	public Map<String, Object> selectPageList(String queryId , Map<String, Object> params){
    	if(params == null) params = new HashMap<String, Object>();
    	StringBuilder queryIds = new StringBuilder(queryId);
    	int page = 1;														//페이지
    	int listSize = 10;													//목록에 보여지는 페이지
    	int totalCount = 0;
    	int startRow = 0;
    	int endRow = 0;
    	int pageSize = 10;    	
    	
    	if(params.get("page") != null) {			
    		try{
    			page =	Integer.parseInt(params.get("page").toString());
    		}catch(Exception e){}
    	}
    	
    	if(params.get("listSize") != null) {		 
    		try{
    			listSize =	Integer.parseInt(params.get("listSize").toString());
    		}catch(Exception e){}
    	}
    	
    	
    	if(params.get("totalCount") != null) {		 
    		try{
    			totalCount =	Integer.parseInt(params.get("totalCount").toString());
    		}catch(Exception e){}
    	}
    	
    	if(params.get("pageSize") != null) {		 
    		try{
    			pageSize =	Integer.parseInt(params.get("pageSize").toString());
    		}catch(Exception e){}
    	}
    	
    	startRow = ((page*listSize)-listSize)+1; 
    	endRow =page*listSize; 
    	
    	params.put("startRow", startRow);
    	params.put("endRow", endRow);
    	
    	printQueryId(queryIds.toString());
    	List<Map<String, Object>> dataList = this.selectList(queryIds.toString(), params);
    	
    	if(totalCount == 0){
    		if(dataList != null && dataList.size() > 0){
    				queryIds.append("Count");
    				printQueryId(queryIds.toString());
    				totalCount = (Integer) this.selectOne(queryIds.toString(), params);
    				if(totalCount < dataList.size()){ totalCount = dataList.size(); }
        	}	
    	}
    	
    	int totalPage = totalCount/listSize;    	
    	int remNum = totalCount%listSize;
    	if(remNum > 0) totalPage++;
    	
		if(page > totalPage) page = totalPage;
		
		int startPage = (int) ((Math.ceil((double)page / (double)pageSize) - 1) * (double)pageSize + 1);
		int endPage = (startPage + pageSize - 1) > totalPage ? totalPage : (startPage + pageSize - 1);
    	

    	
    	Map<String,Object> resultMap = new HashMap<String, Object>();
    	resultMap.put("list", dataList);
    	resultMap.put("totalCount", totalCount);
    	resultMap.put("page", page);
    	resultMap.put("listSize", listSize);
    	resultMap.put("startRow", startRow);
    	resultMap.put("endRow", endRow);
    	resultMap.put("pageSize", pageSize);
    	resultMap.put("startPage", startPage);
    	resultMap.put("endPage", endPage);
    	resultMap.put("totalPage", totalPage);
    	
    	return resultMap;
    }
    
}
