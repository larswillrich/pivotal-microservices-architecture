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
var ng_bootstrap_1 = require('@ng-bootstrap/ng-bootstrap');
var serverConnection_1 = require('../../services/serverConnection');
var DataSet = (function () {
    function DataSet(customerID, progress, orderID) {
        this._customerID = customerID;
        this._progress = progress;
        this._orderID = orderID;
    }
    Object.defineProperty(DataSet.prototype, "customerID", {
        get: function () {
            return this._customerID;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(DataSet.prototype, "progress", {
        get: function () {
            return this._progress;
        },
        enumerable: true,
        configurable: true
    });
    Object.defineProperty(DataSet.prototype, "orderID", {
        get: function () {
            return this._orderID;
        },
        enumerable: true,
        configurable: true
    });
    return DataSet;
}());
var List = (function () {
    function List(config, serverConnection) {
        var _this = this;
        this.captures = ["Order ID", "Progress", "Status"];
        this.data = [];
        // customize default values of progress bars used by this component tree
        config.max = 100;
        config.striped = true;
        config.animated = true;
        config.type = 'success';
        this.serverConnection = serverConnection;
        console.log(this.serverConnection);
        this.serverConnection.connect(function (res) {
            _this.data = [];
            for (var _i = 0, res_1 = res; _i < res_1.length; _i++) {
                var a = res_1[_i];
                _this.data.push(new DataSet(a.id, a.percentage, a.currentStatus));
            }
            console.log(_this.data);
        });
        this.serverConnection.getCurrentOrders(function (res) {
            _this.data = [];
            for (var _i = 0, res_2 = res; _i < res_2.length; _i++) {
                var a = res_2[_i];
                _this.data.push(new DataSet(a.id, a.percentage, a.currentStatus));
            }
            console.log(_this.data);
        });
        this.currentValue = 50;
    }
    List.prototype.ngOnInit = function () {
    };
    List = __decorate([
        core_1.Component({
            selector: 'list',
            template: require('./template.html'),
            providers: [ng_bootstrap_1.NgbProgressbarConfig, serverConnection_1.ServerConnectionService] // add the NgbProgressbarConfig to the component providers
        }), 
        __metadata('design:paramtypes', [ng_bootstrap_1.NgbProgressbarConfig, serverConnection_1.ServerConnectionService])
    ], List);
    return List;
}());
exports.List = List;
//# sourceMappingURL=listView.js.map