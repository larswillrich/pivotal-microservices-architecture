var React = require('react');
import { ProgressBar } from 'react-bootstrap';
var fetch = require('node-fetch');
import ServerConnection from './services/ServerConnection';

//for local deployment
var URI = "http://localhost:8080";

//for cloud deployment
//var URI = "http://ifaApi.cfapps.io:80";

var OrderStatus =  React.createClass({
	
	//set the initial status of react status
	getInitialState: function() {
		return {progressbarValue: 0, 
				statusHistory: ["noch leer"]}; 
	},
	
	componentWillMount: function(){
		this.ServerConnection = new ServerConnection();
		console.log(this.ServerConnection);
		this.ServerConnection.connect((res) =>{
			this.data = [];
			for (let a of res) {
				if (a.id == this.state.orderIDToView){
					this.setState({progressbarValue: a.percentage, 
				statusHistory: [a.currentStatus]});
				}
			}

			console.log("state: " + this.state);
		});

		this.ServerConnection.getCurrentOrders((res) => {
			this.data = [];
			for (let a of res) {
				if (a.id == this.state.orderIDToView){
					this.setState({progressbarValue: a.percentage, 
				statusHistory: [a.currentStatus]});
				}
			}

			console.log("state: " + this.state);
		});
	},
	
	viewOrder: function(){
		console.log("hello" + this.state.orderIDToView);
		this.ServerConnection.getCurrentOrders((res) => {
		this.data = [];
		for (let a of res) {
			if (a.id == this.state.orderIDToView){
				this.setState({progressbarValue: a.percentage, 
				statusHistory: [a.currentStatus]});
			}
		}
		});
	},
	
	handleChange: function (evt) {
		this.state.orderIDToView = evt.target.value;
	},
  
	render: function() {
		return (
			<div className="row">
				<div className="col-xs-4">
					<div className="keyInputForm">
						<div className="form-inline">
							<div className="form-group">
								<label>Key: </label>
								<input onChange={this.handleChange} type="text" className="form-control" id="email"/>
								<button onClick={this.viewOrder} type="submit" className="btn btn-default">Submit</button>
							</div>
						</div>
					</div>
				</div>
				<div className="col-xs-8">
					<div className="col-lg-12 text-center orderStatus">
						<h1>Bestellprozess</h1>
						<p className="lead">Ihr Auto ist in folgendem Bestellstatus</p>
						<ProgressBar active now={this.state.progressbarValue} />
						<ul className="list-unstyled">
							{this.state.statusHistory.map(function(result, i) {
								return <li key={i}>{result}</li>;
							})}
						</ul>
					</div>
				</div>
			</div>

		);
	}
});

export default OrderStatus;
