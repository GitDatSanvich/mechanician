new Vue({
    el: "#app",
    data: {
        handOver: {
            main: ""
        }
    },
    methods: {
        saveHandOver: function () {
            var _this = this;
            var url = "task/handOver/";
            axios.post(url, _this.handOver).then(function (result) {
                alert("保存成功");
                if (confirm("跳转首页还是再写一个?")) {
                    window.location.reload();
                } else {
                    window.location.href = "index.html";
                }
            }).catch(function (err) {
                console.log(err);
            });
        },
        checkLogin: function () {
            var _this = this;
            var url = "task/checkLogin";
            axios.get(url).then(function (result) {
                console.log(result);
                if (result.data.flag !== true) {
                    window.location.href = "userLogin.html"
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
