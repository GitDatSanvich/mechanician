
new Vue({
    el:"#app",
    data:{
        userList:[],
        user:{}
    },
    methods:{
        findAll:function(){
            var _this = this;
            var url = "user/findAll";
            axios.get(url).then(function (result) {
                console.log(result);
                _this.userList = result.data;
            }).catch(function (err) {
                console.log(err);
            });
        },

        findById:function (id) {
            var _this = this;
            var url = "user/findById/"+id;
            axios.get(url).then(function (result) {
                _this.user = result.data;
                console.log(result);
                //显示窗口
                $("#myModal").modal("show");
            }).catch(function (err) {
                console.log(err);
            });
        },

        update:function () {
            var _this = this;
            var url = "user/update";
            axios.post(url,_this.user).then(function () {
                $("#myModal").modal("hide");
                _this.findAll();
            }).catch(function (err) {
                console.log(err);
            });
        }
    },
    created:function () {
        this.findAll();
    }
});
