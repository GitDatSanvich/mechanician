app.service("typeTemplateService",function($http){
	
	
	this.findAll=function(){
		return $http.get('../typeTemplate/findAll');
	}
	
	this.search=function(pageNo,pageSize,searchEntity){
		// return $http.post("../data/typeTemplate.json",searchEntity);  // TODO测试数据
		return $http.post("../typeTemplate/search?pageNum="+pageNo+"&pageSize="+pageSize,searchEntity);  // TODO测试数据
	}
	
	this.add=function(entity){
		return $http.post("../typeTemplate/add",entity);
	}
	
	this.update=function(entity){
		return $http.post("../typeTemplate/update",entity);
	}
	
	
	this.findOne=function(id){
		return $http.get("../typeTemplate/findOne?id="+id); // TODO测试数据
	}
	
})