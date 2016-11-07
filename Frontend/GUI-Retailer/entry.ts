import 'zone.js';
import 'reflect-metadata';

import {NgModule}      from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {Component} from '@angular/core';
import {platformBrowserDynamic} from '@angular/platform-browser-dynamic';
import {List} from './components/list/listView'
import {NgbModule} from '@ng-bootstrap/ng-bootstrap'
import './style.css';


@Component({
    selector: 'listElement',
    template:   `<div class="col-md-2">
                </div>
                <div class="col-md-8">
                    <list></list>
                </div>
                <div class="col-md-2">
                </div>`,
})
export class App {
    constructor() {
    }
}

@NgModule({
    imports: [BrowserModule, NgbModule],
    declarations: [List, App],
    bootstrap: [App]
})
class AppModule {
}


const platform = platformBrowserDynamic();
platform.bootstrapModule(AppModule);