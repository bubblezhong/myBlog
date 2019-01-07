import React from 'react';
import {Link} from 'react-router-dom';
import FontAwesome from 'react-fontawesome';
require('../styles/article.css');
require('../styles/github-markdown.css');
require('../styles/github.css');
import axios from 'axios'
import 'gitalk/dist/gitalk.css'
import Gitalk from 'gitalk'


export default class Article extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      tags: [],
      title: '',
      content: '',
      createTime: '',
      categoryId: '',
      categoryName: '',
      viewNum: ''
    }
  }
  componentWillMount() {
    this.refresh();
  }
  componentDidMount() {

  }
  refresh = () => {
    axios.get(`/api/article/${this.props.match.params.articleId}`).then(res => {
      let tags = res.data.data.tags;
      let viewNum = res.data.data.viewNum;
      let title = res.data.data.title;
      let content = res.data.data.content;
      let createTime = res.data.data.createTime;
      let categoryId = res.data.data.categoryId;
      let categoryName = res.data.data.categoryName;
      this.setState({tags, viewNum, title, content, createTime, categoryId, categoryName})
      const gitalk = new Gitalk({
        clientID: 'fdbfd2eda1b0053c1c38',
        clientSecret: '551beed1b02592fc5d90236d682ff6f963b457a1',
        repo: 'blogComment',
        owner: 'zxxwin',
        admin: 'zxxwin',
        title
      })
      gitalk.render(this.refs.blogComment);
    })
  }
  render() {
    const title = this.state.title;
    const content = this.state.content;
    const viewNum = this.state.viewNum;
    const createTime = this.state.createTime;
    const categoryId = this.state.categoryId;
    const categoryName = this.state.categoryName;
    let tags = this.state.tags.map((tag, index) => (
      <Link to={`/tag/${tag.tagId}`} className="tags-link" key={index} >{tag.tagName}</Link>
    ))
    const resultContent = (
      <div>
        <div className="post-header main-content-wrap">
          <h2 className="post-title">
              {title}
          </h2>
          <div className="postShorten-meta post-meta">
            <time>
              {createTime}
            </time>
            <span className="marginLeft10">
              {categoryName ? <FontAwesome name="folder" /> : ''} </span>
              <Link className="category-link" to={`/category/${categoryId}`}>{categoryName}</Link>
              {viewNum ? <span className="marginLeft10"><FontAwesome name="eye" /> {viewNum}</span> : ''}
          </div>
        </div>
        <div ref="content" className="markdown-body" dangerouslySetInnerHTML={{__html:content}}>
        </div>
        {tags.length?
          <div style={{paddingTop: 0}} className="tags main-content-wrap">
            <span className="marginLeft10"><FontAwesome name="tags " /></span>
            {tags}
          </div>
          :
          ''
        }
        <div style={{paddingTop: 0}} ref="blogComment" className="blogComment main-content-wrap"></div>

      </div>
    )
    return (
      <div>
        {resultContent}
      </div>
    );
  }
}