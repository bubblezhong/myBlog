import React from 'react';
import {Link} from 'react-router-dom';
require('../../styles/node-line.css');

export default class NodeLine extends React.Component {
  constructor(props) {
    super(props);
  }
  render() {
  	const rows = this.props.data.map((row, index) => (
		  <div className="timeline-row" key={index}>
		    <span className="node"></span>
		    <div className="content">
		      <h1>
		        <Link className="timeline-article-title" to={"/article/" + row.articleId}>{row.title}</Link>
		      </h1>
		      <div className="article-meta">
		        <div className="article-date">
		          <i className="fa fa-calendar"></i>
		          <time>{row.time}</time>
		        </div>
		        <div className="article-category">
		          <i className="fa fa-folder"></i>
		          <Link className="article-category-link" to={"/category/" + row.categoryId}>{row.category}</Link>
		         </div>
		      </div>
		    </div>
		  </div>
  	))
    return (
			<div className="timeline timeline-wrap">
			  <div className="timeline-row timeline-row-major">
			    <span className="node">
			      <i className={this.props.type == "tag"?"fa fa-tags":this.props.type == "category"?"fa fa-folder":"fa fa-calendar" }></i>
			    </span>
			    <h1 className="title">{this.props.head}</h1>
			  </div>
				{rows}
			</div>
    );
  }
}