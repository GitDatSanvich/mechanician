app.service("brandService",function ($http) {
    // 只和后台交互，不和html交互 不需要出现$scope
    this.findPage=function (pageNo,pageSize) {
        return $http.get("../brand/findPage?pageNo="+pageNo+"&pageSize="+pageSize);
    }

    //此方法是添加模板时需要的 要求数据返回的格式是：[{"id":1,"text":"联想"},{"id":2,"text":"华为"}]
    this.findBrandList=function () {
        return $http.get("../brand/findBrandList");
    }

    // 查询所有
    this.findAll=function () {
       return $http.get("../brand/findAll");
    }

    this.add=function (entity) {
      return  $http.post("../brand/add",entity);
    }

    this.update=function (entity) {
        return  $http.post("../brand/update",entity);
    }



    this.findOne=function (id) {
       return $http.get("../brand/findOne?id="+id);
    }



    this.dele=function (selectIds) {
            return $http.get("../brand/dele?ids="+selectIds);
    }

    this.search=function (pageNo,pageSize,searchEntity) {

        return $http.post("../brand/search?pageNo="+pageNo+"&pageSize="+pageSize, searchEntity);
    }

})