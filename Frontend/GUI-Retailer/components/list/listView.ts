import {Component, Input} from '@angular/core';
import {NgbProgressbar} from '@ng-bootstrap/ng-bootstrap/progressbar/progressbar';
import {NgbProgressbarConfig} from '@ng-bootstrap/ng-bootstrap';

import {ServerConnectionService} from '../../services/serverConnection';


class DataSet {
	private _customerID: string;
	private _progress: number;
	private _orderID: string;

	get customerID(): string {
		return this._customerID;
	}
	get progress(): number {
		return this._progress;
	}
	get orderID(): string {
		return this._orderID;
	}
	
	constructor (customerID: string, progress: number, orderID: string) {
		this._customerID = customerID;
		this._progress = progress;
		this._orderID = orderID;
	}
}

@Component({
    selector: 'list',
    template: require('./template.html'),
	providers: [NgbProgressbarConfig, ServerConnectionService] // add the NgbProgressbarConfig to the component providers
})
export class List {
	
	public currentValue:number;
	public captures:Array<string> = ["Order ID", "Progress", "Status"];
	public data:Array<DataSet> = [];

	private serverConnection:ServerConnectionService;

  constructor(config: NgbProgressbarConfig, serverConnection: ServerConnectionService) {
    // customize default values of progress bars used by this component tree
    config.max = 100;
    config.striped = true;
    config.animated = true;
    config.type = 'success';
	
	this.serverConnection = serverConnection;
	console.log(this.serverConnection);
	this.serverConnection.connect((res) =>{
			this.data = [];
			for (let a of res) {
				this.data.push(new DataSet(a.id, a.percentage, a.currentStatus));
			}

			console.log(this.data);
	});
	
	this.serverConnection.getCurrentOrders((res) => {
		this.data = [];
			for (let a of res) {
				this.data.push(new DataSet(a.id, a.percentage, a.currentStatus));
			}

			console.log(this.data);
	});
		
	this.currentValue = 50;
  }

    ngOnInit() {
    }
}
