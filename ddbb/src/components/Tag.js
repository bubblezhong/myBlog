import React from 'react';
// import FontAwesome from 'react-fontawesome';
import NodeLine from './common/NodeLine';
import {Link} from 'react-router-dom';
import axios from 'axios';
require('../styles/style.css');
require('../styles/animate-bg.css');
require('../styles/time-line.css');
require('../styles/common.css');


export default class Tag extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    	tags: [],
    	head: '',
    	data: []
    }
  }
  componentWillReceiveProps(nextProps) {
     if (nextProps.match.params.tagId != this.props.match.params.tagId) {
       this.renderNodeLine(nextProps.match.params.tagId, this.getHead(nextProps.match.params.tagId));
     }
  }
  componentWillMount() {
  	axios.get('/api/tags').then( res => {
  		this.setState({tags: res.data.data});
	  	if (!this.props.match.params.tagId) {
	      this.renderNodeLine(this.state.tags[0].tagId, this.state.tags[0].tagName);
	  	} else {
	  		this.renderNodeLine(this.props.match.params.tagId, this.getHead(this.props.match.params.tagId));
	  	}
  	})
  }
  renderNodeLine = (tagId, tagName) => {
  	axios.get('/api/tags/' + tagId).then( res => {
  		this.setState({data: res.data.data}, this.renderNodeHead(tagName));
  	})
  }
  renderNodeHead = (tagName) => {
		// let tagName = this.getHead(this.props.match.params.tagId);
		this.setState({head: tagName});
	}
  getHead = (tagId) => {
  	let tagName;
    this.state.tags.forEach(item => {
    	if (item.tagId === parseInt(tagId) ) {
    		tagName = item.tagName;
    	}
    })
    return tagName;
  }
  render() {
  	const tags = this.state.tags.map( (tag, index) => (
      <Link to={'/tag/' + tag.tagId} key={index} className="tag-primary">{tag.tagName}</Link>
  	))
		return (
			<div>
				<div style={{margin: '15px 10%',paddingBottom: '10px', borderBottom: '1px solid rgb(214, 214, 216)'}}>
					{/*<input className="inline-search" type="search" placeholder="在此输入关键字以搜索..."/>*/}
					<div>
						{tags}
					</div>
				</div>
				{this.state.data ? <NodeLine head={this.state.head} data={this.state.data} type="tag" /> : ''}
			</div>
		)
  }
}
