package com.admin.common.comm.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class CustomMap  extends HashMap<String, Object>{
	
	public CustomMap(){		
		super();
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> getChildren() {
		if(this.get("children") != null){
			return (List<Map<String, Object>>)this.get("children");
		}
		return null;
	}

	public void setChildren(List<Map<String, Object>> children) {
		this.put("children", children);
	}
}
