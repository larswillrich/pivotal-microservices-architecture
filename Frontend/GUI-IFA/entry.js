import angular from 'angular';

import ListController from "./components/list/listView";
import NewOrderController from "./components/newOrder/newOrder";

var ifa = angular.module('ifa', [ListController, NewOrderController]);
