new Vue({
    el: "#app",
    data: {
        Id: {},
        task: {},
        tools: {},
        taskId: {}
    },
    methods: {
        findById: function (Id) {
            var _this = this;
            var url = "task/" + Id;
            axios.get(url).then(function (result) {
                console.log(result);
                _this.task = result.data.data;
            }).catch(function (err) {
                console.log(err);
            });
        },
        findtoolsByTaskId: function (Id) {
            var _this = this;
            var url = "task/tools/" + Id;
            axios.get(url).then(function (result) {
                console.log(result);
                _this.tools = result.data.data;
            }).catch(function (err) {
                console.log(err);
            });
        },
        deleteTask: function (id) {
            var _this = this;
            var url = "task/delete/" + id;
            axios.get(url).then(function (result) {
                if (result.data.flag === true) {
                    alert("删除成功!");
                    window.location.href = 'index.html';
                }
            }).catch(function (err) {
                console.log(err);
            });
        },
        saveTask: function () {
            var _this = this;
            var url = "task";
            axios.post(url, _this.task).then(function (result) {

                _this.taskId = result.data.data;
                alert("任务保存成功");
            }).catch(function (err) {
                console.log(err);
                alert("任务保存失败!")
            });
        },
        saveTools: function () {
            var _this = this;
            var url = "task/saveTools";
            axios.post(url, _this.tools).then(function (result) {
                alert("工具保存成功!");
                if (confirm("要跳转到首页么")) {
                    window.location.href = "index.html";
                }
            }).catch(function (err) {
                console.log(err);
            });
        }
    },
    created: function () {
        var Id = location.search.substring(4, location.search.length);
        if (Id.length > 0) {
            this.findById(Id);
            this.findtoolsByTaskId(Id);
        }
    }
});
