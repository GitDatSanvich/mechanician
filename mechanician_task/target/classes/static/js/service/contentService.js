//服务层
app.service('contentService',function($http){
	    	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../content/findAll.do');		
	}
	//分页 
	this.findPage=function(pageNum,pageSize){
		return $http.get('../content/findPage/'+pageNum+"/"+pageSize);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../content/findOne?id='+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../content/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../content/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../content/delete/'+ids);
	}
	//搜索
	this.search=function(pageNum,pageSize,searchEntity){
		return $http.post('../content/search?pageNum='+pageNum+"&pageSize="+pageSize, searchEntity);
	}    	
});
