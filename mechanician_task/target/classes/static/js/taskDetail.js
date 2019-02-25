new Vue({
    el: "#app",
    data: {
        Id: {},
        task: {},
        tools: {}
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
        }
    },
    created: function () {
        var Id = location.search.substring(4, location.search.length);
        this.findById(Id);
        this.findtoolsByTaskId(Id);
    }
});
