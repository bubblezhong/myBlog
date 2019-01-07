import React from 'react';
// import FontAwesome from 'react-fontawesome';
import NodeLine from './common/NodeLine';
import {Link} from 'react-router-dom';
import axios from 'axios';
require('../styles/style.css');
require('../styles/animate-bg.css');
require('../styles/time-line.css');
require('../styles/common.css');


export default class Archive extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    	years: [],
    	head: '',
    	data: []
    }
  }
  componentWillMount() {
  	axios.get('/api/archive').then( res => {
  		this.setState({data: res.data.data, head: res.data.data[0].time.substring(0,4)});
  	})
  }
  render() {
		const data = this.state.data;
		let monthNodes = [];
		for (var i = 1; i < 13; i++) {
			monthNodes[i] =  new Array();
		}
		data.forEach((item) => {
			monthNodes[parseInt(item.time.substring(5,7))].push(item);
		})
		const timeNode = monthNodes.map((node, index) => {
			if (node.length != 0) {
				return (
					<NodeLine key={index} head={`${this.state.head}年${index}月`} data={node} type="archive" />
				)
			}
		})
		return (
			<div>
				{timeNode.reverse()}
			</div>
		)
  }
}
