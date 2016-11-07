import angular from 'angular';
import ServerConnection from '../../services/serverConnection';

var NewOrderController =  class NewOrderController {
  constructor(ServerConnection) {
    this.name = 'World';
  }
}

function NewOrder() {
  return {
    restrict: 'E',
	controller: 'NewOrderController',
    scope: {
      name: '='
    },
    templateURL: './template.html'
  }
}

export default angular.module('ifa.NewOrderController', [ServerConnection])
  .controller('NewOrderController', NewOrderController)
  .directive('newOrder', NewOrder)
  .name;
  