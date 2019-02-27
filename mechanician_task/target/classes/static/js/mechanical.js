new Vue({
    el:"#app",
    data:{
        task:{},
        taskList:[],
        chapter: {
            chapter1: "",
            chapter2: "",
            chapter3: ""
        }
    },
    methods:{
        findAll:function(){
            var _this = this;
            var url = "task";
            axios.get(url).then(function (result) {
                console.log(result);
                _this.taskList = result.data.data;
            }).catch(function (err) {
                console.log(err);
            });
        },
        toDetail:function (id) {
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
        }
    },
    created:function () {
        this.findAll();
    }
});
