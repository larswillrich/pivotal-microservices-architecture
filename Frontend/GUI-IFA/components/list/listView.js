import angular from 'angular';
import ServerConnection from '../../services/serverConnection';
import progressbar from 'angular-ui-bootstrap/src/progressbar';

function List() {
  return {
    restrict: 'E',
	controller: 'ListController',
	controllerAs: 'lc',
    scope: {
      orderData: '='
    },
    template: require('./template.html')
  }
}

var ListController =  class ListController {
	

	incrementOrder(id){
		this.serverConnection.incrementOrder(id);
	}
	
	deleteOrder(id){
		this.serverConnection.deleteOrder(id);
	}
	
	newOrder(id){
		this.serverConnection.newOrder();
	}
	
	constructor($scope, ServerConnection) {
		this.$scope = $scope;
		
		this.orderData = {};
		this.orderData.data = [];
		
		this.serverConnection = ServerConnection;
		
		this.serverConnection.getCurrentOrders((res) => {
			this.orderData.data = res
			this.$scope.$apply();
		});
		
		this.serverConnection.connect((res)=>{
			this.orderData.data = res;
			this.$scope.$apply();
		});
	}
}

export default angular.module('ifa.ListController', [ServerConnection, progressbar])
  .controller('ListController', ListController)
  .directive('list', List)
  .name;