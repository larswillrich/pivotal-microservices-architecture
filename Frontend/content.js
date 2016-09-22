var React = require('react');
import { ProgressBar } from 'react-bootstrap';
var fetch = require('node-fetch');	

var URI = "http://localhost:8080";
//var URI = "http://ifawrap.cfapps.io:80";

var connectToWebSocket = function(callback){
	var stompClient = null;


	function connect() {
		var socket = new SockJS(URI + '/orderStatus');
		stompClient = Stomp.over(socket);
		

		stompClient.connect({}, function (frame) {

			console.log('Connected: ' + frame);
			stompClient.subscribe('/topic/greetings', function (greeting) {
				callback(JSON.parse(greeting.body));
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
		return {progressbarValue: 0, 
				statusHistory: ["noch leer"]};
	},
	
	componentWillMount: function(){
		
		var that = this;
		
		fetch(URI + '/subscribe')
		.then(function(res) {
			return res.text();
		}).then(function(body) {
			
			var updateStatus = function (content) {
				if (that.state.statusHistory[0] == "noch leer"){
					that.state.statusHistory = [];
				}
				if (content.status == "EINGEGANGEN"){
					that.state.statusHistory = ["EINGEGANGEN"];
				}
				else that.state.statusHistory.unshift(content.status);
				
				
				that.setState(
					{
						progressbarValue: content.percentage,
						statusHistory : that.state.statusHistory
					}
				);
			}
			
			updateStatus(JSON.parse(body));
			
			connectToWebSocket(updateStatus);
		});
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
