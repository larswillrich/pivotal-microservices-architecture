var React = require('react');
import { ProgressBar } from 'react-bootstrap';
var fetch = require('node-fetch');


var socketEndpoint =  '/orderStatus';
var subscribingChannel = '/getOrderUpdate';
var getStatus = '/getStatus';

export default class{
	constructor() {
		//for local deployment
		//this.URI = "http://localhost:8080";

		//for cloud deployment
		this.URI =  "http://IFAAPI.cfapps.io:80";
	}
  
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
		console.log(orderData);
		return orderData;
	}
	
	connect(callback) {
		var socket = new SockJS(this.URI + socketEndpoint);
		var stomp = Stomp.over(socket);

		stomp.connect({}, (frame) => {

			stomp.subscribe(subscribingChannel, (res)=>  {

				callback(this.mapIncomingData(JSON.parse(res.body)));
			});
		});
	}
		
	disconnect() {
		if (stomp != null) {
			stomp.disconnect();
		}
		console.log("Disconnected");
	}
}
