new Vue({
    el: "#app",
    data: {
        task: {},
        taskList: [],
        chapter: {
            chapter1: "",
            chapter2: "",
            chapter3: ""
        },
        handOverList: []
    },
    methods: {
        findAll: function () {
            var _this = this;
            var url = "task";
            axios.get(url).then(function (result) {
                console.log(result);
                _this.taskList = result.data.data;
            }).catch(function (err) {
                console.log(err);
            });
        },
        toDetail: function (id) {
            window.open("detail.html?Id=" + id);
        },
        toSave: function () {
            window.open("save.html?Id=");
        },
        toUpdate: function (id) {
            window.open("save.html?Id=" + id);
        },
        toSearch: function () {
            var _this = this;
            var url = "task/search";
            axios.post(url, _this.chapter).then(function (result) {
                _this.taskList = result.data.data;
            }).catch(function (err) {
                console.log(err);
            });
        },
        findHandOver: function () {
            var _this = this;
            var url = "task/handOver";
            axios.get(url).then(function (result) {
                _this.handOverList = result.data.data;
            }).catch(function (err) {
                console.log(err);
            });
        },
        toDelete: function (id) {
            if (confirm("真的要删除么？这条数据删除了无法恢复（叫爸爸也不好使）")) {
                var _this = this;
                var url = "task/handOver/" + id;
                axios.get(url).then(function (result) {

                    window.location.reload();
                }).catch(function (err) {
                    console.log(err);
                });
            }
        },
        toHandOver: function () {
            window.open("HandOver.html");

        }
    },
    created: function () {
        this.findAll();
        this.findHandOver();
    }
});
