import React from 'react';
import {Link} from 'react-router-dom';
import axios from 'axios';
require('../styles/article-list.css');
let scrollTop = 0;
export default class ArticleList extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
    	page: 1,
    	pageSize: 10,
    	hasMore: true,
    	list: []
    }
  }
	componentWillMount() {
		axios.get(`/api/article?page=${this.state.page}&pageSize=${this.state.pageSize}`).then(res => {
			sessionStorage.list = JSON.stringify(res.data.data.list);
			console.log(this.state.page);
			this.setState({list: res.data.data.list, hasMore: res.data.data.hasNextPage});
		})
	}
  loadMore = () => {
		axios.get(`/api/article?page=${this.state.page+1}&pageSize=${this.state.pageSize}`).then(res => {
			sessionStorage.list = null;
			let list = this.state.list;
			this.setState({page: this.state.page + 1, list: list.concat(res.data.data.list), hasMore: res.data.data.hasNextPage}, () => {
				sessionStorage.list = JSON.stringify(this.state.list);
			});
		})

  }
  handleScroll = () => {
    scrollTop  = this.refs.bodyBox.scrollTop;  //滚动条滚动高度
  }
  setScrollTop = () => {
    this.refs.bodyBox.scrollTop = sessionStorage.scrollTop
  }
  componentWillUnmount() {
    sessionStorage.scrollTop = scrollTop;
		sessionStorage.page = this.state.page;
  }
  componentDidMount() {
  	this.refs.bodyBox.scrollTop = sessionStorage.scrollTop
  	setTimeout(()=>{}, 100);
  }
  render() {
  	const list = this.state.list.map((article, index) => {
  		return (
					<li key={index}>
							<Link to={`/article/${article.articleId}`}>
								<img src={article.cover} alt=""/>
							</Link>
						<h3>
							<Link to={`/article/${article.articleId}`} className="title">
								{article.title}
							</Link>
						</h3>
						{/*
							article.descr?
								<div className="desc">
									{article.descr}
								</div>
							:''
						*/}
						{/*		时间，类别	*/}
						<div className="info">

							<Link to={`/category/${article.categoryId}`} className="category">
								{/*<FontAwesome name='folder' />*/}  {article.categoryName}  
							</Link>
							<time>
								&nbsp;·&nbsp; {article.createTime}&nbsp;·&nbsp; {article.viewNum} 次查看
							</time>
						</div>

						{/*<div className="tags">
							{tags}
						</div>*/}
					</li>


  		)
  	})

		if (!this.state.hasMore ) {
			list.push(
					<li key={Math.random()}><span style={{color: '#aaa'}}>已经没有更多内容...</span></li>
			)
		}

    return (
			<div className="main-body"  onScroll={this.handleScroll}  ref="bodyBox">
				<ul className="article-list">
					{list}
				</ul>
				{
					list.length != 0 && this.state.hasMore ?
						<div className="page-nav">
							{/*<span className="prev">
								<FontAwesome name='angle-left' />  上一页
							</span><span className="next">
								下一页  <FontAwesome name='angle-right' />
							</span>*/}
							<div style={{cursor: 'pointer'}} onClick={this.loadMore} className="loadmore">加载更多</div>
						</div>
					:
					''
				}

			</div>
    );
  }
}