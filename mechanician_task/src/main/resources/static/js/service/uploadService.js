app.service("uploadService",function($http){
    // 1、	form表单提交方式一定是post
    // 2、	form表单的enctype一定是multiple/form-data
    // 3、	form表单中一定要有一个input的type是 file
    this.uploadFile=function () {
       var formData = new FormData();// html5的对象 用来向表单中放需要提交的数据
        // file.files[0]  file.files代表整个页面中所有的input类型是file的输入框
        formData.append("file",file.files[0])
		return $http({
            method:'POST',
			url:'../upload/uploadFile',
			data:formData,
		    headers: {'Content-Type':undefined}, // ‘Content-Type’: undefined，这样浏览器会帮我们把 Content-Type 设置为 multipart/form-data
            transformRequest: angular.identity// transformRequest: angular.identity ，anjularjs transformRequest function 将序列化我们的formdata object.
		})
    }
})