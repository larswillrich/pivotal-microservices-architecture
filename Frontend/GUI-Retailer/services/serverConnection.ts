import { Injectable } from '@angular/core';
//import {StompClient} from 'stomp-client/lib'
//import {SockJS} from 'sockjs-client/lib'
declare var SockJS:any;
declare var Stomp:any;
declare var fetch:any;

@Injectable()
export class ServerConnectionService {

//for local deployment
//URI :string =  "http://localhost:8080";

//for cloud deployment;
URI :string =  "http://IFAAPI.cfapps.io:80";

socketEndpoint:string =  '/orderStatus';
subscribingChannel : string = '/getOrderUpdate';
getStatus: string = '/getStatus';

	incrementOrder(id){ 
		fetch(this.URI+"/incrementStatus?id=" + id)
		.then(function(res) {
			return res.json();
		});
	}
	
	newOrder(){
		fetch(this.URI+"/newOrder")
		.then(function(res) {
			return res.json();
		});
	}
	
	deleteOrder(id){
		fetch(this.URI+"/deleteOrder?id=" + id)
		.then(function(res) {
			return res.json();
		});
	}

	getCurrentOrders(callBack){
		fetch(this.URI+"/getAll")
		.then(function(res) {
			return res.json();
		}).then((json) => {
			callBack(this.mapIncomingData(json));
		});
	}
	
	mapIncomingData(data){
		var orderData = [];
		var i = 1;
		for (var a in data) 
			orderData.push({
				"tableIndex": i++,
				"id":data[a].id,
				"currentStatus":data[a].currentStatus,
				"percentage":data[a].percentage
		});
		console.log(data);
		return orderData;
	}
	
	connect(callBack) {
		var socket = new SockJS(this.URI + this.socketEndpoint);
		Stomp = Stomp.over(socket);

		Stomp.connect({}, (frame) => {

			Stomp.subscribe(this.subscribingChannel, (res)=> {
				callBack(this.mapIncomingData(JSON.parse(res.body)));
			});
		});
	}
		
	disconnect()  : void {
		if (Stomp != null) {
			Stomp.disconnect();
		}
		console.log("Disconnected");
	}
}