var React = require('react');
import { ProgressBar } from 'react-bootstrap';
var fetch = require('node-fetch');	

//for local deployment
var URI = "http://localhost:8081";

//for cloud deployment
//var URI = "http://ifawrap.cfapps.io:80";


var OrderStatus =  React.createClass({
	
	//set the initial status of react status
	getInitialState: function() {
		return {progressbarValue: 0, 
				statusHistory: ["noch leer"]};
	},
	
	statics: {
        serverConnectionInformation: {
            socketEndpoint: '/orderStatus',
			subscribingChannel : '/getOrderUpdate',
			getStatus: '/getStatus'
        },
    },
	
	updateStatus: function (content) {
		if (this.state.statusHistory[0] == "noch leer"){
			this.state.statusHistory = [];
		}
		if (content.status == "EINGEGANGEN"){
			this.state.statusHistory = ["EINGEGANGEN"];
		}
		else this.state.statusHistory.unshift(content.status);
		
		
		this.setState(
			{
				progressbarValue: content.percentage,
				statusHistory : this.state.statusHistory
			}
		);
	},
	

	//subscribe to websocket connection 
	//for receiving push notifications
	connectToWebSocket: function(callback){
		var stompClient = null;

		//connect to websocket
		function connect() {
			var socket = new SockJS(URI + OrderStatus.serverConnectionInformation.socketEndpoint);
			stompClient = Stomp.over(socket);
			
			stompClient.connect({}, function (frame) {
				console.log('Connected: ' + frame);
				stompClient.subscribe(OrderStatus.serverConnectionInformation.subscribingChannel, function (res) {
					callback(JSON.parse(res.body));
				});
			});
		}

		function disconnect() {
			if (stompClient != null) {
				stompClient.disconnect();
			}
			console.log("Disconnected");
		}
		connect();
	},

	
	componentWillMount: function(){
		
		// get initial status
		fetch(URI + OrderStatus.serverConnectionInformation.getStatus)
		.then(function(res) {
			return res.text();
		}).then(body=> { 
			this.updateStatus(JSON.parse(body));
		});
		
		this.connectToWebSocket(this.updateStatus);
	},
  
	render: function() {
		return (
			<div className="col-lg-12 text-center">
			<h1>Bestellprozess</h1>
			<p className="lead">Ihr Auto ist in folgendem Bestellstatus</p>
			<ProgressBar active now={this.state.progressbarValue} />
				<ul className="list-unstyled">
					{this.state.statusHistory.map(function(result, i) {
						return <li key={i}>{result}</li>;
					})}
				</ul>
			</div>
		);
	}
});

export default OrderStatus;
