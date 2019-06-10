new Vue({
    el: "#app",
    data: {
        users: {}
    },
    methods: {
        /*findById:function (id) {
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
        },*/
        userLogIn: function () {
            var _this = this;
            var url = "task/userLogin";
            axios.post(url, _this.users).then(function (result) {
                if (result.data.flag === true) {
                    window.location.href = "index.html"
                } else {
                    alert(result.data.massage);
                }
            });
        },
        checkLogin: function () {
            var _this = this;
            var url = "task/checkLogin";
            axios.get(url).then(function (result) {
                console.log(result);
                if (result.data.flag === true) {
                    window.location.href = "index.html"
                }
            }).catch(function (err) {
                console.log(err);
            });
        }

    },
    created: function () {
        this.checkLogin();
    }
});
