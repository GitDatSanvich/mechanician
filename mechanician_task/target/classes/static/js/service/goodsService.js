//服务层
app.service('goodsService',function($http){
	    	
	this.updateAuditStatus=function(ids,auditStatus){
		return $http.get('../goods/updateAuditStatus/'+ids+"/"+auditStatus);
	}
	
	//读取列表数据绑定到表单中
	this.findAll=function(){
		return $http.get('../goods/findAll.do');		
	}
	//分页 
	this.findPage=function(pageNum,pageSize){
		return $http.get('../goods/findPage/'+pageNum+"/"+pageSize);
	}
	//查询实体
	this.findOne=function(id){
		return $http.get('../goods/findOne/'+id);
	}
	//增加 
	this.add=function(entity){
		return  $http.post('../goods/add.do',entity );
	}
	//修改 
	this.update=function(entity){
		return  $http.post('../goods/update.do',entity );
	}
	//删除
	this.dele=function(ids){
		return $http.get('../goods/delete/'+ids);
	}
	//搜索
	this.search=function(pageNum,pageSize,searchEntity){
		return $http.post('../goods/search/'+pageNum+"/"+pageSize, searchEntity);
	}    	
});
