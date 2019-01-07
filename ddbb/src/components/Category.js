import React from 'react';
// import FontAwesome from 'react-fontawesome';
import NodeLine from './common/NodeLine';
import {Link} from 'react-router-dom';
import axios from 'axios';
require('../styles/style.css');
require('../styles/animate-bg.css');
require('../styles/time-line.css');
require('../styles/common.css');


export default class Category extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    	categories: [],
    	head: '',
    	data: []
    }
  }
  componentWillReceiveProps(nextProps) {
     if (nextProps.match.params.categoryId != this.props.match.params.categoryId) {
       this.renderNodeLine(nextProps.match.params.categoryId, this.getHead(nextProps.match.params.categoryId));
     }
  }
  componentWillMount() {
  	axios.get('/api/category').then( res => {
  		this.setState({categories: res.data.data});
	  	if (!this.props.match.params.categoryId) {
	      this.renderNodeLine(this.state.categories[0].categoryId, this.state.categories[0].categoryName);
	  	} else {
	  		this.renderNodeLine(this.props.match.params.categoryId, this.getHead(this.props.match.params.categoryId));
	  	}
  	})
  }
  renderNodeLine = (categoryId, categoryName) => {
  	axios.get('/api/category/' + categoryId).then( res => {
  		this.setState({data: res.data.data}, this.renderNodeHead(categoryName));
  	})
  }
  renderNodeHead = (categoryName) => {
		// let categoryName = this.getHead(this.props.match.params.categoryId);
		this.setState({head: categoryName});
	}
  getHead = (categoryId) => {
  	let categoryName;
    this.state.categories.forEach(item => {
    	if (item.categoryId === parseInt(categoryId) ) {
    		categoryName = item.categoryName;
    	}
    })
    return categoryName;
  }
  render() {
  	const categories = this.state.categories.map( (category, index) => (
      <Link to={'/category/' + category.categoryId} key={index} className="tag-primary">{category.categoryName}</Link>
  	))
		return (
			<div>
				<div style={{margin: '15px 10%',paddingBottom: '10px', borderBottom: '1px solid rgb(214, 214, 216)'}}>
					{/*<input className="inline-search" type="search" placeholder="在此输入关键字以搜索..."/>*/}
					<div>
						{categories}
					</div>
				</div>
				{this.state.data ? <NodeLine head={this.state.head} data={this.state.data} type="category" /> : ''}
			</div>
		)
  }
}
