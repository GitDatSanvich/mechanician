new Vue({
    el: "#app",
    data: {
        users: {}
    },
    methods: {
        signIn: function () {
            var _this = this;
            var url = "task/signIn";
            axios.post(url, _this.users).then(function (result) {
                if (result.data.flag === true) {
                    alert("用户注册成功请查看邮箱并激活账户,以便进入管理员审核状态");
                    window.location.href = "userLogin.html"
                } else {
                    alert(result.data.massage);
                }
            });
        },
    }
});
