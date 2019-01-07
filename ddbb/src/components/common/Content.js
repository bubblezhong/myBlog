import React from 'react';
import {
  Route,
  Switch,
  Redirect
} from 'react-router-dom';
import NewArticle from '../NewArticle';
import Article from '../Article';
import ArticleList from '../ArticleList';
import About from '../About';
import Tag from '../Tag';
import Category from '../Category';
import Archive from '../Archive';
export default class Content extends React.Component {
  render() {
    return (

    	<div className="content-box">
        <Switch>
          <Redirect from='/' exact to='/newArticle'/>
          <Route exact path="/article"  component={ArticleList}/>
        </Switch>
				<Route exact path="/article/:articleId" component={Article}/>
        <Route exact path="/NewArticle" component={NewArticle}/>
        <Route exact path="/archive" component={Archive}/>
        <Route exact path="/category/:categoryId" component={Category}/>
        <Route exact path="/category" component={Category}/>
        <Route exact path="/tag/:tagId" component={Tag}/>
        <Route exact path="/tag" component={Tag}/>
        <Route exact path="/about" component={About}/>
    	</div>

    );
  }
}