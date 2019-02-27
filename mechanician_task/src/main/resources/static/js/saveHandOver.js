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
                    window.location.href = "index.html";
                } else {
                    window.location.reload();
                }
            }).catch(function (err) {
                console.log(err);
            });
        }
    }
});
