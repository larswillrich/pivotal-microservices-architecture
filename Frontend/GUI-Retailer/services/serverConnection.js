"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
var ServerConnectionService = (function () {
    function ServerConnectionService() {
        //for local deployment
        //URI :string =  "http://localhost:8080";
        //for cloud deployment;
        this.URI = "http://IFAAPI.cfapps.io:80";
        this.socketEndpoint = '/orderStatus';
        this.subscribingChannel = '/getOrderUpdate';
        this.getStatus = '/getStatus';
    }
    ServerConnectionService.prototype.incrementOrder = function (id) {
        fetch(this.URI + "/incrementStatus?id=" + id)
            .then(function (res) {
            return res.json();
        });
    };
    ServerConnectionService.prototype.newOrder = function () {
        fetch(this.URI + "/newOrder")
            .then(function (res) {
            return res.json();
        });
    };
    ServerConnectionService.prototype.deleteOrder = function (id) {
        fetch(this.URI + "/deleteOrder?id=" + id)
            .then(function (res) {
            return res.json();
        });
    };
    ServerConnectionService.prototype.getCurrentOrders = function (callBack) {
        var _this = this;
        fetch(this.URI + "/getAll")
            .then(function (res) {
            return res.json();
        }).then(function (json) {
            callBack(_this.mapIncomingData(json));
        });
    };
    ServerConnectionService.prototype.mapIncomingData = function (data) {
        var orderData = [];
        var i = 1;
        for (var a in data)
            orderData.push({
                "tableIndex": i++,
                "id": data[a].id,
                "currentStatus": data[a].currentStatus,
                "percentage": data[a].percentage
            });
        console.log(data);
        return orderData;
    };
    ServerConnectionService.prototype.connect = function (callBack) {
        var _this = this;
        var socket = new SockJS(this.URI + this.socketEndpoint);
        Stomp = Stomp.over(socket);
        Stomp.connect({}, function (frame) {
            Stomp.subscribe(_this.subscribingChannel, function (res) {
                callBack(_this.mapIncomingData(JSON.parse(res.body)));
            });
        });
    };
    ServerConnectionService.prototype.disconnect = function () {
        if (Stomp != null) {
            Stomp.disconnect();
        }
        console.log("Disconnected");
    };
    ServerConnectionService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [])
    ], ServerConnectionService);
    return ServerConnectionService;
}());
exports.ServerConnectionService = ServerConnectionService;
//# sourceMappingURL=serverConnection.js.map