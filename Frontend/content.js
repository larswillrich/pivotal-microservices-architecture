var React = require('react');
import { ProgressBar } from 'react-bootstrap';
var fetch = require('node-fetch');	


var connectToWebSocket = function(callback){
	var stompClient = null;


	function connect() {
		var socket = new SockJS('http://localhost:8080/orderStatus');
		stompClient = Stomp.over(socket);
		

		stompClient.connect({}, function (frame) {

			console.log('Connected: ' + frame);
			stompClient.subscribe('/topic/greetings', function (greeting) {
				callback(greeting.body);
				console.log(greeting.body);
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
}
export default React.createClass({
	
	getInitialState: function() {
		return {progressbarValue: 75, 
				status: "noch leer"};
	},
	
	componentWillMount: function(){
		
		var that = this;
		
		fetch('http://localhost:8080/subscribe')
		.then(function(res) {
			return res.text();
		}).then(function(body) {
			connectToWebSocket(function (content) {
				console.log("content: " + content);
				that.setState(
					{
						progressbarValue: that.state.progressbarValue,
						status: content
					}
				);
			});
		});
	},

  
	render: function() {
		return (
			<div className="col-lg-12 text-center">
			<h1>Bestellprozess</h1>
			<p className="lead">Ihr Auto ist in folgendem Bestellstatus</p>
			<ProgressBar active now={this.state.progressbarValue} />
			<div>{this.state.status}</div>
				<ul className="list-unstyled">
					<li>Bootstrap v3.3.7</li>
					<li>jQuery v1.11.1</li>
				</ul>
			</div>
		);
	}
});
