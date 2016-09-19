var React = require('react');
import { ProgressBar } from 'react-bootstrap';

export default React.createClass({
	
	getInitialState: function() {
		return {progressbarValue: 75};
	},
  
	render: function() {
		return (
			<div className="col-lg-12 text-center">
			<h1>Bestellprozess</h1>
			<p className="lead">Ihr Auto ist in folgendem Bestellstatus</p>
			<ProgressBar active now={this.state.progressbarValue} />
				<ul className="list-unstyled">
					<li>Bootstrap v3.3.7</li>
					<li>jQuery v1.11.1</li>
				</ul>
			</div>
		);
	}
});
