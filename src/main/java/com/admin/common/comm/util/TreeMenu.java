package com.admin.common.comm.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * 관리자에서 사용하는 tree menu형태로 만들어 줌.
 * @author seung
 *
 */
public class TreeMenu {
	private List<Map<String, Object>> menuList;
	private List<CustomMap> treeMenuList;
	 
	public TreeMenu(){}
	
	public TreeMenu(List<Map<String, Object>> menuList){
		this.menuList = menuList;
	}
	
	/**
	 * treeView 형태로 반환함
	 * @return
	 * @throws Exception
	 */
	public String getTreeMenuStr() throws Exception{
		if(this.menuList == null) { return null; }
		
		if(this.treeMenuList == null){ this.treeMenuList = this.newMenuTreeList(); }
		
		ObjectMapper om = new ObjectMapper();
		return om.writeValueAsString(this.treeMenuList);
	}
	
	/**
	 * 원본 menuList가 변경 시 새로 menuRefresh를 해주어야 
	 * 새로운 메뉴로 tree구조가 반영이됨
	 */
	public void menuRefresh(){
		if(this.menuList != null) {
			this.treeMenuList = this.newMenuTreeList();	
		}
	}
	
	private List<CustomMap> newMenuTreeList(){
		List<String> menuDepth1Menu = new ArrayList<String>(); 
		Map<String, CustomMap> menuListMap = this.createTreeMap(this.menuList, menuDepth1Menu);
		
		List<CustomMap> newMenuTreeList =  new ArrayList<CustomMap>();
		
		for(int i=0;  i<menuDepth1Menu.size(); i++) {
			String menuId = menuDepth1Menu.get(i);
			CustomMap menu = menuListMap.get(menuId);
			
			String reafYn = (String)menu.get("LEAF_YN");
			menu.put("title", menu.get("MENU_NM"));
			
			if("N".equals(reafYn)){				;
				this.changeMenuType(menu, menuListMap);
			}
			newMenuTreeList.add(menu);
		}
		
		return newMenuTreeList;
	}
	
	
	
	private  void changeMenuType(CustomMap menu, Map<String, CustomMap> menuTreeMap){
		String reafYn = (String)menu.get("LEAF_YN");
		menu.put("title", menu.get("MENU_NM"));
		if("N".equals(reafYn) ){
			menu.put("isFolder", true);
			menu.put("key",  menu.get("MENU_ID"));
			menu.put("expand",  true);
			for(int i=0; i<menu.getChildren().size(); i++){
				String menuId = (String)menu.getChildren().get(i).get("MENU_ID");
				CustomMap subMenu = menuTreeMap.get(menuId);
				menu.getChildren().set(i, subMenu);
				this.changeMenuType(subMenu, menuTreeMap);
			}
		}
	}
	
	
	public  Map<String, CustomMap> createTreeMap(List<Map<String, Object>> dataList, List<String> menuDepth1){
		Map<String, CustomMap> categoryMap = null;
		
		if(dataList != null && dataList.size() > 0){
			categoryMap = new HashMap<String, CustomMap>();
			for(int i=0; i<dataList.size(); i++){
				Map<String, Object> data = dataList.get(i);
				
				if(menuDepth1 != null && ((BigDecimal)data.get("MENU_DEPTH")).intValue() == 1){ menuDepth1.add((String)data.get("MENU_ID"));  }
				if(data.get("MENU_URL") == null) data.put("MENU_URL", "NULL");
				
				if(!categoryMap.containsKey(data.get("MENU_ID"))){
					CustomMap currtMap = new CustomMap();
					Iterator<String> it = data.keySet().iterator();
					while (it.hasNext()) {
						String key = it.next().toString();
						currtMap.put(key, data.get(key));
					}
					categoryMap.put((String)data.get("MENU_ID"), currtMap);
				}
				
				if(categoryMap.containsKey(data.get("MENU_UP_ID"))){
					if(categoryMap.get(data.get("MENU_UP_ID")).getChildren() == null){
						List<Map<String, Object>> subList = new ArrayList<Map<String, Object>>();
						subList.add(data);
						categoryMap.get(data.get("MENU_UP_ID")).setChildren(subList);
					}else{
						categoryMap.get(data.get("MENU_UP_ID")).getChildren().add(data);
					}
				}							
			}
		}
		return categoryMap;
	}


	public List<Map<String, Object>> getMenuList() {
		return menuList;
	}

	public void setMenuList(List<Map<String, Object>> menuList) {
		this.menuList = menuList;
	}
}
